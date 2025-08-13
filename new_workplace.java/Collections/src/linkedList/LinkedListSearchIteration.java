package linkedList;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListSearchIteration {
	public static void main(String[] args) {
		LinkedList<String> li = new LinkedList<>();
		li.add("apple");
		li.add("orange");
		li.add("mango");
		li.add("banana");
		li.add("pinaple");
		li.add("mango");
		System.out.println("the elemts are: "+li);
		
		boolean s = li.contains("banana");
		System.out.println("the result is: "+s);
		
		//finding the first occurence 
		int index = li.indexOf("mango");
		System.out.println(index);
		//last occurence of element
		int lastindex = li.lastIndexOf("mango");
		System.out.println("the last occurence of mango: "+lastindex);
		
		
		
		//iterator
		
		Iterator<String> ele = li.iterator();
		while(ele.hasNext()) {
			String one = ele.next();
			System.out.println(one);
		}
		
		// for Each
		li.forEach((e) -> {
			System.out.println(e);
		});
		
		//for loop and for Each advanced
		for(String el : li) {
			System.out.println(el);
		}
		
		for(int i = 0;i< li.size();i++) {
			System.out.println("the elements are : "+li.get(i));
		}
	}
}
