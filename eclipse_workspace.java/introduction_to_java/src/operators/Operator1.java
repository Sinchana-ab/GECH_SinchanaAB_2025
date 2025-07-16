package operators;

public class Operator1 {
	/*
	 * Operators:
	 * ------------------------
	 * 1, Arithmentic operator:- +,-,*,/,%,
	 * 2, Assignment:- -=,+=,*=,/=,%= , =
	 * 3. Logical -> &&,||, !
	 * 4, Bit wise:- &,|,~,^,
	 * 5, Realtional operator: >,<,>=,<=,!=,==
	 * 6, unary:- ++, --
	 * 7, shift:- >>, <<
	 * 8, Ternary operator:- 
	 * */
	public static void main(String[] args) {
		System.out.println("Arithmetic operators:");
		int a = 10;
		int b =  20;
		System.out.println("Addition operator: + using "+a+"+"+b+" is: "+(a+b));
		System.out.println("Substraction operator: - using "+a+"-"+b+" is: "+(a-b));
		System.out.println("Multiplication operator: * using "+a+"*"+b+" is: "+(a*b));
		System.out.println("divition operator: / using "+a+"/"+b+" is: "+(a/b));
		System.out.println("Modules operator: % using "+a+"%"+b+" is: "+(a%b));
		
		
		/*
		 * Asignment operator
		 * */
		System.out.println("Assignment Operators");
		System.out.println("+= is "+a+" += 3 is = "+(a+=3));
		System.out.println("-= is "+a+" -= 3 is = "+(a-=3));
		System.out.println("*= is "+a+" *= 3 is = "+(a*=3));
		System.out.println("/= is "+a+" /= 3 is = "+(a/=3));
		System.out.println("%= is "+a+" %= 3 is = "+(a%=3));
		
		System.out.println("Logical operator");
		//System.out.println("using && "+a+" && "+b+"= "=(a && b));
		
	}
}
