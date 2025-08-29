package method_and_constructor_reference;

import java.util.function.Supplier;

public class InstanceMethodReference1 {
	
	public String getmMessage() {
		return "this is instance method";
	}
	
	public static void main(String[] args) {
		InstanceMethodReference1 ir1 = new InstanceMethodReference1();
		Supplier<String> s = ()-> ir1.getmMessage();
		System.out.println("the method returing statement is:  "+s.get());
		//using instance method reference
		Supplier<String> s2 = ir1::getmMessage;
		System.out.println(s2.get());
		
	}
}
