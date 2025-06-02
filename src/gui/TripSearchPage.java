package gui;
import javax.swing.*;
import java.awt.*;

public class TripSearchPage extends BasePanel {
    private JTextField fromField;
    private JTextField toField;
    private JTextField dateField;
    private JList<String> tripList;
    private DefaultListModel<String> listModel;
    
    public TripSearchPage() {
        super("Search Trips", 700, 600);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("Search Trips");
        
        // Search Form Panel
        JPanel searchPanel = createCardPanel();
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 30, 20, 30)
        ));
        
        fromField = PageComponents.createStyledTextField("From city");
        toField = PageComponents.createStyledTextField("To city");
        dateField = PageComponents.createStyledTextField("Date (DD/MM/YYYY)");
        JButton searchButton = PageComponents.createStyledButton("Search", PageComponents.PRIMARY_COLOR, true);
        JButton backButton = PageComponents.createStyledButton("Back", PageComponents.SECONDARY_COLOR, false);
        
        // Create search form layout
        JPanel formRow1 = new JPanel(new FlowLayout());
        formRow1.setBackground(PageComponents.CARD_COLOR);
        formRow1.add(PageComponents.createFormField("From", fromField));
        formRow1.add(Box.createHorizontalStrut(20));
        formRow1.add(PageComponents.createFormField("To", toField));
        
        JPanel formRow2 = new JPanel(new FlowLayout());
        formRow2.setBackground(PageComponents.CARD_COLOR);
        formRow2.add(PageComponents.createFormField("Date", dateField));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(backButton);
        
        searchPanel.add(formRow1);
        searchPanel.add(Box.createVerticalStrut(15));
        searchPanel.add(formRow2);
        searchPanel.add(Box.createVerticalStrut(20));
        searchPanel.add(buttonPanel);
        
        // Results Panel
        JPanel resultsPanel = createCardPanel();
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel resultsLabel = new JLabel("Search Results:", SwingConstants.LEFT);
        resultsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultsLabel.setForeground(PageComponents.TEXT_COLOR);
        
        listModel = new DefaultListModel<>();
        tripList = new JList<>(listModel);
        tripList.setBackground(PageComponents.INPUT_COLOR);
        tripList.setForeground(PageComponents.TEXT_COLOR);
        tripList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tripList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tripList);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        scrollPane.getViewport().setBackground(PageComponents.INPUT_COLOR);
        
        JButton bookButton = PageComponents.createStyledButton("Book Selected", PageComponents.ACCENT_COLOR, true);
        
        resultsPanel.add(resultsLabel);
        resultsPanel.add(Box.createVerticalStrut(10));
        resultsPanel.add(scrollPane);
        resultsPanel.add(Box.createVerticalStrut(15));
        resultsPanel.add(bookButton);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Action listeners
        searchButton.addActionListener(e -> searchTrips());
        bookButton.addActionListener(e -> bookTrip());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
    }
    
    private void searchTrips() {
        String from = fromField.getText();
        String to = toField.getText();
        String date = dateField.getText();
        
        // Basic validation
        if (from.equals("From city") || from.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter departure city!", this);
            return;
        }
        
        if (to.equals("To city") || to.trim().isEmpty()) {
            PageComponents.showStyledMessage("Error", "Please enter destination city!", this);
            return;
        }
        
        // Simulate search results
        listModel.clear();
        listModel.addElement(from + " → " + to + " | " + date + " | 09:00 - 14:00 | $50.00");
        listModel.addElement(from + " → " + to + " | " + date + " | 15:30 - 20:30 | $65.00");
        listModel.addElement(from + " → " + to + " | " + date + " | 22:00 - 03:00 | $45.00");
        
        PageComponents.showStyledMessage("Success", "Found " + listModel.size() + " trips!", this);
    }
    
    private void bookTrip() {
        int selectedIndex = tripList.getSelectedIndex();
        if (selectedIndex == -1) {
            PageComponents.showStyledMessage("Error", "Please select a trip to book!", this);
            return;
        }
        
        String selectedTrip = listModel.getElementAt(selectedIndex);
        PageComponents.showStyledMessage("Success", "Trip booked successfully!\n" + selectedTrip, this);
    }
}