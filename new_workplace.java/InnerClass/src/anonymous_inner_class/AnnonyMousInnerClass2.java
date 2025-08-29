package anonymous_inner_class;

import java.util.Iterator;

public class AnnonyMousInnerClass2 {
	
	/// here we can't predicated out put, it will changes
	public static void main(String[] args) {
		Thread th = new Thread() {
			@Override
			public void run() {
				for(int i = 0;i<= 10;i++) {
					System.out.println("the child thread");
				}
			}
		};
		th.start();
		for(int i = 0;i<= 10;i++) {
			System.out.println("the main thread");
		}
	}
}
