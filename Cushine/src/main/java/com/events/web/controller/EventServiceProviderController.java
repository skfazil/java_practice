package com.events.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.exception.EmailNotUniqueException;
import com.cushina.common.exception.UserNotUniqueException;
import com.cushina.common.util.CushinaUtil;
import com.cushina.common.util.MailUtil;
import com.cushina.service.PickHotelService;
import com.cushina.service.UserService;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;
import com.cushina.web.validator.LoginValidator;
import com.cushina.web.validator.RegistrationValidator;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventUserDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.common.util.EventChangeReceiptPDFGeneratingThread;
import com.events.service.EventBookingService;
import com.events.service.PickEventOrganizerService;
import com.events.web.bean.EventsOrganizerBean;
import com.events.web.bean.EventsReservationBean;

@Controller
public class EventServiceProviderController {

	private Logger logger = Logger
			.getLogger(EventServiceProviderController.class.getName());

	@Autowired
	private PickEventOrganizerService service;

	@Autowired
	private RegistrationValidator registrationValidator;

	@Autowired
	private UserService userService;

	@Autowired
	private EventBookingService eventBookingService;

	@Autowired
	private LoginValidator loginValidator;
	@ResponseBody
	@RequestMapping(value = "getEventAdminUserProfile.htm", method = RequestMethod.GET)
	public String getEventServiceProviderProfile(Model model, HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getServiceProviderProfile controller : start");
		String userName = (String) session.getAttribute("servUserName");
		logger.info("inside getServiceProviderProfile : userName :" + userName);
		UserDTO dto = userService.getUserDetail(userName);
		logger.info("inside getServiceProviderProfile : userDto " + dto);
		Date dateOfBirth = dto.getDateOfBirth();
		Date joinDate = dto.getJoinDate();
		DateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy");
		if (dateOfBirth != null) {
			dto.setDob(dateformt.format(dateOfBirth));
		}
		if (joinDate != null) {
			dto.setDateJoin(dateformt.format(joinDate));
		}
		ObjectMapper mapper=new  ObjectMapper();
		logger.info("inside getusers.htm final check of dto : " + dto);
		model.addAttribute("mode", "edit");
		model.addAttribute("getSPUser", dto);
		logger.info("getServiceProviderProfile controller : end");
		String getAdminUser=mapper.writeValueAsString(dto);
		return getAdminUser;
	}
	@RequestMapping(value = "getEventUserProfile.htm", method = RequestMethod.GET)
	public String getServiceProviderProfile(Model model, HttpSession session) {
		logger.info("getServiceProviderProfile controller : start");
		String userName = (String) session.getAttribute("servUserName");
		logger.info("inside getServiceProviderProfile : userName :" + userName);
		UserDTO dto = userService.getUserDetail(userName);
		logger.info("inside getServiceProviderProfile : userDto " + dto);
		Date dateOfBirth = dto.getDateOfBirth();
		Date joinDate = dto.getJoinDate();
		DateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy");
		if (dateOfBirth != null) {
			dto.setDob(dateformt.format(dateOfBirth));
		}
		if (joinDate != null) {
			dto.setDateJoin(dateformt.format(joinDate));
		}
		logger.info("inside getusers.htm final check of dto : " + dto);
		model.addAttribute("mode", "edit");
		model.addAttribute("getSPUser", dto);
		logger.info("getServiceProviderProfile controller : end");
		return "serviceprovider_event";
	}

