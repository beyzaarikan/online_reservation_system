package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BasePanel extends JFrame {
    
    public BasePanel(String title, int width, int height) {
        super(title);
        initializeFrame(width, height);
    }
    
    private void initializeFrame(int width, int height) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PageComponents.BACKGROUND_COLOR);
        
        // Set system look and feel for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        return mainPanel;
    }
    
    protected JPanel createTitlePanel(String title) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PageComponents.BACKGROUND_COLOR);
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PageComponents.TEXT_COLOR);
        titlePanel.add(titleLabel);
        return titlePanel;
    }
    
    protected JPanel createCardPanel() {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(PageComponents.CARD_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new EmptyBorder(30, 30, 30, 30)
        ));
        return cardPanel;
    }
    
    public abstract void setupUI();
    
    public void display() {
        setupUI();
        setVisible(true);
    }
}