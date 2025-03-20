package functionInJava.functions;

public class FunctionInjava {
	public int add(int a, int b) {
		return  a+b;
	}
	public void add1() {
		System.out.println("Sinchan" + "A B");
	}
	public int add3() {
		return 2+3;
	}
	public void add4(int m, int n) {
		System.out.println("addition: "+(m+n));
	}
	public static void main(String[] args) {
		/*
		 * Function/method in java
		 * =========
		 * <access_modifier> return_type function-name(parameter){
		 * code
		 * }
		 * public void add(int a, int b){
		 * syso(a+b)
		 * }
		 * ===================
		 * two types of function
		 * 1. built-in function:- next(),nextInt(),println(),equals
		 * 2. userdefined:- defined by user
		 * ==============
		 * 4types 
		 * 1.returning value and not accepting value
		 * 2.not returning value and not accepting value
		 * 3.returning value and not accepting value
		 * 4.not returning value and accepting value
		 * */
		FunctionInjava obj1= new FunctionInjava();
		System.out.println("Function accepting and returning value");
		System.out.println(obj1.add(8, 9));
		System.out.println("Function not accepting and not returing the value");
		obj1.add1();
		System.out.println("Function not accepting and returing the value");
		System.out.println(obj1.add3());
		System.out.println("Function accepting and not returing the value");
		obj1.add4(2, 4);
		FunctionsInjava1 obj2 = new FunctionsInjava1();
		obj2.defaultfunction();
		obj2.publicFunction();
		obj2.staticfunction();
		obj2.protectedFunction();
		FunctionsInjava1.staticfunction();
//		obj2.privateFunction();
		
		
		
	}
}
