package arraysInJava;

import java.util.Scanner;
/*
 * array declaration :- <data type>[] arraya-name = new <datatype>[size]
 * through index we acces the array elements :- arr[0]
 * */
public class Array1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter the array size: ");
		int n = sc.nextInt();
		int[] arr = new int[n];
		System.out.println("enter the array elements: ");
		for(int i = 0;i<n;i++) {
			arr[i] = sc.nextInt();
		}
		System.out.println("array elements are: ");
		for(int i =0;i<n;i++) {
			System.out.println("at the index "+i+"the element is  "+arr[i]);
		}
	}
}
