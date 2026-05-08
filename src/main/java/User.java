import java.util.ArrayList;

/**
 * Represents the base User entity within the Personal Budgeting system.
 * * <p>The User class stores core profile information including credentials and
 * identification. It provides common functionality for retrieving user data
 * from the database based on unique attributes like email addresses. This class
 * serves as the superclass for more specialized user types like {@link Regular_User}.</p>

 */
public class User {
    private int userId;
    private String name;
    private String email;
    private String password;

    /** Database utility instance for executing SQL selection queries. */
    private Database db = new Database();

    /**
     * Default constructor for creating an empty User object.
     */
    public User() {}

    /**
     * Constructs a User with full profile details.
     * * @param userId   The unique primary key identifier from the database.
     * @param name     The full name of the user.
     * @param email    The registered email address (used for identification).
     * @param password The encrypted or plain-text password for authentication.
     */
    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * @return The unique ID of this user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return The name of this user.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The email address associated with this user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the local name of the user.
     * @param name The new name string.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the local email address of the user.
     * @param email The new email string.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Queries the database to retrieve a user's ID based on their email address.
     * * @param email The email address to search for.
     * @return The integer ID found in the USERS table.
     * @throws IndexOutOfBoundsException if no user is found with the provided email.
     */
    public int getIdByEmail(String email){
        String sql = "SELECT ID FROM USERS WHERE EMAIL = ?";
        String [] params = {email};
        ArrayList<String> fetch = db.selectQuery(sql, params, "ID");

        return Integer.valueOf(fetch.get(0));
    }

    /**
     * Queries the database to retrieve a user's name based on their email address.
     * * @param email The email address to search for.
     * @return The name string found in the USERS table.
     * @throws IndexOutOfBoundsException if no user is found with the provided email.
     */
    public String getNameByEmail(String email){
        String sql = "SELECT Name FROM USERS WHERE EMAIL = ?";
        String [] params = {email};
        ArrayList<String> fetch = db.selectQuery(sql, params, "Name");

        return fetch.get(0);
    }
}