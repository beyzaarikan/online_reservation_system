// online_reservation_system/src/gui/TripManagementPage.java
package gui;

import factory.BusTripFactory;
import factory.FlightTripFactory;
import factory.TripFactoryManager;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.BusTrip;
import models.FlightTrip;
import models.Trip;
import repository.TripRepository;
import service.TripService;

public class TripManagementPage extends BasePanel {
    private JTable tripTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> tripTypeCombo;
    private JComboBox<String> statusCombo;
    
    // Services and Repositories
    private TripRepository tripRepository;
    private TripService tripService;
    private TripFactoryManager factoryManager;

    public TripManagementPage() {
        super("Trip Management - Admin Panel", 1000, 700);
        // Initialize repositories and services
        this.tripRepository = new TripRepository();
        this.tripService = new TripService(tripRepository);
        this.factoryManager = new TripFactoryManager(); // Initialize factory manager
        initializeSampleTripData(); // Load sample data using the service
    }

    // Initialize with sample data using Factory pattern
    private void initializeSampleTripData() {
        if (tripRepository.findAll().isEmpty()) { // Only add if repository is empty
            try {
                // Register factories (ensure they are registered before use)
                factoryManager.registerFactory("Bus", new BusTripFactory());
                factoryManager.registerFactory("Flight", new FlightTripFactory());

                Trip busTrip1 = factoryManager.getFactory("Bus").createTrip( // Create BusTrip using factory
                        "BT001", "Istanbul", "Ankara",
                        LocalDateTime.of(2025, 6, 15, 9, 0),
                        LocalDateTime.of(2025, 6, 15, 15, 0),
                        45.0, 40, "Metro Turizm", "6h", "AC, WiFi", "BusNo001");
                tripService.addTrip(busTrip1);

                Trip busTrip2 = factoryManager.getFactory("Bus").createTrip( // Create BusTrip using factory
                        "BT002", "Ankara", "Izmir",
                        LocalDateTime.of(2025, 6, 16, 10, 0),
                        LocalDateTime.of(2025, 6, 16, 18, 0),
                        50.0, 40, "Varan Turizm", "8h", "AC", "BusNo002");
                tripService.addTrip(busTrip2);

                Trip flightTrip1 = factoryManager.getFactory("Flight").createTrip( // Create FlightTrip using factory
                        "FT001", "Istanbul", "London",
                        LocalDateTime.of(2025, 6, 20, 10, 0),
                        LocalDateTime.of(2025, 6, 20, 13, 0),
                        300.0, 150, "Turkish Airlines", "3h", "Meals", "FlightNo001");
                tripService.addTrip(flightTrip1);

                Trip flightTrip2 = factoryManager.getFactory("Flight").createTrip( // Create FlightTrip using factory
                        "FT002", "London", "Istanbul",
                        LocalDateTime.of(2025, 6, 22, 14, 0),
                        LocalDateTime.of(2025, 6, 22, 19, 0),
                        280.0, 150, "Pegasus Airlines", "5h", "No-frills", "FlightNo002");
                tripService.addTrip(flightTrip2);

            } catch (Exception e) {
                System.err.println("Error initializing sample data: " + e.getMessage());
            }
        }
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
        tripTypeCombo = new JComboBox<>(new String[]{"All Types", "Bus", "Flight"});
        statusCombo = new JComboBox<>(new String[]{"All Status", "Active", "Inactive", "Cancelled"});

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

        // Populate table with data from repository
        populateTripTable();

        // Action listeners
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage(true).display();
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
                super.paintComponent(g);
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
    
    private void populateTripTable() { // Renamed from populateSampleTripData
        tableModel.setRowCount(0); // Clear existing data
        List<Trip> allTrips = tripService.getAllTrips(); // Get all trips from the service
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Trip trip : allTrips) {
            String id = trip.getTripNo(); // Get trip number
            String type = trip.getTripType(); // Get trip type (Bus or Flight)
            String route = trip.getStartPoint() + " → " + trip.getEndPoint(); // Get start and end points
            String date = trip.getDepartureTime().format(dateFormatter); // Format departure date
            String time = trip.getDepartureTime().format(timeFormatter); // Format departure time
            String price = String.format("$%.2f", trip.getBasePrice()); // Format base price
            int availableSeats = tripService.findAvailableSeats(trip.getTripNo()).size(); // Get available seats
            String status = "Active"; // Assuming all initialized trips are active for now, update if a status field is added to Trip model

            tableModel.addRow(new Object[]{id, type, route, date, time, price, availableSeats + "/" + trip.getTotalSeats(), status}); // Add row to table model
        }
    }
    
