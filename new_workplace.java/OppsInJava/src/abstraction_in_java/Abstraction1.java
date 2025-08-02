package abstraction_in_java;

abstract class Person{
	public abstract void iswalking();
	public void isSleeping() {
		System.out.println("the person is sleeping");
	}
}

class Student extends Person{
	@Override
	public void iswalking() {
		System.out.println("the student is walking");
	}
}

//1. Interface
interface Vehicle{
	void start();//abstract method by defalut public and abstract
}

class Car implements Vehicle{

	@Override
	public void start() {
		System.out.println("the car is moving");
	}
	
}


public class Abstraction1 {
	public static void main(String[] args) {
		Student std = new Student();
		std.isSleeping();
		std.iswalking();
		
		Car c = new Car();
		c.start();
	}
}
