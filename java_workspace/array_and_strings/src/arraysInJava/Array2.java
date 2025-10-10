package arraysInJava;

import java.util.Scanner;

public class Array2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter the marks size needed: ");
		int n = sc.nextInt();
		int[] std_marks = new int[n];
		System.out.println("enter the Student marks: ");
		for(int i = 0;i<n;i++) {
			std_marks[i] = sc.nextInt();
		}
		System.out.println("the student marks is: ");
		for(int i=0;i<n;i++) {
			System.out.println(i+1+". the Element is: "+std_marks[i]);
		}
		sc.close();
	}
}
