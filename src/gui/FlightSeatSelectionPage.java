package gui;

import command.CommandInvoker;
import factory.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import models.*;
import observer.Observer;
import observer.SeatManager;
import repository.*;
import service.*;
import singleton.*;
import state.*;

public class FlightSeatSelectionPage extends BasePanel implements Observer {
    private String airline;
    private String fromAirport;
    private String toAirport;
    private String departureTime;
    private String arrivalTime;
    private String basePrice;
    private int passengerCount;
    private String aircraft;
    private String flightClass;

    private JPanel seatMapPanel;
    private JLabel selectedSeatsLabel;
    private JLabel totalPriceLabel;
    private JButton confirmButton;
    private JButton backButton;

    private List<FlightSeatButton> selectedSeats;
    private List<FlightSeatButton> allSeats;
    
    private double basePriceValue;

    private SeatManager seatManager;
    private SeatStatusManager seatStatusManager;

    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private TripRepository flightRepository;
    private ReservationService reservationService;
    private CommandInvoker commandInvoker;

    private List<Integer> preReservedSeats;

    public FlightSeatSelectionPage(String airline, String fromAirport, String toAirport,
                                 String departureTime, String arrivalTime, String basePrice,
                                 int passengerCount, String aircraft, String flightClass) {
        super("Flight Seat Selection", 1400, 800);
        this.airline = airline;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.passengerCount = passengerCount;
        this.aircraft = aircraft;
        this.flightClass = flightClass;
        this.selectedSeats = new ArrayList<>();
        this.allSeats = new ArrayList<>();
        this.seatManager = new SeatManager();

        this.seatManager.addObserver(this);

        try {
            this.basePriceValue = Double.parseDouble(basePrice.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            this.basePriceValue = 280.0;
        }

        initializeServices();
        initializePreReservedSeats();
    }

    private void initializeServices(){
        this.reservationRepository = new ReservationRepository();
        this.userRepository = UserRepository.getInstance();
        this.flightRepository = TripRepository.getInstance(); 
        this.reservationService = new ReservationService(reservationRepository, userRepository, flightRepository);
        this.commandInvoker = new CommandInvoker(); 
    }

    private void initializePreReservedSeats() {
        seatStatusManager = SeatStatusManager.getInstance();
        String tripKey = airline + "_" + fromAirport + "_" + toAirport + "_" + departureTime;
        Set<Integer> occupiedSeatsForThisFlight = seatStatusManager.getOccupiedSeats(tripKey);
        
        preReservedSeats = new ArrayList<>(occupiedSeatsForThisFlight);
        
        // Add demo data if no occupied seats exist
        if (preReservedSeats.isEmpty()) {
            preReservedSeats = new ArrayList<>();
            if ("First Class".equals(flightClass)) {
                preReservedSeats.add(1);
                preReservedSeats.add(3);
                preReservedSeats.add(5);
            } else if ("Business".equals(flightClass)) {
                preReservedSeats.add(2);
                preReservedSeats.add(6);
                preReservedSeats.add(10);
                preReservedSeats.add(14);
            } else {
                // Economy class demo occupied seats
                preReservedSeats.add(15);
                preReservedSeats.add(18);
                preReservedSeats.add(22);
                preReservedSeats.add(27);
                preReservedSeats.add(33);
                preReservedSeats.add(41);
                preReservedSeats.add(48);
            }
            
            // Save demo seats
            seatStatusManager.markSeatsAsOccupied(tripKey, preReservedSeats);
        }
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
        JPanel headerPanel = createModernHeaderPanel();
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Flight seat map panel with glassmorphism
        createModernFlightSeatMapPanel();
        
        // Sidebar panel with glassmorphism
        JPanel sidebarPanel = createModernSidebarPanel();

        contentPanel.add(seatMapPanel, BorderLayout.CENTER);
        contentPanel.add(sidebarPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createModernHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));

        // Back button
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        backButton = createModernButton("← Back", new Color(108, 92, 231), false);
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backPanel.add(backButton);

        // Title
        JLabel titleLabel = new JLabel("Select Your Seat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Choose the perfect seat for your flight", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Flight info card with glassmorphism
        JPanel infoCard = new JPanel() {
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
        infoCard.setLayout(new GridLayout(1, 5, 30, 0));
        infoCard.setOpaque(false);
        infoCard.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        infoCard.setMaximumSize(new Dimension(1200, 100));

        // Info items
        JPanel airlineInfo = createModernInfoItem("✈️", airline, "Airline");
        JPanel routeInfo = createModernInfoItem("📍", fromAirport + " → " + toAirport, "Route");
        JPanel timeInfo = createModernInfoItem("🕒", departureTime + " - " + arrivalTime, "Flight Time");
        JPanel aircraftInfo = createModernInfoItem("🛩️", aircraft, "Aircraft");
        JPanel passengerInfo = createModernInfoItem("👥", passengerCount + " passenger(s)", "Passengers");

        infoCard.add(airlineInfo);
        infoCard.add(routeInfo);
        infoCard.add(timeInfo);
        infoCard.add(aircraftInfo);
        infoCard.add(passengerInfo);

        headerPanel.add(backPanel);
        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(30));
        headerPanel.add(infoCard);

        // Back button action
        backButton.addActionListener(e -> {
            dispose();
            new SearchFlightsPage().display();
        });

        return headerPanel;
    }

    private JPanel createModernInfoItem(String icon, String value, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelLabel = new JLabel(label, SwingConstants.CENTER);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelLabel.setForeground(new Color(189, 147, 249));
        labelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(labelLabel);

        return panel;
    }

    private void createModernFlightSeatMapPanel() {
        seatMapPanel = new JPanel(new BorderLayout());
        seatMapPanel.setOpaque(false);

        // Aircraft container with glassmorphism
        JPanel aircraftContainer = new JPanel(new BorderLayout()) {
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
        aircraftContainer.setOpaque(false);
        aircraftContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Aircraft header
        JPanel aircraftHeader = new JPanel(new BorderLayout());
        aircraftHeader.setOpaque(false);

        JLabel aircraftLabel = new JLabel(airline.toUpperCase() + " - " + flightClass.toUpperCase(), SwingConstants.CENTER);
        aircraftLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        aircraftLabel.setForeground(Color.WHITE);

        aircraftHeader.add(aircraftLabel, BorderLayout.CENTER);

        // Seat layout
        JPanel seatsPanel = createModernSeatLayout();

        // Legend
        JPanel legendPanel = createModernLegendPanel();

        aircraftContainer.add(aircraftHeader, BorderLayout.NORTH);
        aircraftContainer.add(seatsPanel, BorderLayout.CENTER);
        aircraftContainer.add(legendPanel, BorderLayout.SOUTH);

        seatMapPanel.add(aircraftContainer, BorderLayout.CENTER);
    }

    private JPanel createModernSeatLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel aircraftLayout = new JPanel();
        aircraftLayout.setLayout(new BoxLayout(aircraftLayout, BoxLayout.Y_AXIS));
        aircraftLayout.setOpaque(false);

        int seatUniqueIdCounter = 1;
        int seatsPerRow = getSeatsPerRow();
        int totalRows = getTotalRows();

        for (int row = 0; row < totalRows; row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
            rowPanel.setOpaque(false);

            JLabel rowLabel = new JLabel(String.valueOf(row + 1));
            rowLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            rowLabel.setForeground(new Color(189, 147, 249));
            rowLabel.setPreferredSize(new Dimension(25, 35));
            rowLabel.setHorizontalAlignment(SwingConstants.CENTER);
            rowPanel.add(rowLabel);

            char currentLetter = 'A';
            for (int col = 0; col < seatsPerRow; col++) {
                if (seatsPerRow == 6 && (col == 2 || col == 4)) {
                    JLabel aisleLabel = new JLabel("  ");
                    aisleLabel.setPreferredSize(new Dimension(20, 35));
                    rowPanel.add(aisleLabel);
                }

                String seatLabel = (row + 1) + String.valueOf(currentLetter);
                int currentSeatUniqueId = seatUniqueIdCounter++;

                String tripKey = airline + "_" + fromAirport + "_" + toAirport + "_" + departureTime;
                boolean isOccupied = preReservedSeats.contains(currentSeatUniqueId);

                boolean isWindow = (col == 0 || col == seatsPerRow - 1);
                boolean isAisle = !isWindow && (seatsPerRow == 6 ? (col == 1 || col == 4) : (col == 1 || col == 2));
                boolean isPremium = row < 3 || "Business".equals(flightClass) || "First Class".equals(flightClass);

                FlightSeatButton seat = new FlightSeatButton(seatLabel, currentSeatUniqueId, isOccupied, isWindow, isAisle, isPremium);
                seat.setSeatManager(seatManager);
                allSeats.add(seat);
                rowPanel.add(seat);

                currentLetter++;
            }

            aircraftLayout.add(rowPanel);
        }

        JScrollPane scrollPane = new JScrollPane(aircraftLayout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(255, 255, 255, 5));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setOpaque(false);
        verticalScrollBar.setUI(new ModernScrollBarUI());
        verticalScrollBar.setPreferredSize(new Dimension(12, 0));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setBlockIncrement(64);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        return mainPanel;
    }

    private class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(138, 43, 226, 100);
            this.trackColor = new Color(255, 255, 255, 20);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(thumbColor);
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, 
                            thumbBounds.width - 4, thumbBounds.height - 4, 6, 6);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(trackColor);
            g2.fillRoundRect(trackBounds.x, trackBounds.y, 
                            trackBounds.width, trackBounds.height, 6, 6);
            g2.dispose();
        }
    }

    private int getSeatsPerRow() {
        if ("First Class".equals(flightClass)) {
            return 4; // 2-2 configuration
        } else if ("Business".equals(flightClass)) {
            return 4; // 2-2 configuration
        } else {
            return aircraft.contains("737") || aircraft.contains("A320") ? 6 : 6; // 3-3 configuration
        }
    }

    private int getTotalRows() {
        if ("First Class".equals(flightClass)) {
            return 6;
        } else if ("Business".equals(flightClass)) {
            return 8;
        } else {
            return 25; // Economy
        }
    }

    private JPanel createModernLegendPanel() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        legendPanel.setOpaque(false);
        legendPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel availableItem = createModernLegendItem("Available", new Color(75, 181, 67), "1A");
        JPanel selectedItem = createModernLegendItem("Selected", new Color(138, 43, 226), "1A");
        JPanel occupiedItem = createModernLegendItem("Occupied", new Color(220, 53, 69), "X");
        JPanel premiumItem = createModernLegendItem("Premium (+50%)", new Color(255, 193, 7), "P1");
        JPanel windowItem = createModernLegendItem("Window (+20%)", new Color(52, 152, 219), "W1");

        legendPanel.add(availableItem);
        legendPanel.add(selectedItem);
        legendPanel.add(occupiedItem);
        legendPanel.add(premiumItem);
        legendPanel.add(windowItem);

        return legendPanel;
    }

    private JPanel createModernLegendItem(String label, Color color, String seatText) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        item.setOpaque(false);

        JButton sampleSeat = new JButton(seatText) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        sampleSeat.setForeground(isLightColor(color) ? Color.BLACK : Color.WHITE);
        sampleSeat.setFont(new Font("Segoe UI", Font.BOLD, 10));
        sampleSeat.setPreferredSize(new Dimension(35, 30));
        sampleSeat.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1, true));
        sampleSeat.setContentAreaFilled(false);
        sampleSeat.setBorderPainted(true);
        sampleSeat.setFocusPainted(false);
        sampleSeat.setOpaque(false);
        sampleSeat.setEnabled(false);

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelText.setForeground(Color.WHITE);

        item.add(sampleSeat);
        item.add(labelText);

        return item;
    }

    private JPanel createModernSidebarPanel() {
        JPanel sidebar = new JPanel() {
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
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);
        sidebar.setPreferredSize(new Dimension(320, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        // Selection info
        selectedSeatsLabel = new JLabel("No seat selected");
        selectedSeatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectedSeatsLabel.setForeground(new Color(189, 147, 249));
        selectedSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel needSeatsLabel = new JLabel("Need " + passengerCount + " seat(s)");
        needSeatsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        needSeatsLabel.setForeground(Color.WHITE);
        needSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Price display
        totalPriceLabel = new JLabel("Total: $0.00");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalPriceLabel.setForeground(new Color(138, 43, 226));
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        confirmButton = createModernButton("Confirm Selection", new Color(138, 43, 226), true);
        confirmButton.setEnabled(false);
        confirmButton.setMaximumSize(new Dimension(280, 50));

        JButton clearButton = createModernButton("Clear Selection", new Color(108, 92, 231), false);
        clearButton.setMaximumSize(new Dimension(280, 50));

        // Features panel
        JPanel featuresPanel = createModernFeaturesPanel();

        // Layout
        sidebar.add(Box.createVerticalStrut(25));
        sidebar.add(selectedSeatsLabel);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(needSeatsLabel);
        sidebar.add(Box.createVerticalStrut(25));
        sidebar.add(totalPriceLabel);
        sidebar.add(Box.createVerticalStrut(35));
        sidebar.add(confirmButton);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(clearButton);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(featuresPanel);

        // Button actions
        clearButton.addActionListener(e -> clearSelection());
        confirmButton.addActionListener(e -> confirmReservation());

        return sidebar;
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
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return button;
    }

    private JPanel createModernFeaturesPanel() {
        JPanel panel = new JPanel() {
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Flight Features");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] features = {
            "• In-flight entertainment",
            "• Complimentary refreshments",
            "• Professional cabin crew",
            "• Modern aircraft",
            "• Safety certified"
        };

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            featureLabel.setForeground(new Color(189, 147, 249));
            featureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(featureLabel);
            panel.add(Box.createVerticalStrut(5));
        }

        return panel;
    }

    private boolean isLightColor(Color color) {
        int yiq = ((color.getRed() * 299) +
                   (color.getGreen() * 587) +
                   (color.getBlue() * 114)) / 1000;
        return yiq >= 180;
    }

    private void clearSelection() {
        for (FlightSeatButton seat : selectedSeats) {
            seat.setSelected(false);
        }
        selectedSeats.clear();
        updateSelectionInfo();
    }

    @Override
    public void update() {
        updateSelectionInfo();
    }

    private void updateSelectionInfo() {
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("No seat selected");
            totalPriceLabel.setText("Total: $0.00");
            confirmButton.setEnabled(false);
        } else {
            StringBuilder seatNumbers = new StringBuilder();
            double totalPrice = 0;

            for (int i = 0; i < selectedSeats.size(); i++) {
                if (i > 0) seatNumbers.append(", ");
                seatNumbers.append(selectedSeats.get(i).getSeatLabel());
                totalPrice += selectedSeats.get(i).getPrice();
            }

            selectedSeatsLabel.setText("Seats: " + seatNumbers.toString());
            totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));

            confirmButton.setEnabled(selectedSeats.size() == passengerCount);
        }
    }
    
    private void confirmReservation() {
        if (selectedSeats.size() != passengerCount) {
            PageComponents.showStyledMessage("Error", 
                "Please select exactly " + passengerCount + " seat(s)!", this);
            return;
        }

        User currentUser = SessionManager.getInstance().getLoggedInUser();
        if (currentUser == null) {
            PageComponents.showStyledMessage("Error", "Please login first!", this);
            return;
        }

        try {
            // Create a proper FlightTrip using the factory pattern
            TripFactoryManager factoryManager = new TripFactoryManager();
            TripFactory flightFactory = factoryManager.getFactory("Flight");
            
            // Generate unique trip ID for this reservation
            String tripId = "FLIGHT_" + java.util.UUID.randomUUID().toString().substring(0, 8);
            
            // Create the flight trip with proper details
            Trip flightTrip = flightFactory.createTrip(
                tripId,
                fromAirport,
                toAirport,
                java.time.LocalDateTime.now().plusDays(1), // departure time
                java.time.LocalDateTime.now().plusDays(1).plusHours(2), // arrival time
                basePriceValue,
                150, // total seats for flight
                airline,
                "2h 0m", // duration
                "In-flight entertainment, Meal service",
                aircraft // flight number/aircraft
            );
            
            // Seçilen koltukların benzersiz tam sayı ID'lerini topla
            List<Integer> selectedSeatUniqueIds = new ArrayList<>();
            for (FlightSeatButton seatButton : selectedSeats) {
                selectedSeatUniqueIds.add(seatButton.getSeatId());
            }

            // Bu koltukları SeatStatusManager'da rezerve edilmiş olarak işaretle
            String tripKey = airline + "_" + fromAirport + "_" + toAirport + "_" + departureTime;
            SeatStatusManager.getInstance().markSeatsAsOccupied(tripKey, selectedSeatUniqueIds);

            // Create list of selected seats from the flight trip
            List<Seat> reservedSeats = new ArrayList<>();
            for (FlightSeatButton seatButton : selectedSeats) {
                // For flights, we need to create seats based on seat labels
                String seatLabel = seatButton.getSeatLabel();
                // Extract seat number from label (e.g., "1A" -> 1)
                int seatNo = Integer.parseInt(seatLabel.replaceAll("[^0-9]", ""));
                
                // Create new seat
                Seat seat = new Seat(seatNo, false);
                seat.reserve(); // Reserve the seat
                reservedSeats.add(seat);
            }
            
            // Generate unique reservation ID
            String reservationId = java.util.UUID.randomUUID().toString();
            
            // Create reservation with the FlightTrip
            Reservation reservation = new Reservation(reservationId, currentUser, flightTrip, reservedSeats);
            
            // Get the global reservation repository instance
            ReservationRepository globalReservationRepo = getGlobalReservationRepository();
            
            // Save reservation to the global repository
            globalReservationRepo.save(reservation);
            
            // Create reservation state context
            ReservationContext reservationContext = new ReservationContext(reservationId);
            reservationContext.confirm(); // Confirm the reservation
            
            // Calculate total price
            double totalPrice = 0;
            for (FlightSeatButton seat : selectedSeats) {
                totalPrice += seat.getPrice();
            }
            
            // Show success message
            String successMessage = String.format(
                "🎉 Flight Reservation Confirmed!\n\n" +
                "Reservation ID: %s\n" +
                "Trip Type: Flight\n" +
                "Flight: %s → %s\n" +
                "Airline: %s\n" +
                "Aircraft: %s\n" +
                "Seats: %s\n" +
                "Total Price: $%.2f\n\n" +
                "Your flight reservation has been saved successfully!",
                reservationId,
                flightTrip.getStartPoint(),
                flightTrip.getEndPoint(),
                airline,
                aircraft,
                selectedSeats.stream()
                    .map(seat -> seat.getSeatLabel())
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse(""),
                totalPrice
            );
            
            PageComponents.showStyledMessage("Flight Reservation Successful!", successMessage, this);
            
            // Navigate back to main menu
            dispose();
            new MainMenuPage().display();
            
        } catch (Exception e) {
            PageComponents.showStyledMessage("Error", 
                "Failed to create flight reservation: " + e.getMessage(), this);
            e.printStackTrace();
        }
    }
    
    // Method to get the global reservation repository instance
    private ReservationRepository getGlobalReservationRepository() {
        return GlobalRepositoryManager.getInstance().getReservationRepository();
    }

    // Modern flight seat button inner class
    private class FlightSeatButton extends JButton {
        private String seatLabel;
        private int seatId;
        private boolean isOccupied;
        private boolean isSelected;
        private boolean isWindow;
        private boolean isAisle;
        private boolean isPremium;
        private double price;
        private SeatManager seatManager;

        public FlightSeatButton(String seatLabel, int seatId, boolean isOccupied, boolean isWindow, boolean isAisle, boolean isPremium) {
            this.seatLabel = seatLabel;
            this.seatId = seatId;
            this.isOccupied = isOccupied;
            this.isSelected = false;
            this.isWindow = isWindow;
            this.isAisle = isAisle;
            this.isPremium = isPremium;
            this.price = calculateSeatPrice();

            setupModernButton();
        }

        public int getSeatId() {
            return seatId;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background color
            if (isOccupied) {
                g2.setColor(new Color(220, 53, 69)); // Red
            } else if (isSelected) {
                g2.setColor(new Color(138, 43, 226)); // Purple
            } else if (isPremium) {
                g2.setColor(new Color(255, 193, 7)); // Yellow
            } else if (isWindow) {
                g2.setColor(new Color(52, 152, 219)); // Blue
            } else {
                g2.setColor(new Color(75, 181, 67)); // Green
            }
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.dispose();

            super.paintComponent(g);
        }

        public void setSeatManager(SeatManager seatManager) {
            this.seatManager = seatManager;
        }

        private void setupModernButton() {
            setPreferredSize(new Dimension(40, 35));
            setFont(new Font("Segoe UI", Font.BOLD, 10));
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);

            if (isOccupied) {
                setText("X");
                setForeground(Color.WHITE);
                setEnabled(false);
                setToolTipText("Seat " + seatLabel + " occupied");
            } else {
                setText(seatLabel);
                setForeground(Color.WHITE);
                
                String tooltip = String.format("Seat %s - $%.2f", seatLabel, price);
                if (isWindow) tooltip += " (Window)";
                if (isAisle) tooltip += " (Aisle)";
                if (isPremium) tooltip += " (Premium)";
                
                setToolTipText(tooltip);
                addActionListener(e -> toggleSelection());
            }

            setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1, true));
        }

        private double calculateSeatPrice() {
            double multiplier = 1.0;

            if (isPremium) multiplier += 0.5;
            if (isWindow) multiplier += 0.2;
            if (isAisle) multiplier += 0.1;
            return basePriceValue * multiplier;
        }
        
        private void toggleSelection() {
            if (isSelected) {
                setSelected(false);
                selectedSeats.remove(this);
            } else {
                if (selectedSeats.size() >= passengerCount) {
                    PageComponents.showStyledMessage("Warning",
                        "You can only select " + passengerCount + " seat(s)!",
                        FlightSeatSelectionPage.this);
                    return;
                }
                setSelected(true);
                selectedSeats.add(this);
            }
            if (seatManager != null) {
                seatManager.update();
            }
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;

            if (selected) {
                setBackground(new Color(138, 43, 226));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createLineBorder(new Color(189, 147, 249), 2, true));
            } else {
                setBackground(isPremium ? new Color(255, 193, 7) : new Color(75, 181, 67));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1, true));
            }
        }

        public String getSeatLabel() {
            return seatLabel;
        }

        public double getPrice() {
            return price;
        }
    }
}