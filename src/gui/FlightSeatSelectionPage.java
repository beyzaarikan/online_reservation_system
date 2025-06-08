// package gui;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// public class FlightSeatSelectionPage extends BasePanel {
//     private String airline;
//     private String fromCity;
//     private String toCity;
//     private String date;
//     private String departureTime;
//     private String arrivalTime;
//     private String basePrice;
//     private int passengerCount;
//     private String flightClass;
    
//     private JPanel seatMapPanel;
//     private JLabel selectedSeatsLabel;
//     private JLabel totalPriceLabel;
//     private JButton confirmButton;
//     private JButton backButton;
    
//     private List<FlightSeatButton> selectedSeats;
//     private double basePriceValue;
    
//     public FlightSeatSelectionPage(String airline, String fromCity, String toCity, String date, 
//                                   String departureTime, String arrivalTime, String basePrice, 
//                                   int passengerCount, String flightClass) {
//         super("Flight Seat Selection", 1400, 900);
//         this.airline = airline;
//         this.fromCity = fromCity;
//         this.toCity = toCity;
//         this.date = date;
//         this.departureTime = departureTime;
//         this.arrivalTime = arrivalTime;
//         this.basePrice = basePrice;
//         this.passengerCount = passengerCount;
//         this.flightClass = flightClass;
//         this.selectedSeats = new ArrayList<>();
        
//         // Parse base price
//         try {
//             this.basePriceValue = Double.parseDouble(basePrice.replaceAll("[^\\d.]", ""));
//         } catch (NumberFormatException e) {
//             this.basePriceValue = 150.0;
//         }
//     }
    
//     @Override
//     public void setupUI() {
//         JPanel mainPanel = createMainPanel();
        
//         // Title Panel with trip info
//         JPanel titlePanel = createTripInfoPanel();
        
//         // Main content panel
//         JPanel contentPanel = new JPanel(new BorderLayout());
//         contentPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
//         // Center - Seat Map
//         createFlightSeatMapPanel();
        
//         // Right side - Selection info and controls
//         JPanel rightPanel = createControlPanel();
        
//         contentPanel.add(seatMapPanel, BorderLayout.CENTER);
//         contentPanel.add(rightPanel, BorderLayout.EAST);
        
//         mainPanel.add(titlePanel, BorderLayout.NORTH);
//         mainPanel.add(contentPanel, BorderLayout.CENTER);
//         add(mainPanel);
        
//         setupActionListeners();
//     }
    
//     private JPanel createTripInfoPanel() {
//         JPanel panel = new JPanel(new BorderLayout());
//         panel.setBackground(PageComponents.BACKGROUND_COLOR);
//         panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
//         // Title
//         JLabel titleLabel = new JLabel("✈️ Select Your Flight Seat", SwingConstants.CENTER);
//         titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
//         titleLabel.setForeground(PageComponents.TEXT_COLOR);
        
//         // Trip info panel
//         JPanel infoPanel = createCardPanel();
//         infoPanel.setLayout(new GridLayout(2, 3, 20, 10));
//         infoPanel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
//             BorderFactory.createEmptyBorder(15, 20, 15, 20)
//         ));
        
//         // Trip info labels
//         JLabel airlineLabel = createInfoLabel("✈️ Airline: " + airline);
//         JLabel routeLabel = createInfoLabel("📍 Route: " + fromCity + " → " + toCity);
//         JLabel dateLabel = createInfoLabel("📅 Date: " + date);
//         JLabel timeLabel = createInfoLabel("🕐 Time: " + departureTime + " - " + arrivalTime);
//         JLabel passengerLabel = createInfoLabel("👥 Passengers: " + passengerCount);
//         JLabel classLabel = createInfoLabel("🎫 Class: " + flightClass);
        
//         airlineLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
//         airlineLabel.setForeground(PageComponents.PRIMARY_COLOR);
        
//         infoPanel.add(airlineLabel);
//         infoPanel.add(routeLabel);
//         infoPanel.add(dateLabel);
//         infoPanel.add(timeLabel);
//         infoPanel.add(passengerLabel);
//         infoPanel.add(classLabel);
        
//         panel.add(titleLabel, BorderLayout.NORTH);
//         panel.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
//         panel.add(infoPanel, BorderLayout.SOUTH);
        
//         return panel;
//     }
    
//     private JLabel createInfoLabel(String text) {
//         JLabel label = new JLabel(text);
//         label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         label.setForeground(PageComponents.TEXT_COLOR);
//         return label;
//     }
    
//     private void createFlightSeatMapPanel() {
//         seatMapPanel = createCardPanel();
//         seatMapPanel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
//             BorderFactory.createEmptyBorder(20, 20, 20, 20)
//         ));
//         seatMapPanel.setLayout(new BorderLayout());
        
