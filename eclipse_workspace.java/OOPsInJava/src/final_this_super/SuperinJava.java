package final_this_super;

class NewAnimal{
	public String name;
	public int age;
	public NewAnimal(String name, int age) {
		super();
		System.out.println("This is parent class constructor");
		this.name = name;
		this.age = age;
	}
	public void display() {
		System.out.println("name: "+this.name);
		System.out.println("Age: "+this.age);
	}
}

class newDog extends NewAnimal{
	public String eat;

	public newDog(String name, int age,String eat) {
		super(name, age);
		System.out.println("child class constructor");
		this.eat = eat;
	}
	public void display() {
		super.display();
		System.out.println("Dog eats: "+this.eat);
		System.out.println("name of dog once again: "+super.name);
	}
}

public class SuperinJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		newDog dog1 = new newDog("Dog1", 20, "rice");
		dog1.display();
	}

}
