package inheritance;
class Employee {
    String name;
    double salary;
    String department;

    public Employee(String name, double salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public void displayDetails() {
        System.out.println("Name: " + name + ", Salary: " + salary + ", Department: " + department);
    }
}

class Manager extends Employee {
    int teamSize;

    public Manager(String name, double salary, String department, int teamSize) {
        super(name, salary, department);
        this.teamSize = teamSize;
    }

    public void displayTeamSize() {
        System.out.println("Team Size: " + teamSize);
    }
}
public class Company {
	public static void main(String[] args) {
		Manager m = new Manager("sinchana", 50000, "development", 5);
		m.displayDetails();
		m.displayTeamSize();
	}
}
