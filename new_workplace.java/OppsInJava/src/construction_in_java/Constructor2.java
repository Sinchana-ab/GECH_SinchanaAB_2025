package construction_in_java;

class Employee{
	
	public int id;
	public String name;
	public String Department;
	
	//parameterised constructor
	public Employee(int id, String name, String department) {
	
		this.id = id;
		this.name = name;
		this.Department = department;
	}


	public void isworking() {
		System.out.println(this.name+" is working in the department "+this.Department);
	}
}
public class Constructor2 {
	public static void main(String[] args) {
		Employee e = new Employee(01, "sinchana", "development");
		System.out.println("id is "+e.id+" with name is "+e.name+" working in department "+e.Department);
		e.isworking();
	}
}
