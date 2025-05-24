import javax.swing.*;
import java.awt.*;

public class gui {
    public static void main(String[] args) {
        createUser();
        loginUser();
    }
    
    public static void createUser(){
        JFrame frame=new JFrame("Sign In User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        frame.setVisible(true);
    }


    public static void loginUser() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

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
    }

    private static JPanel centered(JComponent comp) { //yatay hizalamak icin kullanilan yardimci method
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(comp);
        return wrapper;
    }
}
