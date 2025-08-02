package arraysInJava;

import java.util.Arrays;
import java.util.Scanner;

public class Second_maxElement_array {
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
			int max = arr[0];
			int Second_max = arr[0];
			for(int i = 1;i<n;i++) {
				if(max<arr[i]) {
					Second_max = max;
					max =arr[i];
				}
				else if(arr[i]>Second_max && arr[i] != max) {
					Second_max = arr[i];
				}
			}
			System.out.println("the maximun element is: "+max);
			System.out.println("the second maximun element is: "+Second_max);
			//2. method
			Arrays.sort(arr);
			System.out.println("the array element is is: ");
			for(int i=0;i<n;i++) {
				System.out.println(i+1+". the Element is: "+arr[i]);
			}

			System.out.println("the second maximun element is: "+arr[n-2]);
			sc.close();
	}
}
