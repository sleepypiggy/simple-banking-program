package ui;

import javax.swing.*;

public class BankUI extends JFrame {

    public BankUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(612, 634);
        setLocationRelativeTo(null);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("testBackground.png");

        setContentPane(backgroundPanel);

        setVisible(true);
    }
}

