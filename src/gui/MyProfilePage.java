package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import singleton.*;
import models.*;

public class MyProfilePage extends BasePanel {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField countryField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> genderCombo;
    private JCheckBox emailNotifications;
    private JCheckBox smsNotifications;
    private JCheckBox promotionalEmails;
    private JPanel currentContentPanel;
    private String currentTab = "personal";
    
    public MyProfilePage() {
        super("My Profile", 1200, 800);
    }
    
    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(25, 0, 51)); // Purple background

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Purple gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(25, 0, 51),
                    getWidth(), getHeight(), new Color(124, 58, 237));
                
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Decorative circles
                g2d.setColor(new Color(138, 43, 240, 30));
                g2d.fillOval(-100, -100, 300, 300);
                g2d.fillOval(getWidth()-200, getHeight()-200, 300, 300);
                
                g2d.setColor(new Color(75, 0, 130, 20));
                g2d.fillOval(getWidth()-150, -100, 200, 200);
                g2d.fillOval(-150, getHeight()-150, 200, 200);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Top section with back button and title
        JPanel topSection = createTopSection();
        
        // Main content area
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setOpaque(false);
        contentArea.setBorder(new EmptyBorder(0, 60, 60, 60));
        
        // Left sidebar with tabs
        JPanel sidebar = createSidebar();
        
        // Right content panel
        JPanel rightPanel = createContentPanel();
        
        contentArea.add(sidebar, BorderLayout.WEST);
        contentArea.add(rightPanel, BorderLayout.CENTER);
        
        mainPanel.add(topSection, BorderLayout.NORTH);
        mainPanel.add(contentArea, BorderLayout.CENTER);
        
        add(mainPanel);
    }

    private JPanel createTopSection() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(30, 60, 30, 60));
        
        // Back button
        JButton backButton = new JButton("← Back to Menu") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(255, 255, 255, 20));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                
                super.paintComponent(g);
            }
        };
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setForeground(new Color(196, 181, 253));
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        
        // Title section
        JPanel titleSection = new JPanel();
        titleSection.setLayout(new BoxLayout(titleSection, BoxLayout.Y_AXIS));
        titleSection.setOpaque(false);
        
        JLabel titleLabel = new JLabel(" My Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Manage your account settings and preferences");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(196, 181, 253));
        
        titleSection.add(titleLabel);
        titleSection.add(Box.createVerticalStrut(5));
        titleSection.add(subtitleLabel);
        
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleSection, BorderLayout.CENTER);
        
        return topPanel;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(new EmptyBorder(0, 0, 0, 30));
        
        // Tab buttons
        JButton personalInfoTab = createTabButton(" Personal Info", true);
        JButton securityTab = createTabButton(" Security", false);
        JButton preferencesTab = createTabButton(" Preferences", false);
        JButton statisticsTab = createTabButton("Statistics", false);
        
        // Add action listeners for tab switching
        personalInfoTab.addActionListener(e -> switchTab(personalInfoTab, "personal"));
        securityTab.addActionListener(e -> switchTab(securityTab, "security"));
        preferencesTab.addActionListener(e -> switchTab(preferencesTab, "preferences"));
        statisticsTab.addActionListener(e -> switchTab(statisticsTab, "statistics"));
        
        sidebar.add(personalInfoTab);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(securityTab);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(preferencesTab);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(statisticsTab);
        sidebar.add(Box.createVerticalGlue());
        
        return sidebar;
    }

    private JButton createTabButton(String text, boolean isActive) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected() || getModel().isRollover()) {
                    // Active/hover state with purple background
                    g2d.setColor(new Color(139, 92, 246));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                }
                
                super.paintComponent(g);
            }
        };
        
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(isActive ? Color.WHITE : new Color(196, 181, 253));
        button.setPreferredSize(new Dimension(220, 45));
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setSelected(isActive);
        
        return button;
    }

    private JPanel createContentPanel() {
    JPanel contentPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Glassmorphism background
            g2d.setColor(new Color(255, 255, 255, 10));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
            
            // Border
            g2d.setColor(new Color(255, 255, 255, 20));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 24, 24);
        }
    };
    contentPanel.setLayout(new BorderLayout());
    contentPanel.setOpaque(false);
    contentPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
    
    // Store reference for switching content - YENİ SATIR
    this.currentContentPanel = contentPanel;
    
    // Default content - Personal Info
    JPanel personalInfoContent = createPersonalInfoContent();
    contentPanel.add(personalInfoContent, BorderLayout.CENTER);
    
    return contentPanel;
}

    private JPanel createPersonalInfoContent() {
        User user = SessionManager.getInstance().getLoggedInUser();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Welcome section with profile picture
        JPanel welcomeSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        welcomeSection.setOpaque(false);
        welcomeSection.setBorder(new EmptyBorder(0, 0, 40, 0));
        
        // Profile picture circle
        JPanel profilePic = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Circle background
                g2d.setColor(new Color(139, 92, 246));
                g2d.fillOval(0, 0, getWidth(), getHeight());
                
                // User icon
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 32));
                FontMetrics fm = g2d.getFontMetrics();
                String icon = "👤";
                int x = (getWidth() - fm.stringWidth(icon)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(icon, x, y);
            }
        };
        profilePic.setPreferredSize(new Dimension(80, 80));
        profilePic.setOpaque(false);
        
        JPanel userInfo = new JPanel();
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        userInfo.setOpaque(false);
        userInfo.setBorder(new EmptyBorder(10, 20, 10, 0));
        
        JLabel nameLabel = new JLabel("Welcome back, " + user.getName() + "!");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);
        
        JLabel memberLabel = new JLabel("Member since: January 2023");
        memberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        memberLabel.setForeground(new Color(196, 181, 253));
        
        userInfo.add(nameLabel);
        userInfo.add(Box.createVerticalStrut(5));
        userInfo.add(memberLabel);
        
        welcomeSection.add(profilePic);
        welcomeSection.add(userInfo);
        
        // Form section
        JPanel formSection = new JPanel(new GridBagLayout());
        formSection.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 40);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        formSection.add(createFieldLabel("Full Name"), gbc);
        gbc.gridx = 1;
        nameField = createModernTextField(user.getName());
        formSection.add(nameField, gbc);
        
        // Email Address
        gbc.gridx = 0; gbc.gridy = 1;
        formSection.add(createFieldLabel("Email Address"), gbc);
        gbc.gridx = 1;
        emailField = createModernTextField(user.getEmail());
        formSection.add(emailField, gbc);
        
        // Gender
        gbc.gridx = 0; gbc.gridy = 2;
        formSection.add(createFieldLabel("Gender"), gbc);
        gbc.gridx = 1;
        genderCombo = createModernComboBox(new String[]{"Male", "Female", "Other", "Prefer not to say"});
        formSection.add(genderCombo, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 30));
        buttonPanel.setOpaque(false);
        
        JButton saveButton = createModernButton(" Save Changes", new Color(139, 92, 246), true);
        JButton cancelButton = createModernButton("Cancel", new Color(138, 92, 246), false);
        
        saveButton.addActionListener(e -> savePersonalInfo());
        cancelButton.addActionListener(e -> resetPersonalInfo());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelButton);
        
        panel.add(welcomeSection, BorderLayout.NORTH);
        panel.add(formSection, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(203, 213, 225));
        label.setPreferredSize(new Dimension(120, 25));
        return label;
    }

    private JTextField createModernTextField(String text) {
        JTextField field = new JTextField(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(139, 92, 246));
                    g2d.setStroke(new BasicStroke(2));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.setStroke(new BasicStroke(1));
                }
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                super.paintComponent(g);
            }
        };
        
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        field.setPreferredSize(new Dimension(300, 45));
        
        return field;
    }

    private JComboBox<String> createModernComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<String>(items) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(139, 92, 246));
                    g2d.setStroke(new BasicStroke(2));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.setStroke(new BasicStroke(1));
                }
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                super.paintComponent(g);
            }
        };
        
        combo.setOpaque(false);
        combo.setForeground(Color.WHITE);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(300, 45));
        combo.setBackground(new Color(255, 255, 255, 10));
        
        return combo;
    }

    private JTextField createModernDateField() {
        JTextField dateField = new JTextField("01.01.1990") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(139, 92, 246));
                    g2d.setStroke(new BasicStroke(2));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.setStroke(new BasicStroke(1));
                }
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                super.paintComponent(g);
            }
        };
        
        dateField.setOpaque(false);
        dateField.setForeground(Color.WHITE);
        dateField.setCaretColor(Color.WHITE);
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateField.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        dateField.setPreferredSize(new Dimension(300, 45));
        
        // Add calendar icon at the end
        JPanel datePanel = new JPanel(new BorderLayout());
        datePanel.setOpaque(false);
        datePanel.add(dateField, BorderLayout.CENTER);
        
        JLabel calendarIcon = new JLabel("📅");
        calendarIcon.setBorder(new EmptyBorder(0, 0, 0, 10));
        datePanel.add(calendarIcon, BorderLayout.EAST);
        
        return dateField;
    }

    private JButton createModernButton(String text, Color backgroundColor, boolean isPrimary) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color bgColor = backgroundColor;
                if (getModel().isPressed()) {
                    bgColor = new Color(
                        Math.max(0, backgroundColor.getRed() - 30),
                        Math.max(0, backgroundColor.getGreen() - 30),
                        Math.max(0, backgroundColor.getBlue() - 30)
                    );
                } else if (getModel().isRollover()) {
                    bgColor = new Color(
                        Math.min(255, backgroundColor.getRed() + 20),
                        Math.min(255, backgroundColor.getGreen() + 20),
                        Math.min(255, backgroundColor.getBlue() + 20)
                    );
                }
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                super.paintComponent(g);
            }
        };
        
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        
        return button;
    }

    // Tab switching functionality
   private void switchTab(JButton activeTab, String tabType) {
    // Reset all tab buttons
    Component[] components = activeTab.getParent().getComponents();
    for (Component comp : components) {
        if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            btn.setSelected(false);
            btn.setForeground(new Color(196, 181, 253));
        }
    }
    
    // Set active tab
    activeTab.setSelected(true);
    activeTab.setForeground(Color.WHITE);
    
    // Remove current content
    currentContentPanel.removeAll();
    
    // Add new content based on tab type
    JPanel newContent = null;
    switch (tabType) {
        case "personal":
            newContent = createPersonalInfoContent();
            break;
        case "security":
            newContent = createSecurityContent();
            break;
        case "preferences":
            newContent = createPreferencesContent();
            break;
        case "statistics":
            newContent = createStatisticsContent();
            break;
    }
    
    if (newContent != null) {
        currentContentPanel.add(newContent, BorderLayout.CENTER);
        currentTab = tabType;
    }
    
    // Refresh the panel
    currentContentPanel.revalidate();
    currentContentPanel.repaint();
}

    // Action Methods (keeping your original methods)
    private void savePersonalInfo() {
        try {
            User user = SessionManager.getInstance().getLoggedInUser();
            
            if (nameField.getText().trim().isEmpty()) {
                showErrorMessage("Name cannot be empty!");
                return;
            }
            
            if (emailField.getText().trim().isEmpty() || !isValidEmail(emailField.getText())) {
                showErrorMessage("Please enter a valid email address!");
                return;
            }
            
            user.setName(nameField.getText().trim());
            user.setEmail(emailField.getText().trim());
            
            showSuccessMessage("Personal information updated successfully!");
            
        } catch (Exception e) {
            showErrorMessage("Error updating personal information: " + e.getMessage());
        }
    }

    private void resetPersonalInfo() {
        User user = SessionManager.getInstance().getLoggedInUser();
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        genderCombo.setSelectedIndex(0);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }private JPanel createSecurityContent() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setOpaque(false);
    
    // Title section
    JPanel titleSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    titleSection.setOpaque(false);
    titleSection.setBorder(new EmptyBorder(0, 0, 40, 0));
    
    JLabel titleLabel = new JLabel("🔐 Security Settings");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    titleLabel.setForeground(Color.WHITE);
    
    JLabel subtitleLabel = new JLabel("Manage your password and security preferences");
    subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    subtitleLabel.setForeground(new Color(196, 181, 253));
    
    JPanel titleInfo = new JPanel();
    titleInfo.setLayout(new BoxLayout(titleInfo, BoxLayout.Y_AXIS));
    titleInfo.setOpaque(false);
    titleInfo.add(titleLabel);
    titleInfo.add(Box.createVerticalStrut(5));
    titleInfo.add(subtitleLabel);
    
    titleSection.add(titleInfo);
    
    // Form section
    JPanel formSection = new JPanel(new GridBagLayout());
    formSection.setOpaque(false);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 0, 15, 40);
    gbc.anchor = GridBagConstraints.WEST;
    
    // Current Password
    gbc.gridx = 0; gbc.gridy = 0;
    formSection.add(createFieldLabel("Current Password"), gbc);
    gbc.gridx = 1;
    currentPasswordField = createModernPasswordField();
    formSection.add(currentPasswordField, gbc);
    
    // New Password
    gbc.gridx = 0; gbc.gridy = 1;
    formSection.add(createFieldLabel("New Password"), gbc);
    gbc.gridx = 1;
    newPasswordField = createModernPasswordField();
    formSection.add(newPasswordField, gbc);
    
    // Confirm Password
    gbc.gridx = 0; gbc.gridy = 2;
    formSection.add(createFieldLabel("Confirm Password"), gbc);
    gbc.gridx = 1;
    confirmPasswordField = createModernPasswordField();
    formSection.add(confirmPasswordField, gbc);
    
    // Buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 30));
    buttonPanel.setOpaque(false);
    
    JButton updatePasswordButton = createModernButton("🔑 Update Password", new Color(139, 92, 246), true);
    JButton cancelButton = createModernButton("Cancel", new Color(138, 92, 246), false);
    
    updatePasswordButton.addActionListener(e -> updatePassword());
    cancelButton.addActionListener(e -> resetSecurityFields());
    
    buttonPanel.add(updatePasswordButton);
    buttonPanel.add(Box.createHorizontalStrut(15));
    buttonPanel.add(cancelButton);
    
    panel.add(titleSection, BorderLayout.NORTH);
    panel.add(formSection, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
    
    return panel;
    }private JPanel createPreferencesContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Title section
        JPanel titleSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleSection.setOpaque(false);
        titleSection.setBorder(new EmptyBorder(0, 0, 40, 0));
        
        JLabel titleLabel = new JLabel("⚙️ Preferences");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Customize your experience and notification settings");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(196, 181, 253));
        
        JPanel titleInfo = new JPanel();
        titleInfo.setLayout(new BoxLayout(titleInfo, BoxLayout.Y_AXIS));
        titleInfo.setOpaque(false);
        titleInfo.add(titleLabel);
        titleInfo.add(Box.createVerticalStrut(5));
        titleInfo.add(subtitleLabel);
        
        titleSection.add(titleInfo);
        
        // Preferences section
        JPanel preferencesSection = new JPanel();
        preferencesSection.setLayout(new BoxLayout(preferencesSection, BoxLayout.Y_AXIS));
        preferencesSection.setOpaque(false);
        
        // Notification Settings
        JLabel notificationLabel = new JLabel("📱 Notification Settings");
        notificationLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        notificationLabel.setForeground(Color.WHITE);
        notificationLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        emailNotifications = createModernCheckBox("📧 Email Notifications", true);
        smsNotifications = createModernCheckBox("📱 SMS Notifications", false);
        promotionalEmails = createModernCheckBox("🎯 Promotional Emails", true);
        
        preferencesSection.add(notificationLabel);
        preferencesSection.add(emailNotifications);
        preferencesSection.add(Box.createVerticalStrut(15));
        preferencesSection.add(smsNotifications);
        preferencesSection.add(Box.createVerticalStrut(15));
        preferencesSection.add(promotionalEmails);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 30));
        buttonPanel.setOpaque(false);
        
        JButton savePreferencesButton = createModernButton("💾 Save Preferences", new Color(139, 92, 246), true);
        JButton resetButton = createModernButton("Reset", new Color(138, 92, 246), false);
        
        savePreferencesButton.addActionListener(e -> savePreferences());
        resetButton.addActionListener(e -> resetPreferences());
        
        buttonPanel.add(savePreferencesButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(resetButton);
        
        panel.add(titleSection, BorderLayout.NORTH);
        panel.add(preferencesSection, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }private JPanel createStatisticsContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Title section
        JPanel titleSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleSection.setOpaque(false);
        titleSection.setBorder(new EmptyBorder(0, 0, 40, 0));
        
        JLabel titleLabel = new JLabel("📊 Account Statistics");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("View your account activity and usage statistics");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(196, 181, 253));
        
        JPanel titleInfo = new JPanel();
        titleInfo.setLayout(new BoxLayout(titleInfo, BoxLayout.Y_AXIS));
        titleInfo.setOpaque(false);
        titleInfo.add(titleLabel);
        titleInfo.add(Box.createVerticalStrut(5));
        titleInfo.add(subtitleLabel);
        
        titleSection.add(titleInfo);
        
        // Statistics cards
        JPanel statsSection = new JPanel(new GridLayout(2, 2, 20, 20));
        statsSection.setOpaque(false);
        
        // Create statistic cards
        JPanel totalLoginsCard = createStatCard("🔐 Total Logins", "156", "times");
        JPanel lastLoginCard = createStatCard("⏰ Last Login", "2 hours", "ago");
        JPanel accountAgeCard = createStatCard("📅 Account Age", "2 years", "3 months");
        JPanel settingsChangedCard = createStatCard("⚙️ Settings Changed", "12", "times");
        
        statsSection.add(totalLoginsCard);
        statsSection.add(lastLoginCard);
        statsSection.add(accountAgeCard);
        statsSection.add(settingsChangedCard);
        
        panel.add(titleSection, BorderLayout.NORTH);
        panel.add(statsSection, BorderLayout.CENTER);
        
        return panel;
    }private JPasswordField createModernPasswordField() {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(139, 92, 246));
                    g2d.setStroke(new BasicStroke(2));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.setStroke(new BasicStroke(1));
                }
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                super.paintComponent(g);
            }
        };
        
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        field.setPreferredSize(new Dimension(300, 45));
        
        return field;
    }
    
    private JCheckBox createModernCheckBox(String text, boolean selected) {
        JCheckBox checkBox = new JCheckBox(text, selected) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                super.paintComponent(g);
            }
        };
        
        checkBox.setOpaque(false);
        checkBox.setForeground(Color.WHITE);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setFocusPainted(false);
        
        return checkBox;
    }
    
    private JPanel createStatCard(String title, String value, String unit) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Card background
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
            }
        };
        
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(196, 181, 253));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel unitLabel = new JLabel(unit);
        unitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        unitLabel.setForeground(new Color(196, 181, 253));
        unitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(unitLabel);
        
        return card;
    }
    
    private void updatePassword() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (currentPassword.isEmpty()) {
            showErrorMessage("Please enter your current password!");
            return;
        }
        
        if (newPassword.isEmpty()) {
            showErrorMessage("Please enter a new password!");
            return;
        }
        
        if (newPassword.length() < 6) {
            showErrorMessage("New password must be at least 6 characters long!");
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            showErrorMessage("New password and confirmation do not match!");
            return;
        }
        
        showSuccessMessage("Password updated successfully!");
        resetSecurityFields();
    }
    
    private void resetSecurityFields() {
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
    
    private void savePreferences() {
        showSuccessMessage("Preferences saved successfully!");
    }
    
    private void resetPreferences() {
        emailNotifications.setSelected(true);
        smsNotifications.setSelected(false);
        promotionalEmails.setSelected(true);
    }
    
    
    }