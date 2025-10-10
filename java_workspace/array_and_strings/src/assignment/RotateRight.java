package assignment;

import java.util.Arrays;
import java.util.Scanner;

public class RotateRight {
	
	static void reverse(int[] arr,int start, int end) {
		
		while(start<end){
			int temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			start++;
			end--;
		}
		
	}
	
	static void rotate(int[] arr, int k){
		int n = arr.length;
		k %= n;
		reverse(arr,0,k-1);
		reverse(arr,k,n-1);
		reverse(arr,0,n-1);
	}
	
	public static void main(String[] args) {
		int[] arr = {2,3,4,5,6};
		System.out.println("array is "+Arrays.toString(arr));
		int k = 3;
		rotate(arr,k);
		System.out.println("Rotated array: "+Arrays.toString(arr));
	}
}
