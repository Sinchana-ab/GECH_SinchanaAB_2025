package arraysInJava;

import java.util.Scanner;

public class Max_Element_Array {
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
			int max = arr[0];
			for(int i = 1;i<n;i++) {
				if(max<arr[i]) {
					max =arr[i];
				}
			}
			System.out.println("the maximun element is: "+max);
			sc.close();
	}
}
