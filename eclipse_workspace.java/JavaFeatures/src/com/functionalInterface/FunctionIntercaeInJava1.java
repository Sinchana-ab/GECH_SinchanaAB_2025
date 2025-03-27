package com.functionalInterface;

@FunctionalInterface
interface Interface1{
	public void display();
}
@FunctionalInterface
interface Interface2{
	public int string(String s);
}

@FunctionalInterface
interface Interface3{
	public int add(int a, int b);
}
interface Interface4{
	public void add(int a, int b);
}

public class FunctionIntercaeInJava1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Interface1 i = ()->System.out.println("Hello World");
		i.display();
		System.out.println(i.equals(i));
		Interface2 i2 = s -> s.length();
		i2.string("hello");
		Interface3 i3 = (int a, int b) ->{ return(a+b);};
		int res = i3.add(2,3);
		System.out.println("addition is: "+res);
		
		Interface4 i4 = (int a, int b) ->{ System.out.println("addtion is: "+(a+b));};
		i4.add(2, 8);
	}

}
