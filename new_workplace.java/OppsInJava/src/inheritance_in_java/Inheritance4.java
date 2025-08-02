package inheritance_in_java;

class Person{
	public String name;
	public int age;
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public void display() {
		System.out.println("the details name:  "+name+ " and age is: "+age);
	}
}
class Student extends Person{
	public int Student_id;

	public Student(String name, int age, int student_id) {
		super(name, age);
		Student_id = student_id;
	}
	@Override
	public void display() {
		System.out.println("the details with id"+ Student_id+" and  name:  "+name+ " and age is: "+age);
	}
	
}
public class Inheritance4 {
	public static void main(String[] args) {
		Student s1 = new Student("sinchana", 20, 1);
		s1.display();
	}
}
