package inheritance_in_java;

class GrandParent{
	public String name = "Grand Parent";
	public void Grandparent() {
		System.out.println("the Grand Parent mentod ");
	}
}

class Parent1 extends GrandParent{
	public void parent() {
		System.out.println("the Parent mentod ");
	}
}
public class Multilevel extends Parent1 {
	public static void main(String[] args) {
		String name = "nayana";
		System.out.println(name);
		Multilevel m = new Multilevel();
		System.out.println(m.name);
		m.Grandparent();
		m.parent();
	}
}
