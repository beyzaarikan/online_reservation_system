
package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class WelcomePage extends BasePanel {
    
    public WelcomePage() {
        super("Rezervasyon Sistemi - Hoş Geldiniz", 600, 650);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Logo/Icon Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        JLabel logoLabel = new JLabel("✈️ 🚌");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel);
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PageComponents.BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel("Rezervasyon Sistemi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        titlePanel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Otobüs ve Uçak Biletleriniz Burada", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitleLabel.setForeground(PageComponents.ACCENT_COLOR);
        
        // Center Panel with card-like design
        JPanel centerPanel = createCardPanel();
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 3, true),
            new EmptyBorder(50, 40, 50, 40)
        ));
        
        JLabel questionLabel = new JLabel("Hesabınız var mı?");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setForeground(PageComponents.TEXT_COLOR);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("Devam etmek için bir seçenek belirleyin");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(new Color(189, 147, 249));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton signUpButton = PageComponents.createStyledButton("Yeni Hesap Oluştur", PageComponents.ACCENT_COLOR, true);
        JButton signInButton = PageComponents.createStyledButton("Giriş Yap", PageComponents.PRIMARY_COLOR, true);
        JButton exitButton = PageComponents.createStyledButton("Çıkış", new Color(255, 121, 121), true);
        
        // Add some spacing and styling
        signUpButton.setPreferredSize(new Dimension(250, 50));
        signInButton.setPreferredSize(new Dimension(250, 50));
        exitButton.setPreferredSize(new Dimension(250, 50));
        
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subLabel);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(signUpButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(signInButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(exitButton);
        
        // Footer info
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        JLabel footerLabel = new JLabel("© 2025 Rezervasyon Sistemi - Güvenli ve Hızlı Rezervasyon");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(PageComponents.SECONDARY_COLOR);
        footerPanel.add(footerLabel);
        
        mainPanel.add(logoPanel, BorderLayout.NORTH);
        
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setBackground(PageComponents.BACKGROUND_COLOR);
        titleContainer.add(titlePanel);
        titleContainer.add(subtitleLabel);
        titleContainer.add(Box.createVerticalStrut(20));
        
        mainPanel.add(titleContainer, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.SOUTH);
        
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBackground(PageComponents.BACKGROUND_COLOR);
        bottomContainer.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);
        
        // Action listeners
        signUpButton.addActionListener(e -> {
            dispose();
            new RegisterPage().display();
        });
        
        signInButton.addActionListener(e -> {
            dispose();
            new LoginPage().display();
        });
        
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Uygulamadan çıkmak istediğinizden emin misiniz?",
                "Çıkış Onayı",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}