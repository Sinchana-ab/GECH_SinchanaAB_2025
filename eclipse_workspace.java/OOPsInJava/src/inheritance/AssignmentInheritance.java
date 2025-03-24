package inheritance;

interface InterfaceI1 {
    public void interfaceI1Method();
}

interface InterfaceI2 {
    public void interfaceI2Method1();
}

interface InterfaceI3 extends InterfaceI1, InterfaceI2 {
    public void interfaceI3Method1();
}

class ParentI implements InterfaceI3 {
    @Override
    public void interfaceI1Method() {
        System.out.println("Interface Method");
    }

    @Override
    public void interfaceI2Method1() {
        System.out.println("This is the second interface method");
    }

    @Override
    public void interfaceI3Method1() {
        System.out.println("This is the third interface method");
    }

    public void parentIMethod() {
        System.out.println("I am the parent method");
    }
}

class ChildI1 extends ParentI {
    public void childI1Method() {
        System.out.println("I am child1 method");
    }
}

class ChildI2 extends ParentI {
    public void childI2Method() {
        System.out.println("I am child2 method");
    }
}

public class AssignmentInheritance {
    public static void main(String[] args) {
        ChildI1 child1 = new ChildI1();
        child1.parentIMethod();
        child1.interfaceI1Method();
        child1.interfaceI2Method1();
        child1.interfaceI3Method1();
        child1.childI1Method();

        ChildI2 child2 = new ChildI2();
        child2.parentIMethod();
        child2.interfaceI1Method();
        child2.interfaceI2Method1();
        child2.interfaceI3Method1();
        child2.childI2Method();
    }
}
