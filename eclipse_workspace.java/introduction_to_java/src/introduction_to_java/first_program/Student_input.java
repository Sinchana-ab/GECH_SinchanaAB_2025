package introduction_to_java.first_program;

import java.util.Scanner;

public class Student_input {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter student details");
		System.out.println("name: ");
		String name = sc.nextLine();
		System.out.println("age");
		byte age = sc.nextByte();
		sc.nextLine();
		System.out.println("gender");
		char gender = sc.nextLine().charAt(0);
		System.out.println("enter sub1 sub2 marks");
		double sub1 = sc.nextDouble();
		double sub2 = sc.nextDouble();
		sc.nextLine();
		System.out.println("Branch");
		String branch = sc.nextLine();
		System.out.println("address");
		String address = sc.nextLine();
		System.out.println("Student details: ");
		System.out.println(" name: "+name+ "\n age: "+age+"\n gender: "+gender+"\n sub1 marks: "+sub1+"\nsub2 marks: "+sub2+"\n branch:"+branch+"\n address: "+address);
	}

}
