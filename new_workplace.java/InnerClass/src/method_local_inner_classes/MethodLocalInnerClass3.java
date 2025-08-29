package method_local_inner_classes;

public class MethodLocalInnerClass3 {
// if we declare the method as static nonstatic intsance member can't access
	public int a =10;
	public static int b = 30;
	public static void m1() {
		class Inner{
			public void m2() {
			//	System.out.println(a+" "+b);
			}
		}
		Inner in = new Inner();
		in.m2();
	}
	
	public static void main(String[] args) {
		//  new MethodLocalIneerClass().m1();
		m1();
	}
}
