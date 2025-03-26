package oops_assignment1_bank;

class Bank {
    private String accountHolder;
    private String accountNumber;
    private double balance;

    public Bank(String accountHolder, String accountNumber, double balance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = Math.max(balance, 500);
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("Now your current account balance is ₹" + this.balance);
    }

    public void withdraw(double amount) {
        if (balance - amount < 500) {
            System.out.println("Withdrawal is not possible. Your account balance is too low.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal amount: ₹" + amount + ". Your current balance is ₹" + balance);
        }
    }
}

public class BankAccount {
    public static void main(String[] args) {
        Bank b1 = new Bank("Sinchana", "IFC2012", 1000);
        System.out.println("Your details:");
        System.out.println("Name: " + b1.getAccountHolder());
        System.out.println("Account Number: " + b1.getAccountNumber());
        System.out.println("Balance: ₹" + b1.getBalance());

        b1.deposit(400);
        b1.withdraw(800);
    }
}
