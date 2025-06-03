package gui;

import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TripSearchPage extends BasePanel {
    private JTextField fromField;
    private JTextField toField;
    private JFormattedTextField dateField;
    private JComboBox<String> tripTypeCombo;
    private JTable tripTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JLabel resultCountLabel;
    private JButton searchButton;
    private JButton selectButton;
    private JButton backButton;
    
    public TripSearchPage() {
        super("Search Trips", 900, 700);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("🔍 Search Trips");
        
        // Search Form Panel
        JPanel searchPanel = createSearchPanel();
        
        // Results Panel
        JPanel resultsPanel = createResultsPanel();
        
        // Navigation Panel
        JPanel navPanel = createNavigationPanel();
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);
        
        // Add navigation panel at the bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        bottomPanel.add(navPanel, BorderLayout.CENTER);
        
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        add(mainPanel);
        
        setupActionListeners();
    }
    
    private JPanel createSearchPanel() {
        JPanel searchPanel = createCardPanel();
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(25, 30, 25, 30)
        ));
        
        // Search form title
        JLabel searchTitle = new JLabel("🎯 Search Criteria");
        searchTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        searchTitle.setForeground(PageComponents.TEXT_COLOR);
        searchTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create form fields
        fromField = PageComponents.createStyledTextField("Departure City");
        toField = PageComponents.createStyledTextField("Destination City");
        
        // Date field with formatter
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateField = new JFormattedTextField(dateFormat);
            styleFormattedField(dateField, "Select Date");
        } catch (Exception e) {
            dateField = new JFormattedTextField();
            styleFormattedField(dateField, "dd/MM/yyyy");
        }
        
        // Trip type combo box
        String[] tripTypes = {"All Types", "Bus", "Flight"};
        tripTypeCombo = new JComboBox<>(tripTypes);
        styleComboBox(tripTypeCombo);
        
        searchButton = PageComponents.createStyledButton("🔍 Search Trips", PageComponents.PRIMARY_COLOR, true);
        searchButton.setPreferredSize(new Dimension(180, 45));
        
        // Layout
        JPanel formRow1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        formRow1.setBackground(PageComponents.CARD_COLOR);
        formRow1.add(PageComponents.createFormField("From", fromField));
        formRow1.add(PageComponents.createFormField("To", toField));
        
        JPanel formRow2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        formRow2.setBackground(PageComponents.CARD_COLOR);
        formRow2.add(PageComponents.createFormField("Date", dateField));
        formRow2.add(PageComponents.createFormField("Type", tripTypeCombo));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        buttonPanel.add(searchButton);
        
        searchPanel.add(searchTitle);
        searchPanel.add(Box.createVerticalStrut(20));
        searchPanel.add(formRow1);
        searchPanel.add(Box.createVerticalStrut(15));
        searchPanel.add(formRow2);
        searchPanel.add(Box.createVerticalStrut(20));
        searchPanel.add(buttonPanel);
        
        return searchPanel;
    }
    
    private JPanel createResultsPanel() {
        JPanel resultsPanel = createCardPanel();
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        // Results header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PageComponents.CARD_COLOR);
        
        JLabel resultsLabel = new JLabel("📋 Available Trips");
        resultsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultsLabel.setForeground(PageComponents.TEXT_COLOR);
        
        resultCountLabel = new JLabel("0 trips found");
        resultCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultCountLabel.setForeground(PageComponents.ACCENT_COLOR);
        
        headerPanel.add(resultsLabel, BorderLayout.WEST);
        headerPanel.add(resultCountLabel, BorderLayout.EAST);
        
        // Create enhanced table
        String[] columnNames = {"Type", "Route", "Date", "Departure", "Arrival", "Duration", "Price", "Available Seats"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return ImageIcon.class; // For trip type icons
                return String.class;
            }
        };
        
        tripTable = new JTable(tableModel);
        setupTable();
        
        scrollPane = new JScrollPane(tripTable);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        scrollPane.getViewport().setBackground(PageComponents.INPUT_COLOR);
        scrollPane.setBorder(BorderFactory.createLineBorder(PageComponents.SECONDARY_COLOR));
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBackground(PageComponents.CARD_COLOR);
        
        selectButton = PageComponents.createStyledButton("✈️ Select Trip", PageComponents.ACCENT_COLOR, true);
        JButton refreshButton = PageComponents.createStyledButton("🔄 Refresh", PageComponents.SECONDARY_COLOR, false);
        
        selectButton.setEnabled(false); // Initially disabled
        
        actionPanel.add(selectButton);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(refreshButton);
        
        resultsPanel.add(headerPanel);
        resultsPanel.add(Box.createVerticalStrut(15));
        resultsPanel.add(scrollPane);
        resultsPanel.add(Box.createVerticalStrut(15));
        resultsPanel.add(actionPanel);
        
        return resultsPanel;
    }
    
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        backButton = PageComponents.createStyledButton("⬅️ Back to Menu", PageComponents.SECONDARY_COLOR, false);
        navPanel.add(backButton);
        
        return navPanel;
    }
    
    private void setupTable() {
        tripTable.setBackground(PageComponents.INPUT_COLOR);
        tripTable.setForeground(PageComponents.TEXT_COLOR);
        tripTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tripTable.setGridColor(PageComponents.SECONDARY_COLOR);
        tripTable.setSelectionBackground(PageComponents.PRIMARY_COLOR);
        tripTable.setSelectionForeground(Color.WHITE);
        tripTable.setRowHeight(35);
        tripTable.setShowGrid(true);
        tripTable.setIntercellSpacing(new Dimension(1, 1));
        
        // Set column widths
        tripTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // Type
        tripTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Route
        tripTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Date
        tripTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Departure
        tripTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Arrival
        tripTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Duration
        tripTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Price
        tripTable.getColumnModel().getColumn(7).setPreferredWidth(120); // Available Seats
        
        // Add selection listener
        tripTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectButton.setEnabled(tripTable.getSelectedRow() != -1);
            }
        });
    }
    
    private void styleFormattedField(JFormattedTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(PageComponents.TEXT_COLOR);
        field.setBackground(PageComponents.INPUT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1, true),
            new javax.swing.border.EmptyBorder(10, 12, 10, 12)
        ));
        field.setCaretColor(PageComponents.TEXT_COLOR);
        field.setMaximumSize(new Dimension(200, 40));
        field.setPreferredSize(new Dimension(200, 40));
    }
    
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setForeground(PageComponents.TEXT_COLOR);
        comboBox.setBackground(PageComponents.INPUT_COLOR);
        comboBox.setBorder(BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1, true));
        comboBox.setMaximumSize(new Dimension(200, 40));
        comboBox.setPreferredSize(new Dimension(200, 40));
    }
    
    private void setupActionListeners() {
        searchButton.addActionListener(e -> searchTrips());
        
        selectButton.addActionListener(e -> selectTrip());
        
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        
        // Add refresh action for the refresh button
        // (You can add this listener to the refresh button created in createResultsPanel)
    }
    
    private void searchTrips() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        String selectedType = (String) tripTypeCombo.getSelectedItem();
        
        // Validation
        if (from.equals("Departure City") || from.isEmpty()) {
            PageComponents.showStyledMessage("❌ Error", "Please enter departure city!", this);
            return;
        }
        
        if (to.equals("Destination City") || to.isEmpty()) {
            PageComponents.showStyledMessage("❌ Error", "Please enter destination city!", this);
            return;
        }
        
        if (from.equalsIgnoreCase(to)) {
            PageComponents.showStyledMessage("❌ Error", "Departure and destination cities cannot be the same!", this);
            return;
        }
        
        // Clear previous results
        tableModel.setRowCount(0);
        
        // Simulate search results - In real implementation, call your service layer
        addSampleTrips(from, to, selectedType);
        
        // Update result count
        int resultCount = tableModel.getRowCount();
        resultCountLabel.setText(resultCount + " trip(s) found");
        
        if (resultCount > 0) {
            PageComponents.showStyledMessage("✅ Success", "Found " + resultCount + " available trips!", this);
        } else {
            PageComponents.showStyledMessage("ℹ️ Info", "No trips found for your search criteria.", this);
        }
    }
    
    private void addSampleTrips(String from, String to, String type) {
        // Sample data - replace with actual service calls
        if (type.equals("All Types") || type.equals("Bus")) {
            tableModel.addRow(new Object[]{
                "🚌", from + " → " + to, "15/06/2025", "09:00", "14:00", "5h 0m", "$45.00", "12/45"
            });
            tableModel.addRow(new Object[]{
                "🚌", from + " → " + to, "15/06/2025", "15:30", "20:30", "5h 0m", "$42.00", "8/45"
            });
        }
        
        if (type.equals("All Types") || type.equals("Flight")) {
            tableModel.addRow(new Object[]{
                "✈️", from + " → " + to, "15/06/2025", "10:30", "12:00", "1h 30m", "$120.00", "25/180"
            });
            tableModel.addRow(new Object[]{
                "✈️", from + " → " + to, "15/06/2025", "18:45", "20:15", "1h 30m", "$135.00", "15/180"
            });
        }
    }
    
    private void selectTrip() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("❌ Error", "Please select a trip!", this);
            return;
        }
        
        String tripType = (String) tableModel.getValueAt(selectedRow, 0);
        String route = (String) tableModel.getValueAt(selectedRow, 1);
        String date = (String) tableModel.getValueAt(selectedRow, 2);
        String departure = (String) tableModel.getValueAt(selectedRow, 3);
        String price = (String) tableModel.getValueAt(selectedRow, 6);
        String availableSeats = (String) tableModel.getValueAt(selectedRow, 7);
        
        // Navigate to seat selection page
        dispose();
        new SeatSelectionPage(tripType, route, date, departure, price, availableSeats).display();
    }
}