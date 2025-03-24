package inheritance;

interface InterfaceI11{
	public void interfaceI1Method();
}
interface InterfaceI12{
	public void interfaceI2Method();
}
interface InterfaceI13 extends InterfaceI1, InterfaceI2{
	public void interfaceI3Method();
}

class ChildI11{
	public void childI1Method() {
		System.out.println("iam child1 method");
	}
}
class ChildI12{
	public void childI2Method() {
		System.out.println("iam child2 method");
	}
}
class ParentI1 implements InterfaceI13, extends ChildI11,ChildI12{
	 @Override
	 public void interfaceI1Method() {
		 System.out.println("Interface Method");
	 }
	 @Override
	 public void interfaceI2Method() {
		 System.out.println("THis second interface method");
	 }
	 @Override
	 public void interfaceI3Method() {
		 System.out.println("THis is third interface method");
	 }
	 
	 public void parentImethod() {
		System.out.println("i am parent method");
	}
	
}

public class Assignment2{
	 public static void main(String[] args) {
	        ParentI parent = new ParentI();
	        parent.childI1Method();
	        parent.interfaceI1Method();
	        parent.interfaceI2Method();
	        parent.interfaceI3Method();
	        parent.parentIMethod();
	    }
}
