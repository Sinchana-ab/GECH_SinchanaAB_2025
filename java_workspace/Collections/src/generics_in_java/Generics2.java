package generics_in_java;
class MyGenerics<T>{
	private T variable;

	public MyGenerics(T variable) {
		super();
		this.variable = variable;
	}

	public T getVariable() {
		return variable;
	}

	public void setVariable(T variable) {
		this.variable = variable;
	}

	public int size() {
				return ((MyGenerics<String>) variable).size();
	}
	
}

public class Generics2 {
	public static void main(String[] args) {
		MyGenerics<String> clas1 = new MyGenerics<>("sinchana");
		System.out.println("the variable is: "+(clas1.getVariable()));
		clas1.setVariable("nayana");
		System.out.println("the variable is: "+(clas1.getVariable()));
	//	System.out.println("the size of myclass generics = "+ clas1.size());
	}
}
