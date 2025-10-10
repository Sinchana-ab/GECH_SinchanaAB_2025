package custom_object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


class Student implements Comparable<Student>{
	private String name;
	private int age;
	private int roll;
	private String email;
	public Student(String name, int age, int roll, String email) {
		super();
		this.name = name;
		this.age = age;
		this.roll = roll;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getRoll() {
		return roll;
	}
	public void setRoll(int roll) {
		this.roll = roll;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "student [name=" + name + ", age=" + age + ", roll=" + roll + ", email=" + email + "]";
	}
	@Override
	public int compareTo(Student o1) {
		
		return this.roll - o1.roll ;
	}
	
}

class SortStudent implements Comparator<Student>{
	public int compare(Student s1, Student s2) {
		return s1.getRoll()-s2.getRoll();
	}
}

public class CustomObject {
	public static void main(String[] args) {
		Student std1 = new Student("sinchana", 10, 20, "sinchana@gmail.com");
		Student std2 = new Student("nayana", 12, 22, "nayana@gmail.com");
		Student std3 = new Student("abhi", 14, 21, "sinchanaab@gmail.com");
		Student std4 = new Student("asbs", 15, 23, "asbs@gmail.com");
		
		List<Student> students = new ArrayList<Student>();
		students.add(std1);
		students.add(std2);
		students.add(std3);
		students.add(std4);

		System.out.println("Before sorting students: ");
		students.forEach((e) -> {
			System.out.println(e);
		});
		//overriding the compare inside the collections only 
//		Collections.sort(students, new  Comparator<Student>(){
//			@Override
//			public int compare(Student s1, Student s2) {
//				return Integer.compare(s1.getRoll(), s2.getRoll());
//			}
//		});
		//after java 8 features introduced lamda funtions
		//Collections.sort(students, (s1, s2)-> s2.getRoll() - s1.getRoll());

		//Collections.sort(students, new SortStudent());
		
		Collections.sort(students);
		
		System.out.println("after sorting students: ");
		for(Student s : students) {
			System.out.println(s);
		}
		
		//System.out.println("students: "+students);
	}
}
