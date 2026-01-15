package bankaccount;

public class BankAccount {

    private String name;
    private double balance;

    public BankAccount (String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void deposit (double money){
        balance += money;
    }

    public void withdraw (double money) {
        balance -= money;
    }

    public double getBalance () {
        return balance;
    }

    public String getName () {
        return name;
    }

    @Override
    public String toString () {
        return name;
    }
}
