package com.functionalInterface;
@FunctionalInterface
interface Interface{
	public void display();
}

class Display implements Interface {

	@Override
	public void display() {
		System.out.println("Hello World");
		
	}
	
}

public class FunctionInterfaceInJava1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = new Display();
		display.display();

	}

}
