public class Authentication {

    private Database db = new Database();
    private boolean isLoggedIn = false;
    private String currentUserEmail = null;

    //Check if user is already registered
    public boolean isRegistered(String email){
        email = email.trim();
        String checkSql = "SELECT email FROM Users WHERE Email = ?";
        String[] params = { email };
        if (!db.selectQuery(checkSql, params, "Email").isEmpty())
            return true;
        return false;
    }

    // Register
    public void register(String name,String email, String password) {
        email = email.trim();
        password = password.trim();
       /* // check if user already exists
        String checkSql = "SELECT email FROM Users WHERE Email = ?";
        String[] params = { email };

        if (!db.selectQuery(checkSql, params, "Email").isEmpty()) {
            System.out.println("User already exists!");
            return;
        }*/

        // insert new user
        String insertSql = "INSERT INTO Users (Name, Email, Password) VALUES (?, ?, ?)";
        String[] insertParams = { name, email, password };
        db.updateQuery(insertSql, insertParams);

        System.out.println("Registered successfully!");
    }


    // Login
    public boolean  login(String email, String password) {
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

    //  Logout
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