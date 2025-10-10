package collections_in_java;

import java.util.ArrayList;

public class ArrayList1 {
	public static void main(String[] args) {
		/*
		 * ArrayList
		 * =====================
		 * ArrayList is a improved version of array(Dynamic array
		 * is underline data structure of dynamic array
		 * it will allow random acess(using index)
		 * it will allow null value
		 * arraylist will preserve the order of insertion
		 * 
		 * */
		ArrayList arr = new ArrayList();
		//List li = new ArrayList1();
		/// we called upcasting beacause we are assigning or implememting through the child to parent class
		// here List is parent and ArrayList
		//add the elements to arralist
		arr.add("sinchana");
		arr.add(123);
		arr.add(123);
		arr.add(null);
		arr.add(null);
		System.out.println(arr);
		System.out.println(arr.getFirst());
		System.out.println(arr.getLast());
		System.out.println(arr.remove(0));
		System.out.println(arr.size());
	}
}
