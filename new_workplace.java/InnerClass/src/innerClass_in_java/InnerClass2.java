package innerClass_in_java;

public class InnerClass2 {
	class Engine{
		public String name = "thar";
	}
	public void m1(){
		Engine e = new Engine();
		System.out.println("the engine name is:  "+e.name);
		
	}
	public static void main(String[] args) {
		InnerClass2 in = new InnerClass2();
		in.m1();
	}
}
