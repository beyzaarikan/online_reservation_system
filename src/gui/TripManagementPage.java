package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TripManagementPage extends BasePanel {
    private JTable tripTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> tripTypeCombo;
    private JComboBox<String> statusCombo;
    
    public TripManagementPage() {
        super("Trip Management - Admin Panel", 900, 700);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("Trip Management");
        
        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1),
                "Filters",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                PageComponents.TEXT_COLOR
            ),
            new javax.swing.border.EmptyBorder(10, 15, 10, 15)
        ));
        
        searchField = PageComponents.createStyledTextField("Search trips...");
        searchField.setPreferredSize(new Dimension(200, 35));
        
        tripTypeCombo = new JComboBox<>(new String[]{"All Types", "Bus", "Flight"});
        styleComboBox(tripTypeCombo);
        
        statusCombo = new JComboBox<>(new String[]{"All Status", "Active", "Inactive", "Cancelled"});
        styleComboBox(statusCombo);
        
        JButton searchButton = PageComponents.createStyledButton("Search", PageComponents.PRIMARY_COLOR, true);
        JButton resetButton = PageComponents.createStyledButton("Reset", PageComponents.SECONDARY_COLOR, false);
        
        filterPanel.add(new JLabel("Search: ") {{ 
            setForeground(PageComponents.TEXT_COLOR); 
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }});
        filterPanel.add(searchField);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(new JLabel("Type: ") {{ 
            setForeground(PageComponents.TEXT_COLOR); 
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }});
        filterPanel.add(tripTypeCombo);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(new JLabel("Status: ") {{ 
            setForeground(PageComponents.TEXT_COLOR); 
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }});
        filterPanel.add(statusCombo);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(searchButton);
        filterPanel.add(resetButton);
        
        // Table Panel
        JPanel tablePanel = createCardPanel();
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        // Create table with enhanced columns
        String[] columnNames = {"ID", "Type", "Route", "Date", "Time", "Price", "Available Seats", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tripTable = new JTable(tableModel);
        tripTable.setBackground(PageComponents.INPUT_COLOR);
        tripTable.setForeground(PageComponents.TEXT_COLOR);
        tripTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tripTable.setGridColor(PageComponents.SECONDARY_COLOR);
        tripTable.setSelectionBackground(PageComponents.PRIMARY_COLOR);
        tripTable.setSelectionForeground(Color.WHITE);
        tripTable.setRowHeight(25);
        
        // Set column widths
        tripTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tripTable.getColumnModel().getColumn(1).setPreferredWidth(70);  // Type
        tripTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Route
        tripTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Date
        tripTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Time
        tripTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Price
        tripTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Available Seats
        tripTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Status
        
        // Populate sample data
        populateSampleTripData();
        
        JScrollPane scrollPane = new JScrollPane(tripTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.getViewport().setBackground(PageComponents.INPUT_COLOR);
        
        // Action Buttons Panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton addTripButton = PageComponents.createStyledButton("Add New Trip", PageComponents.ACCENT_COLOR, true);
        JButton editTripButton = PageComponents.createStyledButton("Edit Trip", PageComponents.PRIMARY_COLOR, true);
        JButton viewDetailsButton = PageComponents.createStyledButton("View Details", PageComponents.SECONDARY_COLOR, false);
        JButton cancelTripButton = PageComponents.createStyledButton("Cancel Trip", new Color(255, 121, 121), true);
        JButton refreshButton = PageComponents.createStyledButton("Refresh", PageComponents.PRIMARY_COLOR, true);
        JButton backButton = PageComponents.createStyledButton("Back to Admin", PageComponents.SECONDARY_COLOR, false);
        
        actionPanel.add(addTripButton);
        actionPanel.add(editTripButton);
        actionPanel.add(viewDetailsButton);
        actionPanel.add(cancelTripButton);
        actionPanel.add(refreshButton);
        actionPanel.add(backButton);
        
        tablePanel.add(new JLabel("Trip List") {{
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setForeground(PageComponents.TEXT_COLOR);
        }});
        tablePanel.add(Box.createVerticalStrut(15));
        tablePanel.add(scrollPane);
        tablePanel.add(Box.createVerticalStrut(15));
        tablePanel.add(actionPanel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(filterPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Action listeners
        searchButton.addActionListener(e -> searchTrips());
        resetButton.addActionListener(e -> resetFilters());
        addTripButton.addActionListener(e -> addNewTrip());
        editTripButton.addActionListener(e -> editTrip());
        viewDetailsButton.addActionListener(e -> viewTripDetails());
        cancelTripButton.addActionListener(e -> cancelTrip());
        refreshButton.addActionListener(e -> refreshTripData());
        backButton.addActionListener(e -> {
            dispose();
            new AdminPage().display();
        });
    }
    
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(PageComponents.INPUT_COLOR);
        comboBox.setForeground(PageComponents.TEXT_COLOR);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1));
        comboBox.setPreferredSize(new Dimension(120, 35));
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
        
        PageComponents.showStyledMessage("Search Applied", filterInfo, this);
        
        // Here you would implement actual filtering logic
        // For now, we'll just show the message
    }
    
    private void resetFilters() {
        searchField.setText("Search trips...");
        searchField.setForeground(Color.GRAY);
        tripTypeCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        
        // Refresh table with all data
        tableModel.setRowCount(0);
        populateSampleTripData();
        
        PageComponents.showStyledMessage("Success", "Filters reset successfully!", this);
    }
    
    private void addNewTrip() {
        // This would open a trip creation dialog
        PageComponents.showStyledMessage("Info", "Trip creation dialog would open here.\nIntegration with your Trip models needed.", this);
    }
    
    private void editTrip() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a trip to edit!", this);
            return;
        }
        
        String tripId = (String) tableModel.getValueAt(selectedRow, 0);
        String tripType = (String) tableModel.getValueAt(selectedRow, 1);
        
        PageComponents.showStyledMessage("Info", 
            "Edit dialog would open for:\nTrip ID: " + tripId + "\nType: " + tripType + 
            "\n\nIntegration with your Trip models needed.", this);
    }
    
    private void viewTripDetails() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a trip to view details!", this);
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
        
        PageComponents.showStyledMessage("Trip Details", details, this);
    }
    
    private void cancelTrip() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a trip to cancel!", this);
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
            PageComponents.showStyledMessage("Success", 
                "Trip " + tripId + " has been cancelled!\n" +
                "All affected passengers will be notified.", this);
        }
    }
    
    private void refreshTripData() {
        tableModel.setRowCount(0);
        populateSampleTripData();
        
        // Reset filters
        searchField.setText("Search trips...");
        searchField.setForeground(Color.GRAY);
        tripTypeCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        
        PageComponents.showStyledMessage("Success", "Trip data refreshed successfully!", this);
    }
}