package abstraction;

interface Animal {
	public void eat();
}

class Lion implements Animal{
	public void eat() {
		System.out.println("lion is hunt then it will eat");
	}
}

abstract class Vehical{
	//abm
	public abstract void start();
	//abm
	public void stop() {
		System.out.println("The vehical is stopped");
	}
}

class Car extends Vehical{
	public void start() {
		System.out.println("The car is stopped");
	}
}

public class AbstractionInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lion l1 = new Lion();
		l1.eat();
		Car car1 = new Car();
		car1.start();
		car1.stop();
		
	}

}
