package construction_in_java;

class Student{
	public String name;
	public int roll;
	
	//default constructor
	public Student(String name, int roll) {

		this.name = name;
		this.roll = roll;
	}
	
	
}
public class Constructor1 {
	public static void main(String[] args) {
		Student std1 = new Student("sinchana", 10);
		System.out.println("student name: "+std1.name+"\n Roll is: "+std1.roll);
	}
}
