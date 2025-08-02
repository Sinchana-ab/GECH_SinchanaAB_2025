package java_interview;

import java.util.Scanner;

public class ReverseNumber {
	public static void main(String[] args) {
		Scanner sc  =  new Scanner(System.in);
		System.out.println("enter a digits: ");
		int a = sc.nextInt();
		System.out.println("the number is "+a);
		int rem = 0,rev = 0;
		while(a>0) {
			rem = a % 10;
			rev = rev * 10 +rem;
			a = a/ 10;
		}
		System.out.println("reversed number is: "+rev);
		sc.close();
	}
}
