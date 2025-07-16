package java_10;

import java.util.Scanner;

public class Palindrome {
	public static void main(String[] args) {
		System.out.println("Enter a number:");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int n1 = Integer.reverse(n);
		System.out.println(n1);
		if(n == n1) {
			System.out.println("the number is palindrome");
		}
		else {
			System.out.println("the number is not a palindrome");
		}
		
	}
}
