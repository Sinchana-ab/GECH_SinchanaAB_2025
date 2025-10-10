package method_and_constructor_reference;

import java.util.Arrays;

public class InstanceMethodReference2 {
	public static void main(String[] args) {
		String[] names = {"", "sinchana", "nayana","abhi"};
		//withod instancemethod refence of  a arbitory
		 // Arrays.sort(names, (a,b)->a.compareToIgnoreCase(b));
		//using method reference
		Arrays.sort(names, String::compareToIgnoreCase);
		System.out.println("the sorted names: "+(Arrays.toString(names)));
	}
}
