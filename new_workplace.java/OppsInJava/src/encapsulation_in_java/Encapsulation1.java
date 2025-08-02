package encapsulation_in_java;

class Student{
	private String name;
	private int roll;
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public int getRoll() {
		return roll;
	}
	public void setRoll(int roll) {
		this.roll = roll;
	}
	
	
}
public class Encapsulation1 {
	 public static void main(String[] args) {
		Student std = new Student();
		std.setName("sinchana");
		System.out.println(std.getName());
		std.setRoll(24);
		System.out.println(std.getRoll());
	}
}
