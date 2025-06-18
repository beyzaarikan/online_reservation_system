package gui;
import javax.swing.*;
import utils.SampleDataLoader;

public class MainApplication {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SampleDataLoader.loadSampleTripData();

        SwingUtilities.invokeLater(() -> new WelcomePage().display());
    }
}