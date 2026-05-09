import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Provides a modern graphical user interface for viewing and managing user transactions.
 * * <p>The TransactionPage class serves as a comprehensive financial dashboard, displaying
 * real-time statistics (Balance, Income, Expenses) and a detailed, scrollable history
 * of the user's financial activities. It utilizes custom-painted components for a
 * polished "app-like" aesthetic and integrates with {@link Transaction} and
 * {@link Report} for data persistence and calculation.</p>

 */
public class TransactionPage extends JFrame {

    private Regular_User user;
    private Transaction transactionManager;
    private Report reportManager;
    private Footer footer;

    // UI Color Palette
    private final Color APP_WHITE      = new Color(255, 255, 255);
    private final Color SOFT_BG        = new Color(247, 249, 252);
    private final Color CARD_BORDER    = new Color(234, 238, 243);
    private final Color MAIN_PURPLE    = new Color(108, 92, 231);
    private final Color MINT_GREEN     = new Color(38, 194, 129);
    private final Color SOFT_RED       = new Color(255, 121, 121);
    private final Color TEXT_BLACK     = new Color(45, 52, 54);

    /**
     * Constructs the TransactionPage for a specific user.
     * * <p>Initializes the layout, sets up the main container with a custom scroll pane,
     * and triggers the creation of the header, statistics cards, and the
     * transaction list.</p>
     * * @param user The authenticated {@link Regular_User} whose data is being displayed.
     */
    public TransactionPage(Regular_User user) {
        this.user = user;
        this.transactionManager = new Transaction();
        this.reportManager = new Report();

        setTitle("Transactions");
        setSize(720, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(SOFT_BG);
        setContentPane(container);

        createLightHeader(container);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(10, 25, 30, 25));

        createStatsSection(body);

        // Section Title
        JLabel listLabel = new JLabel("My Transactions");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        listLabel.setForeground(TEXT_BLACK);

        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleWrapper.setOpaque(false);
        titleWrapper.setMaximumSize(new Dimension(480, 50));
        titleWrapper.setBorder(new EmptyBorder(30, 0, 20, 0));
        titleWrapper.add(listLabel);
        body.add(titleWrapper);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        loadtransaction(listPanel);

        body.add(listPanel);

        // Custom scroll implementation for mobile-like feel
        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        container.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates the top header section including a personalized welcome message
     * and the "Add Transaction" button.
     * * @param container The main parent panel where the header is added.
     */
    private void createLightHeader(JPanel container) {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(25, 25, 10, 25));

        JLabel welcome = new JLabel("<html>Hey <b style='color:#6c5ce7;'>" + user.getName() + "</b>,<br>Let's see your journey! ✨</html>");
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcome.setForeground(TEXT_BLACK);

        JButton addBtn = new JButton("Add Transaction");
        styleAddBtn(addBtn);
        addBtn.addActionListener(e -> {
            new AddTransactionPage(user);
        });

        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnWrapper.setOpaque(false);
        btnWrapper.add(addBtn);

        header.add(welcome, BorderLayout.WEST);
        header.add(btnWrapper, BorderLayout.EAST);
        container.add(header, BorderLayout.NORTH);
    }

