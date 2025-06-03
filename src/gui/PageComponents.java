package gui;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PageComponents {
    // Modern color palette - Daha zengin renkler
    public static final Color BACKGROUND_COLOR = new Color(145, 216, 237);
    public static final Color CARD_COLOR = new Color(30, 32, 44);
    public static final Color PRIMARY_COLOR = new Color(79, 172, 254);
    public static final Color PRIMARY_HOVER = new Color(104, 188, 255);
    public static final Color SECONDARY_COLOR = new Color(75, 85, 99);
    public static final Color TEXT_COLOR = new Color(243, 244, 246);
    public static final Color ACCENT_COLOR = new Color(16, 185, 129);
    public static final Color INPUT_COLOR = new Color(55, 65, 81);
    public static final Color ERROR_COLOR = new Color(239, 68, 68);
    public static final Color WARNING_COLOR = new Color(245, 158, 11);
    public static final Color SUCCESS_COLOR = new Color(34, 197, 94);
    public static final Color ADMIN_COLOR = new Color(147, 51, 234);

    public static JButton createStyledButton(String text, Color backgroundColor, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(isPrimary ? Color.WHITE : TEXT_COLOR);
        button.setBackground(backgroundColor);
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 42));
        button.setPreferredSize(new Dimension(150, 42));
        
        // Gradient effect
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color hoverColor = backgroundColor.brighter();
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    public static JButton createIconButton(String text, String iconPath, Color backgroundColor, boolean isPrimary) {
        JButton button = createStyledButton(text, backgroundColor, isPrimary);
        // Icon eklenebilir - şimdilik sadece text
        return button;
    }

    public static JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        field.setBackground(INPUT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        field.setCaretColor(TEXT_COLOR);
        field.setMaximumSize(new Dimension(280, 40));
        field.setPreferredSize(new Dimension(200, 40));
        
        // Add placeholder effect
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_COLOR);
                    field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                        new EmptyBorder(9, 12, 9, 12)
                    ));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true),
                    new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });
        
        return field;
    }

    public static JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        field.setBackground(INPUT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        field.setCaretColor(TEXT_COLOR);
        field.setMaximumSize(new Dimension(280, 40));
        field.setPreferredSize(new Dimension(200, 40));
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_COLOR);
                    field.setEchoChar('•');
                    field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                        new EmptyBorder(9, 12, 9, 12)
                    ));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getPassword().length == 0) {
                    field.setForeground(Color.GRAY);
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true),
                    new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });
        
        return field;
    }

    public static JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setBackground(INPUT_COLOR);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 1, true),
            new EmptyBorder(5, 8, 5, 8)
        ));
        comboBox.setMaximumSize(new Dimension(280, 40));
        comboBox.setPreferredSize(new Dimension(200, 40));
        return comboBox;
    }

    public static JPanel createFormField(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);
        
        return panel;
    }

    public static JPanel createInfoCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(200, 100));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(Color.GRAY);
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }

    public static void showStyledMessage(String title, String message, JFrame parent) {
        showStyledMessage(title, message, parent, PRIMARY_COLOR);
    }

    public static void showStyledMessage(String title, String message, JFrame parent, Color accentColor) {
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            new EmptyBorder(30, 30, 20, 30)
        ));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + 
                                       message.replace("\n", "<br>") + "</div></html>", 
                                       SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        
        JButton okButton = createStyledButton("OK", accentColor, true);
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(okButton);
        
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    public static boolean showConfirmDialog(String title, String message, JFrame parent) {
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);
        
        final boolean[] result = {false};
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNING_COLOR, 2, true),
            new EmptyBorder(30, 30, 20, 30)
        ));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + 
                                       message.replace("\n", "<br>") + "</div></html>", 
                                       SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CARD_COLOR);
        
        JButton yesButton = createStyledButton("Yes", SUCCESS_COLOR, true);
        JButton noButton = createStyledButton("No", ERROR_COLOR, true);
        
        yesButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        
        noButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });
        
        buttonPanel.add(yesButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(noButton);
        
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
        
        return result[0];
    }
}