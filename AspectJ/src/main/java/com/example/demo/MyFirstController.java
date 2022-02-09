package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EmployeeService;

@RestController
public class MyFirstController {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/test")
	public String sampleGet() {
		return "Hello World";
	}
	
	@GetMapping("/test/addEmployee")
	public String addEmployee(@RequestParam("empId") String empId, @RequestParam("firstName") String firstName,
			@RequestParam("secondName") String secondName) {
		System.out.println("DATA RECEIVED :: id "+empId+" firstName "+firstName+" secondName "+secondName);
		return employeeService.createEmployee(empId, firstName, secondName);
	}

}
