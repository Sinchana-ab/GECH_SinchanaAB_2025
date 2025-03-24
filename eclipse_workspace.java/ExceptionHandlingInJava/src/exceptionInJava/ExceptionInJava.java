package exceptionInJava;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExceptionInJava {
	//recursion
	//stackIverflow error
	public static void display() {
		display();
	}
	public static void main(String[] args) {
		/*
		 * --Exception in java ---
		 * There is a unwanted or in-expected event occur in the program
		 * when ever there is exception the normal flow will stops
		 * 
		 * 2.types:
		 * ---------
		 * 1.checked exception(compile Time)- FileNotFoundException[2ways of]
		 * --2ways exception---
		 * Try ad catch
		 * 2. throws
		 * ---------
		 * 2. unchecked exception(run time)- arrayIndoxOUToFBound exception*/
		
		/*
		 * -------Error------
		 * it is a problem where progrmmer can't handle it explicitly
		 * */
		//ExceptionInJava.display();
		try {
			PrintWriter printWriter = new PrintWriter("abc.txt");
			printWriter.println("Hello World it gives as example for exception handling file notFoundExcepton [it is sub package of I/o bpunding] ");
			System.out.println("Hello world");
			printWriter.close();
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
	}
}
