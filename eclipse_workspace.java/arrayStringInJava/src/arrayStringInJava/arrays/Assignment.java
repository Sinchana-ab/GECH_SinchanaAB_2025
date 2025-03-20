package arrayStringInJava.arrays;

import java.util.Scanner;

public class Assignment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter array length: ");
		int n = sc.nextInt();
		int[] array1 = new int[n];
		System.out.println("Enter values of array");
		for(int i=0;i<n;i++) {
			array1[i] = sc.nextInt();
		}
		System.out.println("array elements are:");
		for(var i:array1) {
			System.out.println(i+"");
		}
		sc.close();
	}

}
