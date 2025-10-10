package exception;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class CheckedException {
	/*Checke exception/ compile time
	 * --------------------------------
	 * 1.filenNotFound exception
	 * ---------------------------
	 * to handle any files in java :- io class
	 * it inside io exceptions 
	 * 
	 * -----------------------------------
	 * 2 ways of handle the exception
	 * 		i) using try catch block
	 * 		ii) using throws
	 * 
	 * */
	
	public static void main(String[] args) throws FileNotFoundException {
		try {
			PrintWriter p = new PrintWriter("abc.txt");
			p.println("hello world");
			System.out.println("hello world");
			//System.out.println(Arrays.toString(p));
			p.close();
		}catch(FileNotFoundException e){
			// to print the exceptions
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e);
		}
		
		PrintWriter pw = new PrintWriter("abc.txt");
		pw.println("hello world");
		pw.println("1234543");
		System.out.println("hello world");
		pw.close();
	}
}
