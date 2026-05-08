import java.sql.*;
import java.util.*;

/**
 * The Database class serves as the core communication layer between the application
 * and the SQL Server database.
 * * <p>It manages database connectivity using JDBC and provides simplified
 * methods to execute SELECT and UPDATE/INSERT queries. It utilizes
 * {@link PreparedStatement} to ensure secure, parameterized SQL execution
 * and prevent SQL injection attacks.</p>

 */
public class Database {
    /** Connection string for SQL Server with integrated security and encryption settings. */
    private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433;database=Personal_Budgetting;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

    private Connection connection = null;
    private PreparedStatement query;
    private ArrayList<String> result;

    /**
     * Establishes a connection to the SQL Server database.
     * * @return A {@link Connection} object if successful; {@code null} if a
     * {@link SQLException} occurs.
     */
    public Connection connectDB() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Executes a SELECT SQL statement and returns the values of a specific column.
     * * @param sql        The SQL query string with placeholders (?).
     * @param parameters An array of strings to be bound to the SQL placeholders.
     * @param columnName The name of the column from which data should be retrieved.
     * @return An {@link ArrayList} containing the retrieved column values as strings.
     */
    public ArrayList<String> selectQuery(String sql, String[] parameters, String columnName) {
        try {
            result = new ArrayList<>();
            query = (this.connectDB()).prepareStatement(sql);

            // Binding parameters to placeholders
            for (int i = 0; i < parameters.length; i++) {
                query.setString((i + 1), parameters[i]);
            }

            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Executes a non-query SQL statement (INSERT, UPDATE, or DELETE).
     * * @param sql        The SQL statement string with placeholders (?).
     * @param parameters An array of strings to be bound to the SQL placeholders.
     */
    public void updateQuery(String sql, String[] parameters) {
        try {
            query = (this.connectDB()).prepareStatement(sql);

            // Binding parameters to placeholders
            for (int i = 0; i < parameters.length; i++) {
                query.setString(i + 1, parameters[i]);
            }

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}