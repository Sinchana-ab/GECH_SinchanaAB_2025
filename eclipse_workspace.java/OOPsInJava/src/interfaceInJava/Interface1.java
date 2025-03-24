package interfaceInJava;
interface Interface{
	public void method();
	public default void defaultMethod() {
		System.out.println("this is defalut method in interface");
	}
	public static void staticMethod() {
		System.out.println("This is static method in interface");
	}
}
class Child1 implements Interface{
	@Override
	public  void method() {
		System.out.println("This is a method");
	}
}
public class Interface1 {
	public static void main(String[] args) {
		Child1 child = new Child1();
		child.method();
		child.defaultMethod();
		Interface.staticMethod();
	}
}