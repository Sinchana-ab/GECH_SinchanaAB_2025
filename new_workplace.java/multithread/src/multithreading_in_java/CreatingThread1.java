package multithreading_in_java;


class MyThread1 extends Thread{

	@Override
	public void run() {
		for(int i=0;i<10;i++) {
			System.out.println("Child Thread: "+(i+1));
		}
	}
	public void run(int k) {
		for(int i=0;i<10;i++) {
			System.out.println("Child Thread: "+(i+1));
		}
	}
	public void run(String s) {
		for(int i=0;i<10;i++) {
			System.out.println("Child Thread: "+(i+1));
		}
	}
	
}

public class CreatingThread1 {
	public static void main(String[] args) {
		MyThread1 t1 = new MyThread1();
		t1.start(); //it will create a new thread
//		t1.run(); // it won't create a new thread
		t1.run("mohan");
		for(int i=0;i<10;i++) {
			System.out.println("Main Thread: "+(i+1));
		}
	}
	public static void main(int[] args) {
		MyThread1 t1 = new MyThread1();
		t1.start(); //it will create a new thread
//		t1.run(); // it won't create a new thread
		t1.run("mohan");
		for(int i=0;i<10;i++) {
			System.out.println("Main Thread: "+(i+1));
		}
	}
}	
