package class_and_object;

class Employee{
	public String name;
	public int id;
	
	public void isworking() {
		System.out.println("employee working");
	}
}
public class ClassAndObject2 {
	 public static void main(String[] args) {
		Employee emp1 = new Employee();
		emp1.id = 1;
		emp1.name = "sinchana";
		System.out.println("name: "+emp1.name+" id is: "+emp1.id);
		emp1.isworking();
		Employee emp2 = new Employee();
		emp2.id = 2;
		emp2.name = "nayana";
		System.out.println("name: "+emp2.name+" id is: "+emp2.id);
		emp2.isworking();
	}
}