	@RequestMapping(value = "/updateServiceProfile.htm", method = RequestMethod.POST)
	public String upDateProfile(@ModelAttribute("getSPUser") UserBean userBean,
			Errors errors, Model model, HttpSession session)
			throws EmailNotUniqueException {
		logger.info("updateProfile controller : start");

		registrationValidator.validate(userBean, errors);

		UserDTO userDTo = new UserDTO();
		logger.info("inside updateProfile userBean : " + userBean);
		BeanUtils.copyProperties(userBean, userDTo);

		String message = null;

		if (errors.hasErrors()) {
			logger.info(errors);
			model.addAttribute("mode", "edit");
			model.addAttribute("getSPUser", userBean);
			logger.info("updateProfile controller : end");
			return "serviceprovider_event";
		}

		String phoneNumber = userBean.getPhoneNumber();
		String dob = userBean.getDob();
		if (phoneNumber != null) {
			userDTo.setContactNumber(Long.parseLong(phoneNumber));
		}
		DateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
		logger.info("date format : " + formt);
		try {
			userDTo.setDateOfBirth(formt.parse(dob));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			boolean updateUser = userService.updateUser(userDTo);
			logger.info("inside updateProfile(): updateUser true/false : "
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

		model.addAttribute("mode", "edit");
		model.addAttribute("getSPUser", userBean);
		logger.info("updateUser controller : end");
		return "serviceprovider_event";
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

	/**
	 * In event side showing all customer list by using this method customer
	 * list
	 */
	@RequestMapping(value = "eventCustmrList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String adminCustmrList(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("adminCustmrList controller : start");
		EventsOrganizerBean bean = (EventsOrganizerBean) session
				.getAttribute("event");
		System.out.println("bean id:::" + bean.getEventOrgId());
		Integer orgId = bean.getEventOrgId();
		List<UserBean> cstmrLst = new ArrayList<UserBean>();
		UserBean cstmr = null;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<UserDTO> customersList = eventBookingService
				.getCustomersList(orgId);
		logger.info("user list :" + customersList);
		for (UserDTO userDTO : customersList) {
			cstmr = new UserBean();
			BeanUtils.copyProperties(userDTO, cstmr);
			cstmrLst.add(cstmr);
		}
		logger.info("adminCustmrList controller : end");
		json = mapper.writeValueAsString(cstmrLst);
		return json;
	}

	@RequestMapping(value = "exportEventCustomerLstCSV.htm", method = RequestMethod.GET)
	public void exportCSV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("exportCSV controller : start");
		String userName = (String) request.getParameter("userName");
		logger.info("userName ::" + userName);
		String csvFileName = "customerDetails.csv";

		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		UserDTO userdto = userService.getUserDetail(userName);

		logger.info("userdto in controller -->" + userdto);
		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "firstName", "lastName", "email", "userName",
				"streetName", "city", "country", "postalCode", "phoneNumber" };

		csvWriter.writeHeader(header);

		// for (Book aBook : listBooks) {
		csvWriter.write(userdto, header);
		// }
		csvWriter.close();
		logger.info("exportCSV controller : end");
	}
	
	
	@RequestMapping(value = "exportAllEventCustomerLstCSV.htm", method = RequestMethod.GET)
	public void exportAllCSV(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		logger.info("exportCSV controller : start");
		EventsOrganizerBean bean = (EventsOrganizerBean) session
				.getAttribute("event");
		System.out.println("bean id:::" + bean.getEventOrgId());
		Integer orgId = bean.getEventOrgId();
		String csvFileName = "customerDetails.csv";

		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
		
		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "firstName", "lastName", "email", "userName",
				"streetName", "city", "country", "postalCode", "phoneNumber" };

		csvWriter.writeHeader(header);

		// for (Book aBook : listBooks) {
		
		// }
		UserBean cstmr = null;
		List<UserDTO> customersList = eventBookingService
				.getCustomersList(orgId);
		logger.info("user list :" + customersList);
		for (UserDTO userDTO : customersList) {
			cstmr = new UserBean();
			BeanUtils.copyProperties(userDTO, cstmr);
			csvWriter.write(cstmr, header);
			//cstmrLst.add(cstmr);
		}
	
		csvWriter.close();
		logger.info("exportCSV controller : end");
	}
	
	

	/**
	 * In event side showing all customer booking list by using this method
	 * customer reservation
	 */
	@RequestMapping(value = "eventcustmrsBookingHistory.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCustmrsbookingHistory(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getCustmrsbookingHistory controller : start");
		EventsOrganizerBean bean = (EventsOrganizerBean) session
				.getAttribute("event");
		System.out.println("bean id:::" + bean.getEventOrgId());
		Integer orgId = bean.getEventOrgId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<EventsReservationBean> histryBeanLst = new ArrayList<EventsReservationBean>();
		EventsReservationBean eventResvBean = null;

