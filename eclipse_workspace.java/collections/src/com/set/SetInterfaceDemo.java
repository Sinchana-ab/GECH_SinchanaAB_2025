package com.set;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/*
 * --set--
 * when you respresenting a group of indivduals object as a single entity
 * where the insertion order is not preserved , duplicate will not allow 
 * the we should go for set interface*/

public class SetInterfaceDemo {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(Arrays.asList(12,null,12,30,45,30));
		System.out.println(set);
		System.out.println(set);
		
		List<Integer> list = new LinkedList<Integer>();
		list.addAll(List.of(45,43,23,21,45,43,43));
		System.out.println("list: "+list);
		
		Set<Integer> set1 = new HashSet<Integer>(list);
		System.out.println("set1: "+set1);
		
	//	Collections.sort(set1);
	//	System.out.println("sorted: "+set1);
	}
}
