package assignment_3;
abstract class Employee {
    String name;

    public Employee(String name) {
        this.name = name;
    }

    abstract double calculateSalary();
}

class FullTimeEmployee extends Employee {
    private double salary;

    public FullTimeEmployee(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    @Override
    double calculateSalary() {
        return salary;
    }
}

class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String name, double hourlyRate, int hoursWorked) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}
public class Employees {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("\n===== Employee Test =====");
        Employee fullTimeEmp = new FullTimeEmployee("Alice", 50000);
        Employee partTimeEmp = new PartTimeEmployee("Bob", 500, 20);
        System.out.println(fullTimeEmp.name + "'s Salary: " + fullTimeEmp.calculateSalary());
        System.out.println(partTimeEmp.name + "'s Salary: " + partTimeEmp.calculateSalary());

	}

}
