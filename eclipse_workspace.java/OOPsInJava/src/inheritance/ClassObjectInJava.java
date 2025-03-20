package inheritance;
/////by default can we decleare the class with accesifier only *public* and *default* 
//abstract and final keyword for the class name
class Student{
	//states
	public int age =20;///instance within class which  are declered hrough constactor 
	public String name = "sinchana";
	public double marks = 230.42;
	// action
	public void isplaying() {
		System.out.println(this.name+" is playing");
	}
	public void isSleeping() {
		System.out.println(this.name+" is sleeping");
	}
	
}

public class ClassObjectInJava {
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		/*
		 * Class : > classs will represent the state and behaviour of the object
		 * state
		 * behaviour
		 * 
		 * ==============
		 * Object :-> Object is a implementation of the class(state and behavior)
		 * ======
		 * */
		/*
		 * class:
		 * =====
		 * Class represent the state and behavioure of an object
		 * state -> property
		 * beha -> action
		 * 
		 * Object:
		 * =======
		 * object is a implementation of class(state and action)
		 * ex:
		 * ======
		 * Student
		 * 
		 * property:
		 * ======
		 * USN
		 * BRANCH
		 * NAME
		 * AGE
		 * 
		 * action
		 * =======
		 * isplaying()
		 * idsleeping()
		 * 
		 * how to declare class and object
		 * ==============================
		 * class class_name{
		 * State
		 * action
		 * {
		 * ex:
		 * ====
		 * clas Student{
		 * property:
		 * public int age=20;
		 * 
		 * action:
		 * public void dispalying(){
		 * syso("the window is dispalying")
		 * 		 */
		 Student std1 = new Student();
		 System.out.println("The student1 age is "+std1.age);
		 System.out.println("The student name is "+std1.name);
		 System.out.println(std1.marks);
		 std1.isplaying();
		 std1.isSleeping();
		 Student std2 = new Student();
		 System.out.println("The student1 age is "+std2.age);
		 System.out.println("The student name is "+std2.name);
		 System.out.println(std2.marks);
		 std2.isplaying();
		 std2.isSleeping();
	}

}
