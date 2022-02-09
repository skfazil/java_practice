package com.cushina.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.CategoryDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.service.PickHotelService;
import com.cushina.service.ReservationByRoomService;
import com.cushina.service.UserService;
import com.cushina.web.bean.AvailabilityByDateBean;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.CategoryBean;
import com.cushina.web.bean.HotelAvailabilityBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;

@Controller
@SessionAttributes(value = { "userName", "hotel", "userId", "hotelDetails",
		"currentDate" })
public class PickHotelController {

	private Logger logger = Logger.getLogger(PickHotelController.class
			.getName());

	@Autowired
	private PickHotelService service;

	@Autowired
	private UserService userService;

	@Autowired
	private ReservationByRoomService roomService;
	
	
	public String tstRender(){
		
		return null;
	}

	@RequestMapping(value = "returnToHme.htm", method = RequestMethod.GET)
	public String initReturnToHme(Model model, HttpServletRequest request) {
		logger.info("initReturnToHme : start");
		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		logger.info("initReturnToHme : end");
		return "hotel_reservation";
	}

	
	@RequestMapping(value = "/pickHotel.htm", method = RequestMethod.GET)
	public String intiPickHotel(Model model, HttpServletRequest request,
			HttpSession session) {
		logger.info("pickHotelController  :  start");
		String hotelName = (String) request.getParameter("name");
		List<HotelDTO> hotelInfo = service.getHotelDetails(hotelName);
		HotelDTO hotel = null;
		hotel = new HotelDTO();
		hotel.getImage();
		model.addAttribute("image", hotelInfo.get(0).getImage());
		for (HotelDTO hotelDetails : hotelInfo) {
			hotel = new HotelDTO();
			BeanUtils.copyProperties(hotelDetails, hotel);
		}
		HotelBean bean = new HotelBean();
		BeanUtils.copyProperties(hotel, bean);
		session.setAttribute("hotel", bean);
		
		invalidateUsrAndProviderSession(session);

		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("login", "login");
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		logger.info("pickHotelController  :  end");
		return "hotel_reservation";
	}

	private void invalidateUsrAndProviderSession(HttpSession session) {
		session.setAttribute("loggedIn", null);
		session.setAttribute("providerLoggedIn", null);
		session.setAttribute("servUserId", null);
		session.setAttribute("servUserName", null);
		session.setAttribute("userName", null);
		session.setAttribute("userId", null);
	}

	@RequestMapping(value = "getContactDetail.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getContact(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getContact controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		List<HotelBean> hotelDet = service.getHotelDetail(hotelID);
		System.out.println("hotel address" + hotelDet.get(0).getHotelAddress());
		model.addAttribute("hotelDet", hotelDet);
		Set<HotelBean> hotelDetails = new LinkedHashSet<HotelBean>(hotelDet);
		session.setAttribute("hotelDetails", hotelDetails);

		json = mapper.writeValueAsString(hotelDet);
		model.addAttribute("image", hotelDet.get(0).getImage());
		logger.info("getContact controller : end");

		return json;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadPDF.htm", method = RequestMethod.GET)
	public String downloadPDF(Model model, HttpSession session)
			throws Exception {
		logger.info("downloadPDF : start ");
		LinkedHashSet<HotelBean> reportType = (LinkedHashSet<HotelBean>) session
				.getAttribute("hotelDetails");
		model.addAttribute("listOfAPIs", reportType);
		model.addAttribute("reportType", "Address");
		logger.info("downloadPDF : end ");
		return "addressPDF";
	}

	@RequestMapping(value = "getGreenUserDetails.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getUserDetails(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getUserDetails : start");
		ObjectMapper mapper = new ObjectMapper();
		String userName = (String) session.getAttribute("userName");
		if(userName == null ){
			userName = (String) session.getAttribute("servUserName");
		}
		String json = null;
		if (userName != null) {
			logger.info("user name getting frm session :" + userName);
			UserDTO userDTO = userService.getUserDetail(userName);
			logger.info("user details :" + userDTO);
			UserBean userDetails = new UserBean();
			BeanUtils.copyProperties(userDTO, userDetails);
			json = mapper.writeValueAsString(userDetails);
			logger.info("user data in JSON format :" + json);
		}
		logger.info("getUserDetails : end");
		return json;
	}

