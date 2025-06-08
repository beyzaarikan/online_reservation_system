package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BusSeatSelectionPage extends BasePanel {
    private String busCompany;
    private String fromCity;
    private String toCity;
    private String date;
    private String departureTime;
    private String arrivalTime;
    private String basePrice;
    private int passengerCount;
    private String amenities;
    
    private JPanel seatMapPanel;
    private JLabel selectedSeatsLabel;
    private JLabel totalPriceLabel;
    private JButton confirmButton;
    private JButton backButton;
    
    private List<BusSeatButton> selectedSeats;
    private double basePriceValue;
    
    public BusSeatSelectionPage(String busCompany, String fromCity, String toCity, String date, 
                               String departureTime, String arrivalTime, String basePrice, 
                               int passengerCount, String amenities) {
        super("Bus Seat Selection", 1200, 900);
        this.busCompany = busCompany;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.passengerCount = passengerCount;
        this.amenities = amenities;
        this.selectedSeats = new ArrayList<>();
        
        // Parse base price
        try {
            this.basePriceValue = Double.parseDouble(basePrice.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            this.basePriceValue = 45.0;
        }
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel with trip info
        JPanel titlePanel = createTripInfoPanel();
        
        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        // Center - Seat Map
        createBusSeatMapPanel();
        
        // Right side - Selection info and controls
        JPanel rightPanel = createControlPanel();
        
        contentPanel.add(seatMapPanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        setupActionListeners();
    }
    
    private JPanel createTripInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PageComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Title
        JLabel titleLabel = new JLabel("🚌 Select Your Bus Seat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        
        // Trip info panel
        JPanel infoPanel = createCardPanel();
        infoPanel.setLayout(new GridLayout(3, 2, 20, 10));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Trip info labels
        JLabel companyLabel = createInfoLabel("🚌 Company: " + busCompany);
        JLabel routeLabel = createInfoLabel("📍 Route: " + fromCity + " → " + toCity);
        JLabel dateLabel = createInfoLabel("📅 Date: " + date);
        JLabel timeLabel = createInfoLabel("🕐 Time: " + departureTime + " - " + arrivalTime);
        JLabel passengerLabel = createInfoLabel("👥 Passengers: " + passengerCount);
        JLabel amenitiesLabel = createInfoLabel("✨ Amenities: " + amenities);
        
        companyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        companyLabel.setForeground(PageComponents.PRIMARY_COLOR);
        
        infoPanel.add(companyLabel);
        infoPanel.add(routeLabel);
        infoPanel.add(dateLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(passengerLabel);
        infoPanel.add(amenitiesLabel);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(PageComponents.TEXT_COLOR);
        return label;
    }
    
    private void createBusSeatMapPanel() {
        seatMapPanel = createCardPanel();
        seatMapPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        seatMapPanel.setLayout(new BorderLayout());
        
        // Bus header
        JLabel busLabel = new JLabel("🚌 BUS SEAT MAP - " + busCompany.toUpperCase(), SwingConstants.CENTER);
        busLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        busLabel.setForeground(PageComponents.TEXT_COLOR);
        
        // Create bus seat layout
        JPanel seatsPanel = createBusSeatLayout();
        
        // Legend panel
        JPanel legendPanel = createLegendPanel();
        
        seatMapPanel.add(busLabel, BorderLayout.NORTH);
        seatMapPanel.add(seatsPanel, BorderLayout.CENTER);
        seatMapPanel.add(legendPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createBusSeatLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PageComponents.CARD_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Driver area
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 5;
        JPanel driverPanel = new JPanel(new FlowLayout());
        driverPanel.setBackground(PageComponents.CARD_COLOR);
        JLabel driverLabel = new JLabel("🚗 DRIVER", SwingConstants.CENTER);
        driverLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        driverLabel.setForeground(PageComponents.ACCENT_COLOR);
        driverLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        driverPanel.add(driverLabel);
        panel.add(driverPanel, gbc);
        
        // Add space after driver
        gbc.gridy = 1; gbc.gridheight = 1;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        // Bus seats layout (2-2 configuration, 14 rows)
        Random random = new Random(42); // For consistent random occupied seats
        int seatNumber = 1;
        
        for (int row = 0; row < 14; row++) {
            // Left side seats (Window + Aisle)
            for (int col = 0; col < 2; col++) {
                gbc.gridx = col;
                gbc.gridy = row + 2;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.insets = new Insets(3, 3, 3, 3);
                
                boolean isWindow = (col == 0);
                boolean isOccupied = random.nextDouble() > 0.75; // 25% occupied
                BusSeatButton seat = new BusSeatButton(seatNumber++, isOccupied, isWindow, row < 3);
                panel.add(seat, gbc);
            }
            
            // Aisle space
            gbc.gridx = 2;
            gbc.gridy = row + 2;
            JLabel aisleLabel = new JLabel("AISLE", SwingConstants.CENTER);
            aisleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 8));
            aisleLabel.setForeground(PageComponents.SECONDARY_COLOR);
            panel.add(aisleLabel, gbc);
            
            // Right side seats (Aisle + Window)
            for (int col = 3; col < 5; col++) {
                gbc.gridx = col;
                gbc.gridy = row + 2;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(3, 3, 3, 3);
                
                boolean isWindow = (col == 4);
                boolean isOccupied = random.nextDouble() > 0.75;
                BusSeatButton seat = new BusSeatButton(seatNumber++, isOccupied, isWindow, row < 3);
                panel.add(seat, gbc);
            }
        }
        
        return panel;
    }
    
    private JPanel createLegendPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel.setBackground(PageComponents.CARD_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        // Available seat
        JButton availableExample = new JButton("12");
        availableExample.setPreferredSize(new Dimension(35, 35));
        availableExample.setBackground(PageComponents.ACCENT_COLOR);
        availableExample.setForeground(Color.BLACK);
        availableExample.setEnabled(false);
        availableExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
        // Occupied seat
        JButton occupiedExample = new JButton("X");
        occupiedExample.setPreferredSize(new Dimension(35, 35));
        occupiedExample.setBackground(new Color(220, 53, 69));
        occupiedExample.setForeground(Color.WHITE);
        occupiedExample.setEnabled(false);
        occupiedExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
        // Selected seat
        JButton selectedExample = new JButton("12");
        selectedExample.setPreferredSize(new Dimension(35, 35));
        selectedExample.setBackground(PageComponents.PRIMARY_COLOR);
        selectedExample.setForeground(Color.WHITE);
        selectedExample.setEnabled(false);
        selectedExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
        // Premium seat
        JButton premiumExample = new JButton("P1");
        premiumExample.setPreferredSize(new Dimension(35, 35));
        premiumExample.setBackground(new Color(255, 193, 7));
        premiumExample.setForeground(Color.BLACK);
        premiumExample.setEnabled(false);
        premiumExample.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
        panel.add(availableExample);
        panel.add(new JLabel(" Available") {{ setForeground(PageComponents.TEXT_COLOR); }});
        panel.add(Box.createHorizontalStrut(10));
        panel.add(occupiedExample);
        panel.add(new JLabel(" Occupied") {{ setForeground(PageComponents.TEXT_COLOR); }});
        panel.add(Box.createHorizontalStrut(10));
        panel.add(selectedExample);
        panel.add(new JLabel(" Selected") {{ setForeground(PageComponents.TEXT_COLOR); }});
        panel.add(Box.createHorizontalStrut(10));
        panel.add(premiumExample);
        panel.add(new JLabel(" Premium (+30%)") {{ setForeground(PageComponents.TEXT_COLOR); }});
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = createCardPanel();
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Selection info
        JLabel selectionTitle = new JLabel("Booking Summary");
        selectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        selectionTitle.setForeground(PageComponents.TEXT_COLOR);
        selectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        selectedSeatsLabel = new JLabel("Selected Seats: None");
        selectedSeatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectedSeatsLabel.setForeground(PageComponents.TEXT_COLOR);
        selectedSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel needSeatsLabel = new JLabel("Need to select: " + passengerCount + " seat(s)");
        needSeatsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        needSeatsLabel.setForeground(PageComponents.ACCENT_COLOR);
        needSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        totalPriceLabel = new JLabel("Total Price: $0.00");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalPriceLabel.setForeground(PageComponents.ACCENT_COLOR);
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons
        confirmButton = PageComponents.createStyledButton("🎫 Confirm Booking", PageComponents.ACCENT_COLOR, true);
        confirmButton.setEnabled(false);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton clearButton = PageComponents.createStyledButton("🗑️ Clear Selection", new Color(255, 121, 121), true);
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        backButton = PageComponents.createStyledButton("← Back to Search", PageComponents.SECONDARY_COLOR, false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Bus info panel
        JPanel busInfoPanel = new JPanel();
        busInfoPanel.setLayout(new BoxLayout(busInfoPanel, BoxLayout.Y_AXIS));
        busInfoPanel.setBackground(PageComponents.CARD_COLOR);
        busInfoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.SECONDARY_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel busInfoTitle = new JLabel("🚌 Bus Features:");
        busInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        busInfoTitle.setForeground(PageComponents.TEXT_COLOR);
        busInfoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel feature1 = new JLabel("• Comfortable reclining seats");
        feature1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        feature1.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel feature2 = new JLabel("• " + amenities);
        feature2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        feature2.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel feature3 = new JLabel("• Professional driver service");
        feature3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        feature3.setForeground(PageComponents.TEXT_COLOR);
        
        busInfoPanel.add(busInfoTitle);
        busInfoPanel.add(Box.createVerticalStrut(8));
        busInfoPanel.add(feature1);
        busInfoPanel.add(feature2);
        busInfoPanel.add(feature3);
        
        // Layout
        panel.add(selectionTitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(selectedSeatsLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(needSeatsLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(totalPriceLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(confirmButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(clearButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(backButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(busInfoPanel);
        
        // Clear button action
        clearButton.addActionListener(e -> clearSelection());
        
        return panel;
    }
    
    private void setupActionListeners() {
        confirmButton.addActionListener(e -> confirmBooking());
        backButton.addActionListener(e -> {
            dispose();
            new SearchBusTripPage().display();
        });
    }
    
    private void clearSelection() {
        for (BusSeatButton seat : selectedSeats) {
            seat.setSelected(false);
        }
        selectedSeats.clear();
        updateSelectionInfo();
    }
    
    private void updateSelectionInfo() {
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("Selected Seats: None");
            totalPriceLabel.setText("Total Price: $0.00");
            confirmButton.setEnabled(false);
        } else {
            StringBuilder seatNumbers = new StringBuilder();
            double totalPrice = 0;
            
            for (int i = 0; i < selectedSeats.size(); i++) {
                if (i > 0) seatNumbers.append(", ");
                seatNumbers.append(selectedSeats.get(i).getSeatNumber());
                totalPrice += selectedSeats.get(i).getPrice();
            }
            
            selectedSeatsLabel.setText("Selected Seats: " + seatNumbers.toString());
            totalPriceLabel.setText(String.format("Total Price: $%.2f", totalPrice));
            
            // Enable confirm button only if correct number of seats selected
            confirmButton.setEnabled(selectedSeats.size() == passengerCount);
        }
    }
    
    private void confirmBooking() {
        if (selectedSeats.size() != passengerCount) {
            PageComponents.showStyledMessage("Error", 
                "Please select exactly " + passengerCount + " seat(s)!", this);
            return;
        }
        
        StringBuilder message = new StringBuilder();
        message.append("🎫 Bus Booking Confirmed!\n\n");
        message.append("🚌 Company: ").append(busCompany).append("\n");
        message.append("📍 Route: ").append(fromCity).append(" → ").append(toCity).append("\n");
        message.append("📅 Date: ").append(date).append("\n");
        message.append("🕐 Departure: ").append(departureTime).append("\n");
        message.append("🕓 Arrival: ").append(arrivalTime).append("\n");
        message.append("💺 Seats: ");
        
        for (int i = 0; i < selectedSeats.size(); i++) {
            if (i > 0) message.append(", ");
            message.append(selectedSeats.get(i).getSeatNumber());
        }
        
        double totalPrice = selectedSeats.stream().mapToDouble(BusSeatButton::getPrice).sum();
        message.append("\n💰 Total: $").append(String.format("%.2f", totalPrice));
        message.append("\n✨ Amenities: ").append(amenities);
        
        PageComponents.showStyledMessage("Booking Confirmed", message.toString(), this);
        
        dispose();
        // new PaymentPage or ReservationPage can be opened here
    }
    
    // Inner class for bus seat buttons
    private class BusSeatButton extends JButton {
        private int seatNumber;
        private boolean isOccupied;
        private boolean isSelected;
        private boolean isWindow;
        private boolean isPremium;
        private double price;
        
        public BusSeatButton(int seatNumber, boolean isOccupied, boolean isWindow, boolean isPremium) {
            this.seatNumber = seatNumber;
            this.isOccupied = isOccupied;
            this.isSelected = false;
            this.isWindow = isWindow;
            this.isPremium = isPremium;
            this.price = calculateSeatPrice();
            
            setupButton();
        }
        
        private void setupButton() {
            if (isOccupied) {
                setText("X");
                setBackground(new Color(220, 53, 69));
                setForeground(Color.WHITE);
                setEnabled(false);
                setToolTipText("Seat " + seatNumber + " is occupied");
            } else {
                setText(isPremium ? "P" + seatNumber : String.valueOf(seatNumber));
                setBackground(isPremium ? new Color(255, 193, 7) : PageComponents.ACCENT_COLOR);
                setForeground(isPremium ? Color.BLACK : Color.BLACK);
                setToolTipText(String.format("Seat %d - $%.2f%s%s", 
                    seatNumber, price,
                    isWindow ? " (Window)" : "",
                    isPremium ? " (Premium)" : ""));
                
                addActionListener(e -> toggleSelection());
            }
            
            setPreferredSize(new Dimension(45, 45));
            setFont(new Font("Segoe UI", Font.BOLD, 10));
            setFocusPainted(false);
            setBorder(BorderFactory.createRaisedBevelBorder());
        }
        
        private double calculateSeatPrice() {
            double multiplier = 1.0;
            
            if (isPremium) multiplier += 0.3; // Premium seats +30%
            if (isWindow) multiplier += 0.1;  // Window seats +10%
            
            return basePriceValue * multiplier;
        }
        
        private void toggleSelection() {
            if (isSelected) {
                setSelected(false);
                selectedSeats.remove(this);
            } else {
                // Check if max seats reached
                if (selectedSeats.size() >= passengerCount) {
                    PageComponents.showStyledMessage("Warning", 
                        "You can only select " + passengerCount + " seat(s)!", 
                        BusSeatSelectionPage.this);
                    return;
                }
                setSelected(true);
                selectedSeats.add(this);
            }
            updateSelectionInfo();
        }
        
        public void setSelected(boolean selected) {
            this.isSelected = selected;
            if (selected) {
                setBackground(PageComponents.PRIMARY_COLOR);
                setForeground(Color.WHITE);
            } else {
                setBackground(isPremium ? new Color(255, 193, 7) : PageComponents.ACCENT_COLOR);
                setForeground(isPremium ? Color.BLACK : Color.BLACK);
            }
        }
        
        public int getSeatNumber() {
            return seatNumber;
        }
        
        public double getPrice() {
            return price;
        }
    }
}