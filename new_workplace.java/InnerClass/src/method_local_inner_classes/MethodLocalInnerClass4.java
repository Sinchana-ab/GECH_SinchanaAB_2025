package method_local_inner_classes;

public class MethodLocalInnerClass4 {
	public int a =10;
	public static int b = 30;
	public void m1() {
		 int z = 20;
//		class Inner{
//			public void m2() {
//				System.out.println("the z values: "+z);
//				System.out.println(a+" "+b);
//			}
//		}
		 //we can declare final and abstract
		final class Inner{
				public void m2() {
					System.out.println("the z values: "+z);
					System.out.println(a+" "+b);
				}
			}
		Inner in = new Inner();
		in.m2();
	}
	
	public static void main(String[] args) {
		  new MethodLocalInnerClass4().m1();
	}
}
