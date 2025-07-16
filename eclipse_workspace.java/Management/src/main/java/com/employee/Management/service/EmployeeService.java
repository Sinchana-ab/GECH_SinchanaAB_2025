package com.employee.Management.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import com.employee.Management.dto.AddressDTO;
import com.employee.Management.dto.EmployeeDTO;
import com.employee.Management.model.Address;
import com.employee.Management.model.Department;
import com.employee.Management.model.Employee;
import com.employee.Management.repository.AddressRepository;
import com.employee.Management.repository.DepartmentRepository;
import com.employee.Management.repository.EmployeeRepository;

@Service
public class EmployeeService {

private final EmployeeRepository employeeRepository;
private final DepartmentRepository departmentRepository;
private final AddressRepository addressRepository;

public EmployeeService(EmployeeRepository employeeRepository,
                       DepartmentRepository departmentRepository,
                       AddressRepository addressRepository) {
    this.employeeRepository = employeeRepository;
    this.departmentRepository = departmentRepository;
    this.addressRepository = addressRepository;
}

public void saveEmployee(@Valid EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    mapDTOToEntity(employeeDTO, employee);
    employeeRepository.save(employee);
}

public void updateEmployee(@Valid EmployeeDTO employeeDTO, Long id) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    mapDTOToEntity(employeeDTO, employee);
    employeeRepository.save(employee);
}

private void mapDTOToEntity(EmployeeDTO dto, Employee employee) {
    employee.setName(dto.getName().toUpperCase());
    employee.setEmail(dto.getEmail());

    Department department = departmentRepository.findById(dto.getDepartmentId())
            .orElseThrow(() -> new RuntimeException("Department not found"));
    employee.setDepartment(department);

    if (dto.getAddress() != null) {
        AddressDTO addressDTO = dto.getAddress();
        Address address = employee.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        employee.setAddress(address);
    }
}
}