//         // Flight header
//         JLabel flightLabel = new JLabel("✈️ AIRCRAFT SEAT MAP - " + airline.toUpperCase() + " ✈️", SwingConstants.CENTER);  
//         flightLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
//         flightLabel.setForeground(PageComponents.TEXT_COLOR);

//         JPanel seatsPanel = createFlightSeatLayout();
        
//         // Legend panel
//         JPanel legendPanel = createLegendPanel();
        
//         seatMapPanel.add(flightLabel, BorderLayout.NORTH);
//         seatMapPanel.add(seatsPanel, BorderLayout.CENTER);
//         seatMapPanel.add(legendPanel, BorderLayout.SOUTH);
//     }

//     private JPanel createFlightSeatLayout() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         panel.setBackground(PageComponents.CARD_COLOR);
//         GridBagConstraints gbc = new GridBagConstraints();
        
//         // Driver area
//         gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 5;
//         JPanel driverPanel = new JPanel(new FlowLayout());
//         driverPanel.setBackground(PageComponents.CARD_COLOR);
//         JLabel driverLabel = new JLabel("🚗 DRIVER", SwingConstants.CENTER);
//         driverLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//         driverLabel.setForeground(PageComponents.ACCENT_COLOR);
//         driverLabel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2),
//             BorderFactory.createEmptyBorder(5, 15, 5, 15)
//         ));
//         driverPanel.add(driverLabel);
//         panel.add(driverPanel, gbc);
        
//         // Add space after driver
//         gbc.gridy = 1; gbc.gridheight = 1;
//         panel.add(Box.createVerticalStrut(20), gbc);
        
//         // Bus seats layout (2-2 configuration, 14 rows)
//         Random random = new Random(42); // For consistent random occupied seats
//         int seatNumber = 1;
        
//         for (int row = 0; row < 14; row++) {
//             // Left side seats (Window + Aisle)
//             for (int col = 0; col < 2; col++) {
//                 gbc.gridx = col;
//                 gbc.gridy = row + 2;
//                 gbc.gridwidth = 1;
//                 gbc.gridheight = 1;
//                 gbc.insets = new Insets(3, 3, 3, 3);
                
//                 boolean isWindow = (col == 0);
//                 boolean isOccupied = random.nextDouble() > 0.75; // 25% occupied
//                 FightSeatButton seat = new FightSeatButton(seatNumber++, isOccupied, isWindow, row < 3);
//                 panel.add(seat, gbc);
//             }
            
//             // Aisle space
//             gbc.gridx = 2;
//             gbc.gridy = row + 2;
//             JLabel aisleLabel = new JLabel("AISLE", SwingConstants.CENTER);
//             aisleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 8));
//             aisleLabel.setForeground(PageComponents.SECONDARY_COLOR);
//             panel.add(aisleLabel, gbc);
            
//             // Right side seats (Aisle + Window)
//             for (int col = 3; col < 5; col++) {
//                 gbc.gridx = col;
//                 gbc.gridy = row + 2;
//                 gbc.gridwidth = 1;
//                 gbc.insets = new Insets(3, 3, 3, 3);
                
//                 boolean isWindow = (col == 4);
//                 boolean isOccupied = random.nextDouble() > 0.75;
//                 FightSeatButton seat = new FightSeatButton(seatNumber++, isOccupied, isWindow, row < 3);
//                 panel.add(seat, gbc);
//             }
//         }
        
//         return panel;
//     }

//      private JPanel createLegendPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
//         panel.setBackground(PageComponents.CARD_COLOR);
//         panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
//         // Available seat
//         JButton availableExample = new JButton("12");
//         availableExample.setPreferredSize(new Dimension(35, 35));
//         availableExample.setBackground(PageComponents.ACCENT_COLOR);
//         availableExample.setForeground(Color.BLACK);
//         availableExample.setEnabled(false);
//         availableExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
//         // Occupied seat
//         JButton occupiedExample = new JButton("X");
//         occupiedExample.setPreferredSize(new Dimension(35, 35));
//         occupiedExample.setBackground(new Color(220, 53, 69));
//         occupiedExample.setForeground(Color.WHITE);
//         occupiedExample.setEnabled(false);
//         occupiedExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
//         // Selected seat
//         JButton selectedExample = new JButton("12");
//         selectedExample.setPreferredSize(new Dimension(35, 35));
//         selectedExample.setBackground(PageComponents.PRIMARY_COLOR);
//         selectedExample.setForeground(Color.WHITE);
//         selectedExample.setEnabled(false);
//         selectedExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
//         // Premium seat
//         JButton premiumExample = new JButton("P1");
//         premiumExample.setPreferredSize(new Dimension(35, 35));
//         premiumExample.setBackground(new Color(255, 193, 7));
//         premiumExample.setForeground(Color.BLACK);
//         premiumExample.setEnabled(false);
//         premiumExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
//         panel.add(availableExample);
//         panel.add(new JLabel(" Available") {{ setForeground(PageComponents.TEXT_COLOR); }});
//         panel.add(Box.createHorizontalStrut(10));
//         panel.add(occupiedExample);
//         panel.add(new JLabel(" Occupied") {{ setForeground(PageComponents.TEXT_COLOR); }});
//         panel.add(Box.createHorizontalStrut(10));
//         panel.add(selectedExample);
//         panel.add(new JLabel(" Selected") {{ setForeground(PageComponents.TEXT_COLOR); }});
//         panel.add(Box.createHorizontalStrut(10));
//         panel.add(premiumExample);
//         panel.add(new JLabel(" Premium (+30%)") {{ setForeground(PageComponents.TEXT_COLOR); }});
        
