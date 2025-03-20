package inheritance;
class Student1{
	//states
	public int age;///instance within class which  are declared through constructor 
	public String name;
	public double marks;
	
	public Student1(int age, String name, double marks) {
		System.out.println("Full args Costractor");
		this.age = age;
		this.name = name;
		this.marks = marks;
	}
	public Student1() {
		System.out.println("no args constractor");
	}
	// action
	public void isplaying() {
		System.out.println(this.name+" is playing");
	}
	public void isSleeping() {
		System.out.println(this.name+" is sleeping");
	}
	public void display() {
		System.out.println("name:- "+this.name);
		System.out.println("age:- "+this.age);
		System.out.println("marks:- "+this.marks);
	}
	
}

public class ConstractionsInJava {
	public static void main(String[] args) {
		Student1 std1 = new Student1(20,"sinchana", 230.34);
		std1.display();
		std1.isplaying();
		std1.isSleeping();
		Student1 std2 = new Student1();
		std2.display();
	}
//the instance match with passing argument is called full arm constructor
	
}
