package gui;
import javax.swing.*;
import java.awt.*;

public class ReservationPage extends BasePanel {
    private JList<String> reservationList;
    private DefaultListModel<String> listModel;
    
    public ReservationPage() {
        super("My Reservations", 700, 500);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("My Reservations");
        
        // Content Panel
        JPanel contentPanel = createCardPanel();
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(30, 30, 30, 30)
        ));
        
        JLabel infoLabel = new JLabel("Your Current Reservations:", SwingConstants.LEFT);
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoLabel.setForeground(PageComponents.TEXT_COLOR);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create reservation list with sample data
        listModel = new DefaultListModel<>();
        populateSampleReservations();
        
        reservationList = new JList<>(listModel);
        reservationList.setBackground(PageComponents.INPUT_COLOR);
        reservationList.setForeground(PageComponents.TEXT_COLOR);
        reservationList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(reservationList);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        scrollPane.getViewport().setBackground(PageComponents.INPUT_COLOR);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton cancelButton = PageComponents.createStyledButton("Cancel Selected", new Color(255, 85, 85), true);
        JButton refreshButton = PageComponents.createStyledButton("Refresh", PageComponents.PRIMARY_COLOR, true);
        JButton backButton = PageComponents.createStyledButton("Back", PageComponents.SECONDARY_COLOR, false);
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(refreshButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);
        
        contentPanel.add(infoLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        cancelButton.addActionListener(e -> cancelReservation());
        refreshButton.addActionListener(e -> refreshReservations());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
    }
    
    private void populateSampleReservations() {
        listModel.addElement("RES001 | Istanbul → Ankara | 15/06/2025 | 09:00-14:00 | Status: Confirmed");
        listModel.addElement("RES002 | Ankara → Izmir | 20/06/2025 | 15:30-20:30 | Status: Confirmed");
        listModel.addElement("RES003 | Izmir → Istanbul | 25/06/2025 | 22:00-03:00 | Status: Pending");
    }
    
    private void cancelReservation() {
        int selectedIndex = reservationList.getSelectedIndex();
        if (selectedIndex == -1) {
            PageComponents.showStyledMessage("Error", "Please select a reservation to cancel!", this);
            return;
        }
        
        String selectedReservation = listModel.getElementAt(selectedIndex);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel this reservation?\n" + selectedReservation,
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            listModel.remove(selectedIndex);
            PageComponents.showStyledMessage("Success", "Reservation cancelled successfully!", this);
        }
    }
    
    private void refreshReservations() {
        listModel.clear();
        populateSampleReservations();
        PageComponents.showStyledMessage("Success", "Reservations refreshed!", this);
    }
}