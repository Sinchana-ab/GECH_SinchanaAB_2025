package Array2D_in_java;

import java.util.Scanner;

class Student{
	public String name;
	public int roll;
	public Student(String name, int roll) {
		this.name = name;
		this.roll = roll;
	}
	public Student() {
		// TODO Auto-generated constructor stub
	}
	
}
public class ArrayObjects {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the size of array student: ");
		int n = sc.nextInt();
		Student[] student = new Student[n];
		for(int i = 0;i<n;i++) {
			System.out.println("Enter the "+(i+1)+" student: details");
			student[i] = new Student();
			System.out.println("name: ");
			student[i].name = sc.next();
			sc.nextLine();
			System.out.println("roll: ");
			student[i].roll = sc.nextInt();
		}
		for(int i = 0;i<n;i++) {
			System.out.println((i+1)+"nd Student name is: "+student[i].name + " and roll is "+student[i].roll);
		}
		sc.close();
	}
}
