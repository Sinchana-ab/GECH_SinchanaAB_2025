package java_10;

import java.util.Scanner;

public class Markmultiple {
	public static void main(String[] args) {
		
		System.out.println("enter value");
		Scanner sc = new Scanner(System.in);
		int value = sc.nextInt();
		System.out.println("the value multiple by 10 is:  "+(value*10));
		sc.close();
	}
}