    private void searchTrips() {
        String searchTermRaw = searchField.getText().trim();
        String selectedType = (String) tripTypeCombo.getSelectedItem();
        String selectedStatus = (String) statusCombo.getSelectedItem();
        
        final String searchTerm;
        if (searchTermRaw.equals("Search trips...")) {
            searchTerm = "";
        } else {
            searchTerm = searchTermRaw;
        }
        
        // Clear previous results
        tableModel.setRowCount(0);

        List<Trip> filteredTrips = tripService.getAllTrips();

        // Apply search term filter
        if (!searchTerm.isEmpty()) {
            final String searchTermFinal = searchTerm;
            filteredTrips = filteredTrips.stream()
                    .filter(trip -> trip.getTripNo().toLowerCase().contains(searchTermFinal.toLowerCase()) ||
                            trip.getStartPoint().toLowerCase().contains(searchTermFinal.toLowerCase()) ||
                            trip.getEndPoint().toLowerCase().contains(searchTermFinal.toLowerCase()) ||
                            trip.getCompany().toLowerCase().contains(searchTermFinal.toLowerCase()))
                    .collect(java.util.stream.Collectors.toList());
        }

        // Apply type filter
        if (!selectedType.equals("All Types")) {
            filteredTrips = filteredTrips.stream()
                    .filter(trip -> trip.getTripType().equals(selectedType))
                    .collect(java.util.stream.Collectors.toList());
        }
        
        // Status filtering is more complex without a 'status' field in Trip model.
        // For demonstration, let's assume if availableSeats == 0, status is "Cancelled".
        // In a real application, Trip model should have a proper status enum/field.
        if (!selectedStatus.equals("All Status")) {
            if (selectedStatus.equals("Cancelled")) {
                filteredTrips = filteredTrips.stream()
                        .filter(trip -> tripService.findAvailableSeats(trip.getTripNo()).isEmpty())
                        .collect(java.util.stream.Collectors.toList());
            } else if (selectedStatus.equals("Active")) {
                filteredTrips = filteredTrips.stream()
                        .filter(trip -> !tripService.findAvailableSeats(trip.getTripNo()).isEmpty())
                        .collect(java.util.stream.Collectors.toList());
            }
            // "Inactive" status not easily derivable without more trip data.
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Trip trip : filteredTrips) {
            String id = trip.getTripNo();
            String type = trip.getTripType();
            String route = trip.getStartPoint() + " → " + trip.getEndPoint();
            String date = trip.getDepartureTime().format(dateFormatter);
            String time = trip.getDepartureTime().format(timeFormatter);
            String price = String.format("$%.2f", trip.getBasePrice());
            int availableSeats = tripService.findAvailableSeats(trip.getTripNo()).size();
            String status = availableSeats == 0 ? "Cancelled" : "Active"; // Simplified status derivation

            tableModel.addRow(new Object[]{id, type, route, date, time, price, availableSeats + "/" + trip.getTotalSeats(), status});
        }
        
        String filterInfo = "Searching with filters:\n" +
                           "Search Term: " + (searchTerm.isEmpty() ? "None" : searchTerm) + "\n" +
                           "Type: " + selectedType + "\n" +
                           "Status: " + selectedStatus;
        
        PageComponents.showStyledMessage("Search Applied", filterInfo, this); // Corrected parent to 'this'
    }
    
    private void resetFilters() {
        searchField.setText("Search trips...");
        searchField.setForeground(new Color(150, 150, 150));
        tripTypeCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        
        populateTripTable(); // Reload all data
        
        PageComponents.showStyledMessage("Success", "Filters reset successfully!", this); // Corrected parent to 'this'
    }
    
