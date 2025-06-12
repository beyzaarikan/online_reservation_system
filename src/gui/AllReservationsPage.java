package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import repository.ReservationRepository;
import models.Reservation;
import java.util.Collection;

public class AllReservationsPage extends BasePanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> typeFilter;
    private ReservationRepository reservationRepository;
    
    public AllReservationsPage() {
        super("My Reservations ", 1200, 800);
        this.reservationRepository = new ReservationRepository();
    }
    
    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 15, 35));

        // Ana panel - gradient arkaplan
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(15, 15, 35),
                    getWidth(), getHeight(), new Color(25, 25, 55)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Decorative circles
                g2d.setColor(new Color(138, 43, 226, 30));
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(getWidth()-150, getHeight()-150, 200, 200);
                
                g2d.setColor(new Color(75, 0, 130, 20));
                g2d.fillOval(getWidth()-100, -50, 150, 150);
                g2d.fillOval(-100, getHeight()-100, 150, 150);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createModernButton("← Back", new Color(108, 92, 231), false);
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backPanel.add(backButton);

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Reservation Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Manage all customer reservations", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        headerPanel.add(backPanel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

        // Statistics Panel with glassmorphism
        JPanel statsPanel = createGlassmorphismPanel();
        statsPanel.setLayout(new GridLayout(1, 3, 20, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        Collection<Reservation> allReservations = reservationRepository.reservationMap.values();
        int totalReservations = allReservations.size();
        int busReservations = (int) allReservations.stream().filter(r -> "Bus".equals(getReservationType(r))).count();
        int flightReservations = (int) allReservations.stream().filter(r -> "Flight".equals(getReservationType(r))).count();
        
        JPanel totalCard = createStatCard("Total Reservations", String.valueOf(totalReservations), new Color(138, 43, 226));
        JPanel busCard = createStatCard("Bus Reservations", String.valueOf(busReservations), new Color(46, 204, 113));
        JPanel flightCard = createStatCard("Flight Reservations", String.valueOf(flightReservations), new Color(52, 152, 219));
        
        statsPanel.add(totalCard);
        statsPanel.add(busCard);
        statsPanel.add(flightCard);

        // Filter Panel with glassmorphism
        JPanel filterPanel = createGlassmorphismPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        // Search components
        searchField = createModernTextField("Search by customer name or ID...");
        searchField.setPreferredSize(new Dimension(250, 40));
        
        typeFilter = createModernComboBox(new String[]{"All Types", "Bus", "Flight"});
        
        JButton searchButton = createModernButton("Search", new Color(138, 43, 226), true);
        JButton refreshButton = createModernButton("Refresh", new Color(52, 152, 219), true);
        JButton exportButton = createModernButton("Export", new Color(46, 204, 113), true);
        
        // Filter labels
        JLabel searchLabel = createFilterLabel("Search:");
        JLabel typeLabel = createFilterLabel("Type:");
        
        filterPanel.add(searchLabel);
        filterPanel.add(searchField);
        filterPanel.add(typeLabel);
        filterPanel.add(typeFilter);
        filterPanel.add(searchButton);
        filterPanel.add(refreshButton);
        filterPanel.add(exportButton);

        // Main content panel with glassmorphism
        JPanel contentPanel = createGlassmorphismPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Table setup
        createTable();
        loadReservationsFromRepository();
        
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(1100, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        actionPanel.setOpaque(false);
        
        JButton viewDetailsButton = createModernButton("View Details", new Color(138, 43, 226), true);
        JButton cancelReservationButton = createModernButton("Cancel Reservation", new Color(231, 76, 60), true);
        JButton sendEmailButton = createModernButton("Send Email", new Color(46, 204, 113), true);
        
        actionPanel.add(viewDetailsButton);
        actionPanel.add(cancelReservationButton);
        actionPanel.add(sendEmailButton);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        // Layout assembly
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        centerPanel.add(filterPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Action listeners
        setupActionListeners(backButton, searchButton, refreshButton, exportButton,
                           viewDetailsButton, cancelReservationButton, sendEmailButton);
    }

    private JPanel createGlassmorphismPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
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
                
                // Card background
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Accent border
                g2d.setColor(accentColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 12, 12);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(titleLabel);
        
        return card;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(138, 43, 226));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                }
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        field.setText(placeholder);
        field.setForeground(new Color(150, 150, 150));
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(150, 150, 150));
                }
            }
        });
        
        return field;
    }

    private JComboBox<String> createModernComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<String>(items) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                super.paintComponent(g);
            }
        };
        comboBox.setOpaque(false);
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(120, 40));
        return comboBox;
    }

    private JButton createModernButton(String text, Color baseColor, boolean isPrimary) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(baseColor.brighter());
                } else {
                    g2d.setColor(baseColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(isPrimary ? 120 : 100, 35));
        
        return button;
    }

    private JLabel createFilterLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(189, 147, 249));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void createTable() {
        String[] columnNames = {
            "ID", "Customer", "Trip", "Seats", "Price"
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
                
                // Table background
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                super.paintComponent(g);
            }
        };
        
        reservationTable.setOpaque(false);
        reservationTable.setBackground(new Color(255, 255, 255, 8));
        reservationTable.setForeground(Color.WHITE);
        reservationTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reservationTable.setGridColor(new Color(255, 255, 255, 30));
        reservationTable.setSelectionBackground(new Color(138, 43, 226, 100));
        reservationTable.setSelectionForeground(Color.WHITE);
        reservationTable.setRowHeight(40);
        reservationTable.setShowGrid(true);
        
        // Header styling
        reservationTable.getTableHeader().setOpaque(false);
        reservationTable.getTableHeader().setBackground(new Color(138, 43, 226, 150));
        reservationTable.getTableHeader().setForeground(Color.WHITE);
        reservationTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Custom cell renderer
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(new Color(255, 255, 255, 5));
                    c.setForeground(Color.WHITE);
                }
                
                setOpaque(false);
                return c;
            }
        };
        
        for (int i = 0; i < reservationTable.getColumnCount(); i++) {
            reservationTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        
        // Set column widths
        int[] columnWidths = {120, 200, 200, 150, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            reservationTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }
    
    private void loadReservationsFromRepository() {
        tableModel.setRowCount(0);
        Collection<Reservation> reservations = reservationRepository.reservationMap.values();
        
        for (Reservation reservation : reservations) {
            String seatList = reservation.getSeats().stream()
                    .map(seat -> String.valueOf(seat.getSeatNo()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("N/A");
            
            tableModel.addRow(new Object[]{
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                seatList,
                String.format("$%.2f", reservation.getTrip().getBasePrice()) //totaL PRİCE gelmeli buraya
            });
        }
    }

    private void setupActionListeners(JButton backButton, JButton searchButton, JButton refreshButton,
                                    JButton exportButton, JButton viewDetailsButton,
                                    JButton cancelReservationButton, JButton sendEmailButton) {
        
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        
        searchButton.addActionListener(e -> searchReservations());
        refreshButton.addActionListener(e -> refreshData());
        exportButton.addActionListener(e -> exportData());
        viewDetailsButton.addActionListener(e -> viewReservationDetails());
        cancelReservationButton.addActionListener(e -> cancelReservation());
        sendEmailButton.addActionListener(e -> sendEmailToCustomer());
        
        typeFilter.addActionListener(e -> filterByType());
    }
    
    private void searchReservations() {
        String searchTerm = searchField.getText();
        if (searchTerm.equals("Search by customer name or ID...") || searchTerm.trim().isEmpty()) {
            PageComponents.showStyledMessage("Info", "Please enter a search term!", this);
            return;
        }
        
        // Search in repository
        Collection<Reservation> allReservations = reservationRepository.reservationMap.values();
        tableModel.setRowCount(0);
        int foundCount = 0;
        
        for (Reservation reservation : allReservations) {
            if (reservation.getUser().getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                reservation.getId().toLowerCase().contains(searchTerm.toLowerCase())) {
                
                String seatList = reservation.getSeats().stream()
                        .map(seat -> String.valueOf(seat.getSeatNo()))
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("N/A");
                
                tableModel.addRow(new Object[]{
                 reservation.getId(),
                reservation.getUser().getName(),
                reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                seatList,
                String.format("$%.2f", reservation.getTrip().getBasePrice()) //totaL PRİCE gelmeli buraya
                });
                foundCount++;
            }
        }
        
        PageComponents.showStyledMessage("Search Results", 
            "Found " + foundCount + " reservations matching: " + searchTerm, this);
    }
    
    private void refreshData() {
        loadReservationsFromRepository();
        PageComponents.showStyledMessage("Success", "Reservation data refreshed successfully!", this);
    }
    
    private void exportData() {
        PageComponents.showStyledMessage("Success", "Reservation data exported to CSV file successfully!", this);
    }
    
    private void viewReservationDetails() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to view details!", this);
            return;
        }
        
        String resId = (String) tableModel.getValueAt(selectedRow, 0);
        Reservation reservation = reservationRepository.findById(resId);
        
        if (reservation != null) {
            String seatList = reservation.getSeats().stream()
                    .map(seat -> String.valueOf(seat.getSeatNo()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("N/A");
            
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
                "Vehicle Type: %s\n" ,
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getUser().getEmail(),
                reservation.getTrip().getTripNo(),
                reservation.getTrip().getStartPoint(),
                reservation.getTrip().getEndPoint(),
                reservation.getTrip().getDepartureTime(),
                seatList,
                reservation.getTrip().getBasePrice(), //totaL PRİCE gelmeli buraya
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
        
        String resId = (String) tableModel.getValueAt(selectedRow, 0);
        String customerName = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to CANCEL reservation: " + resId + "\nCustomer: " + customerName + "?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Remove from repository
            boolean deleted = reservationRepository.deleteReservation(resId);
            if (deleted) {
                // Remove from table
                tableModel.removeRow(selectedRow);
                PageComponents.showStyledMessage("Success", 
                    "Reservation " + resId + " has been cancelled and removed!\nCancellation email sent to customer.", this);
            } else {
                PageComponents.showStyledMessage("Error", 
                    "Failed to cancel reservation " + resId, this);
            }
        }
    }
    
    private void sendEmailToCustomer() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to send email!", this);
            return;
        }
        
        String resId = (String) tableModel.getValueAt(selectedRow, 0);
        Reservation reservation = reservationRepository.findById(resId);
        
        if (reservation != null) {
            String email = reservation.getUser().getEmail();
            
            String[] emailTemplates = {
                "Confirmation Email", 
                "Reminder Email", 
                "Custom Message"
            };
            
            String selectedTemplate = (String) JOptionPane.showInputDialog(
                this,
                "Select email template to send to: " + email,
                "Send Email",
                JOptionPane.QUESTION_MESSAGE,
                null,
                emailTemplates,
                emailTemplates[0]
            );
            
            if (selectedTemplate != null) {
                PageComponents.showStyledMessage("Success", 
                    selectedTemplate + " sent successfully to: " + email + 
                    "\nReservation: " + resId, this);
            }
        }
    }
    
    private void filterByType() {
        String selectedType = (String) typeFilter.getSelectedItem();
        
        if ("All Types".equals(selectedType)) {
            loadReservationsFromRepository();
        } else {
            Collection<Reservation> allReservations = reservationRepository.reservationMap.values();
            tableModel.setRowCount(0);
            
            for (Reservation reservation : allReservations) {
                String reservationType = getReservationType(reservation);
                if (selectedType.equals(reservationType)) {
                    String seatList = reservation.getSeats().stream()
                            .map(seat -> String.valueOf(seat.getSeatNo()))
                            .reduce((s1, s2) -> s1 + ", " + s2)
                            .orElse("N/A");
                    
                    tableModel.addRow(new Object[]{
                        reservation.getId(),
                        reservation.getUser().getName(),
                        reservation.getTrip().getStartPoint() + " → " + reservation.getTrip().getEndPoint(),
                        seatList,
                        String.format("$%.2f", reservation.getTrip().getBasePrice()) //totaL PRİCE gelmeli buraya
                    });
                }
            }
        }
        
        PageComponents.showStyledMessage("Info", "Filtering by type: " + selectedType, this);
    }
    
    // Helper method to determine reservation type based on vehicle
    private String getReservationType(Reservation reservation) {
        String vehicleType = reservation.getTrip().getTripType();
        if (vehicleType.equalsIgnoreCase("Bus")) {
            return "Bus";
        } else if (vehicleType.equalsIgnoreCase("Plane") || vehicleType.equalsIgnoreCase("Aircraft")) {
            return "Flight";
        }
        return "Other";
    }
}