//         return panel;
//     }
   
//     private JPanel createControlPanel() {
//         JPanel panel = createCardPanel();
//         panel.setPreferredSize(new Dimension(300, 0));
//         panel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
//             BorderFactory.createEmptyBorder(20, 20, 20, 20)
//         ));
//         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
//         // Selection info
//         JLabel selectionTitle = new JLabel("Booking Summary");
//         selectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
//         selectionTitle.setForeground(PageComponents.TEXT_COLOR);
//         selectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         selectedSeatsLabel = new JLabel("Selected Seats: None");
//         selectedSeatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         selectedSeatsLabel.setForeground(PageComponents.TEXT_COLOR);
//         selectedSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         JLabel needSeatsLabel = new JLabel("Need to select: " + passengerCount + " seat(s)");
//         needSeatsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
//         needSeatsLabel.setForeground(PageComponents.ACCENT_COLOR);
//         needSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         totalPriceLabel = new JLabel("Total Price: $0.00");
//         totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
//         totalPriceLabel.setForeground(PageComponents.ACCENT_COLOR);
//         totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         // Buttons
//         confirmButton = PageComponents.createStyledButton("🎫 Confirm Booking", PageComponents.ACCENT_COLOR, true);
//         confirmButton.setEnabled(false);
//         confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         JButton clearButton = PageComponents.createStyledButton("🗑️ Clear Selection", new Color(255, 121, 121), true);
//         clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         backButton = PageComponents.createStyledButton("← Back to Search", PageComponents.SECONDARY_COLOR, false);
//         backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         // Bus info panel
//         JPanel flightInfoPanel = new JPanel();
//         flightInfoPanel.setLayout(new BoxLayout(flightInfoPanel, BoxLayout.Y_AXIS));
//         flightInfoPanel.setBackground(PageComponents.CARD_COLOR);
//         flightInfoPanel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(PageComponents.SECONDARY_COLOR, 1),
//             BorderFactory.createEmptyBorder(15, 15, 15, 15)
//         ));
        
//         JLabel flightInfoTitle = new JLabel("🚌 flight Features:");
//         flightInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
//         flightInfoTitle.setForeground(PageComponents.TEXT_COLOR);
//         flightInfoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         JLabel feature1 = new JLabel("• Comfortable reclining seats");
//         feature1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//         feature1.setForeground(PageComponents.TEXT_COLOR);
        
//         JLabel feature2 = new JLabel("• " + amenities);
//         feature2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//         feature2.setForeground(PageComponents.TEXT_COLOR);
        
//         JLabel feature3 = new JLabel("• Professional driver service");
//         feature3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//         feature3.setForeground(PageComponents.TEXT_COLOR);
        
//         flightInfoPanel.add(busInfoTitle);
//         flightInfoPanel.add(Box.createVerticalStrut(8));
//         flightInfoPanel.add(feature1);
//         flightInfoPanel.add(feature2);
//         flightInfoPanel.add(feature3);
        
//         // Layout
//         panel.add(selectionTitle);
//         panel.add(Box.createVerticalStrut(20));
//         panel.add(selectedSeatsLabel);
//         panel.add(Box.createVerticalStrut(5));
//         panel.add(needSeatsLabel);
//         panel.add(Box.createVerticalStrut(15));
//         panel.add(totalPriceLabel);
//         panel.add(Box.createVerticalStrut(30));
//         panel.add(confirmButton);
//         panel.add(Box.createVerticalStrut(10));
//         panel.add(clearButton);
//         panel.add(Box.createVerticalStrut(10));
//         panel.add(backButton);
//         panel.add(Box.createVerticalStrut(30));
//         panel.add(busInfoPanel);
        
//         // Clear button action
//         clearButton.addActionListener(e -> clearSelection());
        
//         return panel;
//     }
    
   

// }