	@RequestMapping(value = "/getSelectedDateAvailRooms.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getSelectedDateAvailRooms(
			@RequestParam("date") String selectedDate,
			@RequestParam("selectedCategory") Long categoryID,
			@RequestParam("noOfDays") Integer dayCount, HttpSession session,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("getSelectedDateRooms controller : start");
		
		logger.info("operation start time ---111--- : "+System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String json = null;
		ObjectMapper mapper = new ObjectMapper();

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		// get username from session.
		String userName = (String) session.getAttribute("userName");

		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);

		List<AvailabilityByDateBean> byDateBeans = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> availRooms = null;
		if (categoryID == 0) {
			availRooms = service.getSelectedDateAvailRooms(selectedDate,
					hotelID, userName);
		} else {
			availRooms = service.getSelectedDateAvailRoomsBasedOnCatgeory(
					selectedDate, hotelID, categoryID, dayCount, userName);
		}

		for (AvailabilityByDateDTO availabilityByDateDTO : availRooms) {
			byDateBean = new AvailabilityByDateBean();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categoryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categoryName);
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);
			Date date = byDateBean.getDate();
			String roomAvalDt = format.format(date);
			byDateBean.setRoomAvailDate(roomAvalDt);
			byDateBeans.add(byDateBean);
		}

		json = mapper.writeValueAsString(byDateBeans);
		logger.info("operation end time  ---222---: "+System.currentTimeMillis());
		logger.info("getSelectedDateRooms controller : end");
		return json;
	}

	/**
	 * This method is used to get details based on category and room number
	 * selected by user.
	 * 
	 * @param selectedCategory
	 * @param noOfDays
	 * @param selectedDt
	 * @param model
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "getRoomsByDayCountAndCategory.htm", method = RequestMethod.POST)
	@ResponseBody
	public String initGetRoomsByDayCount(
			@RequestParam("selectedCategory") Long selectedCategory,
			@RequestParam("noOfDays") Integer noOfDays,
			@RequestParam("currentDate") String selectedDt, Model model,
			HttpSession session) throws ParseException,
			JsonGenerationException, JsonMappingException, IOException {
		logger.info("getRoomsByDayCount controller: start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		DateFormat formt = new SimpleDateFormat("dd/MM/yyyy");

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = 0L;
		if (hotelInfomrtn != null) {
			hotelID = hotelInfomrtn.getHotelID();
		}

		// getting userName from session.
		String userName = (String) session.getAttribute("userName");

		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);

		/**
		 * This method is used to get room info based on date selected and
		 * category type selected.
		 */
		// SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateBean> byDateBeans = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> roomAvailByCategory = service
				.getSelectedDateAvailRoomsBasedOnCatgeory(selectedDt, hotelID,
						selectedCategory, noOfDays, userName);

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByCategory) {
			byDateBean = new AvailabilityByDateBean();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categryName);
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);

			Date date = byDateBean.getDate();
			String roomAvalDt = formt.format(date);
			byDateBean.setRoomAvailDate(roomAvalDt);
			byDateBeans.add(byDateBean);
		}

		logger.info("byDateBeans $$$ " + byDateBeans);
		json = mapper.writeValueAsString(byDateBeans);
		logger.info("getRoomsByDayCount controller: end");
		return json;
	}

	@RequestMapping(value = "roomInfoByCategoryType.htm", method = RequestMethod.POST)
	@ResponseBody
	public String roomInfoByCategoryType(
			@RequestParam("type") Long categoryType,
			@RequestParam("currDarte") String currDate, Model model,
			HttpSession session) throws ParseException,
			JsonGenerationException, JsonMappingException, IOException {
		logger.info("roomInfoByCategoryType controller : start");

		String availRoomsByCatgry = null;
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
		Date selectedDt = formt.parse(currDate);
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		// getting userName from session.
		String userName = (String) session.getAttribute("userName");

		Long hotelID = 0L;
		if (hotelInfomrtn != null) {
			hotelID = hotelInfomrtn.getHotelID();
		}

		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);

		/**
		 * This method is used to get room info based on date selected and
		 * category type selected.
		 */
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateBean> byDateBeans = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> roomAvailByCategory = service
				.getRoomAvailByCategory(categoryType, hotelID, selectedDt,
						userName);

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByCategory) {
			byDateBean = new AvailabilityByDateBean();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categryName);
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);

			Date date = byDateBean.getDate();
			String roomAvalDt = format.format(date);
			byDateBean.setRoomAvailDate(roomAvalDt);
			byDateBeans.add(byDateBean);
		}

		availRoomsByCatgry = mapper.writeValueAsString(byDateBeans);
		logger.info("roomInfoByCategoryType controller : end");
		return availRoomsByCatgry;
	}

	@RequestMapping(value = "dbclk.htm", method = RequestMethod.POST)
	@ResponseBody
	public String dbClick(@RequestParam("noOfDays") Integer noOfDays,
			HttpSession session, Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("dbClick controller : start");
		String roomListInfo = null;
		ObjectMapper mapper = new ObjectMapper();
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		// getting userName from session.
		String userName = (String) session.getAttribute("userName");
		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);
		logger.info("category map info in controller :" + categoryListByMap);

		/**
		 * Below snippet is used to get availRoomInfo and shows it on room
		 * reservation grid.
		 */
		List<AvailabilityByDateBean> byDateBeans = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean dateBean = null;
		List<AvailabilityByDateDTO> roomAvailInfo = service.getRoomAvailInfo(
				hotelID, noOfDays, userName);

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
			dateBean = new AvailabilityByDateBean();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categryName);
			BeanUtils.copyProperties(availabilityByDateDTO, dateBean);
			byDateBeans.add(dateBean);
		}
		roomListInfo = mapper.writeValueAsString(byDateBeans);
		logger.info("pickHotelController  :  end");
		return roomListInfo;
	}

	@RequestMapping(value = "showDates.htm", method = RequestMethod.POST)
	@ResponseBody
	public String showDates(@RequestParam("noOfDays") Integer noOfDays,
			@RequestParam("catgry") Long categoryId, HttpSession session,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("showDates controller : start");
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		List<HotelAvailabilityBean> listOfDates = new ArrayList<HotelAvailabilityBean>();
		HotelAvailabilityBean hotelAvailability = null;
		List<HotelAvailabilityDTO> datesBadedOnNoOfDays = service
				.getDatesBadedOnNoOfDays(hotelID, noOfDays, categoryId);
		for (HotelAvailabilityDTO hotelAvailabilityDTO : datesBadedOnNoOfDays) {
			hotelAvailability = new HotelAvailabilityBean();
			BeanUtils.copyProperties(hotelAvailabilityDTO, hotelAvailability);
			listOfDates.add(hotelAvailability);
		}
		logger.info("List of avail dates " + listOfDates);
		json = mapper.writeValueAsString(listOfDates);
		logger.info("showDates controller : end");
		return json;
	}

	@RequestMapping(value = "getSelectedDatesListOnCatgryType.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getSeleectedDatesListOnCatgryType(
			@RequestParam("selctdDate") String selectedDate,
			@RequestParam("catVal") Long categoryId,
			@RequestParam("noOfDays") Integer noOfDays, HttpSession session,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("getSeleectedDatesListOnCatgryType controller : start");

		/**
		 * get hotelId value from session and pass it as parameter.
		 */
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		List<HotelAvailabilityBean> listOfDates = new ArrayList<HotelAvailabilityBean>();
		HotelAvailabilityBean hotelAvailability = null;
		List<HotelAvailabilityDTO> datesBadedOnNoOfDays = service
				.getDatesBadedOnNoOfDaysAndSelectedDate(selectedDate, hotelID,
						noOfDays, categoryId);
		for (HotelAvailabilityDTO hotelAvailabilityDTO : datesBadedOnNoOfDays) {
			hotelAvailability = new HotelAvailabilityBean();
			BeanUtils.copyProperties(hotelAvailabilityDTO, hotelAvailability);
			listOfDates.add(hotelAvailability);
		}

		logger.info("getSeleectedDatesListOnCatgryType controller : end");
		logger.info("List of avail dates " + listOfDates);
		json = mapper.writeValueAsString(listOfDates);
		logger.info("showDates controller : end");
		return json;
	}

	@RequestMapping(value = "showSearchDates.htm", method = RequestMethod.POST)
	@ResponseBody
	public String showSearchDates(@RequestParam("noOfDays") Integer noOfDays,
			@RequestParam("catgry") Long categoryId,
			@RequestParam("date") String selectedDate, HttpSession session,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("showDates controller : start");
		/**
		 * get hotelId value from session and pass it as parameter.
		 */
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		List<HotelAvailabilityBean> listOfDates = new ArrayList<HotelAvailabilityBean>();
		HotelAvailabilityBean hotelAvailability = null;
		List<HotelAvailabilityDTO> datesBadedOnNoOfDays = service
				.getDatesBadedOnNoOfDaysAndSelectedDate(selectedDate, hotelID,
						noOfDays, categoryId);
		for (HotelAvailabilityDTO hotelAvailabilityDTO : datesBadedOnNoOfDays) {
			hotelAvailability = new HotelAvailabilityBean();
			BeanUtils.copyProperties(hotelAvailabilityDTO, hotelAvailability);
			listOfDates.add(hotelAvailability);
		}
		json = mapper.writeValueAsString(listOfDates);
		logger.info("showDates controller : end");
		return json;
	}

	@RequestMapping(value = "getDatesList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDatesList(HttpSession session, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getDatesList : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		ObjectMapper mapper = new ObjectMapper();
		String availDates = null;

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = 0L;
		if (hotelInfomrtn != null) {
			hotelID = hotelInfomrtn.getHotelID();
		}

		UserDTO userDTO = (UserDTO) session.getAttribute("userId");

		List<HotelAvailabilityBean> availabilityDatesBeans = new ArrayList<HotelAvailabilityBean>();
		HotelAvailabilityBean availabilityBean = null;
		List<HotelAvailabilityDTO> datesList = service.getDatesList(hotelID);
		for (HotelAvailabilityDTO hotelAvailabilitDTO : datesList) {
			availabilityBean = new HotelAvailabilityBean();
			BeanUtils.copyProperties(hotelAvailabilitDTO, availabilityBean);
			Date date = availabilityBean.getDate();
			String selectedDt = destFrmt.format(date);
			availabilityBean.setSelecteDt(selectedDt);
			availabilityDatesBeans.add(availabilityBean);
		}

		if (userDTO != null) {
			List<AvailabilityByDateBean> loggedInUserRecrdsList = new ArrayList<AvailabilityByDateBean>();
			AvailabilityByDateBean userRecrds = null;
			List<AvailabilityByDateDTO> loggedInUserList = service
					.getLoggedInUserReservedDates(hotelID, userDTO.getUserId());
			for (AvailabilityByDateDTO availabilityByDateDTO : loggedInUserList) {
				userRecrds = new AvailabilityByDateBean();
				BeanUtils.copyProperties(availabilityByDateDTO, userRecrds);
				loggedInUserRecrdsList.add(userRecrds);
			}
		}

		availDates = mapper.writeValueAsString(availabilityDatesBeans);
		logger.info("getDatesList : end");
		return availDates;
	}

	@RequestMapping(value = "getDatesListOnCatgryType.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDatesListOnCatgryType(
			@RequestParam("catgryId") Long categryId, HttpSession session,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("getDatesListOnCatgryType controller : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		ObjectMapper mapper = new ObjectMapper();
		String availDates = null;

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = 0L;
		if (hotelInfomrtn != null) {
			hotelId = hotelInfomrtn.getHotelID();
		}
		List<HotelAvailabilityBean> availabilityDatesBeans = new ArrayList<HotelAvailabilityBean>();
		HotelAvailabilityBean availabilityBean = null;
		List<HotelAvailabilityDTO> datesListBsdOnCatgry = service
				.getDatesListBsdOnCatgry(hotelId, categryId);
		logger.info("datesListBsdOnCatgry &&& ::" + datesListBsdOnCatgry);
		for (HotelAvailabilityDTO hotelAvailabilitDTO : datesListBsdOnCatgry) {
			availabilityBean = new HotelAvailabilityBean();
			BeanUtils.copyProperties(hotelAvailabilitDTO, availabilityBean);
			Date date = availabilityBean.getDate();
			String selectedDt = destFrmt.format(date);
			availabilityBean.setSelecteDt(selectedDt);
			availabilityDatesBeans.add(availabilityBean);
		}
		logger.info("availabilityDatesBeans bsd on catgry :"
				+ availabilityDatesBeans);
		availDates = mapper.writeValueAsString(availabilityDatesBeans);
		logger.info("getDatesListOnCatgryType controller : end");
		return availDates;
	}

	@RequestMapping(value = "getLoggedInUserReservedDates.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getLoggedInUserReservedDates(HttpSession session, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getLoggedInUserReservedDates : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = 0L;
		if (hotelInfomrtn != null) {
			hotelID = hotelInfomrtn.getHotelID();
		}

		UserDTO userDTO = (UserDTO) session.getAttribute("userId");
		if(userDTO == null){
			userDTO = (UserDTO) session.getAttribute("servUserId");
		}
		List<AvailabilityByDateBean> loggedInUserRecrdsList = new ArrayList<AvailabilityByDateBean>();
		if (userDTO != null) {
			
			AvailabilityByDateBean userRecrds = null;
			List<AvailabilityByDateDTO> loggedInUserList = service
					.getLoggedInUserReservedDates(hotelID, userDTO.getUserId());
			for (AvailabilityByDateDTO availabilityByDateDTO : loggedInUserList) {
				userRecrds = new AvailabilityByDateBean();
				BeanUtils.copyProperties(availabilityByDateDTO, userRecrds);
				Date date = userRecrds.getDate();
				String bookedDt = destFrmt.format(date);
				userRecrds.setUserBkdDate(bookedDt);
				loggedInUserRecrdsList.add(userRecrds);
				json = mapper.writeValueAsString(loggedInUserRecrdsList);
			}
		}
		logger.info("loggedIn user :"+loggedInUserRecrdsList);
		logger.info("getLoggedInUserReservedDates : end");
		return json;
	}

	@RequestMapping(value = "getLoggedInUserDatesOnCategorytype.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getLoggedInUserReservedDatesOnCategoryType(
			@RequestParam("catgry") Long categoryID, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getLoggedInUserReservedDatesOnCategoryType controller : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<AvailabilityByDateBean> loggedInUserRecrdsList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean userRecrds = null;

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = 0L;

		if (hotelInfomrtn != null)
			hotelID = hotelInfomrtn.getHotelID();

		UserDTO userDTO = (UserDTO) session.getAttribute("userId");
		List<AvailabilityByDateDTO> loggedInUserReservedDatesOnCategoryType = null;
		if (userDTO != null) {
			loggedInUserReservedDatesOnCategoryType = service
					.getLoggedInUserReservedDatesOnCategoryType(hotelID,
							userDTO.getUserId(), categoryID);
			for (AvailabilityByDateDTO availabilityByDateDTO : loggedInUserReservedDatesOnCategoryType) {
				userRecrds = new AvailabilityByDateBean();
				BeanUtils.copyProperties(availabilityByDateDTO, userRecrds);
				Date date = userRecrds.getDate();
				String bookedDt = destFrmt.format(date);
				userRecrds.setUserBkdDate(bookedDt);
				loggedInUserRecrdsList.add(userRecrds);
				json = mapper.writeValueAsString(loggedInUserRecrdsList);
			}
		}
		logger.info("getLoggedInUserReservedDatesOnCategoryType controller : end");
		return json;
	}

	@RequestMapping(value = "getRoomsAvailOnCurrentDate.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomsAvailOnCurrentDate(
			@RequestParam("noOfDays") Integer noOfDays, HttpSession session,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("getRoomsAvailOnCurrentDate : start");
		logger.info("noOfDays in getRoomsAvailOnCurrentDate :" + noOfDays);
		String availRoomsListJSON;
		ObjectMapper mapper = new ObjectMapper();

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = 0L;
		if (hotelInfomrtn != null) {
			hotelID = hotelInfomrtn.getHotelID();
		}

		String userName = (String) session.getAttribute("userName");

		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);
		/**
		 * Below snippet is used to get availRoomInfo and shows it on room
		 * reservation grid.
		 */
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateBean> availRoomsList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean dateBean = null;
		List<AvailabilityByDateDTO> roomAvailInfo = service.getRoomAvailInfo(
				hotelID, noOfDays, userName);

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
			dateBean = new AvailabilityByDateBean();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categryName);
			BeanUtils.copyProperties(availabilityByDateDTO, dateBean);
			Date date = dateBean.getDate();
			String roomAvalDt = format.format(date);
			dateBean.setRoomAvailDate(roomAvalDt);
			availRoomsList.add(dateBean);
		}
		availRoomsListJSON = mapper.writeValueAsString(availRoomsList);
		logger.info("getRoomsAvailOnCurrentDate : end");
		return availRoomsListJSON;
	}

	/**
	 * This method is used to get room numbers and append it to room no select
	 * tag.
	 * 
	 * @param categoryID
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "getRoomsList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomsByCategoryName(
			@RequestParam("selectedCategory") Long categoryID, Model model,
			HttpServletRequest request, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getRoomsByCategoryName controller : start");
		String jsonData = null;
		ObjectMapper objectMapper = new ObjectMapper();
		HotelBean hotel = (HotelBean) session.getAttribute("hotel");
		String userName = (String) session.getAttribute("userName");
		List<Long> roomsList = null;
		if (categoryID != null) {
			roomsList = service.getRoomsList(hotel.getHotelID(), categoryID,
					userName);
		}
		jsonData = objectMapper.writeValueAsString(roomsList);
		logger.info("getRoomsByCategoryName controller : end");
		return jsonData;
	}

	@RequestMapping(value = "getCategoryList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCategoryList(HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getCategoryList : start");
		String jsonCatList = null;
		ObjectMapper mapper = new ObjectMapper();
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		List<CategoryDTO> categoryList = service.getCategoryList(hotelInfomrtn
				.getHotelID());
		List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
		CategoryBean categoryBean = null;
		for (CategoryDTO categoryDto : categoryList) {
			categoryBean = new CategoryBean();
			BeanUtils.copyProperties(categoryDto, categoryBean);
			categoryBeans.add(categoryBean);
		}
		jsonCatList = mapper.writeValueAsString(categoryBeans);
		logger.info("getCategoryList : end");
		return jsonCatList;
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
