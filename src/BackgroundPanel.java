import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

class BackgroundPanel extends JPanel {
        private Image background;
        private BankAccount currentAccount = null;

        public BackgroundPanel(String imagePath) {
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/" + imagePath));
            ImageIcon buttonImage = new ImageIcon(getClass().getResource("/buttonNormal.png"));
            Image buttonScaledImage = buttonImage.getImage();
            Image newButtonScaledImage = buttonScaledImage.getScaledInstance(120, 70, java.awt.Image.SCALE_SMOOTH);
            buttonImage = new ImageIcon(newButtonScaledImage);

            ImageIcon buttonClickedImage = new ImageIcon(getClass().getResource("/buttonClicked.png"));
            Image buttonClickedScaledImage = buttonClickedImage.getImage();
            Image newButtonClickedScaledImage = buttonClickedScaledImage.getScaledInstance(120, 70, java.awt.Image.SCALE_SMOOTH);
            buttonClickedImage = new ImageIcon(newButtonClickedScaledImage);

            background = backgroundImage.getImage();

            setLayout(new GridBagLayout());

            JButton createButton = makeButton("Create account", buttonImage);
            JButton viewButton = makeButton("View accounts", buttonImage);
            JButton selectButton = makeButton("Select account", buttonImage);
            JButton depositButton = makeButton("Deposit", buttonImage);
            JButton withdrawButton = makeButton("Withdraw", buttonImage);
            JButton exitButton = makeButton("Exit", buttonImage);


            invisibleButton(createButton);
            invisibleButton(viewButton);
            invisibleButton(selectButton);
            invisibleButton(depositButton);
            invisibleButton(withdrawButton);
            invisibleButton(exitButton);

            textOnButton(createButton);
            textOnButton(viewButton);
            textOnButton(selectButton);
            textOnButton(depositButton);
            textOnButton(withdrawButton);
            textOnButton(exitButton);

            createButton.setPressedIcon(buttonClickedImage);
            viewButton.setPressedIcon(buttonClickedImage);
            selectButton.setPressedIcon(buttonClickedImage);
            depositButton.setPressedIcon(buttonClickedImage);
            withdrawButton.setPressedIcon(buttonClickedImage);
            exitButton.setPressedIcon(buttonClickedImage);


            JPanel buttonPanel = new JPanel(new GridLayout(3, 2));
            buttonPanel.setPreferredSize(new Dimension(300, 300));
            buttonPanel.setOpaque(false);

            buttonPanel.add(createButton);
            buttonPanel.add(viewButton);
            buttonPanel.add(selectButton);
            buttonPanel.add(depositButton);
            buttonPanel.add(withdrawButton);
            buttonPanel.add(exitButton);

            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            wrapper.add(buttonPanel, gbc);

            add(wrapper, gbc);

            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("1");
                }
            });

            viewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("2");
                }
            });

            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("3");
                }
            });

            depositButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("4");
                }
            });

            withdrawButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("5");
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    System.out.println("6");
                }
            });
        }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background == null) return;

        int panelW = getWidth();
        int panelH = getHeight();

        int imgW = background.getWidth(this);
        int imgH = background.getHeight(this);

        if (imgW <= 0 || imgH <= 0) return;

        double panelRatio = (double) panelW / panelH;
        double imageRatio = (double) imgW / imgH;

        int drawW, drawH;

        if (imageRatio > panelRatio) {
            // Image is wider → fit height, crop sides
            drawH = panelH;
            drawW = (int) (panelH * imageRatio);
        } else {
            // Image is taller → fit width, crop top/bottom
            drawW = panelW;
            drawH = (int) (panelW / imageRatio);
        }

        int x = (panelW - drawW) / 2;
        int y = (panelH - drawH) / 2;

        g.drawImage(background, x, y, drawW, drawH, this);
    }


        private JButton makeButton(String label, ImageIcon buttonImage) {
            return new JButton(label, buttonImage);
        }

        private JButton invisibleButton(JButton button) {
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            return button;
        }

        private JButton textOnButton(JButton button) {
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setVerticalTextPosition(JButton.CENTER);
            return button;
        }

    private static BankAccount createAccount (Scanner scanner, ArrayList<BankAccount> accountList) {
        System.out.println("Enter account name: ");
        String accountName = scanner.nextLine();
        System.out.println("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine();
        BankAccount newAccount = new BankAccount(accountName, initialBalance);
        accountList.add(newAccount);
        System.out.println("Account created and selected!");
        return newAccount;
    }

    private static void showAccounts (ArrayList<BankAccount> accountList) {
        if (accountList.isEmpty()) {
            System.out.println("No accounts. To create an account, enter C in selection menu. ");
        } else {
            for (BankAccount account : accountList) {
                System.out.printf("| %s | ", account.getName());
            }
            System.out.println();
        }
    }

    private static BankAccount selectAccount (Scanner scanner, ArrayList<BankAccount> accountList, BankAccount currentAccount) {
        if (accountList.isEmpty()) {
            System.out.println("No accounts. To create an account, enter C in selection menu. ");
        } else {
            System.out.println("Which account would you like to select?: ");
            String name = scanner.nextLine();
            for (BankAccount account : accountList) {
                if (account.getName().equalsIgnoreCase(name)) {
                    System.out.printf("%s selected. \n", account);
                    return account;
                }
            }
        }
        System.out.println("Account not found. ");
        return currentAccount;
    }

    private static void handleDeposit (Scanner scanner, BankAccount account) {
        if (account == null) {
            System.out.println("No accounts. To create an account, enter C in selection menu. ");
        } else {
            System.out.print("How much would you like to deposit?: $");
            double amount;
            amount = scanner.nextDouble();
            scanner.nextLine();
            account.deposit(amount);
            System.out.printf("Account: [%s] | Your new balance is $%.2f. \n", account, account.getBalance());
        }
    }

    private static void handleWithdrawal (Scanner scanner, BankAccount account) {
        if (account == null) {
            System.out.println("No accounts. To create an account, enter C in selection menu. ");
        } else {
            System.out.print("How much would you like to withdraw?: $");
            double amount;
            amount = scanner.nextDouble();
            scanner.nextLine();

            if (negativeBalance(account, amount)) {
                System.out.println("Not enough money in account to withdraw. ");
                System.out.printf("Your new balance is $%.2f. \n", account.getBalance());
            } else {
                account.withdraw(amount);
                System.out.printf("Account: [%s] | Your new balance is $%.2f. ", account, account.getBalance());
            }
        }
    }

    private static boolean negativeBalance (BankAccount account, double withdrawAmount) {
        return (account.getBalance() - withdrawAmount) < 0;
    }

    private static String handleChoice (Scanner scanner, String choice) {
        System.out.println("Not a valid option, try again. ");
        System.out.print("Do you want to deposit (D), withdraw (W), or exit (E)?: ");
        choice = scanner.nextLine();
        return choice;
    }
    }

