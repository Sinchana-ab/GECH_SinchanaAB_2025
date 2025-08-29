package com.runnable_interface;

class MyThread implements Runnable{

	@Override
	public void run() {
		System.out.println("the child Thread");
		System.out.println("the child thread class is: "+Thread.currentThread().getName());
		System.out.println("the child thread class is: "+Thread.currentThread().getPriority());
	}
	
}
// .join() -> help to maintain the child priority and complete its task then main or other thread will execute 
public class ThreadPriority {
	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		Thread  t = new Thread(myThread);
		t.setPriority(9);
		//t.setPriority(Thread.MAX_PRIORITY);
		//t.setPriority(Thread.MIN_PRIORITY);
		//t.setPriority(Thread.NORM_PRIORITY);
		
		t.start();
		try {
			t.join();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("this main method run by: "+(Thread.currentThread().getName()));
		System.out.println("the main thread priority is: "+(Thread.currentThread().getPriority()));
	}
}
