package arraysInJava;

import java.util.Scanner;

public class Reverse_array {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter the array size needed: ");
		int n = sc.nextInt();
		int[] arr = new int[n];
		System.out.println("enter the array element : ");
		for(int i = 0;i<n;i++) {
			arr[i] = sc.nextInt();
		}
		System.out.println("the student marks is: ");
		for(int i=0;i<n;i++) {
			System.out.println(i+1+". the Element is: "+arr[i]);
		}
		System.out.println("the reversed array is: ");
		int k = 0, j =n-1;
		while(k<j) {
			int temp = arr[k];
			arr[k] = arr[j];
			arr[j] = temp;
			k++;
			j--;
		}
		for(int i=0;i<n;i++) {
			System.out.println(i+1+". the Element is: "+arr[i]);
		}
		//for(int i=n-1;i>=0;i--) {
			//5-4
			//System.out.println((n-i)+". the Element is: "+arr[i]);
			//System.out.println(arr[i]);
		//}
		sc.close();

	}
}
