import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.BasePanel;
import gui.LoginPage;
import gui.PageComponents;
import gui.RegisterPage;

import java.awt.*;

public class MainApplication {
    
    public static void main(String[] args) {
        // Set system look and feel for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new WelcomePage().display());
    }
}

class WelcomePage extends BasePanel {
    
    public WelcomePage() {
        super("Welcome to Our Platform", 500, 500);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PageComponents.BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        titlePanel.add(titleLabel);
        
        // Center Panel with card-like design
        JPanel centerPanel = createCardPanel();
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new EmptyBorder(40, 30, 40, 30)
        ));
        
        JLabel questionLabel = new JLabel("Do you have an account?");
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        questionLabel.setForeground(PageComponents.TEXT_COLOR);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("Choose an option");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(new Color(189, 147, 249));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton signUpButton = PageComponents.createStyledButton("Create Account", PageComponents.PRIMARY_COLOR, true);
        JButton signInButton = PageComponents.createStyledButton("Sign In", PageComponents.SECONDARY_COLOR, false);
        
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subLabel);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(signUpButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(signInButton);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        signUpButton.addActionListener(e -> {
            dispose();
            new RegisterPage().display();
        });
        
        signInButton.addActionListener(e -> {
            dispose();
            new LoginPage().display();
        });
    }
}