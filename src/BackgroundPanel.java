import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class BackgroundPanel extends JPanel {
        private Image background;
        private BankAccount currentAccount = null;
        private final ArrayList<BankAccount> accountList = new ArrayList<>();

        private InputMode currentMode = InputMode.NONE;
        private JTextField inputField;
        private JLabel promptLabel;

        // current states of input
        enum InputMode {
            CREATE_ACCOUNT,
            SELECT_ACCOUNT,
            DEPOSIT,
            WITHDRAW,
            NONE
        }


        public BackgroundPanel(String imagePath) {
            // normal button image
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/" + imagePath));
            ImageIcon buttonImage = new ImageIcon(getClass().getResource("/buttonNormal.png"));
            Image buttonScaledImage = buttonImage.getImage();
            Image newButtonScaledImage = buttonScaledImage.getScaledInstance(120, 70, java.awt.Image.SCALE_SMOOTH);
            buttonImage = new ImageIcon(newButtonScaledImage);

            // clicked button image
            ImageIcon buttonClickedImage = new ImageIcon(getClass().getResource("/buttonClicked.png"));
            Image buttonClickedScaledImage = buttonClickedImage.getImage();
            Image newButtonClickedScaledImage = buttonClickedScaledImage.getScaledInstance(120, 70, java.awt.Image.SCALE_SMOOTH);
            buttonClickedImage = new ImageIcon(newButtonClickedScaledImage);

            // background image
            background = backgroundImage.getImage();

            setLayout(new BorderLayout());

            // make all buttons
            JButton createButton = makeButton("Create account", buttonImage);
            JButton viewButton = makeButton("View accounts", buttonImage);
            JButton selectButton = makeButton("Select account", buttonImage);
            JButton depositButton = makeButton("Deposit", buttonImage);
            JButton withdrawButton = makeButton("Withdraw", buttonImage);
            JButton exitButton = makeButton("Exit", buttonImage);



            // make original button icons invisible (replaced with custom image instead)
            invisibleButton(createButton);
            invisibleButton(viewButton);
            invisibleButton(selectButton);
            invisibleButton(depositButton);
            invisibleButton(withdrawButton);
            invisibleButton(exitButton);

            // overlap the text onto respective button
            textOnButton(createButton);
            textOnButton(viewButton);
            textOnButton(selectButton);
            textOnButton(depositButton);
            textOnButton(withdrawButton);
            textOnButton(exitButton);

            // set the image of button to buttonClickedImage when it is clicked
            createButton.setPressedIcon(buttonClickedImage);
            viewButton.setPressedIcon(buttonClickedImage);
            selectButton.setPressedIcon(buttonClickedImage);
            depositButton.setPressedIcon(buttonClickedImage);
            withdrawButton.setPressedIcon(buttonClickedImage);
            exitButton.setPressedIcon(buttonClickedImage);

            JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            inputPanel.setOpaque(false);

            // ===== INPUT AREA (FIXED) =====
            promptLabel = new JLabel("Select an action", SwingConstants.CENTER);
            inputField = new JTextField(15);
            inputField.setOpaque(false);
            inputField.setBackground(new java.awt.Color(237, 175, 115, 0));
            inputField.setBorder(BorderFactory.createEmptyBorder());

            JPanel inputPanel1 = new JPanel(new GridLayout(2, 1, 0, 6));
            inputPanel1.setOpaque(false);
            inputPanel1.add(promptLabel);
            inputPanel1.add(inputField);

            JPanel inputContainer = new JPanel(new GridBagLayout());
            inputContainer.setOpaque(false);

            GridBagConstraints igbc = new GridBagConstraints();
            igbc.gridx = 0;
            igbc.gridy = 0;
            igbc.anchor = GridBagConstraints.CENTER;
            igbc.weightx = 1.0;
            igbc.weighty = 1.0;

            JPanel rightHalf = new JPanel(new GridBagLayout());
            rightHalf.setOpaque(false);
            rightHalf.setPreferredSize(new Dimension(300, 0)); // HALF of 600px window

            rightHalf.add(inputContainer, igbc);
            inputContainer.add(inputPanel1, igbc);

            add(rightHalf, BorderLayout.EAST);


            // layout to manage the buttons
            JPanel buttonPanel = new JPanel(new GridLayout(3, 2));
            buttonPanel.setPreferredSize(new Dimension(300, 300));
            buttonPanel.setOpaque(false);

            // add the buttons to the layout manager
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

            add(wrapper, BorderLayout.SOUTH);




            /* BUTTON ACTIONS */
            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentMode = InputMode.CREATE_ACCOUNT;
                    promptLabel.setText("Enter new account name:");
                    inputField.setText("");
                    inputField.requestFocus();
                }
            });

            viewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showAccounts();
                }
            });

            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentMode = InputMode.SELECT_ACCOUNT;
                    promptLabel.setText("Enter account name:");
                    inputField.setText("");
                    inputField.requestFocus();
                }
            });

            depositButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentMode = InputMode.DEPOSIT;
                    promptLabel.setText("Enter deposit amount:");
                    inputField.setText("");
                    inputField.requestFocus();
                }
            });

            withdrawButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentMode = InputMode.WITHDRAW;
                    promptLabel.setText("Enter withdrawal amount:");
                    inputField.setText("");
                    inputField.requestFocus();
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    System.exit(0);
                }
            });

            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleInput();
                }
            });

        }

    private void handleInput() {
        String text = inputField.getText().trim();
        try {
            switch (currentMode) {
                case CREATE_ACCOUNT -> createAccount(text);
                case SELECT_ACCOUNT -> selectAccount(text);
                case DEPOSIT -> handleDeposit(Double.parseDouble(text));
                case WITHDRAW -> handleWithdrawal(Double.parseDouble(text));
                default -> showError("Select an action first.");
            }
        } catch (NumberFormatException ex) {
            showError("Please enter a valid number.");
        }
        inputField.setText("");
        currentMode = InputMode.NONE;
        promptLabel.setText("Select an action");
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


    private void createAccount(String name) {
        if (name.isEmpty()) {
            showError("Account name cannot be empty.");
            return;
        }
        BankAccount acc = new BankAccount(name, 0);
        accountList.add(acc);
        currentAccount = acc;
        showInfo("Account created and selected.");
    }

    private void showAccounts() {
        if (accountList.isEmpty()) {
            showInfo("No accounts available.");
            return;
        }
        StringBuilder sb = new StringBuilder("Accounts:\n");
        for (BankAccount acc : accountList) {
            sb.append("- ").append(acc.getName()).append("\n");
        }
        showInfo(sb.toString());
    }

    private void selectAccount(String name) {
        for (BankAccount acc : accountList) {
            if (acc.getName().equalsIgnoreCase(name)) {
                currentAccount = acc;
                showInfo("Account selected.");
                return;
            }
        }
        showError("Account not found.");
    }

    private void handleDeposit(double amount) {
        if (currentAccount == null) {
            showError("No account selected.");
            return;
        }
        if (amount <= 0) {
            showError("Amount must be positive.");
            return;
        }
        currentAccount.deposit(amount);
        showInfo("New balance: $" + currentAccount.getBalance());
    }

    private void handleWithdrawal(double amount) {
        if (currentAccount == null) {
            showError("No account selected.");
            return;
        }
        if (amount <= 0) {
            showError("Amount must be positive.");
            return;
        }
        if (currentAccount.getBalance() < amount) {
            showError("Insufficient funds.");
            return;
        }
        currentAccount.withdraw(amount);
        showInfo("New balance: $" + currentAccount.getBalance());
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    }

