package com.customStudent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StartStudent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student std1 = new Student("Sinchana", 44, 20);
		System.out.println(std1);
		Student std2 = new Student("Lavith", 20, 19);
		Student std3 = new Student("Sinchana A B", 44, 21);
		Student std4 = new Student("Nayana", 33, 22);
		Student std5 = new Student("Abhi", 8, 18);
		List<Student> students = new ArrayList<Student>();
		students.add(std1);
		students.add(std2);
		students.add(std3);
		students.add(std4);
		students.add(std5);
		System.out.println(students);
		//removing student directly
		students.remove(0);
		System.out.println(students);
		//adding new student directly
		students.add(new Student("pavan", 38, 25));
		System.out.println(students);
		///sorting the students
		
		// -> lamda function:- which doesn't have name of function, returning type, access modifier
		Collections.sort(students,(s1,s2)->s1.getRoll_num()-s2.getRoll_num());
		System.out.println("after sorted: "+students);
		Collections.sort(students, (s1,s2)->s1.getRoll_num()-s2.getRoll_num());
		System.out.println("after sorted: "+students);
		
		Collections.reverse(students);
		System.out.println("Reversing lists: "+students);
		Collections.sort(students,(s1,s2)->s1.getName().compareTo(s2.getName()));
		System.out.println("Sorting Students as per name:"+students);
	}

}
