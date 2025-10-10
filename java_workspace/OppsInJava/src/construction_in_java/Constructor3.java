package construction_in_java;

class Car{
	public String name;
	public int model;
	
	
	public Car(String name, int model) {
		this.name = name;
		this.model = model;
	}
	public Car() {
	}
	
	
}
public class Constructor3 {
	public static void main(String[] args) {
		Car car = new Car();
		car.name = "Maruti Suzuki";
		car.model= 2025;
		System.out.println("No parameterised constructor");
		System.out.println("the car is "+car.name+" is model is: "+car.model);
		
		Car car1 = new Car("Thar",2024);
		System.out.println("paramerterised constructor");
		System.out.println("the car is "+car1.name+" is model is: "+car1.model);
	}
}
