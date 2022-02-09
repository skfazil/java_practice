package com.cushina.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.exception.EmailNotUniqueException;
import com.cushina.common.exception.UserNotUniqueException;
import com.cushina.common.util.CushinaUtil;
import com.cushina.common.util.MailUtil;
import com.cushina.common.util.PropUtil;
import com.cushina.service.PickHotelService;
import com.cushina.service.UserService;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.GuestUserBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;
import com.cushina.web.validator.GuestUserValidator;
import com.cushina.web.validator.LoginValidator;
import com.cushina.web.validator.RegistrationValidator;

@Controller
@SessionAttributes(value = { "userName", "hotel", "userId", "hotelDetails",
		"currentDate", "quickUserDetails" })
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	private RegistrationValidator validator;

	@Autowired
	private LoginValidator validators;

	@Autowired
	private PickHotelService service;

	@Autowired
	private GuestUserValidator guestvalidator;

	/**
	 * get login page
	 * 
	 * @param model
	 * @return
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd/MM/yyyy"), true));
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String getLogin(Model model, HttpSession session) {
		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("loginsuccess", "login is successfull");
		model.addAttribute("loginDetails", "login popup show");
		return "hotel_reservation";
	}

	@RequestMapping(value = "/servicelogin.htm", method = RequestMethod.GET)
	public String getServiceLogin(Model model, HttpSession session) {
		UserBean userBean = new UserBean();
		model.addAttribute("getSPUser", userBean);
		return "serviceprovider_view";
	}

	/**
	 * 
	 * This method is used to save user and send confirmation mail to intended
	 * recipient.
	 * 
	 * @param model
	 * @param userBean
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public String saveUserDetails(Model model,
			@ModelAttribute("registerUser") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("saveUserDetails controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		String emailTemplate = "/letterTemplate/letterTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String contentPath = request.getContextPath();
		validator.validate(userBean, errors);
		String token = CushinaUtil.generateRandomPassword();

		if (errors.hasErrors()) {
			logger.info("errors :" + errors);
			model.addAttribute("errors", "Please enter all the fields");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("saveUserDetails controller : end");
			return "hotel_reservation";
		}
		UserDTO userDTO = new UserDTO();
		userBean.setJoinDate(new Date());
		userBean.setToken(token);
		userBean.setStatus("InActive");
		if (userBean.getIsSP().equals("N")) {
			userBean.setIsServiceProvider(false);
		}
		userBean.setEmail(userBean.getEmail().toLowerCase());
		BeanUtils.copyProperties(userBean, userDTO);
		HotelBean bean = (HotelBean) session.getAttribute("hotel");
		HotelDTO hotelDTO = new HotelDTO();
		BeanUtils.copyProperties(bean, hotelDTO);
		String message = null;
		try {
			boolean saveUser = userService.saveUser(userDTO);
			if (saveUser) {
				logger.info("useDeatils saved successfully and userName  is :"
						+ userDTO.getUserName());
				// set userName in session.
				session.setAttribute("userName", userDTO.getUserName());

				String confirmationUrl = "http://" + request.getServerName()
						+ ":" + request.getServerPort() + contentPath + "/"
						+ "userRegstrnConfirmtn.htm?token=" + token;
				logger.info("sendMail method : start");
				boolean sendMail = MailUtil
						.sendMail(hotelDTO, userDTO,
								servletContext.getRealPath(emailTemplate),
								confirmationUrl, "Your new password" + "tocken"
										+ token);
				logger.info("sendMail method : end");
				model.addAttribute(
						"signUp",
						"You have successfully registered with our system. Please click "
								+ "on the activation link sent to your email provided during the registration");
			}

		} catch (UserNotUniqueException e) {
			message = e.getMessage();
			model.addAttribute("user_not_unique", message);
		} catch (EmailNotUniqueException e) {
			message = e.getMessage();
			model.addAttribute("email_not_unique", message);
		} catch (Exception e) {
			message = e.getMessage();
			model.addAttribute("message", message);
		}
		UserBean userbean = new UserBean();
		model.addAttribute("userLogin", userbean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("saveUserDetails controller : end");
		return "hotel_reservation";
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/adminregister.htm", method = RequestMethod.POST)
	public String saveAdminDetails(Model model,
			@ModelAttribute("registerAdmin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("saveUserDetails controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		String emailTemplate = "/letterTemplate/letterTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String contentPath = request.getContextPath();
		validator.validate(userBean, errors);
		String token = CushinaUtil.generateRandomPassword();

		if (errors.hasErrors()) {
			logger.info("errors :" + errors);
			model.addAttribute("adminerrors", "Please enter all the fields");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("saveUserDetails controller : end");
			return "hotel_reservation";
		}
		UserDTO userDTO = new UserDTO();
		userBean.setJoinDate(new Date());
		userBean.setToken(token);
		userBean.setStatus("InActive");
		if (userBean.getIsSP().equals("Y")) {
			userBean.setIsServiceProvider(true);
		} else {
			userBean.setIsServiceProvider(false);
		}
		userBean.setHotelID(hotelID);
		userBean.setEmail(userBean.getEmail().toString());
		BeanUtils.copyProperties(userBean, userDTO);
		logger.info("inside registrationmethod : userDto :" + userDTO);
		HotelBean bean = (HotelBean) session.getAttribute("hotel");
		HotelDTO hotelDTO = new HotelDTO();
		BeanUtils.copyProperties(bean, hotelDTO);
		String message = null;
		try {
			boolean saveUser = userService.saveUser(userDTO);
			if (saveUser) {
				logger.info("useDeatils saved successfully and userName  is :"
						+ userDTO.getUserName());
				// set userName in session.
				session.setAttribute("userName", userDTO.getUserName());

				String confirmationUrl = "http://" + request.getServerName()
						+ ":" + request.getServerPort() + contentPath + "/"
						+ "userRegstrnConfirmtn.htm?token=" + token;
				logger.info("sendMail method : start");
				boolean sendMail = MailUtil
						.sendMail(hotelDTO, userDTO,
								servletContext.getRealPath(emailTemplate),
								confirmationUrl, "Your new password" + "tocken"
										+ token);
				logger.info("sendMail method : end");
				model.addAttribute(
						"signUp",
						"You have successfully registered with our system. Please click "
								+ "on the activation link sent to your email provided during the registration");
			}

		} catch (UserNotUniqueException e) {
			message = e.getMessage();
			model.addAttribute("admin_not_unique", message);
		} catch (EmailNotUniqueException e) {
			message = e.getMessage();
			model.addAttribute("admail_not_unique", message);
		} catch (Exception e) {
			message = e.getMessage();
			model.addAttribute("message", message);
		}

		UserBean userbean = new UserBean();
		model.addAttribute("userLogin", userbean);
		model.addAttribute("adminLogin", userbean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("saveUserDetails controller : end");
		return "hotel_reservation";

	}

	/**
	 * checking the login user details
	 * 
	 * @param model
	 * @param userBean
	 * @param request
	 * @return
	 */

	@SuppressWarnings("unused")
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public String getLoginDetails(Model model,
			@ModelAttribute("userLogin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) {
		logger.info("getLoginDetails controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		validators.validate(userBean, errors);

		UserBean user = new UserBean();
		if (errors.hasErrors()) {
			model.addAttribute("tokenerror", "Please enter the Login details");
			model.addAttribute("userLogin", user);
			model.addAttribute("registerUser", user);
			model.addAttribute("adminLogin", user);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", user);
			model.addAttribute("quickReservation", user);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getLoginDetails controller : end");
			return "hotel_reservation";
		}

		UserDTO userDTO = userService.getLoginDetails(userBean.getEmail(),
				userBean.getPassword());

		if (userDTO != null && userDTO.getIsServiceProvider() == true
				&& !(userDTO.getHotelID().equals(hotelID))) {
			logger.info("SP can logged in to other hotel ---> *** ");
			session.setAttribute("servUserId", userDTO);
			session.setAttribute("servUserName", userDTO.getUserName());
			if ("Active".equals(userDTO.getStatus())) {
				session.setAttribute("providerLoggedIn", "loggedIn");
				model.addAttribute("loginsuccess",
						"success email/User Name and password.");
				model.addAttribute("getSPUser", user);
				model.addAttribute("userLogin", user);
				model.addAttribute("adminLogin", user);
				model.addAttribute("registerAdmin", user);
				model.addAttribute("registerUser", user);
				model.addAttribute("getUser", user);
				model.addAttribute("quickReservation", user);
				model.addAttribute("bookReservation", new BookingHistoryBean());
				model.addAttribute("image", hotelInfomrtn.getImage());
				return "hotel_reservation";
			} else {
				model.addAttribute("servAcnt_inactive",
						"Your account is still inactive. Please check your mail for activation link!");
				model.addAttribute("userLogin", user);
				model.addAttribute("adminLogin", user);
				model.addAttribute("registerAdmin", user);
				model.addAttribute("registerUser", user);
				model.addAttribute("getUser", user);
				model.addAttribute("quickReservation", user);
				model.addAttribute("bookReservation", new BookingHistoryBean());
				model.addAttribute("image", hotelInfomrtn.getImage());
				logger.info("getServiceLoginDetails controller : end");
				return "hotel_reservation";
			}

		} else if (userDTO != null && userDTO.getIsServiceProvider() == false) {
			logger.info("user logged in to other hotel ---> *** ");
			session.setAttribute("userId", userDTO);
			session.setAttribute("userName", userDTO.getUserName());
			if ("Active".equals(userDTO.getStatus())) {

				session.setAttribute("loggedIn", "in");
				model.addAttribute("loginsuccess",
						"success email/User Name and password.");
			} else {
				model.addAttribute("account_inactive",
						"Your account is still inactive. Please check your mail for activation link!");
			}
		} else {
			logger.info("came to else conditon  ---> *** ");
			
			if(userDTO != null &&userDTO.getIsServiceProvider() == true){
				model.addAttribute("invalidLogin",
						"Service Provider can not loggedIn as user in same hotel");
			}else{
				model.addAttribute("invalidLogin",
						"Invalid Email/Username or Password");
			}
			
			model.addAttribute("userLogin", user);
			model.addAttribute("registerUser", user);
			model.addAttribute("adminLogin", user);
			model.addAttribute("registerAdmin", user);
			model.addAttribute("getUser", user);
			model.addAttribute("quickReservation", user);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getLoginDetails controller : end");
			return "hotel_reservation";
		}

		/*
		 * if(userDTO!=null&&userDTO.isServiceProvider.equalsIgnoreCasee("true"))
		 * { model.addAttribute("getSPUser", new UserBean()); return
		 * "serviceprovider_view"; }else{ }
		 */

		model.addAttribute("loginhide", "loginhide");
		String userName = userDTO.getUserName();

		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getLoginDetails controller : end");
		return "hotel_reservation";
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/servicelogin.htm", method = RequestMethod.POST)
	public String getServiceLoginDetails(Model model,
			@ModelAttribute("adminLogin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) {
		logger.info("getServiceLoginDetails controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		validators.validate(userBean, errors);

		if (errors.hasErrors()) {
			logger.info("errors  :" + errors);
			model.addAttribute("errorDet", "Please enter the Login details");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getLoginDetails controller : end");
			return "hotel_reservation";
		}
		logger.info("entered email/username : " + userBean.getEmail()
				+ " : entered password : " + userBean.getPassword());
		UserDTO userDTO = userService.getLoginDetails(userBean.getEmail(),
				userBean.getPassword());
		logger.info("userDto : " + userDTO);
		if (userDTO != null && userDTO.getIsServiceProvider() == true ) {
			session.setAttribute("servUserId", userDTO);
			session.setAttribute("servUserName", userDTO.getUserName());
			
			if ("Active".equals(userDTO.getStatus())) {

				session.setAttribute("providerLoggedIn", "loggedIn");
				model.addAttribute("loginsuccess",
						"success email/User Name and password.");
				model.addAttribute("getSPUser", userBean);
				return "serviceprovider_view";
			} else {
				model.addAttribute("servAcnt_inactive",
						"Your account is still inactive. Please check your mail for activation link!");
				model.addAttribute("userLogin", userBean);
				model.addAttribute("adminLogin", userBean);
				model.addAttribute("registerAdmin", userBean);
				model.addAttribute("registerUser", userBean);
				model.addAttribute("getUser", userBean);
				model.addAttribute("quickReservation", userBean);
				model.addAttribute("bookReservation", new BookingHistoryBean());
				model.addAttribute("image", hotelInfomrtn.getImage());
				logger.info("getServiceLoginDetails controller : end");
				return "hotel_reservation";
			}
		} else {
			model.addAttribute("invalidServLogin",
					"Invalid Email/Username or Password.");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getLoginDetails controller : end");
			return "hotel_reservation";
		}

	}

	/**
	 * 
	 * @param model
	 * @param userBean
	 * @param errors
	 * @param request
	 * @param session
	 * @return
	 */

	@RequestMapping(value = "/forgotpass.htm", method = RequestMethod.POST)
	public String getForGotPassWord(Model model,
			@ModelAttribute("userLogin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) {
		logger.info("getForGotPassWord : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		UserDTO userDTO = userService.getPassWord(userBean.getEmail());

		if (userDTO != null) {
			String email = userDTO.getEmail();
			String password = userDTO.getPassword();
			boolean sendMail = MailUtil.sendMail(email, password,
					PropUtil.FORGOT_MAIL_SUBJECT);
			if (!sendMail) {
				model.addAttribute("invalidForgot", "Invalid Email/Username");
				model.addAttribute("loginhide", "loginhide");
				model.addAttribute("userLogin", userBean);
				model.addAttribute("registerUser", userBean);
				model.addAttribute("adminLogin", userBean);
				model.addAttribute("registerAdmin", userBean);
				model.addAttribute("getUser", userBean);
				model.addAttribute("quickReservation", userBean);
				model.addAttribute("bookReservation", new BookingHistoryBean());
				model.addAttribute("image", hotelInfomrtn.getImage());
				logger.info("getForGotPassWord : end");
				return "hotel_reservation";
			}

		} else {
			model.addAttribute("invalidForgot", "Invalid Email/Username ");
			model.addAttribute("loginhide", "loginhide");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getForGotPassWord : end");
			return "hotel_reservation";
		}
		model.addAttribute("invalidForgot", "Your password sent to your email.");
		model.addAttribute("loginhide", "loginhide");
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getForGotPassWord : end");
		return "hotel_reservation";
	}

	/**
	 * This method is validating user token, if token is valid allowed to book
	 * rooms otherwise it shows a pop up to resend activation link.
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userRegstrnConfirmtn.htm", method = RequestMethod.GET)
	public ModelAndView regstrnConfirmtn(@RequestParam("token") String token,
			Model model, HttpServletRequest request, HttpSession session,
			ModelAndView mav) {

		logger.info("regstrnConfirmtn controller : start");
		logger.info("token val :" + token);
		// HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		boolean isUserTokenExists = userService.isUserTokenExistsCheck(token);
		UserDTO userDto = userService.getUserByToken(token);
		UserBean userBean = new UserBean();
		if (isUserTokenExists) {
			if (userDto.getStatus().equals("Active")) {
				model.addAttribute("isTokenValidAlready",
						"Your account is already activated. Proceed to Login.");
			} else {
				boolean userCheck = userService.isUserTokenExists(token);
				if (userCheck) {
					model.addAttribute("isTokenValid",
							"Your account is activated Successfully!");
					userBean.setUserTokenExists(true);
				} else {
					model.addAttribute("isTokenInValid",
							"Your token notification period is expired.");
					userBean.setUserTokenExists(false);
				}
			}
		} else {
			model.addAttribute("tokenNotFound", "You clicked an outdated link");
		}

		model.addAttribute("token", token);
		logger.info("regstrnConfirmtn controller : end");
		mav.setViewName("redirect:");
		return mav;
	}

	/**
	 * 
	 * GetUsersDetails method
	 * 
	 * @param model
	 * @param userBean
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "getusers.htm", method = RequestMethod.GET)
	public String getUsers(Model model, HttpSession session) {
		String userName = (String) session.getAttribute("userName");
		String providerName = (String) session.getAttribute("servUserName");
		logger.info("getUsers controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		
		UserDTO allusers = null;
		if(userName != null){
			allusers = userService.getUserDetail(userName);
		}else {
			allusers = userService.getUserDetail(providerName);
		}	
		UserBean userBean = new UserBean();
		Date dateOfBirth = allusers.getDateOfBirth();
		DateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
		if (dateOfBirth != null) {
			allusers.setDob(formt.format(dateOfBirth));
		}

		model.addAttribute("mode", "edit");
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", allusers);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getUsers controller : end");
		return "hotel_reservation";
	}

	/**
	 * 
	 * UpdateUsers method
	 * 
	 * @param model
	 * @param userBean
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/updateUser.htm", method = RequestMethod.POST)
	public String upDateUsers(@ModelAttribute("getUser") UserBean userBean,
			Errors errors, Model model, HttpSession session)
			throws EmailNotUniqueException {
		logger.info("updateUser controller : start");

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		validator.validate(userBean, errors);

		UserDTO userDTo = new UserDTO();

		BeanUtils.copyProperties(userBean, userDTo);

		String message = null;

		if (errors.hasErrors()) {
			logger.info(errors);
			model.addAttribute("errors", "Please enter all the fields");
			model.addAttribute("mode", "Please enter all the fields");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("updateUser controller : end");
			return "hotel_reservation";
		}

		String phoneNumber = userBean.getPhoneNumber();
		String dob = userBean.getDob();
		if (phoneNumber != null) {
			userDTo.setContactNumber(Long.parseLong(phoneNumber));
		}
		DateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
		try {
			userDTo.setDateOfBirth(formt.parse(dob));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			boolean updateUser = userService.updateUser(userDTo);
			logger.info("inside updateUser(): updateUser true/false : "
					+ updateUser);
			if (updateUser) {
				model.addAttribute("updateUser",
						"Profile updated successfully!");
				model.addAttribute("mode", "Please enter all the fields");
			} else {
				model.addAttribute("updateFailure",
						"Profile update is not successfull!");
				model.addAttribute("mode", "Please enter all the fields");
			}
		} catch (EmailNotUniqueException e) {
			message = e.getMessage();
			model.addAttribute("pemail_not_unique", message);
		}

		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("updateUser controller : end");
		return "hotel_reservation";
	}

	/**
	 * 
	 * @param tokenForResend
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resendMail.htm", method = RequestMethod.GET)
	public ModelAndView resendMailNotification(
			@RequestParam("token") String tokenForResend, Model model,
			HttpServletRequest request, HttpSession session, ModelAndView mav) {
		logger.info("resendMailNotification controller : start ");

		String emailTemplate = "/letterTemplate/resendmailTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String contentPath = request.getContextPath();
		logger.info("tokenForResend" + tokenForResend);

		String newToken = CushinaUtil.generateRandomPassword();
		UserDTO userDTO = userService.getUserByToken(tokenForResend);
		logger.info("inside resendMailNotification userDto : " + userDTO);
		Integer userId = userDTO.getUserId();
		Date mailResentDate = new Date();

		logger.info("calling savetoken method :");
		boolean isSave = userService
				.saveToken(userId, newToken, mailResentDate);
		logger.info("issave " + isSave);
		String message = null;
		try {
			if (isSave) {
				String confirmationUrl = "http://" + request.getServerName()
						+ ":" + request.getServerPort() + contentPath + "/"
						+ "resendRegstrnConfirmtn.htm?token=" + newToken;
				logger.info("sendMail method : start");
				boolean sendMail = MailUtil.resendMail(userDTO,
						servletContext.getRealPath(emailTemplate),
						confirmationUrl, "Your new password" + "tocken"
								+ newToken);
				logger.info("sendMail method : end");
				if (sendMail) {
					model.addAttribute("mail_sent",
							"User Activation Link  Is Sent To Your Mail");
				}
			}
		} catch (Exception e) {
			message = e.getMessage();
			model.addAttribute("message", message);
		}

		logger.info("resendMailNotification controller : end");
		mav.setViewName("redirect:");
		return mav;
	}

	/**
	 * 
	 * @param token
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/resendRegstrnConfirmtn.htm", method = RequestMethod.GET)
	public ModelAndView resendMailConfirmtn(
			@RequestParam("token") String token, Model model,
			HttpServletRequest request, HttpSession session, ModelAndView mav) {
		logger.info("resendMailConfirmtn controller : start");
		boolean isUserTokenExists = userService
				.isResendMailTokenExistsCheck(token);
		UserDTO userDto = userService.getUserByToken(token);
		UserBean userBean = new UserBean();

		if (isUserTokenExists) {
			if (userDto.getStatus().equals("Active")) {
				model.addAttribute("isTokenValidAlready",
						"Your account is already activated. Proceed to Login.");
			} else {
				boolean userCheck = userService.isResendMailTokenExists(token);
				if (userCheck) {
					model.addAttribute("isTokenValid",
							"Your account is activated successfully!");
					userBean.setUserTokenExists(true);
				} else {
					model.addAttribute("isTokenInValid",
							"Your token notification period is expired.");
					userBean.setUserTokenExists(false);
				}
			}
		} else {
			model.addAttribute("tokenNotFound", "You clicked an outdated link");
		}

		model.addAttribute("token", token);
		logger.info("resendMailConfirmtn controller : end");
		mav.setViewName("redirect:");
		return mav;
	}

	@RequestMapping(value = "validateGuestInfo.htm", method = RequestMethod.POST)
	@ResponseBody
	public String validateAndSaveGuestInfo(
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email,
			@RequestParam("contactNumber") String phneNumber, Model model,
			HttpSession session) {
		logger.info("validateAndSaveGuestInfo controller : start");

		GuestUserBean guestUser = new GuestUserBean();
		GuestUserDTO userDTO = new GuestUserDTO();
		String message = null;
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		guestUser.setHotelId(hotelInfomrtn.getHotelID());
		guestUser.setFirstName(firstName);
		guestUser.setUserName(firstName);
		guestUser.setLastName(lastName);
		guestUser.setEmail(email);
		guestUser.setPhoneNumber(phneNumber);
		guestUser.setUserName(firstName);

		BeanUtils.copyProperties(guestUser, userDTO);

		Integer saveGuestUser = userService.saveGuestUser(userDTO);

		logger.info("validateAndSaveGuestInfo controller : end");
		return message;
	}

	/*
	 * @RequestMapping(value = "guestUserReservation.htm", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public String quickReservation(@RequestParam("firstName")
	 * String firstName,
	 * 
	 * @RequestParam("lastName") String lastName,
	 * 
	 * @RequestParam("email") String email,
	 * 
	 * @RequestParam("contactNumber") Long contactNumber, Model model,
	 * HttpSession session) throws JsonGenerationException,
	 * JsonMappingException, IOException {
	 * logger.info("QuickReservation controller : start");
	 * clearGuestUserInfo(session); ObjectMapper mapper = new ObjectMapper();
	 * String json = null; UserBean guestUser = new UserBean();
	 * guestUser.setFirstName(firstName); guestUser.setUserName(firstName);
	 * guestUser.setLastName(lastName); guestUser.setEmail(email);
	 * guestUser.setContactNumber(contactNumber);
	 * guestUser.setUserName(firstName); UserDTO QuickUserDto = new UserDTO();
	 * BeanUtils.copyProperties(guestUser, QuickUserDto);
	 * 
	 * GuestUserDTO guestUserDetail = userService
	 * .getGuestUserDetail(QuickUserDto.getFirstName());
	 * session.setAttribute("quickUserDetails", guestUserDetail);
	 * 
	 * json = mapper.writeValueAsString(guestUser);
	 * logger.info("QuickReservation controller : end"); return json; }
	 */
	@RequestMapping(value = "guestUserReservation.htm", method = RequestMethod.POST)
	public String quickReservation(Model model,
			@ModelAttribute("quickReservation") UserBean userBean,
			Errors errors, HttpSession session, HttpServletRequest request)
			throws Exception {

		logger.info("QuickReservation controller : start");

		guestvalidator.validate(userBean, errors);

		if (errors.hasErrors()) {
			logger.info(errors);
			model.addAttribute("guesterrors", "Please enter all the fields");
			model.addAttribute("guestmode", "Guest User");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("guestUserDetails", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());

			logger.info("updateUser controller : end");
			return "hotel_reservation";
		}
		String values = request.getParameter("valuesinquickform");
		logger.info("values form page as 1,116,11,04/09/2015::::" + values);
		userBean.setValuesinquickform(values);

		GuestUserDTO guestUser = new GuestUserDTO();
		guestUser.setFirstName(userBean.getFirstName());
		guestUser.setUserName(userBean.getFirstName());
		guestUser.setLastName(userBean.getLastName());
		guestUser.setEmail(userBean.getEmail().toLowerCase());
		guestUser.setPhoneNumber(userBean.getPhoneNumber());
		guestUser.setUserName(userBean.getFirstName());
		guestUser.setIsUpdate(false);

		String message = "";

		Integer guestuserId = userService.saveGuestUser(guestUser);
		if (guestuserId != 0) {
			GuestUserDTO guestUserDetail = userService
					.getGuestUserDetailsById(guestuserId);

			/*
			 * GuestUserDTO guestUserDetail =
			 * userService.getGuestUserDetail(guestUser .getEmail());
			 */
			session.setAttribute("quickUserDetails", guestUserDetail);
		}

		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("qucikuserunique", message);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("guestUserDetails", userBean);
		model.addAttribute("reservemode", "ReserveUser");
		model.addAttribute("bookReservation", new BookingHistoryBean());

		return "hotel_reservation";
	}

	/**
	 * get logout page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public ModelAndView logout(Model model, HttpSession session,
			ModelAndView mav) {
		logger.info("logout : start");
		model.addAttribute("logoutsuccess",
				"You have logged out of your account successfully.");
		setAttributesInSession(session);
		logger.info("logout : end");
		mav.setViewName("redirect:");
		return mav;
	}

	public void clearGuestUserInfo(HttpSession session) {
		session.setAttribute("quickUserDetsails", null);
	}

	@RequestMapping(value = "getGuestUserDetails.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUserDetails(HttpSession session, Model model)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		String email = null;
		GuestUserDTO userdto = (GuestUserDTO) session
				.getAttribute("quickUserDetails");

		json = mapper.writeValueAsString(userdto);
		logger.info("getUser Details In Reservation controller : end");
		return json;
	}

	/**
	 * setAttributesInSession
	 * 
	 * @param session
	 */
	public void setAttributesInSession(HttpSession session) {
		session.setAttribute("userName", null);
		session.setAttribute("hotel", null);
		session.setAttribute("userId", null);
		session.setAttribute("hotelDetails", null);
		session.setAttribute("currentDate", null);
		session.setAttribute("quickUserDetails", null);
		session.setAttribute("loggedIn", null);
		session.setAttribute("providerLoggedIn", null);
		session.invalidate();
	}

	@ModelAttribute("languages")
	public Map<String, String> languages() {
		Map<String, String> languages = new LinkedHashMap<String, String>();
		languages.put("Select Language", "Select Language");
		languages.put("English", "English");
		languages.put("Germany", "Germany");
		languages.put("Deutch", "Deutch");
		languages.put("Other Language", "Other Language");
		return languages;
	}

	@ModelAttribute("title")
	public Map<String, String> title() {
		Map<String, String> title = new LinkedHashMap<String, String>();
		title.put("Select Title", "Select Title");
		title.put("Mr", "Mr");
		title.put("Miss", "Miss");
		title.put("Mrs", "Mrs");
		return title;
	}

	@ModelAttribute("countryList")
	public Map<String, String> country() {
		Map<String, String> countryList = new LinkedHashMap<String, String>();
		countryList.put("Select Country", "Select Country");
		countryList.put("USA", "USA");
		countryList.put("UK", "UK");
		countryList.put("Germany", "Germany");
		countryList.put("France", "France");
		countryList.put("Spain", "Spain");
		countryList.put("USA", "USA");
		countryList.put("Others", "Others");
		return countryList;
	}

	@ModelAttribute("notificationPeriod")
	public Map<String, String> notificationPeriod() {
		Map<String, String> notificationPeriod = new LinkedHashMap<String, String>();
		notificationPeriod.put("Notification Period", "Notification Period");
		notificationPeriod.put("24 Hours", "24 Hours");
		notificationPeriod.put("48 Hours", "48 Hours");
		notificationPeriod.put("72 Hours", "72 Hours");
		return notificationPeriod;
	}

}
