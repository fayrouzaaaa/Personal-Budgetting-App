import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormatSymbols;
import java.util.*;
import java.time.*;

/**
 * AddBudgetScreen provides a graphical user interface for users to define
 * and save a monthly budget for specific categories.
 * * <p>It includes validation to ensure the budget amount is numeric and
 * links the budget item to the selected category and month in the database.</p>

 */
public class AddBudgetScreen extends JFrame {
    private Regular_User user;
    private String userID;
    private Database db = new Database();
    private ArrayList<String> categoryNames = new ArrayList<>();
    private ArrayList<String> monthNames = new ArrayList<>();
    private String sql;
    private String[] params = {};
    private JComboBox categoryMenu;
    private JComboBox monthMenu;
    private JTextField amountField;
    private Budget_Item budgetItem;

    /**
     * Constructs a new AddBudgetScreen for a specific user.
     * Initializes UI components and populates category and month lists from the database.
     * * @param user The authenticated Regular_User creating the budget.
     */
    AddBudgetScreen(Regular_User user){
        this.setTitle("Add Budget");
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.user =  user;
        userID = String.valueOf(user.getUserId());

        // Fetch categories from database to populate the dropdown
        sql = "select name from Categories";
        categoryNames.addAll(db.selectQuery(sql, params, "name"));

        // Format month names to abbreviated uppercase (e.g., JAN, FEB)
        String[] months = new DateFormatSymbols().getMonths();
        for (int i=0; i < (months.length)-1; i++){
            monthNames.add(((months[i]).substring(0,3)).toUpperCase());
        }
        showOptions();
    }

    /**
     * Initializes and positions all graphical components (labels, text fields, combos).
     * Adds a KeyListener to the amount field to restrict input to numeric values.
     */
    public void showOptions(){
        JLabel category = new JLabel ("Budget Category");
        category.setBounds(100, 0, 300, 300);
        category.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        this.add(category);

        categoryMenu = new JComboBox();
        for (int i=0; i<categoryNames.size(); i++){
            categoryMenu.addItem(categoryNames.get(i));
        }
        categoryMenu.setBounds(100, 165, 350, 40);
        categoryMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        categoryMenu.setBackground(Color.white);
        this.add(categoryMenu);

        JLabel amount = new JLabel ("Budget Amount");
        amount.setBounds(100, 125, 300, 300);
        amount.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        this.add(amount);

        amountField = new JTextField();
        amountField.setBounds(100, 300, 350, 40);

        // Input validation: Only allow digits, decimals, or backspace
        amountField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent){
                if ((keyEvent.getKeyChar() >='0' && keyEvent.getKeyChar() <= '9') ||
                        keyEvent.getKeyChar()=='.' || keyEvent.getKeyChar()==8){
                    amountField.setEditable(true);
                }
                else {
                    amountField.setEditable(false);
                    validationError();
                }
            }
        });
        this.add(amountField);

        JLabel month = new JLabel ("Budget Month");
        month.setBounds(100, 250, 300, 300);
        month.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        this.add(month);

        monthMenu = new JComboBox();
        for (int i=0; i<monthNames.size(); i++){
            monthMenu.addItem(monthNames.get(i));
        }
        monthMenu.setBounds(100, 420, 350, 40);
        monthMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        monthMenu.setBackground(Color.white);
        this.add(monthMenu);

        JButton create = new JButton ("Create Budget");
        create.setBounds(190, 550, 200, 50);
        create.setBackground(new Color(0x93A7F3));
        create.setForeground(Color.white);
        create.setFont(new Font("Segoe UI", Font.BOLD, 20));
        create.addActionListener(e-> createBudget());
        this.add(create);
    }

    /**
     * Displays an error screen when the user enters a non-numeric value in the amount field.
     */
    public void validationError(){
        ErrorScreen errorScreen = new ErrorScreen();
        errorScreen.addMessage("The value you entered is not a Number", 65, 50, 1, 25);
        errorScreen.addMessage("Please try again",220,120,0,20);
    }

    /**
     * Validates input fields and saves the budget information to the database.
     * Fetches category IDs and creates a {@link Budget_Item} to perform the save operation.
     */
    public void createBudget(){
        if (amountField.getText().equals("")){
            ErrorScreen errorScreen = new ErrorScreen();
            errorScreen.addMessage("Amount field is empty", 160, 50, 1, 25);
            errorScreen.addMessage("Please fill out all fields",195,120,0,20);
        }
        else {
            String limit = amountField.getText();
            double limitAmount = Double.valueOf(limit);
            String categoryName = (categoryMenu.getSelectedItem()).toString();

            // Retrieve Category ID based on selection
            sql = "SELECT category_id from Categories WHERE name = ?";
            ArrayList<String> fetch = db.selectQuery(sql, new String[] {categoryName}, "category_id");
            int id = Integer.valueOf(fetch.get(0));

            // Create budget item object
            budgetItem = new Budget_Item(new Category(id, categoryName, false), limitAmount);
            fetch.clear();

            // Link the budget item to the specific month and user
            String budgetMonth = (monthMenu.getSelectedItem()).toString();
            sql = "select budget_id from Budgets where month=? and user_id=?";
            fetch.addAll(db.selectQuery(sql, new String[] {budgetMonth, userID}, "budget_id"));
            id = Integer.valueOf(fetch.get(0));
            budgetItem.saveToDatabase(id);

            this.dispose();
        }
    }
}