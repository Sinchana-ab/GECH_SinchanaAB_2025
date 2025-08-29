package interupt;

class Display{
	public synchronized void wish(String name) {//do for synchronized
		for(int i = 0;i<10;i++) {
			System.out.println("Good morning: ");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
			System.out.println(name);
		}
	}
}

class MyThread1 extends Thread{
	Display d;
	String name;
	public MyThread1(Display d, String name) {
		
		this.d = d;
		this.name = name;
	}
	public void run() {
		d.wish(name);
	}
}

public class Synchronization {
	public static void main(String[] args) {
		Display d = new Display();
		MyThread1 t1 = new MyThread1(d, "sinchana");
		MyThread1 t2 = new MyThread1(d, "Nayana");
		t1.start();
		t2.start();
	}
}
