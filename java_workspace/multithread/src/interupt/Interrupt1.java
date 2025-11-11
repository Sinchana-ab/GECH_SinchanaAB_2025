package interupt;

class MyThread extends Thread {
	
	public void run() {
		try {
			for(int i= 1;i<5;i++) {
				System.out.println("Thread Running --- iteration "+ i);
				Thread.sleep(1000);
			}
			
		}catch (InterruptedException e) {
			System.out.println("thread interupted by parent Thread");
		}
		System.out.println("thread ends");
	}
}

public class Interrupt1 {
	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		myThread.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main thread interupt th current execution thread");
		myThread.interrupt();
	}
}
