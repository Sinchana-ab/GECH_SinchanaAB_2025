package method_local_inner_classes;

public class MethodLocalIneerClass {
	// to achive the nestedmethod 
	public static void m1() {
		class Inner{
			public void m2() {
				System.out.println("this is method local inner class");
			}
		}
		Inner in = new Inner();
		in.m2();
	}
	
	public static void main(String[] args) {
//		MethodLocalIneerClass class1 = new MethodLocalIneerClass();
//		class1.m1();
		//MethodLocalIneerClass.m1();
		m1();
	}
}
