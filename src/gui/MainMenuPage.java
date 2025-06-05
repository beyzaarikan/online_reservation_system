package gui;

import java.awt.*;
import javax.swing.*;

public class MainMenuPage extends BasePanel {
    private boolean isAdmin;
    private String userName = "User"; // Bu bilgi login'den gelecek
    
    public MainMenuPage() {
        this(false); // Default olarak normal user
    }
    
    public MainMenuPage(boolean isAdmin) {
        super(isAdmin ? "Admin Dashboard" : "Travel Dashboard", 700, 650);
        this.isAdmin = isAdmin;
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Header Panel with user info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // User info panel
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        JLabel userIcon = new JLabel(isAdmin ? "👨‍💼" : "👤");
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + (isAdmin ? "Admin" : userName) + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel statusLabel = new JLabel(isAdmin ? "Administrator" : "Customer");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(PageComponents.ACCENT_COLOR);
        
        userInfoPanel.add(userIcon);
        userInfoPanel.add(Box.createHorizontalStrut(10));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        textPanel.add(welcomeLabel);
        textPanel.add(statusLabel);
        userInfoPanel.add(textPanel);
        
        // Logout button
        JButton logoutButton = PageComponents.createStyledButton("Logout", new Color(255, 85, 85), true);
        logoutButton.setPreferredSize(new Dimension(100, 35));
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        // Title Panel
        JPanel titlePanel = createTitlePanel(isAdmin ? "Admin Dashboard" : "Travel Dashboard");
        
        // Main Content Panel
        JPanel contentPanel = createCardPanel();
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(isAdmin ? PageComponents.ACCENT_COLOR : PageComponents.PRIMARY_COLOR, 3, true),
            new javax.swing.border.EmptyBorder(40, 30, 40, 30)
        ));
        
        JLabel descLabel = new JLabel(
            isAdmin ? "Manage your travel system" : "Plan your perfect journey", 
            SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descLabel.setForeground(new Color(189, 147, 249));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Menu buttons based on user type
        contentPanel.add(descLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        
        if (isAdmin) {
            setupAdminMenu(contentPanel);
        } else {
            setupUserMenu(contentPanel);
        }
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(PageComponents.BACKGROUND_COLOR);
        centerWrapper.add(titlePanel, BorderLayout.NORTH);
        centerWrapper.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        add(mainPanel);
        
        // Logout action
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                // Return to welcome page
                try {
                    Class<?> welcomeClass = Class.forName("WelcomePage");
                    Object welcomePage = welcomeClass.getDeclaredConstructor().newInstance();
                    welcomeClass.getMethod("display").invoke(welcomePage);
                } catch (Exception ex) {
                    System.exit(0);
                }
            }
        });
    }
    
    private void setupUserMenu(JPanel contentPanel) {
        // User menu options with icons
        JButton busTripsButton = createMenuButton("🚌 Search Bus Trips", PageComponents.PRIMARY_COLOR);
        JButton flightTripsButton = createMenuButton("✈️ Search Flights", PageComponents.PRIMARY_COLOR);
        JButton reservationButton = createMenuButton("🎫 My Reservations", PageComponents.SECONDARY_COLOR);
        JButton profileButton = createMenuButton("👤 My Profile", PageComponents.SECONDARY_COLOR);
        
        contentPanel.add(busTripsButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(flightTripsButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(reservationButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(profileButton);
        
        // Action listeners for user menu
        busTripsButton.addActionListener(e -> {
            dispose();
            new SearchBusTripPage().display();
        });
        
        flightTripsButton.addActionListener(e -> {
            dispose();
            new SearchFlightsPage().display();
        });
        
        reservationButton.addActionListener(e -> {
            dispose();
            new ReservationPage().display();
        });
        
        profileButton.addActionListener(e -> {
            dispose();
            new MyProfilePage().display();
            //PageComponents.showStyledMessage("Info", "Profile page coming soon!", this);
        });
    }
    
    private void setupAdminMenu(JPanel contentPanel) {
        // Admin menu options with icons
        JButton userManagementButton = createMenuButton("👥 User Management", PageComponents.ACCENT_COLOR);
        JButton tripManagementButton = createMenuButton("🚍 Trip Management", PageComponents.PRIMARY_COLOR);
        JButton reservationManagementButton = createMenuButton("📋 All Reservations", PageComponents.PRIMARY_COLOR);
        JButton reportsButton = createMenuButton("📊 Reports & Analytics", PageComponents.SECONDARY_COLOR);
        JButton consoleButton = createMenuButton("⚙️ System Console", new Color(255, 121, 121));
        
        contentPanel.add(userManagementButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(tripManagementButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(reservationManagementButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(reportsButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(consoleButton);
        
        // Action listeners for admin menu
        userManagementButton.addActionListener(e -> {
            dispose();
            new AdminPage().display();
        });
        
        tripManagementButton.addActionListener(e -> {
            dispose();
            new TripManagementPage().display();
        });
        
        reservationManagementButton.addActionListener(e -> {
            dispose();
            new AllReservationsPage().display();
        });
        
        reportsButton.addActionListener(e -> {
            // Reports page will be implemented
            PageComponents.showStyledMessage("Info", "Reports page coming soon!", this);
        });
        
        consoleButton.addActionListener(e -> {
            dispose();
            new ConsolePage().display();
        });
    }
    
    private JButton createMenuButton(String text, Color backgroundColor) {
        JButton button = PageComponents.createStyledButton(text, backgroundColor, true);
        button.setPreferredSize(new Dimension(300, 55));
        button.setMaximumSize(new Dimension(300, 55));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        // Add subtle shadow effect
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(backgroundColor.darker(), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        return button;
    }
}