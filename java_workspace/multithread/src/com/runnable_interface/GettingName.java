package com.runnable_interface;

class NameThread implements Runnable{

	@Override
	public void run() {
		System.out.println("child thread: "+(Thread.currentThread().getName()));
		System.out.println("child thread priority is: "+(Thread.currentThread().getPriority()));
	}
	
}

/*
 * 1.runnable interface contain only run method(functional interface)
 *   so by implementing we can override the method
 * 2. if we want to execute the run method we want start method but it present in thread class
 *    so creating an object of the particular thread then passing impleemnting class object we can run method inside that
 * 3. we can set name or get name of the particular thread
 * 	  Thread.currentThread().getName()
 * 	  Thread.currentThread().setName() */

public class GettingName {
	public static void main(String[] args) {
		NameThread nameThread = new NameThread();
		Thread t = new Thread(nameThread);
		t.setName("child Thread1");
		t.start();
		Thread t1 = new Thread(nameThread);
		t1.start();
		Thread t2 = new Thread(nameThread);
		t2.start();
		System.out.println("this main method run by: "+(Thread.currentThread().getName()));
		Thread.currentThread().setName("hello main");
		//System.out.println(10/0);
		System.out.println("the main thread priority is: "+(Thread.currentThread().getPriority()));
	}
}
