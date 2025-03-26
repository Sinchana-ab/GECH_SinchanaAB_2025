package com.collectionsInJava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListDemo1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(20);
		list.add(30);
		list.add(12);
		//1. for loop
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i)+" ");
		}
		System.out.println("\n");
		//2.enhance for each 
		for(int i : list){
			System.out.println(i+"");
		}
		System.out.println("\n");
		
		//3.foreach
		list.forEach(i->System.out.println(i+""));
		System.out.println("\n");
		
		//4.iterator
		Iterator<Integer> itr = list.iterator();
		while(itr.hasNext()) {
			//int i = itr.next();
			System.out.println(itr.next()+"");
		}
		
	}

}
