package dependancy_injection;

public class Employee {
	public String name;
	public Address address;
	
	
	
	 public Employee() {
	}



	 public Employee(String name, Address address) {
		this.name = name;
		this.address = address;
	}



	 public static void main(String[] args) {
		Employee employee = new Employee();
		System.out.println(employee.name);//null
		System.out.println(employee.address);//null
		
		Address address = new Address();
		address.name = "bengaluru";
		employee.name = "sinchana";
		employee.address = address;
		System.out.println("after");
		System.out.println(employee.name);//sinchana
		//System.out.println(employee.address)//referenc address wii print
		System.out.println(employee.address.name);
		
		//Setter injection
		address.setName("hassan");
		address.setCountry("India");
		System.out.println("after setter injection");
		System.out.println(employee.address.getName());
		System.out.println(employee.address.getCountry());
		System.out.println(employee.address.name);
		
		//Construction injection
		Address ad1 = new Address("Mangaluru","India");
		Employee e1 = new Employee("Nayana", ad1);
		System.out.println("afetr constructor injection");
		System.out.println(e1.name);
		System.out.println(e1.address.name);
		System.out.println(e1.address.getCountry());
	 }
}
