package gui;

import java.awt.*;
import java.util.Collection;
import javax.swing.*;
import models.Reservation;
import models.User;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import repository.ReservationRepository;
import singleton.SessionManager;
import repository.UserRepository;
import repository.TripRepository; // Import TripRepository for ReservationService initialization
import service.ReservationService; // Import ReservationService
import strategy.BusPricingStrategy; // Import for pricing calculation
import strategy.FlightPricingStrategy; // Import for pricing calculation
import strategy.PricingContext; // Import for pricing calculation
import models.BusTrip; // Import BusTrip for instanceof checks
import models.FlightTrip; // Import FlightTrip for instanceof checks

public class AllReservationsPage extends BasePanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> typeFilter;
    private ReservationRepository reservationRepository;
    private ReservationService reservationService; // Add ReservationService field
    // private boolean isAdminView; // isAdminView kaldırıldı

    // GIZLI SUTUN INDEKSI (sabit olarak tanimliyoruz)
    private static final int RESERVATION_ID_COLUMN_INDEX = 6;   // Trip Type, Route, Dep Date, Dep Time, Arr Time, Seats, **Reservation ID**

    public AllReservationsPage() {
        super("My Reservations", 1200, 800);
        setTitle("My Reservations");
        
        this.reservationRepository = GlobalRepositoryManager.getInstance().getReservationRepository(); //
        // Initialize ReservationService
        this.reservationService = new ReservationService(
                                    this.reservationRepository,
                                    UserRepository.getInstance(),
                                    TripRepository.getInstance() 
                                );
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

        JPanel headerPanel = createHeaderPanel();
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 40, 60));
        
        JPanel titlePanel = createTitlePanel();
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel menuPanel = createMenuPanel();
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(menuPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createBackButton("← Back to Menu");
        backPanel.add(backButton);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("My Reservations", SwingConstants.CENTER); // Admin kontrolu kaldirildi
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Manage your account settings and preferences", SwingConstants.CENTER); // Admin kontrolu kaldirildi
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        backButton.addActionListener(e -> {
            dispose();

            new MainMenuPage().display(); 
        });
        
        return headerPanel;
    }
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        return titlePanel;
    }
    
    private JPanel createMenuPanel() {
        JPanel menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setOpaque(false);
        
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setOpaque(false);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        menuPanel.setPreferredSize(new Dimension(1000, 600));

        // Statistics row
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 20, 0));
        statsRow.setOpaque(false);
        statsRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        Collection<Reservation> allReservations = reservationRepository.reservationMap.values(); //
        
        // Filter reservations based on user type (Admin görmediği için sadece kendi rezervasyonları)
        User currentUser = SessionManager.getInstance().getLoggedInUser(); //
        Collection<Reservation> displayReservations = allReservations.stream()
            .filter(r -> r.getUser().getId().equals(currentUser.getId())) //
            .collect(java.util.stream.Collectors.toList());
        
        int totalReservations = displayReservations.size();
        int busReservations = (int) displayReservations.stream().filter(r -> "Bus".equals(getReservationType(r))).count();
        int flightReservations = (int) displayReservations.stream().filter(r -> "Flight".equals(getReservationType(r))).count();
        
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

        createTable();
        loadReservationsFromRepository();
        
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(1100, 350));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        actionPanel.setOpaque(false);
        
        JButton viewDetailsButton = createActionButton("View Details", new Color(138, 43, 226));
        JButton cancelReservationButton = createActionButton("Cancel Reservation", new Color(231, 76, 60));
        
        actionPanel.add(viewDetailsButton);
        actionPanel.add(Box.createHorizontalStrut(15)); // Butonlar arası boşluk
        actionPanel.add(cancelReservationButton);
        
        menuPanel.add(statsRow, BorderLayout.NORTH);
        
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setOpaque(false);
        middlePanel.add(filterSection, BorderLayout.NORTH);
        middlePanel.add(scrollPane, BorderLayout.CENTER);
        
        menuPanel.add(middlePanel, BorderLayout.CENTER);
        menuPanel.add(actionPanel, BorderLayout.SOUTH);
        
        menuContainer.add(menuPanel);
        
        // Setup action listeners
        setupActionListeners(refreshButton, viewDetailsButton, cancelReservationButton);
        
        return menuContainer;
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

    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Kart arka planı - daha belirgin
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Accent border
                g2d.setColor(accentColor);
                g2d.fillRoundRect(0, 0, 4, getHeight(), 2, 2);
                
                // Kenar çizgisi
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
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Border
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
        String[] columnNames;
        columnNames = new String[]{"Trip Type", "Route", "Departure Date", "Departure Time", "Arrival Time", "Seats", "Reservation ID"}; //
        
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
                
                // Tablo arka planı
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
        
        // Header styling
        reservationTable.getTableHeader().setOpaque(false);
        reservationTable.getTableHeader().setBackground(new Color(138, 43, 226, 100));
        reservationTable.getTableHeader().setForeground(Color.WHITE);
        reservationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        reservationTable.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        // Custom cell renderer
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
        
        int[] columnWidths = {90, 180, 110, 80, 120, 100, 0}; // Son sütun (Reservation ID) genişliği 0 yapıldı (gizli)
        for (int i = 0; i < columnWidths.length; i++) {
            reservationTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            if (i == RESERVATION_ID_COLUMN_INDEX) { // Eğer gizli sütun ise
                reservationTable.getColumnModel().getColumn(i).setMinWidth(0);
                reservationTable.getColumnModel().getColumn(i).setMaxWidth(0);
                reservationTable.getColumnModel().getColumn(i).setWidth(0);
            }
        }
    }
    
    private void loadReservationsFromRepository() {
        tableModel.setRowCount(0);
        Collection<Reservation> allReservations = reservationRepository.reservationMap.values(); //
        
        // Filter reservations based on current logged-in user
        User currentUser = SessionManager.getInstance().getLoggedInUser(); //
        Collection<Reservation> displayReservations = allReservations.stream()
            .filter(r -> r.getUser().getId().equals(currentUser.getId())) //
            .collect(java.util.stream.Collectors.toList());
        
        for (Reservation reservation : displayReservations) {
            String seatList = reservation.getSeats().stream()
                    .map(seat -> seat.getSeatLabel())
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("N/A");
            
            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
            
            String departureDate = reservation.getTrip().getDepartureTime().format(dateFormatter);
            String departureTime = reservation.getTrip().getDepartureTime().format(timeFormatter);
            String arrivalTime = reservation.getTrip().getArrivalTime().format(timeFormatter);
            
            tableModel.addRow(new Object[]{
                reservation.getTrip().getTripType(),
                reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                departureDate,
                departureTime,
                arrivalTime,
                seatList,
                reservation.getId()
            });
        }
    }

    private void setupActionListeners(JButton refreshButton, JButton viewDetailsButton, JButton cancelReservationButton) {
        refreshButton.addActionListener(e -> refreshData());
        viewDetailsButton.addActionListener(e -> viewReservationDetails());
        cancelReservationButton.addActionListener(e -> cancelReservation()); // Doğru çağrı
        typeFilter.addActionListener(e -> filterByType());
    }
    
    private void refreshData() {
        loadReservationsFromRepository();
        PageComponents.showStyledMessage("Success", "Reservation data refreshed successfully!", this); //
    }
    
    private void viewReservationDetails() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to view details!", this); //
            return;
        }
        
        String reservationId = (String) tableModel.getValueAt(selectedRow, RESERVATION_ID_COLUMN_INDEX); //
        
        Reservation reservation = reservationRepository.findById(reservationId); //

        if (reservation != null) {
            String seatList = reservation.getSeats().stream()
                    .map(seat -> seat.getSeatLabel())
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("N/A");
            
            double totalPrice = 0;
            if (reservation.getTrip() instanceof BusTrip) { 
                PricingContext busPricingContext = new PricingContext(new BusPricingStrategy()); 
                for (models.Seat seat : reservation.getSeats()) { 
                    totalPrice += busPricingContext.calculatePrice(reservation.getTrip(), seat.getSeatNo()); 
                    totalPrice = totalPrice / 100;
                }
            } else if (reservation.getTrip() instanceof FlightTrip) { 
                PricingContext flightPricingContext = new PricingContext(new FlightPricingStrategy());
                for (models.Seat seat : reservation.getSeats()) { 
                    totalPrice += flightPricingContext.calculatePrice(reservation.getTrip(), seat.getSeatNo()); 
                }
            } else {
                totalPrice = reservation.getTrip().getBasePrice() * reservation.getSeats().size(); 
                totalPrice = totalPrice / 100;
            }

            String details = String.format(
                "=== RESERVATION DETAILS ===\n\n" +
                "Reservation ID: %s\n" +
                "Customer: %s\n" +
                "Email: %s\n" +
                "Trip: %s\n" +
                "Route: %s → %s\n" +
                "Date & Time: %s\n" +
                "Seats: %s\n" +
                "Total Price: TL %.2f\n\n" +
                "Vehicle Type: %s",
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getUser().getEmail(),
                reservation.getTrip().getTripNo(),
                reservation.getTrip().getStartPoint(),
                reservation.getTrip().getEndPoint(),
                reservation.getTrip().getDepartureTime().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                seatList,
                totalPrice,
                reservation.getTrip().getTripType()
            );
            
            PageComponents.showStyledMessage("Reservation Details", details, this); //
        } else {
            PageComponents.showStyledMessage("Error", "Selected reservation not found in system!", this); //
        }
    }
    
    private void cancelReservation() { // Bu metod artık doğru şekilde çağrılıyor
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to cancel!", this); //
            return;
        }
        
        String reservationId = (String) tableModel.getValueAt(selectedRow, RESERVATION_ID_COLUMN_INDEX); //
        
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel this reservation?\nThis action cannot be undone.",
            "Confirm Reservation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                reservationService.cancelReservation(reservationId); //
                PageComponents.showStyledMessage("Success",
                    "Reservation has been cancelled successfully!", this); //
                refreshData(); 
            } catch (IllegalArgumentException ex) {
                PageComponents.showStyledMessage("Error", ex.getMessage(), this); //
            } catch (Exception ex) {
                PageComponents.showStyledMessage("Error", "An unexpected error occurred: " + ex.getMessage(), this); //
                ex.printStackTrace();
            }
        }
    }
    
    private void filterByType() {
        String selectedType = (String) typeFilter.getSelectedItem();
        tableModel.setRowCount(0);
        
        Collection<Reservation> allReservations = reservationRepository.reservationMap.values(); //
        
        User currentUser = SessionManager.getInstance().getLoggedInUser(); //
        Collection<Reservation> displayReservations = allReservations.stream()
            .filter(r -> r.getUser().getId().equals(currentUser.getId())) //
            .collect(java.util.stream.Collectors.toList());
        
        int filteredCount = 0;
        
        for (Reservation reservation : displayReservations) {
            String reservationType = getReservationType(reservation);
            
            if (selectedType.equals("All Types") || selectedType.equals(reservationType)) {
                String seatList = reservation.getSeats().stream()
                        .map(seat -> seat.getSeatLabel())
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("N/A");
                
                java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
                
                String departureDate = reservation.getTrip().getDepartureTime().format(dateFormatter);
                String departureTime = reservation.getTrip().getDepartureTime().format(timeFormatter);
                String arrivalTime = reservation.getTrip().getArrivalTime().format(timeFormatter);
                
                // Admin görünümü kaldırıldığı için tek bir addRow mantığı yeterli
                tableModel.addRow(new Object[]{
                    reservation.getTrip().getTripType(),
                    reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                    departureDate,
                    departureTime,
                    arrivalTime,
                    seatList,
                    reservation.getId()
                });
                filteredCount++;
            }
        }
        
        if (!selectedType.equals("All Types")) {
            PageComponents.showStyledMessage("Filter Applied", 
                "Showing " + filteredCount + " " + selectedType + " reservations", this); //
        }
    }
    
    private String getReservationType(Reservation reservation) {
        String tripType = reservation.getTrip().getTripType(); //
        
        if (tripType != null) {
            return tripType;
        }
        
        // Fallback: check the class type
        if (reservation.getTrip() instanceof BusTrip) { //
            return "Bus";
        } else if (reservation.getTrip() instanceof FlightTrip) { //
            return "Flight";
        }
        
        return "Unknown";
    }
        
    private void handleTableSelection() {
        reservationTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = reservationTable.getSelectedRow();
                if (selectedRow != -1) {
                }
            }
        });
    }
    
    public void initializePage() {
        setupUI();
        handleTableSelection();
        loadReservationsFromRepository();
    }
    
    @Override
    public void display() {
        initializePage();
        super.display(); 
    }
}