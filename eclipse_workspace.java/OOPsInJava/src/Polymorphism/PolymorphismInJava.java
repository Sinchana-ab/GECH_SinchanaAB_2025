package Polymorphism;
//method overridding
class Person{
	public  void walking() {
		System.out.println("The person is walking");
	}
}

class Mohan extends Person{
	public void walking() {
		System.out.println("Mohan is walking");
	}
}
class addition {
//	public void add(int a, int b) {
//		System.out.println("the addition of "+a+" and "+b+" is: " +(a+b));
//	}
	public void add(int a, int b,int c) {
		System.out.println("the addition of "+a+" , "+b+" and "+c+" is: " +(a+b+c));
	}
	public static void add(int a, int b) {
	System.out.println("the addition of "+a+" and "+b+" is: " +(a+b));
	}
}

public class PolymorphismInJava {
	/*
	 * --------ploymorphism:-------
	 * poly-many
	 * morphism--> form
	 * it a mechanism that emthos will perform a different action based on the situation
	 * 1. method-overloading(one class)
	 * 2. method-overrading9TWO CLASS)
	 * 
	 * */
	public static void main(String[] args) {
		// Method overridding/ dynamic polymorphism/ runtime ploymorphism
		Mohan m1 = new Mohan();
		m1.walking();
		//method overloading/ completime polymorphism/ static polymorphism 
		//[it will decide hic method need to call on complie time then it will run]
		addition add1 = new addition();
		add1.add(20, 30);
		add1.add(20, 20, 20);
		
	}

}
