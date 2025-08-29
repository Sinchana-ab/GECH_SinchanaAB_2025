package exception;

public class ThrowException {
/*
 * when manually exception or any exception throw by programmer 
 * explicitly exception by programmer
 * */
	public static void validateAge(int age) {
		if(age>=18) {
			System.out.println("you can vote");
		}
		else {
			throw new ArithmeticException("your can't vote");
		}
	}
	public static void main(String[] args) {
		try {
			validateAge(12);
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println("this is final block");
		}
	}
}
