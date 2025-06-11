package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class WelcomePage extends BasePanel {
    
    public WelcomePage() {
        super("Travel Reservation System - Welcome", 1000, 750);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 15, 35));

        // Ana panel - gradient arkaplan (LoginPage ile aynı)
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
                
                // Additional decorative elements for welcome page
                g2d.setColor(new Color(138, 43, 226, 15));
                g2d.fillOval(getWidth()/2 - 100, -75, 200, 150);
                g2d.fillOval(getWidth()/2 - 75, getHeight() - 75, 150, 150);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Header with logo
        JPanel headerPanel = createHeaderPanel();
        
        // Title section
        JPanel titlePanel = createTitlePanel();
        
        // Main content panel with glassmorphism effect
        JPanel contentPanel = createContentPanel();
        
        // Footer
        JPanel footerPanel = createFooterPanel();

        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(headerPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(titlePanel);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(contentPanel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(footerPanel);
        centerPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        
        // Logo with modern styling
        JLabel logoLabel = new JLabel("✈️ 🚌", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(logoLabel);
        return headerPanel;
    }
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("Travel Reservation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Bus and Flight Tickets in One Place", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(12));
        titlePanel.add(subtitleLabel);
        
        return titlePanel;
    }
    
    private JPanel createContentPanel() {
        // Main content panel with glassmorphism effect (LoginPage tarzı)
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
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        contentPanel.setMaximumSize(new Dimension(700, 450));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Question section
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setOpaque(false);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JLabel questionLabel = new JLabel("Do you have an account?", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("Choose an option to continue", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subLabel.setForeground(new Color(189, 147, 249));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(subLabel);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        
        // Create Account Button
        JButton signUpButton = createModernButton("Create New Account", new Color(138, 43, 226));
        signUpButton.setMaximumSize(new Dimension(400, 55));
        
        // Sign In Button
        JButton signInButton = createModernButton("Sign In", new Color(108, 92, 231));
        signInButton.setMaximumSize(new Dimension(400, 55));
        
        // Exit Button
        JButton exitButton = createModernButton("Exit Application", new Color(220, 38, 127));
        exitButton.setMaximumSize(new Dimension(400, 55));

        // Action listeners
        signUpButton.addActionListener(e -> {
            dispose();
            new RegisterPage().display();
        });
        
        signInButton.addActionListener(e -> {
            dispose();
            new LoginPage().display();
        });
        
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the application?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        buttonsPanel.add(signUpButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(signInButton);
        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(exitButton);

        contentPanel.add(questionPanel);
        contentPanel.add(buttonsPanel);
        
        return contentPanel;
    }
    
    private JButton createModernButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color buttonColor;
                if (getModel().isPressed()) {
                    buttonColor = baseColor.darker();
                } else if (getModel().isRollover()) {
                    buttonColor = baseColor.brighter();
                } else {
                    buttonColor = baseColor;
                }
                
                // Gradient effect for buttons
                GradientPaint gradient = new GradientPaint(
                    0, 0, buttonColor,
                    0, getHeight(), buttonColor.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Subtle inner highlight
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()/2-2, 10, 10);
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(400, 55));
        
        // Hover animation effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.repaint();
            }
        });
        
        return button;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        
        JLabel footerLabel = new JLabel("© 2025 Travel Reservation System - Safe and Fast Booking");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footerLabel.setForeground(new Color(189, 147, 249, 150));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        footerPanel.add(footerLabel);
        
        return footerPanel;
    }
}