package java_10;

import java.util.Scanner;

public class Fibbonoci {
	public static void main(String[] args) {
		System.out.println("Enter the number of terms: ");
		Scanner sc = new Scanner(System.in);
		
		int a =0,b =1;
		int d;
		
		 int c = sc.nextInt();
		 for(int i =0;i<c;i++) {
			 System.out.println(a);
			 d = a+b;
			 a =b;
			 b = d;
		 }
		 
		 //recursion
		 /*
		  * fibbo n =0 --> 0
		  * n = 1 --> 0*/
		 //if(n == 0 || n ==1 )
			// return 0;
		// return fibbo(n-1)+fibbo(n-2)
		sc.close();
	}
}
