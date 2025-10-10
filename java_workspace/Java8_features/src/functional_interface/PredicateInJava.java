package functional_interface;

import java.util.function.Predicate;

public class PredicateInJava {
	public static void main(String[] args) {
		Predicate<Integer> pre = i-> i%2 == 0;
		boolean test = pre.test(12);
		System.out.println("the number 132321 is even or add ans is : "+(pre.test(123221)));
		if(test) {
			System.out.println("the number is even");
		}
		else {
			System.out.println("the number is odd");
		}
		
		Predicate<String> pre1 = s->s.length() > 10;
		boolean test1 = pre1.test("Sinchana");
		System.out.println("the number Sinchana is even or add ans is : "+(pre1.test("sinchana")));
		if(test1) {
			System.out.println("the number is more than 10");
		}
		else {
			System.out.println("the number is less than 10");
		}
		
	}
}
