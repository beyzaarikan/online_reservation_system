import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui {
    public static void main(String[] args) {
        welcome();
    }
    public static void welcome(){
        JFrame frame=new JFrame("Welcome!");
        frame.setSize(400, 300); //pencere boyutu

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(centered(new JLabel("Did you have an account? ")));
        JButton createButton = new JButton("Sign In");
        JButton logInButton = new JButton("Log In");
        centerPanel.add(centered(createButton));
        centerPanel.add(centered(logInButton));

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        createButton.addActionListener(e -> {
            frame.dispose(); // Eski pencereyi kapat
            createUser(); // Yeni kullanıcı oluşturma penceresini aç
        });
        logInButton.addActionListener(e -> {
            frame.dispose(); // Eski pencereyi kapat
            loginUser(); // Giriş yapma penceresini aç
        });
    }
    
    public static void createUser(){
        JFrame frame=new JFrame("Sign In User");
        frame.setSize(400, 300); //pencere boyutu

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JTextField nameField= new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JTextField emailField = new JTextField(15);
        JButton createButton = new JButton("Create User");


        centerPanel.add(centered(new JLabel("User Name:")));
        centerPanel.add(centered(nameField));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(centered(new JLabel("Password:")));
        centerPanel.add(centered(passwordField));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(centered(new JLabel("Email:")));
        centerPanel.add(centered(emailField));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(centered(createButton));
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane panel= new JOptionPane();
                panel.showMessageDialog(null,"User Created Successfully!");
                frame.dispose(); 
                loginUser();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public static void loginUser() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel();

        JTextField userText = new JTextField(15); // max 15 karakter
        JPasswordField passwordText = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        centerPanel.add(centered(new JLabel("User name:")));
        centerPanel.add(centered(userText));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(centered(new JLabel("Password:")));
        centerPanel.add(centered(passwordText));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(centered(loginButton));

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

    }

    private static JPanel centered(JComponent comp) { //yatay hizalamak icin kullanilan yardimci method
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(comp);
        return wrapper;
    }
}
