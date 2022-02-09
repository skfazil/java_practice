package com.cushina.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.exception.EmailNotUniqueException;
import com.cushina.common.util.PDFGeneratingThread;
import com.cushina.service.PickHotelService;
import com.cushina.service.ReservationService;
import com.cushina.service.ServiceProviderChangeRoomService;
import com.cushina.service.ServiceProviderService;
import com.cushina.service.UserService;
import com.cushina.web.bean.AvailabilityByDateBean;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;
import com.cushina.web.validator.RegistrationValidator;

@Controller
public class ServiceProviderChangeRoomController {

	private Logger logger = Logger
			.getLogger(ServiceProviderChangeRoomController.class.getName());

	@Autowired
	UserService userService;

	@Autowired
	private RegistrationValidator validator;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private PickHotelService pickHotelService;

	@Autowired
	private ServiceProviderService serviceProvider;

	@Autowired
	private ServiceProviderChangeRoomService changeRoomService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd/MM/yyyy"), true));
	}

	@RequestMapping(value = "initChangeRoom.htm", method = RequestMethod.GET)
	public String initChangeRoom(Model model) {
		model.addAttribute("getChangeSPUser", new UserBean());
		return "serviceprovider_change_room";
	}

	@RequestMapping(value = "returnHmepage.htm", method = RequestMethod.GET)
	public String returnHme(Model model) {
		model.addAttribute("getChangeSPUser", new UserBean());
		return "serviceprovider_change_room";
	}
	
	@RequestMapping(value="dragAndDropPage.htm",method=RequestMethod.GET)
	public String dragAndDrop(Model model,HttpSession session){
		UserDTO usrDTO = (UserDTO) session.getAttribute("servUserId");
		List<HotelBean> hotelDetail = pickHotelService.getHotelDetail(usrDTO
				.getHotelID());
		session.getAttribute("servUserName");
		HotelBean bean = new HotelBean();
		BeanUtils.copyProperties(hotelDetail.get(0), bean);
		session.setAttribute("hotel", bean);
		model.addAttribute("getChangeSPUser", new UserBean());
	return "serviceprovider_change_room";
	}

	@RequestMapping(value = "getRoomsInfoOnCurrDt.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomsInfoOnCurrDt(
			@RequestParam("noOfDays") Integer noOfDays, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getRoomsInfoOnCurrDt controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		List<AvailabilityByDateBean> roomsAvailList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> roomsOnCurrentDate = serviceProvider
				.getRoomsOnCurrentDate(hotelInfomrtn.getHotelID(), noOfDays);
		for (AvailabilityByDateDTO availabilityByDateDTO : roomsOnCurrentDate) {
			byDateBean = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);
			boolean arrived = byDateBean.isArrived();
			boolean paid = byDateBean.isPaid();
			if (arrived == true) {
				byDateBean.setArrivedVal("1");
			} else {
				byDateBean.setArrivedVal("0");
			}
			if (paid == true) {
				byDateBean.setPaidVal("1");
			} else {
				byDateBean.setPaidVal("0");
			}
			roomsAvailList.add(byDateBean);
		}
		json = mapper.writeValueAsString(roomsAvailList);

		model.addAttribute("getChangeSPUser", new UserBean());
		logger.info("getRoomsInfoOnCurrDt controller : end");
		return json;
	}

	@RequestMapping(value = "changeReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String changeReservation(HttpServletRequest request,
			@RequestParam("draggableVal") String draggableVal,
			@RequestParam("droppableVal") String droppableVal,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("changeReservation controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		String reservationTemplatePath = "/letterTemplate/changeReservation.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String emailTemplateRealPath = servletContext
				.getRealPath(reservationTemplatePath);

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();

		BookingHistoryBean historyBean = new BookingHistoryBean();
		BookingHistoryDTO changeReservation = changeRoomService
				.changeReservation(draggableVal, droppableVal, hotelId);
		BeanUtils.copyProperties(changeReservation, historyBean);
		
		historyBean.setChckedInDate(historyBean.getCheckInDt());
		historyBean.setChckedOutDate(historyBean.getCheckOutDt());
		PDFGeneratingThread pdfGeneratingThread = new PDFGeneratingThread(
				request, hotelInfomrtn, historyBean, historyBean.getUserName(),
				emailTemplateRealPath, historyBean.getEmail(), session);
		pdfGeneratingThread.start();

		logger.info("isChangeReservn : " + changeReservation);
		logger.info("changeReservation controller : end");
		json = mapper.writeValueAsString(changeReservation);
		return json;
	}

	@RequestMapping(value = "getChngeProfile.htm", method = RequestMethod.GET)
	public String getServiceProviderProfile(Model model, HttpSession session) {
		logger.info("getServiceProviderProfile controller : start");
		String userName = (String) session.getAttribute("servUserName");
		logger.info("inside getServiceProviderProfile : userName :" + userName);
		UserDTO dto = userService.getUserDetail(userName);
		logger.info("inside getServiceProviderProfile : userDto " + dto);

		Date dateOfBirth = dto.getDateOfBirth();
		DateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
		if (dateOfBirth != null) {
			dto.setDob(formt.format(dateOfBirth));
		}
		logger.info("inside getusers.htm final check of dto : " + dto);
		model.addAttribute("mode", "edit");
		model.addAttribute("getChangeSPUser", dto);
		logger.info("getServiceProviderProfile controller : end");
		return "serviceprovider_change_room";
	}

	@RequestMapping(value = "/updateChngeProfile.htm", method = RequestMethod.POST)
	public String upDateProfile(
			@ModelAttribute("getChangeSPUser") UserBean userBean,
			Errors errors, Model model, HttpSession session)
			throws EmailNotUniqueException {
		logger.info("updateProfile controller : start");

		validator.validate(userBean, errors);

		UserDTO userDTo = new UserDTO();
		logger.info("inside updateProfile userBean : " + userBean);
		BeanUtils.copyProperties(userBean, userDTo);

		String message = null;

		if (errors.hasErrors()) {
			logger.info(errors);
			model.addAttribute("mode", "edit");
			model.addAttribute("getChangeSPUser", userBean);
			logger.info("updateProfile controller : end");
			return "serviceprovider_change_room";
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
		model.addAttribute("getChngeSPUser", userBean);
		logger.info("updateUser controller : end");
		return "serviceprovider_change_room";
	}

	@RequestMapping(value = "/getMyChngeReservations.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getBookingDetails(Model model, HttpServletRequest request,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getBookingDetails controller : start");

		UserDTO dto = (UserDTO) session.getAttribute("servUserId");
		Integer userId1 = dto.getUserId();
		Long userId2 = new Long(userId1);
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		UserDTO userDTO = null;

		userDTO = serviceProvider.getUserById(userId1);
		String userName = userDTO.getUserName();

		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		// Map<HotelBean, BookingHistoryBean> map = new HashMap<HotelBean,
		// BookingHistoryBean>();
		List<BookingHistoryBean> historyBeanList = new ArrayList<BookingHistoryBean>();
		BookingHistoryBean historyBean = null;
		List<BookingHistoryDTO> historyDtoList = null;
		if (userName != null) {
			historyDtoList = serviceProvider.getBookingHistoryDetails(userId2,
					hotelID);
		}
		logger.info("historyDtoList : " + historyDtoList);

		Map<Long, String> categoryListByMap = pickHotelService
				.getCategoryListByMap(hotelID);

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		long previousDt = previousDate.getTime();
		for (BookingHistoryDTO bookDto : historyDtoList) {
			Date checkIn = bookDto.getCheckInDate();
			Date checkOut = bookDto.getCheckOutDate();
			long time = checkIn.getTime();
			if (time <= previousDt)
				bookDto.setStatus("not visited");

			historyBean = new BookingHistoryBean();
			BeanUtils.copyProperties(bookDto, historyBean);
			Long categoryId = bookDto.getCategoryId();
			String categryName = categoryListByMap.get(categoryId);
			logger.info("categoryName : " + categryName);
			if (bookDto.isArrived()) {
				historyBean.setArrivedVal("1");
			} else {
				historyBean.setArrivedVal("0");
			}
			historyBean.setCategoryName(categryName);
			historyBean.setChckedInDate(format.format(checkIn));
			historyBean.setChckedOutDate(format.format(checkOut));
			historyBeanList.add(historyBean);
		}

		Collections.sort(historyBeanList, new Comparator<BookingHistoryBean>() {

			public int compare(BookingHistoryBean histryBeanOne,
					BookingHistoryBean histryBeanTwo) {
				return histryBeanOne.getCheckInDate().compareTo(
						histryBeanTwo.getCheckInDate());
			}
		});
		logger.info("inside getMyReservation end: historyBeanList"
				+ historyBeanList);
		json = mapper.writeValueAsString(historyBeanList);
		model.addAttribute("getChangeSPUser", new UserBean());
		logger.info("getBookingDetails controller : end");
		return json;
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

	@RequestMapping(value = "returnToChngeServiceHme.htm", method = RequestMethod.GET)
	public String initReturnToServiceHme(Model model, HttpServletRequest request) {
		logger.info("initReturnToServiceHme : start");
		UserBean userBean = new UserBean();
		model.addAttribute("getChangeSPUser", userBean);
		logger.info("initReturnToHme : end");
		return "serviceprovider_change_room";
	}
}
