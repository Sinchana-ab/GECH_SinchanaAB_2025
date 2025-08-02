package polymorphism;

class Addition{
	public void add(int a, int b) {
		System.out.println("the result is "+(a+b));
	}
	public void add(int a, int b,int c) {
		System.out.println("the result is "+(a+b+c));
	}
}


public class Polymorphism1 {
	public static void main(String[] args) {
		Addition a = new Addition();
		a.add(10, 20);
		a.add(20, 30, 40);
	}
}
