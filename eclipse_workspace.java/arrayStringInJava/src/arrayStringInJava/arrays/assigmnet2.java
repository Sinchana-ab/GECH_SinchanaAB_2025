package arrayStringInJava.arrays;

import java.util.Scanner;

public class assigmnet2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter array size: ");
		int m = sc.nextInt();
		int n = sc.nextInt();
		int [][] array = new int[m][n];
		System.out.println("Enter the array elements: ");
		for(int i=0;i<array.length;i++) {
			for(int j=0;j<array[i].length;j++) {
				array[i][j] = sc.nextInt();
			}
		}
		for(int i=0;i<array.length;i++) {
			for(int j=0;j<array[i].length;j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
		sc.close();
		/*assignment for character array*/
	}

}
