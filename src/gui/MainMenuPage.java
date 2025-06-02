package gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuPage extends BasePanel {
    
    public MainMenuPage() {
        super("Main Menu", 600, 500);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("Main Menu");
        
        // Center Panel with menu options
        JPanel centerPanel = createCardPanel();
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(40, 30, 40, 30)
        ));
        
        JLabel welcomeLabel = new JLabel("Welcome to the System!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeLabel.setForeground(PageComponents.TEXT_COLOR);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("Choose an option", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(new Color(189, 147, 249));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton tripSearchButton = PageComponents.createStyledButton("Search Trips", PageComponents.PRIMARY_COLOR, true);
        JButton reservationButton = PageComponents.createStyledButton("My Reservations", PageComponents.SECONDARY_COLOR, false);
        JButton adminButton = PageComponents.createStyledButton("Admin Panel", PageComponents.ACCENT_COLOR, true);
        JButton consoleButton = PageComponents.createStyledButton("Console", new Color(255, 121, 121), true);
        JButton logoutButton = PageComponents.createStyledButton("Logout", new Color(255, 85, 85), true);
        
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subLabel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(tripSearchButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(reservationButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(adminButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(consoleButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(logoutButton);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        tripSearchButton.addActionListener(e -> {
            dispose();
            new TripSearchPage().display();
        });
        
        reservationButton.addActionListener(e -> {
            dispose();
            new ReservationPage().display();
        });
        
        adminButton.addActionListener(e -> {
            dispose();
            new AdminPage().display();
        });
        
        consoleButton.addActionListener(e -> {
            dispose();
            new ConsolePage().display();
        });
        
        logoutButton.addActionListener(e -> {
            dispose();
            // Return to welcome page (you'll need to create this)
            System.exit(0); // For now, just exit
        });
    }
}
