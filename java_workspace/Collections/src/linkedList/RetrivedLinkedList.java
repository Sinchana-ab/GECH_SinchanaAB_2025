package linkedList;

import java.util.LinkedList;

/*
 * How to get the first element in linkedList 
 * last and element at a given index in thr linkedList
 * 
 * how to retrive all the elements */
public class RetrivedLinkedList {
	public static void main(String[] args) {
		LinkedList<String> li = new LinkedList<>();
		li.add("apple");
		li.add("orange");
		li.add("banana");
		li.add("pinaple");
		li.add("mango");
		
		
		String Felement = li.getFirst();
		System.out.println("the first element is: "+Felement);
		
		System.out.println("the last element is: "+(li.getLast()));
		
		//retriving elements index based 
		String indexElement = li.get(3);
		System.out.println("the index based elements not present: "+indexElement);
		
		for(String ele : li) {
			System.out.println(ele);
		}
	
	}
}
