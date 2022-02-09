package com.events.web.controller;



import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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

import com.cushina.common.dto.UserDTO;
import com.cushina.common.util.MailUtil;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.UserBean;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.service.EventUserByObjectService;
import com.events.web.bean.EventScheduleBean;
import com.events.web.bean.EventsBean;
import com.events.web.bean.EventsOrganizerBean;
import com.events.web.bean.EventsReservationBean;


@Controller
public class EventUserByObjectContrller {
	
	Logger logger = Logger.getLogger(EventUserByObjectContrller.class);
	
	@Autowired
	private EventUserByObjectService eventByObjectService;
	
	
	@RequestMapping(value = "getEventUserDatesLst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getEventDatesList(HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getEventDatesList controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean orgnizer = (EventsOrganizerBean) session
				.getAttribute("event");
		List<EventScheduleBean> datesList = new ArrayList<EventScheduleBean>();
		EventScheduleBean scheduleDt = null;
		List<EventScheduleDTO> orginizerInfo = eventByObjectService
				.getEventsDatesList(orgnizer.getEventOrgId());
		for (EventScheduleDTO eventScheduleDTO : orginizerInfo) {
			scheduleDt = new EventScheduleBean();
			BeanUtils.copyProperties(eventScheduleDTO, scheduleDt);
			datesList.add(scheduleDt);
		}
		json = mapper.writeValueAsString(datesList);
		logger.info("getEventDatesList controller : end");
		return json;
	}

	/*@RequestMapping(value = "getDatesLst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCarousalDatesLst(HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getEventDatesList controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean orgnizer = (EventsOrganizerBean) session
				.getAttribute("event");
		List<EventScheduleBean> datesList = new ArrayList<EventScheduleBean>();
		List<EventScheduleBean> scheduleLst = new ArrayList<EventScheduleBean>();
		EventScheduleBean scheduleDt = null;
		EventScheduleBean scheduleDate = null;
		List<EventScheduleDTO> orginizerInfo = eventByObjectService
				.getCarouselDatesList(orgnizer.getEventOrgId());
		for (EventScheduleDTO eventScheduleDTO : orginizerInfo) {
			scheduleDt = new EventScheduleBean();
			List<EventScheduleDTO> eventScheduleDTOs = eventScheduleDTO
					.getEventScheduleDTOs();
			BeanUtils.copyProperties(eventScheduleDTO, scheduleDt);
			datesList.add(scheduleDt);
		}

		for (EventScheduleDTO eventScheduleDTOs : orginizerInfo) {
			List<EventScheduleDTO> eventScheduleDTO = eventScheduleDTOs
					.getEventScheduleDTOs();
			for (EventScheduleDTO eventSchedulesDTO : eventScheduleDTO) {
				scheduleDate = new EventScheduleBean();
				BeanUtils.copyProperties(eventSchedulesDTO, scheduleDate);
				scheduleLst.add(scheduleDate);
			}
			scheduleDt.setEventScheduleBeans(scheduleLst);
		}

		logger.info("datesList *** ::: " + orginizerInfo);
		json = mapper.writeValueAsString(orginizerInfo);
		logger.info("getEventDatesList controller : end");
		return json;
	}

	@RequestMapping(value = "getStrtAndEndTimeInfo.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getStrtAnsEndTimeOfEvnt(
			@RequestParam("currDt") String currDt, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getStrtAnsEndTimeOfEvnt controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean orgnizer = (EventsOrganizerBean) session
				.getAttribute("event");
		EventScheduleDTO strtTmeAndEndTme = eventByObjectService
				.getStrtTmeAndEndTme(orgnizer.getEventOrgId(), currDt);
		EventScheduleBean scheduleTme = new EventScheduleBean();
		BeanUtils.copyProperties(strtTmeAndEndTme, scheduleTme);
		json = mapper.writeValueAsString(scheduleTme);
		logger.info("getStrtAnsEndTimeOfEvnt controller : end");
		return json;
	}*/

