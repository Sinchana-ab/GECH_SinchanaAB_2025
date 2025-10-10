package arraysInJava;

import java.util.Arrays;
import java.util.Scanner;

public class Second_minElement_array {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter the array size needed: ");
		int n = sc.nextInt();
		int[] arr = new int[n];
		System.out.println("enter the array element : ");
		for(int i = 0;i<n;i++) {
			arr[i] = sc.nextInt();
		}
		System.out.println("the array element is is: ");
		for(int i=0;i<n;i++) {
			System.out.println(i+1+". the Element is: "+arr[i]);
		}
		int min = arr[0];
		int Second_min = Integer.MAX_VALUE;
		for(int i = 1;i<n;i++) {
			if(min>arr[i]) {
				Second_min = min;
				min =arr[i];
			}
			else if(Second_min>arr[i] && arr[i] != min) {
				Second_min = arr[i];
			}
		}
		System.out.println("the maximun element is: "+min);
		System.out.println("the second maximun element is: "+Second_min);
		//2. method
		Arrays.sort(arr);
		System.out.println("the array element is is: ");
		for(int i=0;i<n;i++) {
			System.out.println(i+1+". the Element is: "+arr[i]);
		}
		// method
		System.out.println("the maximun element is: "+arr[0]);
		System.out.println("the second maximun element is: "+arr[1]);
		sc.close();
	}
}
