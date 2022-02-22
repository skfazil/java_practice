package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {
	
	/*1. Extend SpringBootServletInitiazer, and have configure method return with set sources
	2. Add "<start-class>com.example.demo.DemoApplication</start-class>" in the properties tag in pom
	3. Change packaging in pom from jar to war
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
