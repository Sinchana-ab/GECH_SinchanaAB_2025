package java_interview;

import java.util.Scanner;

public class SwapWithouttemp {
	public static void main(String[] args) {
		Scanner sc  =  new Scanner(System.in);
		System.out.println("enter two digits: ");
		int a = sc.nextInt();
		int b = sc.nextInt();
		System.out.println("Before swapping: a is"+a+" and b is "+b);
		a = a+b;
		b = a - b;
		a = a - b;
		System.out.println("after swapping: a is"+a+" and b is "+b);
		sc.close();
	}
}
