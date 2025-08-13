package generics_in_java;

import java.util.ArrayList;
import java.util.List;

public class Generics1 {
/*
 * Generics:
 * ==========
 * to provide type safety and avoid type casting in 
 * collection then we should go for generics
 * syntax:
 * =========
 * List<type of object> arr = new List<type of object>();
 * */
	public static void main(String[] args) {
		// from java8 it becomes optional <String>
		List<String> names = new ArrayList<String>();
		names.add("sinchana");
		names.add("nayana");
		
		String n1 = names.get(0);
		String n2 = names.get(1);
		System.out.println("the first name is: "+n1);
		System.out.println("the second name is: "+n2);
	}
}
