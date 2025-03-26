package com.LinedList;

import java.util.LinkedList;
import java.util.List;

/*
 * ----LinedList---------
 * it is underline dataStructure and doubly linked list
 * lined list allow duplicate
 * it allow random access(using index)
 * it will allow null value
 * it will allow null value
 * it will preserve the order of insertion
 * */
public class LinedListInJava {

	public static void main(String[] args) {
		List<Integer> list = new LinkedList<Integer>();
		list.addAll(List.of(45,43,null,23,43));
		System.out.println(list);
		System.out.println(list.get(2));
		
		
		/*
		 * Linked list occupy more memory compare to array list because LL has 3 nodes
		 * when ever there is a  continuous on deletion we should go for linked list[we shouldn't go for array list]
		 * */

	}

}
