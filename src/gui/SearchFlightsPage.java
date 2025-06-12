package gui;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SearchFlightsPage extends BasePanel {
    private JTextField fromField;
    private JTextField toField;
    private JSpinner dateSpinner;
    private JComboBox<String> passengerCount;
    private JComboBox<String> classType;
    private JCheckBox roundTripCheckbox;
    private JTable flightTable;
    private DefaultTableModel tableModel;
    
    
    public SearchFlightsPage() {
        super("Search Flights - Travel System", 1200, 800);
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

        JLabel titleLabel = new JLabel("✈️ Search Flights", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Discover amazing destinations", SwingConstants.CENTER);
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
        searchFormPanel.setMaximumSize(new Dimension(900, 450));

        // Form title
        JLabel formTitle = new JLabel("Plan Your Flight", SwingConstants.CENTER);
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
        fieldsPanel.add(createFieldPanel("From Airport", fromField = createModernTextField("Enter departure airport")), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        fieldsPanel.add(createFieldPanel("To Airport", toField = createModernTextField("Enter destination airport")), gbc);

        // Date and Passengers - same row
        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(createFieldPanel("Departure Date", dateSpinner = createDateSpinner(), true), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        passengerCount = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6+"});
        styleComboBox(passengerCount);
        fieldsPanel.add(createFieldPanel("Passengers", passengerCount), gbc);

        // Class type and round trip - same row
        gbc.gridx = 0; gbc.gridy = 2;
        classType = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});
        styleComboBox(classType);
        fieldsPanel.add(createFieldPanel("Class", classType), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        JPanel roundTripPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundTripPanel.setOpaque(false);
        roundTripCheckbox = new JCheckBox("Round Trip");
        roundTripCheckbox.setOpaque(false);
        roundTripCheckbox.setForeground(Color.WHITE);
        roundTripCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roundTripPanel.add(roundTripCheckbox);
        fieldsPanel.add(roundTripPanel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        JButton searchButton = createModernButton("🔍 Search Flights", new Color(138, 43, 226), true);
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

        JLabel resultsTitle = new JLabel("Available Flights", SwingConstants.CENTER);
        resultsTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        resultsTitle.setForeground(Color.WHITE);
        resultsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create table
        String[] columnNames = {
            "Airline", "Route", "Departure", "Arrival", "Duration", 
            "Price", "Seats Available", "Aircraft"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        flightTable = new JTable(tableModel);
        styleTable(flightTable);
        
        JScrollPane tableScrollPane = new JScrollPane(flightTable);
        styleScrollPane(tableScrollPane);
        tableScrollPane.setPreferredSize(new Dimension(800, 250));

        // Select and proceed button
        JButton selectButton = createModernButton("Select Flight & Proceed", new Color(138, 43, 226), true);
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
        selectButton.addActionListener(e -> selectFlightAndProceed());
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

    // Overloaded method with centerLabel parameter
    private JPanel createFieldPanel(String labelText, JComponent field, boolean centerLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(189, 147, 249));
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        if (centerLabel) {
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        } else {
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);
        
        return panel;
    }

    // Original method without centerLabel parameter
    private JPanel createFieldPanel(String labelText, JComponent field) {
        return createFieldPanel(labelText, field, false);
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
        textField.setHorizontalAlignment(JTextField.CENTER);
        
        return spinner;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(new Color(255, 255, 255, 15));
        comboBox.setForeground(Color.BLACK);
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

    
    private void searchFlights() {
        String from = fromField.getText();
        String to = toField.getText();
        
        if (from.equals("Enter departure airport") || from.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter departure airport!", this);
            return;
        }
        
        if (to.equals("Enter destination airport") || to.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter destination airport!", this);
            return;
        }
        
        // Clear previous results
        tableModel.setRowCount(0);
        
        // Add sample flight results
        populateSampleFlightData(from, to);
    }
    
    private void populateSampleFlightData(String from, String to) {
        String selectedClass = (String) classType.getSelectedItem();
        
        // Adjust prices based on class
        double baseMultiplier = 1.0;
        if ("Business".equals(selectedClass)) {
            baseMultiplier = 3.0;
        } else if ("First Class".equals(selectedClass)) {
            baseMultiplier = 6.0;
        }
        
        tableModel.addRow(new Object[]{
            "Turkish Airlines", from + " → " + to, "08:30", "11:45", "3h 15m", 
            String.format("$%.0f", 280 * baseMultiplier), "18 seats", "Boeing 737"
        });
        tableModel.addRow(new Object[]{
            "Pegasus Airlines", from + " → " + to, "10:15", "13:30", "3h 15m", 
            String.format("$%.0f", 220 * baseMultiplier), "12 seats", "Airbus A320"
        });
        tableModel.addRow(new Object[]{
            "SunExpress", from + " → " + to, "14:00", "17:15", "3h 15m", 
            String.format("$%.0f", 250 * baseMultiplier), "25 seats", "Boeing 737"
        });
        tableModel.addRow(new Object[]{
            "AtlasGlobal", from + " → " + to, "16:30", "19:45", "3h 15m", 
            String.format("$%.0f", 290 * baseMultiplier), "8 seats", "Airbus A321"
        });
        tableModel.addRow(new Object[]{
            "Onur Air", from + " → " + to, "19:00", "22:15", "3h 15m", 
            String.format("$%.0f", 200 * baseMultiplier), "30 seats", "Boeing 737"
        });
    }
    
    private void clearForm() {
        fromField.setText("Enter departure airport");
        fromField.setForeground(new Color(150, 150, 150));
        toField.setText("Enter destination airport");
        toField.setForeground(new Color(150, 150, 150));
        dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.now()));
        passengerCount.setSelectedIndex(0);
        classType.setSelectedIndex(0);
        roundTripCheckbox.setSelected(false);
        tableModel.setRowCount(0);
        flightTable.clearSelection();
    }
    
    private void selectFlightAndProceed() {
        int selectedRow = flightTable.getSelectedRow();
        
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Warning", "Please select a flight first!", this);
            return;
        }
        
        // Seçilen satırdan tüm bilgileri al
        String airline = (String) tableModel.getValueAt(selectedRow, 0);
        String departureTime = (String) tableModel.getValueAt(selectedRow, 2);
        String arrivalTime = (String) tableModel.getValueAt(selectedRow, 3);
        String price = (String) tableModel.getValueAt(selectedRow, 5);
        String aircraft = (String) tableModel.getValueAt(selectedRow, 7);
        
        // Form bilgilerini al
        String fromAirport = fromField.getText();
        String toAirport = toField.getText();
        
        // Tarih bilgisini al
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String departureDate = dateFormat.format((Date) dateSpinner.getValue());
        
        String passengerCountStr = (String) passengerCount.getSelectedItem();
        int passengerCountInt = Integer.parseInt(passengerCountStr.replace("+", ""));
        
        String selectedClass = (String) classType.getSelectedItem();
        
        // Proceed to FlightSeatSelectionPage
        dispose();
        try {
            FlightSeatSelectionPage seatSelectionPage = new FlightSeatSelectionPage(
                airline, 
                fromAirport, 
                toAirport, 
                departureTime,
                arrivalTime, 
                price, 
                passengerCountInt, 
                aircraft,
                selectedClass
            );
            seatSelectionPage.display();
        } catch (Exception ex) {
            PageComponents.showStyledMessage("Error", 
                "Failed to proceed to seat selection: " + ex.getMessage(), this);
            ex.printStackTrace();
        }
    }
}