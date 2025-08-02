package java_interview;

import java.util.Scanner;

public class PalindromeString {
	public static void main(String[] args) {
		Scanner sc  =  new Scanner(System.in);
		System.out.println("enter a string: ");
		String s = sc.next();
		int n = s.length();
		String s1 = "";
		for(int i = n-1;i>=0;i--) {
			 char ch =  s.charAt(i);
			 s1 += ch;
		}
		System.out.println("entered string is: "+s);
		System.out.println("reversed string is "+s1);
		if(s1.equals(s)) {
			System.out.println("the String is palindrome");
		}
		else {
			System.out.println("the String is not a palindrome");
		}
		sc.close();
	}
}
