package exception;

public class FinallyBlock {
	public static void main(String[] args) {
		// finally
				/*
				 * it will execute even if the exception occur or not what are the clean up
				 * resources(ex:-database connection,file close)
				 *
				 * in which scenario,finally will not execute 1.System.exit() 2.when the
				 * exception occur in the finally block
				 */
//				try {
//					System.out.println(10/0);
//				}finally {
//					System.out.println("this is a finally block");
//				}
				try {
					System.out.println(10 / 2);
				} finally {
					System.out.println("this is a finally block");
				}
				try {
					System.out.println(10 / 0);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.exit(0);
				} finally {
					// System.out.println(10/0);
					System.out.println("this is a finally block");
				}
	}
}
