import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BudgetScreen extends JFrame {
    Calendar calendar = Calendar.getInstance();
    Regular_User user;
    String totalBudget;
    String totalSpent;
    String totalRemaining;
    private String month = new SimpleDateFormat("MMM").format(calendar.getTime());
    private String year = new SimpleDateFormat("YYYY").format(calendar.getTime());
    private Database db = new Database();
    private String userID;
    private ArrayList<String> fetch = new ArrayList<>();
    private Footer footer;

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
        userID = String.valueOf(user.getUserId());
        String sql = "SELECT SUM(limit_amount) as SUM from Budget_Items, Budgets " +
                     "where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        fetch.addAll(db.selectQuery(sql, new String[] {userID}, "SUM"));

        totalBudget = fetch.get(0);

        fetch.clear();

        sql = "SELECT SUM(spent_amount) as SUM from Budget_Items, Budgets " +
                "where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";

        fetch.addAll(db.selectQuery(sql, new String[] {userID}, "SUM"));

        totalSpent = fetch.get(0);

        showBudgetSummary();

        showBudgetItems();

        createButton();
    }

    public void showBudgetSummary(){
        JPanel budgetPanel = new JPanel();
        budgetPanel.setLayout(null);
        budgetPanel.setBounds(100, 50, 540, 180);
        budgetPanel.setBackground(new Color(0x93A7F3));
        this.add(budgetPanel);

        JLabel date = new JLabel(month + " " + year + " Total Budget");
        date.setBounds(20, -130, 400, 300);
        date.setFont(new Font("SansSerif", Font.PLAIN, 20));
        date.setForeground(Color.white);
        budgetPanel.add(date);

        JLabel budgetAmount = new JLabel (user.getCurrency()+ " " + totalBudget);
        budgetAmount.setBounds(20, -80, 400, 300);
        budgetAmount.setFont(new Font("SansSerif", Font.BOLD, 50));
        budgetAmount.setForeground(Color.white);
        budgetPanel.add(budgetAmount);

        double totalBudgetNum = Double.valueOf(totalBudget);
        double totalSpentNum = Double.valueOf(totalSpent);
        double totalRemaining = totalBudgetNum - totalSpentNum;


        JLabel spent =new JLabel (user.getCurrency()+ " Spent: "+ totalSpent);
        spent.setBounds(20, -30, 400, 300);
        spent.setFont(new Font("SansSerif", Font.PLAIN, 20));
        spent.setForeground(Color.white);
        budgetPanel.add(spent);

        JLabel remain =new JLabel (user.getCurrency()+ " Spent: "+ String.valueOf(totalRemaining));
        remain.setBounds(350, -30, 400, 300);
        remain.setFont(new Font("SansSerif", Font.PLAIN, 20));
        remain.setForeground(Color.white);
        budgetPanel.add(remain);

        JProgressBar progress = new JProgressBar();
        int progressValue = ((int)totalSpentNum*100)/(int)totalBudgetNum;
        progress.setValue(progressValue);
        progress.setBounds(20, 140, 500, 30);
        budgetPanel.add(progress);

    }

    public void showBudgetItems(){
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(null);
        itemsPanel.setPreferredSize(new Dimension(530, 340));

        String sql = "select Categories.name as Name from Categories, Budget_Items,Budgets " +
                "where Budgets.user_id=? AND Budgets.budget_id=Budget_Items.budget_id AND " +
                "Categories.category_id=Budget_Items.category_id";
        ArrayList<String> itemNames = db.selectQuery(sql, new String[] {userID}, "Name");

        sql = "select spent_amount from Budget_Items, dbo.Budgets where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        ArrayList<String> itemSpent = db.selectQuery(sql, new String[] {userID}, "spent_amount");

        sql = "select limit_amount from Budget_Items, dbo.Budgets where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        ArrayList<String> itemLimit = db.selectQuery(sql, new String[] {userID}, "limit_amount");
        int yOfName= -120;
        int yOfBar = 50;
        for (int i=0; i<itemNames.size(); i++){
            JLabel name = new JLabel (itemNames.get(i));
            name.setBounds(15, yOfName, 300, 300);
            name.setFont(new Font ("SansSerif", Font.BOLD, 20));
            itemsPanel.add(name);

            JLabel progress = new JLabel(itemSpent.get(i) + " / " + itemLimit.get(i));
            progress.setBounds(390, yOfName, 300, 300);
            progress.setFont(new Font("SansSerif",Font.PLAIN, 20 ));
            itemsPanel.add(progress);

            yOfName += 90;

            double limit = Double.valueOf(itemLimit.get(i));
            double spent = Double.valueOf(itemSpent.get(i));
            int progressValue = ((int)spent*100)/(int)limit;

            JProgressBar itemProgress = new JProgressBar();
            itemProgress.setBounds(15, yOfBar, 500, 30);
            itemProgress.setValue(progressValue);
            itemsPanel.add(itemProgress);
            yOfBar+=90;
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBounds(100, 270, 540, 350);
        this.add(scrollPane);
    }

    public void createButton(){
        JButton addBudgetButton = new JButton("Create Budget");
        addBudgetButton.setBackground(new Color(0x93A7F3));
        addBudgetButton.setForeground(Color.white);
        addBudgetButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        addBudgetButton.setBounds(500, 650, 200, 50);
        this.add(addBudgetButton);
    }
}
