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
import service.UserService;
import repository.UserRepository;

public class AllReservationsPage extends BasePanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> typeFilter;
    private ReservationRepository reservationRepository;
    private boolean isAdminView;
    
    public AllReservationsPage() {
        super("My Reservations", 1200, 800);
        // Check if current user is admin
        User currentUser = SessionManager.getInstance().getLoggedInUser();
        UserService userService = new UserService(UserRepository.getInstance());
        this.isAdminView = (currentUser != null && userService.isAdmin(currentUser));
        
        // Use the global repository instance
        this.reservationRepository = GlobalRepositoryManager.getInstance().getReservationRepository();
        
        // Update title based on user type
        if (isAdminView) {
            setTitle("All Reservations - Admin Panel");
        }
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
        JPanel headerPanel = createHeaderPanel();
        
        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 40, 60));
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Menu Grid Panel
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

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createBackButton("← Back to Menu");
        backPanel.add(backButton);

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel(isAdminView ? "All Reservations" : "My Reservations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(isAdminView ? "Manage all system reservations" : "Manage your account settings and preferences", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);

        headerPanel.add(backPanel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Action listeners
        backButton.addActionListener(e -> {
            dispose();
            if (isAdminView) {
                new MainMenuPage(true).display();
            } else {
                new MainMenuPage().display();
            }
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
        
        // Glassmorphism container for menu cards
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border
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
        
        Collection<Reservation> allReservations = reservationRepository.reservationMap.values();
        
        // Filter reservations based on user type
        Collection<Reservation> displayReservations;
        if (isAdminView) {
            displayReservations = allReservations; // Admin sees all reservations
        } else {
            User currentUser = SessionManager.getInstance().getLoggedInUser();
            displayReservations = allReservations.stream()
                .filter(r -> r.getUser().getId().equals(currentUser.getId()))
                .collect(java.util.stream.Collectors.toList());
        }
        
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

        // Table section
        createTable();
        loadReservationsFromRepository();
        
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
        if (isAdminView) {
            columnNames = new String[]{"Customer", "Trip Type", "Route", "Departure Date", "Departure Time", "Arrival Time", "Seats"};
        } else {
            columnNames = new String[]{"Trip Type", "Route", "Departure Date", "Departure Time", "Arrival Time", "Seats"};
        }
        
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
        
        // Set column widths based on admin view
        if (isAdminView) {
            int[] columnWidths = {150, 100, 200, 120, 100, 100, 150};
            for (int i = 0; i < columnWidths.length; i++) {
                reservationTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            }
        } else {
            int[] columnWidths = {100, 200, 120, 100, 100, 150};
            for (int i = 0; i < columnWidths.length; i++) {
                reservationTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            }
        }
    }
    
    private void loadReservationsFromRepository() {
        tableModel.setRowCount(0);
        Collection<Reservation> reservations = reservationRepository.reservationMap.values();
        
        // Filter reservations based on user type
        Collection<Reservation> displayReservations;
        if (isAdminView) {
            displayReservations = reservations; // Admin sees all reservations
        } else {
            User currentUser = SessionManager.getInstance().getLoggedInUser();
            displayReservations = reservations.stream()
                .filter(r -> r.getUser().getId().equals(currentUser.getId()))
                .collect(java.util.stream.Collectors.toList());
        }
        
        for (Reservation reservation : displayReservations) {
            String seatList = reservation.getSeats().stream()
                    .map(seat -> String.valueOf(seat.getSeatNo()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("N/A");
            
            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
            
            String departureDate = reservation.getTrip().getDepartureTime().format(dateFormatter);
            String departureTime = reservation.getTrip().getDepartureTime().format(timeFormatter);
            String arrivalTime = reservation.getTrip().getArrivalTime().format(timeFormatter);
            
            if (isAdminView) {
                tableModel.addRow(new Object[]{
                    reservation.getUser().getName(),
                    reservation.getTrip().getTripType(),
                    reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                    departureDate,
                    departureTime,
                    arrivalTime,
                    seatList
                });
            } else {
                tableModel.addRow(new Object[]{
                    reservation.getTrip().getTripType(),
                    reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                    departureDate,
                    departureTime,
                    arrivalTime,
                    seatList
                });
            }
        }
    }

    private void setupActionListeners(JButton refreshButton, JButton viewDetailsButton, JButton cancelReservationButton) {
        refreshButton.addActionListener(e -> refreshData());
        viewDetailsButton.addActionListener(e -> viewReservationDetails());
        cancelReservationButton.addActionListener(e -> cancelReservation());
        typeFilter.addActionListener(e -> filterByType());
    }
    
    private void refreshData() {
        loadReservationsFromRepository();
        PageComponents.showStyledMessage("Success", "Reservation data refreshed successfully!", this);
    }
    
    private void viewReservationDetails() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to view details!", this);
            return;
        }
        
        String customerName;
        if (isAdminView) {
            customerName = (String) tableModel.getValueAt(selectedRow, 0);
        } else {
            User currentUser = SessionManager.getInstance().getLoggedInUser();
            customerName = currentUser.getName();
        }
        
        // Find reservation by customer name
        Reservation reservation = null;
        for (Reservation r : reservationRepository.reservationMap.values()) {
            if (r.getUser().getName().equals(customerName)) {
                // If admin view, get the first matching reservation
                // If user view, ensure it's the user's reservation
                if (isAdminView || r.getUser().getId().equals(SessionManager.getInstance().getLoggedInUser().getId())) {
                    reservation = r;
                    break;
                }
            }
        }
        
        if (reservation != null) {
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
                "Vehicle Type: %s",
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
        
        String customerName;
        if (isAdminView) {
            customerName = (String) tableModel.getValueAt(selectedRow, 0);
        } else {
            User currentUser = SessionManager.getInstance().getLoggedInUser();
            customerName = currentUser.getName();
        }
        
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel this reservation?\nThis action cannot be undone.",
            "Cancel Reservation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            // Find and remove the reservation
            Reservation toRemove = null;
            for (Reservation r : reservationRepository.reservationMap.values()) {
                if (r.getUser().getName().equals(customerName)) {
                    if (isAdminView || r.getUser().getId().equals(SessionManager.getInstance().getLoggedInUser().getId())) {
                        toRemove = r;
                        break;
                    }
                }
            }
            
            if (toRemove != null) {
                reservationRepository.deleteById(toRemove.getId());
                PageComponents.showStyledMessage("Success",
                    "Reservation for " + customerName + " has been cancelled successfully!", this);
                refreshData(); // Refresh the table after cancellation
            }
        }
    }
    
    private void filterByType() {
        String selectedType = (String) typeFilter.getSelectedItem();
        tableModel.setRowCount(0);
        
        Collection<Reservation> allReservations = reservationRepository.reservationMap.values();
        
        // Filter reservations based on user type
        Collection<Reservation> displayReservations;
        if (isAdminView) {
            displayReservations = allReservations;
        } else {
            User currentUser = SessionManager.getInstance().getLoggedInUser();
            displayReservations = allReservations.stream()
                .filter(r -> r.getUser().getId().equals(currentUser.getId()))
                .collect(java.util.stream.Collectors.toList());
        }
        
        int filteredCount = 0;
        
        for (Reservation reservation : displayReservations) {
            String reservationType = getReservationType(reservation);
            
            if (selectedType.equals("All Types") || selectedType.equals(reservationType)) {
                String seatList = reservation.getSeats().stream()
                        .map(seat -> String.valueOf(seat.getSeatNo()))
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("N/A");
                
                java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
                
                String departureDate = reservation.getTrip().getDepartureTime().format(dateFormatter);
                String departureTime = reservation.getTrip().getDepartureTime().format(timeFormatter);
                String arrivalTime = reservation.getTrip().getArrivalTime().format(timeFormatter);
                
                if (isAdminView) {
                    tableModel.addRow(new Object[]{
                        reservation.getUser().getName(),
                        reservation.getTrip().getTripType(),
                        reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                        departureDate,
                        departureTime,
                        arrivalTime,
                        seatList
                    });
                } else {
                    tableModel.addRow(new Object[]{
                        reservation.getTrip().getTripType(),
                        reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                        departureDate,
                        departureTime,
                        arrivalTime,
                        seatList
                    });
                }
                filteredCount++;
            }
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
        
        // Fallback: check the class type
        if (reservation.getTrip() instanceof models.BusTrip) {
            return "Bus";
        } else if (reservation.getTrip() instanceof models.FlightTrip) {
            return "Flight";
        }
        
        return "Unknown";
    }
        
    // Method to handle table row selection events
    private void handleTableSelection() {
        reservationTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = reservationTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Optional: Show preview of selected reservation
                }
            }
        });
    }
    
    // Initialize the page
    public void initializePage() {
        setupUI();
        handleTableSelection();
        loadReservationsFromRepository();
    }
    
    // Override display method to ensure proper initialization
    @Override
    public void display() {
        initializePage();
        super.display(); 
    }
}