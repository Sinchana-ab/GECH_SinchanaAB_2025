package com.employee.EmployeeCRUD.dto;

import lombok.Data;
@Data
public class EmployeeDTO {
	    private String name;
	    private String position;
	    private double salary;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public double getSalary() {
			return salary;
		}
		public void setSalary(double salary) {
			this.salary = salary;
		}

}
