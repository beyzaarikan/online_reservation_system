package gui;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import repository.UserRepository;
import service.UserService;

public class RegisterPage extends BasePanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private UserService userService;
    
    // Modern pastel renk paleti - WelcomePage ile uyumlu
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
    private final Color INPUT_BG = new Color(249, 250, 251);
    
    public RegisterPage() {
        super("Travel Reservation System - Create Account", 600, 700);
        this.userService = new UserService(UserRepository.getInstance());
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
        
        // Form Panel
        JPanel formPanel = createFormPanel();
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Footer Panel
        JPanel footerPanel = createFooterPanel();
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createVerticalStrut(20));
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
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
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
            new EmptyBorder(30, 40, 30, 40)
        ));
        titleCard.setMaximumSize(new Dimension(450, 120));
        
        JLabel titleLabel = new JLabel("Create Your Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Join our travel community today", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titleCard.add(titleLabel);
        titleCard.add(Box.createVerticalStrut(10));
        titleCard.add(subtitleLabel);
        
        return titleCard;
    }
    
    private JPanel createFormPanel() {
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(CARD_COLOR);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(40, 40, 40, 40)
        ));
        formCard.setMaximumSize(new Dimension(450, 400));
        
        // Form fields
        usernameField = createStyledTextField("Enter your username");
        passwordField = createStyledPasswordField("Enter your password");
        emailField = createStyledTextField("Enter your email");
        
        JPanel usernamePanel = createFormField("Username", usernameField);
        JPanel passwordPanel = createFormField("Password", passwordField);
        JPanel emailPanel = createFormField("Email", emailField);
        
        // Buttons
        JButton createButton = createStyledButton("Create Account", ACCENT_COLOR, true);
        JButton backButton = createStyledButton("Back to Welcome", SECONDARY_COLOR, false);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(createButton);
        buttonPanel.add(backButton);
        
        formCard.add(usernamePanel);
        formCard.add(Box.createVerticalStrut(20));
        formCard.add(passwordPanel);
        formCard.add(Box.createVerticalStrut(20));
        formCard.add(emailPanel);
        formCard.add(Box.createVerticalStrut(30));
        formCard.add(buttonPanel);
        
        // Action listeners
        createButton.addActionListener(e -> createAccount());
        backButton.addActionListener(e -> {
            dispose();
            new WelcomePage().display();
        });
        
        return formCard;
    }
    
    private JPanel createFormField(String labelText, JTextField field) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(CARD_COLOR);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        fieldPanel.add(label);
        fieldPanel.add(Box.createVerticalStrut(8));
        fieldPanel.add(field);
        
        return fieldPanel;
    }
    
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(350, 45));
        field.setMaximumSize(new Dimension(350, 45));
        field.setBackground(INPUT_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        field.setForeground(TEXT_SECONDARY);
        
        // Placeholder behavior
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                    new EmptyBorder(9, 14, 9, 14)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_SECONDARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        return field;
    }
    
    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(350, 45));
        field.setMaximumSize(new Dimension(350, 45));
        field.setBackground(INPUT_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        field.setForeground(TEXT_SECONDARY);
        field.setEchoChar((char) 0); // Show placeholder text initially
        
        // Placeholder behavior
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•');
                    field.setForeground(TEXT_PRIMARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_COLOR, 2),
                    new EmptyBorder(9, 14, 9, 14)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(field.getPassword()).trim().isEmpty()) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(TEXT_SECONDARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR, 1),
                    new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        return field;
    }
    
    private JButton createStyledButton(String text, Color bgColor, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(350, 45));
        button.setMaximumSize(new Dimension(350, 45));
        button.setBackground(bgColor);
        button.setForeground(Color.black);
        button.setBorder(BorderFactory.createLineBorder(bgColor, 1));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color hoverColor = new Color(
                    Math.max(0, bgColor.getRed() - 20),
                    Math.max(0, bgColor.getGreen() - 20),
                    Math.max(0, bgColor.getBlue() - 20)
                );
                button.setBackground(hoverColor);
                button.setBorder(BorderFactory.createLineBorder(hoverColor, 1));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createLineBorder(bgColor, 1));
            }
        });
        
        return button;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(BG_COLOR);
        footerPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JLabel footerLabel = new JLabel("Already have an account? Sign in from welcome page");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(TEXT_SECONDARY);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        footerPanel.add(footerLabel);
        
        return footerPanel;
    }
    
    private void createAccount() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        
        // Basic validation
        if (username.equals("Enter your username") || username.trim().isEmpty()) {
            showStyledMessage("Error", "Please enter a username!", ERROR_COLOR);
            return;
        }
        
        if (password.equals("Enter your password") || password.trim().isEmpty() || password.length() <= 6) {
            showStyledMessage("Error", "Password must be longer than 6 characters!", ERROR_COLOR);
            return;
        }
        
        if (email.equals("Enter your email") || email.trim().isEmpty()) {
            showStyledMessage("Error", "Please enter an email!", ERROR_COLOR);
            return;
        }
        
        try {
            // Kullanıcıyı kaydetme
            userService.registerCustomer(username, password, email, username, "");
            showStyledMessage("Success!", "Account created successfully!", SUCCESS_COLOR);

            // Navigate to login page
            dispose();
            new LoginPage().display();
        } catch (IllegalArgumentException e) {
            showStyledMessage("Registration Error", e.getMessage(), ERROR_COLOR);
        } catch (Exception e) {
            showStyledMessage("Error", "An unexpected error occurred during registration.", ERROR_COLOR);
            e.printStackTrace();
        }
    }
    
    private void showStyledMessage(String title, String message, Color accentColor) {
        // Modern stil message dialog
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BG_COLOR);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_PRIMARY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton okButton = createStyledButton("OK", accentColor, true);
        okButton.setMaximumSize(new Dimension(100, 35));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> dialog.dispose());
        
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(okButton);
        
        dialog.add(contentPanel);
        dialog.setVisible(true);
    }
}