package multithreading_in_java;

class Mythread extends Thread{
	@Override
	public void run() {
		for(int i = 0;i<10;i++) {
			System.out.println("the child thread: "+(i+1));
		}
	}
}

public class CreatingThread {
	public static void main(String[] args) {
		Mythread thread1 = new Mythread();
		thread1.start();
		//here output can't be predicted 
		//start():- create new child thread that will execute the run() method
		// run() :- is the normal instance method belongs thread class so to run we can create new 
		// thread or by normally creating object then we access run method then main thread will handle 
		thread1.run();// these will execute by main thread not child thread
		//execution wont be change 
		for(int i =0;i<10;i++) {
			System.out.println("Main Thread "+(i+1));
		}
		// now the execution in order the main method first method declare in child thread ie run() method
	}
}
