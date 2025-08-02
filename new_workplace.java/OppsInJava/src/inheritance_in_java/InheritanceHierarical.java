package inheritance_in_java;

class GrandParent1{
	public String name = "GrandParent";
	public void GrandParent() {
		System.out.println("the Grandparent method");
	}
}

class Parents extends GrandParent1{
	public String nameclass = "Parent1";
	public void Parent1 () {
		System.out.println("the parent1 method");
	}
}

class Parents2 extends GrandParent{
	public String name = "Parent2";
	public void Parent2 () {
		System.out.println("the parent2 method");
	}
}

class son1 extends Parents2{
	public String nameclass = "son1";
	public void Son1 () {
		System.out.println("the parent1 method");
	}
}

public class InheritanceHierarical extends Parents2 {
	public static void main(String[] args) {
		String name = "second son";
		System.out.println("the heirarical class");
		System.out.println(name);
		InheritanceHierarical one = new InheritanceHierarical();
		System.out.println(one.name);
		one.Grandparent();
		one.Parent2();
		
	}
}
