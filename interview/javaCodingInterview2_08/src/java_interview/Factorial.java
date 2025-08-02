package java_interview;

import java.util.Scanner;

public class Factorial {
	public static int fact(int n) {
		if(n == 0 || n == 1) {
			return 1;
		}
		return n*fact(n-1);
	}
	public static void main(String[] args) {
		Scanner sc  =  new Scanner(System.in);
		System.out.println("enter a digits: ");
		int a = sc.nextInt();
		System.out.println("factorial of number: "+(fact(a)));
		sc.close();
	}
}
