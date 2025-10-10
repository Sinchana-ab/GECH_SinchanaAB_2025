package Array2D_in_java;

import java.util.Scanner;

public class Traingular {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("enter the number upto triangle print");
		int n = sc.nextInt();
		
		for(int i = 1;i<=n;i++) {
			for(int k = 1;k<=i;k++) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
		for(int i = 1;i<=n;i++) {
			for(int j = 1; j<= n-i;j++) {
				System.out.print(" ");
			}
			for(int k = 1;k<=i;k++) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
		for(int i = n;i>=1;i--) {
			for(int k = 1;k<=i;k++) {
				System.out.print(i+" ");
			}
			System.out.println();
		}
		sc.close();
	}
}
