package anonymous_inner_class;

public class Outer {
	static class Inner{
		public void main() {
			System.out.println("Hii");
		}
	}
	public static void main(String[] args) {
		Inner i = new Inner();
		//i.main();
		Outer.Inner n=  new Outer.Inner();
		n.main();
	}
}
