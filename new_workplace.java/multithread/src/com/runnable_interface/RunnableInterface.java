package com.runnable_interface;

class ThreadClass implements Runnable{

	@Override
	public void run() {
		for(int i = 0;i<10;i++) {
			System.out.println("child Thread");
		}
	}

	public void start() {
		System.out.println("start");
		
	}
	
}

public class RunnableInterface {
	public static void main(String[] args) {
		ThreadClass t = new ThreadClass();
		Thread th = new Thread(t);
		Thread th1 = new Thread();
		th.start();
		th1.start(); //you get empty output
		t.start();//it works normally
		for(int i = 0;i<10;i++) {
			System.out.println("main Thread");
		}
	}
}
