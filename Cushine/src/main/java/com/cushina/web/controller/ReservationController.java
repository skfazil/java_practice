package com.cushina.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.util.CushinaUtil;
import com.cushina.common.util.PDFGeneratingThread;
import com.cushina.service.BookingHistoryService;
import com.cushina.service.PickHotelService;
import com.cushina.service.ReservationByRoomService;
import com.cushina.service.ReservationService;
import com.cushina.service.UserService;
import com.cushina.web.bean.AvailabilityByDateBean;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;
import com.itextpdf.text.DocumentException;

@Controller
public class ReservationController {

	private final Logger logger = Logger.getLogger(ReservationController.class
			.getName());

	@Autowired
	private UserService userService;

	@Autowired
	private BookingHistoryService service;

	@Autowired
	private PickHotelService hotelService;

	@Autowired
	private ReservationByRoomService roomService;

	@Autowired
	private ReservationService reservationService;

	@RequestMapping(value = "reservation.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getReservationDetails(
			@RequestParam("date") String checkedInDt,
			@RequestParam("roomId") Long roomId,
			@RequestParam("availcnt") Integer availcnt,
			@RequestParam("categoryId") Long categoryId, HttpSession session,
			Model model) throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {

		logger.info("getReservationDetails controller : start");
		SimpleDateFormat srcFrmt = new SimpleDateFormat("dd/MM/yyyy");
		Date dat = srcFrmt.parse(checkedInDt);
		ObjectMapper mapper = new ObjectMapper();
		logger.info("checkedInDt:::" + dat);
		logger.info("roomId:::" + roomId);
		logger.info("availcnt:::" + availcnt);
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		String json = null;
		List<AvailabilityByDateBean> availReserveDtList = new ArrayList<AvailabilityByDateBean>();
		List<AvailabilityByDateDTO> availDatesToReserveRoom = null;
		Long changeReservtn = (Long) session.getAttribute("changeReservtnId");
		logger.info("changeReservtn :: " + changeReservtn);

		availDatesToReserveRoom = reservationService
				.getAvailDatesToReserveRoom(hotelID, dat, roomId);

		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		AvailabilityByDateBean availReserveDt = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : availDatesToReserveRoom) {
			availReserveDt = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, availReserveDt);
			Date availDt = availReserveDt.getDate();
			String availDate = format.format(availDt);
			availReserveDt.setCheckedOutDate(availDate);
			availReserveDt.setRoomId(roomId);
			availReserveDt.setAvailcnt(availcnt);
			availReserveDt.setCategoryId(categoryId);
			availReserveDt.setCheckedInDate(format.format(dat));
			availReserveDtList.add(availReserveDt);
		}
		json = mapper.writeValueAsString(availReserveDtList);

		logger.info("getReservationDetails controller : end");
		return json;
	}

	@RequestMapping(value = "exportPDF.htm", method = RequestMethod.GET)
	public void exportPDF(Model model, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		logger.info("exportPDF method : start");
		String pdfPathVal = (String) session.getAttribute("pdfPathVal");
		logger.info("path value is (before):" + pdfPathVal);
		pdfPathVal = pdfPathVal.replace("/", "\\\\");
		logger.info("path value is (after):" + pdfPathVal);
		String path = pdfPathVal;
		ServletContext context = session.getServletContext();
		final int BUFFER_SIZE = 4096;
		try {
			logger.error("inside download get try");
			File downloadFile = new File(path);
			FileInputStream inputStream = new FileInputStream(downloadFile);

			String mimeType = context.getMimeType(path);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/pdf";
			}
			System.out.println("MIME type: " + mimeType);

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();

		} catch (Exception e) {
			logger.error("download failed came to catch block");
		}
		logger.info("exportPDF method : end");

	}

	@RequestMapping(value = "getHotelDetails.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getHotelDetails(HttpSession session, Model model)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getHotelDetails controller : start");
		ObjectMapper mapper = new ObjectMapper();
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		String json = null;
		json = mapper.writeValueAsString(hotelInfomrtn);
		logger.info("getHotel Details controller : end");
		return json;
	}

	@RequestMapping(value = "getUserDetails.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUserDetails(HttpSession session, Model model)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getUserDetails controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		String userName = null;
		UserDTO userdto = null;
		userName = (String) session.getAttribute("userName");
		if (userName == null) {
			userName = (String) session.getAttribute("servUserName");
		}
		if (userName != null) {
			logger.info("logged in user ");
			userdto = userService.getUserDetail(userName);
		} else {
			userdto = new UserDTO();
			GuestUserDTO guestUsr = (GuestUserDTO) session
					.getAttribute("quickUserDetails");
			Integer userId = guestUsr.getUserId();
			userdto.setGuestUserId(userId);
			String guestUsrNme = guestUsr.getUserName();
			userdto.setUserName(guestUsrNme);
			userdto.setEmail(guestUsr.getEmail());
			String phoneNumber = guestUsr.getPhoneNumber();
			userdto.setPhoneNumber(phoneNumber);
		}
		json = mapper.writeValueAsString(userdto);
		logger.info("getUserDetails controller : end");
		return json;
	}

	/**
	 * 
	 * @param jsondat
	 * @param session
	 * @param model
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws JSONException
	 * @throws DocumentException
	 */

	@RequestMapping(value = "isReservationProgress.htm", method = RequestMethod.POST)
	@ResponseBody
	public String isReservationprogress(
			@RequestParam("jsonData") String jsondata, HttpSession session,
			Model model, HttpServletRequest request) throws JSONException,
			JsonGenerationException, JsonMappingException, IOException {
		logger.info("isReservationprogress controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		JSONObject jsonObject = new JSONObject(jsondata);
		Long categoryId = jsonObject.getLong("categoryId");
		Long roomId = jsonObject.getLong("roomid");
		String checkedInDt = jsonObject.getString("checkedindate");
		String checkedoutdate = jsonObject.getString("checkedoutdate");

		boolean isProgress = reservationService.isReservationProgress(hotelID,
				categoryId, roomId, checkedInDt, checkedoutdate);
		json = mapper.writeValueAsString(isProgress);
		logger.info("isReservationprogress controller : end");
		return json;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "saveReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String saveReservation(@RequestParam("jsonData") String jsonData,
			HttpSession session, Model model, HttpServletRequest request)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException, JSONException, DocumentException {
		logger.info("save reservation controller : start");

		String reservationTemplatePath = "/letterTemplate/reservationTemplate.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String emailTemplateRealPath = servletContext
				.getRealPath(reservationTemplatePath);

		JSONObject jsonObject = new JSONObject(jsonData);
		String checkedInDt = jsonObject.getString("checkedindate");

		String checkedoutdate = jsonObject.getString("checkedoutdate");
		Long roomId = jsonObject.getLong("roomid");
		Long categoryId = jsonObject.getLong("categoryId");
		Long availcnt = jsonObject.getLong("availcnt");
		String notes = jsonObject.getString("notes");
		String emailShare = jsonObject.getString("emailShare");
		SimpleDateFormat srcFormat = new SimpleDateFormat("dd MMMM,yyyy");
		Date parse = srcFormat.parse(checkedInDt);
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		UserDTO userDetails = (UserDTO) session.getAttribute("userId");
		if (userDetails == null) {
			userDetails = (UserDTO) session.getAttribute("servUserId");
		}
		String email = null;
		ObjectMapper mapper = new ObjectMapper();
		Long hotelID = hotelInfomrtn.getHotelID();
		String userName = null;
		userName = (String) session.getAttribute("userName");
		if (userName == null) {
			userName = (String) session.getAttribute("servUserName");
		}
		UserDTO quickUserDto = null;
		BookingHistoryBean historyBean = new BookingHistoryBean();
		if (userName != null) {
			email = userDetails.getEmail();
			UserDTO userdto = userService.getUserDetail(userName);
			historyBean.setUserId(userdto.getUserId());
			historyBean.setUserName(userdto.getUserName());
			historyBean.setEmail(userdto.getEmail());
		} else {
			quickUserDto = new UserDTO();
			GuestUserDTO guestUserDto = (GuestUserDTO) session
					.getAttribute("quickUserDetails");
			quickUserDto.setGuestUserId(guestUserDto.getUserId());
			UserDTO userdto = userService.getPassWord(guestUserDto.getEmail());
			if (userdto != null
					&& userdto.getEmail().equalsIgnoreCase(
							guestUserDto.getEmail())) {
				userName = userdto.getUserName();
				historyBean.setUserName(userdto.getUserName());
				historyBean.setUserId(userdto.getUserId());
				email = userdto.getEmail();
				historyBean.setEmail(userdto.getEmail());
			} else {
				userName = guestUserDto.getUserName();
				historyBean.setUserName(userName);
				historyBean.setGuestUserId(guestUserDto.getUserId());
				email = guestUserDto.getEmail();
				historyBean.setEmail(email);
			}
			logger.info("guest user email address :" + email);
		}

		Map<Long, String> categoryListByMap = hotelService
				.getCategoryListByMap(hotelID);

		String json = null;
		SimpleDateFormat desformat = new SimpleDateFormat("dd MMMM,yyyy");
		Date date = new Date();
		historyBean.setBookingDate(date);
		historyBean.setCategoryId(categoryId);
		String categryName = categoryListByMap.get(categoryId);
		historyBean.setCategoryName(categryName);

		historyBean.setCheckInDate(parse);
		historyBean.setCheckOutDate(srcFormat.parse(checkedoutdate));
		historyBean.setRoomId(roomId);
		historyBean.setEmailShare(emailShare);
		String refNum = String.valueOf(CushinaUtil
				.generateRandomReferenceNumber());
		historyBean.setReservationNumber(Long.valueOf(refNum));
		historyBean.setArrived(false);
		historyBean.setPaid(false);
		historyBean.setNotes(notes);
		historyBean.setStatus("active");
		historyBean.setHotelID(hotelID);
		long diff = historyBean.getCheckOutDate().getTime()
				- historyBean.getCheckInDate().getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		historyBean.setNumberOfDays(diffDays);
		historyBean.setChckedInDate(desformat.format(historyBean
				.getCheckInDate()));
		historyBean.setChckedOutDate(desformat.format(historyBean
				.getCheckOutDate()));

		boolean save = reservationService.isSave(historyBean);

		if (save) {
			Long changeReservtn = (Long) session
					.getAttribute("changeReservtnId");
			logger.info("changeReservtn :: " + changeReservtn);
			if (changeReservtn != null) {
				logger.info("update change reservation , which he had alredy reserved");
				// update availibility_btyDate and hotel_availibility tables.
				boolean isChangeReservaion = reservationService
						.isChangeReservaion(changeReservtn);
			}

			String emailTemplate = "/letterTemplate/reservationTemplate.html";
			PDFGeneratingThread pdfGeneratingThread = new PDFGeneratingThread(
					request, hotelInfomrtn, historyBean, userName,
					emailTemplateRealPath, email, session);
			pdfGeneratingThread.start();
			json = mapper.writeValueAsString(historyBean);
		}
		invalidateChangeReservtnAttrib(session);
		logger.info("save reservation controller : end");
		return json;
	}

	private void invalidateChangeReservtnAttrib(HttpSession session) {
		logger.info("invalidate changeReservtn ID of current loggedIn user");
		session.setAttribute("changeReservtnId", null);

	}

	/**
	 * This method is used to delete reservation record permanently from
	 * DataBase.
	 * 
	 * 
	 * @param bookingID
	 * @param model
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "deleteReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String deleteReservation(
			@RequestParam("deleteRcrdId") Long bookingID, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("deleteReservation controller : start");
		String message = null;
		Long reservtnNum = null;
		reservtnNum = reservationService.deleteReservation(bookingID);
		if (reservtnNum != null) {
			message = "Reservation number " + reservtnNum
					+ " has been deleted successfully";
		} else {
			message = "Reservation number " + reservtnNum
					+ " has been failed to deleted";
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
	@RequestMapping(value = "cancelReservation.htm", method = RequestMethod.POST)
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

	@RequestMapping(value = "isSameHotel.htm", method = RequestMethod.POST)
	@ResponseBody
	public String isSameHotel(@RequestParam("bookingId") Long bookingID,
			Model model, HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("isSameHotel controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		// set bookingID in session.
		session.setAttribute("changeReservtnId", bookingID);
		String hotelIdVal = null;
		Long hotelIdDiff = reservationService.sameHotelReservtn(bookingID);
		hotelIdVal = String.valueOf(hotelIdDiff);
		json = mapper.writeValueAsString(hotelIdVal);
		logger.info("isSameHotel controller : end");
		return json;
	}

	@RequestMapping(value = "closeCnfrmtnPopUp.htm", method = RequestMethod.GET)
	public String invalidateChangeReservtnAttrib(Model model,
			HttpSession session) {
		logger.info("invalidateChangeReservtnAttrib  controller : start");
		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("login", "login");
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		logger.info("invalidateChangeReservtnAttrib  controller : end");
		return "hotel_reservation";
	}

	@RequestMapping(value = "changeHotel.htm", method = RequestMethod.GET)
	public String changeHotel(HttpServletRequest request, Model model,
			HttpSession session) {
		logger.info("changeHotel controller : start");
		String hotelId = (String) request.getParameter("hotelId");
		Long hotelVal = Long.valueOf(hotelId);
		List<HotelBean> hotelDetails = hotelService.getHotelDetail(hotelVal);
		HotelBean hotelBean = hotelDetails.get(0);
		logger.info("hotel Bean :" + hotelBean);
		session.setAttribute("hotel", hotelBean);
		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("login", "login");
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		logger.info("changeHotel controller : end");
		return "hotel_reservation";
	}

	@RequestMapping(value = "getCountNumberOfNights.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getCountOFDays(
			@RequestParam("checkoutdate") Date checkoutdate,
			@RequestParam("checkIndate") Date checkIndate, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getCountOFDays method : start");
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		long diff = checkoutdate.getTime() - checkIndate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		json = mapper.writeValueAsString(diffDays);
		logger.info("getCountOFDays method : start");
		return json;
	}

}
