package com.cushina.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.service.BookingHistoryService;
import com.cushina.service.PickHotelService;
import com.cushina.service.UserService;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;

@Controller
public class BookingHistoryController {

	private Logger logger = Logger.getLogger(BookingHistoryController.class
			.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private BookingHistoryService service;

	@Autowired
	private PickHotelService hotelService;

	@RequestMapping(value = "/bookingHistory.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getBookingDetails(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getBookingDetails controller : start");

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		UserDTO userDTO = null;
		Integer userId = null, quickUserId = null;
		
		String userName = null;
		userName = (String) session.getAttribute("userName");
		logger.info("spusername :::: "+ session.getAttribute("servUserName"));
		if(userName == null){
			userName = (String) session.getAttribute("servUserName");
		}

		if (userName != null) {
			userDTO = userService.getUserDetail(userName);
			userId = userDTO.getUserId();
		} else {
			GuestUserDTO guestUserDTO = (GuestUserDTO) session
					.getAttribute("QuickUserDetails");
			quickUserId = guestUserDTO.getUserId();
		}

		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		// Map<HotelBean, BookingHistoryBean> map = new HashMap<HotelBean,
		// BookingHistoryBean>();
		List<BookingHistoryBean> historyBeanList = new ArrayList<BookingHistoryBean>();
		List<Map<HotelDTO, BookingHistoryDTO>> bookingHistory = null;
		if (userName != null) {
			bookingHistory = service.getBookingHistoryDetails(userId,
					quickUserId, hotelID);
		} else {
			bookingHistory = service.getBookingHistoryDetails(userId,
					quickUserId, hotelID);
		}
		logger.info("bookingHistory :: " +bookingHistory);
		
		Map<Long, String> categoryListByMap = hotelService
				.getCategoryListByMap(hotelID);

		BookingHistoryBean historyBean = null;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		long previousDt = previousDate.getTime();
		for (int i = 0; i < bookingHistory.size(); i++) {
			Map<HotelDTO, BookingHistoryDTO> mapInfo = (Map<HotelDTO, BookingHistoryDTO>) bookingHistory
					.get(i);
			Set<HotelDTO> keySet = mapInfo.keySet();
			Iterator<HotelDTO> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				HotelDTO dto = (HotelDTO) iterator.next();
				BookingHistoryDTO historyDTO = mapInfo.get(dto);
				Date checkIn = historyDTO.getCheckInDate();
				long time = checkIn.getTime();
				if (time <= previousDt)
					historyDTO.setStatus("not visited");

				historyBean = new BookingHistoryBean();
				BeanUtils.copyProperties(historyDTO, historyBean);
				Long categoryId = historyDTO.getCategoryId();
				String categryName = categoryListByMap.get(categoryId);
				if (historyDTO.isArrived()) {
					historyBean.setArrivedVal("1");
				} else {
					historyBean.setArrivedVal("0");
				}
				historyBean.setCategoryName(categryName);
				historyBean.setHotelName(dto.getHotelName());
				historyBean.setHotelAddress(dto.getHotelAddress());
				historyBean.setHotelPhneNumber(dto.getPhoneNumber());
				historyBean.setCity(dto.getCity());
				historyBean.setPostalCode(dto.getPostalCode());
				Date checkInDate = historyDTO.getCheckInDate();
				Date checkOutDate = historyBean.getCheckOutDate();
				historyBean.setCheckInDt(format.format(checkInDate));
				historyBean.setCheckOutDt(format.format(checkOutDate));
				historyBeanList.add(historyBean);

				iterator.remove();// avoids concurrenModification....
			}
		}

		Collections.sort(historyBeanList, new Comparator<BookingHistoryBean>() {

			public int compare(BookingHistoryBean histryBeanOne,
					BookingHistoryBean histryBeanTwo) {
				return histryBeanOne.getCheckInDate().compareTo(
						histryBeanTwo.getCheckInDate());
			}
		});
		json = mapper.writeValueAsString(historyBeanList);
		logger.info("getBookingDetails controller : end");
		return json;
	}

	@RequestMapping(value = "/deleteHistory.htm", method = RequestMethod.GET)
	public String deleteHistory(HttpServletRequest request, Model model) {
		Integer registrationId = Integer.valueOf(request.getParameter("id"));
		logger.info("deleteHistory : start");
		logger.info("registration id :" + registrationId);

		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		logger.info("deleteHistory : end");
		return "hotel_reservation";
	}
}

class CheckInDtComparator implements Comparable<BookingHistoryBean> {

	private Date checkInDate;
	private Date checkOutDate;

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int compareTo(BookingHistoryBean histrybean) {
		return this.checkInDate.getTime() > histrybean.getCheckInDate()
				.getTime() ? 1 : (this.checkInDate.getTime() > histrybean
				.getCheckInDate().getTime() ? -1 : 0);
	}

	public String toString() {
		return "CheckInDtComparator [checkInDate=" + checkInDate
				+ ", checkOutDate=" + checkOutDate + "]";
	}

}
