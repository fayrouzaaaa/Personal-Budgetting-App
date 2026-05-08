import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private Authentication authentication = new Authentication();
    private User user = new User();

    LoginScreen(){
        this.setTitle("Welcome Back!");
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        JLabel welcomeText = new JLabel("Welcome Back to Personal Budgeting App!");
        welcomeText.setBounds(80,50,700,300);
        welcomeText.setFont(new Font("SansSerif", Font.BOLD, 30));
        this.add(welcomeText);

        loginGUI();

        this.setVisible(true);
    }

    public void loginGUI(){
        JLabel enterEmail= new JLabel("Enter Email");
        enterEmail.setBounds(165, 100, 500, 300);
        enterEmail.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(enterEmail);

        emailField = new JTextField();
        emailField.setBounds(165,270,300,30);
        this.add(emailField);

        JLabel enterPassword= new JLabel("Enter Password");
        enterPassword.setBounds(165, 190, 500, 300);
        enterPassword.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(enterPassword);

        passwordField=  new JPasswordField();
        passwordField.setBounds(165,360,300,30);
        this.add(passwordField);

        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(340, 430, 100, 40);
        this.add(loginButton);

        JLabel signup = new JLabel("Don't have an account?");
        signup.setBounds(240, 360, 500, 300);
        signup.setFont(new Font("SansSerif", Font.ITALIC, 15));
        this.add(signup);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(430, 490, 100, 40);
        this.add(signupButton);

        loginButton.addActionListener(e->validateLogin());
        signupButton.addActionListener(e->signUpScreen());
    }

    void validateLogin() {
        if ((emailField.getText()).equals("") || (new String(passwordField.getPassword())).equals("")) {
            ErrorScreen error = new ErrorScreen();
            error.addMessage("Some fields are empty", 160, 50, 1, 25);
            error.addMessage("Please fill out all fields", 195, 120, 0, 20);
        }

        else if (!(authentication.login(emailField.getText(), new String(passwordField.getPassword())))) {
            ErrorScreen error = new ErrorScreen();
            error.addMessage("The email or password are incorrect", 90, 50, 1, 25);
            error.addMessage("Please try again", 220, 120, 0, 20);
        }

        else {
            authentication.login(emailField.getText(), new String(passwordField.getPassword()));
            String email = emailField.getText();
            int ID = user.getIdByEmail(emailField.getText());
            String name = user.getNameByEmail(emailField.getText());
            Regular_User regularUser = new Regular_User(ID , name, emailField.getText(), new String(passwordField.getPassword()));
            this.dispose();
            DashboardScreen dashboardScreen = new DashboardScreen(regularUser);
        }
    }

    void signUpScreen(){
        this.dispose();
        SignupScreen signupScreen = new SignupScreen();
    }

}