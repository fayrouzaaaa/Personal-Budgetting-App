import java.sql.*;

public class Main{
    public static void main(String[] args){

        final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433;database=Personal_Budgetting;encrypt=true;trustServerCertificate=true;integratedSecurity=true";

        try{
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Transactions");

            while (result.next()){
                System.out.println(result.getString("Type"));
            }
        }

        catch (SQLException e){
            e.printStackTrace();
        }
    }
}