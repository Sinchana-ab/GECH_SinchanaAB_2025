package functional_interface;

import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionInJava {
	public static void main(String[] args) {
		/*
		 * Function
		 * =========
		 * 	*it will take input argument and perform some action
		 *  and return result
		 * */
		
		Function<String, Integer> funt1 = s-> s.length();
		int res = funt1.apply("hello");
		System.out.println(res);
		
		//factorial of a number
		Function<Integer, Integer> factorial = f->{
			if(f == 0 || f == 1)
				return 1;
			int fact =1;
			for(int i = 1;i<=f;i++) {
				fact = fact * i;
			}
			return fact;
		};
		
		int result = factorial.apply(5);
		System.out.println("the factorial is: "+result);
		
		//prime or not 
		Predicate<Integer> pre = num->{
			if(num <= 1) {
				return false;
			}
			for(int i = 2;i<num/2;i++) {
				if(num%i == 0) {
					return false;
				}
			}
			return true;
		};
		boolean results =pre.test(4);
		if(results) {
			System.out.println("the number is prime");
		}
		else {
			System.out.println("the number not prime");
		}
	}
}
