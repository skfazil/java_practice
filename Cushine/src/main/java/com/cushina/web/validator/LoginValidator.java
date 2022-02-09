package com.cushina.web.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cushina.web.bean.UserBean;


@Component
public class LoginValidator implements Validator{
	
	public boolean supports(Class<?> arg0) {
		
		return UserBean.class.equals(arg0);
	}
	
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "email",
				"cushina.login.page.validation.email.required");
		ValidationUtils.rejectIfEmpty(errors, "password",
				"cushina.login.page.validation.password.required");
		
	}

}
