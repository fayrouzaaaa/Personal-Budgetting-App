import javax.swing.*;
import java.awt.*;

/**
 * Provides the graphical user interface for user authentication.
 * * <p>The LoginScreen class allows existing users to access the application by entering
 * their credentials. It handles input validation, communicates with the
 * {@link Authentication} service to verify credentials, and manages the transition
 * to the {@link DashboardScreen} or the {@link SignupScreen}.</p>

 */
public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private Authentication authentication = new Authentication();
    private User user = new User();

    /**
     * Constructs the LoginScreen frame.
     * Sets the window properties, creates the welcome header, and initializes
     * the login form components.
     */
    LoginScreen(){
        this.setTitle("Welcome Back!");
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("src/main/icon.png");
        this.setIconImage(image.getImage());

        JLabel welcomeText = new JLabel("Welcome Back to Personal Budgeting App!");
        welcomeText.setBounds(80,50,700,300);
        welcomeText.setFont(new Font("Segoe UI", Font.BOLD, 30));
        this.add(welcomeText);

        loginGUI();

        this.setVisible(true);
    }

    /**
     * Initializes and positions the GUI components for the login form.
     * <p>Includes text fields for email and password, the login submission button,
     * and a redirection button for users who do not yet have an account.</p>
     */
    public void loginGUI(){
        JLabel enterEmail = new JLabel("Enter Email");
        enterEmail.setBounds(165, 100, 500, 300);
        enterEmail.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.add(enterEmail);

        emailField = new JTextField();
        emailField.setBounds(165,270,300,30);
        this.add(emailField);

        JLabel enterPassword = new JLabel("Enter Password");
        enterPassword.setBounds(165, 190, 500, 300);
        enterPassword.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.add(enterPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(165,360,300,30);
        this.add(passwordField);

        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(340, 430, 100, 40);
        this.add(loginButton);

        JLabel signup = new JLabel("Don't have an account?");
        signup.setBounds(240, 360, 500, 300);
        signup.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        this.add(signup);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(430, 490, 100, 40);
        this.add(signupButton);

        // Attach event listeners
        loginButton.addActionListener(e -> validateLogin());
        signupButton.addActionListener(e -> signUpScreen());
    }

    /**
     * Processes the login attempt.
     * * <p>This method performs the following checks:
     * <ul>
     * <li>Verifies that all input fields are populated.</li>
     * <li>Authenticates credentials against the database via the {@link Authentication} class.</li>
     * </ul>
     * On success, it retrieves user profile information and initializes the {@link Regular_User}
     * session before launching the dashboard.</p>
     */
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
            // Re-auth call to ensure logic consistency (Note: redundant in logic but follows provided snippet)
            authentication.login(emailField.getText(), new String(passwordField.getPassword()));

            String email = emailField.getText();
            int ID = user.getIdByEmail(email);
            String name = user.getNameByEmail(email);

            Regular_User regularUser = new Regular_User(ID, name, email, new String(passwordField.getPassword()));

            this.dispose(); // Close login window
            DashboardScreen dashboardScreen = new DashboardScreen(regularUser);
        }
    }

    /**
     * Closes the current login screen and navigates to the registration interface.
     */
    void signUpScreen(){
        this.dispose();
        SignupScreen signupScreen = new SignupScreen();
    }
}