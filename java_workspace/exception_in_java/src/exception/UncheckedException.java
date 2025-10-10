package exception;

import java.util.Scanner;

public class UncheckedException {
	
	/*
	 * Arthemntic expression:- 
	 * 	/ by zero
	 * 
	 * NullpointerException
	 * --------------------
	 * 
	 * when we performing on null values we get, to handle we optional class*/
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter first number: ");
		int a = sc.nextInt();
		System.out.println("enter Second number: ");
		int  b = sc.nextInt();
		try {
			int res = a/b;
			System.out.println("devision is: "+res);
		} catch (ArithmeticException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e);
		}
		System.out.println("End of the program");
		sc.close();
	}
}
