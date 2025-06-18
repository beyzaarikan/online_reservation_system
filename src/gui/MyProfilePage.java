package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import singleton.*;
import models.*;
import singleton.*;
import models.*;
import repository.*;

public class MyProfilePage extends BasePanel {
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> genderCombo;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JPanel currentContentPanel;
    private String currentTab = "personal";
    private JLabel errorMessageLabel; 
    User user = SessionManager.getInstance().getLoggedInUser();
    UserRepository userRepository = new UserRepository();

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
        
        // Tab buttons - only Personal Info and Security
        JButton personalInfoTab = createTabButton(" Personal Info", true);
        JButton securityTab = createTabButton(" Security", false);
        
        // Add action listeners for tab switching
        personalInfoTab.addActionListener(e -> switchTab(personalInfoTab, "personal"));
        securityTab.addActionListener(e -> switchTab(securityTab, "security"));
        
        sidebar.add(personalInfoTab);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(securityTab);
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
        
        // Store reference for switching content
        this.currentContentPanel = contentPanel;
        
        // Default content - Personal Info
        JPanel personalInfoContent = createPersonalInfoContent();
        contentPanel.add(personalInfoContent, BorderLayout.CENTER);
        
        return contentPanel;
    }

    private JPanel createPersonalInfoContent() {
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
        
        userInfo.add(nameLabel);
        userInfo.add(Box.createVerticalStrut(5));
        
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
        nameField = createTextField(user.getName());
        formSection.add(nameField, gbc);
        
        // Email Address
        gbc.gridx = 0; gbc.gridy = 1;
        formSection.add(createFieldLabel("Email Address"), gbc);
        gbc.gridx = 1;
        emailField = createTextField(user.getEmail());
        formSection.add(emailField, gbc);
        
        // Gender
        gbc.gridx = 0; gbc.gridy = 2;
        formSection.add(createFieldLabel("Gender"), gbc);
        gbc.gridx = 1;
        genderCombo = createComboBox(new String[]{"Male", "Female", "Other", "Prefer not to say"});
        formSection.add(genderCombo, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 30));
        buttonPanel.setOpaque(false);
        
        JButton saveButton = createButton(" Save Changes", new Color(139, 92, 246), true);
        JButton cancelButton = createButton("Cancel", new Color(138, 92, 246), false);
        
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

    private JTextField createTextField(String text) {
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

    private JComboBox<String> createComboBox(String[] items) {
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

    private JButton createButton(String text, Color backgroundColor, boolean isPrimary) {
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

    private void switchTab(JButton activeTab, String tabType) {
        Component[] components = activeTab.getParent().getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setSelected(false);
                btn.setForeground(new Color(196, 181, 253));
            }
        }
        
        activeTab.setSelected(true);
        activeTab.setForeground(Color.WHITE);
        
        currentContentPanel.removeAll();
        
        JPanel newContent = null;
        switch (tabType) {
            case "personal":
                newContent = createPersonalInfoContent();
                break;
            case "security":
                newContent = createSecurityContent();
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

    // Action Methods
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
    }
    
    private JPanel createSecurityContent() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // Title section
        JPanel titleSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleSection.setOpaque(false);
        titleSection.setBorder(new EmptyBorder(0, 0, 40, 0));
        
        JLabel titleLabel = new JLabel(" Security Settings");
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
        currentPasswordField = createPasswordField();
        formSection.add(currentPasswordField, gbc);
        
        // New Password
        gbc.gridx = 0; gbc.gridy = 1;
        formSection.add(createFieldLabel("New Password"), gbc);
        gbc.gridx = 1;
        newPasswordField = createPasswordField();
        formSection.add(newPasswordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 2;
        formSection.add(createFieldLabel("Confirm Password"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = createPasswordField();
        formSection.add(confirmPasswordField, gbc);

        // Error message label
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formSection.add(errorMessageLabel, gbc);
        gbc.gridwidth = 1; // Reset

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 30));
        buttonPanel.setOpaque(false);

        JButton updatePasswordButton = createButton(" Update Password", new Color(139, 92, 246), true);
        JButton cancelButton = createButton("Cancel", new Color(138, 92, 246), false);

        updatePasswordButton.addActionListener(e -> updatePassword());
        cancelButton.addActionListener(e -> resetSecurityFields());

        buttonPanel.add(updatePasswordButton);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(cancelButton);

        panel.add(titleSection, BorderLayout.NORTH);
        panel.add(formSection, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPasswordField createPasswordField() {
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
    
    private void updatePassword() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (currentPassword.isEmpty()) {
            showErrorMessage("Please enter your current password!");
            return;
        }

        // Check current password
        if (!currentPassword.equals(user.getPassword())) {
            showErrorMessage("Current password is incorrect!");
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

        // Save new password
        user.setPassword(newPassword);

        showSuccessMessage("Password updated successfully!");
        resetSecurityFields();
    }

    private void resetSecurityFields() {
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}