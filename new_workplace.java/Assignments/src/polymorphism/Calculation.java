package polymorphism;
abstract class Shape {
    public abstract double area();
}

class Circle extends Shape {
    double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double area() {
        return length * width;
    }
}
public class Calculation {
	public static void main(String[] args) {
		Shape c = new Circle(5);
		System.out.println(c.area());
		Shape r = new Rectangle(30, 40);
		System.out.println(r.area());
	}
}
