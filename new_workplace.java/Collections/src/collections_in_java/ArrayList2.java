package collections_in_java;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

public class ArrayList2 {
	 public static void main(String[] args) {
		
		/*
		 * Accessing element throgh loop
		 * 1. for
		 * 2. for each
		 * 3. forEach
		 * 4. iterator()
		 * 5. ListIterator() */
		 
		 ArrayList arr = new ArrayList();
		 	arr.add(2);
			arr.add("sinchana");
			arr.add(123);
			arr.add(123);
			arr.add(null);
			arr.add(null);
			
		//for loop
			System.out.println("the For loop");
			for(int i = 0; i< arr.size();i++) {
				System.out.println(arr.get(i));
			}
			
		// for each
			System.out.println("using for each loop");
			for(Object item : arr) {
				System.out.println(item);
			}
		
			System.out.println("using the forEach");
		// forEach
			arr.forEach(item ->{
				System.out.println(item);
			});
			
			// iterator()
			System.out.println("using iterator()");
		Iterator itr = arr.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		
		// ListIterator()
		System.out.println("using listIterator()");
		ListIterator it = arr.listIterator();
			while(it.hasNext()) {
				System.out.println(it.next());
			}
//			while(it.hasPrevious()) {
//				System.out.println(it.next());
//			}
			
		//6. enumeration:- is a interface which helps traverse the element in arrayList
			// suitable for old class{implementing classs[
			Vector v = new Vector();
			v.add("123");
			v.add(123);
			System.out.println("using enumeration");
			Enumeration e = v.elements();
			while(e.hasMoreElements()) {
				System.out.println(e.nextElement());
			}
			
	}
}