	@RequestMapping(value = "slctEventlst.htm", method = RequestMethod.POST)
	@ResponseBody
	public String selectTour(HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("selectTour controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean orgnizer = (EventsOrganizerBean) session
				.getAttribute("event");
		List<EventsBean> toursList = new ArrayList<EventsBean>();
		EventsBean tour = null;
		List<EventsDTO> allTours = eventByObjectService.getAllTours(orgnizer
				.getEventOrgId());
		for (EventsDTO eventsDTO : allTours) {
			tour = new EventsBean();
			BeanUtils.copyProperties(eventsDTO, tour);
			toursList.add(tour);
		}
		json = mapper.writeValueAsString(toursList);
		logger.info("selectTour controller : end");
		return json;
	}

/*	@RequestMapping(value = "getGuidesInfo.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getGuidesLst(@RequestParam("tourVal") Integer tourVal,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getGuidesLst controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean orgnizer = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer eventOrgId = orgnizer.getEventOrgId();
		logger.info("tour val :" + tourVal);
		List<EventsGuideBean> guidesLst = new ArrayList<EventsGuideBean>();
		EventsGuideBean guide = null;
		List<EventsGuideDTO> guideList = eventByObjectService.getGuideList(
				tourVal, eventOrgId);
		for (EventsGuideDTO eventsGuideDTO : guideList) {
			guide = new EventsGuideBean();
			BeanUtils.copyProperties(eventsGuideDTO, guide);
			guidesLst.add(guide);
		}
		json = mapper.writeValueAsString(guidesLst);
		logger.info("getGuidesLst controller : end");
		return json;
	}
	*/
/*	@RequestMapping(value = "/getDataBasedOnEvent.htm" , method = RequestMethod.POST)
	@ResponseBody
	public String getDataBsdOnEvents(@RequestParam("slctdEvnt") Integer eventVal, 
			HttpServletRequest request , HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		Map<Date, List<EventScheduleDTO>> scheduleMap = eventByGuideService.getDataByEventId(eventVal);
		Map<String, List<EventScheduleBean>> scheduleBeanMap = new LinkedHashMap<String, List<EventScheduleBean>>();		
		EventScheduleBean bean = null;
		Set<Date> datesSet = scheduleMap.keySet();
		logger.info("datesSet : "+datesSet);
		for(Date date : datesSet) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateString = formatter.format(date);
			List<EventScheduleBean> listBean = new ArrayList<EventScheduleBean>();
			List<EventScheduleDTO> listDto = scheduleMap.get(date);
			for(EventScheduleDTO dto : listDto) {
				SimpleDateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
				bean = new EventScheduleBean();
				BeanUtils.copyProperties(dto, bean);
				Integer divCount = eventByGuideService.getDivCount(bean.getEvntStrtTme(),bean.getEvntEndTme());
				Date eventDt = bean.getDate();
				bean.setEvntDt(formt.format(eventDt));
				bean.setDivCount(divCount);
				listBean.add(bean);
			}
			scheduleBeanMap.put(dateString, listBean);
		}
		logger.info("before returning inside controller scheduleBeanMap : "+scheduleBeanMap);
		json = mapper.writeValueAsString(scheduleBeanMap);
		return json;
	}*/

	@SuppressWarnings("unused")
	@RequestMapping(value = "/showUserReservationlist.htm" , method = RequestMethod.POST)
	@ResponseBody
	public String getEvntReservedUserList(@RequestParam("scheduleId") Integer scheduleId,
			HttpServletRequest request,HttpSession session) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		Map<EventScheduleDTO,List<EventsReservationDTO>> map = eventByObjectService.getEventReservedUsersMap(scheduleId);
		Set<EventScheduleDTO> set = map.keySet();
		List<EventsReservationBean> listBean = new ArrayList<EventsReservationBean>();
		Map<String,List<EventsReservationBean>> beanMap = new HashMap<String, List<EventsReservationBean>>();
		EventScheduleBean scheduleBean = new EventScheduleBean();
		EventsReservationBean reserveBean = null;
		for(EventScheduleDTO dto : set) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy - HH:mm");
			List<EventsReservationDTO> listDto = map.get(dto);
			BeanUtils.copyProperties(dto, scheduleBean);
			scheduleBean.setEvntStrtTme(formatter.format(scheduleBean.getStartTime()));
			scheduleBean.setEvntEndTme(formatter.format(scheduleBean.getEndTime()));
			for(EventsReservationDTO reserveDto : listDto) {
				reserveBean = new EventsReservationBean();
				BeanUtils.copyProperties(reserveDto, reserveBean);
				listBean.add(reserveBean);
			}			
		}
		String key = mapper.writeValueAsString(scheduleBean);
		beanMap.put(key, listBean);
		logger.info("getevntreserved befre returning beanMAp : "+beanMap);
		json = mapper.writeValueAsString(beanMap);
		return json;
	}
	
		@RequestMapping(value = "/saveUserReservationInfo.htm" , method = RequestMethod.POST)
	@ResponseBody
	public String saveGuestResrvtion(@RequestParam("json") String json,
			Model model, HttpSession session, HttpServletRequest request) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json1 = null;
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		EventsOrganizerBean organizer = (EventsOrganizerBean) session.getAttribute("event");
		//UserDTO userDto = (UserDTO) session.getAttribute("servUserId");
		JSONObject jsonObj = new JSONObject(json);
		String userName = jsonObj.getString("userName");
		String phoneNumber = jsonObj.getString("phone");
		String email = jsonObj.getString("email");
		Integer selectedSeats = jsonObj.getInt("slctdSeats");
		Integer scheduleId = jsonObj.getInt("scheduleId");
		String notes = jsonObj.getString("notes");
		String strtTime = jsonObj.getString("strtTime");
		String endTime = jsonObj.getString("endTime");
		String duration = jsonObj.getString("duration");
		Integer availSeats = jsonObj.getInt("availSeats");
		String eventName = jsonObj.getString("eventName");
		Integer diffOfSeats = availSeats-selectedSeats;
		String share = jsonObj.getString("share");
		logger.info("organizer in session : "+share);
		logger.info("logged in userdto : "+userDto);
		logger.info("userName "+userName+" phoneNumber "+phoneNumber+" email "+email+" selectedSeats "+selectedSeats+" scheduleId "+scheduleId+" notes "+notes);
		
		/*EventGuestUserDTO guestDto = new EventGuestUserDTO();
		
		guestDto.setUserName(userName);
		guestDto.setEmail(email);
		guestDto.setPhoneNumber(phoneNumber);
		guestDto.setFirstName(userName);
		guestDto.setScheduleId(scheduleId);
		guestDto.setServProId(userDto.getUserId());		
		Integer guestId = eventByObjectService.saveReservedGuest(guestDto);*/
		
		EventsReservationDTO reservationDto = new EventsReservationDTO();
		reservationDto.setNoOfPeople(selectedSeats);
		reservationDto.setUserID(userDto.getUserId());
		reservationDto.setEventScheduleId(scheduleId);
		reservationDto.setEventOrgId(organizer.getEventOrgId());
		reservationDto.setNotes(notes);
		reservationDto.setEmailShare(share);
		
		Long refNumber = eventByObjectService.saveEventReservation(reservationDto);
		
		Integer isUpdate = eventByObjectService.updateAvailSeats(scheduleId,diffOfSeats);
		
		String body = "Hi Mr/Mrs "+userName+"!"
				+ " You have successfully reserved "+selectedSeats+" seats for the tour of "+eventName+" "
				+ " with reference number "+refNumber+". The tour duration will be from "+strtTime+" to "
				+ " "+endTime+". Have a safe tour!";
		String subject = "Your tour reservation details!";	
		
		if(isUpdate>0) {
			boolean isSent = MailUtil.sendMail(email, body, subject);					
		}
		
		json1 = mapper.writeValueAsString(refNumber);	
		return json1;
	}
	
	@RequestMapping(value = "/getEventUserByOjectInfo.htm" , method=RequestMethod.POST)
	@ResponseBody
	public String loadDataByClckedDt(@RequestParam("clickedDt") String stringdate,@RequestParam("eventId") Integer eventId,
			 HttpServletRequest request,HttpSession session) throws ParseException, JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		logger.info("clickedDt : "+stringdate+" eventId : "+eventId+" guideId : ");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(stringdate);
		Map<Date,List<EventScheduleDTO>> map = eventByObjectService.getDataMap(date,eventId,userDto);
		logger.info("dto map in loadDataByclikcedDt controller : "+map);
		Map<String, List<EventScheduleBean>> scheduleBeanMap = new LinkedHashMap<String, List<EventScheduleBean>>();		
		EventScheduleBean bean = null;
		Set<Date> datesSet = map.keySet();
		logger.info("datesSet : "+datesSet);
		for(Date dt : datesSet) {
			SimpleDateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
			String dateString = formt.format(dt);
			List<EventScheduleBean> listBean = new ArrayList<EventScheduleBean>();
			List<EventScheduleDTO> listDto = map.get(dt);
			for(EventScheduleDTO dto : listDto) {
				SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
				bean = new EventScheduleBean();
				BeanUtils.copyProperties(dto, bean);
				Integer divCount = eventByObjectService.getDivCount(bean.getEvntStrtTme(),bean.getEvntEndTme());
				Date eventDt = bean.getDate();
				bean.setEvntDt(frmt.format(eventDt));
				bean.setDivCount(divCount);
				listBean.add(bean);
			}
			scheduleBeanMap.put(dateString, listBean);
		}
		logger.info("before returning inside controller loadDatabyclickedDt scheduleBeanMap : "+scheduleBeanMap);
		json = mapper.writeValueAsString(scheduleBeanMap);
		
		return json;		
	}
