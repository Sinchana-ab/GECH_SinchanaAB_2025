package java_interview;

class Student{
	public String name;
	public int age;
	
	//default or no argument constructor
	public Student() {
	}

	//parametersied constructor
	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
}

public class ConstructorOverloading {
	public static void main(String[] args) {
		Student s = new Student();
		System.out.println("Default value of constructor");
		System.out.println("name is: "+s.name);
		System.out.println("Age is: "+s.age);
		s.name = "sinchana";
		s.age = 20;
		System.out.println("After assigning the value:");
		System.out.println("name is: "+s.name);
		System.out.println("Age is: "+s.age);
		Student s2 = new Student("nayana", 21);
		System.out.println("parameterized constructions");
		System.out.println("name is: "+s2.name);
		System.out.println("Age is: "+s2.age);
	}
}
