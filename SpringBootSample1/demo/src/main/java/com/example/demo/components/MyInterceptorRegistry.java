package com.example.demo.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


//WebMvcConfigurerAdapter is deprecated
//WebMvcConfigurationSupport is the replacement class
@Component
public class MyInterceptorRegistry extends WebMvcConfigurationSupport{
	
	@Autowired
	private MyInterceptor myInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor);
	}

}
