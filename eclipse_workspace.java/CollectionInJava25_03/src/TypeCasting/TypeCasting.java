package TypeCasting;

public class TypeCasting {

	public static void main(String[] args) {
		/*
		 * Type casting
		 * =============
		 * conversion of one data type to another
		 * 2types
		 * ===========
		 * 1.implicit type casting
		 * byte->short->int->long->float->double
		 * 
		 * 2..explicit type casting
		 * ======================
		 * double->float->long->int->short->byte
		 * 
		 * 
		 * */
		//1.implicit type casting
		int a=10;
		long a1=a;
		System.out.println("Int:"+a);
		System.out.println("long:"+a1);
		
		short var3=50;
		double a6=var3;
		System.out.println("short:"+var3);
		System.out.println("double:"+a6);
		
		
		//2.explicit type casting
		long a2=50;
		int a3=(int)a2;
		System.out.println("int:"+a3);
		System.out.println("long:"+a2);
		
		double var2=40;
		byte a5=(byte)var2;
		System.out.println("double:"+var2);
		System.out.println("byte:"+a5);
		
	}

}