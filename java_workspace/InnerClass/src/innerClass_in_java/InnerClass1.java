package innerClass_in_java;

public class InnerClass1 {
	class Department{
		public int a = 10;
	}
	public static void main(String[] args) {
		InnerClass1 outer = new InnerClass1();
		Department dept = outer.new Department();
		System.out.println(dept.a);
	}
}
