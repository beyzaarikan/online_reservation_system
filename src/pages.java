
// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;


// public class pages {
//     // Modern color palette
//     private static final Color BACKGROUND_COLOR = new Color(24, 25, 26);
//     private static final Color CARD_COLOR = new Color(40, 42, 54);
//     private static final Color PRIMARY_COLOR = new Color(98, 114, 164);
//     private static final Color PRIMARY_HOVER = new Color(139, 148, 186);
//     private static final Color SECONDARY_COLOR = new Color(68, 71, 90);
//     private static final Color TEXT_COLOR = new Color(248, 248, 242);
//     private static final Col or ACCENT_COLOR = new Color(80, 250, 123);
//     private static final Color INPUT_COLOR = new Color(68, 71, 90);
    
//     public static void main(String[] args) {
//         // Set system look and feel for better integration
//         try {
//             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
        
//         SwingUtilities.invokeLater(() -> welcome());
//     }
    
//     public static void welcome(){
//         JFrame frame = new JFrame("Welcome to Our Platform");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(500, 500);
//         frame.setLocationRelativeTo(null); // Center the window
//         frame.getContentPane().setBackground(BACKGROUND_COLOR);

//         JPanel mainPanel = new JPanel(new BorderLayout());
//         mainPanel.setBackground(BACKGROUND_COLOR);
//         mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

//         // Title Panel
//         JPanel titlePanel = new JPanel();
//         titlePanel.setBackground(BACKGROUND_COLOR);
//         JLabel titleLabel = new JLabel("Welcome!", SwingConstants.CENTER);
//         titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
//         titleLabel.setForeground(TEXT_COLOR);
//         titlePanel.add(titleLabel);

//         // Center Panel with card-like design
//         JPanel centerPanel = new JPanel();
//         centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
//         centerPanel.setBackground(CARD_COLOR);
//         centerPanel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(98, 114, 164), 2, true),
//             new EmptyBorder(40, 30, 40, 30)
//         ));

//         JLabel questionLabel = new JLabel("Dou you have an account?");
//         questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
//         questionLabel.setForeground(TEXT_COLOR);
//         questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

//         JLabel subLabel = new JLabel("Choose an option ");
//         subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         subLabel.setForeground(new Color(189, 147, 249));
//         subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

//         JButton signInButton = createStyledButton("Create Account", PRIMARY_COLOR, true);
//         JButton logInButton = createStyledButton("Sign In", SECONDARY_COLOR, false);

//         centerPanel.add(questionLabel);
//         centerPanel.add(Box.createVerticalStrut(10));
//         centerPanel.add(subLabel);
//         centerPanel.add(Box.createVerticalStrut(50));
//         centerPanel.add(signInButton);
//         centerPanel.add(Box.createVerticalStrut(15));
//         centerPanel.add(logInButton);

//         mainPanel.add(titlePanel, BorderLayout.NORTH);
//         mainPanel.add(centerPanel, BorderLayout.CENTER);
//         frame.add(mainPanel);
//         frame.setVisible(true);

//         signInButton.addActionListener(e -> {
//             frame.dispose();
//             createUser();
//         });
        
//         logInButton.addActionListener(e -> {
//             frame.dispose();
//             loginUser();
//         });
//     }
    
//     public static void createUser(){
//         JFrame frame = new JFrame("Create Your Account");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(500, 500);
//         frame.setLocationRelativeTo(null);
//         frame.getContentPane().setBackground(BACKGROUND_COLOR);

//         JPanel mainPanel = new JPanel(new BorderLayout());
//         mainPanel.setBackground(BACKGROUND_COLOR);
//         mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

//         // Title Panel
//         JPanel titlePanel = new JPanel();
//         titlePanel.setBackground(BACKGROUND_COLOR);
//         JLabel titleLabel = new JLabel("Create Account", SwingConstants.CENTER);
//         titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
//         titleLabel.setForeground(TEXT_COLOR);
//         titlePanel.add(titleLabel);

//         // Form Panel
//         JPanel formPanel = new JPanel();
//         formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
//         formPanel.setBackground(CARD_COLOR);
//         formPanel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(98, 114, 164), 2, true),
//             new EmptyBorder(30, 30, 30, 30)
//         ));

