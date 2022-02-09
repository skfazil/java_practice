package com.cushina.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cushina.web.bean.UserBean;

@Component
public class GuestUserValidator implements Validator {

	public boolean supports(Class<?> arg0) {
		return UserBean.class.equals(arg0);
	}

	public void validate(Object target, Errors errors) {

		UserBean bean = (UserBean) target;

		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "firstName",
						"cushina.hotel_reservation_byroom.page.validation.firstName.required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "lastName",
						"cushina.hotel_reservation_byroom.page.validation.lastName.required");
		
		ValidationUtils
		.rejectIfEmptyOrWhitespace(errors, "email",
				"cushina.hotel_reservation_byroom.page.validation.email.required");

               if ((bean.getEmail()).length() > 0) {
	                bean.setEmail((bean.getEmail().trim()));
	               Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	          Matcher m = p.matcher(bean.getEmail());
	               boolean b = m.matches();
	         if (b != true) {
	              	errors.rejectValue("email", "cushina.is.not.valid",
				"User Email is  not a well-formed email address.");
	}
}

ValidationUtils
.rejectIfEmptyOrWhitespace(errors, "phoneNumber",
		"cushina.hotel_reservation_byroom.page.validation.phoneNumber.required");

String phoneNumber = bean.getPhoneNumber();
if (phoneNumber.matches("^\\+?[0-9. ()-]{1,25}$")) {

} else {
errors.rejectValue("phoneNumber",
	"cushina.hotel_reservation_byroom.page.validation.phoneNumber");

}

		
	}
}