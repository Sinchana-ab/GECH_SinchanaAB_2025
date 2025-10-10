package collections_in_java;

import java.util.ArrayList;
import java.util.Collections;

public class ArrayList4 {
	public static void main(String[] args) {
		//comparable and comparator
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(23);
		arr.add(43);
		arr.add(21);
		arr.add(90);
		arr.add(13);
		
		//1. comparable:- if you need default or natural sorting 
		//order then we should comparable
		Collections.sort(arr);
		System.out.println("using comparable: "+arr);
		
		/*
		 * compare() Method: The core of the Comparator interface is the compare(T o1, To2) method. This method takes two objects of the same type (T) as arguments
		 * and returns:
		 * A negative integer if o1 should come before o2. Zero if o1 and
		 * o2 are considered equal for sorting purposes. 
		 * A positive integer if o1 should come after o2.*/
		
		
		//2. comparator:- if you need custom sorting order then we should o for comparatr interface
		Collections.sort(arr,(a1, a2)-> a2 - a1);
		// arr (23, 43)-> 43 - 23(true)-> 43 first then 23
		System.out.println("using comparable: "+arr);
	}
}
