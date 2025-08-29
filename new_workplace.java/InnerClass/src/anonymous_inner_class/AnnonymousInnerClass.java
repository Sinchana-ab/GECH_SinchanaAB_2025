package anonymous_inner_class;

class Person{
	public int age;
	public String name;
	public void isWalking() {
		System.out.println("the person is walking");
	}
}

public class AnnonymousInnerClass {
	public static void main(String[] args) {
		Person p = new Person(){
			//public static int i;
			{//instance block:- used to initilize instance variables
				age = 20;
				name = "sinchana";
			}
//			static {
//				//static block
//				i = 20;
//			}
			static{
				//int roll = 20;
				System.out.println("these static block");
			}
			public void isWalking() {
				System.out.println("Mohan is walking");
			}
		};
		System.out.println(p.name);
		System.out.println(p.age);
		//System.out.println(Person.i);
		p.isWalking();
	}
}
