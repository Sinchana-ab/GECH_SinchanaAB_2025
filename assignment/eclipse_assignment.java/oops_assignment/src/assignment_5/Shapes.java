package assignment_5;
class Shape {
    public void calculateArea() {
        System.out.println("Area calculation is not defined");
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void calculateArea() {
        double area = Math.PI * radius * radius;
        System.out.println("Circle Area: " + area);
    }
}

class Rectangle extends Shape {
    private double length, width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public void calculateArea() {
        double area = length * width;
        System.out.println("Rectangle Area: " + area);
    }
}

public class Shapes {

	public static void main(String[] args) {
		System.out.println("\n===== Shape Test =====");
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);
        circle.calculateArea();
        rectangle.calculateArea();

	}

}