    /**
     * Applies custom graphics rendering to a button to create a rounded gradient effect.
     * * @param btn The JButton to be styled.
     */
    private void styleAddBtn(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(new EmptyBorder(8, 25, 8, 25));

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, MAIN_PURPLE, c.getWidth(), 0, new Color(140, 122, 230));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);
                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    /**
     * Calculates and displays the user's monthly financial summary in card format.
     * * <p>Fetches data for the current month's balance, total income, and total expenses
     * using the {@link Report} manager and renders them into the UI.</p>
     * * @param body The panel where the statistic cards will be added.
     */
    private void createStatsSection(JPanel body) {
        int uID = user.getUserId();
        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        double balance = 0, income = 0, expense = 0;
        try {
            Object result = reportManager.getBalance(uID);

            String balanceStr = "0.0";

            if (result instanceof java.util.List && !((java.util.List<?>) result).isEmpty()) {
                balanceStr = ((java.util.List<?>) result).get(0).toString();
            } else if (result != null) {
                balanceStr = result.toString();
            }
            balance = Double.parseDouble(balanceStr);
            income = Double.parseDouble(reportManager.getTotalIncome(uID, start, end));
            expense = Double.parseDouble(reportManager.getTotalExpenses(uID, start, end));
        } catch (Exception e) {
            System.err.println("Error loading stats: " + e.getMessage());
        }

        body.add(createCard("Total Balance", "$" + String.format("%.2f", balance), MAIN_PURPLE, true));
        body.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel row = new JPanel(new GridLayout(1, 2, 20, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(480, 100));

        row.add(createCard("Income", "+$" + String.format("%.2f", income), MINT_GREEN, false));
        row.add(createCard("Expense", "-$" + String.format("%.2f", expense), SOFT_RED, false));

        body.add(row);
    }

    /**
     * Creates a stylized UI card to display a specific financial metric.
     * * @param label  The title of the card (e.g., "Income").
     * @param value  The monetary value to display.
     * @param accent The color associated with the card's theme.
     * @param isBig  Whether the card should be rendered at full width or half width.
     * @return A {@link JPanel} configured as a metric card.
     */
    private JPanel createCard(String label, String value, Color accent, boolean isBig) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(APP_WHITE);
        card.setBorder(BorderFactory.createLineBorder(CARD_BORDER, 1));
        if(isBig) card.setMaximumSize(new Dimension(430, 130));
        else card.setPreferredSize(new Dimension(205, 100));

        JPanel inner = new JPanel(new GridLayout(2, 1));
        inner.setOpaque(false);
        inner.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel lLabel = new JLabel(label);
        lLabel.setForeground(Color.GRAY);
        lLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel lValue = new JLabel(value);
        lValue.setForeground(accent);
        lValue.setFont(new Font("Segoe UI Bold", Font.BOLD, isBig ? 32 : 20));

        inner.add(lLabel);
        inner.add(lValue);
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    /**
     * Queries the database for the user's transaction history and renders the items
     * into the transaction list.
     * * @param list The panel that will contain the transaction items.
     */
    private void loadtransaction(JPanel list) {
        int uID = user.getUserId();

        // Custom SQL to fetch latest transactions
        String sql = "SELECT TOP 100 * FROM Transactions WHERE User_ID = ? ORDER BY Date DESC";
        String[] p = {Integer.toString(uID)};

        ArrayList<String> names = transactionManager.db.selectQuery(sql, p, "Name");
        ArrayList<String> amounts = transactionManager.db.selectQuery(sql, p, "Amount");
        ArrayList<String> types = transactionManager.db.selectQuery(sql, p, "Type");
        ArrayList<String> dates = transactionManager.db.selectQuery(sql, p, "Date");

        if (names.isEmpty()) {
            JLabel empty = new JLabel("No transactions found yet! 🏦");
            empty.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            empty.setForeground(Color.GRAY);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            list.add(empty);
        } else {
            for (int i = 0; i < names.size(); i++) {
                list.add(createTransactionItem(names.get(i), types.get(i), amounts.get(i), dates.get(i)));
                list.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        list.revalidate();
        list.repaint();
    }

    /**
     * Creates a horizontal list item representing a single transaction.
     * * @param title The name of the transaction.
     * @param type  The transaction type (Income/Expense).
     * @param amt   The transaction amount.
     * @param time  The date of the transaction.
     * @return A {@link JPanel} configured as a transaction list item.
     */
    private JPanel createTransactionItem(String title, String type, String amt, String time) {
        boolean isInc = type.equalsIgnoreCase("Income");
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(APP_WHITE);
        item.setMaximumSize(new Dimension(430, 85));
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        JLabel name = new JLabel(title);
        name.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        JLabel date = new JLabel(time);
        date.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        date.setForeground(Color.LIGHT_GRAY);
        left.add(name);
        left.add(date);

        JLabel price = new JLabel((isInc ? "+" : "-") + "$" + amt);
        price.setForeground(isInc ? MINT_GREEN : SOFT_RED);
        price.setFont(new Font("Segoe UI Bold", Font.BOLD, 18));

        item.add(left, BorderLayout.CENTER);
        item.add(price, BorderLayout.EAST);

        return item;
    }
}