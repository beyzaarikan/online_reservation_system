package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import models.User;
import singleton.SessionManager;

public class MainMenuPage extends JFrame {
    private boolean isAdmin;
    
    // Modern pastel renk paleti
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
    
    public MainMenuPage() {
        this(false); // Default olarak normal user
    }
    
    public MainMenuPage(boolean isAdmin) {
        this.isAdmin = isAdmin;
        setupWindow();
        setupUI();
    }
    
    private void setupWindow() {
        setTitle(isAdmin ? "Admin Dashboard" : "Travel Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Modern Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        
        // Header Panel with user info
        JPanel headerPanel = createHeaderPanel();
        
        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BG_COLOR);
        contentPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Menu Grid Panel
        JPanel menuPanel = createMenuPanel();
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(menuPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        // User info panel
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.setBackground(BG_COLOR);
        
        // User avatar
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                // Profil dairesi
                g2d.setColor(isAdmin ? ACCENT_COLOR : PRIMARY_COLOR);
                g2d.fillOval(centerX - 20, centerY - 20, 40, 40);
                
                // Kullanıcı simgesi
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
                FontMetrics fm = g2d.getFontMetrics();
                String text = isAdmin ? "A" : "U";
                int textX = centerX - fm.stringWidth(text) / 2;
                int textY = centerY + fm.getAscent() / 2 - 2;
                g2d.drawString(text, textX, textY);
            }
        };
        avatarPanel.setPreferredSize(new Dimension(50, 50));
        avatarPanel.setOpaque(false);
        
        User currentUser = SessionManager.getInstance().getLoggedInUser(); 
        
        JLabel welcomeLabel = new JLabel("Welcome, " + (isAdmin ? "Admin" : currentUser.getName()) + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(TEXT_PRIMARY);
        
        JLabel statusLabel = new JLabel(isAdmin ? "Administrator" : "Customer");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(isAdmin ? ACCENT_COLOR : PRIMARY_COLOR);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(BG_COLOR);
        textPanel.add(welcomeLabel);
        textPanel.add(statusLabel);
        
        userInfoPanel.add(avatarPanel);
        userInfoPanel.add(Box.createHorizontalStrut(15));
        userInfoPanel.add(textPanel);
        
        // Logout button
        JButton logoutButton = createSecondaryButton("Logout");
        logoutButton.setBackground(ERROR_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> handleLogout());
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createTitlePanel() {
        JPanel titleCard = new JPanel();
        titleCard.setLayout(new BoxLayout(titleCard, BoxLayout.Y_AXIS));
        titleCard.setBackground(CARD_COLOR);
        titleCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(40, 50, 40, 50)
        ));
        
        JLabel titleLabel = new JLabel(isAdmin ? "Admin Dashboard" : "Travel Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel(isAdmin ? "Manage your travel system" : "Plan your perfect journey");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titleCard.add(titleLabel);
        titleCard.add(Box.createVerticalStrut(10));
        titleCard.add(subtitleLabel);
        
        return titleCard;
    }
    
    private JPanel createMenuPanel() {
        JPanel menuGrid = new JPanel(new GridLayout(isAdmin ? 3 : 2, 2, 25, 25));
        menuGrid.setBackground(BG_COLOR);
        menuGrid.setPreferredSize(new Dimension(700, isAdmin ? 450 : 300));
        
        if (isAdmin) {
            setupAdminMenu(menuGrid);
        } else {
            setupUserMenu(menuGrid);
        }
        
        return menuGrid;
    }
    
