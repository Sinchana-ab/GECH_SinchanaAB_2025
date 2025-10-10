package inheritance_in_java;

class Vehical{
	public String brand;
	public String model;
	public Vehical(String brand, String model) {
		this.brand = brand;
		this.model = model;
	}
	public void start() {
		System.out.println(brand+" is starting");
	}
	
}

class Car extends Vehical{
	public int moWheels;

	public Car(String brand, String model, int moWheels) {
		super(brand, model);
		this.moWheels = moWheels;
	}
	@Override
	public void start() {
		System.out.println(brand+" "+model+" is starting");
	}
}
public class interface3 {
	public static void main(String[] args) {
		Car c1 = new Car("tesla","models",4);
		c1.start();
	}
}
