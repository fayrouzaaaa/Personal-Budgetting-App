/**
 * Handles user identity management, including registration, login, and session state.
 * * <p>The Authentication class interacts with the {@link Database} to verify user
 * credentials and persist new user records. It maintains a simple session state
 * using a login flag and the current user's email.</p>
 */
public class Authentication {

    private Database db = new Database();
    private boolean isLoggedIn = false;
    private String currentUserEmail = null;

    /**
     * Checks if a user email is already present in the database.
     * * @param email The email address to check.
     * @return {@code true} if the email exists in the Users table; {@code false} otherwise.
     */
    public boolean isRegistered(String email){
        email = email.trim();
        String checkSql = "SELECT email FROM Users WHERE Email = ?";
        String[] params = { email };
        return !db.selectQuery(checkSql, params, "Email").isEmpty();
    }

    /**
     * Registers a new user by inserting their details into the database.
     * * @param name     The full name of the user.
     * @param email    The email address to be used for account identification.
     * @param password The plain-text password for the account.
     */
    public void register(String name, String email, String password) {
        email = email.trim();
        password = password.trim();

        String insertSql = "INSERT INTO Users (Name, Email, Password) VALUES (?, ?, ?)";
        String[] insertParams = { name, email, password };
        db.updateQuery(insertSql, insertParams);

        System.out.println("Registered successfully!");
    }

    /**
     * Authenticates a user based on email and password.
     * * <p>If successful, sets the {@code isLoggedIn} flag to true and stores
     * the current user's email in the session.</p>
     * * @param email    The email address entered by the user.
     * @param password The password entered by the user.
     * @return {@code true} if credentials match a database record; {@code false} otherwise.
     */
    public boolean login(String email, String password) {
        email = email.trim();
        password = password.trim();

        String sql = "SELECT email FROM Users WHERE Email = ? AND Password = ?";
        String[] params = { email, password };

        if (!db.selectQuery(sql, params, "Email").isEmpty()) {
            isLoggedIn = true;
            currentUserEmail = email;
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid email or password!");
            return false;
        }
    }

    /**
     * Terminates the current user session.
     * Clears session flags and resets the {@code currentUserEmail} to null.
     */
    public void logout() {
        if (isLoggedIn) {
            isLoggedIn = false;
            currentUserEmail = null;
            System.out.println("Logged out successfully!");
        } else {
            System.out.println("No user is logged in!");
        }
    }
}