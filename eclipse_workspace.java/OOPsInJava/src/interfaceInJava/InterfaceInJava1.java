package interfaceInJava;

interface A{
	public void methodA();
}
interface B{
	public void methodB();
}

 class Child implements A, B{
	@Override
	public void methodB() {
		System.out.println("This is method B");
	}
	@Override
	public void methodA() {
		// TODO Auto-generated method stub
		System.out.println("This is a method A");
	}
}

public class InterfaceInJava1 {
	public static void main(String[] args) {
		Child child = new Child();
		child.methodB();
		child.methodB();
	}
}
