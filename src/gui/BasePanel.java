package gui;
import javax.swing.*;

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
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public abstract void setupUI();
    
    public void display() {
        setupUI();
        setVisible(true);
    }
}