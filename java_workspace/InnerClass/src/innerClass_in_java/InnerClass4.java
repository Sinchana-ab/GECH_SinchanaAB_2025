package innerClass_in_java;

class Car{
	public String name = "car1";
	class Engine{
		public String name = "engine1";
		public void dispaly() {
			System.out.println("the name is: "+this.name);
			System.out.println("the name is: "+Engine.this.name);
			System.out.println("the name is: "+Car.this.name);
		}
	}
}

public class InnerClass4 {
	public static void main(String[] args) {
		//Car c = new Car();
		//Engine e1 = c.new Engine();
		new Car().new Engine().dispaly();
		
	}
}
