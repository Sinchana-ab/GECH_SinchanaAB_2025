package java_10;

import java.util.Scanner;

public class Factorial {
	public int fact(int n) {
		if (n ==1 || n == 0)
			return 1;
		return n*fact(n-1);
	}

	public static void main(String[] args) {
		System.out.println("Enter the number: ");
		Scanner sc = new Scanner(System.in);
		int n =sc.nextInt();
		Factorial fact1 = new Factorial();
		int result = fact1.fact(n);
		System.out.println("the factorial of value "+n+" is "+result);
		//fact = fact * i
		sc.close();
	}
}
