package inheritance;

class Parent1{
	public void parentMethod() {
		System.out.println("i am parent method");
	}
}

class Child1 extends Parent1{
	public void child1Method() {
		System.out.println("iam child1 method");
	}
}
class Child2 extends Parent1{
	public void child2Method() {
		System.out.println("iam child2 method");
	}
} 

public class Hierarchical {
	public static void main(String[] args) {
		Child1 child1 = new Child1();
		child1.child1Method();
		child1.parentMethod();
		Child2 child2 = new Child2();
		child2.child2Method();
		child2.parentMethod();
	}
}
