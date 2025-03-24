package exceptionInJava;

public class ExceptionInjava3 {

	private static void validate(int age) {
		if(age<18) {
			throw new ArithmeticException("Invalid age");
		}
		else {
			System.out.println("Your age is "+age);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * finally block:
		 * ==============
		 * 		 */
//		try {
//			System.out.println(10/0);
//		} finally {
//			// TODO: handle finally clause
//			System.out.println("this is finally block");
//		}
//		try {
//			System.out.println(10/0);
//		} catch(Exception e) {
//			System.out.println(e);
//		} finally {
//			// TODO: handle finally clause
//			System.out.println("this is final block");
//		}
		validate(20);
		try {
			validate(12);
		}
		catch(Exception e) {
			System.out.println(e);
		}

	}

}