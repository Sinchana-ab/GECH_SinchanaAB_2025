package encapsulation;

class Student{
	private String name;
	private int age;
	private double marks;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
//	public Student(String name, int age, double marks) {
//		this.name = name;
//		this.age = age;
//		this.marks = marks;
//	}
}
public class EncapsulationInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    /*Encapsulation:- its is a mechanism that building the data into the single
     * */		
		
//		Student std = new Student(null, 0, 0);
		Student std = new Student();
		std.setName("sinchana A B");
		std.setAge(20);
		std.setMarks(200);
		System.out.println(std.getName());
		System.out.println(std.getAge());
		System.out.println(std.getMarks());
		
	}

}
