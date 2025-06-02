package gui;
import javax.swing.*;
import java.awt.*;

public class LoginPage extends BasePanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    
    public LoginPage() {
        super("Sign In", 500, 500);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("Sign In");
        
        // Form Panel
        JPanel formPanel = createCardPanel();
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(40, 40, 30, 40)
        ));
        
        emailField = PageComponents.createStyledTextField("Enter your email");
        passwordField = PageComponents.createStyledPasswordField("Enter your password");
        JButton loginButton = PageComponents.createStyledButton("Sign In", PageComponents.PRIMARY_COLOR, true);
        
        formPanel.add(PageComponents.createFormField("Email", emailField));
        formPanel.add(Box.createVerticalStrut(60));
        formPanel.add(PageComponents.createFormField("Password", passwordField));
        formPanel.add(Box.createVerticalStrut(45));
        formPanel.add(loginButton);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        loginButton.addActionListener(e -> login());
    }
    
    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        // Basic validation
        if (email.equals("Enter your email") || email.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter your email!", this);
            return;
        }
        
        if (password.equals("Enter your password") || password.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter your password!", this);
            return;
        }
        
        // Simulate login process
        PageComponents.showStyledMessage("Welcome!", "Login successful!", this);
        
        // Navigate to main menu
        dispose();
        new MainMenuPage().display();
    }
}