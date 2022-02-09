package com.events.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.mapping.Array;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

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
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;
import com.cushina.web.validator.LoginValidator;
import com.cushina.web.validator.RegistrationValidator;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventUserDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.common.dto.UserResult;
import com.events.common.util.EventReceiptPDFGeneratingThread;
import com.events.service.PickEventOrganizerService;
import com.events.web.bean.EventScheduleBean;
import com.events.web.bean.EventsOrganizerBean;
import com.events.web.bean.EventsReservationBean;
import com.massage.common.dto.MassagePersonDTO;
import com.massage.common.dto.MassageServiceScheduleDTO;
import com.massage.web.bean.MassageServiceScheduleBean;

@Controller
@SessionAttributes(value = { "userName", "hotel", "userId", "hotelDetails",
		"currentDate" })
public class EventUserController {

	private Logger logger = Logger.getLogger(EventUserController.class
			.getName());

	@Autowired
	private PickEventOrganizerService service;

	@Autowired
	private RegistrationValidator registrationValidator;
	@Autowired
	private UserService userService;

	@Autowired
	private LoginValidator loginValidator;

	@RequestMapping(value = "/eventUserLogin.htm", method = RequestMethod.POST)
	public String getLoginDetails(Model model,
			@ModelAttribute("userLogin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) {
		logger.info("getLoginDetails controller : start");
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();
		String page = (String) request.getParameter("page");
		loginValidator.validate(userBean, errors);

		if (errors.hasErrors()) {

			logger.info("errors  :" + errors);
			model.addAttribute("tokenerror", "Please enter the Login details");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			// model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getLoginDetails controller : end");
			return "event_reservation";

		}

		UserDTO userDTO = userService.getLoginDetails(userBean.getEmail(),
				userBean.getPassword());
		EventUserDTO eventUserDTO = null;
		if (userDTO != null) {
			Integer userId = userDTO.getUserId();
			eventUserDTO = userService.isEventServiceProvider(userId);
		}
		if (eventUserDTO != null) {
			if (eventUserDTO.getIsServiceProvider().equals("Y")
					&& !(eventOrgID.equals(eventUserDTO.getEventOrgId()))) {
				if ("Active".equals(userDTO.getStatus())) {
					session.setAttribute("providerLoggedIn", "loggedIn");
					model.addAttribute("loginsuccess",
							"success email/User Name and password.");

					model.addAttribute("getSPUser", userBean);
					model.addAttribute("userLogin", userBean);
					model.addAttribute("adminLogin", userBean);
					model.addAttribute("registerAdmin", userBean);
					model.addAttribute("registerUser", userBean);
					model.addAttribute("getUser", userBean);
					model.addAttribute("quickReservation", userBean);
					model.addAttribute("bookReservation",
							new BookingHistoryBean());
					// model.addAttribute("image", hotelInfomrtn.getImage());
					return "event_reservation";
				} else {
					model.addAttribute("servAcnt_inactive",
							"Your account is still inactive. Please check your mail for activation link!");
					model.addAttribute("userLogin", userBean);
					model.addAttribute("adminLogin", userBean);
					model.addAttribute("registerAdmin", userBean);
					model.addAttribute("registerUser", userBean);
					model.addAttribute("getUser", userBean);
					model.addAttribute("quickReservation", userBean);
					model.addAttribute("bookReservation",
							new BookingHistoryBean());
					// model.addAttribute("image", hotelInfomrtn.getImage());
					logger.info("getServiceLoginDetails controller : end");
					return "event_reservation";
				}
			} else if (eventUserDTO.getIsServiceProvider().equals("N")) {
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

				if (eventUserDTO != null
						&& eventUserDTO.getIsServiceProvider().equals("Y")) {
					model.addAttribute("invalidLogin",
							"Service Provider can not loggedIn as user in same hotel");
				} else {
					model.addAttribute("invalidLogin",
							"Invalid Email/Username or Password");
				}

				model.addAttribute("userLogin", userBean);
				model.addAttribute("registerUser", userBean);
				model.addAttribute("adminLogin", userBean);
				model.addAttribute("registerAdmin", userBean);
				model.addAttribute("getUser", userBean);
				model.addAttribute("quickReservation", userBean);
				model.addAttribute("bookReservation", new BookingHistoryBean());
				// model.addAttribute("image", hotelInfomrtn.getImage());
				logger.info("getLoginDetails controller : end");
				return "event_reservation";

			}
		} else {

			model.addAttribute("invalidLogin",
					"Invalid Email/Username or Password.");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());

			logger.info("getLoginDetails controller : end");
			return "event_reservation";

		}

		logger.info("getLoginDetails controller : end");

		model.addAttribute("loginhide", "loginhide");

		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		// model.addAttribute("image", hotelInfomrtn.getImage());
		return "event_reservation";

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/adminEventRegister.htm", method = RequestMethod.POST)
	public String saveAdminDetails(Model model,
			@ModelAttribute("registerAdmin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("saveUserDetails controller : start");

		String emailTemplate = "/letterTemplate/letterTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String contentPath = request.getContextPath();
		registrationValidator.validate(userBean, errors);
		String token = CushinaUtil.generateRandomPassword();

		if (errors.hasErrors()) {
			logger.info("errors :" + errors);
			model.addAttribute("adminerrors", "Please enter all the fields");
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			logger.info("saveUserDetails controller : end");
			return "event_reservation";
		}
		UserDTO userDTO = new UserDTO();
		userBean.setJoinDate(new Date());
		userBean.setToken(token);
		userBean.setStatus("InActive");
		/*
		 * if (userBean.getIsSP().equals("N")) {
		 * userBean.setIsServiceProvider(false); }
		 */
		BeanUtils.copyProperties(userBean, userDTO);
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();

		EventsOrganizerDTO eventsOrganizerDTO = new EventsOrganizerDTO();
		BeanUtils.copyProperties(eventInfomrtn, eventsOrganizerDTO);
		String message = null;
		try {
			userDTO.setEventOrgId(eventOrgID);
			boolean saveUser = userService.saveEventServiceUser(userDTO);
			if (saveUser) {
				logger.info("useDeatils saved successfully and userName  is :"
						+ userDTO.getUserName());
				// set userName in session.
				session.setAttribute("userName", userDTO.getUserName());

				String confirmationUrl = "http://" + request.getServerName()
						+ ":" + request.getServerPort() + contentPath + "/"
						+ "eventUserRegstrnConfirmtn.htm?token=" + token;
				logger.info("sendMail method : start");
				boolean sendMail = MailUtil
						.sendMail(eventsOrganizerDTO, userDTO,
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
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("saveUserDetails controller : end");
		return "event_reservation";
	}

	@RequestMapping(value = "getEventUsers.htm", method = RequestMethod.GET)
	public String getUsers(Model model, HttpSession session,
			HttpServletRequest request) throws ParseException {
		String userName = (String) session.getAttribute("userName");
		logger.info("getUsers controller : start");

		UserDTO allusers = userService.getUserDetail(userName);
		UserBean userBean = new UserBean();
		Date dateOfBirth = allusers.getDateOfBirth();
		Date joinDate = allusers.getJoinDate();
		DateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy");
		if (dateOfBirth != null) {
			allusers.setDob(dateformt.format(dateOfBirth));
		}
		if (joinDate != null) {
			allusers.setDateJoin(dateformt.format(joinDate));
		}
		model.addAttribute("mode", "edit");
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", allusers);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getUsers controller : end");
		return "event_reservation";

	}

	@RequestMapping(value = "/eventUserRegister.htm", method = RequestMethod.POST)
	public String saveUserDetails(Model model,
			@ModelAttribute("registerUser") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("saveUserDetails controller : start");

		String emailTemplate = "/letterTemplate/letterTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String contentPath = request.getContextPath();
		registrationValidator.validate(userBean, errors);
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
			logger.info("saveUserDetails controller : end");
			return "event_reservation";
		}
		UserDTO userDTO = new UserDTO();
		userBean.setJoinDate(new Date());
		userBean.setToken(token);
		userBean.setStatus("InActive");
		/*
		 * if (userBean.getIsSP().equals("N")) {
		 * userBean.setIsServiceProvider(false); }
		 */
		BeanUtils.copyProperties(userBean, userDTO);
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();

		EventsOrganizerDTO eventsOrganizerDTO = new EventsOrganizerDTO();
		BeanUtils.copyProperties(eventInfomrtn, eventsOrganizerDTO);
		String message = null;
		try {
			userDTO.setEventOrgId(eventOrgID);
			boolean saveUser = userService.saveEventUser(userDTO);
			if (saveUser) {
				logger.info("useDeatils saved successfully and userName  is :"
						+ userDTO.getUserName());
				// set userName in session.
				session.setAttribute("userName", userDTO.getUserName());

				String confirmationUrl = "http://" + request.getServerName()
						+ ":" + request.getServerPort() + contentPath + "/"
						+ "eventUserRegstrnConfirmtn.htm?token=" + token;
				logger.info("sendMail method : start");
				boolean sendMail = MailUtil
						.sendMail(eventsOrganizerDTO, userDTO,
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
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("saveUserDetails controller : end");
		return "event_reservation";
	}

	@RequestMapping(value = "/updateEventUser.htm", method = RequestMethod.POST)
	public String upDateUsers(@ModelAttribute("getUser") UserBean userBean,
			Errors errors, Model model, HttpSession session)
			throws EmailNotUniqueException {
		logger.info("updateUser controller : start");

		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();

		registrationValidator.validate(userBean, errors);

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
			// model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("updateUser controller : end");
			return "event_reservation";
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
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("updateUser controller : end");
		return "event_reservation";
	}

	@RequestMapping(value = "/eventUserRegstrnConfirmtn.htm", method = RequestMethod.GET)
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

	@RequestMapping(value = "/eventServicelogin.htm", method = RequestMethod.POST)
	public String getServiceLoginDetails(Model model,
			@ModelAttribute("adminLogin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) {
		logger.info("getServiceLoginDetails controller : start");
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();
		loginValidator.validate(userBean, errors);

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
			// model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getLoginDetails controller : end");
			return "event_reservation";
		}
		logger.info("entered email/username : " + userBean.getEmail()
				+ " : entered password : " + userBean.getPassword());
		UserDTO userDTO = userService.getLoginDetails(userBean.getEmail(),
				userBean.getPassword());
		EventUserDTO eventUserDTO = null;
		if (userDTO != null) {
			Integer userId = userDTO.getUserId();
			eventUserDTO = userService.isEventServiceProvider(userId);
		}
		logger.info("userDto : " + userDTO);
		if (eventUserDTO != null) {
			if (eventUserDTO.getIsServiceProvider().equals("Y")) {
				session.setAttribute("servUserId", userDTO);
				session.setAttribute("servUserName", userDTO.getUserName());
				if ("Active".equals(userDTO.getStatus())) {

					session.setAttribute("providerLoggedIn", "loggedIn");
					model.addAttribute("loginsuccess",
							"success email/User Name and password.");
					model.addAttribute("getSPUser", userBean);
					return "serviceprovider_event";

				} else {
					model.addAttribute("servAcnt_inactive",
							"Your account is still inactive. Please check your mail for activation link!");
					model.addAttribute("userLogin", userBean);
					model.addAttribute("adminLogin", userBean);
					model.addAttribute("registerAdmin", userBean);
					model.addAttribute("registerUser", userBean);
					model.addAttribute("getUser", userBean);
					model.addAttribute("quickReservation", userBean);
					model.addAttribute("bookReservation",
							new BookingHistoryBean());
					// model.addAttribute("image", hotelInfomrtn.getImage());
					logger.info("getServiceLoginDetails controller : end");
					return "event_reservation";
				}
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
			// model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getLoginDetails controller : end");
			return "event_reservation";
		}
		model.addAttribute("invalidServLogin",
				"Invalid Email/Username or Password.");
		model.addAttribute("userLogin", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getLoginDetails controller : end");
		return "event_reservation";

	}

	@RequestMapping(value = "/forgotEventUserPass.htm", method = RequestMethod.POST)
	public String getForGotPassWord(Model model,
			@ModelAttribute("userLogin") UserBean userBean, Errors errors,
			HttpServletRequest request, HttpSession session) {
		logger.info("getForGotPassWord : start");
		// HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

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
				// model.addAttribute("image", hotelInfomrtn.getImage());
				logger.info("getForGotPassWord : end");
				return "event_reservation";
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
			// model.addAttribute("image", hotelInfomrtn.getImage());
			logger.info("getForGotPassWord : end");
			return "event_reservation";
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
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getForGotPassWord : end");
		return "event_reservation";
	}

	@RequestMapping(value = "/getPrimaryDates.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getPrimaryDates(@RequestParam("date") String date,
			HttpSession session) {
		ObjectMapper mapper = new ObjectMapper();
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date frmtDate = null;
		try {
			frmtDate = dateFormat.parse(date);
		} catch (ParseException e) {
			return "Failed";
		}
		Integer eventId = 0, guideId = 0;
		Map<Date, List<EventScheduleDTO>> map = service.getDataMap(frmtDate,
				eventId, guideId);
		logger.info("dto map in loadDataByclikcedDt controller : " + map);
		Map<String, List<EventScheduleBean>> scheduleBeanMap = new LinkedHashMap<String, List<EventScheduleBean>>();

		Set<Date> datesSet = map.keySet();
		logger.info("datesSet : " + datesSet);

		List<Integer> guideIds = new ArrayList<Integer>();

		for (Date dt : datesSet) {
			List<EventScheduleDTO> listDto = map.get(dt);
			for (EventScheduleDTO dto : listDto) {
				guideIds.add(dto.getGuideId());
			}
		}

		List<EventsGuideDTO> guidesList = service.getEventGuidesByIds(guideIds);

		Map<Integer, String> guideNameMap = new HashMap<Integer, String>();
		for (EventsGuideDTO eventsGuideDTO : guidesList) {
			guideNameMap.put(eventsGuideDTO.getGuideId(),
					eventsGuideDTO.getGuideName());
		}

		for (Date dt : datesSet) {
			String dateString = dateFormat.format(dt);
			List<EventScheduleBean> listBean = new ArrayList<EventScheduleBean>();
			List<EventScheduleDTO> listDto = map.get(dt);
			for (EventScheduleDTO dto : listDto) {
				EventScheduleBean bean = new EventScheduleBean();
				BeanUtils.copyProperties(dto, bean);
				Integer divCount = service.getDivCount(bean.getEvntStrtTme(),
						bean.getEvntEndTme());
				Date eventDt = bean.getDate();
				bean.setEvntDt(dateFormat.format(eventDt));
				bean.setDivCount(divCount);
				EventsDTO eventDto = service.getEventById(dto.getEventId());
				if (userDto != null) {
					boolean haveReservation = service.haveReservation(
							bean.getScheduleId(), userDto.getUserId());
					bean.setHaveReservation(haveReservation);
				}
				bean.setEventName(eventDto.getEventName());
				String guideName = guideNameMap.get(dto.getGuideId());
				bean.setGuideName(guideName);
				listBean.add(bean);
			}
			scheduleBeanMap.put(dateString, listBean);
		}
		//logger.info("getPrimaryDates controller : before returning scheduleBeanMAp : "
				//+ scheduleBeanMap);
		String json = "";
		try {
			json = mapper.writeValueAsString(scheduleBeanMap);
		} catch (Exception e) {
			return "Failed";
		}
		return json;
	}
	
	
	
	

	@RequestMapping(value = "/getEventWidgetData.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getEventWidgetData(
			@RequestParam("date") String selectedDateStr, HttpSession session)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date selectedDate = dateFormat.parse(selectedDateStr);
		Map<String, List<EventScheduleDTO>> eventScheduleListMap = service
				.getEvntWidgetData(selectedDate);
		Set<String> eventNames = eventScheduleListMap.keySet();
		List<Integer> scheduleIds = new ArrayList<Integer>();
		for (String eventKey : eventNames) {
			List<EventScheduleDTO> dtoList = eventScheduleListMap.get(eventKey);
			for (EventScheduleDTO dto : dtoList) {
				scheduleIds.add(dto.getScheduleId());
			}
		}

		Map<Integer, Boolean[]> isNoteAndPaidMap = new HashMap<Integer, Boolean[]>();
		for (Integer id : scheduleIds) {
			Boolean[] bool = service.getIsNoteAndIsPaid(id);
			isNoteAndPaidMap.put(id, bool);
		}
		logger.info("isNoteAndPaidMap : " + isNoteAndPaidMap);

		Map<String, List<EventScheduleBean>> eventSchedulesMap = new LinkedHashMap<String, List<EventScheduleBean>>();
		EventScheduleBean bean = null;
		for (String eventName : eventNames) {
			List<EventScheduleDTO> scheduleDtoList = eventScheduleListMap
					.get(eventName);
			List<EventScheduleBean> scheduleBeanList = new ArrayList<EventScheduleBean>();
			for (EventScheduleDTO scheduleDto : scheduleDtoList) {
				bean = new EventScheduleBean();
				Integer divCount = service.getDivCount(
						scheduleDto.getEvntStrtTme(),
						scheduleDto.getEvntEndTme());
				logger.info("controller divcount : " + divCount);
				scheduleDto.setDivCount(divCount);
				Boolean[] boolVal = isNoteAndPaidMap.get(scheduleDto
						.getScheduleId());
				scheduleDto.setHaveNote(boolVal[0]);
				scheduleDto.setHavePaid(boolVal[1]);
				if (userDto != null) {
					boolean haveReservation = service.haveReservation(
							scheduleDto.getScheduleId(), userDto.getUserId());
					scheduleDto.setHaveReservation(haveReservation);
				}
				BeanUtils.copyProperties(scheduleDto, bean);
				scheduleBeanList.add(bean);
			}
			eventSchedulesMap.put(eventName, scheduleBeanList);
		}
		logger.info("controller getEventWidgetData before returning beanMap : "
				+ eventSchedulesMap);
		String result = mapper.writeValueAsString(eventSchedulesMap);

		return result;
	}

	@RequestMapping(value = "/saveEvntUsrReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String saveEvntUsrResrvtion(@RequestParam("json") String json,
			Model model, HttpSession session, HttpServletRequest request)
			throws JSONException, JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json1 = null;
		EventsOrganizerBean organizer = (EventsOrganizerBean) session
				.getAttribute("event");
		EventsOrganizerDTO orgDto = new EventsOrganizerDTO();
		BeanUtils.copyProperties(organizer, orgDto);
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		EventScheduleDTO scheduleDto = new EventScheduleDTO();
		String emailTemplate = "/letterTemplate/evntRsrvtnCnfrm.html";
		String pdfPath = "/receiptBgImg/pdfReceipt.png";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String pdfRealPath = servletContext.getRealPath(pdfPath);

		Map<String, String> map = service.getImageMap();
		Map<String, String> imgRealPathMap = new LinkedHashMap<String, String>();
		Set<String> set = map.keySet();
		String reelPath = null;
		for (String key : set) {
			String val = map.get(key);
			reelPath = servletContext.getRealPath(val);
			imgRealPathMap.put(key, reelPath);
		}

		String realPath = servletContext.getRealPath(emailTemplate);
		UserBean bean = new UserBean();
		BeanUtils.copyProperties(userDto, bean);
		JSONObject jsonObj = new JSONObject(json);
		Integer selectedSeats = jsonObj.getInt("slctdSeats");
		Integer scheduleId = jsonObj.getInt("scheduleId");
		String notes = jsonObj.getString("notes");
		if (notes.isEmpty()) {
			notes = "";
		}
		String strtTime = jsonObj.getString("strtTime");
		String endTime = jsonObj.getString("endTime");
		String duration = jsonObj.getString("duration");
		Integer availSeats = jsonObj.getInt("availSeats");
		String eventName = jsonObj.getString("eventName");
		String emailShare = jsonObj.getString("emailShare");
		Integer diffOfSeats = availSeats - selectedSeats;

		logger.info("organizer in session : " + organizer);
		logger.info("logged in userdto : " + userDto);
		logger.info(" selectedSeats " + selectedSeats + " scheduleId "
				+ scheduleId + " notes " + notes);

		EventsReservationDTO reservationDto = new EventsReservationDTO();
		reservationDto.setNoOfPeople(selectedSeats);
		reservationDto.setUserID(userDto.getUserId());
		reservationDto.setEventScheduleId(scheduleId);
		reservationDto.setEventOrgId(organizer.getEventOrgId());
		reservationDto.setNotes(notes);
		reservationDto.setEmailShare(emailShare);

		EventScheduleDTO scheduleDTO = service.getScheduleById(scheduleId);
		EventsGuideDTO guideDto = service
				.getGuideById(scheduleDTO.getGuideId());

		Long refNumber = service.saveEventReservation(reservationDto);
		bean.setRefNumber(refNumber);

		Integer isUpdate = service.updateAvailSeats(scheduleId, diffOfSeats);

		String subject = "Confirmation of you Tour reservation with Woofre!";

		scheduleDto.setUserName(userDto.getUserName());
		scheduleDto.setEmail(userDto.getEmail());
		scheduleDto.setPhoneNumber(userDto.getPhoneNumber());
		scheduleDto.setRefNumber(refNumber);
		scheduleDto.setBookedSeats(selectedSeats);
		scheduleDto.setStrtTme(strtTime);
		scheduleDto.setEndTme(endTime);
		scheduleDto.setEventName(eventName);
		scheduleDto.setGuideName(guideDto.getGuideName());

		logger.info("orgDto in session : " + orgDto);
		logger.info("scheduleDto passed to mail method : " + scheduleDto);
		logger.info("realPath : " + realPath);
		logger.info("sMap : " + imgRealPathMap);

		if (isUpdate > 0) {
			EventReceiptPDFGeneratingThread mailThread = new EventReceiptPDFGeneratingThread(
					request, orgDto, scheduleDto,
					servletContext.getRealPath(emailTemplate), subject,
					imgRealPathMap, pdfRealPath, session);
			mailThread.start();
		}

		json1 = mapper.writeValueAsString(bean);
		return json1;
	}

	@RequestMapping(value = "/getBlueReservedUsers.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getEvntReservedUserList(
			@RequestParam("scheduleId") Integer scheduleId,
			HttpServletRequest request, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		Map<EventScheduleDTO, List<EventsReservationDTO>> map = service
				.getBlueReservedUsersMap(scheduleId, userDto.getUserId());
		Set<EventScheduleDTO> set = map.keySet();
		List<EventsReservationBean> listBean = new ArrayList<EventsReservationBean>();
		Map<String, List<EventsReservationBean>> beanMap = new HashMap<String, List<EventsReservationBean>>();
		EventScheduleBean scheduleBean = new EventScheduleBean();
		EventsReservationBean reserveBean = null;
		for (EventScheduleDTO dto : set) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd MMMM, yyyy - HH:mm");
			List<EventsReservationDTO> listDto = map.get(dto);
			BeanUtils.copyProperties(dto, scheduleBean);
			scheduleBean.setEvntStrtTme(formatter.format(scheduleBean
					.getStartTime()));
			scheduleBean.setEvntEndTme(formatter.format(scheduleBean
					.getEndTime()));
			for (EventsReservationDTO reserveDto : listDto) {
				reserveBean = new EventsReservationBean();
				BeanUtils.copyProperties(reserveDto, reserveBean);
				listBean.add(reserveBean);
			}
		}
		String key = mapper.writeValueAsString(scheduleBean);
		beanMap.put(key, listBean);
		logger.info("getevntreserved befre returning beanMAp : " + beanMap);
		json = mapper.writeValueAsString(beanMap);
		return json;
	}

	@RequestMapping(value = "/returnToUserEventReservation.htm", method = RequestMethod.GET)
	public String resetToUserEventReservation(Model model,
			HttpServletRequest request, HttpSession session) {

		model.addAttribute("userLogin", new UserBean());
		model.addAttribute("registerUser", new UserBean());
		model.addAttribute("adminLogin", new UserBean());
		model.addAttribute("registerAdmin", new UserBean());
		model.addAttribute("getUser", new UserBean());
		model.addAttribute("quickReservation", new UserBean());
		model.addAttribute("bookReservation", new BookingHistoryBean());

		return "event_reservation";
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

	@RequestMapping(value = "/eventUserAjaxLogin.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getLoginDetailsUsingAjax(UserBean userbean,
			HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getLoginDetails controller : start" + userbean);
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();
		String message = null;
		UserDTO userDTO = userService.getLoginDetails(userbean.getEmail(),
				userbean.getPassword());
		EventUserDTO eventUserDTO = null;
		if (userDTO != null) {
			Integer userId = userDTO.getUserId();
			eventUserDTO = userService.isEventServiceProvider(userId);
		}
		if (eventUserDTO != null) {
			if (eventUserDTO.getIsServiceProvider().equals("Y")
					&& !(eventOrgID.equals(eventUserDTO.getEventOrgId()))) {
				if ("Active".equals(userDTO.getStatus())) {
					session.setAttribute("providerLoggedIn", "loggedIn");
					message = "success email/User Name and password.";

				} else {
					message = "Your account is still inactive. Please check your mail for activation link!";

				}
			} else if (eventUserDTO.getIsServiceProvider().equals("N")) {
				session.setAttribute("userId", userDTO);
				session.setAttribute("userName", userDTO.getUserName());
				if ("Active".equals(userDTO.getStatus())) {

					session.setAttribute("loggedIn", "in");
					message = "success email/User Name and password.";

				} else {
					message = "Your account is still inactive. Please check your mail for activation link!";

				}
			} else {

				logger.info("came to else conditon  ---> *** ");

				if (eventUserDTO != null
						&& eventUserDTO.getIsServiceProvider().equals("Y")) {
					message = "Service Provider can not loggedIn as user in same hotel";
				} else {
					message = "Invalid Email/Username and Password";

				}

			}
		} else {

			message = "Invalid Email/Username and Password";

			logger.info("getLoginDetails controller : end");

		}

		logger.info("getLoginDetails controller : end");
		UserResult userResult=new UserResult();
		userResult.setMessage(message);
		userResult.setSessionVal((String)session.getAttribute("loggedIn"));
		ObjectMapper mapper=new ObjectMapper();
		// model.addAttribute("image", hotelInfomrtn.getImage());
		return mapper.writeValueAsString(userResult);

	}
	@ResponseBody
	@RequestMapping(value = "/eventAjaxUserUpdate.htm", method = RequestMethod.POST)
	public String updateEventUsers(UserBean userBean,
			Errors errors, Model model, HttpSession session)
			throws EmailNotUniqueException {
		logger.info("updateUser controller : start"+userBean);

		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();

		

		UserDTO userDTo = new UserDTO();

		BeanUtils.copyProperties(userBean, userDTo);

		String message = null;

		

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
				message="Profile updated successfully!";
						
			} else {
				
				message="Profile update is not successfull!";
			}
		} catch (EmailNotUniqueException e) {
			message = e.getMessage();
		}

		
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("updateUser controller : end");
		return message;
	}
	@ResponseBody
	@RequestMapping(value = "/forgotPassEventUser.htm", method = RequestMethod.POST)
	public String getForgotPassWord(Model model,
			 UserBean userBean,
			HttpServletRequest request, HttpSession session) {
		String message="";
		logger.info("getForGotPassWord : start");
		// HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		UserDTO userDTO = userService.getPassWord(userBean.getEmail());

		if (userDTO != null) {
			String email = userDTO.getEmail();
			String password = userDTO.getPassword();
			boolean sendMail = MailUtil.sendMail(email, password,
					PropUtil.FORGOT_MAIL_SUBJECT);
			if (!sendMail) {
				message="Invalid Email/Username";
				
				
			}else{
				message="Your password sent to your email.";
			}

		} else {
			message="Invalid Email/Username";
		}
		
	
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getForGotPassWord : end");
		return message;
	}
	@ResponseBody
	@RequestMapping(value = "/eventAdminAjaxLogin.htm", method = RequestMethod.GET)
	public String getAdminLoginDetails(Model model,
		 UserBean userBean,
			HttpServletRequest request, HttpSession session) {
		logger.info("getServiceLoginDetails controller : start");
		
		
		logger.info("entered email/username : " + userBean.getEmail()
				+ " : entered password : " + userBean.getPassword());
		UserDTO userDTO = userService.getLoginDetails(userBean.getEmail(),
				userBean.getPassword());
		EventUserDTO eventUserDTO = null;
		if (userDTO != null) {
			Integer userId = userDTO.getUserId();
			eventUserDTO = userService.isEventServiceProvider(userId);
		}
		String message="";
				logger.info("userDto : " + userDTO);
		if (eventUserDTO != null) {
			if (eventUserDTO.getIsServiceProvider().equals("Y")) {
				session.setAttribute("servUserId", userDTO);
				session.setAttribute("servUserName", userDTO.getUserName());
				if ("Active".equals(userDTO.getStatus())) {

					session.setAttribute("providerLoggedIn", "loggedIn");
					message="success email/User Name and password.";
							
				

				} else {
				message="Your account is still inactive. Please check your mail for activation link!";
			
				}
			}else{
				message="User Dont have Credentials to  login As Service Provider.";	
			}
		} else {
			message="Invalid Email/Username or Password.";
		
		
			logger.info("getLoginDetails controller : end");
		}
	
	
		logger.info("getLoginDetails controller : end");
		return message;

	}
	@RequestMapping(value = "/serviceprovider_view.htm", method = RequestMethod.GET)
	public String getAdminLoginPage(Model model) {
		logger.info("getServiceLoginDetails controller : start");
		model.addAttribute("getSPUser", new UserBean());
		return "serviceprovider_event";
		

	}
	@ResponseBody
	@RequestMapping(value = "/adminEventAjaxRegister.htm", method = RequestMethod.POST)
	public String saveAdminuserDetails(Model model,
		UserBean userBean,
			HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("saveUserDetails controller : start");

		String emailTemplate = "/letterTemplate/registerletterTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String contentPath = request.getContextPath();
		String token = CushinaUtil.generateRandomPassword();

		UserDTO userDTO = new UserDTO();
		userBean.setJoinDate(new Date());
		userBean.setToken(token);
		userBean.setStatus("InActive");
		
		BeanUtils.copyProperties(userBean, userDTO);
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();

		EventsOrganizerDTO eventsOrganizerDTO = new EventsOrganizerDTO();
		BeanUtils.copyProperties(eventInfomrtn, eventsOrganizerDTO);
		String message = null;
		try {
			userDTO.setIsServiceProvider(true);
			userDTO.setEventOrgId(eventOrgID);
			boolean saveUser = userService.saveEventServiceUser(userDTO);
			if (saveUser) {
				logger.info("useDeatils saved successfully and userName  is :"
						+ userDTO.getUserName());
				// set userName in session.
				session.setAttribute("userName", userDTO.getUserName());

				String confirmationUrl = "http://" + request.getServerName()
						+ ":" + request.getServerPort() + contentPath + "/"
						+ "eventUserRegstrnConfirmtn.htm?token=" + token;
				logger.info("sendMail method : start");
				boolean sendMail = MailUtil
						.sendMail(eventsOrganizerDTO, userDTO,
								servletContext.getRealPath(emailTemplate),
								confirmationUrl, "Your new password" + "tocken"
										+ token);
				logger.info("sendMail method : end");
			message="You have successfully registered with our system. Please click "
					+ "on the activation link sent to your email provided during the registration";
						
			}

		} catch (UserNotUniqueException e) {
			message = e.getMessage();
		} catch (EmailNotUniqueException e) {
			message = e.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
		
		return message;
	}
	@ResponseBody
	@RequestMapping(value = "/eventAjaxUserRegister.htm", method = RequestMethod.POST)
	public String saveUserAjaxDetails(Model model,
			 @ModelAttribute UserBean userBean,Errors errors,
			HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("saveUserDetails controller : start"+userBean);
		
		String message = null;
		/*if(errors.hasErrors()){
			message="errors";
		}*/

		String emailTemplate = "/letterTemplate/registerletterTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String contentPath = request.getContextPath();
		String token = CushinaUtil.generateRandomPassword();

		
		UserDTO userDTO = new UserDTO();
		userBean.setJoinDate(new Date());
		userBean.setToken(token);
		userBean.setStatus("InActive");
		
		BeanUtils.copyProperties(userBean, userDTO);
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();

		EventsOrganizerDTO eventsOrganizerDTO = new EventsOrganizerDTO();
		BeanUtils.copyProperties(eventInfomrtn, eventsOrganizerDTO);
		
		try {
			userDTO.setEventOrgId(eventOrgID);
			userDTO.setIsServiceProvider(false);
			boolean saveUser = userService.saveEventUser(userDTO);
			if (saveUser) {
				logger.info("useDeatils saved successfully and userName  is :"
						+ userDTO.getUserName());
				// set userName in session.
				session.setAttribute("userName", userDTO.getUserName());

				String confirmationUrl = "http://" + request.getServerName()
						+ ":" + request.getServerPort() + contentPath + "/"
						+ "eventUserRegstrnConfirmtn.htm?token=" + token;
				logger.info("sendMail method : start");
				boolean sendMail = MailUtil
						.sendMail(eventsOrganizerDTO, userDTO,
								servletContext.getRealPath(emailTemplate),
								confirmationUrl, "Your new password" + "tocken"
										+ token);
				logger.info("sendMail method : end");
				message="You have successfully registered with our system. Please click "
						+ "on the activation link sent to your email provided during the registration";
						
			}

		} catch (UserNotUniqueException e) {
			message = e.getMessage();
			
		} catch (EmailNotUniqueException e) {
			message = e.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
	
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("saveUserDetails controller : end");
		return message;
	}
	@ResponseBody
	@RequestMapping(value = "getEventUserForEdit.htm", method = RequestMethod.GET)
	public String getUsersForEdit(Model model, HttpSession session,
			HttpServletRequest request) throws ParseException, JsonGenerationException, JsonMappingException, IOException {
		String userName = (String) session.getAttribute("userName");
		logger.info("getUsers controller : start");
		ObjectMapper mapper=new  ObjectMapper();
		UserDTO allusers = userService.getUserDetail(userName);
		Date dateOfBirth = allusers.getDateOfBirth();
		Date joinDate = allusers.getJoinDate();
		DateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy");
		if (dateOfBirth != null) {
			allusers.setDob(dateformt.format(dateOfBirth));
		}
		if (joinDate != null) {
			allusers.setDateJoin(dateformt.format(joinDate));
		}
		String getUser=mapper.writeValueAsString(allusers);
		
		model.addAttribute("getUser", allusers);
	
		// model.addAttribute("image", hotelInfomrtn.getImage());
		logger.info("getUsers controller : end");
		return getUser;

	}
	
}
