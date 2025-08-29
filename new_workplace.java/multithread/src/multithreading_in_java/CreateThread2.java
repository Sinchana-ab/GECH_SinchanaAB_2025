package multithreading_in_java;

class ParentThread extends Thread{

	@Override
	public void start() {
		super.start();
		System.out.println("start method");
	}

	@Override
	public void run() {
		for(int i = 0;i< 10; i++) {
			System.out.println("child method");
		}
	}
	
}

public class CreateThread2 {
	public static void main(String[] args) {
		ParentThread t1 = new ParentThread();
		t1.start();
		System.out.println("Main Thread");
		for(int i = 0;i< 10;i++) {
			System.out.println("the main thread"+(i+1));
		}
	}
}
