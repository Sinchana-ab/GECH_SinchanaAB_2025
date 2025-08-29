package functional_interface;

import java.util.function.Consumer;

public class ConsumersInJava {
	public static void main(String[] args) {
		/*
		 * consumer
		 * ---------
		 *  * it will consume the value
		 *  * it wont return anything
		 *  
		 * */
		
		Consumer<String> con = s->System.out.println("the given String is "+s);
		con.accept("Sinchana A B");
	}
}
