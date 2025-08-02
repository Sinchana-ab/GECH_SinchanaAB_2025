package java_interview;

import java.util.Scanner;

public class SeconMax {
	public static void main(String[] args) {
		Scanner sc  =  new Scanner(System.in);
		System.out.println("enter a length of array: ");
		int n = sc.nextInt();
		int[] arr = new int[n];
		System.out.println("enter the array elements");
		for(int i = 0;i<n;i++) {
			arr[i] = sc.nextInt();
		}
		int max = arr[0], smax = Integer.MIN_VALUE;
		for(int  i = 0;i<n;i++) {
			if(arr[i]>max) {
				smax = max;
				max = arr[i];
			}
			else if(arr[i]>smax && arr[i] != max) {
				smax = arr[i];
			}
		}
		System.out.println("Second Maximum element is "+smax);
		sc.close();
	}
}
