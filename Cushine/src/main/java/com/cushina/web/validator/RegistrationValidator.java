package com.cushina.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cushina.web.bean.UserBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegistrationValidator implements Validator {

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
				.rejectIfEmptyOrWhitespace(errors, "userName",
						"cushina.hotel_reservation_byroom.page.validation.userName.required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "password",
						"cushina.hotel_reservation_byroom.page.validation.password.required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "confirmPassWord",
						"cushina.hotel_reservation_byroom.page.validation.confirmPassWord.required");
		if (!bean.getPassword().equals(bean.getConfirmPassWord())) {
			errors.rejectValue(
					"confirmPassWord",
					"cushina.hotel_reservation_byroom.page.validation.confirmPassWord.required",
					"ConfirmPassword must be same as Password");
		}

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
				.rejectIfEmptyOrWhitespace(errors, "city",
						"cushina.hotel_reservation_byroom.page.validation.city.required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "country",
						"cushina.hotel_reservation_byroom.page.validation.country.required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "streetName",
						"cushina.hotel_reservation_byroom.page.validation.streetName.required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "postalCode",
						"cushina.hotel_reservation_byroom.page.validation.postalCode.required");
		String postalCode = bean.getPostalCode();
		if (postalCode.matches("^\\+?[0-9. ()-]{1,25}$")) {

		} else {
			errors.rejectValue("postalCode",
					"cushina.hotel_reservation_byroom.page.validation.postalCode");
		}
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "title",
						"cushina.hotel_reservation_byroom.page.validation.title.required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "language",
						"cushina.hotel_reservation_byroom.page.validation.language.required");

		if (bean.getLanguage().contains("Select")) {
			errors.rejectValue("language", "language.select",
					"Please Select  language");
		}
		
		ValidationUtils
				.rejectIfEmptyOrWhitespace(
						errors,
						"notificationDuration",
						"cushina.hotel_reservation_byroom.page.validation.notificationDuration.required");

		if (bean.getNotificationDuration().contains("Notification")) {
			errors.rejectValue("notificationDuration", "notificationDuration.select",
					"Please Select Notification Period");
		}


		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "dob",
						"cushina.hotel_reservation_byroom.page.validation.dob.required");

		if (!bean
				.getDob()
				.matches(
						"^(?<month>[0-3]?[0-9])/(?<day>[0-3]?[0-9])/(?<year>(?:[0-9]{2})?[0-9]{2})$")) {
			errors.rejectValue("dob", "field.dob.NAN");

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
