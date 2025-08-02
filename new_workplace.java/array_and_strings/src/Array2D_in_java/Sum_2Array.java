package Array2D_in_java;

import java.util.Scanner;

public class Sum_2Array {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the rows and columns: ");
		int n = sc.nextInt();
		int m = sc.nextInt();
		int[][] arr = new int[n][m];
		System.out.println("Enter the array elements:");
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				arr[i][j] = sc.nextInt();
			}
		}
		System.out.println("Array elements are: ");
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
		 
		int sum = 0;
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				sum += arr[i][j] ;
			}
		}
		System.out.println("the sum of 2d array is: "+sum);
		
		//method1: to find max
		int max = arr[0][0];
		int min = arr[0][0];
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				
				if(max < arr[i][j]) {
					max = arr[i][j];
				}
				if(min>arr[i][j]) {
					min = arr[i][j];
				}
			}
		}
		System.out.println("the maximun element is: "+max+" and minimum elements is "+min);
		
		sc.close();
	}
}
