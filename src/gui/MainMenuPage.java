// online_reservation_system/src/gui/MainMenuPage.java
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
    
    public MainMenuPage() {
        this(false); // Default olarak normal user
    }
    
    public MainMenuPage(boolean isAdmin) {
        this.isAdmin = isAdmin; // isAdmin özelliğini ayarla
        setupWindow();
        setupUI();
    }
    
    private void setupWindow() {
        setTitle(isAdmin ? "Admin Dashboard - Travel System" : "Travel Dashboard - Travel System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
   // BasePanel'den gelmediği için bu annotasyon burada olmaz.
    public void setupUI() {
        // Ana panel - gradient arkaplan
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient background - LoginPage ile aynı
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(15, 15, 35),
                    getWidth(), getHeight(), new Color(25, 25, 55)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Decorative circles - LoginPage ile aynı
                g2d.setColor(new Color(138, 43, 226, 30));
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(getWidth()-150, getHeight()-150, 200, 200);
                
                g2d.setColor(new Color(75, 0, 130, 20));
                g2d.fillOval(getWidth()-100, -50, 150, 150);
                g2d.fillOval(-100, getHeight()-100, 150, 150);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header Panel with user info and logout
        JPanel headerPanel = createHeaderPanel();
        
        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(20, 60, 40, 60));
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Menu Grid Panel
        JPanel menuPanel = createMenuPanel();
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(menuPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(20, 40, 10, 40));
        
        // User info panel with glassmorphism
        JPanel userInfoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        userInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.setOpaque(false);
        userInfoPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        // User avatar
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                // Profil dairesi - gradient
                GradientPaint avatarGradient = new GradientPaint(
                    0, 0, new Color(138, 43, 226),
                    getWidth(), getHeight(), new Color(108, 92, 231)
                );
                g2d.setPaint(avatarGradient);
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
        
        JLabel welcomeLabel = new JLabel("Welcome, " + (isAdmin ? "Admin" : (currentUser != null ? currentUser.getName() : "Guest")) + "!"); // Added null check for currentUser
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel statusLabel = new JLabel(isAdmin ? "Administrator" : "Customer");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(189, 147, 249));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(welcomeLabel);
        textPanel.add(statusLabel);
        
        userInfoPanel.add(avatarPanel);
        userInfoPanel.add(Box.createHorizontalStrut(15));
        userInfoPanel.add(textPanel);
        
        // Logout button - modern style
        JButton logoutButton = createModernButton("Logout", new Color(220, 38, 127), false);
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutButton.addActionListener(e -> {
            // Clear the logged-in user when logging out
            SessionManager.getInstance().logout();
            dispose();
            new WelcomePage().display(); // Correctly navigate to WelcomePage
        });
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel(isAdmin ? "Admin Dashboard" : "Travel Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(isAdmin ? "Manage your travel system" : "Plan your perfect journey", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);
        
        return titlePanel;
    }
    
    private JPanel createMenuPanel() {
        JPanel menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setOpaque(false);
        
        // Glassmorphism container for menu cards
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 8));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        
        menuPanel.setLayout(new GridLayout(isAdmin ? 3 : 2, 2, 25, 25));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        menuPanel.setPreferredSize(new Dimension(700, isAdmin ? 480 : 320));
        
        if (isAdmin) {
            setupAdminMenu(menuPanel);
        } else {
            setupUserMenu(menuPanel);
        }
        
        menuContainer.add(menuPanel);
        return menuContainer;
    }
    
    private void setupUserMenu(JPanel menuGrid) {
        // User menu options
        JPanel busTripsCard = createModernMenuCard("Search Bus Trips", 
            "Find and book bus journeys", new Color(138, 43, 226), "🚌");
        JPanel flightTripsCard = createModernMenuCard("Search Flights", 
            "Discover flight options", new Color(56, 189, 248), "   ✈️");
        JPanel reservationCard = createModernMenuCard("My Reservations", 
            "View your bookings", new Color(52, 211, 153), "🎫");
        JPanel profileCard = createModernMenuCard("My Profile", 
            "Manage your account", new Color(251, 146, 60), "👤");
        
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
            new AllReservationsPage().display();
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
        JPanel userManagementCard = createModernMenuCard("User Management", 
            "Manage system users", new Color(108, 92, 231), "👥");
        JPanel tripManagementCard = createModernMenuCard("Trip Management", 
            "Manage trips and routes", new Color(138, 43, 226), "🗺️");
        JPanel reservationManagementCard = createModernMenuCard("All Reservations", 
            "View all bookings", new Color(52, 211, 153), "📋");
        JPanel reportsCard = createModernMenuCard("Reports & Analytics", 
            "System reports and stats", new Color(56, 189, 248), "📊");
        JPanel consoleCard = createModernMenuCard("System Console", 
            "Advanced system tools", new Color(220, 38, 127), "⚙️");
            
        
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
                "• User statistics\n• Revenue reports\n• Trip analytics");
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
    
    private JPanel createModernMenuCard(String title, String description, Color accentColor, String emoji) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 12));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Icon panel
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                // Icon background circle with gradient
                GradientPaint iconGradient = new GradientPaint(
                    0, 0, accentColor,
                    getWidth(), getHeight(), accentColor.darker()
                );
                g2d.setPaint(iconGradient);
                g2d.fillOval(centerX - 25, centerY - 25, 50, 50);
                
                // Emoji
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
                FontMetrics fm = g2d.getFontMetrics();
                int textX = centerX - fm.stringWidth(emoji) / 2;
                int textY = centerY + fm.getAscent() / 2 - 2;
                g2d.drawString(emoji, textX, textY);
            }
        };
        iconPanel.setPreferredSize(new Dimension(60, 60));
        iconPanel.setOpaque(false);
        
        // Text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(189, 147, 249));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(descLabel);
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Enhanced glassmorphism on hover
                card.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.repaint();
            }
        });
        
        card.add(iconPanel, BorderLayout.WEST);
        card.add(Box.createHorizontalStrut(20), BorderLayout.CENTER);
        card.add(textPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private JButton createModernButton(String text, Color baseColor, boolean isPrimary) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(baseColor.brighter());
                } else {
                    g2d.setColor(baseColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
    
    private void showEnhancedFeatureDialog(String title, String message) {
        // Modern dialog with glassmorphism
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(15, 15, 35),
                    getWidth(), getHeight(), new Color(25, 25, 55)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Title
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        // Content panel with glassmorphism
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setForeground(new Color(189, 147, 249));
        messageArea.setOpaque(false);
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setBorder(null);
        
        contentPanel.add(messageArea);
        
        // OK Button
        JButton okButton = createModernButton("OK", new Color(138, 43, 226), true);
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    public void display() {
        setVisible(true);
    }
}