package collections_in_java;

import java.util.ArrayList;
import java.util.List;

public class ListInterfcae1 {
	public static void main(String[] args) {
		/*
		 * list
		 * -------------------
		 * its the  child interface of collection
		 * it will contain some of the built in methods(add(), remove(), get(),etc .. that used inside a list implemented 
		 * class only 
		 * when  you represent group of object represnted as singke entity 
		 * where order is preserved and index based acess and duplications  are allowed
		 * */
		
		List l1 = new ArrayList();
		l1.add("hello");
		l1.add(123);
		l1.add("hello");
		l1.add(true);
		String s = (String) l1.get(0);
		////type casting is doing to overcome from these we use generics 
		System.out.println(l1);
		System.out.println(s);
		System.out.println(l1.size());
		System.out.println(l1.remove(0));
		System.out.println(l1);
	}
}