    private void setupUserMenu(JPanel menuGrid) {
        // User menu options
        JPanel busTripsCard = createVisualMenuCard("Search Bus Trips", 
            "Find and book bus journeys", PRIMARY_COLOR, "bus");
        JPanel flightTripsCard = createVisualMenuCard("Search Flights", 
            "Discover flight options", INFO_COLOR, "flight");
        JPanel reservationCard = createVisualMenuCard("My Reservations", 
            "View your bookings", SUCCESS_COLOR, "reservation");
        JPanel profileCard = createVisualMenuCard("My Profile", 
            "Manage your account", WARNING_COLOR, "profile");
        
        // Event listeners
        addClickListener(busTripsCard, () -> {
            dispose();
            new SearchBusTripPage().display();
        });
        
        addClickListener(flightTripsCard, () -> {
            dispose();
            new SearchFlightsPage().display();
        });
        
        addClickListener(reservationCard, () -> {
            dispose();
            new ReservationPage().display();
        });
        
        addClickListener(profileCard, () -> {
            dispose();
            new MyProfilePage().display();
        });
        
        menuGrid.add(busTripsCard);
        menuGrid.add(flightTripsCard);
        menuGrid.add(reservationCard);
        menuGrid.add(profileCard);
    }
    
    private void setupAdminMenu(JPanel menuGrid) {
        // Admin menu options
        JPanel userManagementCard = createVisualMenuCard("User Management", 
            "Manage system users", ACCENT_COLOR, "users");
        JPanel tripManagementCard = createVisualMenuCard("Trip Management", 
            "Manage trips and routes", PRIMARY_COLOR, "trips");
        JPanel reservationManagementCard = createVisualMenuCard("All Reservations", 
            "View all bookings", SUCCESS_COLOR, "allreservations");
        JPanel reportsCard = createVisualMenuCard("Reports & Analytics", 
            "System reports and stats", INFO_COLOR, "reports");
        JPanel consoleCard = createVisualMenuCard("System Console", 
            "Advanced system tools", ERROR_COLOR, "console");
        
        // Event listeners
        addClickListener(userManagementCard, () -> {
            dispose();
            new AdminPage().display();
        });
        
        addClickListener(tripManagementCard, () -> {
            dispose();
            new TripManagementPage().display();
        });
        
        addClickListener(reservationManagementCard, () -> {
            dispose();
            new AllReservationsPage().display();
        });
        
        addClickListener(reportsCard, () -> {
            showEnhancedFeatureDialog("Reports & Analytics", 
                "Reports page will be available soon.\n\nFeatures:\n" +
                "• User statistics\n• Revenue reports\n• Trip analytics", INFO_COLOR);
        });
        
        addClickListener(consoleCard, () -> {
            dispose();
            new ConsolePage().display();
        });
        
        menuGrid.add(userManagementCard);
        menuGrid.add(tripManagementCard);
        menuGrid.add(reservationManagementCard);
        menuGrid.add(reportsCard);
        menuGrid.add(consoleCard);
        menuGrid.add(new JPanel()); // Empty panel for spacing
    }
    
    private JPanel createVisualMenuCard(String title, String description, Color accentColor, String iconType) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(25, 25, 25, 25)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Görsel ikon
        JPanel iconPanel = createSimpleIcon(accentColor, iconType);
        
        // Başlık ve açıklama
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(CARD_COLOR);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(descLabel);
        
