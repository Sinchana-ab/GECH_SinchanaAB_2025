import student.Student;

public class App {
    public static void main(String[] args) throws Exception {
        Student std1 = new Student(21, "Priya");
        System.out.println("The std1 details are\n");
        System.out.println("id: " +std1.stdID);
        System.out.println("Name: " +std1.stdName);
    }
}

