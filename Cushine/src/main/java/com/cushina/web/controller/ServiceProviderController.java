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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.CategoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListServiceDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.common.exception.EmailNotUniqueException;
import com.cushina.common.util.PDFGeneratingThread;
import com.cushina.service.PickHotelService;
import com.cushina.service.ReservationService;
import com.cushina.service.ServiceProviderService;
import com.cushina.service.UserService;
import com.cushina.web.bean.AvailabilityByDateBean;
import com.cushina.web.bean.BlackListUsersBean;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.CategoryBean;
import com.cushina.web.bean.GuestUserBean;
import com.cushina.web.bean.HotelAvailabilityBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.RoomInfoBean;
import com.cushina.web.bean.UserBean;
import com.cushina.web.bean.WhiteListUsersBean;
import com.cushina.web.validator.RegistrationValidator;

@Controller
public class ServiceProviderController {

	private final static Logger logger = Logger
			.getLogger(ServiceProviderController.class);

	@Autowired
	UserService userService;

	@Autowired
	private RegistrationValidator validator;

	@Autowired
	private ServiceProviderService serviceProvider;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private PickHotelService pickHotelService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd/MM/yyyy"), true));
	}

	@RequestMapping(value = "serviceProviderView.htm", method = RequestMethod.GET)
	public String intiServiceProvider(Model model) {
		model.addAttribute("getSPUser", new UserBean());
		return "serviceprovider_view";
	}

	@RequestMapping(value = "serviceProviderPage.htm", method = RequestMethod.GET)
	public String getProviderPage(HttpSession session, Model model) {
		logger.info("serviceProviderPage controller : start");
		UserDTO usrDTO = (UserDTO) session.getAttribute("servUserId");
		List<HotelBean> hotelDetail = pickHotelService.getHotelDetail(usrDTO
				.getHotelID());
		session.getAttribute("servUserName");
		HotelBean bean = new HotelBean();
		BeanUtils.copyProperties(hotelDetail.get(0), bean);
		session.setAttribute("hotel", bean);
		model.addAttribute("getSPUser", new UserBean());
		logger.info("serviceProviderPage controller : end");
		return "serviceprovider_view";
	}

	@RequestMapping(value = "backToHomePage.htm", method = RequestMethod.GET)
	public String backToHomePage(Model model) {
		model.addAttribute("getSPUser", new UserBean());
		return "serviceprovider_view";
	}

	@RequestMapping(value = "getRoomsOnCurrentDate.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomsOnCurrentDate(
			@RequestParam("noOfDays") Integer noOfDays, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getRoomsOnCurrentDate : start");

		// get hotelId value from session and pass it as parameter.
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
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
		logger.info("roomsAvailList %%% :" + roomsAvailList);
		json = mapper.writeValueAsString(roomsAvailList);
		logger.info("getRoomsOnCurrentDate : end");
		return json;
	}

	@RequestMapping(value = "getSelectedDateAvailRoomsForProvider.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getSelectedDateAvailRoomsForServiceProvider(
			@RequestParam("date") String selectedDate,
			@RequestParam("selectedCategory") Long categoryID,
			@RequestParam("noOfDays") Integer dayCount, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getSelectedDateAvailRoomsForServiceProvider controller : start");
		String json = null;
		ObjectMapper mapper = new ObjectMapper();

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		List<AvailabilityByDateBean> byDateBeans = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> availRooms = null;
		if (categoryID == 0) {
			availRooms = serviceProvider.getSelectedDateAvailRoomsForProvider(
					selectedDate, hotelID);
		} else {
			availRooms = serviceProvider
					.getSelectedDateAvailRoomsBasedOnCatgeoryForProvider(
							selectedDate, hotelID, categoryID, dayCount);
		}
		for (AvailabilityByDateDTO availabilityByDateDTO : availRooms) {
			byDateBean = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);
			byDateBeans.add(byDateBean);
		}
		logger.info("by dates beans $$$&&& :: " + byDateBeans);
		json = mapper.writeValueAsString(byDateBeans);
		logger.info("getSelectedDateAvailRoomsForServiceProvider controller : end");
		return json;
	}

	@RequestMapping(value = "getRoomInfoByCatgryToProvider.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomInfoByCatgryToProvider(
			@RequestParam("type") Long categoryType,
			@RequestParam("currDarte") String currDate, Model model,
			HttpSession session) throws ParseException,
			JsonGenerationException, JsonMappingException, IOException {

		logger.info("getRoomInfoByCatgryToProvider controller : start");
		SimpleDateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
		Date selectedDt = formt.parse(currDate);
		String json = null;
		ObjectMapper mapper = new ObjectMapper();

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		Long hotelID = 0L;
		if (hotelInfomrtn != null) {
			hotelID = hotelInfomrtn.getHotelID();
		}

		/**
		 * This method is used to get room info based on date selected and
		 * category type selected.
		 */
		List<AvailabilityByDateBean> roomAvalList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean roomAvail = null;
		List<AvailabilityByDateDTO> roomAvailByCategoryForProvider = serviceProvider
				.getRoomAvailByCategoryForProvider(categoryType, hotelID,
						selectedDt);
		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByCategoryForProvider) {
			roomAvail = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, roomAvail);
			roomAvalList.add(roomAvail);
		}
		json = mapper.writeValueAsString(roomAvalList);
		logger.info("getRoomInfoByCatgryToProvider controller : end");
		return json;
	}

	@RequestMapping(value = "getDatesOnSelectedRoomNum.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDatesOnSelectedRoomNum(
			@RequestParam("cat") String categoryType,
			@RequestParam("roomNo") String roomNo,
			@RequestParam("currDate") String currentDt, HttpSession session)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {

		logger.info("getDatesOnSelectedRoomNum controller : start");
		String json = null;
		ObjectMapper mapper = new ObjectMapper();

		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat srcFrmt = new SimpleDateFormat("dd-MM-yyyy");
		Date prseDt = srcFrmt.parse(currentDt);
		String parsedDt = destFrmt.format(prseDt);
		prseDt = destFrmt.parse(parsedDt);

		Long categoryId = Long.valueOf(categoryType);
		Long roomNum = Long.valueOf(roomNo);

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		List<AvailabilityByDateBean> selectedRoomDatesList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean roomInfo = null;

		List<AvailabilityByDateDTO> dateListOnSelectedRoomNo = serviceProvider
				.getDateListOnSelectedRoomNo(roomNum, prseDt, hotelID,
						categoryId);
		for (AvailabilityByDateDTO availabilityByDateDTO : dateListOnSelectedRoomNo) {
			roomInfo = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, roomInfo);
			selectedRoomDatesList.add(roomInfo);
		}
		json = mapper.writeValueAsString(selectedRoomDatesList);
		logger.info("getDatesOnSelectedRoomNum controller : end");
		return json;
	}

	@RequestMapping(value = "getRoomsAvailOnCurrentDateToProvider.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomsAvailOnCurrentDateToProvider(
			@RequestParam("cat") String categoryType,
			@RequestParam("roomNo") String roomNo,
			@RequestParam("currDate") String currentDt, HttpSession session)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {

		logger.info("getRoomsAvailOnCurrentDateToProvider controller : start");
		DateFormat srcFrmt = new SimpleDateFormat("dd-MM-yyyy");
		Date currentDtVal = srcFrmt.parse(currentDt);
		Long categoryId = Long.valueOf(categoryType);
		Long roomNum = Long.valueOf(roomNo);

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		List<AvailabilityByDateBean> roomAvalByCategryList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean roomAvailibility = null;
		List<AvailabilityByDateDTO> roomAvailByCategoryAndRoomNum = serviceProvider
				.getRoomAvailByCategoryAndRoomNum(categoryId, hotelID, roomNum,
						currentDtVal);
		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByCategoryAndRoomNum) {
			roomAvailibility = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, roomAvailibility);

			boolean arrived = roomAvailibility.isArrived();
			boolean paid = roomAvailibility.isPaid();
			if (arrived == true) {
				roomAvailibility.setArrivedVal("1");
			} else {
				roomAvailibility.setArrivedVal("0");
			}

			if (paid == true) {
				roomAvailibility.setPaidVal("1");
			} else {
				roomAvailibility.setPaidVal("0");
			}

			roomAvalByCategryList.add(roomAvailibility);
		}
		json = mapper.writeValueAsString(roomAvalByCategryList);
		logger.info("getRoomsAvailOnCurrentDateToProvider controller : end");
		return json;
	}

	@RequestMapping(value = "doubleclick.htm", method = RequestMethod.POST)
	@ResponseBody
	public String doubliClick(@RequestParam("noOfDays") Integer noOfDays,
			HttpSession session, Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("doubliClick controller : start");
		String json = null;
		ObjectMapper mapper = new ObjectMapper();

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		List<AvailabilityByDateBean> roomsAvailList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> roomsOnCurrentDate = serviceProvider
				.getRoomsOnCurrentDate(hotelID, noOfDays);
		for (AvailabilityByDateDTO availabilityByDateDTO : roomsOnCurrentDate) {
			byDateBean = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);
			roomsAvailList.add(byDateBean);
		}
		json = mapper.writeValueAsString(roomsAvailList);
		logger.info("doubliClick controller : end");
		return json;
	}

	@RequestMapping(value = "getAvailRoomsByDayCountAndCategory.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomsByDayCountAndCategory(
			@RequestParam("selectedCategory") Long selectedCategory,
			@RequestParam("noOfDays") Integer noOfDays,
			@RequestParam("currentDate") String selectedDt, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getRoomsByDayCountAndCategory controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = 0L;
		if (hotelInfomrtn != null) {
			hotelID = hotelInfomrtn.getHotelID();
		}

		List<AvailabilityByDateBean> byDateBeans = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> selectedDateAvailRooms = serviceProvider
				.getSelectedDateAvailRoomsBasedOnCatgeoryForProvider(
						selectedDt, hotelID, selectedCategory, noOfDays);
		for (AvailabilityByDateDTO availabilityByDateDTO : selectedDateAvailRooms) {
			byDateBean = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);
			byDateBeans.add(byDateBean);
		}
		json = mapper.writeValueAsString(byDateBeans);
		logger.info("getRoomsByDayCountAndCategory controller : end");
		return json;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "getUserReservedRoomDetails.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getUserReservedRoomDetails(
			@RequestParam("jsonData") String jsonData, Model model,
			HttpSession session) throws JSONException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getUserReservedRoomDetails controller : start");
		String json = null;
		Integer usrIdVal = null;
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObject = new JSONObject(jsonData);
		logger.info("jsonObject :" + jsonObject);

		/**
		 * get hotel information from session
		 */
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		String availCnt = jsonObject.getString("availCnt");
		String dateVal = jsonObject.getString("dateVal");
		String categeoryId = jsonObject.getString("categoryId");
		String usreId = jsonObject.getString("usreId");
		String guestUsrId = jsonObject.getString("guestUsr");
		String roomId = jsonObject.getString("roomId");
		String reservtnNo = jsonObject.getString("reservationNumber");
		Integer availCount = Integer.valueOf(availCnt);// : 15/08/2015
		Integer catgryId = Integer.valueOf(categeoryId);
		Integer usrId = null, gustUsrId = null;
		if (!usreId.equals("null"))
			usrId = Integer.valueOf(usreId);

		if (!guestUsrId.equals("null"))
			gustUsrId = Integer.valueOf(guestUsrId);

		Integer roomIDval = Integer.valueOf(roomId);
		Long reservationNumber = Long.valueOf(reservtnNo);

		BookingHistoryBean userReservedRoomDetails = serviceProvider
				.getUserReservedRoomDetails(usrId, gustUsrId, dateVal,
						roomIDval, catgryId, reservationNumber, hotelID);
		logger.info("userreservation reservation details :"
				+ userReservedRoomDetails);
		if (userReservedRoomDetails.getGuestUserId() != null)
			userReservedRoomDetails.setUserId(usrIdVal);

		json = mapper.writeValueAsString(userReservedRoomDetails);
		logger.info("getUserReservedRoomDetails controller end");
		return json;
	}

	@RequestMapping(value = "paidAmt.htm", method = RequestMethod.POST)
	@ResponseBody
	public String paidAmt(@RequestParam("userId") Integer userId,
			@RequestParam("hotelId") Long hotelId,
			@RequestParam("reservtnNum") Long reservtnNum,
			@RequestParam("guestUsrId") Integer guestUsrId, Model model) {
		logger.info("paidAmt controller : start");

		boolean paymentPaid = serviceProvider.isPaymentPaid(userId, hotelId,
				guestUsrId, reservtnNum);
		String status = null;
		if (paymentPaid == true) {
			status = "Amount paid successfully";
		} else {
			status = "amount paid failed";
		}

		logger.info("paidAmt controller : end");
		return status;
	}

	@RequestMapping(value = "arrived.htm", method = RequestMethod.POST)
	@ResponseBody
	public String arrived(@RequestParam("userId") Integer userId,
			@RequestParam("hotelId") Long hotelId,
			@RequestParam("reservtnNum") Long reservtnNum,
			@RequestParam("guestUsrId") Integer guestUsrId, Model model) {
		logger.info("arrived controller : start");

		boolean paymentPaid = serviceProvider.isArrived(userId, hotelId,
				guestUsrId, reservtnNum);
		String status = null;
		if (paymentPaid == true) {
			status = "customer has arrived at location";
		} else {
			status = "customer arrived failed";
		}
		logger.info("arrived controller : end");
		return status;
	}

	@RequestMapping(value = "isChangeReservtn.htm", method = RequestMethod.POST)
	@ResponseBody
	public String isChangeReservtn(HttpSession session, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {

		logger.info("isChangeReservtn controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		GuestUserBean guestUser = new GuestUserBean();
		BookingHistoryBean changeReservtnBean = (BookingHistoryBean) session
				.getAttribute("changeReservBean");
		GuestUserDTO guestUserDetails = null;
		if (changeReservtnBean != null) {
			Integer guestUserId = changeReservtnBean.getGuestUserId();
			guestUserDetails = userService.getGuestUserDetailsById(guestUserId);
			if (guestUserDetails != null)
				BeanUtils.copyProperties(guestUserDetails, guestUser);
			logger.info("guestUser :" + guestUser);
		}
		json = mapper.writeValueAsString(guestUser);
		logger.info("isChangeReservtn controller : end");
		return json;
	}

	@RequestMapping(value = "manualReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String manualReservation(@RequestParam("date") String checkedInDt,
			@RequestParam("roomId") Long roomId,
			@RequestParam("availcnt") Integer availcnt,
			@RequestParam("categoryId") Long categoryId, HttpSession session,
			Model model) throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("manualReservation controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		/**
		 * get hotel information from session
		 */
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		SimpleDateFormat srcFrmt = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		Date date = srcFrmt.parse(checkedInDt);
		List<AvailabilityByDateBean> availReserveDtList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean availReserveDt = null;
		List<AvailabilityByDateDTO> availDatesToReserveRoom = reservationService
				.getAvailDatesToReserveRoom(hotelID, date, roomId);
		logger.info("availDatesToReserveRoom ::: " + availDatesToReserveRoom);
		for (AvailabilityByDateDTO availabilityByDateDTO : availDatesToReserveRoom) {
			availReserveDt = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, availReserveDt);
			Date availDt = availReserveDt.getDate();
			String availDate = format.format(availDt);
			availReserveDt.setCheckedOutDate(availDate);
			availReserveDt.setRoomId(roomId);
			availReserveDt.setAvailcnt(availcnt);
			availReserveDt.setCategoryId(categoryId);
			availReserveDt.setCheckedInDate(format.format(date));
			availReserveDtList.add(availReserveDt);
		}
		logger.info("availReserveDtList :" + availReserveDtList);
		json = mapper.writeValueAsString(availReserveDtList);
		logger.info("josn info avail rooms list :" + json);
		logger.info("manualReservation controller : end");
		return json;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "saveManualReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String saveManualReservation(@RequestParam("json") String json,
			Model model, HttpSession session, HttpServletRequest request)
			throws JSONException, ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("saveManualReservation controller : start");
		UserDTO userdto = (UserDTO) session.getAttribute("servUserId");
		Integer servProId = userdto.getUserId();
		SimpleDateFormat srcFormat = new SimpleDateFormat("dd MMMM,yyyy");
		JSONObject jsonObject = new JSONObject(json);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = null;

		String reservationTemplatePath = "/letterTemplate/reservationTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String emailTemplateRealPath = servletContext
				.getRealPath(reservationTemplatePath);

		logger.info("real path of reservation template :"
				+ emailTemplateRealPath);

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		BookingHistoryBean changeBean = (BookingHistoryBean) session
				.getAttribute("changeReservBean");
		BookingHistoryDTO changeDto = new BookingHistoryDTO();
		GuestUserBean chngebean = (GuestUserBean) session
				.getAttribute("guestBean");
		GuestUserDTO chngeDto = new GuestUserDTO();
		if (changeBean != null) {
			BeanUtils.copyProperties(changeBean, changeDto);
			logger.info("insdie saveManualReservation changeReservationBean : "
					+ changeBean);
		}

		logger.info("insdie saveManualReservation changeReservationBean : "
				+ changeBean);

		String message = null;
		logger.info("json oject :" + jsonObject);
		String isArrived = jsonObject.getString("arrivedVal");
		long phneNum = jsonObject.getLong("phone");
		String email = jsonObject.getString("email");
		String isPaid = jsonObject.getString("paymentVal");
		String notes = jsonObject.getString("userNotes");
		String userName = jsonObject.getString("userName");
		String checkIn = jsonObject.getString("checkedInDt");
		String checkOut = jsonObject.getString("checkedOutDt");
		Long roomId = jsonObject.getLong("roomId");
		Long categoryId = jsonObject.getLong("categoryId");

		BookingHistoryBean historyBean = new BookingHistoryBean();
		BookingHistoryDTO manualReservtnInfo = null;
		BookingHistoryDTO historyDTO = new BookingHistoryDTO();
		if (changeBean != null) {
			historyDTO.setBookingId(changeBean.getBookingId());
			historyDTO.setUserId(changeBean.getUserId());
			historyDTO.setGuestUserId(changeBean.getGuestUserId());
			logger.info("checking service provider value getting or not : "
					+ changeBean.getServProId());
		}
		historyDTO.setCheckInDate(srcFormat.parse(checkIn));
		historyDTO.setCheckOutDate(srcFormat.parse(checkOut));

		if (isPaid.equals("1")) {
			historyDTO.setPaid(true);
		} else {
			historyDTO.setPaid(false);
		}
		if (isArrived.equals("1")) {
			historyDTO.setArrived(true);
		} else {
			historyDTO.setArrived(false);
			logger.info("isArrived :::: " + historyDTO.isArrived());
		}
		historyDTO.setEmailShare("N");
		historyDTO.setNotes(notes);
		historyDTO.setHotelID(hotelID);
		historyDTO.setRoomId(roomId);
		historyDTO.setCategoryId(categoryId);
		Date date = new Date();
		historyDTO.setBookingDate(date);
		historyDTO.setUserId(servProId);

		UserDTO userDTO = new UserDTO();
		GuestUserDTO guestUsrDTO = new GuestUserDTO();
		guestUsrDTO.setIsUpdate(false);
		if (changeBean != null) {
			guestUsrDTO.setUserId(changeBean.getGuestUserId());
			guestUsrDTO.setServProId(new Long(changeBean.getUserId()));
			guestUsrDTO.setIsUpdate(true);
		}
		guestUsrDTO.setPhoneNumber(String.valueOf(phneNum));
		guestUsrDTO.setEmail(email);
		guestUsrDTO.setHotelId(hotelID);
		guestUsrDTO.setUserName(userName);
		guestUsrDTO.setFirstName(userName);
		guestUsrDTO.setServProId(new Long(servProId));

		userDTO.setUserName(userName);
		// userDTO.setContactNumber(phneNum);
		userDTO.setPhoneNumber(String.valueOf(phneNum));
		userDTO.setEmail(email);
		userDTO.setHotelID(hotelID);

		logger.info("before saveGuestUser method : guestUsrDTO : "
				+ guestUsrDTO);
		if (chngebean != null) {
			BeanUtils.copyProperties(chngebean, chngeDto);
			chngeDto.setIsUpdate(true);
			BeanUtils.copyProperties(chngeDto, guestUsrDTO);
		}
		Integer guestUserId = userService.saveGuestUser(guestUsrDTO);
		if (guestUserId != 0) {
			manualReservtnInfo = serviceProvider.saveManualReservation(
					historyDTO, guestUsrDTO, guestUserId);
			if (manualReservtnInfo != null) {
				if (changeBean != null) {
					boolean isUpdate = serviceProvider
							.updatePreviousReservations(changeDto);
				}
				BeanUtils.copyProperties(manualReservtnInfo, historyBean);
				historyBean.setCategoryName(manualReservtnInfo.getCategory());
				historyBean.setChckedInDate(manualReservtnInfo.getCheckInDt());
				historyBean
						.setChckedOutDate(manualReservtnInfo.getCheckOutDt());
				historyBean.setPhoneNumber(guestUsrDTO.getPhoneNumber());
				historyBean.setUserName(guestUsrDTO.getUserName());
				historyBean.setEmail(guestUsrDTO.getEmail());

				List<HotelBean> hotelDetail = pickHotelService
						.getHotelDetail(historyBean.getHotelID());

				if (hotelDetail.size() != 0 && hotelDetail != null) {
					historyBean.setHotelName(hotelDetail.get(0).getHotelName());
					historyBean.setHotelAddress(hotelDetail.get(0)
							.getHotelAddress());
					historyBean.setHotelPhneNumber(hotelDetail.get(0)
							.getPhoneNumber());
					historyBean.setCity(hotelDetail.get(0).getCity());
					historyBean.setPostalCode(hotelDetail.get(0)
							.getPostalCode());
				}
				// srcFormat
				Date checkInDate = historyBean.getCheckInDate();
				Date checkOutDate = historyBean.getCheckOutDate();
				String checkInDt = srcFormat.format(checkInDate);
				String checkOutDt = srcFormat.format(checkOutDate);
				logger.info("checkIN date *** " + checkInDt);
				logger.info("checkOutDt *** " + checkOutDt);
				historyBean.setChckedInDt(checkInDt);
				historyBean.setCheckOutDt(checkOutDt);

				PDFGeneratingThread pdfGeneratingThread = new PDFGeneratingThread(
						request, hotelInfomrtn, historyBean, userName,
						emailTemplateRealPath, email, session);
				pdfGeneratingThread.start();
				jsonData = mapper.writeValueAsString(historyBean);
				logger.info("historyBean info ---***--- :" + historyBean);
			}
		}
		invalidateChangeReservAttrib(session);

		logger.info("saveManualReservation controller : end");
		return jsonData;
	}

	private void invalidateChangeReservAttrib(HttpSession session) {
		logger.info("invalidateChangeReservAttrib method : start");
		session.setAttribute("changeReservBean", null);
		logger.info("invalidateChangeReservAttrib method : end");
	}

	@RequestMapping(value = "addCustmerToBlackList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addCustmerToBlackList(@RequestParam("hotelId") Long hotelID,
			@RequestParam("userId") Integer userId,
			@RequestParam("guestUsrId") Integer guestUsrId,
			HttpSession session, Model model) {
		logger.info("addCustmerToBlackList controller : start");
		String status = null;
		status = serviceProvider.isCustmrAddToBlckLst(userId, hotelID,
				guestUsrId);
		logger.info("addCustmerToBlackList controller : end");
		return status;
	}

	@RequestMapping(value = "addCustmerTOWhiteList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addCustmerTOWhiteList(@RequestParam("hotelId") Long hotelID,
			@RequestParam("guestUsrId") Integer guestUsrId,
			@RequestParam("userId") Integer userId, HttpSession session,
			Model model) {
		logger.info("addCustmerTOWhiteList controller : start");
		String status = null;
		status = serviceProvider.isCustmrAddToWhtLst(userId, hotelID,
				guestUsrId);
		logger.info("addCustmerTOWhiteList controller : end");
		return status;
	}

	@RequestMapping(value = "adminCustmrList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String adminCustmrList(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("adminCustmrList controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		List<UserBean> cstmrLst = new ArrayList<UserBean>();
		UserBean cstmr = null;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<UserDTO> customersList = serviceProvider.getCustomersList(hotelId);
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

	@RequestMapping(value = "getCustmrReservedHistry.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCustmrReservedRoomInfo(
			@RequestParam("userId") Integer userId, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getCustmrReservedRoomInfo controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		List<BookingHistoryBean> bkngHistryList = new ArrayList<BookingHistoryBean>();
		BookingHistoryBean bkngHistry = null;
		List<BookingHistoryDTO> custmrReservedHistry = serviceProvider
				.getCustmrReservedHistry(userId, hotelId);
		for (BookingHistoryDTO bookingHistoryDTO : custmrReservedHistry) {
			bkngHistry = new BookingHistoryBean();
			BeanUtils.copyProperties(bookingHistoryDTO, bkngHistry);
			bkngHistryList.add(bkngHistry);
		}
		logger.info("checked IN :" + bkngHistry);
		json = mapper.writeValueAsString(bkngHistryList);
		logger.info("getCustmrReservedRoomInfo controller : end");
		return json;
	}

	@RequestMapping(value = "getCustomerInfo.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCustomerInfo(
			@RequestParam("custmrName") String custmrName, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getCustomerInfo controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userDetail = null;
		UserBean custmrInfo = null;
		try {
			userDetail = userService.getUserDetail(custmrName);
			custmrInfo = new UserBean();
			BeanUtils.copyProperties(userDetail, custmrInfo);
			json = mapper.writeValueAsString(custmrInfo);
		} catch (Exception e) {
			custmrInfo = new UserBean();
			custmrInfo.setStatus("no record with this user name");
			json = mapper.writeValueAsString(custmrInfo);
		}
		logger.info("getCustomerInfo controller : end");
		return json;
	}

	/**
	 * Get all customers history based on hotel, In which hotel Service Provider
	 * loggedIn.
	 * 
	 * @param model
	 * @param session
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "custmrsBookingHistory.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCustmrsbookingHistory(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getCustmrsbookingHistory controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<BookingHistoryBean> histryBeanLst = new ArrayList<BookingHistoryBean>();
		BookingHistoryBean historyBean = null;

		try {
			List<BookingHistoryDTO> custmrsHistry = serviceProvider
					.getCustmrsHistry(hotelId);
			for (BookingHistoryDTO bookingHistoryDTO : custmrsHistry) {
				historyBean = new BookingHistoryBean();
				BeanUtils.copyProperties(bookingHistoryDTO, historyBean);
				histryBeanLst.add(historyBean);
			}
			logger.info("histryBeanLst ::: " + histryBeanLst);
			json = mapper.writeValueAsString(histryBeanLst);
		} catch (Exception ex) {
			logger.info("came to catch block getCustmrsbookingHistory controller....");
			String status = "there is no customer history exists for this hotel";
			historyBean = new BookingHistoryBean();
			historyBean.setStatus(status);
			json = mapper.writeValueAsString(historyBean);
		}

		logger.info("getCustmrsbookingHistory controller : end");
		return json;
	}

	@RequestMapping(value = "getCustomerReservtnDetails.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCustomerReservtnDetails(
			@RequestParam("cstmrName") String userName, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getCustomerReservtnDetails controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<BookingHistoryBean> customerHistryList = new ArrayList<BookingHistoryBean>();
		BookingHistoryBean histryBean = null;

		try {
			List<BookingHistoryDTO> customerReservtnHistryRecords = serviceProvider
					.getCustomerReservtnHistryRecords(hotelId, userName);
			for (BookingHistoryDTO bookingHistoryDTO : customerReservtnHistryRecords) {
				histryBean = new BookingHistoryBean();
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

	@RequestMapping(value = "adminWhiteList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String adminWhiteList(HttpSession session, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("adminWhiteList controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		List<WhiteListUsersBean> whiteUsrsLst = new ArrayList<WhiteListUsersBean>();
		WhiteListUsersBean whiteUsr = null;

		try {
			List<WhiteListUsersDTO> allWhiteLstUsrs = serviceProvider
					.getAllWhiteLstUsrs(hotelId);
			for (WhiteListUsersDTO whiteListUsersDTO : allWhiteLstUsrs) {
				whiteUsr = new WhiteListUsersBean();
				BeanUtils.copyProperties(whiteListUsersDTO, whiteUsr);
				whiteUsrsLst.add(whiteUsr);
			}
			logger.info("whiteUsrsLst ::: "+whiteUsrsLst);
			json = mapper.writeValueAsString(whiteUsrsLst);
		} catch (Exception e) {
			e.getMessage();
			String statusMsg = "there is no Info about white customer exits in list";
			json = mapper.writeValueAsString(statusMsg);
			logger.info("no white customer exits in list whitelist_users table.");
		}
		logger.info("adminWhiteList controller : end");
		return json;
	}

	@RequestMapping(value = "adminBlackList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String adminBlackList(HttpSession session, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("adminBlackList cntroller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<BlackListUsersBean> blackUsrsLst = new ArrayList<BlackListUsersBean>();
		BlackListUsersBean blckUsr = null;

		try {
			List<BlackListUsersDTO> allBlackLstUsrs = serviceProvider
					.getAllBlackLstUsrs(hotelId);
			for (BlackListUsersDTO blackListUsersDTO : allBlackLstUsrs) {
				blckUsr = new BlackListUsersBean();
				BeanUtils.copyProperties(blackListUsersDTO, blckUsr);
				blackUsrsLst.add(blckUsr);
			}
			json = mapper.writeValueAsString(blackUsrsLst);
		} catch (Exception ex) {
			ex.getMessage();
			String statusMsg = "there is no Info about black customer exits in list";
			json = mapper.writeValueAsString(statusMsg);
			logger.info("no white customer exits in list blacklist_users table.");
		}
		logger.info("adminBlackList cntroller : end");
		return json;
	}

	@RequestMapping(value = "getWhitCustmrRecrd.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getWhitCustmrRecrd(@RequestParam("userName") String userName,
			Model model, HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getWhitCustmrRecrd controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		WhiteListUsersBean whteCustmr = new WhiteListUsersBean();
		try {
			WhiteListUsersDTO whiteCustmrRecord = serviceProvider
					.getWhiteCustmrRecord(hotelId, userName);
			if (whiteCustmrRecord == null) {
				String status = "no customer exists in white list";
				whteCustmr.setStatusMsg(status);
				json = mapper.writeValueAsString(whteCustmr);
			}
			BeanUtils.copyProperties(whiteCustmrRecord, whteCustmr);
			json = mapper.writeValueAsString(whteCustmr);
		} catch (Exception ex) {
			logger.info("came to getWhitCustmrRecrd() ");
			ex.getMessage();
			String status = "no customer exists in white list";
			whteCustmr.setStatusMsg(status);
			json = mapper.writeValueAsString(whteCustmr);
		}
		logger.info("getWhitCustmrRecrd controller : end");
		return json;
	}

	@RequestMapping(value = "getBlackCustmrRecrd.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getBlackCustmrRecrd(
			@RequestParam("userName") String userName, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getBlackCustmrRecrd controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		BlackListUsersBean blckCusmr = new BlackListUsersBean();
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		try {
			BlackListUsersDTO blackCustmrRecord = serviceProvider
					.getBlackCustmrRecord(hotelId, userName);
			if (blackCustmrRecord == null) {
				String status = "no customer exists in black list";
				blckCusmr.setStatusMsg(status);
				json = mapper.writeValueAsString(blckCusmr);
			}
			BeanUtils.copyProperties(blackCustmrRecord, blckCusmr);
			json = mapper.writeValueAsString(blckCusmr);
		} catch (Exception ex) {
			logger.info("came to getBlackCustmrRecrd() ");
			ex.getMessage();
			String status = "no customer exists in black list";
			blckCusmr.setStatusMsg(status);
			json = mapper.writeValueAsString(blckCusmr);
		}
		logger.info("getBlackCustmrRecrd controller : end");
		return json;
	}

	@RequestMapping(value = "exportWhiteCustmrCSV.htm", method = RequestMethod.GET)
	public void exportWhiteCustmrCSV(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session)
			throws IOException {
		logger.info("exportWhiteCustmrCSV controller : start");
		String userName = request.getParameter("userName");

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();

		String csvFileName = "whiteCustomer.csv";
		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		WhiteListUsersDTO whiteCustmrRecord = serviceProvider
				.getWhiteCustmrRecord(hotelId, userName);
		WhiteListUsersBean whteCustmr = new WhiteListUsersBean();
		BeanUtils.copyProperties(whiteCustmrRecord, whteCustmr);

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "userName", "email", "strtDate", "phoneNumber", };

		csvWriter.writeHeader(header);

		csvWriter.write(whteCustmr, header);
		csvWriter.close();

		logger.info("exportWhiteCustmrCSV controller : end");
	}

	@RequestMapping(value = "exportBlckCustmrCSV.htm", method = RequestMethod.GET)
	public void exportElckCustmrCSV(HttpServletRequest request,
			HttpSession session, HttpServletResponse response)
			throws IOException {
		logger.info("exportElckCustmrCSV controller : start");
		String userName = request.getParameter("userName");
		BlackListUsersBean blckCusmr = new BlackListUsersBean();
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();

		String csvFileName = "blackCustomer.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
		BlackListUsersDTO blackCustmrRecord = serviceProvider
				.getBlackCustmrRecord(hotelId, userName);
		BeanUtils.copyProperties(blackCustmrRecord, blckCusmr);

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "userName", "email", "strtDate", "phoneNumber", };

		csvWriter.writeHeader(header);

		csvWriter.write(blackCustmrRecord, header);
		csvWriter.close();
		logger.info("exportElckCustmrCSV controller : end");
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "exportCustmrReservntHstryCSV.htm", method = RequestMethod.GET)
	public void exportCustmrReservntHstryCSV(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("exportCustmrReservntHstryCSV controller : start");
		String userName = (String) request.getParameter("un");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		String csvFileName = "customerResrvtnHstry.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		List<BookingHistoryBean> customerHistryList = new ArrayList<BookingHistoryBean>();
		BookingHistoryBean histryBean = null;

		try {
			List<BookingHistoryDTO> customerReservtnHistryRecords = serviceProvider
					.getCustomerReservtnHistryRecords(hotelId, userName);
			for (BookingHistoryDTO bookingHistoryDTO : customerReservtnHistryRecords) {
				histryBean = new BookingHistoryBean();
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

		String[] header = { "userName", "reservationNumber", "checkInDt",
				"checkOutDt", "roomId", "numberOfDays", "category", "email",
				"phoneNumber" };

		csvWriter.writeHeader(header);

		for (BookingHistoryBean cusHstry : customerHistryList) {
			csvWriter.write(cusHstry, header);
		}

		csvWriter.close();

		logger.info("exportCustmrReservntHstryCSV controller : end");
	}

	@RequestMapping(value = "exporCustomerLstCSV.htm", method = RequestMethod.GET)
	public void exportCSV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("exportCSV controller : start");
		String userName = (String) request.getParameter("userName");
		String csvFileName = "customerDetails.csv";

		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		UserDTO userdto = userService.getUserDetail(userName);

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

	@RequestMapping(value = "exporAllCustomersLstCSV.htm", method = RequestMethod.GET)
	public void exporAllCustomersLstCSV(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		logger.info("exporAllCustomersLstCSV method : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		List<UserBean> customersList = new ArrayList<UserBean>();
		UserBean custmr = null;

		String csvFileName = "customersList.csv";
		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";

		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		List<UserDTO> allCustomrsLst = userService.getAllCustomrsLst(hotelId);
		for (UserDTO userDTO : allCustomrsLst) {
			custmr = new UserBean();
			BeanUtils.copyProperties(userDTO, custmr);
			customersList.add(custmr);
		}

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "firstName", "lastName", "email", "userName",
				"streetName", "city", "country", "postalCode", "phoneNumber" };

		csvWriter.writeHeader(header);
		for (UserBean customerInfo : customersList) {
			csvWriter.write(customerInfo, header);
		}
		csvWriter.close();
		logger.info("exporAllCustomersLstCSV method : end");
	}

	@RequestMapping(value = "exportCustmrEmailCSV.htm", method = RequestMethod.GET)
	public void exportCustmrEmailCSV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("exportCustmrEmailCSV controller : start");

		String custmrName = (String) request.getParameter("un");
		UserDTO userDetail = null;
		UserBean custmrInfo = null;

		String csvFileName = "CustomersEmailList.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		try {
			userDetail = userService.getUserDetail(custmrName);
			custmrInfo = new UserBean();
			BeanUtils.copyProperties(userDetail, custmrInfo);

		} catch (Exception e) {
			custmrInfo = new UserBean();
			custmrInfo.setStatus("no record with this user name");
		}

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "firstName", "lastName", "email", "userName",
				"streetName", "city", "country", "postalCode", "phoneNumber" };

		csvWriter.writeHeader(header);
		csvWriter.write(custmrInfo, header);
		csvWriter.close();

		logger.info("exportCustmrEmailCSV controller : start");
	}

	@RequestMapping(value = "getAdminCatgryLst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getAdminCatgryLst(HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getAdminCatgryLst controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<CategoryBean> categryList = new ArrayList<CategoryBean>();
		CategoryBean categry = null;
		List<CategoryDTO> categoryList = pickHotelService
				.getCategoryList(hotelId);
		for (CategoryDTO categoryDTO : categoryList) {
			categry = new CategoryBean();
			BeanUtils.copyProperties(categoryDTO, categry);
			categryList.add(categry);
		}
		json = mapper.writeValueAsString(categryList);
		logger.info("getAdminCatgryLst controller : end");
		return json;
	}

	@RequestMapping(value = "getAdminRoomsLst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getAdminRoomsLst(@RequestParam("catVal") Long catVal,
			HttpSession session, Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getAdminRoomsLst controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		List<RoomInfoBean> roomInfoLst = new ArrayList<RoomInfoBean>();
		RoomInfoBean roomInfoBean = new RoomInfoBean();
		List<Long> roomsList = pickHotelService.getRoomsAvailList(hotelId,
				catVal);
		for (Long categryVal : roomsList) {
			roomInfoBean.setRoomId(categryVal);
			roomInfoLst.add(roomInfoBean);
		}
		json = mapper.writeValueAsString(roomsList);
		logger.info("room list :" + roomInfoLst);
		logger.info("getAdminRoomsLst controller : end");
		return json;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "addRoomTOWhteLst", method = RequestMethod.POST)
	@ResponseBody
	public String addRoomTOWhteLst(@RequestParam("catVal") Long catVal,
			@RequestParam("roomId") Long roomNo, Model model,
			HttpSession session) {
		logger.info("addRoomTOWhteLst controller : start");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();
		Boolean addRoomTOWhteLst = serviceProvider.addRoomTOWhteLst(catVal,
				roomNo, hotelId);
		logger.info("addRoomTOWhteLst controller : end");
		return String.valueOf(roomNo);
	}

	@RequestMapping(value = "getProfile.htm", method = RequestMethod.GET)
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
		model.addAttribute("getSPUser", dto);
		logger.info("getServiceProviderProfile controller : end");
		return "serviceprovider_view";
	}

	@RequestMapping(value = "/updateProfile.htm", method = RequestMethod.POST)
	public String upDateProfile(@ModelAttribute("getSPUser") UserBean userBean,
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
			model.addAttribute("getSPUser", userBean);
			logger.info("updateProfile controller : end");
			return "serviceprovider_view";
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
		return "serviceprovider_view";
	}

	@RequestMapping(value = "/getServiceWhiteListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getServiceWhiteListDemo(Model model,
			HttpServletRequest request, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		HotelBean bean = (HotelBean) session.getAttribute("hotel");
		logger.info("hotel bean value : " + bean);
		Long hotelId = bean.getHotelID();

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<UserBean> userBeanList = new ArrayList<UserBean>();

		List<WhiteListServiceDTO> whitelistdto = serviceProvider
				.getServiceWhiteListStartDate(hotelId);

		for (WhiteListServiceDTO whiteListServiceDTO : whitelistdto) {
			UserBean userBean = new UserBean();
			Integer userId = whiteListServiceDTO.getUserId();
			UserDTO userDTO = serviceProvider.getUserById(userId);
			Date startDate = whiteListServiceDTO.getStartDate();
			String date = destFrmt.format(startDate);
			userDTO.setStrtDate(date);
			BeanUtils.copyProperties(userDTO, userBean);
			userBeanList.add(userBean);
			/*
			 * HotelBean hotelbean = new HotelBean(); Long hotelId =
			 * whiteListServiceDTO.getHotelID(); Date startDate =
			 * whiteListServiceDTO.getStartDate(); String date =
			 * destFrmt.format(startDate); HotelDTO hotelDTO = whiteService
			 * .getServiceWhiteListHotelInfo(hotelId);
			 * BeanUtils.copyProperties(hotelDTO, hotelbean);
			 * hotelbean.setStrtDate(date); hotelbeanList.add(hotelbean);
			 */

		}
		logger.info("hotel bean" + userBeanList);
		json = mapper.writeValueAsString(userBeanList);
		model.addAttribute("getSPUser", new UserBean());
		return json;
	}

	@RequestMapping(value = "/getMyReservations.htm", method = RequestMethod.POST)
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
		model.addAttribute("getSPUser", new UserBean());
		logger.info("getBookingDetails controller : end");
		return json;
	}

	@RequestMapping(value = "deleteServiceReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String deleteReservation(
			@RequestParam("deleteRcrdId") Long bookingID, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("deleteReservation controller : start");
		logger.info("deleteRcrdId : " + bookingID);
		String message = null;
		Long reservtnNum = null;
		reservtnNum = reservationService.deleteReservation(bookingID);
		logger.info("reservtnNm : " + reservtnNum);
		if (reservtnNum != null) {
			message = "Reservation number " + reservtnNum
					+ " has been deleted successfully";
		} else {
			message = "Reservation number " + reservtnNum
					+ " has been failed to delete";
		}
		logger.info("deleteReservation controller : end");
		return message;
	}

	/**
	 * This method is used to cancel user(customer) reservation and update
	 * column as 'cancel' in Booking_Reservations table.
	 * 
	 * @param bookingID
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "cancelServiceReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String cancelReservation(@RequestParam("bookingID") Long bookingID,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("cancelReservation controller : start");
		String status = null;
		Long reservtnNum = null;
		reservtnNum = reservationService.cancelReservation(bookingID);
		if (reservtnNum != null) {
			status = "Reservation  " + reservtnNum
					+ "has been cancelled successfully";
		} else {
			status = "Reservation  " + reservtnNum
					+ "has been failed to cancel";
		}
		logger.info("cancelReservation controller : end");
		return status;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "changeServiceReservation.htm", method = RequestMethod.GET)
	public String changeReservation(@RequestParam("bookingID") Long bookingID,
			Model model, HttpSession session) {
		logger.info("changeReservation controller : start");
		session.setAttribute("changeReservtnId", bookingID);
		session.setAttribute("chngePopup", "changing");
		BookingHistoryDTO changeReservationDto = serviceProvider
				.getBookingHistoryById(bookingID);
		GuestUserDTO guestDto = userService
				.getGuestUserDetailsById(changeReservationDto.getGuestUserId());
		logger.info("inside changeservicereservation userDto :" + guestDto);
		BookingHistoryBean changeBean = new BookingHistoryBean();
		GuestUserBean guestBean = new GuestUserBean();
		BeanUtils.copyProperties(guestDto, guestBean);
		session.setAttribute("guestBean", guestBean);
		logger.info("session bean in change:" + guestBean);
		BeanUtils.copyProperties(changeReservationDto, changeBean);
		logger.info("inside changeReservation : changeReservationBean : "
				+ changeReservationDto);
		session.setAttribute("changeReservBean", changeBean);
		UserBean userBean = new UserBean();
		logger.info("changeReservation controller : end");
		model.addAttribute("getSPUser", new UserBean());
		return "serviceprovider_view";
	}

	@RequestMapping(value = "/getServiceEmailListDet.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getSharedEmailList(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getSharedEmailList controller  : start");
		HotelBean bean = (HotelBean) session.getAttribute("hotel");
		UserDTO userdto = (UserDTO) session.getAttribute("servUserId");
		Long userId = new Long(userdto.getUserId());
		Long hotelId = bean.getHotelID();
		logger.info("hotelId" + hotelId + "userId" + userId);
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		BookingHistoryBean guestBean = null;
		List<BookingHistoryBean> beanList = new ArrayList<BookingHistoryBean>();
		List<BookingHistoryDTO> reservedList = serviceProvider
				.getSPEmailSharedList(hotelId, userId);
		logger.info("printing reservedList details : " + reservedList);
		SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");

		for (BookingHistoryDTO dto : reservedList) {
			guestBean = new BookingHistoryBean();
			dto.setBookingDt(frmt.format(dto.getBookingDate()));
			BeanUtils.copyProperties(dto, guestBean);
			beanList.add(guestBean);
		}
		logger.info("inside getSharedEmailList : emailShared list  : "
				+ beanList);
		json = mapper.writeValueAsString(beanList);
		model.addAttribute("getSPUser", new UserBean());
		logger.info("getSharedEmailList controller : end");
		return json;

	}

	@RequestMapping(value = "/stopServiceEmailShare.htm", method = RequestMethod.POST)
	@ResponseBody
	public String stopEmailSharing(@RequestParam("bookingId") Long bookingId,
			Model model, HttpSession session) {
		logger.info("inside stopEmailSharing method : start");
		BookingHistoryDTO dto = serviceProvider
				.getBookingHistoryById(bookingId);
		boolean isUpdate = serviceProvider.stopEmailShare(dto);
		logger.info("isUpdate" + isUpdate);
		String msg = null;
		if (isUpdate) {
			msg = dto.getUserName() + " has stopped email sharing!";
		} else {
			msg = "Email sharing is not stopped!";
		}
		logger.info("msg value : " + msg);
		return msg;
	}

	@RequestMapping(value = "removeWhteCustmrFrmLst.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeWhteCustmrFrmLst(
			@RequestParam("userId") Integer userId, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("removeWhteCustmrFrmLst controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = serviceProvider.isRemoveWhteCustmrFrmLst(userId);
		logger.info("removeWhteCustmrFrmLst controller : end");
		json = mapper.writeValueAsString(isRemove);
		return json;
	}

	@RequestMapping(value = "removeWhteGuestUsrFrmLst.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeWhteGuestUstmrFrmLst(
			@RequestParam("userId") Integer userId, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("removeWhteCustmrFrmLst controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = serviceProvider
				.isRemoveWhteGuestCustmrFrmLst(userId);
		logger.info("removeWhteCustmrFrmLst controller : end");
		json = mapper.writeValueAsString(isRemove);
		return json;
	}

	@RequestMapping(value = "removeBlackUser.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeBlackUser(@RequestParam("userId") Integer userId,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("removeBlackUser controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = serviceProvider.isRemoveBlackCustmrFrmLst(userId);
		json = mapper.writeValueAsString(isRemove);
		logger.info("removeBlackUser controller : end");
		return json;
	}

	@RequestMapping(value = "removeBlackGuestUser.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeBlackGuestUser(@RequestParam("userId") Integer userId,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("removeBlackGuestUser controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = serviceProvider
				.isRemoveBlackGuestCustmrFrmLst(userId);
		json = mapper.writeValueAsString(isRemove);
		logger.info("removeBlackGuestUser controller : end");
		return json;
	}

	@RequestMapping(value = "displaySearchDates.htm", method = RequestMethod.POST)
	@ResponseBody
	public String displaySearchDates(
			@RequestParam("noOfDays") Integer noOfDays,
			@RequestParam("catgry") Long categoryId, HttpSession session,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("displaySearchDates controller : start");
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		List<HotelAvailabilityBean> listOfDates = new ArrayList<HotelAvailabilityBean>();
		HotelAvailabilityBean hotelAvailability = null;
		List<HotelAvailabilityDTO> datesBadedOnNoOfDays = serviceProvider
				.getDatesListBasedOnNoOfDays(hotelID, noOfDays, categoryId);
		for (HotelAvailabilityDTO hotelAvailabilityDTO : datesBadedOnNoOfDays) {
			hotelAvailability = new HotelAvailabilityBean();
			BeanUtils.copyProperties(hotelAvailabilityDTO, hotelAvailability);
			listOfDates.add(hotelAvailability);
		}
		logger.info("List of avail dates " + listOfDates);
		json = mapper.writeValueAsString(listOfDates);
		logger.info("displaySearchDates controller : end");
		return json;
	}

	@RequestMapping(value = "returnToServiceHme.htm", method = RequestMethod.GET)
	public String initReturnToServiceHme(Model model, HttpServletRequest request) {
		logger.info("initReturnToServiceHme : start");
		UserBean userBean = new UserBean();
		model.addAttribute("getSPUser", userBean);
		logger.info("initReturnToHme : end");
		return "serviceprovider_view";
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
