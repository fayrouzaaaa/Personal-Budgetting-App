import java.sql.*;
import java.util.*;

public class Database {
    private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433;database=Personal_Budgetting;encrypt=true;trustServerCertificate=true;integratedSecurity=true";
    private Connection connection = null;
    private PreparedStatement query;
    private ArrayList<String> result;

    public Connection connectDB() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public ArrayList<String> selectQuery(String sql, String[] parameters, String columnName) {
        try {
            result = new ArrayList<>();
            query = (this.connectDB()).prepareStatement(sql);
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

    public void updateQuery(String sql, String[] parameters) {
        try {
            query = (this.connectDB()).prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                query.setString(i + 1, parameters[i]);
            }
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
};