package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;

@Service
public class EmployeeService {
	
	public String createEmployee(String id, String fName, String lName) {
		Employee emp = new Employee();
		emp.setId(id);
		emp.setfName(fName);
		emp.setlName(lName);
		System.out.println("Employee object created");
		return emp.toString();
	}

	public void deleteEmployee(String empId) {
		
	}
}
