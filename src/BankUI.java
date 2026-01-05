import javax.swing.*;
import java.awt.*;

public class BankUI extends JFrame {

    public BankUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(608, 632);
        setLocationRelativeTo(null);
        setResizable(false);

        BackgroundPanel backgroundPanel =
                new BackgroundPanel("backgroundImage4Quadrants.png");

        setContentPane(backgroundPanel);

        setVisible(true);
    }
}

