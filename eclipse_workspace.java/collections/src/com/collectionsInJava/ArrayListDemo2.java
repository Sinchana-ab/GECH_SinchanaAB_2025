package com.collectionsInJava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayListDemo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		list.add(120);
		list.add(3);
		list.add(40);
		list.add(40);
		list.add(12);
		System.out.println("Before: "+list);
		/*----collections----
		 * it is utility class that provides methods to work with collection
		 * */
		Collections.sort(list);
		System.out.println("After sorting: "+list);
		System.out.println("min value: "+Collections.min(list));
		Collections.reverse(list);
		System.out.println("after reversing list: "+list);
	}

}
