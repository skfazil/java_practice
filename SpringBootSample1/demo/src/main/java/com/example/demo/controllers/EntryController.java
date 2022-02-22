package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UserException;
import com.example.demo.service.EntryService;

@RestController
public class EntryController {
	
	@Autowired
	EntryService entryService;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@RequestMapping("/hello")
	public String sayHello() {
		System.out.println("Saying Hello");
		return "Hello Fazil, Welcome to "+applicationName;
	}
	
	@RequestMapping("/mocktest")
	public String mockTest() {
		return entryService.getName();
	}
	
	@RequestMapping("/globalexceptiontest")
	public String exceptionTest() throws UserException {
		System.out.println("Saying Hello");
		throw new UserException("Global exception Test");
		//return "Hello Fazil, Welcome to "+applicationName;
	}

}
