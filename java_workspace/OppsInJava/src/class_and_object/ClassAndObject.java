package class_and_object;

class Student{
	public String name;
	public int age;
	
	

	public void iswriting() {
		System.out.println("student is writing");
	}
}

public class ClassAndObject {
	public static void main(String[] args) {
		Student std =  new Student();
		std.name = "Sinchana";
		std.age = 20;
		
		System.out.println("name: "+std.name);
		System.out.println("age: "+std.age);
		std.iswriting();
		
	}
}
