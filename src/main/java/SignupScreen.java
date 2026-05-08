import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SignupScreen extends JFrame{

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private User user = new User();
    private Authentication authentication = new Authentication();

    SignupScreen(){
        this.setTitle("Welcome!");
        setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        JLabel welcomeText = new JLabel("Welcome to Personal Budgeting App!");
        welcomeText.setBounds(120,0,700,300);
        welcomeText.setFont(new Font("SansSerif", Font.BOLD, 30));
        this.add(welcomeText);

        signUpGUI();

        this.setVisible(true);
    }

    public void signUpGUI(){
        JLabel enterName = new JLabel("Enter Name");
        enterName.setBounds(165, 50, 500, 300);
        enterName.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(enterName);

        nameField = new JTextField();
        nameField.setBounds(165,220,300,30);
        this.add(nameField);

        JLabel enterEmail= new JLabel("Enter Email");
        enterEmail.setBounds(165, 140, 500, 300);
        enterEmail.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(enterEmail);

        emailField = new JTextField();
        emailField.setBounds(165,310,300,30);
        this.add(emailField);

        JLabel enterPassword= new JLabel("Enter Password");
        enterPassword.setBounds(165, 230, 500, 300);
        enterPassword.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(enterPassword);

        passwordField=  new JPasswordField();
        passwordField.setBounds(165,400,300,30);
        this.add(passwordField);

        JLabel enterConfirm= new JLabel("Confirm Password");
        enterConfirm.setBounds(165, 320, 500, 300);
        enterConfirm.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(enterConfirm);

        confirmField = new JPasswordField();
        confirmField.setBounds(165,490,300,30);
        this.add(confirmField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(340, 550, 100, 40);
        this.add(signupButton);

        JLabel login = new JLabel("Already have an account?");
        login.setBounds(230, 480, 500, 300);
        login.setFont(new Font("SansSerif", Font.ITALIC, 15));
        this.add(login);

        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(430, 610, 100, 40);
        this.add(loginButton);

        signupButton.addActionListener(e->validateSignUp());
        loginButton.addActionListener(e->loginScreen());
    }

    public void validateSignUp(){
        if ((nameField.getText()).equals("") || (emailField.getText()).equals("") || (new String(passwordField.getPassword())).equals("")){
            ErrorScreen error = new ErrorScreen();
            error.addMessage("Some fields are empty", 160, 50, 1, 25);
            error.addMessage("Please fill out all fields",195,120,0,20);
        }

        else if (authentication.isRegistered(emailField.getText())){
            ErrorScreen error = new ErrorScreen();
            error.addMessage("This user already exists", 150, 50, 1, 25);
            error.addMessage("Please enter another email or log in", 130, 120, 0, 20);
        }

        else if (!(passwordField.getText()).equals(confirmField.getText())){
            ErrorScreen error = new ErrorScreen();
            error.addMessage("The passwords you entered don't match", 50, 50, 1, 25);
            error.addMessage("Please try again",220,120,0,20);
        }

        else {
            authentication.register(nameField.getText(), emailField.getText(), new String(passwordField.getPassword()));
            int ID = user.getIdByEmail(emailField.getText());
            Regular_User regularUser = new Regular_User(ID, nameField.getText(), emailField.getText(), new String(passwordField.getPassword()));
            this.dispose();
            DashboardScreen dashboardScreen = new DashboardScreen(regularUser);
        }
    }

    public void loginScreen(){
        this.dispose();
        LoginScreen loginScreen = new LoginScreen();
    }
}
