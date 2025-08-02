package java_interview;

import java.util.Scanner;

public class StrongNumber {
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
		int rem = 0, strong = 0;
		int n = a;
		while(a>0) {
			rem = a % 10;
			strong += fact(rem);
			a = a / 10;
		}
		if(n == strong) {
			System.out.println("the number is strong number");
		}
		else {
			System.out.println("the number is not strong number");
		}
		sc.close();
	}
}
