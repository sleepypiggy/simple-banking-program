package ui;

import javax.swing.*;
import java.awt.*;

public class BankUI extends JFrame {

    public BankUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(612, 634);
        setLocationRelativeTo(null);
        setResizable(false);

        BackgroundPanel backgroundPanel =
                new BackgroundPanel("backgroundImageNoQuadrants.png");

        setContentPane(backgroundPanel);

        setVisible(true);
    }
}

