package gui;

import java.awt.*;
import java.util.Collection;
import java.util.Optional;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.User;
import repository.TripRepository;
import repository.UserRepository;
import service.TripService;
import service.UserService;

public class UserManagementPage extends BasePanel {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private UserRepository userRepository;
    private UserService userService;
    private TripRepository tripRepository;
    private TripService tripService;
    
    public UserManagementPage() {
        super("Admin Panel - Travel System", 1000, 700);
        this.userRepository = UserRepository.getInstance();
        this.userService = new UserService(userRepository);
        this.tripRepository = TripRepository.getInstance();
        this.tripService = new TripService(tripRepository);
        initializeSampleUsers();
    }

    @Override
    public void setupUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 15, 35));

        // Main panel with gradient background (same as LoginPage)
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(15, 15, 35),
                    getWidth(), getHeight(), new Color(25, 25, 55)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Decorative circles
                g2d.setColor(new Color(138, 43, 226, 30));
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(getWidth()-150, getHeight()-150, 200, 200);
                
                g2d.setColor(new Color(75, 0, 130, 20));
                g2d.fillOval(getWidth()-100, -50, 150, 150);
                g2d.fillOval(-100, getHeight()-100, 150, 150);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.setOpaque(false);
        JButton backButton = createButton("← Back", new Color(108, 92, 231), false);
        backButton.setPreferredSize(new Dimension(100, 35));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backPanel.add(backButton);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("User Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 147, 249));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(subtitleLabel);

        // Main content panel with glassmorphism effect
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 10));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
            }
        };
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        contentPanel.setMaximumSize(new Dimension(900, 500));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel searchLabel = new JLabel("Search Users:");
        searchLabel.setForeground(new Color(189, 147, 249));
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        searchField = createTextField("Enter username, email or ID...");
        JButton searchButton = createButton("Search", new Color(138, 43, 226), true);
        JButton refreshButton = createButton("Refresh", new Color(108, 92, 231), false);
        
        searchPanel.add(searchLabel);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(refreshButton);
        
        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        
        // Create table
        String[] columnNames = {"ID", "Username", "Email", "Status", "Registration Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        userTable = new JTable(tableModel);
        setupTable();
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Style the scrollpane
        scrollPane.getVerticalScrollBar().setBackground(new Color(255, 255, 255, 20));
        scrollPane.getHorizontalScrollBar().setBackground(new Color(255, 255, 255, 20));
        
        // Action Buttons Panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton viewButton = createButton("View Details", new Color(138, 43, 226), true);
        JButton deleteButton = createButton("Delete User", new Color(255, 85, 85), true);
        
        actionPanel.add(viewButton);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(deleteButton);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(actionPanel, BorderLayout.SOUTH);
        
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        
        centerPanel.add(titlePanel);
        centerPanel.add(contentPanel);

        mainPanel.add(backPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Populate data
        populateSampleData();
        
        // Action listeners
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage(true).display();
        });
        
        searchButton.addActionListener(e -> searchUsers());
        refreshButton.addActionListener(e -> refreshData());
        viewButton.addActionListener(e -> viewUserDetails());
        deleteButton.addActionListener(e -> deleteUser());
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Border
                if (isFocusOwner()) {
                    g2d.setColor(new Color(138, 43, 226));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 30));
                }
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        field.setPreferredSize(new Dimension(250, 35));
        field.setText(placeholder);
        field.setForeground(new Color(150, 150, 150));
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(150, 150, 150));
                }
            }
        });
        
        return field;
    }

    private JButton createButton(String text, Color baseColor, boolean isPrimary) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(baseColor.brighter());
                } else {
                    g2d.setColor(baseColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));
        
        return button;
    }

    private void setupTable() {
        userTable.setBackground(new Color(255, 255, 255, 10));
        userTable.setForeground(Color.WHITE);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userTable.setGridColor(new Color(255, 255, 255, 30));
        userTable.setSelectionBackground(new Color(138, 43, 226, 100));
        userTable.setSelectionForeground(Color.WHITE);
        userTable.setRowHeight(30);
        userTable.setShowGrid(true);
        userTable.setIntercellSpacing(new Dimension(1, 1));
        
        // Custom header renderer
        userTable.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(138, 43, 226, 150));
                c.setForeground(Color.WHITE);
                c.setFont(new Font("Segoe UI", Font.BOLD, 12));
                setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                return c;
            }
        });
        
        // Custom cell renderer
        userTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(new Color(138, 43, 226, 100));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(255, 255, 255, 5));
                    c.setForeground(Color.WHITE);
                }
                c.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                return c;
            }
        });
    }
    
    private void populateSampleData() {
        tableModel.setRowCount(0);
        Collection<User> users = userRepository.findAll();

        for (User user : users) {
            String status = userService.isAdmin(user) ? "Admin" : "Customer";
            String registrationDate = "N/A";

            tableModel.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getEmail(),
                status,
                registrationDate
            });
        }
    }
    
    private void initializeSampleUsers() {
        userRepository.save(new models.Admin("admin1", "admin@travel.com", "admin@travel.com", "admin@travel.com"));
        userRepository.save(new models.Customer("user1", "john_doe", "pass123", "john@example.com"));
        userRepository.save(new models.Customer("user2", "jane_smith", "pass456", "jane@example.com"));
        userRepository.save(new models.Customer("user3", "mike_wilson", "password789", "mike@example.com"));
    }
    
    private void searchUsers() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.equals("Enter username, email or ID...") || searchTerm.isEmpty()) {
            showStyledMessage("Info", "Please enter a search term!");
            return;
        }

        tableModel.setRowCount(0);
        Collection<User> allUsers = userRepository.findAll();
    
        for (User user : allUsers) {
            if (user.getId().contains(searchTerm) || 
                user.getName().toLowerCase().contains(searchTerm.toLowerCase()) || 
                user.getEmail().toLowerCase().contains(searchTerm.toLowerCase())) {
                
                String status = userService.isAdmin(user) ? "Admin" : "Customer";
                String registrationDate = "N/A"; 
                
                tableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    status,
                    registrationDate
                });
            }
        }
        showStyledMessage("Info", "Found " + tableModel.getRowCount() + " users matching: " + searchTerm);
    }
    
    private void refreshData() {
        tableModel.setRowCount(0);
        populateSampleData();
        showStyledMessage("Success", "Data refreshed successfully!");
    }
    
    private void viewUserDetails() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Error", "Please select a user to view details!");
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String details = "User Details:\n\n" +
                            "ID: " + user.getId() + "\n" +
                            "Username: " + user.getName() + "\n" +
                            "Email: " + user.getEmail() + "\n" +
                            "User Type: " + user.getUserType();

            showStyledMessage("User Details", details);
        } else {
            showStyledMessage("Error", "User not found in system!");
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Error", "Please select a user to delete!");
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to DELETE user: " + user.getName() + "?\nThis action cannot be undone!",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                userRepository.deleteById(userId);
                tableModel.removeRow(selectedRow);
                showStyledMessage("Success", "User " + user.getName() + " has been deleted!");
            }
        } else {
            showStyledMessage("Error", "User not found in system!");
        }
    }

    private void showStyledMessage(String title, String message) {
        // Create a styled message dialog that matches the theme
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(15, 15, 35));
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(25, 25, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message.replace("\n", "<br>") + "</div></html>");
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton okButton = createButton("OK", new Color(138, 43, 226), true);
        okButton.addActionListener(e -> dialog.dispose());
        
        panel.setLayout(new BorderLayout());
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}