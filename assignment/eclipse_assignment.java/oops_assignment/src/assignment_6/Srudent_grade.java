package assignment_6;
class Student {
    private String name;
    private int rollNumber;
    private double marks;

    public Student(String name, int rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public double getMarks() {
        return marks;
    }

    public boolean hasPassed() {
        return marks >= 40;
    }

    public void displayStudent() {
        System.out.println("Name: " + name + ", Roll No: " + rollNumber + ", Marks: " + marks + ", Passed: " + hasPassed());
    }
}

public class Srudent_grade {

	public static void main(String[] args) {
	
		System.out.println("\n===== Student Grade Test =====");
        Student student1 = new Student("Charlie", 101, 75);
        Student student2 = new Student("David", 102, 35);
        student1.displayStudent();
        student2.displayStudent();
	}

}
