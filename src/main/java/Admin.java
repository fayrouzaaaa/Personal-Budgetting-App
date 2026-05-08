/**
 * Represents an Administrative user within the system with elevated privileges.
 * * <p>The Admin class extends the {@link User} base class and provides specialized
 * functionality for system oversight, including user management, report generation,
 * and global category configuration.</p>

 */
public class Admin extends User {

    /**
     * Provides functionality to manage system users.
     * This includes tasks such as viewing user lists, updating user status,
     * or handling account-related administrative actions.
     * * @throws UnsupportedOperationException if the method is not yet implemented.
     */
    public void manageUsers() {
        throw new UnsupportedOperationException();
    }

    /**
     * Generates comprehensive system-wide reports.
     * These reports typically aggregate data across all users to provide insights
     * into system usage and financial trends.
     * * @throws UnsupportedOperationException if the method is not yet implemented.
     */
    public void generateSystemReports() {
        throw new UnsupportedOperationException();
    }

    /**
     * Configures and manages the global transaction categories.
     * Allows the administrator to add, edit, or remove default categories
     * available to all regular users.
     * * @throws UnsupportedOperationException if the method is not yet implemented.
     */
    public void configureCategories() {
        throw new UnsupportedOperationException();
    }
}