/*	
	@RequestMapping(value = "/addTowhtlst.htm" , method=RequestMethod.POST)
	@ResponseBody
	public String addToWhitelist(@RequestParam("reserveId") Integer reserveId, 
			@RequestParam("scheduleId") Integer scheduleId,	HttpServletRequest request, HttpSession session) {
		logger.info("addToWhitelist in controller : start");
		logger.info("reseveId : "+reserveId+"scheduleId"+scheduleId);
		EventsOrganizerBean organizer = (EventsOrganizerBean) session.getAttribute("event");
		Integer orgId = organizer.getEventOrgId();
		String status = eventByObjectService.addToWhiteList(reserveId, scheduleId, orgId);
		logger.info("whitelist controller : returning status : "+status);
		logger.info("addToWhitelist in controller : start");
		return status;
	}
	
	@RequestMapping(value = "/addToBlcklist.htm" , method = RequestMethod.POST)
	@ResponseBody
	public String addToBlacklist(@RequestParam("reserveId") Integer reserveId, 
			@RequestParam("scheduleId") Integer scheduleId,	HttpServletRequest request, HttpSession session) {
		logger.info("reseveId : "+reserveId+"scheduleId"+scheduleId);
		EventsOrganizerBean organizer = (EventsOrganizerBean) session.getAttribute("event");
		Integer orgId = organizer.getEventOrgId();
		String status = eventByObjectService.addToBlackList(reserveId, scheduleId, orgId);
		logger.info("whitelist controller : returning status : "+status);
		return status;
	}
	
	@RequestMapping(value = "/setpaid.htm" , method = RequestMethod.POST)
	@ResponseBody	
	public String setPaid(@RequestParam("reserveId") Integer reserveId,
			HttpServletRequest request, HttpSession session) {
		logger.info("reseveId : "+reserveId);
		String status = eventByObjectService.setPaid(reserveId);
		return status;
	}
	
	@RequestMapping(value = "/setarrived.htm" , method = RequestMethod.POST)
	@ResponseBody	
	public String setArrived(@RequestParam("reserveId") Integer reserveId,
			HttpServletRequest request, HttpSession session) {
		logger.info("reseveId : "+reserveId);
		String status = eventByObjectService.setArrived(reserveId);
		return status;
	}*/
	
	
	@RequestMapping(value="/returnToEventUserByObject.htm" , method = RequestMethod.GET)
	public String returnToSrviceHme(Model model , HttpServletRequest request,
			HttpSession session) {
		//model.addAttribute("getSPUser", new UserBean());
		return "event_reservation_byobject";
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/eventUserByObject.htm", method=RequestMethod.GET)
	public String servicebyguide(@RequestParam("clickedDt") String stringdate,@RequestParam("eventId") Integer eventId,
			HttpServletRequest request,HttpSession session,Model model) throws ParseException, JsonGenerationException, JsonMappingException, IOException{
		//String stringdate ="10/11/2015";
		//Integer eventId =0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(stringdate);
		System.out.println("date format"+date);
		String currentDate=format.format(date);
		System.out.println("currentDate"+currentDate);
		UserBean userBean = new UserBean();
		if(eventId != 0){
			model.addAttribute("evntDate", currentDate);
			model.addAttribute("evntId", eventId);
			String evntName=eventByObjectService.getEventName(eventId);
			model.addAttribute("eventName", evntName);
			
		}else{
			//eventId =0;
			model.addAttribute("evntDate", currentDate);
			model.addAttribute("evntId", eventId);
			model.addAttribute("userLogin", userBean);
			model.addAttribute("registerUser", userBean);
			model.addAttribute("adminLogin", userBean);
			model.addAttribute("registerAdmin", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("getUser", userBean);
			model.addAttribute("quickReservation", userBean);
			model.addAttribute("bookReservation", new BookingHistoryBean());
			return "event_reservation";
		}
		model.addAttribute("userLogin", userBean);
		return "event_reservation_byobject";
		//return "event_reservation_byobject";
	}
		

}
