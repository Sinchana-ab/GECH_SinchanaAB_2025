package functionInJava.functions;

public class FunctionsInjava1 {
	/*
	 * function accesifier
	 * 1. public -> anywhere*/
	public void publicFunction(){
		System.out.println("Public function");
	}
	/*
	 * 2. private ->within class */
	private void privateFunction(){
		System.out.println("private function");
	}
	//3. default -within package
	void defaultfunction() {
		System.out.println("defaiult function");
	}
	//4. protected function-> accesed by within package and subclass in other function
	protected void protectedFunction() {
		System.out.println("protected function");
	}
	//5 static function :-> can directly accesed within class and no need to call object
	public static void staticfunction() {
		System.out.println("This is static Function");
	}
	public static void main(String[] args) {
		System.out.println("This is static  main function");
	}
}
