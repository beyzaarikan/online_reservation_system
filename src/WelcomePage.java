

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import gui.BasePanel;
import gui.LoginPage;
import gui.PageComponents;
import gui.RegisterPage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends BasePanel {
    
    public WelcomePage() {
        super("Travel Reservation System", 600, 650);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Logo/Icon Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        logoPanel.setPreferredSize(new Dimension(200, 120));
        
        // Create a simple travel icon using text
        JLabel logoLabel = new JLabel("✈ 🚌", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setForeground(PageComponents.ACCENT_COLOR);
        logoPanel.add(logoLabel);
        
        // Title Panel with enhanced design
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("Travel Reservation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel subtitleLabel = new JLabel("Book your bus and flight tickets easily", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitleLabel.setForeground(PageComponents.ACCENT_COLOR);
        
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        
        // Enhanced Center Panel
        JPanel centerPanel = createCardPanel();
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 3, true),
            new EmptyBorder(50, 40, 50, 40)
        ));
        
        JLabel welcomeLabel = new JLabel("Welcome to Our Platform!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(PageComponents.TEXT_COLOR);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel questionLabel = new JLabel("Ready to start your journey?", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionLabel.setForeground(new Color(189, 147, 249));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Enhanced buttons with better styling
        JButton signUpButton = PageComponents.createStyledButton("Create New Account", PageComponents.PRIMARY_COLOR, true);
        signUpButton.setPreferredSize(new Dimension(220, 50));
        signUpButton.setMaximumSize(new Dimension(220, 50));
        
        JButton signInButton = PageComponents.createStyledButton("Sign In", PageComponents.SECONDARY_COLOR, false);
        signInButton.setPreferredSize(new Dimension(220, 50));
        signInButton.setMaximumSize(new Dimension(220, 50));
        
        // Quick info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(PageComponents.CARD_COLOR);
        infoPanel.setBorder(BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 1, true));
        infoPanel.setLayout(new FlowLayout());
        infoPanel.setMaximumSize(new Dimension(400, 40));
        
        JLabel infoLabel = new JLabel("🎫 Bus & Flight Tickets | 🔒 Secure Booking | ⭐ Best Prices");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(PageComponents.ACCENT_COLOR);
        infoPanel.add(infoLabel);
        
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(signUpButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(signInButton);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(infoPanel);
        
        // Footer info
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        JLabel footerLabel = new JLabel("© 2025 Travel System - All Rights Reserved", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(150, 150, 150));
        footerPanel.add(footerLabel);
        
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(PageComponents.BACKGROUND_COLOR);
        centerWrapper.add(titlePanel, BorderLayout.NORTH);
        centerWrapper.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Action listeners with smooth transitions
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add fade effect (simulated with brief delay)
                signUpButton.setEnabled(false);
                Timer timer = new Timer(200, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new RegisterPage().display();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
        
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signInButton.setEnabled(false);
                Timer timer = new Timer(200, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new LoginPage().display();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}