package exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReadingThroughTry {
	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
		
		catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
		catch(ArithmeticException e){
			System.out.println(e.getMessage());
		}
	
}
}