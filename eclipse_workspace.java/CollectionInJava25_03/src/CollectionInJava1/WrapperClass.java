package CollectionInJava1;

public class WrapperClass {
	public static void main(String[] args) {
		/*
		 * java is a pure oop or not
		 * ======================================
		 * no(primitive data types
			
		 * primitive data types:
		 * ==========================
		 * byte,short,int,long,float,double,char,boolean
		 * 
		 * string name=new string("munazza");
		 * 
		 * collection
		 * ================
		 * to store group of individual objects as a single entity===string is an object
		 * 10,20,30,40----------------integer values
		 * 
		 * Wrapper Classes:
		 * =====================================
		 * byte → Byte
			short → Short
			int → Integer
			long → Long
			float → Float
			double → Double
			char → Character
			boolean->Boolean

		 * example: new Byte(20);
		 * 
		 * 
		 *1.auto-boxing-->form primitive to wrapper
		 * */
		//1.auto-boxing-->form primitive to wrapper	
			int a=100;
			Integer i=Integer.valueOf(a);
			//Integer i=a;//default conversion
			System.out.println("int:"+a);
			System.out.println("integer:"+i);
			System.out.println("integer type:"+i.getClass().getName());
		
		
			//1.
			byte b = 10;
	        Byte byteObj = Byte.valueOf(b);  // Converting byte to Byte (Wrapper Class)
	        System.out.println("Byte Value: " + byteObj);
	        System.out.println("byte type:"+byteObj.getClass().getName());
	        
	        //2.
	        short a9=90;
	        Short shortObj = Short.valueOf(a9);
	        System.out.println(+shortObj);
	        System.out.println("short type:"+shortObj.getClass().getName());
	        
	        long a8=90;
	        Long longObj1 = Long.valueOf(a8);
			System.out.println(+longObj1);
	        System.out.println("short type:"+longObj1.getClass().getName());
	        
	        //2.unboxing---from object to primitive
	        Integer i1=10;
	        int a1=i1.intValue();
	        //int a1=i1;
	        System.out.println("int:"+a1);
			System.out.println("integer:"+i1);
			System.out.println("integer type:"+i1.getClass().getName());
			
			Byte b1 = 10;
	        byte byteObj1 = b1.byteValue();  
	        System.out.println("Byte Value: " + byteObj1);
	        System.out.println("byte type:"+b1.getClass().getName());
	        
	        Short b2 = 10;
	        short byteObj2 = b2.shortValue();  
	        System.out.println("short Value: " + byteObj2);
	        System.out.println("short type:"+b2.getClass().getName());
	        
	        Long b3= (long) 100;
	        long byteObj3 = b3.longValue();  
	        System.out.println("long Value: " + byteObj3);
	        System.out.println("long type:"+b3.getClass().getName());
	        
	        Character b4 = 'a';
	        char byteObj4 = b4.charValue();  
	        System.out.println("char Value: " + byteObj4);
	        System.out.println("char type:"+b4.getClass().getName());
	}

}