    private void addNewTrip() {
        JDialog addTripDialog = new JDialog(this, "Add New Trip", true);
        addTripDialog.setSize(450, 550);
        addTripDialog.setLocationRelativeTo(this);
        addTripDialog.setLayout(new BorderLayout());
        addTripDialog.getContentPane().setBackground(new Color(25, 25, 55));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(25, 25, 55));

        JTextField tripNoField = new JTextField();
        JTextField startPointField = new JTextField();
        JTextField endPointField = new JTextField();
        JSpinner departureSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor depEditor = new JSpinner.DateEditor(departureSpinner, "yyyy-MM-dd HH:mm");
        departureSpinner.setEditor(depEditor);

        JSpinner arrivalSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor arrEditor = new JSpinner.DateEditor(arrivalSpinner, "yyyy-MM-dd HH:mm");
        arrivalSpinner.setEditor(arrEditor);

        JTextField priceField = new JTextField();
        JTextField totalSeatsField = new JTextField();
        JTextField companyField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField amenitiesField = new JTextField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Bus", "Flight"});
        JTextField vehicleNoField = new JTextField(); // For busNo or flightNo

        formPanel.add(new JLabel("Trip No:"));
        formPanel.add(tripNoField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCombo);
        formPanel.add(new JLabel("Start Point:"));
        formPanel.add(startPointField);
        formPanel.add(new JLabel("End Point:"));
        formPanel.add(endPointField);
        formPanel.add(new JLabel("Departure Time:"));
        formPanel.add(departureSpinner);
        formPanel.add(new JLabel("Arrival Time:"));
        formPanel.add(arrivalSpinner);
        formPanel.add(new JLabel("Base Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Total Seats:"));
        formPanel.add(totalSeatsField);
        formPanel.add(new JLabel("Company:"));
        formPanel.add(companyField);
        formPanel.add(new JLabel("Duration:"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Amenities:"));
        formPanel.add(amenitiesField);
        formPanel.add(new JLabel("Vehicle No (Bus/Flight):"));
        formPanel.add(vehicleNoField);

        // Style form components
        for (Component comp : formPanel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
                ((JLabel) comp).setFont(new Font("Segoe UI", Font.BOLD, 12));
            } else if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                field.setBackground(new Color(60, 63, 65));
                field.setForeground(Color.WHITE);
                field.setCaretColor(Color.WHITE);
                field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            } else if (comp instanceof JSpinner) {
                JSpinner spinner = (JSpinner) comp;
                spinner.setBackground(new Color(60, 63, 65));
                spinner.setForeground(Color.WHITE);
                JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
                tf.setBackground(new Color(60, 63, 65));
                tf.setForeground(Color.WHITE);
                tf.setCaretColor(Color.WHITE);
            } else if (comp instanceof JComboBox) {
                JComboBox<?> combo = (JComboBox<?>) comp;
                combo.setBackground(new Color(60, 63, 65));
                combo.setForeground(Color.WHITE);
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(25, 25, 55));
        JButton saveButton = createModernButton("Save Trip", new Color(52, 152, 219), true);
        JButton cancelButton = createModernButton("Cancel", new Color(231, 76, 60), false);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        addTripDialog.add(new JLabel("Add New Trip", SwingConstants.CENTER) {{
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        }}, BorderLayout.NORTH);
        addTripDialog.add(formPanel, BorderLayout.CENTER);
        addTripDialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            try {
                String tripNo = tripNoField.getText();
                String startPoint = startPointField.getText();
                String endPoint = endPointField.getText();
                
                // Convert Date from JSpinner to LocalDateTime
                Date depDate = (Date) departureSpinner.getValue();
                LocalDateTime departureTime = depDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                
                Date arrDate = (Date) arrivalSpinner.getValue();
                LocalDateTime arrivalTime = arrDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                
                double price = Double.parseDouble(priceField.getText());
                int totalSeats = Integer.parseInt(totalSeatsField.getText());
                String company = companyField.getText();
                String duration = durationField.getText();
                String amenities = amenitiesField.getText();
                String vehicleNo = vehicleNoField.getText();
                String tripType = (String) typeCombo.getSelectedItem();

                if (tripNo.isEmpty() || startPoint.isEmpty() || endPoint.isEmpty() || company.isEmpty() ||
                    duration.isEmpty() || amenities.isEmpty() || vehicleNo.isEmpty()) {
                    PageComponents.showStyledMessage("Error", "All fields must be filled!", this);
                    return;
                }

                // Use the factory to create the trip based on selected type
                Trip newTrip = factoryManager.getFactory(tripType).createTrip( // Create trip using factory
                    tripNo, startPoint, endPoint, departureTime, arrivalTime,
                    price, totalSeats, company, duration, amenities, vehicleNo
                );

                tripService.addTrip(newTrip); // Add trip using the service
                populateTripTable(); // Refresh the table
                PageComponents.showStyledMessage("Success", "Trip added successfully!", this); // Corrected parent to 'this'
                this.dispose();
            } catch (NumberFormatException ex) {
                PageComponents.showStyledMessage("Error", "Price and Total Seats must be valid numbers!", this);
            } catch (IllegalArgumentException ex) {
                PageComponents.showStyledMessage("Error", ex.getMessage(), this);
            } catch (Exception ex) {
                PageComponents.showStyledMessage("Error", "An unexpected error occurred: " + ex.getMessage(), this);
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> addTripDialog.dispose());

        addTripDialog.setVisible(true);
    }
    
