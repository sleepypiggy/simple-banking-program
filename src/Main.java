import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new BankUI();
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
        BankAccount currentAccount = null;

            while (!choice.equalsIgnoreCase("E")) {
                System.out.println("""
                        -----------------------------
                        | 1. Create new account (C) |
                        | 2. View all accounts  (V) |
                        | 3. Select account     (S) |
                        | 4. Deposit            (D) |
                        | 5. Withdraw           (W) |
                        | 6. Exit               (E) |
                        -----------------------------""");
                System.out.println("What would you like to do?: ");
                choice = scanner.nextLine();

                switch (choice.toUpperCase()) {
                    case "C" -> currentAccount = createAccount(scanner, accountList);
                    //case "T" -> System.out.println(currentAccount);
                    case "V" -> showAccounts(accountList);
                    case "S" -> currentAccount = selectAccount(scanner, accountList, currentAccount);
                    case "D" -> handleDeposit(scanner, currentAccount);
                    case "W" -> handleWithdrawal(scanner, currentAccount);
                    case "E" -> System.out.println("You have exited the banking program. ");
                    default -> System.out.println("That is not a valid option. Try again. ");
                }
            }
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