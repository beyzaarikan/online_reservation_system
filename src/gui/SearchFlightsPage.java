package gui;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchFlightsPage extends BasePanel {
    private JTextField fromField;
    private JTextField toField;
    private JSpinner departureSpinner;
    private JSpinner returnSpinner;
    private JComboBox<String> passengerCount;
    private JComboBox<String> flightClass;
    private JCheckBox roundTripCheckbox;
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private JPanel returnDatePanel;
    
    public SearchFlightsPage() {
        super("Search Flights", 1200, 800);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("✈️ Search Flights");
        
        // Search Form Panel
        JPanel searchPanel = createSearchPanel();
        
        // Results Panel
        JPanel resultsPanel = createResultsPanel();
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createSearchPanel() {
        JPanel searchCard = createCardPanel();
        searchCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel searchTitle = new JLabel("Discover Your Next Flight", SwingConstants.CENTER);
        searchTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        searchTitle.setForeground(PageComponents.TEXT_COLOR);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PageComponents.CARD_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // From Airport
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createFieldLabel("From Airport:"), gbc);
        gbc.gridx = 1;
        fromField = PageComponents.createStyledTextField("Enter departure airport/city");
        fromField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(fromField, gbc);
        
        // To Airport
        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(createFieldLabel("To Airport:"), gbc);
        gbc.gridx = 3;
        toField = PageComponents.createStyledTextField("Enter destination airport/city");
        toField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(toField, gbc);
        
        // Departure Date
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createFieldLabel("Departure Date:"), gbc);
        gbc.gridx = 1;
        departureSpinner = createDateSpinner();
        formPanel.add(departureSpinner, gbc);
        
        // Round Trip Checkbox
        gbc.gridx = 2; gbc.gridy = 1;
        roundTripCheckbox = new JCheckBox("Round Trip");
        roundTripCheckbox.setBackground(PageComponents.CARD_COLOR);
        roundTripCheckbox.setForeground(PageComponents.TEXT_COLOR);
        roundTripCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roundTripCheckbox.addActionListener(e -> toggleReturnDate());
        formPanel.add(roundTripCheckbox, gbc);
        
        // Return Date (initially hidden)
        returnDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        returnDatePanel.setBackground(PageComponents.CARD_COLOR);
        returnDatePanel.add(createFieldLabel("Return Date:"));
        returnSpinner = createDateSpinner();
        returnDatePanel.add(returnSpinner);
        returnDatePanel.setVisible(false);
        
        gbc.gridx = 3; gbc.gridy = 1;
        formPanel.add(returnDatePanel, gbc);
        
        // Passengers
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createFieldLabel("Passengers:"), gbc);
        gbc.gridx = 1;
        passengerCount = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6+"});
        passengerCount.setBackground(PageComponents.INPUT_COLOR);
        passengerCount.setForeground(PageComponents.TEXT_COLOR);
        passengerCount.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(passengerCount, gbc);
        
        // Flight Class
        gbc.gridx = 2; gbc.gridy = 2;
        formPanel.add(createFieldLabel("Class:"), gbc);
        gbc.gridx = 3;
        flightClass = new JComboBox<>(new String[]{"Economy", "Premium Economy", "Business", "First Class"});
        flightClass.setBackground(PageComponents.INPUT_COLOR);
        flightClass.setForeground(PageComponents.TEXT_COLOR);
        flightClass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(flightClass, gbc);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton searchButton = PageComponents.createStyledButton("✈️ Search Flights", PageComponents.ACCENT_COLOR, true);
        JButton clearButton = PageComponents.createStyledButton("Clear", PageComponents.SECONDARY_COLOR, false);
        JButton backButton = PageComponents.createStyledButton("← Back to Menu", PageComponents.SECONDARY_COLOR, false);
        
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(backButton);
        
        // Add action listeners
        searchButton.addActionListener(e -> searchFlights());
        clearButton.addActionListener(e -> clearForm());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        
        searchCard.add(searchTitle);
        searchCard.add(Box.createVerticalStrut(20));
        searchCard.add(formPanel);
        searchCard.add(Box.createVerticalStrut(15));
        searchCard.add(buttonPanel);
        
        return searchCard;
    }
    
    private JPanel createResultsPanel() {
        JPanel resultsCard = createCardPanel();
        resultsCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 193, 7), 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel resultsTitle = new JLabel("Available Flights", SwingConstants.CENTER);
        resultsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultsTitle.setForeground(PageComponents.TEXT_COLOR);
        
        // Create table
        String[] columnNames = {
            "Airline", "Flight No", "Route", "Departure", "Arrival", 
            "Duration", "Stops", "Class", "Price", "Book"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9; // Only Book column is editable
            }
        };
        
        flightTable = new JTable(tableModel);
        flightTable.setBackground(PageComponents.INPUT_COLOR);
        flightTable.setForeground(PageComponents.TEXT_COLOR);
        flightTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        flightTable.setGridColor(PageComponents.SECONDARY_COLOR);
        flightTable.setSelectionBackground(PageComponents.ACCENT_COLOR);
        flightTable.setSelectionForeground(Color.WHITE);
        flightTable.setRowHeight(40);
        
        // Set column widths
        flightTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Airline
        flightTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Flight No
        flightTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Route
        flightTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Departure
        flightTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Arrival
        flightTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Duration
        flightTable.getColumnModel().getColumn(6).setPreferredWidth(70);  // Stops
        flightTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Class
        flightTable.getColumnModel().getColumn(8).setPreferredWidth(80);  // Price
        flightTable.getColumnModel().getColumn(9).setPreferredWidth(90);  // Book
        
        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setPreferredSize(new Dimension(1000, 250));
        scrollPane.getViewport().setBackground(PageComponents.INPUT_COLOR);
        
        resultsCard.add(resultsTitle);
        resultsCard.add(Box.createVerticalStrut(15));
        resultsCard.add(scrollPane);
        
        return resultsCard;
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(PageComponents.TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }
    
    private JSpinner createDateSpinner() {
        LocalDate today = LocalDate.now();
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(dateModel);
        
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        spinner.setValue(java.sql.Date.valueOf(today));
        
        spinner.setPreferredSize(new Dimension(120, 35));
        
        // Style the spinner
        spinner.getEditor().getComponent(0).setBackground(PageComponents.INPUT_COLOR);
        spinner.getEditor().getComponent(0).setForeground(PageComponents.TEXT_COLOR);
        
        return spinner;
    }
    
    private void toggleReturnDate() {
        returnDatePanel.setVisible(roundTripCheckbox.isSelected());
        revalidate();
        repaint();
    }
    
    private void searchFlights() {
        String from = fromField.getText();
        String to = toField.getText();
        
        if (from.equals("Enter departure airport/city") || from.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter departure airport/city!", this);
            return;
        }
        
        if (to.equals("Enter destination airport/city") || to.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter destination airport/city!", this);
            return;
        }
        
        // Clear previous results
        tableModel.setRowCount(0);
        
        // Add sample flight results
        populateSampleFlightData(from, to);
        
        String selectedClass = (String) flightClass.getSelectedItem();
        PageComponents.showStyledMessage("Success", 
            "Found " + tableModel.getRowCount() + " " + selectedClass + " flights from " + from + " to " + to + "!", this);
    }
    
    private void populateSampleFlightData(String from, String to) {
        String selectedClass = (String) flightClass.getSelectedItem();
        
        // Sample flight data based on class
        if (selectedClass.equals("Economy")) {
            tableModel.addRow(new Object[]{
                "Turkish Airlines", "TK1234", from + " → " + to, "08:15", "11:45", 
                "3h 30m", "Direct", "Economy", "$320.00", "Book Now"
            });
            tableModel.addRow(new Object[]{
                "Pegasus Airlines", "PC5678", from + " → " + to, "14:30", "18:00", 
                "3h 30m", "Direct", "Economy", "$285.00", "Book Now"
            });
            tableModel.addRow(new Object[]{
                "AnadoluJet", "AJ9012", from + " → " + to, "19:20", "22:50", 
                "3h 30m", "Direct", "Economy", "$295.00", "Book Now"
            });
        } else if (selectedClass.equals("Business")) {
            tableModel.addRow(new Object[]{
                "Turkish Airlines", "TK1234", from + " → " + to, "08:15", "11:45", 
                "3h 30m", "Direct", "Business", "$1,250.00", "Book Now"
            });
            tableModel.addRow(new Object[]{
                "Lufthansa", "LH3456", from + " → " + to, "16:40", "20:10", 
                "3h 30m", "Direct", "Business", "$1,180.00", "Book Now"
            });
        } else if (selectedClass.equals("First Class")) {
            tableModel.addRow(new Object[]{
                "Turkish Airlines", "TK1234", from + " → " + to, "08:15", "11:45", 
                "3h 30m", "Direct", "First Class", "$2,450.00", "Book Now"
            });
            tableModel.addRow(new Object[]{
                "Emirates", "EK7890", from + " → " + to, "22:35", "02:05", 
                "3h 30m", "Direct", "First Class", "$2,680.00", "Book Now"
            });
        } else { // Premium Economy
            tableModel.addRow(new Object[]{
                "Turkish Airlines", "TK1234", from + " → " + to, "08:15", "11:45", 
                "3h 30m", "Direct", "Premium Economy", "$485.00", "Book Now"
            });
            tableModel.addRow(new Object[]{
                "KLM", "KL2468", from + " → " + to, "13:25", "16:55", 
                "3h 30m", "Direct", "Premium Economy", "$520.00", "Book Now"
            });
        }
        
        // Add some connecting flights
        tableModel.addRow(new Object[]{
            "Air France", "AF1357", from + " → " + to, "06:30", "12:15", 
            "5h 45m", "1 Stop", selectedClass, getPriceForClass(selectedClass, 0.85), "Book Now"
        });
    }
    
    private String getPriceForClass(String flightClass, double multiplier) {
        double basePrice = 320.0;
        switch (flightClass) {
            case "Premium Economy": basePrice = 485.0; break;
            case "Business": basePrice = 1250.0; break;
            case "First Class": basePrice = 2450.0; break;
        }
        return "$" + String.format("%.2f", basePrice * multiplier);
    }
    
    private void clearForm() {
        fromField.setText("Enter departure airport/city");
        toField.setText("Enter destination airport/city");
        departureSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));
        returnSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));
        passengerCount.setSelectedIndex(0);
        flightClass.setSelectedIndex(0);
        roundTripCheckbox.setSelected(false);
        toggleReturnDate();
        tableModel.setRowCount(0);
    }
}