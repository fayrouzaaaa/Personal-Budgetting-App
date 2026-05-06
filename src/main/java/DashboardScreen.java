import javax.swing.*;
import java.awt.*;

public class DashboardScreen extends JFrame{
	private Database db = new Database();
	DashboardScreen(){
		this.setTitle("Home");
		this.setSize(800,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);

		JLabel hello = new JLabel("Hello, username");
		hello.setBounds(80,-60,700,300);
		hello.setFont(new Font("SansSerif", Font.BOLD, 30));
		this.add(hello);

		showSummary();

		this.setVisible(true);
	}

	public void showSummary(){
		JPanel transactions = new JPanel();
		transactions.setLayout(null);
		transactions.setBounds(150, 150, 500,300);
		transactions.setBackground(new Color(0x93A7F3));
		this.add(transactions);

		JLabel totalBalance = new JLabel("Total Balance: NUMBER");
		totalBalance.setBounds(20, -100, 400, 300);
		totalBalance.setFont(new Font("SansSerif", Font.PLAIN, 20));
		totalBalance.setForeground(Color.white);
		transactions.add(totalBalance);

	}
	public void updateSummary(){

	}

	public void showRecentTransactions(){

	}
}
