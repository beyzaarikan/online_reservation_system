package gui;

import java.awt.*;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.swing.*;
import models.Reservation;
import models.User;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import repository.ReservationRepository;


public class AllReservationsPage extends BasePanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> typeFilter;
    private ReservationRepository reservationRepository;
    private User currentUser; // Mevcut kullanıcı bilgisi
    
    // Constructor - kullanıcı bilgisini parametre olarak alır
    public AllReservationsPage(User user) {
        super("My Reservations", 1200, 800);
        this.currentUser = user;
        this.reservationRepository = GlobalRepositoryManager.getInstance().getReservationRepository();
    }
    
    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(42, 39, 83));

        // Ana panel - gradient arkaplan
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(42, 39, 83),
                    getWidth(), getHeight(), new Color(65, 50, 110)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Dekoratif yumuşak ışık efektleri
                g2d.setColor(new Color(147, 112, 219, 25));
                g2d.fillOval(-100, -100, 300, 300);
                g2d.fillOval(getWidth()-200, getHeight()-200, 300, 300);
                
                g2d.setColor(new Color(138, 43, 226, 15));
                g2d.fillOval(getWidth()-150, -100, 250, 250);
                g2d.fillOval(-150, getHeight()-150, 250, 250);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createBackButton("← Back to Menu");
        backPanel.add(backButton);

        // Title section - kullanıcı adı eklendi
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("My Reservations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Kullanıcı adını gösteren subtitle
        JLabel subtitleLabel = new JLabel("Welcome " + currentUser.getName() + " - Manage your reservations", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);

        headerPanel.add(backPanel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Tab benzeri navigasyon
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tabPanel.setOpaque(false);
        tabPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));
        
        // Ana içerik kartı - tek büyük kart
        JPanel contentCard = createMainCard();
        contentCard.setLayout(new BorderLayout());
        contentCard.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Statistics row - kullanıcıya özel istatistikler
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 20, 0));
        statsRow.setOpaque(false);
        statsRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Sadece bu kullanıcının rezervasyonlarını al
        Collection<Reservation> userReservations = getUserReservations();
        int totalReservations = userReservations.size();
        int busReservations = (int) userReservations.stream().filter(r -> "Bus".equals(getReservationType(r))).count();
        int flightReservations = (int) userReservations.stream().filter(r -> "Flight".equals(getReservationType(r))).count();
        
        JPanel totalCard = createStatCard("Total Reservations", String.valueOf(totalReservations), new Color(138, 43, 226));
        JPanel busCard = createStatCard("Bus Reservations", String.valueOf(busReservations), new Color(46, 204, 113));
        JPanel flightCard = createStatCard("Flight Reservations", String.valueOf(flightReservations), new Color(52, 152, 219));
        
        statsRow.add(totalCard);
        statsRow.add(busCard);
        statsRow.add(flightCard);

        // Filter section
        JPanel filterSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15));
        filterSection.setOpaque(false);
        filterSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        typeFilter = createStyledComboBox(new String[]{"All Types", "Bus", "Flight"});
        JButton refreshButton = createActionButton("Refresh", new Color(52, 152, 219));
        
        filterSection.add(typeFilter);
        filterSection.add(Box.createHorizontalStrut(15));
        filterSection.add(refreshButton);
        filterSection.add(Box.createHorizontalStrut(10));

        // Table section
        createTable();
        loadUserReservations(); // Kullanıcıya özel rezervasyonları yükle
        
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(1100, 350));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        actionPanel.setOpaque(false);
        
        JButton viewDetailsButton = createActionButton("View Details", new Color(138, 43, 226));
        JButton cancelReservationButton = createActionButton("Cancel Reservation", new Color(231, 76, 60));
        
        actionPanel.add(viewDetailsButton);
        actionPanel.add(cancelReservationButton);
        contentCard.add(statsRow, BorderLayout.NORTH);
        
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setOpaque(false);
        middlePanel.add(filterSection, BorderLayout.NORTH);
        middlePanel.add(scrollPane, BorderLayout.CENTER);
        
        contentCard.add(middlePanel, BorderLayout.CENTER);
        contentCard.add(actionPanel, BorderLayout.SOUTH);
        
        // Layout assembly
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(tabPanel, BorderLayout.NORTH);
        centerPanel.add(contentCard, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        setupActionListeners(backButton, refreshButton, viewDetailsButton, cancelReservationButton);
    }

    // Kullanıcıya özel rezervasyonları getiren method
    private Collection<Reservation> getUserReservations() {
        return reservationRepository.reservationMap.values().stream()
                .filter(reservation -> reservation.getUser().getId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    // Kullanıcıya özel rezervasyonları tabloya yükleyen method
    private void loadUserReservations() {
        tableModel.setRowCount(0);
        Collection<Reservation> userReservations = getUserReservations();
        
        for (Reservation reservation : userReservations) {
            String seatList = reservation.getSeats().stream()
                    .map(seat -> String.valueOf(seat.getSeatNo()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("N/A");
            
            tableModel.addRow(new Object[]{
                reservation.getId(), // Rezervasyon ID'si göster
                reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                reservation.getTrip().getDepartureTime(),
                seatList,
                reservation.getTrip().getArrivalTime()
            });
        }
        
        // Eğer kullanıcının hiç rezervasyonu yoksa bilgilendirici mesaj
        if (userReservations.isEmpty()) {
            tableModel.addRow(new Object[]{
                "No reservations found", "", "", "", ""
            });
        }
    }

    private JButton createBackButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color bgColor = new Color(255, 255, 255, 15);
                if (getModel().isPressed()) {
                    bgColor = new Color(255, 255, 255, 25);
                } else if (getModel().isRollover()) {
                    bgColor = new Color(255, 255, 255, 20);
                }
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.setColor(new Color(255, 255, 255, 40));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        
        return button;
    }

    private JButton createTabButton(String text, boolean active) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (active) {
                    g2d.setColor(new Color(138, 43, 226));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                } else {
                    if (getModel().isRollover()) {
                        g2d.setColor(new Color(255, 255, 255, 15));
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    }
                }
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(active ? Color.WHITE : new Color(189, 147, 249));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 45));
        button.setMargin(new Insets(10, 20, 10, 20));
        
        return button;
    }

    private JPanel createMainCard() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 0, 51),
                    getWidth(), getHeight(), new Color(124, 58, 237));
                
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.setColor(accentColor);
                g2d.fillRoundRect(0, 0, 4, getHeight(), 2, 2);
                
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.LEFT);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(189, 147, 249));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(titleLabel);
        
        return card;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<String>(items) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                
                super.paintComponent(g);
            }
        };
        comboBox.setOpaque(false);
        comboBox.setForeground(Color.BLACK);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(140, 45));
        return comboBox;
    }

    private JButton createActionButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bgColor = baseColor;
                if (getModel().isPressed()) {
                    bgColor = baseColor.darker();
                } else if (getModel().isRollover()) {
                    bgColor = baseColor.brighter();
                }
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 40));
        
        return button;
    }

    private void createTable() {
        String[] columnNames = {
            "Reservation ID", "Trip", "Departure Date", "Seats", "Departure Time"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        reservationTable = new JTable(tableModel) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(255, 255, 255, 3));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                super.paintComponent(g);
            }
        };
        
        reservationTable.setOpaque(false);
        reservationTable.setBackground(new Color(255, 255, 255, 3));
        reservationTable.setForeground(Color.WHITE);
        reservationTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        reservationTable.setGridColor(new Color(255, 255, 255, 15));
        reservationTable.setSelectionBackground(new Color(138, 43, 226, 80));
        reservationTable.setSelectionForeground(Color.WHITE);
        reservationTable.setRowHeight(45);
        reservationTable.setShowGrid(true);
        reservationTable.setIntercellSpacing(new Dimension(1, 1));
        
        reservationTable.getTableHeader().setOpaque(false);
        reservationTable.getTableHeader().setBackground(new Color(138, 43, 226, 100));
        reservationTable.getTableHeader().setForeground(Color.WHITE);
        reservationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        reservationTable.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(255, 255, 255, 3));
                    } else {
                        c.setBackground(new Color(255, 255, 255, 6));
                    }
                    c.setForeground(Color.WHITE);
                }
                
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                setOpaque(true);
                return c;
            }
        };
        
        for (int i = 0; i < reservationTable.getColumnCount(); i++) {
            reservationTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        
        int[] columnWidths = {120, 200, 200, 150, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            reservationTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

    private void setupActionListeners(JButton backButton, JButton refreshButton,
                                     JButton viewDetailsButton, JButton cancelReservationButton) {
        
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        
        refreshButton.addActionListener(e -> refreshData());
        viewDetailsButton.addActionListener(e -> viewReservationDetails());
        cancelReservationButton.addActionListener(e -> cancelReservation());
        typeFilter.addActionListener(e -> filterByType());
    }
    
    private void refreshData() {
        loadUserReservations();
        PageComponents.showStyledMessage("Success", "Your reservation data refreshed successfully!", this);
    }
    
    private void viewReservationDetails() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to view details!", this);
            return;
        }
        
        String reservationId = (String) tableModel.getValueAt(selectedRow, 0);
        Reservation reservation = reservationRepository.reservationMap.get(reservationId);
        
        if (reservation != null && reservation.getUser().getId().equals(currentUser.getId())) {
            String seatList = reservation.getSeats().stream()
                    .map(seat -> String.valueOf(seat.getSeatNo()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("N/A");
            
            double totalPrice = reservation.getTrip().getBasePrice() * reservation.getSeats().size();
            
            String details = String.format(
                "=== RESERVATION DETAILS ===\n\n" +
                "Reservation ID: %s\n" +
                "Customer: %s\n" +
                "Email: %s\n" +
                "Trip: %s\n" +
                "Route: %s → %s\n" +
                "Date & Time: %s\n" +
                "Seats: %s\n" +
                "Total Price: $%.2f\n\n" +
                "Vehicle Type: %s\n",
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getUser().getEmail(),
                reservation.getTrip().getTripNo(),
                reservation.getTrip().getStartPoint(),
                reservation.getTrip().getEndPoint(),
                reservation.getTrip().getDepartureTime(),
                seatList,
                totalPrice,
                reservation.getTrip().getTripType()
            );
            
            PageComponents.showStyledMessage("Reservation Details", details, this);
        }
    }
    
    private void cancelReservation() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to cancel!", this);
            return;
        }
        
        String reservationId = (String) tableModel.getValueAt(selectedRow, 0);
        Reservation reservation = reservationRepository.reservationMap.get(reservationId);
        
        if (reservation != null && reservation.getUser().getId().equals(currentUser.getId())) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this reservation?\nThis action cannot be undone.",
                "Cancel Reservation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                // Koltukları serbest bırak
                reservation.getSeats().forEach(seat -> seat.unreserve());
                
                // Rezervasyonu repository'den kaldır
                reservationRepository.reservationMap.remove(reservationId);
                
                PageComponents.showStyledMessage("Success", 
                    "Your reservation has been cancelled successfully!", this);
                refreshData();
            }
        } else {
            PageComponents.showStyledMessage("Error", "Invalid reservation or unauthorized access!", this);
        }
    }
    
    private void filterByType() {
        String selectedType = (String) typeFilter.getSelectedItem();
        tableModel.setRowCount(0);
        
        Collection<Reservation> userReservations = getUserReservations();
        int filteredCount = 0;
        
        for (Reservation reservation : userReservations) {
            String reservationType = getReservationType(reservation);
            
            if (selectedType.equals("All Types") || selectedType.equals(reservationType)) {
                String seatList = reservation.getSeats().stream()
                        .map(seat -> String.valueOf(seat.getSeatNo()))
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("N/A");
                
                tableModel.addRow(new Object[]{
                    reservation.getId(),
                    reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                    reservation.getTrip().getDepartureTime(),
                    seatList,
                    reservation.getTrip().getArrivalTime()
                });
                filteredCount++;
            }
        }
        
        if (filteredCount == 0) {
            tableModel.addRow(new Object[]{
                "No " + selectedType.toLowerCase() + " reservations found", "", "", "", ""
            });
        }
        
        if (!selectedType.equals("All Types")) {
            PageComponents.showStyledMessage("Filter Applied", 
                "Showing " + filteredCount + " " + selectedType + " reservations", this);
        }
    }
    
    private String getReservationType(Reservation reservation) {
        String tripType = reservation.getTrip().getTripType();
        
        if (tripType != null) {
            return tripType;
        }
        
        if (reservation.getTrip() instanceof models.BusTrip) {
            return "Bus";
        } else if (reservation.getTrip() instanceof models.FlightTrip) {
            return "Flight";
        }
        
        return "Unknown";
    }
    
    public void initializePage() {
        setupUI();
        loadUserReservations();
    }
    
    @Override
    public void display() {
        initializePage();
        super.display(); 
    }
}