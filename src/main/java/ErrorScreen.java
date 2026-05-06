import javax.swing.*;
import java.awt.*;

public class ErrorScreen extends JFrame{

    ErrorScreen(){
        this.setTitle("Error");
        this.setSize(600,350);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        JButton ok = new JButton ("OK");
        ok.setBounds(250, 200, 90, 30);
        ok.addActionListener(ev->{
            this.dispose();
        });
        this.add(ok);

        this.setVisible(true);
    }

    public void addMessage(String m, int x, int y, int font, int size){
        JLabel message = new JLabel(m);
        message.setBounds(x, y, 500, 50);
        message.setFont(new Font("SansSerif",font,  size));
        this.add(message);
    }
}
