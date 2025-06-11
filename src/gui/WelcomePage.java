package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WelcomePage extends BasePanel {
    
    // Modern pastel renk paleti - MainMenuPage ile uyumlu
    private final Color BG_COLOR = new Color(248, 250, 252);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color PRIMARY_COLOR = new Color(129, 140, 248); // Soft indigo
    private final Color SECONDARY_COLOR = new Color(107, 114, 128);
    private final Color ACCENT_COLOR = new Color(99, 102, 241);
    private final Color SUCCESS_COLOR = new Color(52, 211, 153); // Soft emerald
    private final Color WARNING_COLOR = new Color(251, 146, 60); // Soft orange
    private final Color ERROR_COLOR = new Color(248, 113, 113); // Soft red
    private final Color INFO_COLOR = new Color(56, 189, 248); // Soft blue
    private final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private final Color TEXT_SECONDARY = new Color(75, 85, 99);
    private final Color BORDER_COLOR = new Color(229, 231, 235);
    private final Color HOVER_COLOR = new Color(243, 244, 246);
    private final Color GRADIENT_START = new Color(139, 92, 246); // Purple
    private final Color GRADIENT_END = new Color(59, 130, 246); // Blue
    
    public WelcomePage() {
        super("Travel Reservation System - Welcome", 1000, 750);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);
        
        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        
        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_COLOR);
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Menu Cards Panel
        JPanel menuPanel = createMenuPanel();
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(menuPanel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(footerPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Logo Panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(BG_COLOR);
        
        JLabel logoLabel = new JLabel("✈️ 🚌");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        logoPanel.add(logoLabel);
        headerPanel.add(logoPanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createTitlePanel() {
        JPanel titleCard = new JPanel();
        titleCard.setLayout(new BoxLayout(titleCard, BoxLayout.Y_AXIS));
        titleCard.setBackground(CARD_COLOR);
        titleCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(50, 60, 50, 60)
        ));
        titleCard.setMaximumSize(new Dimension(800, 200));
        
        JLabel titleLabel = new JLabel("Travel Reservation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Bus and Flight Tickets in One Place", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titleCard.add(titleLabel);
        titleCard.add(Box.createVerticalStrut(15));
        titleCard.add(subtitleLabel);
        
        return titleCard;
    }
    
    private JPanel createMenuPanel() {
        JPanel menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setBackground(BG_COLOR);
        
        // Question Panel
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBackground(BG_COLOR);
        questionPanel.setBorder(new EmptyBorder(30, 0, 30, 0));
        
        JLabel questionLabel = new JLabel("Do you have an account?");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        questionLabel.setForeground(TEXT_PRIMARY);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("Choose an option to continue");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subLabel.setForeground(TEXT_SECONDARY);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(subLabel);
        
        // Cards Panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 25, 0));
        cardsPanel.setBackground(BG_COLOR);
        cardsPanel.setMaximumSize(new Dimension(900, 220));
        
        // Create Account Card
        JPanel signUpCard = createActionCard("Create New Account", 
            "Join our travel community", ACCENT_COLOR, "signup");
        
        // Sign In Card
        JPanel signInCard = createActionCard("Sign In", 
            "Access your account", PRIMARY_COLOR, "signin");
        
        // Exit Card
        JPanel exitCard = createActionCard("Exit", 
            "Close the application", ERROR_COLOR, "exit");
        
        // Event listeners
        addClickListener(signUpCard, () -> {
            dispose();
            new RegisterPage().display();
        });
        
        addClickListener(signInCard, () -> {
            dispose();
            new LoginPage().display();
        });
        
        addClickListener(exitCard, () -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the application?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        cardsPanel.add(signUpCard);
        cardsPanel.add(signInCard);
        cardsPanel.add(exitCard);
        
        menuContainer.add(questionPanel);
        menuContainer.add(cardsPanel);
        
        return menuContainer;
    }
    
    private JPanel createActionCard(String title, String description, Color accentColor, String iconType) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(30, 25, 30, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(280, 220));
        
        // Icon Panel
        JPanel iconPanel = createActionIcon(accentColor, iconType);
        
        // Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(CARD_COLOR);
        textPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(descLabel);
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(HOVER_COLOR);
                textPanel.setBackground(HOVER_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentColor, 2),
                    new EmptyBorder(29, 24, 29, 24)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_COLOR);
                textPanel.setBackground(CARD_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(30, 25, 30, 25)
                ));
            }
        });
        
        card.add(iconPanel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createActionIcon(Color color, String iconType) {
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                // Background circle
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
                g2d.fillOval(centerX - 35, centerY - 35, 70, 70);
                
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(3.0f));
                
                switch (iconType) {
                    case "signup":
                        // User plus icon
                        g2d.drawOval(centerX - 8, centerY - 15, 16, 16);
                        g2d.drawArc(centerX - 15, centerY - 5, 30, 30, 30, 120);
                        g2d.drawLine(centerX + 15, centerY - 15, centerX + 15, centerY - 5);
                        g2d.drawLine(centerX + 10, centerY - 10, centerX + 20, centerY - 10);
                        break;
                    case "signin":
                        // Login arrow icon
                        g2d.drawLine(centerX - 15, centerY, centerX + 10, centerY);
                        g2d.drawLine(centerX + 5, centerY - 5, centerX + 10, centerY);
                        g2d.drawLine(centerX + 5, centerY + 5, centerX + 10, centerY);
                        g2d.drawRoundRect(centerX + 8, centerY - 12, 8, 24, 4, 4);
                        break;
                    case "exit":
                        // Exit door icon
                        g2d.drawRoundRect(centerX - 12, centerY - 15, 24, 30, 4, 4);
                        g2d.drawLine(centerX - 6, centerY - 15, centerX - 6, centerY + 15);
                        g2d.drawLine(centerX - 15, centerY, centerX - 8, centerY);
                        g2d.drawLine(centerX - 10, centerY - 3, centerX - 8, centerY);
                        g2d.drawLine(centerX - 10, centerY + 3, centerX - 8, centerY);
                        break;
                }
            }
        };
        iconPanel.setPreferredSize(new Dimension(80, 80));
        iconPanel.setOpaque(false);
        return iconPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(BG_COLOR);
        footerPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JLabel footerLabel = new JLabel("© 2025 Travel Reservation System - Safe and Fast Booking");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footerLabel.setForeground(TEXT_SECONDARY);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        footerPanel.add(footerLabel);
        
        return footerPanel;
    }
    
    private void addClickListener(JPanel card, Runnable action) {
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
    }
}