		try {
			List<EventsReservationDTO> custmrsHistry = eventBookingService
					.getCustmrsHistry(orgId);
			System.out.println("resv history ::::" + custmrsHistry);
			for (EventsReservationDTO reservationHistoryDTO : custmrsHistry) {
				eventResvBean = new EventsReservationBean();
				BeanUtils.copyProperties(reservationHistoryDTO, eventResvBean);
				histryBeanLst.add(eventResvBean);
			}
			logger.info("histryBeanLst ::: " + histryBeanLst);
			json = mapper.writeValueAsString(histryBeanLst);
		} catch (Exception ex) {
			logger.info("came to catch block getCustmrsbookingHistory controller....");
			String status = "there is no customer history exists for this hotel";
			eventResvBean = new EventsReservationBean();
			eventResvBean.setStatus(status);
			json = mapper.writeValueAsString(eventResvBean);
		}

		logger.info("getCustmrsbookingHistory controller : end");
		return json;
	}

	@RequestMapping(value = "getEventsCustomerReservtnDetails.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCustomerReservtnDetails(
			@RequestParam("cstmrName") String userName, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getCustomerReservtnDetails controller : start");
		EventsOrganizerBean bean = (EventsOrganizerBean) session
				.getAttribute("event");
		System.out.println("bean id:::" + bean.getEventOrgId());
		Integer orgId = bean.getEventOrgId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<EventsReservationBean> customerHistryList = new ArrayList<EventsReservationBean>();
		EventsReservationBean histryBean = null;

		try {
			List<EventsReservationDTO> customerReservtnHistryRecords = eventBookingService
					.getCustomerReservtnHistryRecords(orgId, userName);
			System.out.println("customerReservtnHistryRecords ::"
					+ customerReservtnHistryRecords);
			for (EventsReservationDTO bookingHistoryDTO : customerReservtnHistryRecords) {
				histryBean = new EventsReservationBean();
				BeanUtils.copyProperties(bookingHistoryDTO, histryBean);
				customerHistryList.add(histryBean);
				json = mapper.writeValueAsString(customerHistryList);
			}
		} catch (Exception ex) {
			ex.getMessage();
			String statusMsg = "user name is not exits in list";
			json = mapper.writeValueAsString(statusMsg);
			logger.info("customer name does n't exists in both user_profile and guestUser_profile tables.");
		}
		logger.info("getCustomerReservtnDetails controller : end");
		return json;
	}

	@RequestMapping(value = "exportEventCustmrReservntHstryCSV.htm", method = RequestMethod.GET)
	public void exportCustmrReservntHstryCSV(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("exportCustmrReservntHstryCSV controller : start");
		String userName = (String) request.getParameter("un");
		EventsOrganizerBean bean = (EventsOrganizerBean) session
				.getAttribute("event");
		System.out.println("bean id:::" + bean.getEventOrgId());
		Integer orgId = bean.getEventOrgId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		String csvFileName = "customerResrvtnHstry.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		List<EventsReservationBean> customerHistryList = new ArrayList<EventsReservationBean>();
		EventsReservationBean histryBean = null;

		try {
			List<EventsReservationDTO> customerReservtnHistryRecords = eventBookingService
					.getCustomerReservtnHistryRecords(orgId, userName);
			for (EventsReservationDTO bookingHistoryDTO : customerReservtnHistryRecords) {
				histryBean = new EventsReservationBean();
				BeanUtils.copyProperties(bookingHistoryDTO, histryBean);
				customerHistryList.add(histryBean);

				json = mapper.writeValueAsString(customerHistryList);
			}
		} catch (Exception ex) {
			ex.getMessage();
			String statusMsg = "user name in not in list";
			json = mapper.writeValueAsString(statusMsg);
			logger.info("customer name does n't exists in both user_profile and guestUser_profile tables.");
		}

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "userName", "referenceNumber", "startTime",
				"endTime", "reserveId", "duration", "email", "phoneNumber" };

		csvWriter.writeHeader(header);

		for (EventsReservationBean cusHstry : customerHistryList) {
			csvWriter.write(cusHstry, header);
		}

		csvWriter.close();

		logger.info("exportCustmrReservntHstryCSV controller : end");
	}
	
	
	
	@RequestMapping(value = "exportAllEventCustmrReservntHstryCSV.htm", method = RequestMethod.GET)
	public void exportAllCustmrReservntHstryCSV(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("exportCustmrReservntHstryCSV controller : start");
		String userName =null;
		
		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("userId");
		if (userInfo != null) {
			userId = userInfo.getUserId();
			userName = userInfo.getUserName();
		} else {
			userInfo = (UserDTO) session.getAttribute("servUserId");
			userId = userInfo.getUserId();
			userName = userInfo.getUserName();
		}
		EventsOrganizerBean bean = (EventsOrganizerBean) session
				.getAttribute("event");
		System.out.println("bean id:::" + bean.getEventOrgId());
		Integer orgId = bean.getEventOrgId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		String csvFileName = "customerResrvtnHstry.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		List<EventsReservationBean> customerHistryList = new ArrayList<EventsReservationBean>();
		EventsReservationBean histryBean = null;

		try {
			List<EventsReservationDTO> customerReservtnHistryRecords =eventBookingService.getCustomerReservationHistryRecords(orgId);
			for (EventsReservationDTO bookingHistoryDTO : customerReservtnHistryRecords) {
				histryBean = new EventsReservationBean();
				BeanUtils.copyProperties(bookingHistoryDTO, histryBean);
				customerHistryList.add(histryBean);

				json = mapper.writeValueAsString(customerHistryList);
			}
		} catch (Exception ex) {
			ex.getMessage();
			String statusMsg = "user name in not in list";
			json = mapper.writeValueAsString(statusMsg);
			logger.info("customer name does n't exists in both user_profile and guestUser_profile tables.");
		}

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "userName", "referenceNumber", "startTime",
				"endTime", "reserveId", "duration", "email", "phoneNumber" };

		csvWriter.writeHeader(header);

		for (EventsReservationBean cusHstry : customerHistryList) {
			csvWriter.write(cusHstry, header);
		}

		csvWriter.close();

		logger.info("exportCustmrReservntHstryCSV controller : end");
	}
	
	@RequestMapping(value = "changeEventReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String changeReservation(
			@RequestParam("draggableInfo") String dragableInfo,
			@RequestParam("droppableInfo") String dropableInfo, HttpServletRequest request,HttpSession session) throws ParseException {
		
		logger.info("changeReservation controller : start");
		logger.info("dragable info :" + dragableInfo);
		logger.info("dropable info :" + dropableInfo);
		String result=null;
		String[] dragable = dragableInfo.split(",");
		Integer scheduleId = Integer.valueOf(dragable[0]);
		/*Integer eventOrgId = Integer.valueOf(dragable[1]);
		Integer eventId = Integer.valueOf(dragable[2]);
		Integer availableSeats = Integer.valueOf(dragable[3]);*/

		String[] dropable = dropableInfo.split(",");
		Integer scheduledId = Integer.valueOf(dropable[0]);
		if(scheduleId.equals(scheduledId)) {
			return result;
		}else {
			Integer eventOrgtnId = Integer.valueOf(dropable[1]);
			Integer evntId = Integer.valueOf(dropable[2]);
			String strtTime=dropable[5].substring(4,24);
			String endTime=dropable[6].substring(4,24);
			System.out.println("StartTime--->"+strtTime);
			System.out.println("eND tIME--->"+endTime);
			String guideName=dropable[7];
			String eventName=dropable[8];
			
			SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
			Date startDate=dateFormat.parse(strtTime);
			Date endDate=dateFormat.parse(endTime);
			
			
			System.out.println("Start time--->"+strtTime);
			System.out.println("end Time--->"+endTime);
			System.out.println("Guide Nmae--->"+guideName);
			System.out.println("eventName--->"+eventName);
		
			EventsOrganizerBean organizer = (EventsOrganizerBean) session
					.getAttribute("event");
			EventsOrganizerDTO orgDto = new EventsOrganizerDTO();
			BeanUtils.copyProperties(organizer, orgDto);
			
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
			 String emailTemplate = "/letterTemplate/changeEvent.html";
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
			//BeanUtils.copyProperties(userDto, bean);
			
			String subject = "Confirmation of you Tour reservation with Woofre!";

			List<EventsReservationDTO> reservedUserRecords = service
					.getReservedUserRecords(scheduleId);
			EventScheduleDTO eventScheduleDTO=service.getScheduleById(scheduleId);
			List<EventScheduleDTO> eventScheduleDTOList=new ArrayList<EventScheduleDTO>();
			EventScheduleDTO scheduleDTO=null;
			for (EventsReservationDTO eventsReservationDTO : reservedUserRecords) {
				
				scheduleDTO=new EventScheduleDTO();
				Long oldReferenceNum = eventsReservationDTO.getReferenceNumber();
				EventsReservationDTO reserveSeatsForUser = service
						.reserveSeatsForUser(eventsReservationDTO, scheduledId,
								eventOrgtnId, evntId);
				System.out.println("Reservation DTO----->"+reserveSeatsForUser);
				UserDTO userDetails=service.getUserDetails(reserveSeatsForUser.getUserID());
				reserveSeatsForUser.getNoOfPeople();
				System.out.println("UserDto--->"+userDetails);
				eventScheduleDTO.setScheduleId(scheduleId);
				eventScheduleDTO.setEmail(userDetails.getEmail());
				eventScheduleDTO.setPhoneNumber(userDetails.getPhoneNumber());
				eventScheduleDTO.setUserName(userDetails.getUserName());
				eventScheduleDTO.setRefNumber(reserveSeatsForUser.getReferenceNumber());
				eventScheduleDTO.setBookedSeats(reserveSeatsForUser.getNoOfPeople());
				eventScheduleDTO.setStartTime(startDate);
				eventScheduleDTO.setEndTime(endDate);
				eventScheduleDTO.setStrtTme(strtTime);
				eventScheduleDTO.setEndTme(endTime);
				eventScheduleDTO.setEventName(eventName);
				eventScheduleDTO.setGuideName(guideName);
				BeanUtils.copyProperties(eventScheduleDTO, scheduleDTO);
				eventScheduleDTOList.add(scheduleDTO);
				
				/*sendMailThread(request, orgDto, eventScheduleDTO,
						servletContext.getRealPath(emailTemplate), subject,
						imgRealPathMap, pdfRealPath, session);	*/
			}
		/*	session.setAttribute("request",request);
			session.setAttribute("organizerDetails",orgDto);
			session.setAttribute("eventScheduledList", eventScheduleDTOList);
			session.setAttribute("emailTemplate", servletContext.getRealPath(emailTemplate));
			session.setAttribute("mailSubject",subject);
			session.setAttribute("imgMap",imgRealPathMap);
			session.setAttribute("pdfRealPath", pdfRealPath);*/
			//session.setAttribute("session",session);
			EventChangeReceiptPDFGeneratingThread mailThread=new EventChangeReceiptPDFGeneratingThread(request, orgDto, eventScheduleDTOList,
					servletContext.getRealPath(emailTemplate), subject,
					imgRealPathMap, pdfRealPath, session);
			mailThread.start();
			
			
			List<EventsReservationDTO> updateReservtnSatatus = service
					.updateUsersReservtnStatus(scheduleId);
			boolean updateScheduleRecrd = service
					.isUpdateEventScheduleRecord(scheduleId);
			
		
			if(updateScheduleRecrd==true) {
				result="true";
			}
		}
		
		logger.info("changeReservation controller : end");
		return result;
	}
	/*@RequestMapping(value = "eventChangeReservationMail.htm", method = RequestMethod.POST)
	public void eventChangeReservationMail(HttpSession httpSession) {
		HttpServletRequest request=(HttpServletRequest) httpSession.getAttribute("request");
		EventsOrganizerDTO orgDto=(EventsOrganizerDTO) request.getAttribute("organizerDetails");
		List<EventScheduleDTO> eventScheduleDTOList=(List<EventScheduleDTO>) httpSession.getAttribute("eventScheduledList");
		String realPath=(String) httpSession.getAttribute("emailTemplate");
		String subject=(String) httpSession.getAttribute("mailSubject");
		Map<String, String> imgRealPathMap=(Map<String, String>) httpSession.getAttribute("imgMap");
		String pdfRealPath=(String) httpSession.getAttribute("pdfRealPath");
		//httpSession.getAttribute("session");
		
		EventChangeReceiptPDFGeneratingThread mailThread=new EventChangeReceiptPDFGeneratingThread(request, orgDto, eventScheduleDTOList,
				realPath, subject,
				imgRealPathMap, pdfRealPath, httpSession);
		mailThread.start();
	}*/


}
