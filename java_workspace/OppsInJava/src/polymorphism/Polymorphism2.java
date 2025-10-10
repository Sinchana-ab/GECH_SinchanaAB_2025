package polymorphism;

class Shape{
	public void area() {
		System.out.println("calculating area: ");
	}
}

class Circle extends Shape{
	private int r;

	public Circle(int r) {
		super();
		this.r = r;
	}
//upcasting comes in ploymorphism
	@Override
	public void area() {
		double area = 3.14*this.r*r;
		System.out.println("the calculated area is "+area);
	}	
}

class Rectangle extends Shape{
	private int width;
	private int length;
	
	public Rectangle(int width, int length) {
		super();
		this.width = width;
		this.length = length;
	}

	@Override
	public void area() {
		double area = length*width;
		System.out.println("the area of rectangle is: "+area);
	}
	
}

public class Polymorphism2 {
	public static void main(String[] args) {
		Shape c = new Circle(5);
		c.area();
		Shape r = new Rectangle(20, 40);
		r.area();
	}
}
