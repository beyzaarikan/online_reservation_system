package gui;
import java.awt.*;
import java.util.Optional;
import singleton.SessionManager;

import javax.swing.*;
import repository.UserRepository; 
import models.User; 
import service.UserService; 

public class LoginPage extends BasePanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeBox;
    private boolean isAdminLogin = false;
    private UserRepository userRepository; // UserRepository örneği
    private UserService userService; // UserService örneği

     public LoginPage() {
        super("Sign In - Travel System", 550, 600);
        this.userRepository = UserRepository.getInstance(); // Singleton örneğini kullan
        this.userService = new UserService(userRepository);
    }

    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        JButton backButton = PageComponents.createStyledButton("← Back", PageComponents.SECONDARY_COLOR, false);
        backButton.setPreferredSize(new Dimension(100, 25));
        backPanel.add(backButton);

        // Title Panel with icon
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PageComponents.BACKGROUND_COLOR);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));


        JLabel titleLabel = new JLabel("Welcome Back!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sign in to your account", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(PageComponents.ACCENT_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        // Enhanced Form Panel
        JPanel formPanel = createCardPanel();
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(40, 40, 30, 40)
        ));

        emailField = PageComponents.createStyledTextField("Enter your email");
        emailField.setPreferredSize(new Dimension(280, 45));
        emailField.setMaximumSize(new Dimension(280, 45));

        passwordField = PageComponents.createStyledPasswordField("Enter your password");
        passwordField.setPreferredSize(new Dimension(280, 45));
        passwordField.setMaximumSize(new Dimension(280, 45));

        // Remember me checkbox
        rememberMeBox = new JCheckBox("Remember me");
        rememberMeBox.setBackground(PageComponents.CARD_COLOR);
        rememberMeBox.setForeground(PageComponents.TEXT_COLOR);
        rememberMeBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rememberMeBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Enhanced buttons
        JButton loginButton = PageComponents.createStyledButton("Sign In", PageComponents.PRIMARY_COLOR, true);
        loginButton.setPreferredSize(new Dimension(200, 50));
        loginButton.setMaximumSize(new Dimension(200, 50));

        JButton adminLoginButton = PageComponents.createStyledButton("Admin Login", PageComponents.ACCENT_COLOR, true);
        adminLoginButton.setPreferredSize(new Dimension(200, 50));
        adminLoginButton.setMaximumSize(new Dimension(200, 50));

        // Forgot password link
        JLabel forgotLabel = new JLabel("<html><u>Forgot your password?</u></html>");
        forgotLabel.setForeground(PageComponents.ACCENT_COLOR);
        forgotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Register link
        JPanel registerPanel = new JPanel(new FlowLayout());
        registerPanel.setBackground(PageComponents.CARD_COLOR);
        JLabel noAccountLabel = new JLabel("Don't have an account? ");
        noAccountLabel.setForeground(PageComponents.TEXT_COLOR);
        noAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel registerLink = new JLabel("<html><u>Sign up here</u></html>");
        registerLink.setForeground(PageComponents.ACCENT_COLOR);
        registerLink.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerPanel.add(noAccountLabel);
        registerPanel.add(registerLink);

        formPanel.add(PageComponents.createFormField("Email Address", emailField));
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(PageComponents.createFormField("Password", passwordField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(rememberMeBox);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(adminLoginButton);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(forgotLabel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(registerPanel);

        mainPanel.add(backPanel, BorderLayout.NORTH);
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(PageComponents.BACKGROUND_COLOR);
        centerWrapper.add(titlePanel, BorderLayout.NORTH);
        centerWrapper.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        add(mainPanel);

        // Action listeners
        backButton.addActionListener(e -> {
            dispose();
            WelcomePage welcomePage =new WelcomePage();
            welcomePage.display();
        });

        loginButton.addActionListener(e -> {
            isAdminLogin = false;
            login();
        });

        adminLoginButton.addActionListener(e -> {
            isAdminLogin = true;
            login();
        });

        forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PageComponents.showStyledMessage("Info",
                    "Password recovery feature will be implemented soon!", LoginPage.this);
            }
        });

        registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new RegisterPage().display();
            }
        });

        // Enter key support
        passwordField.addActionListener(e -> {
            isAdminLogin = false;
            login();
        });
    }

    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Enhanced validation
        if (email.equals("Enter your email") || email.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter your email address!", this);
            emailField.requestFocus();
            return;
        }

        if (!isValidEmail(email)) {
            PageComponents.showStyledMessage("Error", "Please enter a valid email address!", this);
            emailField.requestFocus();
            return;
        }

        if (password.equals("Enter your password") || password.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter your password!", this);
            passwordField.requestFocus();
            return;
        }

        // Admin login validation
        if (isAdminLogin) {
            if (email.equals("admin@travel.com") && password.equals("admin123")) {
                PageComponents.showStyledMessage("Welcome!", "Admin login successful!", this);
                // Set admin session flag (you'll need to implement SessionManager)
                dispose();
                new MainMenuPage(true).display(); // true for admin
            } else {
                PageComponents.showStyledMessage("Error", "Invalid admin credentials!", this);
                return;
            }
        } else {
            Optional<User> loggedInUserOptional = userService.login(email, password);

            if (loggedInUserOptional.isPresent()) { // Basic validation
                User loggedInUser = loggedInUserOptional.get(); // Optional'dan User nesnesini al
                SessionManager.getInstance().setLoggedInUser(loggedInUser); // Giriş yapan kullanıcıyı kaydet

                PageComponents.showStyledMessage("Welcome!", "Login successful!", this);
                dispose();
                // MainMenuPage'e kullanıcı adını da gönderebilirsiniz.
                //new MainMenuPage(false, foundUser.getName()).display();
                new MainMenuPage().display(); 
            } else {
                PageComponents.showStyledMessage("Error", "Invalid email or password!", this);
            }
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
}