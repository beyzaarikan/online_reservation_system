package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsolePage extends BasePanel {
    private JTextArea consoleOutput;
    private JTextField commandInput;
    private JScrollPane scrollPane;
    
    public ConsolePage() {
        super("System Console", 800, 600);
    }
    
    @Override
    public void setupUI() {
        JPanel mainPanel = createMainPanel();
        
        // Title Panel
        JPanel titlePanel = createTitlePanel("System Console");
        
        // Console Panel
        JPanel consolePanel = createCardPanel();
        consolePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PageComponents.ACCENT_COLOR, 2, true),
            new javax.swing.border.EmptyBorder(20, 20, 20, 20)
        ));
        
        // Console Output Area
        consoleOutput = new JTextArea();
        consoleOutput.setBackground(new Color(30, 30, 30));
        consoleOutput.setForeground(new Color(0, 255, 0)); // Green text like terminal
        consoleOutput.setFont(new Font("Consolas", Font.PLAIN, 12));
        consoleOutput.setEditable(false);
        consoleOutput.setLineWrap(true);
        consoleOutput.setWrapStyleWord(true);
        
        // Initialize with welcome message
        appendToConsole("System Console Initialized...");
        appendToConsole("Type 'help' for available commands.");
        appendToConsole("=====================================");
        
        scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setPreferredSize(new Dimension(700, 350));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        
        // Command Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(PageComponents.CARD_COLOR);
        
        JLabel promptLabel = new JLabel("CMD> ");
        promptLabel.setForeground(PageComponents.ACCENT_COLOR);
        promptLabel.setFont(new Font("Consolas", Font.BOLD, 14));
        
        commandInput = new JTextField();
        commandInput.setBackground(new Color(30, 30, 30));
        commandInput.setForeground(new Color(0, 255, 0));
        commandInput.setFont(new Font("Consolas", Font.PLAIN, 12));
        commandInput.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        commandInput.setCaretColor(new Color(0, 255, 0));
        
        inputPanel.add(promptLabel, BorderLayout.WEST);
        inputPanel.add(commandInput, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(PageComponents.CARD_COLOR);
        
        JButton executeButton = PageComponents.createStyledButton("Execute", PageComponents.ACCENT_COLOR, true);
        JButton clearButton = PageComponents.createStyledButton("Clear", new Color(255, 121, 121), true);
        JButton backButton = PageComponents.createStyledButton("Back", PageComponents.SECONDARY_COLOR, false);
        
        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        
        consolePanel.add(new JLabel("Console Output:") {{
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setForeground(PageComponents.TEXT_COLOR);
        }});
        consolePanel.add(Box.createVerticalStrut(10));
        consolePanel.add(scrollPane);
        consolePanel.add(Box.createVerticalStrut(15));
        consolePanel.add(inputPanel);
        consolePanel.add(Box.createVerticalStrut(15));
        consolePanel.add(buttonPanel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(consolePanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Action listeners
        executeButton.addActionListener(e -> executeCommand());
        clearButton.addActionListener(e -> clearConsole());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage().display();
        });
        
        // Enter key support for command input
        commandInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    executeCommand();
                }
            }
        });
        
        // Focus on command input
        SwingUtilities.invokeLater(() -> commandInput.requestFocus());
    }
    
    private void appendToConsole(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        consoleOutput.append("[" + timestamp + "] " + message + "\n");
        
        // Auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            consoleOutput.setCaretPosition(consoleOutput.getDocument().getLength());
        });
    }
    
    private void executeCommand() {
        String command = commandInput.getText().trim();
        if (command.isEmpty()) {
            return;
        }
        
        // Echo the command
        appendToConsole("CMD> " + command);
        
        // Process the command
        processCommand(command);
        
        // Clear input field
        commandInput.setText("");
    }
    
    private void processCommand(String command) {
        String[] parts = command.toLowerCase().split("\\s+");
        String mainCommand = parts[0];
        
        switch (mainCommand) {
            case "help":
                showHelp();
                break;
            case "status":
                showSystemStatus();
                break;
            case "users":
                showUserCount();
                break;
            case "trips":
                showTripStats();
                break;
            case "memory":
                showMemoryInfo();
                break;
            case "time":
                showCurrentTime();
                break;
            case "clear":
                clearConsole();
                break;
            case "version":
                showVersion();
                break;
            case "ping":
                if (parts.length > 1) {
                    pingHost(parts[1]);
                } else {
                    appendToConsole("Usage: ping <hostname>");
                }
                break;
            case "exit":
                appendToConsole("Exiting console...");
                dispose();
                new MainMenuPage().display();
                break;
            default:
                appendToConsole("Unknown command: " + command);
                appendToConsole("Type 'help' for available commands.");
                break;
        }
    }
    
    private void showHelp() {
        appendToConsole("Available Commands:");
        appendToConsole("  help     - Show this help message");
        appendToConsole("  status   - Show system status");
        appendToConsole("  users    - Show user statistics");
        appendToConsole("  trips    - Show trip statistics");
        appendToConsole("  memory   - Show memory information");
        appendToConsole("  time     - Show current time");
        appendToConsole("  clear    - Clear console output");
        appendToConsole("  version  - Show application version");
        appendToConsole("  ping <host> - Ping a host");
        appendToConsole("  exit     - Exit console and return to main menu");
    }
    
    private void showSystemStatus() {
        appendToConsole("System Status: ONLINE");
        appendToConsole("Database: Connected");
        appendToConsole("Services: Running (4/4)");
        appendToConsole("Last Backup: 02/06/2025 08:30");
    }
    
    private void showUserCount() {
        appendToConsole("Total Users: 1,247");
        appendToConsole("Active Users: 892");
        appendToConsole("Blocked Users: 15");
        appendToConsole("New Registrations Today: 23");
    }
    
    private void showTripStats() {
        appendToConsole("Total Trips: 456");
        appendToConsole("Active Routes: 67");
        appendToConsole("Completed Today: 89");
        appendToConsole("Scheduled for Tomorrow: 123");
    }
    
    private void showMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        
        appendToConsole("Memory Information:");
        appendToConsole("  Total Memory: " + totalMemory + " MB");
        appendToConsole("  Used Memory:  " + usedMemory + " MB");
        appendToConsole("  Free Memory:  " + freeMemory + " MB");
        appendToConsole("  Max Memory:   " + maxMemory + " MB");
    }
    
    private void showCurrentTime() {
        appendToConsole("Current Time: " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
    
    private void showVersion() {
        appendToConsole("Travel Management System v1.0.0");
        appendToConsole("Build: 20250602");
        appendToConsole("Java Version: " + System.getProperty("java.version"));
        appendToConsole("OS: " + System.getProperty("os.name"));
    }
    
    private void pingHost(String host) {
        appendToConsole("Pinging " + host + "...");
        // Simulate ping response
        try {
            Thread.sleep(500); // Simulate network delay
            appendToConsole("Reply from " + host + ": time=24ms TTL=64");
            appendToConsole("Reply from " + host + ": time=23ms TTL=64");
            appendToConsole("Reply from " + host + ": time=25ms TTL=64");
            appendToConsole("Ping statistics: 3 packets sent, 3 received, 0% loss");
        } catch (InterruptedException e) {
            appendToConsole("Ping interrupted.");
        }
    }
    
    private void clearConsole() {
        consoleOutput.setText("");
        appendToConsole("Console cleared.");
        appendToConsole("Type 'help' for available commands.");
    }
}