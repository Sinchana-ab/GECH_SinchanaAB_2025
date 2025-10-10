package thread_execution_methods;

class MyThraed1 implements  Runnable {
	static Thread mt;
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" staterd execution");
		try {
			mt.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0;i< 10;i++) {
			System.out.println("child thread"+(i+1));
		}
		System.out.println(Thread.currentThread().getName()+" completed execution");
	}
}

public class JoinMethod1 {
	public static void main(String[] args) {
		Thread t = new Thread(new MyThraed1());
		t.start();
		System.out.println(Thread.currentThread().getName()+" staterd execution");
		for(int i = 0;i< 10;i++) {
			System.out.println(" main thread"+(i+1));
		}
		System.out.println(Thread.currentThread().getName()+" completed execution");
	}
}
