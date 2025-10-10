package collections_in_java;

import java.util.ArrayList;
import java.util.Collection;

public class Collection2 {
	public static void main(String[] args) {
		
		/*
		 * collections:- its framework provide the utility class to perform the task
		 * 
		 * collection:- its interface its group of objects */
		
		Collection col = new ArrayList();
		col.add("123");
		col.add(231);
		col.add(123.322);
		col.add(true);// type safety is note there 
		
		Collection col2 = new ArrayList();
		col2.add("hello");
		col.addAll(col2);
		
		 System.out.println(col);
		 System.out.println(col.size());
	}
}
