import javax.swing.*;
import java.awt.*;

/**
 * ErrorScreen serves as a reusable custom dialog window for displaying
 * error messages, validation alerts, or system notifications to the user.
 * * <p>It provides a standardized look and feel for errors across the application,
 * featuring an "OK" button to dismiss the window and a dynamic labeling
 * system to support varied messaging needs.</p>

 */
public class ErrorScreen extends JFrame{

    /**
     * Constructs the ErrorScreen window.
     * * <p>Initializes the frame with a specific size, centers it on the screen,
     * and adds a dismissal button ("OK") that closes only the current
     * error window using {@link JFrame#DISPOSE_ON_CLOSE}.</p>
     */
    ErrorScreen(){
        this.setTitle("Error");
        this.setSize(600,350);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Standard OK button to close the dialog
        JButton ok = new JButton ("OK");
        ok.setBounds(250, 200, 90, 30);
        ok.addActionListener(ev->{
            this.dispose();
        });
        this.add(ok);

        this.setVisible(true);
    }

    /**
     * Dynamically adds a text message to the error screen with custom positioning and styling.
     * * @param m    The message string to be displayed.
     * @param x    The horizontal coordinate for the label's position.
     * @param y    The vertical coordinate for the label's position.
     * @param font The font style constant (e.g., {@link Font#PLAIN}, {@link Font#BOLD}).
     * @param size The point size of the font.
     */
    public void addMessage(String m, int x, int y, int font, int size){
        JLabel message = new JLabel(m);
        message.setBounds(x, y, 500, 50);
        message.setFont(new Font("Segoe UI", font, size));
        this.add(message);
    }
}