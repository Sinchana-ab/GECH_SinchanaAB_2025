package Array2D_in_java;

import java.util.Scanner;

public class TwoDArray {
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
		
		sc.close();
	}
}
