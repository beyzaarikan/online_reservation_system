package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AllReservationsPage extends BasePanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private JComboBox<String> typeFilter;
    
    public AllReservationsPage() {
        super("All Reservations Management", 1000, 700);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("All Reservations Management");
        
        // Filter and Search Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        // Search components
        searchField = PageComponents.createStyledTextField("Search by customer name or ID...");
        searchField.setPreferredSize(new Dimension(250, 35));
        
        statusFilter = new JComboBox<>(new String[]{"All Status", "Confirmed", "Pending", "Cancelled"});
        statusFilter.setBackground(PageComponents.INPUT_COLOR);
        statusFilter.setForeground(PageComponents.TEXT_COLOR);
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        typeFilter = new JComboBox<>(new String[]{"All Types", "Bus", "Flight"});
        typeFilter.setBackground(PageComponents.INPUT_COLOR);
        typeFilter.setForeground(PageComponents.TEXT_COLOR);
        typeFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JButton searchButton = PageComponents.createStyledButton("Search", PageComponents.PRIMARY_COLOR, true);
        JButton refreshButton = PageComponents.createStyledButton("Refresh", PageComponents.SECONDARY_COLOR, false);
        JButton exportButton = PageComponents.createStyledButton("Export", PageComponents.ACCENT_COLOR, true);
        
        filterPanel.add(new JLabel("Search: ") {{ 
            setForeground(PageComponents.TEXT_COLOR); 
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }});
        filterPanel.add(searchField);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JLabel("Status: ") {{ 
            setForeground(PageComponents.TEXT_COLOR); 
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }});
        filterPanel.add(statusFilter);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JLabel("Type: ") {{ 
            setForeground(PageComponents.TEXT_COLOR); 
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }});
        filterPanel.add(typeFilter);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(searchButton);
        filterPanel.add(refreshButton);
        filterPanel.add(exportButton);
        
        // Statistics Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JPanel totalCard = createStatCard("Total Reservations", "156", PageComponents.PRIMARY_COLOR);
        JPanel confirmedCard = createStatCard("Confirmed", "142", PageComponents.ACCENT_COLOR);
        JPanel pendingCard = createStatCard("Pending", "8", new Color(255, 193, 7));
        JPanel cancelledCard = createStatCard("Cancelled", "6", new Color(255, 85, 85));
        
        statsPanel.add(totalCard);
        statsPanel.add(confirmedCard);
        statsPanel.add(pendingCard);
        statsPanel.add(cancelledCard);
        
        // Table Panel
        JPanel tablePanel = createCardPanel();
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        // Create table with enhanced columns
        String[] columnNames = {
            "Reservation ID", "Customer Name", "Email", "Type", "Route", 
            "Date", "Time", "Seat", "Price", "Status", "Actions"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 10; // Only actions column is editable
            }
        };
        
        reservationTable = new JTable(tableModel);
        reservationTable.setBackground(PageComponents.INPUT_COLOR);
        reservationTable.setForeground(PageComponents.TEXT_COLOR);
        reservationTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reservationTable.setGridColor(PageComponents.SECONDARY_COLOR);
        reservationTable.setSelectionBackground(PageComponents.PRIMARY_COLOR);
        reservationTable.setSelectionForeground(Color.WHITE);
        reservationTable.setRowHeight(35);
        
        // Set column widths
        reservationTable.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        reservationTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Name
        reservationTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Email
        reservationTable.getColumnModel().getColumn(3).setPreferredWidth(60);  // Type
        reservationTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Route
        reservationTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Date
        reservationTable.getColumnModel().getColumn(6).setPreferredWidth(60);  // Time
        reservationTable.getColumnModel().getColumn(7).setPreferredWidth(50);  // Seat
        reservationTable.getColumnModel().getColumn(8).setPreferredWidth(60);  // Price
        reservationTable.getColumnModel().getColumn(9).setPreferredWidth(80);  // Status
        reservationTable.getColumnModel().getColumn(10).setPreferredWidth(100); // Actions
        
        // Populate sample data
        populateSampleData();
        
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setPreferredSize(new Dimension(900, 300));
        scrollPane.getViewport().setBackground(PageComponents.INPUT_COLOR);
        
        // Action Buttons Panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton viewDetailsButton = PageComponents.createStyledButton("View Details", PageComponents.PRIMARY_COLOR, true);
        JButton updateStatusButton = PageComponents.createStyledButton("Update Status", PageComponents.ACCENT_COLOR, true);
        JButton cancelReservationButton = PageComponents.createStyledButton("Cancel Reservation", new Color(255, 121, 121), true);
        JButton sendEmailButton = PageComponents.createStyledButton("Send Email", PageComponents.SECONDARY_COLOR, false);
        JButton backButton = PageComponents.createStyledButton("← Back", PageComponents.SECONDARY_COLOR, false);
        
        actionPanel.add(viewDetailsButton);
        actionPanel.add(updateStatusButton);
        actionPanel.add(cancelReservationButton);
        actionPanel.add(sendEmailButton);
        actionPanel.add(Box.createHorizontalStrut(20));
        actionPanel.add(backButton);
        
        tablePanel.add(new JLabel("Reservation Management") {{
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setForeground(PageComponents.TEXT_COLOR);
        }});
        tablePanel.add(Box.createVerticalStrut(15));
        tablePanel.add(scrollPane);
        tablePanel.add(Box.createVerticalStrut(15));
        tablePanel.add(actionPanel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(statsPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Action listeners
        searchButton.addActionListener(e -> searchReservations());
        refreshButton.addActionListener(e -> refreshData());
        exportButton.addActionListener(e -> exportData());
        viewDetailsButton.addActionListener(e -> viewReservationDetails());
        updateStatusButton.addActionListener(e -> updateReservationStatus());
        cancelReservationButton.addActionListener(e -> cancelReservation());
        sendEmailButton.addActionListener(e -> sendEmailToCustomer());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display(); // Ana menüye dön
        });
        
        statusFilter.addActionListener(e -> filterByStatus());
        typeFilter.addActionListener(e -> filterByType());
    }
    
    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PageComponents.CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(titleLabel);
        
        return card;
    }
    
    private void populateSampleData() {
        // Bus reservations
        tableModel.addRow(new Object[]{
            "RES001", "John Doe", "john@example.com", "Bus", "Istanbul → Ankara", 
            "15/06/2025", "09:00", "A12", "$50.00", "Confirmed", "Actions"
        });
        tableModel.addRow(new Object[]{
            "RES002", "Jane Smith", "jane@example.com", "Flight", "Istanbul → London", 
            "18/06/2025", "14:30", "12F", "$450.00", "Confirmed", "Actions"
        });
        tableModel.addRow(new Object[]{
            "RES003", "Mike Wilson", "mike@example.com", "Bus", "Ankara → Izmir", 
            "20/06/2025", "22:00", "B05", "$65.00", "Pending", "Actions"
        });
        tableModel.addRow(new Object[]{
            "RES004", "Sarah Jones", "sarah@example.com", "Flight", "Izmir → Paris", 
            "22/06/2025", "16:45", "8A", "$380.00", "Confirmed", "Actions"
        });
        tableModel.addRow(new Object[]{
            "RES005", "David Brown", "david@example.com", "Bus", "Istanbul → Izmir", 
            "25/06/2025", "08:30", "C18", "$45.00", "Cancelled", "Actions"
        });
        tableModel.addRow(new Object[]{
            "RES006", "Emily Davis", "emily@example.com", "Flight", "Ankara → Berlin", 
            "28/06/2025", "11:20", "15C", "$520.00", "Pending", "Actions"
        });
    }
    
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
        // Implement actual filtering logic here
    }
    
    private void filterByType() {
        String selectedType = (String) typeFilter.getSelectedItem();
        PageComponents.showStyledMessage("Info", "Filtering by type: " + selectedType, this);
        // Implement actual filtering logic here
    }
}