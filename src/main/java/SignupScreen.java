import javax.swing.*;
import java.awt.*;

public class SignupScreen extends JFrame{

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;

    SignupScreen(){
        this.setTitle("Welcome!");
        this.setSize(800,800);
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
        if (!(passwordField.getText()).equals(confirmField.getText())){

            JFrame error = new JFrame ("Error");
            error.setSize(600,350);
            error.setLayout(null);
            error.setLocationRelativeTo(null);

            JLabel message1 = new JLabel ("The passwords you entered don't match");
            JLabel message2 = new JLabel("Please try again");
            message1.setBounds(50, 50, 500, 50);
            message2.setBounds(220,120, 500, 50);
            message1.setFont(new Font ("SansSerif", Font.BOLD, 25));
            message2.setFont(new Font("SansSerif", Font.PLAIN, 20));
            error.add(message1);
            error.add(message2);
            JButton ok = new JButton ("OK");
            ok.setBounds(250, 200, 90, 30);
            ok.addActionListener(ev->{
                error.dispose();
            });
            error.add(ok);

            error.setVisible(true);
        }

        else {
            this.dispose();
            DashboardScreen dashboardScreen = new DashboardScreen();
        }
    }

    public void loginScreen(){
        this.dispose();
        LoginScreen loginScreen = new LoginScreen();
    }
}
