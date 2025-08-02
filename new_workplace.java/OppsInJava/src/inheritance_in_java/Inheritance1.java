package inheritance_in_java;

class Parent{
	public String name = "sinchana";
	public int age = 20;
	public void parent() {
		System.out.println("this is parent class");
	}
}

public class Inheritance1 extends Parent{
	public static void main(String[] args) {
		
		Inheritance1 one = new Inheritance1();
		one.name = "nayana";
		one.age = 23;
		System.out.println(one.age);
		System.out.println(one.name);
		one.parent();
	}
}
