package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeatSelectionPage extends BasePanel {
    private String tripType;
    private String route;
    private String date;
    private String departure;
    private String price;
    private String availableSeats;
    
    private JPanel seatMapPanel;
    private JLabel selectedSeatsLabel;
    private JLabel totalPriceLabel;
    private JButton confirmButton;
    private JButton backButton;
    
    private List<SeatButton> selectedSeats;
    private double basePrice;
     
    public SeatSelectionPage(String tripType, String route, String date, String departure, String price, String availableSeats) {
        super("Select Your Seat", 1000, 800);
        this.tripType = tripType;
        this.route = route;
        this.date = date;
        this.departure = departure;
        this.price = price;
        this.availableSeats = availableSeats;
        this.selectedSeats = new ArrayList<>();
        
        // Parse base price from price string (remove currency symbols)
        try {
            this.basePrice = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            this.basePrice = 50.0; // Default price
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
        
        // Left side - Seat Map
        createSeatMapPanel();
        
        // Right side - Selection info and controls
        JPanel rightPanel = createControlPanel();
        
        contentPanel.add(seatMapPanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        setupActionListeners();
    }
    
    private JPanel createTripInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PageComponents.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Title
        JLabel titleLabel = new JLabel("Select Your Seat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        
        // Trip info panel
        JPanel infoPanel = createCardPanel();
        infoPanel.setLayout(new GridLayout(2, 2, 20, 10));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        // Trip info labels
        JLabel routeLabel = createInfoLabel("Route: " + route);
        JLabel dateLabel = createInfoLabel("Date: " + date);
        JLabel departureLabel = createInfoLabel("Departure: " + departure);
        JLabel typeLabel = createInfoLabel("Type: " + tripType.toUpperCase());
        
        infoPanel.add(routeLabel);
        infoPanel.add(dateLabel);
        infoPanel.add(departureLabel);
        infoPanel.add(typeLabel);
        
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
    
    private void createSeatMapPanel() {
        seatMapPanel = createCardPanel();
        seatMapPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        seatMapPanel.setLayout(new BorderLayout());
        
        // Vehicle type header
        JLabel vehicleLabel = new JLabel(tripType.toUpperCase() + " SEAT MAP", SwingConstants.CENTER);
        vehicleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        vehicleLabel.setForeground(PageComponents.TEXT_COLOR);
        
        // Create seat layout based on trip type
        JPanel seatsPanel;
        if (tripType.equalsIgnoreCase("bus")) {
            seatsPanel = createBusSeatLayout();
        } else {
            seatsPanel = createFlightSeatLayout();
        }
        
        // Legend panel
        JPanel legendPanel = createLegendPanel();
        
        seatMapPanel.add(vehicleLabel, BorderLayout.NORTH);
        seatMapPanel.add(seatsPanel, BorderLayout.CENTER);
        seatMapPanel.add(legendPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createBusSeatLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PageComponents.CARD_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Driver area
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        JLabel driverLabel = new JLabel("🚌 DRIVER", SwingConstants.CENTER);
        driverLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        driverLabel.setForeground(PageComponents.ACCENT_COLOR);
        driverLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(driverLabel, gbc);
        
        // Bus seats layout (2-2 configuration, 12 rows)
        Random random = new Random(42); // For consistent random occupied seats
        int seatNumber = 1;
        
        for (int row = 0; row < 12; row++) {
            // Left side seats
            for (int col = 0; col < 2; col++) {
                gbc.gridx = col;
                gbc.gridy = row + 1;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(2, 2, 2, 2);
                
                SeatButton seat = new SeatButton(seatNumber++, random.nextDouble() > 0.7);
                panel.add(seat, gbc);
            }
            
            // Aisle space
            gbc.gridx = 2;
            gbc.gridy = row + 1;
            panel.add(Box.createHorizontalStrut(30), gbc);
            
            // Right side seats
            for (int col = 3; col < 5; col++) {
                gbc.gridx = col;
                gbc.gridy = row + 1;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(2, 2, 2, 2);
                
                SeatButton seat = new SeatButton(seatNumber++, random.nextDouble() > 0.7);
                panel.add(seat, gbc);
            }
        }
        
        return panel;
    }
    
    private JPanel createFlightSeatLayout() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PageComponents.CARD_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Cockpit area
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 6;
        JLabel cockpitLabel = new JLabel("✈️ COCKPIT", SwingConstants.CENTER);
        cockpitLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cockpitLabel.setForeground(PageComponents.ACCENT_COLOR);
        cockpitLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(cockpitLabel, gbc);
        
        // Flight seats layout (3-3 configuration, 20 rows)
        Random random = new Random(42);
        int seatNumber = 1;
        
        for (int row = 0; row < 20; row++) {
            // Left side seats (A, B, C)
            for (int col = 0; col < 3; col++) {
                gbc.gridx = col;
                gbc.gridy = row + 1;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(2, 2, 2, 2);
                
                char seatLetter = (char)('A' + col);
                SeatButton seat = new SeatButton(seatNumber + "" + seatLetter, random.nextDouble() > 0.6);
                panel.add(seat, gbc);
            }
            
            // Aisle space
            gbc.gridx = 3;
            gbc.gridy = row + 1;
            panel.add(Box.createHorizontalStrut(40), gbc);
            
            // Right side seats (D, E, F)
            for (int col = 4; col < 7; col++) {
                gbc.gridx = col;
                gbc.gridy = row + 1;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(2, 2, 2, 2);
                
                char seatLetter = (char)('A' + col - 1);
                SeatButton seat = new SeatButton(seatNumber + "" + seatLetter, random.nextDouble() > 0.6);
                panel.add(seat, gbc);
            }
            seatNumber++;
        }
        
        return panel;
    }
    
    private JPanel createLegendPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(PageComponents.CARD_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        // Available seat
        JButton availableExample = new JButton();
        availableExample.setPreferredSize(new Dimension(30, 30));
        availableExample.setBackground(PageComponents.ACCENT_COLOR);
        availableExample.setEnabled(false);
        
        // Occupied seat
        JButton occupiedExample = new JButton();
        occupiedExample.setPreferredSize(new Dimension(30, 30));
        occupiedExample.setBackground(new Color(255, 85, 85));
        occupiedExample.setEnabled(false);
        
        // Selected seat
        JButton selectedExample = new JButton();
        selectedExample.setPreferredSize(new Dimension(30, 30));
        selectedExample.setBackground(PageComponents.PRIMARY_COLOR);
        selectedExample.setEnabled(false);
        
        panel.add(availableExample);
        panel.add(new JLabel(" Available") {{ setForeground(PageComponents.TEXT_COLOR); }});
        panel.add(Box.createHorizontalStrut(20));
        panel.add(occupiedExample);
        panel.add(new JLabel(" Occupied") {{ setForeground(PageComponents.TEXT_COLOR); }});
        panel.add(Box.createHorizontalStrut(20));
        panel.add(selectedExample);
        panel.add(new JLabel(" Selected") {{ setForeground(PageComponents.TEXT_COLOR); }});
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = createCardPanel();
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Selection info
        JLabel selectionTitle = new JLabel("Selection Summary");
        selectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        selectionTitle.setForeground(PageComponents.TEXT_COLOR);
        selectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        selectedSeatsLabel = new JLabel("Selected Seats: None");
        selectedSeatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectedSeatsLabel.setForeground(PageComponents.TEXT_COLOR);
        selectedSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        totalPriceLabel = new JLabel("Total Price: $0.00");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalPriceLabel.setForeground(PageComponents.ACCENT_COLOR);
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons
        confirmButton = PageComponents.createStyledButton("Confirm Booking", PageComponents.ACCENT_COLOR, true);
        confirmButton.setEnabled(false);
        
        backButton = PageComponents.createStyledButton("Back", PageComponents.SECONDARY_COLOR, false);
        
        JButton clearButton = PageComponents.createStyledButton("Clear Selection", new Color(255, 121, 121), true);
        
        // Tips panel
        JPanel tipsPanel = new JPanel();
        tipsPanel.setLayout(new BoxLayout(tipsPanel, BoxLayout.Y_AXIS));
        tipsPanel.setBackground(PageComponents.CARD_COLOR);
        tipsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.SECONDARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel tipsTitle = new JLabel("💡 Tips:");
        tipsTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tipsTitle.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel tip1 = new JLabel("• Click seats to select");
        tip1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tip1.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel tip2 = new JLabel("• Front seats cost extra");
        tip2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tip2.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel tip3 = new JLabel("• Window seats recommended");
        tip3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tip3.setForeground(PageComponents.TEXT_COLOR);
        
        tipsPanel.add(tipsTitle);
        tipsPanel.add(tip1);
        tipsPanel.add(tip2);
        tipsPanel.add(tip3);
        
        // Layout
        panel.add(selectionTitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(selectedSeatsLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(totalPriceLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(confirmButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(clearButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(backButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(tipsPanel);
        
        // Clear button action
        clearButton.addActionListener(e -> clearSelection());
        
        return panel;
    }
    
    private void setupActionListeners() {
        confirmButton.addActionListener(e -> confirmBooking());
        backButton.addActionListener(e -> {
            dispose();
            new TripSearchPage().display();
        });
    }
    
    private void clearSelection() {
        for (SeatButton seat : selectedSeats) {
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
            confirmButton.setEnabled(true);
        }
    }
    
    private void confirmBooking() {
        if (selectedSeats.isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please select at least one seat!", this);
            return;
        }
        
        StringBuilder message = new StringBuilder();
        message.append("Booking Confirmed!\n\n");
        message.append("Trip: ").append(route).append("\n");
        message.append("Date: ").append(date).append("\n");
        message.append("Departure: ").append(departure).append("\n");
        message.append("Seats: ");
        
        for (int i = 0; i < selectedSeats.size(); i++) {
            if (i > 0) message.append(", ");
            message.append(selectedSeats.get(i).getSeatNumber());
        }
        
        double totalPrice = selectedSeats.stream().mapToDouble(SeatButton::getPrice).sum();
        message.append("\nTotal: $").append(String.format("%.2f", totalPrice));
        
        PageComponents.showStyledMessage("Booking Confirmed", message.toString(), this);
        
        dispose();
        new AllReservationsPage().display();
    }
    
    // Inner class for seat buttons
    private class SeatButton extends JButton {
        private String seatNumber;
        private boolean isOccupied;
        private boolean isSelected;
        private double price;
        
        public SeatButton(String seatNumber, boolean isOccupied) {
            this.seatNumber = seatNumber;
            this.isOccupied = isOccupied;
            this.isSelected = false;
            this.price = calculateSeatPrice(seatNumber);
            
            setupButton();
        }
        
        public SeatButton(int seatNumber, boolean isOccupied) {
            this(String.valueOf(seatNumber), isOccupied);
        }
        
        private void setupButton() {
            setText(seatNumber);
            setPreferredSize(new Dimension(40, 40));
            setFont(new Font("Segoe UI", Font.BOLD, 10));
            setFocusPainted(false);
            setBorder(BorderFactory.createRaisedBevelBorder());
            
            if (isOccupied) {
                setBackground(new Color(255, 85, 85));
                setForeground(Color.WHITE);
                setEnabled(false);
                setToolTipText("Seat " + seatNumber + " is occupied");
            } else {
                setBackground(PageComponents.ACCENT_COLOR);
                setForeground(Color.BLACK);
                setToolTipText("Seat " + seatNumber + " - $" + String.format("%.2f", price));
                
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toggleSelection();
                    }
                });
            }
        }
        
        private double calculateSeatPrice(String seatNumber) {
            // Premium pricing for front seats and window seats
            double multiplier = 1.0;
            
            if (tripType.equalsIgnoreCase("flight")) {
                // For flights, first 3 rows are business class
                try {
                    int rowNum = Integer.parseInt(seatNumber.replaceAll("[A-Z]", ""));
                    if (rowNum <= 3) multiplier = 1.5;
                    else if (seatNumber.matches(".*[AF]")) multiplier = 1.2; // Window seats
                } catch (NumberFormatException e) {
                    // Default pricing
                }
            } else {
                // For bus, first 3 rows are premium
                try {
                    int seatNum = Integer.parseInt(seatNumber);
                    if (seatNum <= 6) multiplier = 1.3; // First 3 rows (6 seats)
                } catch (NumberFormatException e) {
                    // Default pricing
                }
            }
            
            return basePrice * multiplier;
        }
        
        private void toggleSelection() {
            if (isSelected) {
                setSelected(false);
                selectedSeats.remove(this);
            } else {
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
                setBackground(PageComponents.ACCENT_COLOR);
                setForeground(Color.BLACK);
            }
        }
        
        public String getSeatNumber() {
            return seatNumber;
        }
        
        public double getPrice() {
            return price;
        }
    }
}