package inheritance;

interface Interface2{
	public void interfaceMethod();
}

class Parents {
	public void parentmethod() {
		System.out.println("I am interface called inside Parent");
	}
}
 class Childs extends Parents implements Interface2{
	 @Override
	 public void interfaceMethod() {
		 System.out.println("Interface Method");
	 }
	 public void child3() {
		System.out.println("i am child method");
	}
 }

public class Hybrid {
	public static void main(String[] args) {
		Childs child = new Childs();
		child.child3();
		child.parentmethod();
		child.interfaceMethod();
	}
}
