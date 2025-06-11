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

/* 
//package gui;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SearchFlightTripPage extends BasePanel {
    private JComboBox<String> flightClass;
    private JTextField fromField;
    private JTextField toField;
    private JSpinner dateSpinner;
    private JSpinner returnDateSpinner;
    private JComboBox<String> passengerCount;
    private JCheckBox roundTripCheckbox;
    private JTable busTable;
    private DefaultTableModel tableModel;
    private JPanel returnDatePanel;
    
    public SearchFlightTripPage() {
        super("Search Flight Trips - Travel System", 1200, 800);
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

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createModernButton("← Back to Menu", new Color(108, 92, 231), false);
        backButton.setPreferredSize(new Dimension(150, 35));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backPanel.add(backButton);

        // Center panel with scroll
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JLabel titleLabel = new JLabel("Search Flight Trips", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Find your perfect journey", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);

        // Search Form panel with glassmorphism effect
        JPanel searchFormPanel = new JPanel() {
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
        searchFormPanel.setLayout(new BoxLayout(searchFormPanel, BoxLayout.Y_AXIS));
        searchFormPanel.setOpaque(false);
        searchFormPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        searchFormPanel.setMaximumSize(new Dimension(900, 400));

        // Form title
        JLabel formTitle = new JLabel("Plan Your Trip", SwingConstants.CENTER);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        formTitle.setForeground(Color.WHITE);
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form fields panel
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // From and To cities - same row
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(createFieldPanel("From City", fromField = createModernTextField("Enter departure city")), gbc);
        
        gbc.gridx = 1;
        fieldsPanel.add(createFieldPanel("To City", toField = createModernTextField("Enter destination city")), gbc);

        // Date and Round trip - same row
        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(createFieldPanel("Departure Date", dateSpinner = createDateSpinner()), gbc);
        
        gbc.gridx = 1;
        JPanel roundTripPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundTripPanel.setOpaque(false);
        roundTripCheckbox = new JCheckBox("Round Trip");
        roundTripCheckbox.setOpaque(false);
        roundTripCheckbox.setForeground(Color.WHITE);
        roundTripCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roundTripCheckbox.addActionListener(e -> toggleReturnDate());
        roundTripPanel.add(roundTripCheckbox);
        fieldsPanel.add(createFieldPanel("Trip Type", roundTripPanel), gbc);

        
        gbc.gridx = 1;
        passengerCount = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6+"});
        styleComboBox(passengerCount);
        fieldsPanel.add(createFieldPanel("Passengers", passengerCount), gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        JButton searchButton = createModernButton("Search Buses", new Color(138, 43, 226), true);
        JButton clearButton = createModernButton("Clear Form", new Color(108, 92, 231), false);
        
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        searchFormPanel.add(formTitle);
        searchFormPanel.add(Box.createVerticalStrut(25));
        searchFormPanel.add(fieldsPanel);
        searchFormPanel.add(Box.createVerticalStrut(20));
        searchFormPanel.add(buttonPanel);

        // Results panel with glassmorphism effect
        JPanel resultsPanel = new JPanel() {
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
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        resultsPanel.setMaximumSize(new Dimension(900, 500));

        JLabel resultsTitle = new JLabel("Available Flight Trips", SwingConstants.CENTER);
        resultsTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        resultsTitle.setForeground(Color.WHITE);
        resultsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create table
        String[] columnNames = {
            "Flight Company", "Route", "Departure", "Arrival", "Duration", 
            "Price", "Seats Available", "Amenities"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        busTable = new JTable(tableModel);
        styleTable(busTable);
        
        JScrollPane tableScrollPane = new JScrollPane(busTable);
        styleScrollPane(tableScrollPane);
        tableScrollPane.setPreferredSize(new Dimension(800, 250));

        // Select and proceed button
        JButton selectButton = createModernButton("Select Trip & Proceed", new Color(138, 43, 226), true);
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultsPanel.add(resultsTitle);
        resultsPanel.add(Box.createVerticalStrut(20));
        resultsPanel.add(tableScrollPane);
        resultsPanel.add(Box.createVerticalStrut(15));
        resultsPanel.add(selectButton);

        centerPanel.add(titlePanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(searchFormPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(resultsPanel);
        centerPanel.add(Box.createVerticalStrut(30));

        scrollPane.setViewportView(centerPanel);
        
        mainPanel.add(backPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Action listeners
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });

        searchButton.addActionListener(e -> searchFlights());
        clearButton.addActionListener(e -> clearForm());
        selectButton.addActionListener(e -> selectTripAndProceed());
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
        field.setPreferredSize(new Dimension(250, 45));
        field.setMaximumSize(new Dimension(250, 45));
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
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(isPrimary ? 200 : 120, 45));
        
        return button;
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(189, 147, 249));
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);
        
        return panel;
    }

    private JSpinner createDateSpinner() {
        LocalDate today = LocalDate.now();
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(dateModel) {
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
        
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        spinner.setValue(java.sql.Date.valueOf(today));
        spinner.setPreferredSize(new Dimension(250, 45));
        spinner.setMaximumSize(new Dimension(250, 45));
        
        // Style the spinner components
        Component editor = spinner.getEditor();
        JFormattedTextField textField = ((JSpinner.DateEditor) editor).getTextField();
        textField.setBackground(new Color(255, 255, 255, 0));
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        return spinner;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(new Color(255, 255, 255, 15));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(250, 45));
        comboBox.setMaximumSize(new Dimension(250, 45));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleTable(JTable table) {
        table.setBackground(new Color(255, 255, 255, 10));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setGridColor(new Color(255, 255, 255, 30));
        table.setSelectionBackground(new Color(138, 43, 226));
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(140);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(90);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(180);
        
        // Center align some columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(new Color(255, 255, 255, 10));
        centerRenderer.setForeground(Color.WHITE);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        
        // Style header
        table.getTableHeader().setBackground(new Color(138, 43, 226));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 1, true));
        scrollPane.getVerticalScrollBar().setBackground(new Color(255, 255, 255, 20));
        scrollPane.getHorizontalScrollBar().setBackground(new Color(255, 255, 255, 20));
    }

    private void toggleReturnDate() {
        returnDatePanel.setVisible(roundTripCheckbox.isSelected());
        revalidate();
        repaint();
    }
    
    private void searchFlights() {
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
        populateSampleFlightData(from, to);
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
        fromField.setText("Enter departure city");
        fromField.setForeground(new Color(150, 150, 150));
        toField.setText("Enter destination city");
        toField.setForeground(new Color(150, 150, 150));
        dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));
        returnDateSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));
        passengerCount.setSelectedIndex(0);
        roundTripCheckbox.setSelected(false);
        toggleReturnDate();
        tableModel.setRowCount(0);
        busTable.clearSelection();
    }
    
    private void selectTripAndProceed() {
        int selectedRow = busTable.getSelectedRow();
        
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Warning", "Please select a bus trip first!", this);
            return;
        }
        
        // Seçilen satırdan tüm bilgileri al
        String busCompany = (String) tableModel.getValueAt(selectedRow, 0);
        String departureTime = (String) tableModel.getValueAt(selectedRow, 2);
        String arrivalTime = (String) tableModel.getValueAt(selectedRow, 3);
        String price = (String) tableModel.getValueAt(selectedRow, 5);
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
        int passengerCountInt = Integer.parseInt(passengerCountStr.replace("+", ""));
        
        // Proceed to BusSeatSelectionPage
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
            ex.printStackTrace();
        }
    }
}
*/