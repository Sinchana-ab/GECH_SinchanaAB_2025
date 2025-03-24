package inheritance;
class Parent{
	int pdata = 200;
	public void parentMethod() {
		System.out.println("This parent method");
	}
	void defaultMethod() {
	System.out.println("default method");
}
	public static void staticMethod() {
		System.out.println("static methods");
	}
}
class Child extends Parent{
	int cdata = 200;
	public void childMethod() {
		System.out.println("This is a child method");
	}
}
public class InheritanceInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Inheritance -> extending the propertices from parent
		 * properties(state and actions)
		 * =============
		 * extending the properties from parent to child
		 * properties(state and actions)
		 * types:
		 * =================
		 * 1. single level
		 * 2. multilevel
		 * 3. hierarchical
		 * 4. hybrid
		 * 5. multiple -> not possible
		 * */
		Child child = new Child();
		child.childMethod();
		child.parentMethod();
		child.defaultMethod();
		Parent.staticMethod();
//		child.staticMethod();
		System.out.println(child.cdata);
		System.out.println(child.pdata);
	}

}