    private void editTrip() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a trip to edit!", this);
            return;
        }

        String tripId = (String) tableModel.getValueAt(selectedRow, 0); // Get trip ID from table
        Trip existingTrip = tripService.findTripByNo(tripId); // Find trip by ID

        if (existingTrip == null) {
            PageComponents.showStyledMessage("Error", "Selected trip not found in system!", this);
            return;
        }

        JDialog editTripDialog = new JDialog(this, "Edit Trip", true);
        editTripDialog.setSize(450, 550);
        editTripDialog.setLocationRelativeTo(this);
        editTripDialog.setLayout(new BorderLayout());
        editTripDialog.getContentPane().setBackground(new Color(25, 25, 55));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(25, 25, 55));

        // Populate fields with existing trip data
        JTextField tripNoField = new JTextField(existingTrip.getTripNo());
        tripNoField.setEditable(false); // Trip number should not be editable

        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Bus", "Flight"});
        typeCombo.setSelectedItem(existingTrip.getTripType());

        JTextField startPointField = new JTextField(existingTrip.getStartPoint());
        JTextField endPointField = new JTextField(existingTrip.getEndPoint());

        JSpinner departureSpinner = new JSpinner(new SpinnerDateModel(
            java.util.Date.from(existingTrip.getDepartureTime().atZone(java.time.ZoneId.systemDefault()).toInstant()),
            null, null, java.util.Calendar.MINUTE));
        JSpinner.DateEditor depEditor = new JSpinner.DateEditor(departureSpinner, "yyyy-MM-dd HH:mm");
        departureSpinner.setEditor(depEditor);

        JSpinner arrivalSpinner = new JSpinner(new SpinnerDateModel(
            java.util.Date.from(existingTrip.getArrivalTime().atZone(java.time.ZoneId.systemDefault()).toInstant()),
            null, null, java.util.Calendar.MINUTE));
        JSpinner.DateEditor arrEditor = new JSpinner.DateEditor(arrivalSpinner, "yyyy-MM-dd HH:mm");
        arrivalSpinner.setEditor(arrEditor);

        JTextField priceField = new JTextField(String.valueOf(existingTrip.getBasePrice()));
        JTextField totalSeatsField = new JTextField(String.valueOf(existingTrip.getTotalSeats()));
        JTextField companyField = new JTextField(existingTrip.getCompany());
        JTextField durationField = new JTextField(existingTrip.getDuration());
        JTextField amenitiesField = new JTextField(existingTrip.getAmentities());

        JTextField vehicleNoField = new JTextField();
        if (existingTrip instanceof BusTrip) {
            vehicleNoField.setText(((BusTrip) existingTrip).getBusNo());
        } else if (existingTrip instanceof FlightTrip) {
            vehicleNoField.setText(((FlightTrip) existingTrip).getFlightNo());
        }

        formPanel.add(new JLabel("Trip No:"));
        formPanel.add(tripNoField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCombo);
        formPanel.add(new JLabel("Start Point:"));
        formPanel.add(startPointField);
        formPanel.add(new JLabel("End Point:"));
        formPanel.add(endPointField);
        formPanel.add(new JLabel("Departure Time:"));
        formPanel.add(departureSpinner);
        formPanel.add(new JLabel("Arrival Time:"));
        formPanel.add(arrivalSpinner);
        formPanel.add(new JLabel("Base Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Total Seats:"));
        formPanel.add(totalSeatsField);
        formPanel.add(new JLabel("Company:"));
        formPanel.add(companyField);
        formPanel.add(new JLabel("Duration:"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Amenities:"));
        formPanel.add(amenitiesField);
        formPanel.add(new JLabel("Vehicle No (Bus/Flight):"));
        formPanel.add(vehicleNoField);

        // Style form components (similar to add)
        for (Component comp : formPanel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setForeground(Color.WHITE);
                ((JLabel) comp).setFont(new Font("Segoe UI", Font.BOLD, 12));
            } else if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                field.setBackground(new Color(60, 63, 65));
                field.setForeground(Color.WHITE);
                field.setCaretColor(Color.WHITE);
                field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            } else if (comp instanceof JSpinner) {
                JSpinner spinner = (JSpinner) comp;
                spinner.setBackground(new Color(60, 63, 65));
                spinner.setForeground(Color.WHITE);
                JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
                tf.setBackground(new Color(60, 63, 65));
                tf.setForeground(Color.WHITE);
                tf.setCaretColor(Color.WHITE);
            } else if (comp instanceof JComboBox) {
                JComboBox<?> combo = (JComboBox<?>) comp;
                combo.setBackground(new Color(60, 63, 65));
                combo.setForeground(Color.WHITE);
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(25, 25, 55));
        JButton saveButton = createModernButton("Save Changes", new Color(52, 152, 219), true);
        JButton cancelButton = createModernButton("Cancel", new Color(231, 76, 60), false);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        editTripDialog.add(new JLabel("Edit Trip", SwingConstants.CENTER) {{
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        }}, BorderLayout.NORTH);
        editTripDialog.add(formPanel, BorderLayout.CENTER);
        editTripDialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            try {
                String newStartPoint = startPointField.getText();
                String newEndPoint = endPointField.getText();
                
                Date depDate = (Date) departureSpinner.getValue();
                LocalDateTime newDepartureTime = depDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                
                Date arrDate = (Date) arrivalSpinner.getValue();
                LocalDateTime newArrivalTime = arrDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                
                double newPrice = Double.parseDouble(priceField.getText());
                int newTotalSeats = Integer.parseInt(totalSeatsField.getText());
                String newCompany = companyField.getText();
                String newDuration = durationField.getText();
                String newAmenities = amenitiesField.getText();
                String newVehicleNo = vehicleNoField.getText();
                String newTripType = (String) typeCombo.getSelectedItem();

                if (newStartPoint.isEmpty() || newEndPoint.isEmpty() || newCompany.isEmpty() ||
                    newDuration.isEmpty() || newAmenities.isEmpty() || newVehicleNo.isEmpty()) {
                    PageComponents.showStyledMessage("Error", "All fields must be filled!", this);
                    return;
                }

                Trip updatedTrip;
                if ("Bus".equals(newTripType)) {
                    updatedTrip = factoryManager.getFactory("Bus").createTrip(existingTrip.getTripNo(),
                            newStartPoint, newEndPoint, newDepartureTime, newArrivalTime, newPrice,
                            newTotalSeats, newCompany, newDuration, newAmenities, newVehicleNo);
                } else { // Flight
                    updatedTrip = factoryManager.getFactory("Flight").createTrip(existingTrip.getTripNo(),
                            newStartPoint, newEndPoint, newDepartureTime, newArrivalTime, newPrice,
                            newTotalSeats, newCompany, newDuration, newAmenities, newVehicleNo);
                }
                
                tripService.updateTrip(updatedTrip); // Update trip using the service
                populateTripTable(); // Refresh the table
                PageComponents.showStyledMessage("Success", "Trip updated successfully!", this); // Corrected parent to 'this'
                editTripDialog.dispose();
            } catch (NumberFormatException ex) {
                PageComponents.showStyledMessage("Error", "Price and Total Seats must be valid numbers!", this);
            } catch (IllegalArgumentException ex) {
                PageComponents.showStyledMessage("Error", ex.getMessage(), this);
            } catch (Exception ex) {
                PageComponents.showStyledMessage("Error", "An unexpected error occurred: " + ex.getMessage(), this);
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> editTripDialog.dispose());

        editTripDialog.setVisible(true);
    }
    
    private void viewTripDetails() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a trip to view details!", this);
            return;
        }
        
        String tripId = (String) tableModel.getValueAt(selectedRow, 0); // Get trip ID from table
        Trip trip = tripService.findTripByNo(tripId); // Find trip by ID

        if (trip == null) {
            PageComponents.showStyledMessage("Error", "Selected trip not found in system!", this);
            return;
        }

        String details = "Trip Details:\n\n" +
                        "ID: " + trip.getTripNo() + "\n" +
                        "Type: " + trip.getTripType() + "\n" +
                        "Route: " + trip.getStartPoint() + " → " + trip.getEndPoint() + "\n" +
                        "Departure: " + trip.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" +
                        "Arrival: " + trip.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n" +
                        "Price: " + String.format("$%.2f", trip.getBasePrice()) + "\n" +
                        "Total Seats: " + trip.getTotalSeats() + "\n" +
                        "Available Seats: " + tripService.findAvailableSeats(trip.getTripNo()).size() + "\n" +
                        "Company: " + trip.getCompany() + "\n" +
                        "Duration: " + trip.getDuration() + "\n" +
                        "Amenities: " + trip.getAmentities();
        
        if (trip instanceof BusTrip) {
            details += "\nBus No: " + ((BusTrip) trip).getBusNo();
        } else if (trip instanceof FlightTrip) {
            details += "\nFlight No: " + ((FlightTrip) trip).getFlightNo();
        }

        PageComponents.showStyledMessage("Trip Details", details, this); // Corrected parent to 'this'
    }
    
    private void cancelTrip() {
        int selectedRow = tripTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a trip to cancel!", this);
            return;
        }
        
        String tripId = (String) tableModel.getValueAt(selectedRow, 0); // Get trip ID from table
        Trip tripToCancel = tripService.findTripByNo(tripId); // Find trip by ID

        if (tripToCancel == null) {
            PageComponents.showStyledMessage("Error", "Selected trip not found in system!", this);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to CANCEL this trip?\n\n" +
            "Trip ID: " + tripId + "\n" +
            "Route: " + tripToCancel.getStartPoint() + " → " + tripToCancel.getEndPoint() + "\n\n" +
            "This action cannot be undone!",
            "Confirm Trip Cancellation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                tripService.deleteTrip(tripId); // Delete trip using the service
                populateTripTable(); // Refresh the table
                PageComponents.showStyledMessage("Success", 
                    "Trip " + tripId + " has been cancelled!", this); // Corrected parent to 'this'
            } catch (IllegalArgumentException ex) {
                PageComponents.showStyledMessage("Error", ex.getMessage(), this); // Corrected parent to 'this'
            } catch (Exception ex) {
                PageComponents.showStyledMessage("Error", "An unexpected error occurred: " + ex.getMessage(), this); // Corrected parent to 'this'
                ex.printStackTrace();
            }
        }
    }
    
    private void refreshTripData() {
        populateTripTable(); // Refresh the table with current data
        
        // Reset filters
        searchField.setText("Search trips...");
        searchField.setForeground(new Color(150, 150, 150));
        tripTypeCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        
        PageComponents.showStyledMessage("Success", "Trip data refreshed successfully!", this); // Corrected parent to 'this'
    }
private void showStyledMessage(String title, String message, Window parent) {
    // Create a styled message dialog that matches the theme
    JDialog dialog = new JDialog(parent, title, Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setSize(400, 200);
    dialog.setLocationRelativeTo(parent);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
    
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(new Color(25, 25, 55));
    buttonPanel.add(okButton);
    
    panel.setLayout(new BorderLayout());
    panel.add(messageLabel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
    
    dialog.add(panel);
    dialog.setVisible(true);
}
}