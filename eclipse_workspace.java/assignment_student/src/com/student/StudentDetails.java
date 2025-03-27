package com.student;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Student{
	public int id ;
	public String name ;
	public int age;
	public int marks;
	public Student(int id, String name, int age, int marks) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.marks = marks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", marks=" + marks + "]";
	}
	
}

public class StudentDetails {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Student> students = Arrays.asList(new Student(1,"Sinchana",20,89),
				new Student(2,"Sinchana a",22,90),
				new Student(3,"Sinchana B",21,55),
				new Student(4,"Sinchana A B",22,76)
				);
		System.out.println("Student list: "+students);
		List<Student> filterStudent = students.stream().filter(s->s.getMarks()>60).collect(Collectors.toList());
		System.out.println("the marks 60 above students: "+filterStudent);

	}

}
