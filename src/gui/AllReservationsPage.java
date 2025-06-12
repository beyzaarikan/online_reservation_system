package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class AllReservationsPage extends BasePanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private JComboBox<String> typeFilter;
    
    public AllReservationsPage() {
        super("My Reservations ", 1200, 800);
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
        statsPanel.setLayout(new GridLayout(1, 4, 20, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel totalCard = createStatCard("Total Reservations", "156", new Color(138, 43, 226));
        JPanel confirmedCard = createStatCard("Confirmed", "142", new Color(46, 204, 113));
        JPanel pendingCard = createStatCard("Pending", "8", new Color(241, 196, 15));
        JPanel cancelledCard = createStatCard("Cancelled", "6", new Color(231, 76, 60));
        
        statsPanel.add(totalCard);
        statsPanel.add(confirmedCard);
        statsPanel.add(pendingCard);
        statsPanel.add(cancelledCard);

        // Filter Panel with glassmorphism
        JPanel filterPanel = createGlassmorphismPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        // Search components
        searchField = createModernTextField("Search by customer name or ID...");
        searchField.setPreferredSize(new Dimension(250, 40));
        
        statusFilter = createModernComboBox(new String[]{"All Status", "Confirmed", "Pending", "Cancelled"});
        typeFilter = createModernComboBox(new String[]{"All Types", "Bus", "Flight"});
        
        JButton searchButton = createModernButton("Search", new Color(138, 43, 226), true);
        JButton refreshButton = createModernButton("Refresh", new Color(52, 152, 219), true);
        JButton exportButton = createModernButton("Export", new Color(46, 204, 113), true);
        
        // Filter labels
        JLabel searchLabel = createFilterLabel("Search:");
        JLabel statusLabel = createFilterLabel("Status:");
        JLabel typeLabel = createFilterLabel("Type:");
        
        filterPanel.add(searchLabel);
        filterPanel.add(searchField);
        filterPanel.add(statusLabel);
        filterPanel.add(statusFilter);
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
        populateSampleData();
        
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(1100, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        actionPanel.setOpaque(false);
        
        JButton viewDetailsButton = createModernButton("View Details", new Color(138, 43, 226), true);
        JButton updateStatusButton = createModernButton("Update Status", new Color(52, 152, 219), true);
        JButton cancelReservationButton = createModernButton("Cancel Reservation", new Color(231, 76, 60), true);
        JButton sendEmailButton = createModernButton("Send Email", new Color(46, 204, 113), true);
        
        actionPanel.add(viewDetailsButton);
        actionPanel.add(updateStatusButton);
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
                           viewDetailsButton, updateStatusButton, cancelReservationButton,
                           sendEmailButton);
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
            "ID", "Customer", "Email", "Type", "Route", 
            "Date", "Time", "Seat", "Price", "Status"
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
                    
                    // Status column coloring
                    if (column == 9 && value != null) {
                        String status = value.toString();
                        switch (status) {
                            case "Confirmed":
                                c.setForeground(new Color(46, 204, 113));
                                break;
                            case "Pending":
                                c.setForeground(new Color(241, 196, 15));
                                break;
                            case "Cancelled":
                                c.setForeground(new Color(231, 76, 60));
                                break;
                        }
                    }
                }
                
                setOpaque(false);
                return c;
            }
        };
        
        for (int i = 0; i < reservationTable.getColumnCount(); i++) {
            reservationTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
        
        // Set column widths
        int[] columnWidths = {80, 120, 150, 60, 120, 80, 60, 50, 70, 80};
        for (int i = 0; i < columnWidths.length; i++) {
            reservationTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }
    
    private void populateSampleData() {
        // Sample data
        tableModel.addRow(new Object[]{
            "RES001", "John Doe", "john@example.com", "Bus", "Istanbul → Ankara", 
            "15/06/2025", "09:00", "A12", "$50.00", "Confirmed"
        });
        tableModel.addRow(new Object[]{
            "RES002", "Jane Smith", "jane@example.com", "Flight", "Istanbul → London", 
            "18/06/2025", "14:30", "12F", "$450.00", "Confirmed"
        });
        tableModel.addRow(new Object[]{
            "RES003", "Mike Wilson", "mike@example.com", "Bus", "Ankara → Izmir", 
            "20/06/2025", "22:00", "B05", "$65.00", "Pending"
        });
        tableModel.addRow(new Object[]{
            "RES004", "Sarah Jones", "sarah@example.com", "Flight", "Izmir → Paris", 
            "22/06/2025", "16:45", "8A", "$380.00", "Confirmed"
        });
        tableModel.addRow(new Object[]{
            "RES005", "David Brown", "david@example.com", "Bus", "Istanbul → Izmir", 
            "25/06/2025", "08:30", "C18", "$45.00", "Cancelled"
        });
        tableModel.addRow(new Object[]{
            "RES006", "Emily Davis", "emily@example.com", "Flight", "Ankara → Berlin", 
            "28/06/2025", "11:20", "15C", "$520.00", "Pending"
        });
    }

    private void setupActionListeners(JButton backButton, JButton searchButton, JButton refreshButton,
                                    JButton exportButton, JButton viewDetailsButton, JButton updateStatusButton,
                                    JButton cancelReservationButton, JButton sendEmailButton) {
        
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        
        searchButton.addActionListener(e -> searchReservations());
        refreshButton.addActionListener(e -> refreshData());
        exportButton.addActionListener(e -> exportData());
        viewDetailsButton.addActionListener(e -> viewReservationDetails());
        updateStatusButton.addActionListener(e -> updateReservationStatus());
        cancelReservationButton.addActionListener(e -> cancelReservation());
        sendEmailButton.addActionListener(e -> sendEmailToCustomer());
        
        statusFilter.addActionListener(e -> filterByStatus());
        typeFilter.addActionListener(e -> filterByType());
    }
    
    // Keep all the existing action methods unchanged
    private void searchReservations() {
        String searchTerm = searchField.getText();
        if (searchTerm.equals("Search by customer name or ID...") || searchTerm.trim().isEmpty()) {
            PageComponents.showStyledMessage("Info", "Please enter a search term!", this);
            return;
        }
        
        PageComponents.showStyledMessage("Info", "Searching for: " + searchTerm + "\nFound 3 matching reservations.", this);
    }
    
    private void refreshData() {
        tableModel.setRowCount(0);
        populateSampleData();
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
        String customerName = (String) tableModel.getValueAt(selectedRow, 1);
        String email = (String) tableModel.getValueAt(selectedRow, 2);
        String type = (String) tableModel.getValueAt(selectedRow, 3);
        String route = (String) tableModel.getValueAt(selectedRow, 4);
        String date = (String) tableModel.getValueAt(selectedRow, 5);
        String time = (String) tableModel.getValueAt(selectedRow, 6);
        String seat = (String) tableModel.getValueAt(selectedRow, 7);
        String price = (String) tableModel.getValueAt(selectedRow, 8);
        String status = (String) tableModel.getValueAt(selectedRow, 9);
        
        String details = String.format(
            "=== RESERVATION DETAILS ===\n\n" +
            "Reservation ID: %s\n" +
            "Customer: %s\n" +
            "Email: %s\n" +
            "Type: %s\n" +
            "Route: %s\n" +
            "Date & Time: %s %s\n" +
            "Seat: %s\n" +
            "Price: %s\n" +
            "Status: %s\n\n" +
            "Booking Date: 01/06/2025\n" +
            "Payment Method: Credit Card\n" +
            "Special Requests: None",
            resId, customerName, email, type, route, date, time, seat, price, status
        );
        
        PageComponents.showStyledMessage("Reservation Details", details, this);
    }
    
    private void updateReservationStatus() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to update!", this);
            return;
        }
        
        String[] statusOptions = {"Confirmed", "Pending", "Cancelled"};
        String newStatus = (String) JOptionPane.showInputDialog(
            this,
            "Select new status:",
            "Update Reservation Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            statusOptions,
            statusOptions[0]
        );
        
        if (newStatus != null) {
            tableModel.setValueAt(newStatus, selectedRow, 9);
            String resId = (String) tableModel.getValueAt(selectedRow, 0);
            PageComponents.showStyledMessage("Success", 
                "Reservation " + resId + " status updated to: " + newStatus, this);
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
            tableModel.setValueAt("Cancelled", selectedRow, 9);
            PageComponents.showStyledMessage("Success", 
                "Reservation " + resId + " has been cancelled!\nCancellation email sent to customer.", this);
        }
    }
    
    private void sendEmailToCustomer() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to send email!", this);
            return;
        }
        
        String email = (String) tableModel.getValueAt(selectedRow, 2);
        String resId = (String) tableModel.getValueAt(selectedRow, 0);
        
        String[] emailTemplates = {
            "Confirmation Email", 
            "Reminder Email", 
            "Cancellation Notice", 
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
    
    private void filterByStatus() {
        String selectedStatus = (String) statusFilter.getSelectedItem();
        PageComponents.showStyledMessage("Info", "Filtering by status: " + selectedStatus, this);
    }
    
    private void filterByType() {
        String selectedType = (String) typeFilter.getSelectedItem();
        PageComponents.showStyledMessage("Info", "Filtering by type: " + selectedType, this);
    }
}