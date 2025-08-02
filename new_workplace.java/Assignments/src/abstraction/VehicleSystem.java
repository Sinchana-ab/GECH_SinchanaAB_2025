package abstraction;

abstract class Vehicle {
    public abstract void start();
}

class Car extends Vehicle {
	@Override
    public void start() {
		System.out.println("the car is started");
    }
}

class Bike extends Vehicle {
	@Override
    public void start() {
		System.out.println("the bike is started");
    }
}

public class VehicleSystem {
	public static void main(String[] args) {
		Car c = new Car();
		c.start();
		Bike b = new Bike();
		b.start();
	}
}
