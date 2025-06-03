package gui;
import java.awt.*;
import javax.swing.*;

public class RegisterPage extends BasePanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    
    public RegisterPage() {
        super("Create Your Account", 500, 500);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("Create Account");
        
        // Form Panel
        JPanel formPanel = createCardPanel();
        
        usernameField = PageComponents.createStyledTextField("Enter your username");
        passwordField = PageComponents.createStyledPasswordField("Enter your password");
        emailField = PageComponents.createStyledTextField("Enter your email");
        JButton createButton = PageComponents.createStyledButton("Create Account", PageComponents.ACCENT_COLOR, true);
        
        formPanel.add(PageComponents.createFormField("Username", usernameField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(PageComponents.createFormField("Password", passwordField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(PageComponents.createFormField("Email", emailField));
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(createButton);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        createButton.addActionListener(e -> createAccount());
    }
    
    private void createAccount() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        
        // Basic validation
        if (username.equals("Enter your username") || username.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter a username!", this);
            return;
        }
        
        if (password.equals("Enter your password") || password.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter a password!", this);
            return;
        }
        
        if (email.equals("Enter your email") || email.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter an email!", this);
            return;
        }
        
        // Simulate account creation
        PageComponents.showStyledMessage("Success!", "Account created successfully!", this);
        
        // Navigate to login page
        dispose();
        new LoginPage().display();
    }
}