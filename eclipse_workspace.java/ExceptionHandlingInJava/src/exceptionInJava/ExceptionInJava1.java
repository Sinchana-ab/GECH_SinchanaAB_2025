package exceptionInJava;

public class ExceptionInJava1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//unchecked exception
		
		  int a =10;
		  int b =0;
		  try {
			  System.out.println("result is :"+(a/b));
			
		} catch (ArithmeticException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}

	}

}