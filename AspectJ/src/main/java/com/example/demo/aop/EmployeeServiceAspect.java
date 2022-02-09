package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeServiceAspect {

	@Around(value = "execution(* com.example.demo.service.EmployeeService.*(..)) and args(id, fName, lName)")
	public void beforeAdvice(JoinPoint joinPoint, String id, String fName, String lName) {
		System.out.println(
				"Advice execute for Employee with first name - " + fName + ", last name - " + lName + " and id - " + id);
	}
}
