import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * BudgetScreen provides a comprehensive graphical overview of the user's monthly budgets.
 * * <p>It features a summary card showing total budget vs. total spent, a visual
 * global progress bar, and a scrollable list of individual {@link Budget_Item}
 * progress trackers for different categories.</p>

 */
public class BudgetScreen extends JFrame {
    private Calendar calendar = Calendar.getInstance();
    private Regular_User user;
    private String totalBudget;
    private String totalSpent;
    private Budget budget;
    private Budget_Item item;
    private String month;
    private String year;
    private int userID;
    private Footer footer;

    /**
     * Constructs the BudgetScreen for a specific user.
     * Initializes current date context, fetches budget data from the database,
     * and triggers the UI assembly methods.
     * * @param user The authenticated {@link Regular_User} whose budget data is displayed.
     */
    BudgetScreen(Regular_User user){
        this.setTitle("Budgets");
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        footer = new Footer(this, user );
        this.add(footer);
        this.user = user;
        userID = user.getUserId();

        // Initialize current Month and Year strings
        month = new SimpleDateFormat("MMM").format(calendar.getTime());
        year = new SimpleDateFormat("YYYY").format(calendar.getTime());

        budget = new Budget();
        item = new Budget_Item();

        // Fetch financial aggregates from the database
        totalBudget = String.valueOf(budget.getBudgetSum(userID));
        totalSpent = String.valueOf(budget.getSpentSum(userID));

        showBudgetSummary();
        showBudgetItems();
        createButton();
    }

    /**
     * Builds and displays the top summary panel.
     * <p>Includes the month/year title, total budget amount, total spent,
     * remaining balance, and a {@link JProgressBar} representing the
     * overall budget usage percentage.</p>
     */
    public void showBudgetSummary(){
        JPanel budgetPanel = new JPanel();
        budgetPanel.setLayout(null);
        budgetPanel.setBounds(100, 50, 540, 180);
        budgetPanel.setBackground(new Color(0x93A7F3));
        this.add(budgetPanel);

        JLabel date = new JLabel(month + " " + year + " Total Budget");
        date.setBounds(20, -130, 400, 300);
        date.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        date.setForeground(Color.white);
        budgetPanel.add(date);

        JLabel budgetAmount = new JLabel (user.getCurrency()+ " " + totalBudget);
        budgetAmount.setBounds(20, -80, 400, 300);
        budgetAmount.setFont(new Font("Segoe UI", Font.BOLD, 50));
        budgetAmount.setForeground(Color.white);
        budgetPanel.add(budgetAmount);

        double totalBudgetNum = Double.valueOf(totalBudget);
        double totalSpentNum = Double.valueOf(totalSpent);
        double totalRemaining = totalBudgetNum - totalSpentNum;

        JLabel spent = new JLabel (user.getCurrency()+ " Spent: "+ totalSpent);
        spent.setBounds(20, -30, 400, 300);
        spent.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        spent.setForeground(Color.white);
        budgetPanel.add(spent);

        JLabel remain = new JLabel (user.getCurrency()+ " Spent: "+ String.valueOf(totalRemaining));
        remain.setBounds(350, -30, 400, 300);
        remain.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        remain.setForeground(Color.white);
        budgetPanel.add(remain);

        // Global progress calculation
        JProgressBar progress = new JProgressBar();
        if ((int)totalBudgetNum != 0) {
            int progressValue = ((int) totalSpentNum * 100) / (int) totalBudgetNum;
            progress.setValue(progressValue);
        } else {
            progress.setValue(0);
        }
        progress.setBounds(20, 140, 500, 30);
        budgetPanel.add(progress);
    }

    /**
     * Generates a scrollable list of individual budget items.
     * <p>For each category, it creates a label showing the name and the ratio
     * of spent vs. limit, accompanied by a category-specific progress bar.</p>
     */
    public void showBudgetItems(){
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(null);
        itemsPanel.setPreferredSize(new Dimension(530, 350));

        // Fetch item details lists
        ArrayList<String> itemNames = item.getItemCategory(userID);
        ArrayList<String> itemSpent = item.getItemSpent(userID);
        ArrayList<String> itemLimit = item.getItemLimit(userID);

        int yOfName = -120;
        int yOfBar = 50;

        for (int i = 0; i < itemNames.size(); i++){
            JLabel name = new JLabel (itemNames.get(i));
            name.setBounds(15, yOfName, 300, 300);
            name.setFont(new Font ("Segoe UI", Font.BOLD, 20));
            itemsPanel.add(name);

            JLabel progress = new JLabel(itemSpent.get(i) + " / " + itemLimit.get(i));
            progress.setBounds(390, yOfName, 300, 300);
            progress.setFont(new Font("Segoe UI",Font.PLAIN, 20 ));
            itemsPanel.add(progress);

            yOfName += 90;

            // Category progress calculation
            double limit = Double.valueOf(itemLimit.get(i));
            double spent = Double.valueOf(itemSpent.get(i));
            int progressValue = ((int)spent * 100) / (int)limit;

            JProgressBar itemProgress = new JProgressBar();
            itemProgress.setBounds(15, yOfBar, 500, 30);
            itemProgress.setValue(progressValue);
            itemsPanel.add(itemProgress);
            yOfBar += 90;
        }

        // Add the items panel to a scroll pane to handle long lists
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBounds(100, 270, 540, 350);
        this.add(scrollPane);
    }

    /**
     * Initializes and styles the "Create Budget" button.
     */
    public void createButton(){
        JButton addBudgetButton = new JButton("Create Budget");
        addBudgetButton.setBackground(new Color(0x93A7F3));
        addBudgetButton.setForeground(Color.white);
        addBudgetButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        addBudgetButton.setBounds(500, 650, 200, 50);
        this.add(addBudgetButton);
        addBudgetButton.addActionListener(e -> addBudget());
    }

    /**
     * Action handler that opens the {@link AddBudgetScreen} to allow the user
     * to input a new budget record.
     */
    public void addBudget(){
        AddBudgetScreen addBudgetScreen = new AddBudgetScreen(user);
    }
}