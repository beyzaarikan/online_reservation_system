package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TripManagementPage extends BasePanel {
    private JTable tripTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> tripTypeCombo;
    private JComboBox<String> statusCombo;
    
    public TripManagementPage() {
        super("Trip Management - Admin Panel", 1000, 700);
    }
    
    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 15, 35));

        // Main panel with gradient background (same as AdminPage)
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

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createModernButton("← Back", new Color(108, 92, 231), false);
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backPanel.add(backButton);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("Trip Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Manage All Travel Services", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);

        // Main content panel with glassmorphism effect
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        contentPanel.setMaximumSize(new Dimension(950, 600));

        // Filter Panel with glassmorphism
        JPanel filterPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Filter title
        JLabel filterTitle = new JLabel("Filters");
        filterTitle.setForeground(new Color(189, 147, 249));
        filterTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        searchField = createModernTextField("Search trips...");
        tripTypeCombo = createModernComboBox(new String[]{"All Types", "Bus", "Flight"});
        statusCombo = createModernComboBox(new String[]{"All Status", "Active", "Inactive", "Cancelled"});

        JButton searchButton = createModernButton("Search", new Color(138, 43, 226), true);
        JButton resetButton = createModernButton("Reset", new Color(108, 92, 231), false);

        JPanel filterContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        filterContent.setOpaque(false);
        
        filterContent.add(createFilterLabel("Search:"));
        filterContent.add(searchField);
        filterContent.add(createFilterLabel("Type:"));
        filterContent.add(tripTypeCombo);
        filterContent.add(createFilterLabel("Status:"));
        filterContent.add(statusCombo);
        filterContent.add(searchButton);
        filterContent.add(resetButton);

        JPanel filterMainPanel = new JPanel();
        filterMainPanel.setLayout(new BoxLayout(filterMainPanel, BoxLayout.Y_AXIS));
        filterMainPanel.setOpaque(false);
        filterTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterMainPanel.add(filterTitle);
        filterMainPanel.add(Box.createVerticalStrut(10));
        filterMainPanel.add(filterContent);
        
        filterPanel.add(filterMainPanel);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Create table with enhanced columns
        String[] columnNames = {"ID", "Type", "Route", "Date", "Time", "Price", "Available Seats", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tripTable = new JTable(tableModel);
        setupTable();
        
        JScrollPane scrollPane = new JScrollPane(tripTable);
        scrollPane.setPreferredSize(new Dimension(850, 250));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Style the scrollpane
        scrollPane.getVerticalScrollBar().setBackground(new Color(255, 255, 255, 20));
        scrollPane.getHorizontalScrollBar().setBackground(new Color(255, 255, 255, 20));

        // Action Buttons Panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton addTripButton = createModernButton("Add New Trip", new Color(138, 43, 226), true);
        JButton editTripButton = createModernButton("Edit Trip", new Color(108, 92, 231), true);
        JButton viewDetailsButton = createModernButton("View Details", new Color(189, 147, 249), false);
        JButton cancelTripButton = createModernButton("Cancel Trip", new Color(255, 121, 121), true);
        JButton refreshButton = createModernButton("Refresh", new Color(138, 43, 226), true);

        actionPanel.add(addTripButton);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(editTripButton);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(viewDetailsButton);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(cancelTripButton);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(refreshButton);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(actionPanel, BorderLayout.SOUTH);

        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        
        centerPanel.add(titlePanel);
        centerPanel.add(contentPanel);

        mainPanel.add(backPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Populate sample data
        populateSampleTripData();

        // Action listeners
        backButton.addActionListener(e -> {
            dispose();
            new AdminPage().display();
        });
        
        searchButton.addActionListener(e -> searchTrips());
        resetButton.addActionListener(e -> resetFilters());
        addTripButton.addActionListener(e -> addNewTrip());
        editTripButton.addActionListener(e -> editTrip());
        viewDetailsButton.addActionListener(e -> viewTripDetails());
        cancelTripButton.addActionListener(e -> cancelTrip());
        refreshButton.addActionListener(e -> refreshTripData());
    }

    private JLabel createFilterLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(189, 147, 249));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
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
        field.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        field.setPreferredSize(new Dimension(200, 35));
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
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        comboBox.setPreferredSize(new Dimension(120, 35));
        comboBox.setFocusable(false);
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
        button.setPreferredSize(new Dimension(120, 35));
        
        return button;
    }

    private void setupTable() {
        tripTable.setBackground(new Color(255, 255, 255, 10));
        tripTable.setForeground(Color.WHITE);
        tripTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tripTable.setGridColor(new Color(255, 255, 255, 30));
        tripTable.setSelectionBackground(new Color(138, 43, 226, 100));
        tripTable.setSelectionForeground(Color.WHITE);
        tripTable.setRowHeight(30);
        tripTable.setShowGrid(true);
        tripTable.setIntercellSpacing(new Dimension(1, 1));
        
        // Set column widths
        tripTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tripTable.getColumnModel().getColumn(1).setPreferredWidth(70);  // Type
        tripTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Route
        tripTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Date
        tripTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Time
        tripTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Price
        tripTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Available Seats
        tripTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Status
        
        // Custom header renderer
        tripTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(138, 43, 226, 150));
                c.setForeground(Color.WHITE);
                c.setFont(new Font("Segoe UI", Font.BOLD, 12));
                setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                return c;
            }
        });
        
        // Custom cell renderer
        tripTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(new Color(138, 43, 226, 100));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(255, 255, 255, 5));
                    c.setForeground(Color.WHITE);
                }
                
                c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                return c;
            }
        });
    }
    
    private void populateSampleTripData() {
        // Sample Bus trips
        tableModel.addRow(new Object[]{"T001", "Bus", "Istanbul → Ankara", "15/06/2025", "09:00", "$45.00", "12/40", "Active"});
        tableModel.addRow(new Object[]{"T002", "Bus", "Ankara → Izmir", "16/06/2025", "14:30", "$55.00", "8/40", "Active"});
        tableModel.addRow(new Object[]{"T003", "Bus", "Izmir → Istanbul", "17/06/2025", "22:00", "$50.00", "25/40", "Active"});
        
        // Sample Flight trips
        tableModel.addRow(new Object[]{"F001", "Flight", "Istanbul → London", "18/06/2025", "15:45", "$350.00", "45/180", "Active"});
        tableModel.addRow(new Object[]{"F002", "Flight", "Ankara → Paris", "19/06/2025", "08:30", "$420.00", "78/180", "Active"});
        tableModel.addRow(new Object[]{"F003", "Flight", "Izmir → Berlin", "20/06/2025", "11:15", "$380.00", "0/180", "Cancelled"});
        
        // More sample data
        tableModel.addRow(new Object[]{"T004", "Bus", "Bursa → Antalya", "21/06/2025", "20:00", "$65.00", "30/45", "Active"});
        tableModel.addRow(new Object[]{"F004", "Flight", "Istanbul → Dubai", "22/06/2025", "02:30", "$580.00", "120/200", "Active"});
        tableModel.addRow(new Object[]{"T005", "Bus", "Trabzon → Istanbul", "23/06/2025", "19:30", "$75.00", "15/40", "Inactive"});
    }
    
    private void searchTrips() {
        String searchTerm = searchField.getText();
        String selectedType = (String) tripTypeCombo.getSelectedItem();
        String selectedStatus = (String) statusCombo.getSelectedItem();
        
        if (searchTerm.equals("Search trips...")) {
            searchTerm = "";
        }
        
        // This would normally filter the actual data
        String filterInfo = "Searching with filters:\n" +
                           "Search Term: " + (searchTerm.isEmpty() ? "None" : searchTerm) + "\n" +
                           "Type: " + selectedType + "\n" +
                           "Status: " + selectedStatus;
        
        showStyledMessage("Search Applied", filterInfo);
        
        // Here you would implement actual filtering logic
        // For now, we'll just show the message
    }
    
    private void resetFilters() {
        searchField.setText("Search trips...");
        searchField.setForeground(new Color(150, 150, 150));
        tripTypeCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        
        // Refresh table with all data
        tableModel.setRowCount(0);
        populateSampleTripData();
        
        showStyledMessage("Success", "Filters reset successfully!");
    }
    
    private void addNewTrip() {
        // This would open a trip creation dialog
        showStyledMessage("Info", "Trip creation dialog would open here.\nIntegration with your Trip models needed.");
    }
    
    private void editTrip() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Error", "Please select a trip to edit!");
            return;
        }
        
        String tripId = (String) tableModel.getValueAt(selectedRow, 0);
        String tripType = (String) tableModel.getValueAt(selectedRow, 1);
        
        showStyledMessage("Info", 
            "Edit dialog would open for:\nTrip ID: " + tripId + "\nType: " + tripType + 
            "\n\nIntegration with your Trip models needed.");
    }
    
    private void viewTripDetails() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Error", "Please select a trip to view details!");
            return;
        }
        
        String tripId = (String) tableModel.getValueAt(selectedRow, 0);
        String tripType = (String) tableModel.getValueAt(selectedRow, 1);
        String route = (String) tableModel.getValueAt(selectedRow, 2);
        String date = (String) tableModel.getValueAt(selectedRow, 3);
        String time = (String) tableModel.getValueAt(selectedRow, 4);
        String price = (String) tableModel.getValueAt(selectedRow, 5);
        String seats = (String) tableModel.getValueAt(selectedRow, 6);
        String status = (String) tableModel.getValueAt(selectedRow, 7);
        
        String details = "Trip Details:\n\n" +
                        "ID: " + tripId + "\n" +
                        "Type: " + tripType + "\n" +
                        "Route: " + route + "\n" +
                        "Date: " + date + "\n" +
                        "Time: " + time + "\n" +
                        "Price: " + price + "\n" +
                        "Available Seats: " + seats + "\n" +
                        "Status: " + status;
        
        showStyledMessage("Trip Details", details);
    }
    
    private void cancelTrip() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Error", "Please select a trip to cancel!");
            return;
        }
        
        String tripId = (String) tableModel.getValueAt(selectedRow, 0);
        String route = (String) tableModel.getValueAt(selectedRow, 2);
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to CANCEL this trip?\n\n" +
            "Trip ID: " + tripId + "\n" +
            "Route: " + route + "\n\n" +
            "This will affect all existing reservations!",
            "Confirm Trip Cancellation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Cancelled", selectedRow, 7);
            tableModel.setValueAt("0/" + tableModel.getValueAt(selectedRow, 6).toString().split("/")[1], selectedRow, 6);
            showStyledMessage("Success", 
                "Trip " + tripId + " has been cancelled!\n" +
                "All affected passengers will be notified.");
        }
    }
    
    private void refreshTripData() {
        tableModel.setRowCount(0);
        populateSampleTripData();
        
        // Reset filters
        searchField.setText("Search trips...");
        searchField.setForeground(new Color(150, 150, 150));
        tripTypeCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        
        showStyledMessage("Success", "Trip data refreshed successfully!");
    }

    private void showStyledMessage(String title, String message) {
        // Create a styled message dialog that matches the theme
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(15, 15, 35));
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(25, 25, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message.replace("\n", "<br>") + "</div></html>");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton okButton = createModernButton("OK", new Color(138, 43, 226), true);
        okButton.addActionListener(e -> dialog.dispose());
        
        panel.setLayout(new BorderLayout());
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}