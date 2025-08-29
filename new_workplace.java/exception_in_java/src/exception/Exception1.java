package exception;

public class Exception1 {
	/*
	 * Exception In Java: ========= there is a unwanted or unexpected event occur in
	 * the programme. when ever there is a exception the normal flow will stops.
	 * 
	 * 2 types: ====== 
	 * 1.checked exception(compile time)-FileNotFoundException: 
	 * ====
	 * 2 ways * try catch throws 
	 * 2.unchecked exception(runtime)-ArrayIndexoutofboundException 
	 * =====
	 * 1 way
	 *
	 * 
	 * Error: ======= 
	 * it is a problem when the programmer can't handle it ex: ====
	 * stack overflow error 
	 * out of memory error
	 * virtual machine error.
	 */
	private static int fact(int i) {
		return i* fact(i - 1);
	}
	public static void main(String[] args) {
		int res = fact(3);
		System.out.println(res);
	}
}
