package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminPage extends BasePanel {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public AdminPage() {
        super("Admin Panel", 800, 600);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("Admin Panel");
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(PageComponents.BACKGROUND_COLOR);
        
        searchField = PageComponents.createStyledTextField("Search users...");
        JButton searchButton = PageComponents.createStyledButton("Search", PageComponents.PRIMARY_COLOR, true);
        JButton refreshButton = PageComponents.createStyledButton("Refresh", PageComponents.SECONDARY_COLOR, false);
        
        searchPanel.add(new JLabel("Search: ") {{ 
            setForeground(PageComponents.TEXT_COLOR); 
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }});
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        // Table Panel
        JPanel tablePanel = createCardPanel();
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.PRIMARY_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        // Create table
        String[] columnNames = {"ID", "Username", "Email", "Status", "Registration Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        userTable = new JTable(tableModel);
        userTable.setBackground(PageComponents.INPUT_COLOR);
        userTable.setForeground(PageComponents.TEXT_COLOR);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userTable.setGridColor(PageComponents.SECONDARY_COLOR);
        userTable.setSelectionBackground(PageComponents.PRIMARY_COLOR);
        userTable.setSelectionForeground(Color.WHITE);
        
        // Populate sample data
        populateSampleData();
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        scrollPane.getViewport().setBackground(PageComponents.INPUT_COLOR);
        
        // Action Buttons Panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton viewButton = PageComponents.createStyledButton("View Details", PageComponents.PRIMARY_COLOR, true);
        JButton blockButton = PageComponents.createStyledButton("Block User", new Color(255, 121, 121), true);
        JButton deleteButton = PageComponents.createStyledButton("Delete User", new Color(255, 85, 85), true);
        JButton backButton = PageComponents.createStyledButton("Back", PageComponents.SECONDARY_COLOR, false);
        
        actionPanel.add(viewButton);
        actionPanel.add(blockButton);
        actionPanel.add(deleteButton);
        actionPanel.add(backButton);
        
        tablePanel.add(new JLabel("User Management") {{
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setForeground(PageComponents.TEXT_COLOR);
        }});
        tablePanel.add(Box.createVerticalStrut(15));
        tablePanel.add(scrollPane);
        tablePanel.add(Box.createVerticalStrut(15));
        tablePanel.add(actionPanel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Action listeners
        searchButton.addActionListener(e -> searchUsers());
        refreshButton.addActionListener(e -> refreshData());
        viewButton.addActionListener(e -> viewUserDetails());
        blockButton.addActionListener(e -> blockUser());
        deleteButton.addActionListener(e -> deleteUser());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
    }
    
    private void populateSampleData() {
        tableModel.addRow(new Object[]{"001", "john_doe", "john@example.com", "Active", "01/06/2025"});
        tableModel.addRow(new Object[]{"002", "jane_smith", "jane@example.com", "Active", "02/06/2025"});
        tableModel.addRow(new Object[]{"003", "mike_wilson", "mike@example.com", "Blocked", "03/06/2025"});
        tableModel.addRow(new Object[]{"004", "sarah_jones", "sarah@example.com", "Active", "04/06/2025"});
        tableModel.addRow(new Object[]{"005", "david_brown", "david@example.com", "Pending", "05/06/2025"});
    }
    
    private void searchUsers() {
        String searchTerm = searchField.getText();
        if (searchTerm.equals("Search users...") || searchTerm.trim().isEmpty()) {
            PageComponents.showStyledMessage("Info", "Please enter a search term!", this);
            return;
        }
        
        PageComponents.showStyledMessage("Info", "Searching for: " + searchTerm, this);
        // Implement actual search logic here
    }
    
    private void refreshData() {
        tableModel.setRowCount(0);
        populateSampleData();
        PageComponents.showStyledMessage("Success", "Data refreshed successfully!", this);
    }
    
    private void viewUserDetails() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a user to view details!", this);
            return;
        }
        
        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        String email = (String) tableModel.getValueAt(selectedRow, 2);
        String status = (String) tableModel.getValueAt(selectedRow, 3);
        
        String details = "User Details:\n" +
                        "ID: " + userId + "\n" +
                        "Username: " + username + "\n" +
                        "Email: " + email + "\n" +
                        "Status: " + status;
        
        PageComponents.showStyledMessage("User Details", details, this);
    }
    
    private void blockUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a user to block!", this);
            return;
        }
        
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to block user: " + username + "?",
            "Confirm Block",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Blocked", selectedRow, 3);
            PageComponents.showStyledMessage("Success", "User " + username + " has been blocked!", this);
        }
    }
    
    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            PageComponents.showStyledMessage("Error", "Please select a user to delete!", this);
            return;
        }
        
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to DELETE user: " + username + "?\nThis action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            PageComponents.showStyledMessage("Success", "User " + username + " has been deleted!", this);
        }
    }
}