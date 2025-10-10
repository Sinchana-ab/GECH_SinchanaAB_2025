package encapsulation;

class Account{
	private String accountNumber;
	private String ccountHolderName;
	private double balance;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCcountHolderName() {
		return ccountHolderName;
	}
	public void setCcountHolderName(String ccountHolderName) {
		this.ccountHolderName = ccountHolderName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Account(String accountNumber, String ccountHolderName, double balance) {
		this.accountNumber = accountNumber;
		this.ccountHolderName = ccountHolderName;
		this.balance = balance;
	}
	
	public void deposite(double amount) {
		if(amount > 0 ) {
			this.balance = balance+amount;
			System.out.println("deposite successfully and balance is: "+balance);
		}
		else {
			System.out.println("anount is not valid number");
		}
	}
	public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
        	balance -= amount;
        	System.out.println("current account balance: "+balance);
        }
        else {
        	System.out.println("insufficient bank balance");
        }
    }
}

public class BankAccount {
	public static void main(String[] args) {
		Account  a1  = new Account("Ac01", "sinchana", 2000);
		System.out.println(a1.getAccountNumber());
		System.out.println(a1.getCcountHolderName());
		a1.deposite(500);
		a1.withdraw(1000);
		System.out.println(a1.getBalance());
	}
}
