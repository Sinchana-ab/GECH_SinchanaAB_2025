package collections_in_java;

import java.util.Arrays;

class Student{
	public String name;
	public int age;
	public double marks;
	public Student(String name, int age, double marks) {
		this.name = name;
		this.age = age;
		this.marks = marks;
	}
	public Student() {
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", marks=" + marks + "]";
	}
	
	
}

public class Collection1 {
	public static void main(String[] args) {
		int[] arr = new int[5];
	// type safety
		arr[0] = 20;
		arr[1] = 30;
	//	arr[1] = "hii";
		System.out.println(Arrays.toString(arr));
	// type casting 
		int value = arr[0];
		System.out.println("value is: "+value);
		
		
		Object[] obj = new Object[6];
		obj[0] = "sinchana";
		obj[1] = 123;
		obj[2] = 12.25;
		obj[3] = true;
		
		 obj[4] = new Student("sinchana", 20, 200.34);
		Student std = new Student("nayana", 20, 300.32);
		obj[5] = std;
		Student s1 = (Student) obj[5];
		System.out.println(obj[5]);
		//in object explicitly type to access the element
		String name = (String) obj[0];
		System.out.println(name);
		
		System.out.println(obj[4]);
		
		System.out.println(Arrays.toString(obj));
	}
}
