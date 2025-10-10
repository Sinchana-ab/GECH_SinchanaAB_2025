package thread_execution_methods;



class MyThraed3 implements  Runnable{
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" staterd execution");
		for(int i = 0;i< 10;i++) {
			System.out.println("child thread"+(i+1));
		}
		System.out.println(Thread.currentThread().getName()+" completed execution");
	}
}
	
public class SleepMethod {
	public static void main(String[] args) {
		Thread t = new Thread(new MyThraed1());
		t.start();
		try {
			t.sleep(1000);
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

