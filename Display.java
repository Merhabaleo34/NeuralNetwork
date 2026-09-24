import javax.swing.*;
import java.awt.*;

public class Display {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Swing Sample");
        frame.setSize(320, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello, Swing!", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        frame.add(label);

        frame.setVisible(true);
    }
}
