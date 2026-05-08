import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DashboardScreen extends JFrame{
	private Database db = new Database();
	private Footer footer;
	private String a[] ={};
	private Regular_User user;
	private String userID;
	ArrayList<String> fetch = new ArrayList<>();

	DashboardScreen(Regular_User user){
		this.user = user;
		userID = String.valueOf(user.getUserId());
		this.setTitle("Home");
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);

		JLabel hello = new JLabel("Hello, " + user.getName());
		hello.setBounds(80,-60,700,300);
		hello.setFont(new Font("SansSerif", Font.BOLD, 30));
		this.add(hello);

		footer = new Footer (this, user);
		this.add(footer);

		showSummary();
		showRecentTransactions();
		this.setVisible(true);
	}

	public void showSummary(){
		JPanel summary = new JPanel();
		summary.setLayout(null);
		summary.setBounds(100, 130, 540,200);
		summary.setBackground(new Color(0x93A7F3));
		this.add(summary);

		JLabel totalBalance = new JLabel("Total Balance:");
		totalBalance.setBounds(20, -120, 400, 300);
		totalBalance.setFont(new Font("SansSerif", Font.PLAIN, 20));
		totalBalance.setForeground(Color.white);
		summary.add(totalBalance);

		JLabel balanceAmount = new JLabel (user.getCurrency()+ " " + user.getBalance());
		balanceAmount.setBounds(20, -70, 400, 300);
		balanceAmount.setFont(new Font("SansSerif", Font.BOLD, 50));
		balanceAmount.setForeground(Color.white);
		summary.add(balanceAmount);

		JLabel income = new JLabel("Income:");
		income.setBounds(20, 0, 400, 300);
		income.setFont(new Font("SansSerif", Font.PLAIN, 15));
		income.setForeground(Color.white);
		summary.add(income);

		fetch.addAll(db.selectQuery("Select SUM(Amount) as Sum From Transactions where Type='Income' And User_ID=?", new String[] {userID}, "Sum"));
		JLabel incomeAmount = new JLabel(user.getCurrency() + " " + fetch.get(0));
		incomeAmount.setBounds(20, 20, 400, 300);
		incomeAmount.setFont(new Font("SansSerif", Font.BOLD, 20));
		incomeAmount.setForeground(Color.white);
		summary.add(incomeAmount);
		fetch.clear();

		JLabel expense = new JLabel("Expense:");
		expense.setBounds(400, 0, 400, 300);
		expense.setFont(new Font("SansSerif", Font.PLAIN, 15));
		expense.setForeground(Color.white);
		summary.add(expense);

		fetch.addAll(db.selectQuery("Select SUM(Amount) as Sum From Transactions where Type='Expense' And User_ID= ?", new String[] {userID}, "Sum"));
		JLabel expenseAmount = new JLabel(user.getCurrency() + " " + fetch.get(0));
		expenseAmount.setBounds(400, 20, 400, 300);
		expenseAmount.setFont(new Font("SansSerif", Font.BOLD, 20));
		expenseAmount.setForeground(Color.white);
		summary.add(expenseAmount);
		fetch.clear();

		JPanel transactionsSummary = new JPanel();
		transactionsSummary.setLayout(null);
		transactionsSummary.setBounds(100, 350, 160,100);
		transactionsSummary.setBackground(Color.white);
		this.add(transactionsSummary);

		JLabel transactions = new JLabel ("Transactions");
		transactions.setBounds(20,0, 300, 50);
		transactions.setFont(new Font("SansSerif", Font.PLAIN, 20));
		transactionsSummary.add(transactions);

		fetch.addAll(db.selectQuery("Select Count(ID) AS SUM FROM TRANSACTIONS WHERE User_ID=?",
				new String[] {userID}, "SUM"));
		JLabel transactionCount = new JLabel (fetch.get(0));
		transactionCount.setBounds(65, 40, 300, 50);
		transactionCount.setFont(new Font("SansSerif", Font.BOLD, 50));
		transactionsSummary.add(transactionCount);
		fetch.clear();

		JPanel budgetSummary = new JPanel();
		budgetSummary.setLayout(null);
		budgetSummary.setBounds(290, 350, 160,100);
		budgetSummary.setBackground(Color.white);
		this.add(budgetSummary);

		JLabel budgets = new JLabel ("Active Budgets");
		budgets.setBounds(13,0, 300, 50);
		budgets.setFont(new Font("SansSerif", Font.PLAIN, 20));
		budgetSummary.add(budgets);

		fetch.addAll(db.selectQuery("Select Count(Budget_Items.id) AS SUM FROM Budget_Items, Budgets " +
						"WHERE Budgets.user_id=? AND Budgets.budget_id=Budget_Items.budget_id",
				new String[] {userID}, "SUM"));
		JLabel activeBudgets = new JLabel (fetch.get(0));
		activeBudgets.setBounds(65, 40, 300, 50);
		activeBudgets.setFont(new Font("SansSerif", Font.BOLD, 50));
		budgetSummary.add(activeBudgets);
		fetch.clear();

		JPanel goalSummary = new JPanel();
		goalSummary.setLayout(null);
		goalSummary.setBounds(480, 350, 160, 100);
		goalSummary.setBackground(Color.white);
		this.add(goalSummary);

		JLabel goals = new JLabel ("Goals");
		goals.setBounds(50,0, 300, 50);
		goals.setFont(new Font("SansSerif", Font.PLAIN, 20));
		goalSummary.add(goals);

		fetch.addAll(db.selectQuery("Select Count(goal_id) AS SUM FROM Goals where user_id = ?",
				new String[] {userID}, "SUM"));
		JLabel goalCount = new JLabel (fetch.get(0));
		goalCount.setBounds(65, 40, 300, 50);
		goalCount.setFont(new Font("SansSerif", Font.BOLD, 50));
		goalSummary.add(goalCount);
		fetch.clear();

	}
	public void updateSummary(){

	}

	public void showRecentTransactions(){
		JLabel recent = new JLabel("Recent Transactions");
		recent.setBounds(100,340, 400, 300);
		recent.setFont(new Font("SansSerif", Font.BOLD, 30));
		this.add(recent);

		fetch.addAll(db.selectQuery("select Name from Transactions where User_ID=? order by Date desc", new String[] {userID}, "Name"));
		int y = 390;
		for (int i=0; i<fetch.size() && i<3; i++){
			JLabel name = new JLabel(fetch.get(i));
			name.setBounds(100, y, 400,300);
			name.setFont(new Font("SansSerif", Font.BOLD, 20));
			this.add(name);
			y += 60;
		}
		fetch.clear();

		fetch.addAll(db.selectQuery("select Category from Transactions where User_ID=? order by Date desc", new String[] {userID}, "Category"));
		y = 420;
		for (int i=0; i<fetch.size() && i<3; i++){
			JLabel category = new JLabel(fetch.get(i));
			category.setBounds(100, y, 400,300);
			category.setFont(new Font("SansSerif", Font.PLAIN, 15));
			this.add(category);
			y += 60;
		}
		fetch.clear();

		fetch.addAll(db.selectQuery("select Date from Transactions where User_ID=? order by Date desc", new String[] {userID}, "Date"));
		y = 420;
		for (int i=0; i<fetch.size() && i<3; i++){
			JLabel date = new JLabel(fetch.get(i));
			date.setBounds(220, y, 400,300);
			date.setFont(new Font("SansSerif", Font.PLAIN, 15));
			this.add(date);
			y += 60;
		}
		fetch.clear();
		ArrayList<String> type = db.selectQuery("select Type from Transactions where User_ID=? order by Date desc", new String[] {userID}, "Type");
		fetch.addAll(db.selectQuery("select Amount from Transactions where User_ID=? order by Date desc", new String[] {userID}, "Amount"));
		y = 420;
		for (int i=0; i<fetch.size() && i<3; i++){
			String symbol = "";
			Color color;
			if ((type.get(i)).equals("Income")){
				color = new Color(0x81DE5E);
				symbol = "+ ";
			}
			else {
				color = new Color(0xC23636);
				symbol = "- ";
			}
			JLabel amount = new JLabel(symbol + fetch.get(i));
			amount.setBounds(550, y, 400,300);
			amount.setFont(new Font("SansSerif", Font.BOLD, 20));
			amount.setForeground(color);
			this.add(amount);
			y += 60;
		}
		fetch.clear();
	}

	public void tabs(){

	}
}
