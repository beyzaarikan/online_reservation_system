package gui;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SearchBusTripPage extends BasePanel {
    private JTextField fromField;
    private JTextField toField;
    private JSpinner dateSpinner;
    private JSpinner returnDateSpinner;
    private JComboBox<String> passengerCount;
    private JCheckBox roundTripCheckbox;
    private JTable busTable;
    private DefaultTableModel tableModel;
    private JPanel returnDatePanel;
    
    public SearchBusTripPage() {
        super("Search Bus Trips", 1200, 800);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("🚌 Search Bus Trips");
        
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
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel searchTitle = new JLabel("Find Your Perfect Bus Trip", SwingConstants.CENTER);
        searchTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        searchTitle.setForeground(PageComponents.TEXT_COLOR);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PageComponents.CARD_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // From City
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createFieldLabel("From City:"), gbc);
        gbc.gridx = 1;
        fromField = PageComponents.createStyledTextField("Enter departure city");
        fromField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(fromField, gbc);
        
        // To City
        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(createFieldLabel("To City:"), gbc);
        gbc.gridx = 3;
        toField = PageComponents.createStyledTextField("Enter destination city");
        toField.setPreferredSize(new Dimension(200, 35));
        formPanel.add(toField, gbc);
        
        // Departure Date
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createFieldLabel("Departure Date:"), gbc);
        gbc.gridx = 1;
        dateSpinner = createDateSpinner();
        formPanel.add(dateSpinner, gbc);
        
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
        returnDateSpinner = createDateSpinner();
        returnDatePanel.add(returnDateSpinner);
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
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton searchButton = PageComponents.createStyledButton("🔍 Search Buses", PageComponents.PRIMARY_COLOR, true);
        JButton clearButton = PageComponents.createStyledButton("Clear", PageComponents.SECONDARY_COLOR, false);
        JButton backButton = PageComponents.createStyledButton("← Back to Menu", PageComponents.SECONDARY_COLOR, false);
        JButton okeyButton = PageComponents.createStyledButton("Select Trip & Proceed", PageComponents.PRIMARY_COLOR, true);

        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(okeyButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);
        
        // Add action listeners
        searchButton.addActionListener(e -> searchBuses());
        clearButton.addActionListener(e -> clearForm());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });

        okeyButton.addActionListener(e -> selectTripAndProceed());
        
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
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel resultsTitle = new JLabel("Available Bus Trips", SwingConstants.CENTER);
        resultsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultsTitle.setForeground(PageComponents.TEXT_COLOR);
        
        // Create table
        String[] columnNames = {
            "Bus Company", "Route", "Departure", "Arrival", "Duration", 
            "Price", "Seats Available", "Amenities"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hiçbir hücre düzenlenemesin
            }
        };
        
        busTable = new JTable(tableModel);
        busTable.setBackground(PageComponents.INPUT_COLOR);
        busTable.setForeground(PageComponents.TEXT_COLOR);
        busTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        busTable.setGridColor(PageComponents.SECONDARY_COLOR);
        busTable.setSelectionBackground(PageComponents.PRIMARY_COLOR);
        busTable.setSelectionForeground(Color.WHITE);
        busTable.setRowHeight(40);
        busTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Tek satır seçimi
        
        // Set column widths
        busTable.getColumnModel().getColumn(0).setPreferredWidth(140); // Company
        busTable.getColumnModel().getColumn(1).setPreferredWidth(180); // Route
        busTable.getColumnModel().getColumn(2).setPreferredWidth(90);  // Departure
        busTable.getColumnModel().getColumn(3).setPreferredWidth(90);  // Arrival
        busTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Duration
        busTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Price
        busTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Seats
        busTable.getColumnModel().getColumn(7).setPreferredWidth(180); // Amenities
        
        // Center align some columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        busTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Departure
        busTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Arrival
        busTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Duration
        busTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Price
        busTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Seats
        
        JScrollPane scrollPane = new JScrollPane(busTable);
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
    
    private void searchBuses() {
        String from = fromField.getText();
        String to = toField.getText();
        
        if (from.equals("Enter departure city") || from.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter departure city!", this);
            return;
        }
        
        if (to.equals("Enter destination city") || to.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter destination city!", this);
            return;
        }
        
        // Clear previous results
        tableModel.setRowCount(0);
        
        // Add sample bus results
        populateSampleBusData(from, to);
        
    }
    
    private void populateSampleBusData(String from, String to) {
        // Sample bus data - Book kolonu kaldırıldı
        tableModel.addRow(new Object[]{
            "Metro Turizm", from + " → " + to, "08:00", "14:30", "6h 30m", 
            "$45.00", "12 seats", "WiFi, AC, TV"
        });
        tableModel.addRow(new Object[]{
            "Varan Turizm", from + " → " + to, "10:15", "16:45", "6h 30m", 
            "$52.00", "8 seats", "WiFi, AC, Refreshment"
        });
        tableModel.addRow(new Object[]{
            "Kamil Koç", from + " → " + to, "14:00", "20:30", "6h 30m", 
            "$48.00", "15 seats", "WiFi, AC"
        });
        tableModel.addRow(new Object[]{
            "Pamukkale Turizm", from + " → " + to, "18:30", "01:00", "6h 30m", 
            "$50.00", "6 seats", "WiFi, AC, TV, Meal"
        });
        tableModel.addRow(new Object[]{
            "Ulusoy", from + " → " + to, "22:00", "04:30", "6h 30m", 
            "$42.00", "20 seats", "WiFi, AC"
        });
    }
    
    private void clearForm() {
        fromField.setText("Enter departure city");
        toField.setText("Enter destination city");
        dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));
        returnDateSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));
        passengerCount.setSelectedIndex(0);
        roundTripCheckbox.setSelected(false);
        toggleReturnDate();
        tableModel.setRowCount(0);
        busTable.clearSelection(); // Seçimi temizle
    }
    
    private void selectTripAndProceed() {
        int selectedRow = busTable.getSelectedRow();
        
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Warning", "Please select a bus trip first!", this);
            return;
        }
        
        // Seçilen satırdan tüm bilgileri al
        String busCompany = (String) tableModel.getValueAt(selectedRow, 0);
        // String route = (String) tableModel.getValueAt(selectedRow, 1); // Route is constructed below
        String departureTime = (String) tableModel.getValueAt(selectedRow, 2);
        String arrivalTime = (String) tableModel.getValueAt(selectedRow, 3);
        // String duration = (String) tableModel.getValueAt(selectedRow, 4); // Not directly used in constructor
        String price = (String) tableModel.getValueAt(selectedRow, 5);
        // String seatsAvailable = (String) tableModel.getValueAt(selectedRow, 6); // Not directly used in constructor
        String amenities = (String) tableModel.getValueAt(selectedRow, 7);
        
        // Form bilgilerini al
        String fromCity = fromField.getText();
        String toCity = toField.getText();
        
        // Tarih bilgisini al
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String departureDate = dateFormat.format((Date) dateSpinner.getValue());
        
        String returnDate = null;
        if (roundTripCheckbox.isSelected()) {
            returnDate = dateFormat.format((Date) returnDateSpinner.getValue());
        }
        
        String passengerCountStr = (String) passengerCount.getSelectedItem();
        int passengerCountInt = Integer.parseInt(passengerCountStr.replace("+", "")); // Remove '+' for parsing
        
        // Directly proceed to BusSeatSelectionPage
        dispose();
        try {
            BusSeatSelectionPage seatSelectionPage = new BusSeatSelectionPage(
                busCompany, 
                fromCity, 
                toCity, 
                returnDate, 
                departureDate, 
                arrivalTime, 
                price, 
                passengerCountInt, 
                amenities
            );
            seatSelectionPage.display();
        } catch (Exception ex) {
            PageComponents.showStyledMessage("Error", 
                "Failed to proceed to seat selection: " + ex.getMessage(), this);
            ex.printStackTrace(); // Print stack trace for debugging
        }
    }
}