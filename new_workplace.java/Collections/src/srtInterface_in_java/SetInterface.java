package srtInterface_in_java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SetInterface {
	public static void main(String[] args) {
		/*
		 * Set:
		 * --------------
		 * * when you represent a group of individual object as a single entity
		 * where the insertion order is not preserved, duplicated will not allow
		 * then we should got for set interface
		 * 
		 * 1. order is not preserved 
		 * 2. duplicate will not allow
		 * */
		
		Set<Integer> st =  new HashSet<Integer>();
		st.add(12);
		st.add(23);
		st.add(42);
		st.add(92);
		st.add(null);
		
		System.out.println(st);
		
		List<Integer> marks = new ArrayList<Integer>();
		marks.add(100);
		marks.add(200);
		marks.add(100);
		marks.add(230);
		marks.add(200);
		marks.add(150);
		
		System.out.println(marks);
		Iterator<Integer> a  =marks.iterator();
		while(a.hasNext()) {
			System.out.println(a.next());
		}
		Set<Integer> s = new HashSet<Integer>(marks);
		System.out.println(s);
		
	}
}
