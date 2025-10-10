package interfaces;

interface Payment {
    void pay(double amount);
}

class CardPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using Card.");
    }
}

class UPIPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " using UPI.");
    }
}
public class PaymentSystem {
	public static void main(String[] args) {
		CardPayment c= new CardPayment();
		c.pay(2000);
		UPIPayment u = new UPIPayment();
		u.pay(1000.0);
	}
}
