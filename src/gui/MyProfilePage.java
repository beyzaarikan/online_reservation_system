package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
    private JSpinner birthdateSpinner;
    private JCheckBox emailNotifications;
    private JCheckBox smsNotifications;
    private JCheckBox promotionalEmails;
    
    public MyProfilePage() {
        super("My Profile", 1000, 700);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("👤 My Profile");
        
        // Create tabbed pane for different sections
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(PageComponents.BACKGROUND_COLOR);
        tabbedPane.setForeground(PageComponents.TEXT_COLOR);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Personal Information Tab
        JPanel personalInfoPanel = createPersonalInfoPanel();
        tabbedPane.addTab("Personal Information", personalInfoPanel);
        
        // Security Tab
        JPanel securityPanel = createSecurityPanel();
        tabbedPane.addTab("Security", securityPanel);
        
        // Preferences Tab
        JPanel preferencesPanel = createPreferencesPanel();
        tabbedPane.addTab("Preferences", preferencesPanel);
        
        // Statistics Tab
        JPanel statsPanel = createStatsPanel();
        tabbedPane.addTab("Travel Statistics", statsPanel);
        
        // Back Button Panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        JButton backButton = PageComponents.createStyledButton("← Back to Menu", PageComponents.SECONDARY_COLOR, false);
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        backPanel.add(backButton);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(backPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Profile Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        JLabel profileIcon = new JLabel("👤");
        profileIcon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        JLabel welcomeLabel = new JLabel("Welcome back, John Doe!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(PageComponents.TEXT_COLOR);
        
        JLabel memberSinceLabel = new JLabel("Member since: January 2023");
        memberSinceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        memberSinceLabel.setForeground(PageComponents.SECONDARY_COLOR);
        
        userInfoPanel.add(welcomeLabel);
        userInfoPanel.add(memberSinceLabel);
        
        headerPanel.add(profileIcon);
        headerPanel.add(Box.createHorizontalStrut(15));
        headerPanel.add(userInfoPanel);
        
        // Personal Information Form
        JPanel formCard = createCardPanel();
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel formTitle = new JLabel("Personal Information", SwingConstants.LEFT);
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(PageComponents.TEXT_COLOR);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PageComponents.CARD_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Full Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createFieldLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        nameField = PageComponents.createStyledTextField("John Doe");
        nameField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(nameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createFieldLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = PageComponents.createStyledTextField("john.doe@example.com");
        emailField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createFieldLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = PageComponents.createStyledTextField("+90 555 123 4567");
        phoneField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(phoneField, gbc);
        
        // Gender
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createFieldLabel("Gender:"), gbc);
        gbc.gridx = 1;
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other", "Prefer not to say"});
        genderCombo.setBackground(PageComponents.INPUT_COLOR);
        genderCombo.setForeground(PageComponents.TEXT_COLOR);
        genderCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(genderCombo, gbc);
        
        // Birthdate
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createFieldLabel("Birth Date:"), gbc);
        gbc.gridx = 1;
        birthdateSpinner = createDateSpinner();
        formPanel.add(birthdateSpinner, gbc);
        
        // Address
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createFieldLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressField = PageComponents.createStyledTextField("123 Main Street, Apt 4B");
        addressField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(addressField, gbc);
        
        // City
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createFieldLabel("City:"), gbc);
        gbc.gridx = 1;
        cityField = PageComponents.createStyledTextField("Istanbul");
        cityField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(cityField, gbc);
        
        // Country
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(createFieldLabel("Country:"), gbc);
        gbc.gridx = 1;
        countryField = PageComponents.createStyledTextField("Turkey");
        countryField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(countryField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton saveButton = PageComponents.createStyledButton("💾 Save Changes", PageComponents.PRIMARY_COLOR, true);
        JButton cancelButton = PageComponents.createStyledButton("Cancel", PageComponents.SECONDARY_COLOR, false);
        
        saveButton.addActionListener(e -> savePersonalInfo());
        cancelButton.addActionListener(e -> resetPersonalInfo());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        formCard.add(formTitle);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(formPanel);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(buttonPanel);
        
        panel.add(headerPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(formCard);
        
        return panel;
    }
    
    private JPanel createSecurityPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Security Card
        JPanel securityCard = createCardPanel();
        securityCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 85, 85), 2, true),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel securityTitle = new JLabel("🔒 Security Settings", SwingConstants.LEFT);
        securityTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        securityTitle.setForeground(PageComponents.TEXT_COLOR);
        
        // Password Change Form
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBackground(PageComponents.CARD_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Current Password
        gbc.gridx = 0; gbc.gridy = 0;
        passwordPanel.add(createFieldLabel("Current Password:"), gbc);
        gbc.gridx = 1;
        currentPasswordField = createPasswordField();
        passwordPanel.add(currentPasswordField, gbc);
        
        // New Password
        gbc.gridx = 0; gbc.gridy = 1;
        passwordPanel.add(createFieldLabel("New Password:"), gbc);
        gbc.gridx = 1;
        newPasswordField = createPasswordField();
        passwordPanel.add(newPasswordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 2;
        passwordPanel.add(createFieldLabel("Confirm New Password:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = createPasswordField();
        passwordPanel.add(confirmPasswordField, gbc);
        
        // Security Info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(PageComponents.CARD_COLOR);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 193, 7), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel infoTitle = new JLabel("ℹ️ Password Requirements:");
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoTitle.setForeground(PageComponents.TEXT_COLOR);
        
        String[] requirements = {
            "• At least 8 characters long",
            "• Contains uppercase and lowercase letters",
            "• Contains at least one number",
            "• Contains at least one special character"
        };
        
        infoPanel.add(infoTitle);
        for (String req : requirements) {
            JLabel reqLabel = new JLabel(req);
            reqLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            reqLabel.setForeground(PageComponents.SECONDARY_COLOR);
            infoPanel.add(reqLabel);
        }
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton changePasswordButton = PageComponents.createStyledButton("🔐 Change Password", new Color(255, 85, 85), true);
        JButton cancelButton = PageComponents.createStyledButton("Cancel", PageComponents.SECONDARY_COLOR, false);
        
        changePasswordButton.addActionListener(e -> changePassword());
        cancelButton.addActionListener(e -> clearPasswordFields());
        
        buttonPanel.add(changePasswordButton);
        buttonPanel.add(cancelButton);
        
        securityCard.add(securityTitle);
        securityCard.add(Box.createVerticalStrut(15));
        securityCard.add(passwordPanel);
        securityCard.add(Box.createVerticalStrut(15));
        securityCard.add(infoPanel);
        securityCard.add(Box.createVerticalStrut(15));
        securityCard.add(buttonPanel);
        
        panel.add(securityCard);
        
        return panel;
    }
    
    private JPanel createPreferencesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Preferences Card
        JPanel preferencesCard = createCardPanel();
        preferencesCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel preferencesTitle = new JLabel("⚙️ Notification Preferences", SwingConstants.LEFT);
        preferencesTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        preferencesTitle.setForeground(PageComponents.TEXT_COLOR);
        
        // Notification Settings
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        notificationPanel.setBackground(PageComponents.CARD_COLOR);
        
        emailNotifications = createStyledCheckbox("📧 Email Notifications", "Receive booking confirmations and updates via email");
        smsNotifications = createStyledCheckbox("📱 SMS Notifications", "Receive important alerts via SMS");
        promotionalEmails = createStyledCheckbox("🎯 Promotional Emails", "Receive special offers and deals");
        
        emailNotifications.setSelected(true);
        smsNotifications.setSelected(true);
        
        notificationPanel.add(emailNotifications);
        notificationPanel.add(Box.createVerticalStrut(10));
        notificationPanel.add(smsNotifications);
        notificationPanel.add(Box.createVerticalStrut(10));
        notificationPanel.add(promotionalEmails);
        
        // Travel Preferences
        JPanel travelPrefsPanel = new JPanel();
        travelPrefsPanel.setLayout(new BoxLayout(travelPrefsPanel, BoxLayout.Y_AXIS));
        travelPrefsPanel.setBackground(PageComponents.CARD_COLOR);
        travelPrefsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR),
            "✈️ Travel Preferences",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            PageComponents.TEXT_COLOR
        ));
        
        JPanel currencyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        currencyPanel.setBackground(PageComponents.CARD_COLOR);
        currencyPanel.add(createFieldLabel("Preferred Currency:"));
        
        JComboBox<String> currencyCombo = new JComboBox<>(new String[]{"USD ($)", "EUR (€)", "TRY (₺)", "GBP (£)"});
        currencyCombo.setBackground(PageComponents.INPUT_COLOR);
        currencyCombo.setForeground(PageComponents.TEXT_COLOR);
        currencyCombo.setSelectedIndex(2); // TRY selected
        currencyPanel.add(currencyCombo);
        
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        languagePanel.setBackground(PageComponents.CARD_COLOR);
        languagePanel.add(createFieldLabel("Language:"));
        
        JComboBox<String> languageCombo = new JComboBox<>(new String[]{"English", "Türkçe", "Deutsch", "Français"});
        languageCombo.setBackground(PageComponents.INPUT_COLOR);
        languageCombo.setForeground(PageComponents.TEXT_COLOR);
        languagePanel.add(languageCombo);
        
        travelPrefsPanel.add(currencyPanel);
        travelPrefsPanel.add(languagePanel);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton savePrefsButton = PageComponents.createStyledButton("💾 Save Preferences", PageComponents.ACCENT_COLOR, true);
        JButton resetButton = PageComponents.createStyledButton("Reset to Default", PageComponents.SECONDARY_COLOR, false);
        
        savePrefsButton.addActionListener(e -> savePreferences());
        resetButton.addActionListener(e -> resetPreferences());
        
        buttonPanel.add(savePrefsButton);
        buttonPanel.add(resetButton);
        
        preferencesCard.add(preferencesTitle);
        preferencesCard.add(Box.createVerticalStrut(15));
        preferencesCard.add(notificationPanel);
        preferencesCard.add(Box.createVerticalStrut(20));
        preferencesCard.add(travelPrefsPanel);
        preferencesCard.add(Box.createVerticalStrut(15));
        preferencesCard.add(buttonPanel);
        
        panel.add(preferencesCard);
        
        return panel;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PageComponents.BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Statistics Cards
        JPanel statsGrid = new JPanel(new GridLayout(2, 2, 15, 15));
        statsGrid.setBackground(PageComponents.BACKGROUND_COLOR);
        
        JPanel totalTripsCard = createStatCard("Total Trips", "23", "🚗", PageComponents.PRIMARY_COLOR);
        JPanel totalSpentCard = createStatCard("Total Spent", "$2,450", "💰", PageComponents.ACCENT_COLOR);
        JPanel favDestCard = createStatCard("Favorite Destination", "Istanbul", "📍", new Color(255, 193, 7));
        JPanel membershipCard = createStatCard("Membership Level", "Gold", "⭐", new Color(255, 85, 85));
        
        statsGrid.add(totalTripsCard);
        statsGrid.add(totalSpentCard);
        statsGrid.add(favDestCard);
        statsGrid.add(membershipCard);
        
        // Recent Activity
        JPanel activityCard = createCardPanel();
        activityCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel activityTitle = new JLabel("📈 Recent Activity", SwingConstants.LEFT);
        activityTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        activityTitle.setForeground(PageComponents.TEXT_COLOR);
        
        String[] activities = {
            "✈️ Flight booked to London - June 18, 2025",
            "🚌 Bus trip completed: Istanbul → Ankara - June 15, 2025",
            "💳 Payment processed for reservation RES002",
            "📧 Confirmation email sent for upcoming trip",
            "⭐ Upgraded to Gold membership - June 10, 2025"
        };
        
        JPanel activityList = new JPanel();
        activityList.setLayout(new BoxLayout(activityList, BoxLayout.Y_AXIS));
        activityList.setBackground(PageComponents.CARD_COLOR);
        
        for (String activity : activities) {
            JLabel activityLabel = new JLabel(activity);
            activityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            activityLabel.setForeground(PageComponents.TEXT_COLOR);
            activityLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
            activityList.add(activityLabel);
        }
        
        activityCard.add(activityTitle);
        activityCard.add(Box.createVerticalStrut(15));
        activityCard.add(activityList);
        
        panel.add(statsGrid);
        panel.add(Box.createVerticalStrut(20));
        panel.add(activityCard);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, String icon, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PageComponents.CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(titleLabel);
        
        return card;
    }
    
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(PageComponents.TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }
    
    private JSpinner createDateSpinner() {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(dateModel);
        
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        spinner.setPreferredSize(new Dimension(120, 35));
        
        spinner.getEditor().getComponent(0).setBackground(PageComponents.INPUT_COLOR);
        spinner.getEditor().getComponent(0).setForeground(PageComponents.TEXT_COLOR);
        
        return spinner;
    }
    
    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setBackground(PageComponents.INPUT_COLOR);
        field.setForeground(PageComponents.TEXT_COLOR);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.SECONDARY_COLOR),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JCheckBox createStyledCheckbox(String text, String tooltip) {
        JCheckBox checkbox = new JCheckBox(text);
        checkbox.setBackground(PageComponents.CARD_COLOR);
        checkbox.setForeground(PageComponents.TEXT_COLOR);
        checkbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkbox.setToolTipText(tooltip);
        return checkbox;
    }
    
    // Action Methods
    private void savePersonalInfo() {
        PageComponents.showStyledMessage("Success", "Personal information saved successfully!", this);
    }
    
    private void resetPersonalInfo() {
        nameField.setText("John Doe");
        emailField.setText("john.doe@example.com");
        phoneField.setText("+90 555 123 4567");
        addressField.setText("123 Main Street, Apt 4B");
        cityField.setText("Istanbul");
        countryField.setText("Turkey");
        genderCombo.setSelectedIndex(0);
    }
    
    private void changePassword() {
        String current = new String(currentPasswordField.getPassword());
        String newPass = new String(newPasswordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());
        
        if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please fill in all password fields!", this);
            return;
        }
        
        if (!newPass.equals(confirm)) {
            PageComponents.showStyledMessage("Error", "New passwords do not match!", this);
            return;
        }
        
        if (newPass.length() < 8) {
            PageComponents.showStyledMessage("Error", "New password must be at least 8 characters long!", this);
            return;
        }
        
        PageComponents.showStyledMessage("Success", "Password changed successfully!", this);
        clearPasswordFields();
    }
    
    private void clearPasswordFields() {
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
    
    private void savePreferences() {
        PageComponents.showStyledMessage("Success", "Preferences saved successfully!", this);
    }
    
    private void resetPreferences() {
        emailNotifications.setSelected(true);
        smsNotifications.setSelected(true);
        promotionalEmails.setSelected(false);
        PageComponents.showStyledMessage("Info", "Preferences reset to default values!", this);
    }
}