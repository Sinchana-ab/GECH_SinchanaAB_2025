//package anonymous_inner_class;
//
//public class AnnonyMousInnerClass3 implements Runnable {
//
//	@Override
//	public void run() {
//		for(int i = 0;i<= 10;i++) {
//			System.out.println("the child thread");
//		}
//	}
//	public static void main(String[] args) {
//		new AnnonyMousInnerClass3().run();
//		for(int i = 0;i<= 10;i++) {
//			System.out.println("the main thread");
//		}
//	}
//	
//}

package anonymous_inner_class;

public class AnnonyMousInnerClass3 {
	public static void main(String[] args) {
		 // Create a Thread with an anonymous Runnable
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    System.out.println("Child thread");
                }
            }
        });
        t.start();
        
        Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println("the second runnbale class");
			}
		};
		Thread t2 =new Thread(r);
		t2.start();
		
        // Main thread loop
        for (int i = 0; i <= 10; i++) {
            System.out.println("Main thread");
        }

	}
	
}

