package exceptionInJava;

public class ExceptionInJava2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * try with multiple catches
		 * go from parent to child not from parent to child
		 */
		int [] arr = new int[2];
		arr[0]=80;
		arr[1]=30;
		
		try {
		//	System.out.println(arr[10]);
			System.out.println(10/0);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e);
			// TODO: handle exception
		} catch(ArithmeticException e) {
			System.out.println(e);
		}
		System.out.println("hello word");
		
	}

}