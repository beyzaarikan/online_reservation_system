package gui;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PageComponents {
public static final Color BACKGROUND_COLOR = new Color(145, 216, 237); // Açık mavi

public static final Color CARD_COLOR = new Color(30, 32, 44); // Koyu lacivert/gri

public static final Color PRIMARY_COLOR = new Color(79, 172, 254); // Açık mavi

public static final Color SECONDARY_COLOR = new Color(75, 85, 99); // Gri

public static final Color TEXT_COLOR = new Color(243, 244, 246); // Açık gri

public static final Color INPUT_COLOR = new Color(55, 65, 81); // Koyu gri-mavi

public static final Color ERROR_COLOR = new Color(239, 68, 68); // Kırmızı

public static final Color WARNING_COLOR = new Color(245, 158, 11); // Turuncu

public static final Color SUCCESS_COLOR = new Color(34, 197, 94); // Yeşil


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


    public static void showStyledMessage(String title, String message, JFrame parent) {
    showStyledMessage(title, message, parent, PRIMARY_COLOR);
}

    public static void showStyledMessage(String title, String message, JFrame parent, Color accentColor) {
        JDialog dialog = new JDialog(parent, title, true);
        dialog.getContentPane().setBackground(BACKGROUND_COLOR);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2, true),
            new EmptyBorder(30, 30, 20, 30)
        ));
        
        // Create message label with HTML formatting
        JLabel messageLabel = new JLabel("<html><div style='text-align: left; line-height: 1.4;'>" + 
                                       message.replace("\n", "<br>") + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        messageLabel.setVerticalAlignment(SwingConstants.TOP);
        
        // Calculate dynamic size based on text content
        FontMetrics fm = messageLabel.getFontMetrics(messageLabel.getFont());
        String[] lines = message.split("\n");
        
        // Calculate required width and height
        int maxLineWidth = 0;
        for (String line : lines) {
            int lineWidth = fm.stringWidth(line);
            if (lineWidth > maxLineWidth) {
                maxLineWidth = lineWidth;
            }
        }
        int minWidth = 350;
        int maxWidth = 600;
        int minHeight = 200;
        int maxHeight = 500;
        
        // Calculate dialog dimensions
        int dialogWidth = Math.max(minWidth, Math.min(maxWidth, maxLineWidth + 120)); // +120 for padding and borders
        int lineHeight = fm.getHeight();
        int textHeight = lines.length * (int)(lineHeight * 1.4); // 1.4 for line spacing
        int dialogHeight = Math.max(minHeight, Math.min(maxHeight, textHeight + 150)); // +150 for title bar, button, padding
        
        // Set preferred size for message label
        messageLabel.setPreferredSize(new Dimension(dialogWidth - 120, textHeight + 20));
        
        // Create scroll pane for very long messages
        JScrollPane scrollPane = new JScrollPane(messageLabel);
        scrollPane.setBackground(CARD_COLOR);
        scrollPane.getViewport().setBackground(CARD_COLOR);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Style scrollbar if needed
        if (textHeight > maxHeight - 150) {
            scrollPane.getVerticalScrollBar().setBackground(SECONDARY_COLOR);
            scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = PRIMARY_COLOR;
                    this.trackColor = SECONDARY_COLOR;
                }
            });
        }
        
        JButton okButton = createStyledButton("OK", accentColor, true);
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(okButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setSize(dialogWidth, dialogHeight);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
    
    
}