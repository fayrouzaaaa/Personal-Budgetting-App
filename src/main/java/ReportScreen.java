import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.util.*;

public class ReportScreen extends JFrame {
    private Regular_User user;
    private int userID;
    private int current = Calendar.getInstance().get(Calendar.MONTH);
    private String currentName;
    private Report report;
    private Footer footer;

    ReportScreen(Regular_User user) {
        this.setTitle("Report");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        footer = new Footer(this, user );
        this.add(footer);

        userID = user.getUserId();
        report = new Report();
        String[] months = new DateFormatSymbols().getMonths();
        currentName = months[current];
        displayReport();
    }

    public void displayReport(){
        ArrayList<String> reportValues = report.generateReport(currentName, userID);

        JLabel monthlyReport = new JLabel (currentName + " Report");
        monthlyReport.setBounds(100, -30, 300,300);
        monthlyReport.setFont(new Font("SansSerif", Font.BOLD, 40));
        this.add(monthlyReport);

        JLabel balance = new JLabel("Balance:");
        balance.setBounds(100, 50, 300, 300);
        balance.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(balance);

        JLabel income = new JLabel("Total Income:");
        income.setBounds(100, 150, 300, 300);
        income.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(income);

        JLabel expense = new JLabel ("Total Expense:");
        expense.setBounds(100, 260, 300,300);
        expense.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(expense);

        JLabel savings = new JLabel("Savings:");
        savings.setBounds(100, 380, 300,300);
        savings.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.add(savings);

        int yOfLabels = 80;

        for (int i=0; i<reportValues.size(); i++){
            JLabel reportValue = new JLabel (reportValues.get(i));
            reportValue.setBounds(100, yOfLabels, 300, 300);
            reportValue.setFont(new Font("SansSerif", Font.BOLD, 40));
            reportValue.setForeground(new Color(0x93A7F3));

            this.add(reportValue);
            yOfLabels+=115;
        }
    }
}