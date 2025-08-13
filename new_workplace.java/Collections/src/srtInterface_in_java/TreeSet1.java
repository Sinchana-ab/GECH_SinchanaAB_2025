package srtInterface_in_java;

import java.util.Set;
import java.util.TreeSet;

public class TreeSet1 {
	/*
	 * package java.util and it store the unique elements in sorted order
	 * sorted order(nature of comparator)
	 * unique elemets
	 * navigableSet menthods :- provides method like higher(),lower(), ceiling() floor()
	 * Not Thread safe
	 * implemets Red black tree
	 * 
	 * */
	public static void main(String[] args) {
		Set<String> ts = new TreeSet<>();

        // Elements are added using add() method
        ts.add("Java");
        ts.add("Full");
        ts.add("Stack");
        System.out.println(ts);
        
        String s = "Java";
        // checking that object present in set use contains(object)
        System.out.println("if the string is contains: "+(ts.contains(s)));
        
        // higher => based on the size of object size 
	}
}
