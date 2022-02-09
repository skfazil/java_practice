package com.events.web.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.service.BookingHistoryService;
import com.cushina.service.PickHotelService;
import com.cushina.service.UserService;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.UserBean;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.service.EventBookingService;
import com.events.service.PickEventOrganizerService;
import com.events.web.bean.EventsBean;
import com.events.web.bean.EventsGuideBean;
import com.events.web.bean.EventsOrganizerBean;
import com.events.web.bean.EventsReservationBean;

@Controller
public class BookingEventHistoryController {

	private Logger logger = Logger.getLogger(BookingEventHistoryController.class
			.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private EventBookingService service;
	
	@Autowired
	private BookingHistoryService bookingHistoryService;
	
	@Autowired
	private PickHotelService hotelService;

	@Autowired
	private PickEventOrganizerService eventOrganizerService;
	
	@RequestMapping(value = "/getMyEventReservations.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getBookingDetails(Model model, HttpServletRequest request,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {

		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("servUserId");
		 if (userInfo != null){
			userId = userInfo.getUserId();
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();
		

		UserDTO userDTO = userService.getUserById(userId);
		logger.info("userDTO::::::::::::"+userDTO);
		String userName = userDTO.getUserName();
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy hh:mm:SS");
		List<EventsReservationBean> historyBeanList = new ArrayList<EventsReservationBean>();
		List<EventsReservationDTO> bookingHistory = null;
		if (userName != null) {
			bookingHistory = service.getEventBookingHistoryDetails(userId,
					 eventOrgID);
		} else {
			bookingHistory = service.getEventBookingHistoryDetails(userId,
					 eventOrgID);
		}
		
		
		EventsReservationBean eventsReservationBean=new EventsReservationBean();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		long previousDt = previousDate.getTime();
		
		for (EventsReservationDTO eventsReservationDTO : bookingHistory) {
			EventsReservationBean eventsReservation=new EventsReservationBean();
			if (eventsReservationDTO.isArrived()) {
				eventsReservation.setArrived(true);
			} else {
				eventsReservation.setArrived(false);
			}
			
			eventsReservation.setEventOrgAddress(eventInfomrtn.getEventOrgAddress());
			eventsReservation.setEventOrgCity(eventInfomrtn.getEventOrgCity());
			eventsReservation.setEventOrgPhoneNumber(eventInfomrtn.getEventOrgPhoneNumber());
			eventsReservation.setEventOrgPostalCode(eventInfomrtn.getEventOrgPostalCode());
			eventsReservation.setReferenceNumber(eventsReservationDTO.getReferenceNumber());
			Integer eventScheduleId = eventsReservationDTO.getEventScheduleId();
			EventScheduleDTO eventScheduleDTOs=service.getScheduleInforamtion(eventScheduleId);
			EventsDTO eventsDto=service.getEventName(eventScheduleDTOs.getEventId());
			eventsReservation.setEventScheduleId(eventScheduleId);
			Date startTime = eventScheduleDTOs.getStartTime();
			
			if (startTime.getTime() <= previousDt){
				eventsReservation.setStatus("not visited");
			}else{
				eventsReservation.setStatus(eventsReservationDTO.getStatus());
			}
			
			eventsReservation.setCheckInTime(format.format(startTime));
			Date endTime = eventScheduleDTOs.getEndTime();
			eventsReservation.setCheckOutTime(format.format(endTime));
			eventsReservation.setEventOrgName(eventInfomrtn.getEventOrgName());
			eventsReservation.setNumberOfDays(eventScheduleDTOs.getDuration());
			eventsReservation.setEventName(eventsDto.getEventName());
			eventsReservation.setReserveId(eventsReservationDTO.getReserveId());
			Integer userid=eventsReservationDTO.getUserID();
			UserDTO userDTOs = userService.getUserById(userid);
			eventsReservation.setUserName(userDTOs.getUserName());
			eventsReservation.setEmail(userDTOs.getEmail());
			eventsReservation.setEventOrgPhoneNumber(userDTOs.getPhoneNumber());
			System.out.println("reservation info"+eventsReservation);
			historyBeanList.add(eventsReservation);
		}

		
		json = mapper.writeValueAsString(historyBeanList);
		model.addAttribute("getSPUser", new UserBean());
		logger.info("getBookingDetails controller : end"+historyBeanList);
		return json;
	}
	
	
	@RequestMapping(value = "/getMyEventUserReservations.htm", method = RequestMethod.POST)
	@ResponseBody
	public String myEventUserDetails(Model model, HttpServletRequest request,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {logger.info("myEventUserDetails controller : start");
			Integer userId = null;
			UserDTO userInfo = null;
			userInfo = (UserDTO) session.getAttribute("userId");
			if (userInfo != null) {
				userId = userInfo.getUserId();
			} 
			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
					.getAttribute("event");
			Integer eventOrgID = eventInfomrtn.getEventOrgId();
			

			UserDTO userDTO = userService.getUserById(userId);
			logger.info("userDTO::::::::::::"+userDTO);
			String userName = userDTO.getUserName();
			SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy hh:mm:SS");
			List<EventsReservationBean> historyBeanList = new ArrayList<EventsReservationBean>();
			List< EventsReservationDTO> bookingHistory = null;
			if (userName != null) {
				bookingHistory = service.getEventBookingHistoryDetails(userId,
						 eventOrgID);
			} else {
				bookingHistory = service.getEventBookingHistoryDetails(userId,
						 eventOrgID);
			}
			
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			Date previousDate = cal.getTime();// get previous date
			long previousDt = previousDate.getTime();
			for (EventsReservationDTO eventsReservationDTO : bookingHistory) {
				EventsReservationBean eventsReservationBean=new EventsReservationBean();
				if (eventsReservationDTO.isArrived()) {
					eventsReservationBean.setArrived(true);
				} else {
					eventsReservationBean.setArrived(false);
				}
				
				eventsReservationBean.setEventOrgAddress(eventInfomrtn.getEventOrgAddress());
				eventsReservationBean.setEventOrgCity(eventInfomrtn.getEventOrgCity());
				eventsReservationBean.setEventOrgPhoneNumber(eventInfomrtn.getEventOrgPhoneNumber());
				eventsReservationBean.setEventOrgPostalCode(eventInfomrtn.getEventOrgPostalCode());
				eventsReservationBean.setReferenceNumber(eventsReservationDTO.getReferenceNumber());
				Integer eventScheduleId = eventsReservationDTO.getEventScheduleId();
				EventScheduleDTO eventScheduleDTOs=service.getScheduleInforamtion(eventScheduleId);
				EventsDTO eventsDto=service.getEventName(eventScheduleDTOs.getEventId());
				eventsReservationBean.setEventScheduleId(eventScheduleId);
				Date startTime = eventScheduleDTOs.getStartTime();
				
				if (startTime.getTime() <= previousDt){
					eventsReservationBean.setStatus("not visited");
				}else{
					eventsReservationBean.setStatus(eventsReservationDTO.getStatus());
				}
				
				eventsReservationBean.setCheckInTime(format.format(startTime));
				Date endTime = eventScheduleDTOs.getEndTime();
				eventsReservationBean.setCheckOutTime(format.format(endTime));
				eventsReservationBean.setEventOrgName(eventInfomrtn.getEventOrgName());
				eventsReservationBean.setNumberOfDays(eventScheduleDTOs.getDuration());
				eventsReservationBean.setEventName(eventsDto.getEventName());
				eventsReservationBean.setReserveId(eventsReservationDTO.getReserveId());
				Integer userid=eventsReservationDTO.getUserID();
				UserDTO userDTOs = userService.getUserById(userid);
				eventsReservationBean.setUserName(userDTOs.getUserName());
				eventsReservationBean.setEmail(userDTOs.getEmail());
				eventsReservationBean.setEventOrgPhoneNumber(userDTOs.getPhoneNumber());
				System.out.println("reservation info"+eventsReservationBean);
				historyBeanList.add(eventsReservationBean);
			}
			
			json = mapper.writeValueAsString(historyBeanList);
			model.addAttribute("getSPUser", new UserBean());
			logger.info("myEventUserDetails controller : end");
			return json;
	}
	
	
	@RequestMapping(value = "/bookingEventHistory.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getBookingEventDetails(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getBookingDetails controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		Long hotelID = 1L;
		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("servUserId");
		 if (userInfo != null){
			userId = userInfo.getUserId();
		}
		UserDTO userDTO = null;
		Integer quickUserId = null;
		String userName = userInfo.getUserName();
		if (userName != null) {
			userDTO = userService.getUserDetail(userName);
			userId = userDTO.getUserId();
		} else {
			GuestUserDTO guestUserDTO = (GuestUserDTO) session
					.getAttribute("QuickUserDetails");
			quickUserId = guestUserDTO.getUserId();
		}

		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		List<BookingHistoryBean> historyBeanList = new ArrayList<BookingHistoryBean>();
		List<Map<HotelDTO, BookingHistoryDTO>> bookingHistory = null;
		if (userName != null) {
			bookingHistory = bookingHistoryService.getBookingHistoryDetails(userId,
					quickUserId, hotelID);
		} else {
			bookingHistory = bookingHistoryService.getBookingHistoryDetails(userId,
					quickUserId, hotelID);
		}
		
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
				Map<Long, String> categoryListByMap = hotelService
						.getCategoryListByMap(dto.getHotelID());
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
				historyBean.setUserName(userName);
				historyBean.setEmail(userInfo.getEmail());
				historyBean.setPhoneNumber(userInfo.getPhoneNumber());
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
	
	@RequestMapping(value = "/bookingEventUserHistory.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getUserBookingDetails(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getBookingDetails controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		Long hotelID = 1L;
		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("userId");
		if (userInfo != null) {
			userId = userInfo.getUserId();
		} 

		UserDTO userDTO = null;
		Integer quickUserId = null;
		String userName = userInfo.getUserName();
		if (userName != null) {
			userDTO = userService.getUserDetail(userName);
			userId = userDTO.getUserId();
		} else {
			GuestUserDTO guestUserDTO = (GuestUserDTO) session
					.getAttribute("QuickUserDetails");
			quickUserId = guestUserDTO.getUserId();
		}

		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		List<BookingHistoryBean> historyBeanList = new ArrayList<BookingHistoryBean>();
		List<Map<HotelDTO, BookingHistoryDTO>> bookingHistory = null;
		if (userName != null) {
			bookingHistory = bookingHistoryService.getBookingHistoryDetails(userId,
					quickUserId, hotelID);
		} else {
			bookingHistory = bookingHistoryService.getBookingHistoryDetails(userId,
					quickUserId, hotelID);
		}
		
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
				Map<Long, String> categoryListByMap = hotelService
						.getCategoryListByMap(dto.getHotelID());
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
				historyBean.setUserName(userName);
				historyBean.setEmail(userInfo.getEmail());
				historyBean.setPhoneNumber(userInfo.getPhoneNumber());
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
	
	
	
	
	@RequestMapping(value = "isSameEventOrg.htm", method = RequestMethod.POST)
	@ResponseBody
	public String isSameHotel(@RequestParam("bookingId") Integer bookingID,
			Model model, HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("isSameHotel controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		
		// set bookingID in session.
		session.setAttribute("changeReservtnId", bookingID);

		String hotelIdVal = null;
		Integer eventOrgId = service.sameEventReservtn(bookingID);
		hotelIdVal = String.valueOf(eventOrgId);
		json = mapper.writeValueAsString(hotelIdVal);
		logger.info("isSameHotel controller : end");
		return json;
	}

	@RequestMapping(value = "changeEvnetUser.htm", method = RequestMethod.GET)
	public String changeEventUser(HttpServletRequest request, Model model,
			HttpSession session) {
		logger.info("changeHotel controller : start");
		String eventOrgId =request.getParameter("eventOrgId");
		
		 List<EventsOrganizerBean> eventOrganizationDetails = eventOrganizerService.getEventOrganizationDetailsById(Integer.valueOf(eventOrgId));
		 EventsOrganizerBean eventsOrganizerDTO = eventOrganizationDetails.get(0);
		logger.info("hotel Bean :" + eventsOrganizerDTO);
		session.setAttribute("event", eventsOrganizerDTO);		
		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("login", "login");
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("getSPUser", new UserBean());
		logger.info("changeHotel controller : end");
		return "event_reservation";
	}
	
	@RequestMapping(value = "changeEvnetOrg.htm", method = RequestMethod.GET)
	public String changeEventService(HttpServletRequest request, Model model,
			HttpSession session) {
		logger.info("changeHotel controller : start");
		String eventOrgId =request.getParameter("eventOrgId");
		
		 List<EventsOrganizerBean> eventOrganizationDetails = eventOrganizerService.getEventOrganizationDetailsById(Integer.valueOf(eventOrgId));
		 EventsOrganizerBean eventsOrganizerDTO = eventOrganizationDetails.get(0);
		logger.info("hotel Bean :" + eventsOrganizerDTO);
		session.setAttribute("event", eventsOrganizerDTO);		
		UserBean userBean = new UserBean();
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("login", "login");
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("bookReservation", new BookingHistoryBean());
		model.addAttribute("getSPUser", new UserBean());
		logger.info("changeHotel controller : end");
		return "serviceprovider_event";
	}
	@RequestMapping(value = "cancelEventReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String cancelReservation(@RequestParam("bookingID") Integer bookingID,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("cancelReservation controller : start");
		String status = null;
		Long reservtnNum = null;
		reservtnNum = service.cancelEventReservation(bookingID);
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
	@RequestMapping(value = "deleteEventReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String deleteReservation(
			@RequestParam("deleteRcrdId") Integer bookingID, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("deleteReservation controller : start");
		String message = null;
		Long reservtnNum = null;
		reservtnNum = service.deleteEventReservation(bookingID);
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
	
	@RequestMapping(value = "getEventAdminCatgryLst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getAdminCatgryLst(HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getAdminCatgryLst controller : start");
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<EventsBean> categryList = new ArrayList<EventsBean>();
		EventsBean categry = null;
		List<EventsDTO> categoryList = service.getCategoryList(eventOrgID);
		System.out.println("events ctgry:::"+categoryList);
		for (EventsDTO categoryDTO : categoryList) {
			categry = new EventsBean();
			BeanUtils.copyProperties(categoryDTO, categry);
			categryList.add(categry);
		}
		json = mapper.writeValueAsString(categryList);
		logger.info("getAdminCatgryLst controller : end");
		return json;
	}
	
	@RequestMapping(value = "getEventAdminRoomsLst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getAdminRoomsLst(@RequestParam("catVal") Integer catVal,
			HttpSession session, Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getAdminRoomsLst controller : start");
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<EventsGuideBean> categryList = new ArrayList<EventsGuideBean>();		
		EventsGuideBean roomInfoBean = null;
		List<EventsGuideDTO> roomsList = service.getRoomsAvailList(eventOrgID,catVal);
		for (EventsGuideDTO categoryDTO : roomsList) {
			roomInfoBean = new EventsGuideBean();
			BeanUtils.copyProperties(categoryDTO, roomInfoBean);
			categryList.add(roomInfoBean);
		}
		json = mapper.writeValueAsString(categryList);
		logger.info("room list :" + categryList);
		logger.info("getAdminRoomsLst controller : end");
		return json;
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "addEventTOWhteLst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addRoomTOWhteLst(@RequestParam("eventId") Integer eventId,
			@RequestParam("guideName") String guideName, Model model,
			HttpSession session) {
		logger.info("addRoomTOWhteLst controller : start");
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session.getAttribute("event");
		Integer eventOrgID = eventInfomrtn.getEventOrgId();
		Boolean addRoomTOWhteLst = service.addRoomTOWhteLst(eventId,guideName,eventOrgID);
		logger.info("addRoomTOWhteLst controller : end");
		return String.valueOf(guideName);
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
