package allOops;
abstract class User {
    private String name;
    private String phone;
    
    
    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public abstract void showProfile();
}

class Driver extends User {
    private String vehicle;

    public Driver(String name, String phone, String vehicle) {
        super(name, phone);
        this.vehicle = vehicle;
    }

    public void showProfile() {
        System.out.println("Driver with vehicle: " + vehicle);
    }
}

class Customer extends User {
    private String pickupLocation;

    public Customer(String name, String phone, String pickupLocation) {
        super(name, phone);
        this.pickupLocation = pickupLocation;
    }

    public void showProfile() {
        System.out.println("Customer at: " + pickupLocation);
    }
}

interface Ride {
    void book(String from, String to);
}

class UberRide implements Ride {
    public void book(String from, String to) {
        System.out.println("Ride booked from " + from + " to " + to);
    }
}
public class RideSharingApp {
	public static void main(String[] args) {
		Driver driver = new Driver("Ravi", "9876543210", "Toyota Innova");
        driver.showProfile(); 

        Customer customer = new Customer("Anita", "9123456780", "MG Road");
        customer.showProfile(); 

        // Book a ride
        Ride ride = new UberRide();
        ride.book("MG Road", "Electronic City");

	}
}
