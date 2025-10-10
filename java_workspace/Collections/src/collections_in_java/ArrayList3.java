package collections_in_java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayList3 {
	public static void main(String[] args) {
		List l = new  ArrayList();
		l.add(10);
		l.add(23);
		l.add(2);
		l.add(31);
		l.add(5);
		System.out.println(l);
		System.out.println("after sorting: ");
		//Collections is utility fuction which give some inbuilt method 
		Collections.sort(l);//sorting method in collections which default and natural sorting 
							// we can't modify it behaviour
		System.out.println(l);
		System.out.println("maximum element in array: "+Collections.max(l));
		System.out.println("minimum element in array: "+Collections.min(l));
		
	}
}
