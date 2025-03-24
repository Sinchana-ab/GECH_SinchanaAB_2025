package inheritance;

class grandParentclass{
	public void grandParentMethod() {
		System.out.println("Grand parent method");
	}
}
class Parentclass extends grandParentclass{
	public void parentclassmethod() {
		System.out.println("Hello this is parent class ");
	}
}
class childclass extends Parentclass{
	public void childMethod() {
		System.out.println("this is child method");
	}
}

public class Multilevel {
	public static void main(String[] args) {
		childclass child = new childclass();
		child.childMethod();
		child.grandParentMethod();
		child.parentclassmethod();
	}
}
