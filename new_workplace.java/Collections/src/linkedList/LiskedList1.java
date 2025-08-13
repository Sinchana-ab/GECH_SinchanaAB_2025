package linkedList;

import java.util.LinkedList;

/*
 * Java Linked list class allows duplicate elements
 * it contains the insertion order
 * linked list class implements Queue and deque interfacex, 
 * it can also be used as a queue, deque or stack
 * is not thread safe , you must explicitly synchronize 
 * concurrent modifications to the linkedList in a multi-threaded
 * (collections utility method provide staic method synchronuous the class, ex:- SynchrounizeList method )
 * */

/*
 * Difference blw the ArrayList and LinkedList
 * ArrayList:- 1. it internally used array used stor the data(index based access)
 * 				2. insertion and deletions are not fast compare with linkedList(shifting), 
 * 				3. consume less memory	
 * 
 * LinkedList:- 1. which internally uses doublylinkedlist to store the data elements
 * it holds the collection of nodes, 
 * insertion and deletion is easy 
 * consume more memenory(internallu store doublyLinkedList 
 *  */
public class LiskedList1 {
	public static void main(String[] args) {
		//add() method
		//add(2, element)
		// addFirst()
		//addLast()
		
		LinkedList<String> fruit = new LinkedList<String>();
		
		// adding the elements
		fruit.add("banana");
		fruit.add("Apple");
		fruit.add("mangoo");
		fruit.add("Apple");
		
		//adding an element at specified position
		fruit.add(2, "watermeleon");
		System.out.println(fruit);
		
		//adding first element
		fruit.addFirst("orange");
		System.out.println(fruit);
		
		//adding elements at end of list
		fruit.addLast("jackfruit");

		System.out.println(fruit);

	}

}
