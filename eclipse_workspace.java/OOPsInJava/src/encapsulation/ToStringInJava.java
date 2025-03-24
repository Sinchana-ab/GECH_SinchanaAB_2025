package encapsulation;

class Person{
	public String name;
	public int age;
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ",age=" +age +"]";
	}
}
public class ToStringInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person p1 = new Person("Sinchana", 20);
		System.out.println(p1.name);
		System.out.println(p1.age);
	}

}
