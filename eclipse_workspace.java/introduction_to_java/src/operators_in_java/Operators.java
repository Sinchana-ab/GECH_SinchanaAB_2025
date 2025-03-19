package operators_in_java;

public class Operators {
	
/*
Types of Operators in Java
Arithmetic Operators
Unary Operators
Assignment Operator
Relational Operators
Logical Operators
Ternary Operator
Bitwise Operators
Shift Operators
instance of operator
*/
	public static void main(String[] args) {
		System.out.println("Arithmetic operators");
		int a = 10;
        int b = 3;
      
        // Arithmetic operators on Strings
        String n1 = "15";
        String n2 = "25";

        // Convert Strings to integers
        int a1 = Integer.parseInt(n1);
        int b1 = Integer.parseInt(n2);
           
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));
        System.out.println("a % b = " + (a % b));
        System.out.println("n1 + n2 = " + (n1 + n2));
        System.out.println("a1 + b1 = " + (a1 + b1)); 
        
        System.out.println("Unary operators");
      
        // Using unary operators
        System.out.println("Postincrement : " + (a++));
        System.out.println("Preincrement : " + (++a));

        System.out.println("Postdecrement : " + (b--));
        System.out.println("Predecrement : " + (--b));
		
	}
}