//         JTextField nameField = createStyledTextField("Enter your username");
//         JPasswordField passwordField = createStyledPasswordField("Enter your password");
//         JTextField emailField = createStyledTextField("Enter your email");
//         JButton createButton = createStyledButton("Create Account", ACCENT_COLOR, true);

//         formPanel.add(createFormField("Username", nameField));
//         formPanel.add(Box.createVerticalStrut(20));
//         formPanel.add(createFormField("Password", passwordField));
//         formPanel.add(Box.createVerticalStrut(20));
//         formPanel.add(createFormField("Email", emailField));
//         formPanel.add(Box.createVerticalStrut(25));
//         formPanel.add(createButton);

//         mainPanel.add(titlePanel, BorderLayout.NORTH);
//         mainPanel.add(formPanel, BorderLayout.CENTER);
//         frame.add(mainPanel);

//         createButton.addActionListener(e -> {
//             showStyledMessage("Success!", "Account created successfully!", frame);
//             frame.dispose();
//             loginUser();
//         });

//         frame.setVisible(true);
//     }

//     public static void loginUser() {
//         JFrame frame = new JFrame("LogIn");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(500, 500);
//         frame.setLocationRelativeTo(null);
//         frame.getContentPane().setBackground(BACKGROUND_COLOR);

//         JPanel mainPanel = new JPanel(new BorderLayout());
//         mainPanel.setBackground(BACKGROUND_COLOR);
//         mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

//         // Title Panel
//         JPanel titlePanel = new JPanel();
//         titlePanel.setBackground(BACKGROUND_COLOR);
//         JLabel titleLabel = new JLabel("Sign In", SwingConstants.CENTER);
//         titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
//         titleLabel.setForeground(TEXT_COLOR);
//         titlePanel.add(titleLabel);

//         // Form Panel
//         JPanel formPanel = new JPanel();
//         formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
//         formPanel.setBackground(CARD_COLOR);
//         formPanel.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(98, 114, 164), 2, true),
//             new EmptyBorder(40, 40, 30, 40)
//         ));

//         JTextField userText = createStyledTextField("Enter your email");
//         JPasswordField passwordText = createStyledPasswordField("Enter your password");
//         JButton loginButton = createStyledButton("Sign In", PRIMARY_COLOR, true);

//         formPanel.add(createFormField("Email", userText));
//         formPanel.add(Box.createVerticalStrut(60));
//         formPanel.add(createFormField("Password", passwordText));
//         formPanel.add(Box.createVerticalStrut(45));
//         formPanel.add(loginButton);

//         mainPanel.add(titlePanel, BorderLayout.NORTH);
//         mainPanel.add(formPanel, BorderLayout.CENTER);
//         frame.add(mainPanel);

//         loginButton.addActionListener(e -> {
//             showStyledMessage("Welcome!", "Login successful!", frame);
//         });

//         frame.setVisible(true);
//     }

//     private static JButton createStyledButton(String text, Color backgroundColor, boolean isPrimary) {
//         JButton button = new JButton(text);
//         button.setFont(new Font("Segoe UI", Font.BOLD, 16));
//         button.setForeground(isPrimary ? Color.WHITE : TEXT_COLOR);
//         button.setBackground(backgroundColor);
//         button.setBorder(new EmptyBorder(12, 24, 12, 24));
//         button.setFocusPainted(false);
//         button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//         button.setAlignmentX(Component.CENTER_ALIGNMENT);
//         button.setMaximumSize(new Dimension(200, 45));
        
//         // Add hover effect
//         button.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseEntered(MouseEvent e) {
//                 if (isPrimary) {
//                     button.setBackground(backgroundColor.brighter());
//                 } else {
//                     button.setBackground(PRIMARY_HOVER);
//                 }
//             }

//             @Override
//             public void mouseExited(MouseEvent e) {
//                 button.setBackground(backgroundColor);
//             }
//         });

//         return button;
//     }

