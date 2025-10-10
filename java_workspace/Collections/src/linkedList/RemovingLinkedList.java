package linkedList;

import java.util.LinkedList;

/*
 * remove the first element
 * remove the last element
 * remove the first occurence of a given elements in the linkedList
 * clear completely
 * */

public class RemovingLinkedList {
	public static void main(String[] args) {
		LinkedList<String> li = new LinkedList<>();
		li.add("apple");
		li.add("orange");
		li.add("banana");
		li.add("pinaple");
		li.add("mango");
		System.out.println("the elemts are: "+li);
		
		String removed = li.removeFirst();
		System.out.println("after removing first element: "+li);
		
		System.out.println("after removing the last elements: "+li.removeLast());
		System.out.println("completly removing the elements: "+li);
		
		System.out.println("Removing the the particular elements through index: "+li.remove(1));
		System.out.println("completly removing the elements: "+li);
		
		System.out.println("Removing the the particular elements: "+li.remove("pinaple"));
		System.out.println("the elements  present: "+li);
		
		li.clear();
		System.out.println("completly removing the elements: "+li);
		
		
	}
}
