package method_and_constructor_reference;

import java.util.function.Function;
import java.util.function.Supplier;

class Student{

	public Student() {
		System.out.println("this student constructor");
	}
	
}

class Student1{
	public String name;

	public Student1(String name) {
		this.name = name;
		System.out.println("the name is "+name);
	}
	
}

public class ConstructorReference {
	public static void main(String[] args) {
		//without
		Supplier<Student> sup = ()->new Student();
		sup.get();
		Supplier<Student> sup1 = Student::new;
		sup1.get();
		//Student1 s = new Student1("sinchana");
		Function<String, Student1> fun = Student1::new;
		fun.apply("sinchan");
	}
}
