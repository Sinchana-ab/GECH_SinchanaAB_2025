package java_interview;

import java.util.Scanner;

public class TypecastDouble_Int {
	public static void main(String[] args) {
		/*
		 * when we doing explicit typecasting from double int
		 * the chance of values loss will occure , because the int round of values by directly
		 * user input may losses*/
		Scanner sc = new Scanner(System.in);
		System.out.println("enter the double value: ");
		double n = sc.nextDouble();
		int n1 = (int)n;
		System.out.println("the double value: "+n);
		System.out.println("the int values: "+n1);
		sc.close();
	}
}
