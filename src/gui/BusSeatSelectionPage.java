package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

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
        super("Bus Seat Selection", 1400, 800);
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
        
        // Header with trip info
        JPanel headerPanel = createHeaderPanel();
        
        // Main content - horizontal layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        // Center - Bus seat map (horizontal)
        createHorizontalBusSeatMapPanel();
        
        // Right sidebar - Selection controls
        JPanel sidebarPanel = createSidebarPanel();
        
        contentPanel.add(seatMapPanel, BorderLayout.CENTER);
        contentPanel.add(sidebarPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        setupActionListeners();
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Title
        JLabel titleLabel = new JLabel("Select Your Seat", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        
        // Trip info card
        JPanel infoCard = new JPanel(new GridLayout(1, 4, 30, 0));
        infoCard.setBackground(PageComponents.CARD_COLOR);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        
        // Info items
        JPanel companyInfo = createInfoItem("🚌", busCompany, "Company");
        JPanel routeInfo = createInfoItem("📍", fromCity + " → " + toCity, "Route");
        JPanel dateInfo = createInfoItem("📅", date + " at " + departureTime, "Departure");
        JPanel passengerInfo = createInfoItem("👥", passengerCount + " passenger(s)", "Travelers");
        
        infoCard.add(companyInfo);
        infoCard.add(routeInfo);
        infoCard.add(dateInfo);
        infoCard.add(passengerInfo);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(Box.createVerticalStrut(20), BorderLayout.CENTER);
        headerPanel.add(infoCard, BorderLayout.SOUTH);
        
        return headerPanel;
    }
    
    private JPanel createInfoItem(String icon, String value, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.CARD_COLOR);
        
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueLabel.setForeground(PageComponents.TEXT_COLOR);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelLabel = new JLabel(label, SwingConstants.CENTER);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelLabel.setForeground(PageComponents.SECONDARY_COLOR);
        labelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(labelLabel);
        
        return panel;
    }
    
    private void createHorizontalBusSeatMapPanel() {
        seatMapPanel = new JPanel(new BorderLayout());
        seatMapPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        seatMapPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Bus container with modern styling
        JPanel busContainer = new JPanel(new BorderLayout());
        busContainer.setBackground(PageComponents.CARD_COLOR);
        busContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        // Bus header
        JPanel busHeader = new JPanel(new BorderLayout());
        busHeader.setBackground(PageComponents.CARD_COLOR);
        
        JLabel busLabel = new JLabel("🚌 " + busCompany.toUpperCase() + " BUS", SwingConstants.CENTER);
        busLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        busLabel.setForeground(PageComponents.TEXT_COLOR);
        
        // Driver section
        JPanel driverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        driverPanel.setBackground(PageComponents.CARD_COLOR);
        
        JLabel driverLabel = new JLabel("🚗 DRIVER");
        driverLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        driverLabel.setForeground(PageComponents.ACCENT_COLOR);
        driverLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        driverPanel.add(driverLabel);
        
        busHeader.add(busLabel, BorderLayout.CENTER);
        busHeader.add(driverPanel, BorderLayout.WEST);
        
        // Horizontal seat layout
        JPanel seatsPanel = createHorizontalSeatLayout();
        
        // Legend
        JPanel legendPanel = createModernLegendPanel();
        
        busContainer.add(busHeader, BorderLayout.NORTH);
        busContainer.add(seatsPanel, BorderLayout.CENTER);
        busContainer.add(legendPanel, BorderLayout.SOUTH);
        
        seatMapPanel.add(busContainer, BorderLayout.CENTER);
    }
    
    private JPanel createHorizontalSeatLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PageComponents.CARD_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Create horizontal bus layout
        JPanel busLayout = new JPanel();
        busLayout.setLayout(new BoxLayout(busLayout, BoxLayout.Y_AXIS));
        busLayout.setBackground(PageComponents.CARD_COLOR);
        
        Random random = new Random(42);
        int seatNumber = 1;
        
        // Create 10 columns (representing bus length) with 4 seats each (2-2 configuration)
        for (int row = 0; row < 4; row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
            rowPanel.setBackground(PageComponents.CARD_COLOR);
            
            for (int col = 0; col < 10; col++) {
                if (row == 1) {
                    // Aisle space in the middle
                    JLabel aisleLabel = new JLabel("AISLE");
                    aisleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 8));
                    aisleLabel.setForeground(PageComponents.SECONDARY_COLOR);
                    aisleLabel.setPreferredSize(new Dimension(50, 20));
                    aisleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    rowPanel.add(aisleLabel);
                } else {
                    boolean isWindow = (row == 0 || row == 3);
                    boolean isOccupied = random.nextDouble() > 0.75;
                    boolean isPremium = col < 3; // First 3 columns are premium
                    
                    BusSeatButton seat = new BusSeatButton(seatNumber++, isOccupied, isWindow, isPremium);
                    rowPanel.add(seat);
                }
            }
            
            busLayout.add(rowPanel);
        }
        
        mainPanel.add(busLayout, BorderLayout.CENTER);
        return mainPanel;
    }
    
    private JPanel createModernLegendPanel() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        legendPanel.setBackground(PageComponents.CARD_COLOR);
        legendPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Legend items with modern styling
        JPanel availableItem = createLegendItem("Available", PageComponents.ACCENT_COLOR, "12");
        JPanel selectedItem = createLegendItem("Selected", PageComponents.PRIMARY_COLOR, "12");
        JPanel occupiedItem = createLegendItem("Occupied", new Color(255, 85, 85), "X");
        JPanel premiumItem = createLegendItem("Premium (+30%)", new Color(255, 193, 7), "P1");
        
        legendPanel.add(availableItem);
        legendPanel.add(selectedItem);
        legendPanel.add(occupiedItem);
        legendPanel.add(premiumItem);
        
        return legendPanel;
    }
    
    private JPanel createLegendItem(String label, Color color, String seatText) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        item.setBackground(PageComponents.CARD_COLOR);
        
        JButton sampleSeat = new JButton(seatText);
        sampleSeat.setPreferredSize(new Dimension(40, 40));
        sampleSeat.setBackground(color);
        sampleSeat.setForeground(color.equals(PageComponents.ACCENT_COLOR) || color.equals(new Color(255, 193, 7)) ? Color.BLACK : Color.WHITE);
        sampleSeat.setFont(new Font("Segoe UI", Font.BOLD, 10));
        sampleSeat.setEnabled(false);
        sampleSeat.setBorder(BorderFactory.createLineBorder(color.darker(), 1, true));
        
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelText.setForeground(PageComponents.TEXT_COLOR);
        
        item.add(sampleSeat);
        item.add(labelText);
        
        return item;
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PageComponents.CARD_COLOR);
        sidebar.setPreferredSize(new Dimension(320, 0));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(30, 25, 30, 25)
        ));
        
        // Selection summary
        JLabel summaryTitle = new JLabel("Booking Summary");
        summaryTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        summaryTitle.setForeground(PageComponents.TEXT_COLOR);
        summaryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Selection info
        selectedSeatsLabel = new JLabel("No seats selected");
        selectedSeatsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectedSeatsLabel.setForeground(PageComponents.SECONDARY_COLOR);
        selectedSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel needSeatsLabel = new JLabel("Select " + passengerCount + " seat(s)");
        needSeatsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        needSeatsLabel.setForeground(PageComponents.ACCENT_COLOR);
        needSeatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Price display
        totalPriceLabel = new JLabel("Total: $0.00");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalPriceLabel.setForeground(PageComponents.ACCENT_COLOR);
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action buttons
        confirmButton = createModernButton("Confirm Booking", PageComponents.ACCENT_COLOR, true);
        confirmButton.setEnabled(false);
        
        JButton clearButton = createModernButton("Clear Selection", new Color(255, 121, 121), false);
        backButton = createModernButton("← Back to Search", PageComponents.SECONDARY_COLOR, false);
        
        // Trip features
        JPanel featuresPanel = createFeaturesPanel();
        
        // Layout
        sidebar.add(summaryTitle);
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
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(backButton);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(featuresPanel);
        
        // Clear button action
        clearButton.addActionListener(e -> clearSelection());
        
        return sidebar;
    }
    
    private JButton createModernButton(String text, Color color, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(isPrimary ? Color.WHITE : PageComponents.TEXT_COLOR);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(280, 50));
        button.setPreferredSize(new Dimension(280, 50));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(color);
                }
            }
        });
        
        return button;
    }
    
    private JPanel createFeaturesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.SECONDARY_COLOR, 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel title = new JLabel("🚌 Bus Features");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(PageComponents.TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String[] features = {
            "• Comfortable reclining seats",
            "• " + amenities,
            "• Professional driver service",
            "• Air conditioning",
            "• Safety certified"
        };
        
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));
        
        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            featureLabel.setForeground(PageComponents.SECONDARY_COLOR);
            featureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(featureLabel);
            panel.add(Box.createVerticalStrut(5));
        }
        
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
            selectedSeatsLabel.setText("No seats selected");
            totalPriceLabel.setText("Total: $0.00");
            confirmButton.setEnabled(false);
        } else {
            StringBuilder seatNumbers = new StringBuilder();
            double totalPrice = 0;
            
            for (int i = 0; i < selectedSeats.size(); i++) {
                if (i > 0) seatNumbers.append(", ");
                seatNumbers.append(selectedSeats.get(i).getSeatNumber());
                totalPrice += selectedSeats.get(i).getPrice();
            }
            
            selectedSeatsLabel.setText("Seats: " + seatNumbers.toString());
            totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
            
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
        message.append("🎫 Booking Confirmed!\n\n");
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
        message.append("\n✨ Features: ").append(amenities);
        
        PageComponents.showStyledMessage("Booking Confirmed", message.toString(), this);
        
        dispose();
        new ReservationPage().display();
    }
    
    // Inner class for modern bus seat buttons
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
                setBackground(new Color(255, 85, 85));
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
            
            setPreferredSize(new Dimension(50, 50));
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setFocusPainted(false);
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        private double calculateSeatPrice() {
            double multiplier = 1.0;
            
            if (isPremium) multiplier += 0.3;
            if (isWindow) multiplier += 0.1;
            
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
                setBorder(BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR.darker(), 2, true));
            } else {
                setBackground(isPremium ? new Color(255, 193, 7) : PageComponents.ACCENT_COLOR);
                setForeground(isPremium ? Color.BLACK : Color.BLACK);
                setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
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