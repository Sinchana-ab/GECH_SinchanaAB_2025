package thread_execution_methods;

class MyThread implements Runnable{

	@Override
	public void run() {
		for(int i = 0;i< 10;i++) {
			System.out.println("child Thread name: "+(i+1));
			//Thread.yield();
			//Thread.sleep(10000);
		}
	}
	
}

public class YeildMethod {
	public static void main(String[] args) {
		MyThread myThread  = new MyThread();
		Thread t = new Thread(myThread);
		t.start();
		for(int  i = 0;i< 10;i++) {
			System.out.println("Main Thread name: "+(i+1));
		}
	}
}
