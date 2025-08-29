package method_and_constructor_reference;

import java.util.function.Consumer;

public class StaticMethodReference {
	
	public static void printMessage(String msg) {
		System.out.println("your message is: "+msg);
	}
	
	public static void main(String[] args) {
		//
//		Consumer<String> con = s->StaticMethodReference.printMessage(s);
//		con.accept("hello world");
		// it helps to reduce these code 
		Consumer<String> con = StaticMethodReference::printMessage;
		con.accept("hello world");
	}
}
