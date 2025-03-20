package arrayStringInJava.arrays;

import java.util.Scanner;

public class ArrayInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Array in java
		 * ============
		 * if we wnat to store multiple values in same datatype
		 * =========
		 * how to declare the array
		 * ===================
		 * <datatype>[] = new int[4]
		 * ==========
		 * how to intilize the array value
		 * int [] array1 = {1,2,3,4}
		 * ==========
		 * types of array
		 * 1. 1D array:- single rows
		 * ex: int [] array = {1,2,3,4} int [] array = new int[4]
		 * =============
		 * 2. 2D array:- table(both row and column)
		 * int [][] array1 = new int[4][4]; --> 4*3=12;
		 * 
		 * */
		int[] arr = new int[5];
		arr[0] = 10;
		arr[1] = 20;
		arr[2] = 30;
		arr[3] = 40;
		arr[4] = 50;
		// arr[0] = 30; we get arraybond exception
//		for(var i:arr) {
//			System.out.println(i+"");
//		}
	
		for(int i=0;i<=4;i++) {
			arr[i] = 10;
		}
		for(var i:arr) {
			System.out.println(i+"");
		}
		
	}

}
