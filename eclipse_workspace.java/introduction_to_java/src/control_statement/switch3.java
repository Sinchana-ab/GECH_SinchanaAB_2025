package control_statement;

public class switch3 {
	    public static void main(String[] args) {
//	        Object obj1 = "Hello, world!"; 
//	    	Object obj1 = 30; 
	    	Object obj1 = 30.23;

	        switch (obj1) {
	            case String s -> System.out.println("The object is a String: " + s);
	            case Integer i -> System.out.println("The object is an Integer: " + i);
	            case Double d -> System.out.println("The object is a Double: " + d);
	            default -> System.out.println("The object is of an unknown type.");
	        }
	}

}
