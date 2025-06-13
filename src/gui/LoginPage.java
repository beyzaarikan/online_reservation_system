package gui;
import java.awt.*;
import java.util.Optional;
import javax.swing.*;
import models.User;
import repository.UserRepository;
import service.UserService; 
import singleton.SessionManager; 

public class LoginPage extends BasePanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeBox;
    private boolean isAdminLogin = false;
    private UserRepository userRepository;
    private UserService userService;

    public LoginPage() {
        super("Sign In - Travel System", 600, 700);
        this.userRepository = UserRepository.getInstance();
        this.userService = new UserService(userRepository);
    }

    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 15, 35));

        // Ana panel - gradient arkaplan
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
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createModernButton("← Back", new Color(108, 92, 231), false);
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backPanel.add(backButton);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));

        JLabel titleLabel = new JLabel("Welcome Back!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sign in to your account", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);

        // Form panel with glassmorphism effect
        JPanel formPanel = new JPanel() {
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
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formPanel.setMaximumSize(new Dimension(400, 600));

        // Form fields
        emailField = createModernTextField("Enter your email");
        passwordField = createModernPasswordField("Enter your password");

        // Remember me checkbox
        rememberMeBox = new JCheckBox("Remember me");
        rememberMeBox.setOpaque(false);
        rememberMeBox.setForeground(Color.WHITE);
        rememberMeBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rememberMeBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton loginButton = createModernButton("Sign In", new Color(138, 43, 226), true);
        loginButton.setMaximumSize(new Dimension(300, 50));

        JButton adminLoginButton = createModernButton("Admin Login", new Color(108, 92, 231), true);
        adminLoginButton.setMaximumSize(new Dimension(300, 50));

        // Links
        JLabel forgotLabel = createLinkLabel("Forgot your password?");
        JPanel registerPanel = createRegisterPanel();

        // Add components to form
        formPanel.add(createFieldPanel("Email Address", emailField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createFieldPanel("Password", passwordField));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(rememberMeBox);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(adminLoginButton);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(forgotLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(registerPanel);

        centerPanel.add(titlePanel);
        centerPanel.add(formPanel);

        mainPanel.add(backPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Action listeners
        backButton.addActionListener(e -> {
            dispose();
            WelcomePage welcomePage = new WelcomePage();
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

        passwordField.addActionListener(e -> {
            isAdminLogin = false;
            login();
        });
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(138, 43, 226));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                }
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        field.setPreferredSize(new Dimension(300, 45));
        field.setMaximumSize(new Dimension(300, 45));
        field.setText(placeholder);
        field.setForeground(new Color(150, 150, 150));
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(150, 150, 150));
                }
            }
        });
        
        return field;
    }

    private JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(138, 43, 226));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                }
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        field.setPreferredSize(new Dimension(300, 45));
        field.setMaximumSize(new Dimension(300, 45));
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(new Color(150, 150, 150));
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                    field.setEchoChar('•');
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(new Color(150, 150, 150));
                }
            }
        });
        
        return field;
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
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return button;
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(189, 147, 249));
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);
        
        return panel;
    }

    private JLabel createLinkLabel(String text) {
        JLabel label = new JLabel("<html><u>" + text + "</u></html>");
        label.setForeground(new Color(189, 147, 249));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setOpaque(false);
        
        JLabel noAccountLabel = new JLabel("Don't have an account? ");
        noAccountLabel.setForeground(Color.black);
        noAccountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel registerLink = new JLabel("<html><u>Sign up here</u></html>");
        registerLink.setForeground(new Color(189, 147, 249));
        registerLink.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new RegisterPage().display();
            }
        });

        panel.add(noAccountLabel);
        panel.add(registerLink);
        return panel;
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
            if (email.equals("admin@travel.com") && password.equals("admin@travel.com")) {
                PageComponents.showStyledMessage("Welcome!", "Admin login successful!", this);
                dispose();
                new MainMenuPage(true).display();
            } else {
                PageComponents.showStyledMessage("Error", "Invalid admin credentials!", this);
                return;
            }
        } else {
            Optional<User> loggedInUserOptional = userService.login(email, password);

            if (loggedInUserOptional.isPresent()) {
                User loggedInUser = loggedInUserOptional.get();
                SessionManager.getInstance().setLoggedInUser(loggedInUser);

                dispose();
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