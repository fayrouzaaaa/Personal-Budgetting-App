import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * AddTransactionPage is a Swing-based graphical interface used to record
 * new financial transactions (Incomes or Expenses).
 * * <p>The page handles user input validation, dynamic styling for modern UI/UX,
 * and communicates with the {@link Regular_User} and {@link Transaction}
 * logic to persist data to the database.</p>

 */
public class AddTransactionPage extends JFrame {

    private Regular_User user;
    private Transaction transaction;

    private JTextField nameField;
    private JTextField amountField;
    private JComboBox<String> typeCombo;
    private JComboBox<String> catCombo;

    // Modern UI Color Palette
    private final Color PRIMARY_BLUE   = new Color(30, 58, 138);
    private final Color HOVER_BLUE     = new Color(37, 99, 235);
    private final Color BG_COLOR       = new Color(248, 250, 252);
    private final Color CARD_WHITE     = Color.WHITE;
    private final Color TEXT_DARK      = new Color(15, 23, 42);
    private final Color TEXT_MUTED     = new Color(100, 116, 139);

    /**
     * Constructs the AddTransactionPage and initializes all UI components.
     * * @param user The current authenticated user session required for
     * linking the transaction and checking balances.
     */
    public AddTransactionPage(Regular_User user) {
        this.user = user;
        this.transaction = new Transaction();

        setTitle("New Transaction");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon image = new ImageIcon("src/main/icon.png");
        this.setIconImage(image.getImage());

        // Main background panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(40, 45, 40, 45));
        setContentPane(mainPanel);

        // Central white card container
        JPanel card = new JPanel();
        card.setBackground(CARD_WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(35, 60, 35, 60)
        ));

        // Header Section
        JLabel title = new JLabel("Add Transaction");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(PRIMARY_BLUE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        JLabel subtitle = new JLabel("Enter your transaction details below.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitle);
        card.add(Box.createRigidArea(new Dimension(0, 40)));

        // Input Fields Initialization
        nameField = addModernInput(card, "Transaction Name", "Enter name...");
        amountField = addModernInput(card, "Amount ($)", "0.00");

        // Transaction Type Dropdown
        addLabel(card, "Type");
        typeCombo = new JComboBox<>(transaction.getTransactionsType());
        styleDropdown(typeCombo);
        card.add(typeCombo);
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // Category Selection
        addLabel(card, "Category");
        String[] categories = {"Food", "Transportation", "Shopping", "Entertainment", "Bills", "Health", "Education", "Savings"};
        catCombo = new JComboBox<>(categories);
        styleDropdown(catCombo);
        card.add(catCombo);

        card.add(Box.createVerticalGlue());
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // Footer Buttons (Cancel / Save)
        JPanel footer = new JPanel(new GridLayout(1, 2, 20, 0));
        footer.setOpaque(false);
        footer.setMaximumSize(new Dimension(500, 50));

        JButton backBtn = new JButton("Back");
        styleSecondaryBtn(backBtn);
        backBtn.addActionListener(e -> {
            dispose();
        });

        JButton saveBtn = new JButton("Save Transaction");
        stylePrimaryBtn(saveBtn);
        saveBtn.addActionListener(e -> saveData());

        footer.add(backBtn);
        footer.add(saveBtn);
        card.add(footer);

        mainPanel.add(card, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Gathers data from the form, validates input formats, and attempts to save
     * the transaction through the {@link Regular_User#addTransaction} method.
     * <p>Handles {@link NumberFormatException} for invalid amounts and generic
     * exceptions for database or logic errors.</p>
     */
    private void saveData() {
        try {
            String name = nameField.getText().trim();
            String amountStr = amountField.getText().trim();
            String typeStr = (String) typeCombo.getSelectedItem();
            String categoryStr = (String) catCombo.getSelectedItem();

            if (name.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double amount = Double.parseDouble(amountStr);

            Regular_User.TransactionType type = typeStr.equalsIgnoreCase("Income")
                    ? Regular_User.TransactionType.INCOME
                    : Regular_User.TransactionType.EXPENSE;

            Category selectedCategory = new Category(categoryStr);

            // Execute logic: Update balance and save to DB
            user.addTransaction(type, name, amount, selectedCategory);

            JOptionPane.showMessageDialog(this, "Transaction Saved Successfully! ✅");

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Helper method to create a labeled text field with focus effects and custom borders.
     * * @param parent The panel to which the components will be added.
     * @param label  The text to display above the input field.
     * @param hint   The placeholder text (hint) for the field.
     * @return The initialized {@link JTextField} object.
     */
    private JTextField addModernInput(JPanel parent, String label, String hint) {
        addLabel(parent, label);
        JTextField f = new JTextField();
        f.setHorizontalAlignment(JTextField.CENTER);
        f.setMaximumSize(new Dimension(500, 45));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));

        f.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_BLUE, 2),
                        new EmptyBorder(8, 12, 8, 12)));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                        new EmptyBorder(8, 12, 8, 12)));
            }
        });

        f.setAlignmentX(Component.CENTER_ALIGNMENT);
        parent.add(f);
        parent.add(Box.createRigidArea(new Dimension(0, 25)));
        return f;
    }

    /**
     * Standardizes the look of section labels.
     * * @param parent The panel to add the label to.
     * @param text   The content of the label.
     */
    private void addLabel(JPanel parent, String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        l.setForeground(TEXT_DARK);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        l.setBorder(new EmptyBorder(0, 0, 8, 0));
        parent.add(l);
    }

    /**
     * Applies styling to JComboBox components for a modern look.
     * * @param cb The JComboBox to style.
     */
    private void styleDropdown(JComboBox<String> cb) {
        cb.setMaximumSize(new Dimension(500, 45));
        cb.setBackground(Color.WHITE);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        if (cb.getRenderer() instanceof JLabel) {
            ((JLabel)cb.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        }
        cb.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Styles the primary action button (Save) with hover effects.
     * * @param b The button to style.
     */
    private void stylePrimaryBtn(JButton b) {
        b.setBackground(PRIMARY_BLUE);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI Bold", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(HOVER_BLUE); }
            public void mouseExited(MouseEvent e) { b.setBackground(PRIMARY_BLUE); }
        });
    }

    /**
     * Styles the secondary action button (Cancel) with an outlined look.
     * * @param b The button to style.
     */
    private void styleSecondaryBtn(JButton b) {
        b.setBackground(Color.WHITE);
        b.setForeground(PRIMARY_BLUE);
        b.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        b.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 2));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(239, 246, 255)); }
            public void mouseExited(MouseEvent e) { b.setBackground(Color.WHITE); }
        });
    }
}