        // Hover efekti
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(HOVER_COLOR);
                textPanel.setBackground(HOVER_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(accentColor, 2),
                    new EmptyBorder(24, 24, 24, 24)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_COLOR);
                textPanel.setBackground(CARD_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(25, 25, 25, 25)
                ));
            }
        });
        
        card.add(iconPanel, BorderLayout.WEST);
        card.add(Box.createHorizontalStrut(20), BorderLayout.CENTER);
        card.add(textPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createSimpleIcon(Color color, String iconType) {
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                // Arka plan dairesi
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
                g2d.fillOval(centerX - 25, centerY - 25, 50, 50);
                
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(2.5f));
                
                switch (iconType) {
                    case "bus":
                        // Bus icon
                        g2d.drawRoundRect(centerX - 10, centerY - 6, 20, 12, 4, 4);
                        g2d.drawLine(centerX - 6, centerY - 6, centerX - 6, centerY + 6);
                        g2d.drawLine(centerX + 2, centerY - 6, centerX + 2, centerY + 6);
                        g2d.fillOval(centerX - 8, centerY + 8, 4, 4);
                        g2d.fillOval(centerX + 4, centerY + 8, 4, 4);
                        break;
                    case "flight":
                        // Plane icon
                        g2d.drawLine(centerX - 8, centerY + 2, centerX + 8, centerY - 2);
                        g2d.drawLine(centerX - 4, centerY - 4, centerX + 4, centerY);
                        g2d.drawLine(centerX + 2, centerY + 2, centerX + 6, centerY + 4);
                        break;
                    case "reservation":
                        // Ticket icon
                        g2d.drawRoundRect(centerX - 8, centerY - 4, 16, 8, 2, 2);
                        g2d.drawLine(centerX - 2, centerY - 4, centerX - 2, centerY + 4);
                        g2d.drawLine(centerX + 2, centerY - 4, centerX + 2, centerY + 4);
                        break;
                    case "profile":
                        // User icon
                        g2d.drawOval(centerX - 4, centerY - 8, 8, 8);
                        g2d.drawArc(centerX - 8, centerY - 2, 16, 16, 30, 120);
                        break;
                    case "users":
                        // Multiple users icon
                        g2d.drawOval(centerX - 8, centerY - 6, 6, 6);
                        g2d.drawOval(centerX + 2, centerY - 6, 6, 6);
                        g2d.drawArc(centerX - 10, centerY, 8, 8, 30, 120);
                        g2d.drawArc(centerX, centerY, 8, 8, 30, 120);
                        break;
                    case "trips":
                        // Route icon
                        g2d.drawOval(centerX - 8, centerY - 2, 4, 4);
                        g2d.drawOval(centerX + 4, centerY - 2, 4, 4);
                        g2d.drawLine(centerX - 4, centerY, centerX + 4, centerY);
                        break;
                    case "allreservations":
                        // List icon
                        g2d.drawLine(centerX - 8, centerY - 6, centerX + 8, centerY - 6);
                        g2d.drawLine(centerX - 8, centerY, centerX + 8, centerY);
                        g2d.drawLine(centerX - 8, centerY + 6, centerX + 8, centerY + 6);
                        break;
                    case "reports":
                        // Chart icon
                        g2d.drawLine(centerX - 8, centerY + 8, centerX - 8, centerY - 8);
                        g2d.drawLine(centerX - 8, centerY + 8, centerX + 8, centerY + 8);
                        g2d.drawLine(centerX - 6, centerY + 6, centerX - 6, centerY + 2);
                        g2d.drawLine(centerX - 2, centerY + 6, centerX - 2, centerY - 2);
                        g2d.drawLine(centerX + 2, centerY + 6, centerX + 2, centerY + 4);
                        g2d.drawLine(centerX + 6, centerY + 6, centerX + 6, centerY);
                        break;
                    case "console":
                        // Terminal icon
                        g2d.drawRoundRect(centerX - 8, centerY - 6, 16, 12, 2, 2);
                        g2d.drawLine(centerX - 4, centerY - 2, centerX - 2, centerY);
                        g2d.drawLine(centerX - 2, centerY, centerX - 4, centerY + 2);
                        g2d.drawLine(centerX, centerY + 2, centerX + 4, centerY + 2);
                        break;
                }
            }
        };
        iconPanel.setPreferredSize(new Dimension(60, 60));
        iconPanel.setOpaque(false);
        return iconPanel;
    }
    
    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(CARD_COLOR);
        button.setForeground(TEXT_SECONDARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(12, 24, 12, 24)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                    new EmptyBorder(12, 24, 12, 24)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_COLOR);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(12, 24, 12, 24)
                ));
            }
        });
        
        return button;
    }
    
    private void addClickListener(JPanel card, Runnable action) {
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
    }
    
    private void handleLogout() {
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
    }
    
    private void showEnhancedFeatureDialog(String title, String message, Color accentColor) {
        // Özel dialog oluştur
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Başlık paneli
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        headerPanel.add(titleLabel);
        
        // İçerik paneli
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setForeground(TEXT_SECONDARY);
        messageArea.setBackground(CARD_COLOR);
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setBorder(null);
        
        contentPanel.add(messageArea);
        
        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JButton okButton = createSecondaryButton("Tamam");
        okButton.setBackground(PRIMARY_COLOR);
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    public void display() {
        setVisible(true);
    }
}