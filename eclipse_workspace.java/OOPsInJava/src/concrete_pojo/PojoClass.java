package concrete_pojo;

class Pojo{
	private String name;
	private int age;
	
	//all args constructor
	public Pojo(String name, int age) {
		super();
		System.out.println("full args Cnstructor");
		this.name = name;
		this.age = age;
	}
	
	//no args constructor
	public Pojo() {
		super();
		System.out.println("no args constructor");
	}
	
	//tostring

	@Override
	public String toString() {
		return "Pojo [name=" + name + ", age=" + age + "]";
	}
	
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
	
}

public class PojoClass {
	public static void main(String[] args) {
		/*
		 * POJO Class:
		 * ======================
		 * plain old java object (pojo)
		 * rules: -
		 * 1, the class should not extends or implements any class or interface
		 * 2. Every feilds(state/properties) should be private 
		 * 3.all- args COnstractor
		 * 4. no-args Cnstructor
		 * 5. tostring()
		 * 6. getter and setter public method*/
		Pojo student1 = new Pojo("Sinchana", 20);
		System.out.println(student1);
	}
}
