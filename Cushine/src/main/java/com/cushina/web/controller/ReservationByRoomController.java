package com.cushina.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.CategoryDTO;
import com.cushina.service.PickHotelService;
import com.cushina.service.ReservationByRoomService;
import com.cushina.web.bean.AvailabilityByDateBean;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.CategoryBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;

@Controller

public class ReservationByRoomController {

	private Logger logger = Logger.getLogger(ReservationByRoomController.class);

	@Autowired
	private ReservationByRoomService roomService;

	@Autowired
	private PickHotelService service;

	/**
	 * This method is used to get rooms avail list based on date and category
	 * type and room Number selected by user.
	 * 
	 * @param categoryType
	 * @param roomNo
	 * @param currentDt
	 * @param session
	 * @return
	 * @throws ParseException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "roomAvailByCategoryAndRoomNum.htm", method = RequestMethod.POST)
	@ResponseBody
	public String initReservationByRoom(
			@RequestParam("cat") String categoryType,
			@RequestParam("roomNo") String roomNo,
			@RequestParam("currDate") String currentDt, HttpSession session)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("initReservationByRoom controller : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat srcFrmt = new SimpleDateFormat("dd-MM-yyyy");
		logger.info("current date :" + currentDt);
		Date currentDtVal = srcFrmt.parse(currentDt);
		Long categoryId = Long.valueOf(categoryType);
		Long roomNum = Long.valueOf(roomNo);

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
	
		ObjectMapper mapper = new ObjectMapper();
		String roomsAvailList = null;

		/**
		 * Here get rooms avail info by category and selected room number and
		 * shows it on hotel_reservation_byroom jsp.
		 */
		List<AvailabilityByDateBean> availRoomsByDateWise = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean availByDt = null;
		List<AvailabilityByDateDTO> roomAvailByCategory = roomService
				.getRoomAvailByCategoryAndRoomNum(categoryId, hotelID, roomNum,currentDtVal);
		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByCategory) {
			availByDt = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, availByDt);
			Date date = availByDt.getDate();
			String roomAvailDate = destFrmt.format(date);
			availByDt.setRoomAvailDate(roomAvailDate);
			availByDt.setCategoryId(categoryId);
			availByDt.setRoomId(roomNum);
			availRoomsByDateWise.add(availByDt);
		}
		logger.info("availability by date bean List :" + availRoomsByDateWise);
		roomsAvailList = mapper.writeValueAsString(availRoomsByDateWise);
		logger.info("rooms avail list :" + roomsAvailList);
		logger.info("initReservationByRoom controller : end");
		return roomsAvailList;
	}

	/**
	 * This method is used to get dates avail list and shows it on the dates
	 * bar.
	 * 
	 * @param categoryType
	 * @param roomNo
	 * @param currentDt
	 * @param session
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "getDatesByRoomNum.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getDatesByRoomNum(@RequestParam("cat") String categoryType,
			@RequestParam("roomNo") String roomNo,
			@RequestParam("currDate") String currentDt, HttpSession session)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("initReservationByRoom controller : start");
		
		String datesAvailJSON = null;
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat srcFrmt = new SimpleDateFormat("dd-MM-yyyy");
		
		Long categoryId = Long.valueOf(categoryType);
		Long roomNum = Long.valueOf(roomNo);

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		logger.info("parameter val :" + categoryType + " room No :" + roomNo);

		Date prseDt = srcFrmt.parse(currentDt);
		String parsedDt = destFrmt.format(prseDt);
		prseDt = destFrmt.parse(parsedDt);
		logger.info("parsed date $$$ ::: " + prseDt);
		ObjectMapper mapper = new ObjectMapper();

		/**
		 * This snippet is used to get dates list based on the room, which is
		 * selected by the customer
		 */
		List<AvailabilityByDateBean> datesBeansList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean dateBean = null;
		List<AvailabilityByDateDTO> dateListByRoomNo = roomService
				.getDateListByRoomNo(roomNum, prseDt, hotelID, categoryId);
		for (AvailabilityByDateDTO availabilityByDateDTO : dateListByRoomNo) {
			dateBean = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, dateBean);
			Date date = dateBean.getDate();
			String roomAvailDate = destFrmt.format(date);
			dateBean.setRoomAvailDate(roomAvailDate);
			datesBeansList.add(dateBean);
		}
		logger.info("avail dates beans list : " + datesBeansList);
		datesAvailJSON = mapper.writeValueAsString(datesBeansList);
		logger.info("dates avail json :" + datesAvailJSON);
		return datesAvailJSON;
	}

	@RequestMapping(value = "getRoomsAvailByDayCount.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getRoomsAvailByDateCount(
			@RequestParam("category") String category,
			@RequestParam("roomNo") String roomNo,
			@RequestParam("noOfDays") String daysCount,
			@RequestParam("currDate") String currDate, Model model,
			HttpSession session) throws ParseException,
			JsonGenerationException, JsonMappingException, IOException {
		logger.info("getRoomsAvailByDateCount controller : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat srcFrmt = new SimpleDateFormat("dd-MM-yyyy");
		
		ObjectMapper mapper = new ObjectMapper();
		String availListByNoOfDays = null;

		Long categoryID = Long.valueOf(category);
		Long roomNum = Long.valueOf(roomNo);
		Integer dayCount = Integer.valueOf(daysCount);
		Date prseDt = srcFrmt.parse(currDate);
		String parsedDt = destFrmt.format(prseDt);
		prseDt = destFrmt.parse(parsedDt);

		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();

		List<AvailabilityByDateBean> availDtBeansList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean availDtBeans = null;
		List<AvailabilityByDateDTO> roomAvailByDaysCount = roomService
				.getRoomAvailByDaysCount(categoryID, hotelID, roomNum,
						dayCount, prseDt);
		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByDaysCount) {
			availDtBeans = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, availDtBeans);
			Date date = availDtBeans.getDate();
			String roomAvailDate = destFrmt.format(date);
			availDtBeans.setRoomAvailDate(roomAvailDate);
			availDtBeansList.add(availDtBeans);
		}
		logger.info("roomAvailByDaysCount list :" + availDtBeansList);
		availListByNoOfDays = mapper.writeValueAsString(availDtBeansList);
		logger.info("JSON data :"+availListByNoOfDays);
		logger.info("getRoomsAvailByDateCount controller : end");
		return availListByNoOfDays;
	}

	@RequestMapping(value = "roomAvailInfoByCategoryType.htm", method = RequestMethod.GET)
	public String initRoomAvailInfoByCategoryType(
			@RequestParam("type") Long categoryId,
			@RequestParam("currDarte") String currDate, Model model,
			HttpSession session) throws ParseException {
		DateFormat dtFrmt = new SimpleDateFormat("yyyy/MM/dd");
		// get hotelId value from session and pass it as parameter.
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		String selectedDt = (String) session.getAttribute("currentDate");
		Date currDt = dtFrmt.parse(currDate);
		logger.info("selected date is :" + selectedDt + " : " + currDt);
		Long roomNo = 0L;
		Integer dayCount = 0;

		List<AvailabilityByDateBean> availCategoryList = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean byDateBean = null;
		List<AvailabilityByDateDTO> dateListByRoomNo = roomService
				.getDateListByRoomNo(roomNo, currDt, hotelID, categoryId);
		for (AvailabilityByDateDTO availabilityByDateDTO : dateListByRoomNo) {
			byDateBean = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, byDateBean);
			availCategoryList.add(byDateBean);
		}
		logger.info("avail dates list by category :" + availCategoryList);

		List<AvailabilityByDateBean> availabilityByDateBeans = new ArrayList<AvailabilityByDateBean>();
		AvailabilityByDateBean dateBean = null;
		List<AvailabilityByDateDTO> roomAvailByDaysCount = roomService
				.getRoomAvailByDaysCount(categoryId, hotelID, roomNo, dayCount,
						currDt);
		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByDaysCount) {
			dateBean = new AvailabilityByDateBean();
			BeanUtils.copyProperties(availabilityByDateDTO, dateBean);
			Date date = dateBean.getDate();
			String roomAvailDate = dtFrmt.format(date);
			dateBean.setRoomAvailDate(roomAvailDate);
			availabilityByDateBeans.add(dateBean);
		}
		logger.info("avail dates list in selected category :"
				+ availabilityByDateBeans);

		/**
		 * This snippet is for displaying rooms based on category, which type
		 * user selected.
		 */
		HotelBean hotel = (HotelBean) session.getAttribute("hotel");
		List<Long> roomsList = null;
		if (categoryId != null) {
			logger.info(categoryId);
			roomsList = service.getRoomsAvailList(hotelID, categoryId);
		}

		/**
		 * Below snippet is used to get the category List, i.e.,
		 * single,double,triple and suits.
		 */
		List<CategoryDTO> categoryList = service.getCategoryList(hotelInfomrtn
				.getHotelID());
		List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
		CategoryBean categoryBean = null;
		for (CategoryDTO categoryDto : categoryList) {
			categoryBean = new CategoryBean();
			BeanUtils.copyProperties(categoryDto, categoryBean);
			categoryBeans.add(categoryBean);
		}
		String selectedDate = (String) session.getAttribute("currentDate");
		UserBean userBean = new UserBean();
		AvailabilityByDateBean reservtnByNoOfDays = new AvailabilityByDateBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("currentDate", selectedDate);
		model.addAttribute("roomsList", roomsList);
		model.addAttribute("categoryTypeList", categoryBeans);
		model.addAttribute("categoryTypeId", categoryId);
		model.addAttribute("reservtnByNoOfDays", reservtnByNoOfDays);
		model.addAttribute("availRoomsByDateWise", availabilityByDateBeans);// showing
																			// avail
																			// and
																			// booked
																			// dates
																			// in
																			// widget.
		model.addAttribute("availDatesByroom", availCategoryList);// showing
																	// dates on
																	// dates
																	// bar.
		return "hotel_reservation_byroom";
	}
}
