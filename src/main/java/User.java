import java.util.ArrayList;

public class User {
     private int userId;
     private String name;
     private String email;
     private String password;
     private Database db = new Database();

     public User() {}

     public User(int userId, String name, String email, String password) {
         this.userId = userId;
         this.name = name;
         this.email = email;
         this.password = password;
     }

     public int getUserId() {
         return userId;
     }

     public String getName() {
         return name;
     }

     public String getEmail() {
         return email;
     }

     public void setName(String name) {
         this.name = name;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public int getIdByEmail(String email){
         String sql = "SELECT ID FROM USERS WHERE EMAIL = ?";
         String [] params = {email};
         ArrayList<String> fetch= db.selectQuery(sql, params, "ID");

         return Integer.valueOf(fetch.get(0));
     }

     public String getNameByEmail(String email){
         String sql = "SELECT Name FROM USERS WHERE EMAIL = ?";
         String [] params = {email};
         ArrayList<String> fetch= db.selectQuery(sql, params, "Name");

         return fetch.get(0);
     }
 }

