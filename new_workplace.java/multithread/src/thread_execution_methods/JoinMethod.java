package thread_execution_methods;

class MyThraed implements  Runnable {
	public void run() {
		System.out.println(Thread.currentThread().getName()+" staterd execution");
		for(int i = 0;i< 10;i++) {
			System.out.println("child thread"+(i+1));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+" completed execution");
	}
}

public class JoinMethod {
	public static void main(String[] args) {
		Thread t = new Thread(new MyThread());
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" staterd execution");
		for(int i = 0;i< 10;i++) {
			System.out.println(" main thread"+(i+1));
		}
		System.out.println(Thread.currentThread().getName()+" completed execution");
	}
}