//     private static JTextField createStyledTextField(String placeholder) {
//         JTextField field = new JTextField(15); // 20'den 15'e düşürdüm
//         field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         field.setForeground(TEXT_COLOR);
//         field.setBackground(INPUT_COLOR);
//         field.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(98, 114, 164), 1, true),
//             new EmptyBorder(10, 12, 10, 12) // padding'i küçülttüm
//         ));
//         field.setCaretColor(TEXT_COLOR);
//         field.setMaximumSize(new Dimension(250, 40)); // maksimum boyut ekledim
        
//         // Add placeholder effect
//         field.setText(placeholder);
//         field.setForeground(Color.GRAY);
        
//         field.addFocusListener(new java.awt.event.FocusAdapter() {
//             public void focusGained(java.awt.event.FocusEvent evt) {
//                 if (field.getText().equals(placeholder)) {
//                     field.setText("");
//                     field.setForeground(TEXT_COLOR);
//                 }
//             }
//             public void focusLost(java.awt.event.FocusEvent evt) {
//                 if (field.getText().isEmpty()) {
//                     field.setForeground(Color.GRAY);
//                     field.setText(placeholder);
//                 }
//             }
//         });
        
//         return field;
//     }

//     private static JPasswordField createStyledPasswordField(String placeholder) {
//         JPasswordField field = new JPasswordField(15); // 20'den 15'e düşürdüm
//         field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//         field.setForeground(TEXT_COLOR);
//         field.setBackground(INPUT_COLOR);
//         field.setBorder(BorderFactory.createCompoundBorder(
//             BorderFactory.createLineBorder(new Color(98, 114, 164), 1, true),
//             new EmptyBorder(10, 12, 10, 12) // padding'i küçülttüm
//         ));
//         field.setCaretColor(TEXT_COLOR);
//         field.setMaximumSize(new Dimension(250, 40)); // maksimum boyut ekledim
//         field.setEchoChar((char) 0); // Show placeholder text initially
//         field.setText(placeholder);
//         field.setForeground(Color.GRAY);
        
//         field.addFocusListener(new java.awt.event.FocusAdapter() {
//             public void focusGained(java.awt.event.FocusEvent evt) {
//                 if (String.valueOf(field.getPassword()).equals(placeholder)) {
//                     field.setText("");
//                     field.setForeground(TEXT_COLOR);
//                     field.setEchoChar('•');
//                 }
//             }
//             public void focusLost(java.awt.event.FocusEvent evt) {
//                 if (field.getPassword().length == 0) {
//                     field.setForeground(Color.GRAY);
//                     field.setEchoChar((char) 0);
//                     field.setText(placeholder);
//                 }
//             }
//         });
        
//         return field;
//     }

//     private static JPanel createFormField(String labelText, JComponent field) {
//         JPanel panel = new JPanel();
//         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//         panel.setBackground(CARD_COLOR);
        
//         JLabel label = new JLabel(labelText);
//         label.setFont(new Font("Segoe UI", Font.BOLD, 14));
//         label.setForeground(TEXT_COLOR);
//         label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
//         panel.add(label);
//         panel.add(Box.createVerticalStrut(8));
//         panel.add(field);
        
//         return panel;
//     }

//     private static void showStyledMessage(String title, String message, JFrame parent) {
//         JDialog dialog = new JDialog(parent, title, true);
//         dialog.setSize(350, 100);
//         dialog.setLocationRelativeTo(parent);
//         dialog.getContentPane().setBackground(BACKGROUND_COLOR);
        
//         JPanel panel = new JPanel(new BorderLayout());
//         panel.setBackground(CARD_COLOR);
//         panel.setBorder(new EmptyBorder(40, 30, 10, 30));
        
//         JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
//         messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
//         messageLabel.setForeground(TEXT_COLOR);
        
//         JButton okButton = createStyledButton("OK", ACCENT_COLOR, true);
//         okButton.addActionListener(e -> dialog.dispose());
        
//         JPanel buttonPanel = new JPanel();
//         buttonPanel.setBackground(CARD_COLOR);
//         buttonPanel.add(okButton);
        
//         panel.add(messageLabel, BorderLayout.CENTER);
//         panel.add(buttonPanel, BorderLayout.SOUTH);
        
//         dialog.add(panel);
//         dialog.setVisible(true);
//     }
// }
