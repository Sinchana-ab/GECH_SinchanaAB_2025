package final_this_super;
class Person1{
	public String name;
	public int age;
	
	//no-args
	public Person1() {
		System.out.println("Default constructor");
	}
//
	public Person1(String name, int age) {
		this();
		this.name = name;
		this.age = age;
	}
	public void sayHello() {
		System.out.println("Hello");
	}
	public void display() {
		this.sayHello();
		System.out.println("Name is: "+this.name);
		System.out.println("Age is: "+this.age);
	}
}
public class ThisInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person1 p1 = new Person1("nayana", 22);
		
		p1.display();
	}

}
