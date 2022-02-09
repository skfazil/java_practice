package com.events.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cushina.common.dto.UserDTO;
import com.cushina.common.util.MailUtil;
import com.cushina.scheduler.MyServletContextListener;
import com.cushina.service.UserService;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.common.util.EventCancelReservationMailThread;
import com.events.common.util.EventReceiptPDFGeneratingThread;
import com.events.model.pojo.EventOrganizerEntity;
import com.events.service.PickEventOrganizerService;
import com.events.web.bean.EventScheduleBean;
import com.events.web.bean.EventsBean;
import com.events.web.bean.EventsGuideBean;
import com.events.web.bean.EventsOrganizerBean;
import com.events.web.bean.EventsReservationBean;

@Controller
@SessionAttributes(value = { "userName", "hotel", "userId", "hotelDetails",
		"currentDate" })
public class PickEventOrganizerController {

	private Logger logger = Logger.getLogger(PickEventOrganizerController.class
			.getName());

	@Autowired
	private PickEventOrganizerService evntOrganizerService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/pickEventOrganizer.htm", method = RequestMethod.GET)
	public String intiPickEventtOranizer(Model model,
			HttpServletRequest request, HttpSession session) {
		logger.info("PickEventtOranizerController  :  start");
		String eventOrgName = request.getParameter("name");
		List<EventsOrganizerDTO> evntOrganizationInfo = evntOrganizerService
				.getEventOrganizationDetails(eventOrgName);
		EventsOrganizerDTO evntOrgnizer = new EventsOrganizerDTO();
		for (EventsOrganizerDTO hotelDetails : evntOrganizationInfo) {
			BeanUtils.copyProperties(hotelDetails, evntOrgnizer);
		}

		EventsOrganizerBean orgnizer = new EventsOrganizerBean();
		BeanUtils.copyProperties(evntOrgnizer, orgnizer);
		session.setAttribute("event", orgnizer);
		UserBean userBean = new UserBean();
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("login", "login");
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("quickReservation", userBean);
		logger.info("PickEventOranizerController  :  end");
		return "event_reservation";
	}

	@RequestMapping(value = "getEventContactDetail.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getContact(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getContact controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");

		json = mapper.writeValueAsString(eventInfomrtn);
		// model.addAttribute("image", hotelDet.get(0).getImage());
		logger.info("getContact controller : end");

		return json;
	}

	@RequestMapping(value = "selectTour.htm", method = RequestMethod.POST)
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
		List<EventsDTO> allTours = evntOrganizerService.getAllTours(orgnizer
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

	@RequestMapping(value = "getGuidesLst.htm", method = RequestMethod.POST)
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
		List<EventsGuideDTO> guideList = evntOrganizerService.getGuideList(
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

	@RequestMapping(value = "/getEventReservedUsers.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getEvntReservedUserList(
			@RequestParam("scheduleId") Integer scheduleId,
			HttpServletRequest request, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		Map<EventScheduleDTO, List<EventsReservationDTO>> map = evntOrganizerService
				.getEventReservedUsersMap(scheduleId);
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

	@RequestMapping(value = "/saveGuestReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String saveGuestResrvtion(@RequestParam("json") String json,
			Model model, HttpSession session, HttpServletRequest request)
			throws JSONException, JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("came to saveguestreservation in controller : ");
		ObjectMapper mapper = new ObjectMapper();
		String json1 = null;
		EventsOrganizerBean organizer = (EventsOrganizerBean) session
				.getAttribute("event");
		EventsOrganizerDTO orgDto = new EventsOrganizerDTO();
		BeanUtils.copyProperties(organizer, orgDto);
		EventScheduleDTO scheduleDto = new EventScheduleDTO();
		String pdfPath = "/receiptBgImg/pdfReceipt.png";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String pdfRealPath = servletContext.getRealPath(pdfPath);
		Map<String, String> map = evntOrganizerService.getImageMap();
		Map<String, String> imgRealPathMap = new LinkedHashMap<String, String>();
		Set<String> set = map.keySet();
		String reelPath = null;
		for (String key : set) {
			String val = map.get(key);
			reelPath = servletContext.getRealPath(val);
			imgRealPathMap.put(key, reelPath);
		}

		String emailTemplate = "/letterTemplate/evntRsrvtnCnfrm.html";

		String realPath = servletContext.getRealPath(emailTemplate);
		UserDTO userDto = (UserDTO) session.getAttribute("servUserId");
		JSONObject jsonObj = new JSONObject(json);
		String userName = jsonObj.getString("userName");
		String phoneNumber = jsonObj.getString("phone");
		String email = jsonObj.getString("email");
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
		Integer diffOfSeats = availSeats - selectedSeats;

		logger.info("organizer in session : " + organizer);
		logger.info("logged in userdto : " + userDto);
		logger.info("userName " + userName + " phoneNumber " + phoneNumber
				+ " email " + email + " selectedSeats " + selectedSeats
				+ " scheduleId " + scheduleId + " notes " + notes);

		EventGuestUserDTO guestDto = new EventGuestUserDTO();
		EventsReservationDTO reservationDto = new EventsReservationDTO();
		guestDto.setUserName(userName);
		guestDto.setEmail(email);
		guestDto.setPhoneNumber(phoneNumber);
		guestDto.setFirstName(userName);
		guestDto.setScheduleId(scheduleId);
		guestDto.setEventOrgId(organizer.getEventOrgId());
		if (userDto != null) {
			guestDto.setServProId(userDto.getUserId());
		}

		Integer guestId = evntOrganizerService.saveReservedGuest(guestDto);

		reservationDto.setNoOfPeople(selectedSeats);
		if (userDto != null) {
			reservationDto.setUserID(userDto.getUserId());
		}
		reservationDto.setEventScheduleId(scheduleId);
		reservationDto.setEventOrgId(organizer.getEventOrgId());
		reservationDto.setNotes(notes);
		reservationDto.setGuestId(guestId);
		
		EventScheduleDTO scheduleDTO = evntOrganizerService.getScheduleById(scheduleId);
		EventsGuideDTO guideDto = evntOrganizerService.getGuideById(scheduleDTO.getGuideId());

		Long refNumber = evntOrganizerService
				.saveEventReservation(reservationDto);

		Integer isUpdate = evntOrganizerService.updateAvailSeats(scheduleId,
				diffOfSeats);

		String subject = "Confirmation of you Tour reservation with Woofre!";

		scheduleDto.setRefNumber(refNumber);
		scheduleDto.setStrtTme(strtTime);
		scheduleDto.setEndTme(endTime);
		scheduleDto.setBookedSeats(selectedSeats);
		scheduleDto.setUserName(userName);
		scheduleDto.setEmail(email);
		scheduleDto.setPhoneNumber(phoneNumber);
		scheduleDto.setEventName(eventName);
		scheduleDto.setGuideName(guideDto.getGuideName());
		
		logger.info("in saveguestreservation pickevent scheduleDtio : "+scheduleDto);

		if (isUpdate > 0) {
			EventReceiptPDFGeneratingThread mailThread = new EventReceiptPDFGeneratingThread(
					request, orgDto, scheduleDto, realPath, subject,
					imgRealPathMap, pdfRealPath, session);
			mailThread.start();
		}
		logger.info("refNumber value before returning : " + refNumber);
		json1 = mapper.writeValueAsString(refNumber);
		return json1;
	}

	@RequestMapping(value = "/loadDataByClickedDt.htm", method = RequestMethod.POST)
	@ResponseBody
	public String loadDataByClckedDt(
			@RequestParam("clickedDt") String stringdate,
			@RequestParam("eventId") Integer eventId,
			@RequestParam("guideId") Integer guideId,
			HttpServletRequest request, HttpSession session)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		UserDTO servDto = (UserDTO) session.getAttribute("servUserId");
		logger.info("clickedDt : " + stringdate + " eventId : " + eventId
				+ " guideId : " + guideId);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(stringdate);
		Map<Date, List<EventScheduleDTO>> map = evntOrganizerService
				.getDataMap(date, eventId, guideId);
		logger.info("dto map in loadDataByclikcedDt controller : " + map);
		Map<String, List<EventScheduleBean>> scheduleBeanMap = new LinkedHashMap<String, List<EventScheduleBean>>();
		EventScheduleBean bean = null;
		Set<Date> datesSet = map.keySet();
		logger.info("datesSet : " + datesSet);
		
		List<Integer> scheduleIds = new ArrayList<Integer>();
		for(Date dateKey : datesSet) {
			List<EventScheduleDTO> dtoList = map.get(dateKey);
			for(EventScheduleDTO dto : dtoList) {
				scheduleIds.add(dto.getScheduleId());
			}
		}
		
		Map<Integer,Boolean[]> isNoteAndPaidMap = new HashMap<Integer, Boolean[]>();
		for(Integer id : scheduleIds) {
			Boolean[] bool = evntOrganizerService.getIsNoteAndIsPaid(id);
			isNoteAndPaidMap.put(id, bool);
		}
		logger.info("isNoteAndPaidMap : "+isNoteAndPaidMap);
		
		for (Date dt : datesSet) {
			SimpleDateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
			String dateString = formt.format(dt);
			List<EventScheduleBean> listBean = new ArrayList<EventScheduleBean>();
			List<EventScheduleDTO> listDto = map.get(dt);
			for (EventScheduleDTO dto : listDto) {
				SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy");
				bean = new EventScheduleBean();
				BeanUtils.copyProperties(dto, bean);
				Integer divCount = evntOrganizerService.getDivCount(
						bean.getEvntStrtTme(), bean.getEvntEndTme());
				Date eventDt = bean.getDate();
				bean.setEvntDt(frmt.format(eventDt));
				bean.setDivCount(divCount);
				Boolean[] boolVal = isNoteAndPaidMap.get(bean.getScheduleId());
				bean.setHaveNote(boolVal[0]);
				bean.setHavePaid(boolVal[1]);
				EventsGuideDTO guideDto = evntOrganizerService.getGuideById(dto
						.getGuideId());
				EventsDTO eventDto = evntOrganizerService.getEventById(dto
						.getEventId());
				if (userDto != null) {
					boolean haveReservation = evntOrganizerService
							.haveReservation(bean.getScheduleId(),
									userDto.getUserId());
					bean.setHaveReservation(haveReservation);
				} else if (servDto != null) {
					boolean haveReservation = evntOrganizerService
							.haveReservation(bean.getScheduleId(),
									servDto.getUserId());
					bean.setHaveReservation(haveReservation);
				}
				bean.setEventName(eventDto.getEventName());
				bean.setGuideName(guideDto.getGuideName());
				listBean.add(bean);
			}
			scheduleBeanMap.put(dateString, listBean);
		}
		logger.info("before returning inside controller loadDatabyclickedDt scheduleBeanMap : "
				+ scheduleBeanMap);
		json = mapper.writeValueAsString(scheduleBeanMap);

		return json;
	}

	@RequestMapping(value = "/addToWhitelist.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addToWhitelist(@RequestParam("reserveId") Integer reserveId,
			@RequestParam("scheduleId") Integer scheduleId,
			HttpServletRequest request, HttpSession session) {
		logger.info("addToWhitelist in controller : start");
		logger.info("reseveId : " + reserveId + "scheduleId" + scheduleId);
		EventsOrganizerBean organizer = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer orgId = organizer.getEventOrgId();
		String status = evntOrganizerService.addToWhiteList(reserveId,
				scheduleId, orgId);
		logger.info("whitelist controller : returning status : " + status);
		logger.info("addToWhitelist in controller : start");
		return status;
	}

	@RequestMapping(value = "/addToBlacklist.htm", method = RequestMethod.POST)
	@ResponseBody
	public String addToBlacklist(@RequestParam("reserveId") Integer reserveId,
			@RequestParam("scheduleId") Integer scheduleId,
			HttpServletRequest request, HttpSession session) {
		logger.info("reseveId : " + reserveId + "scheduleId" + scheduleId);
		EventsOrganizerBean organizer = (EventsOrganizerBean) session
				.getAttribute("event");
		Integer orgId = organizer.getEventOrgId();
		String status = evntOrganizerService.addToBlackList(reserveId,
				scheduleId, orgId);
		logger.info("whitelist controller : returning status : " + status);
		return status;
	}

	@RequestMapping(value = "/setPaid.htm", method = RequestMethod.POST)
	@ResponseBody
	public String setPaid(@RequestParam("reserveId") Integer reserveId,
			HttpServletRequest request, HttpSession session) {
		logger.info("reseveId : " + reserveId);
		String status = evntOrganizerService.setPaid(reserveId);
		return status;
	}

	@RequestMapping(value = "/setArrived.htm", method = RequestMethod.POST)
	@ResponseBody
	public String setArrived(@RequestParam("reserveId") Integer reserveId,
			HttpServletRequest request, HttpSession session) {
		logger.info("reseveId : " + reserveId);
		String status = evntOrganizerService.setArrived(reserveId);
		return status;
	}

	@RequestMapping(value = "/cancelEvntReservation.htm", method = RequestMethod.POST)
	@ResponseBody
	public String cancelEvntReservation(
			@RequestParam("reserveId") Integer reserveId,
			HttpServletRequest request, HttpSession session) {
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		String emailTemplate = "/letterTemplate/evntRsrvtnCancel.html";
		ServletContext servletContext = request.getSession()
				.getServletContext();
		Map<String, String> rootPathImgMap = evntOrganizerService.getImageMap();
		Map<String, String> realPathImgMap = new LinkedHashMap<String, String>();
		Set<String> set = rootPathImgMap.keySet();
		for (String key : set) {
			String val = rootPathImgMap.get(key);
			realPathImgMap.put(key, servletContext.getRealPath(val));
		}
		EventsOrganizerBean orgBean = (EventsOrganizerBean) session
				.getAttribute("event");
		EventsOrganizerDTO orgDto = new EventsOrganizerDTO();
		BeanUtils.copyProperties(orgBean, orgDto);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd MMMM, yyyy - HH:mm");
		EventsReservationDTO reserveDto = evntOrganizerService
				.getReservationById(reserveId);
		EventScheduleDTO scheduleDto = evntOrganizerService
				.getScheduleById(reserveDto.getEventScheduleId());
		EventsDTO eventDto = evntOrganizerService.getEventById(scheduleDto
				.getEventId());
		EventsGuideDTO guideDto = evntOrganizerService.getGuideById(scheduleDto
				.getGuideId());
		scheduleDto.setGuideName(guideDto.getGuideName());
		scheduleDto.setEventName(eventDto.getEventName());
		scheduleDto.setRefNumber(reserveDto.getReferenceNumber());
		scheduleDto.setBookedSeats(reserveDto.getNoOfPeople());
		scheduleDto.setStrtTme(dateFormat.format(scheduleDto.getStartTime()));
		scheduleDto.setEndTme(dateFormat.format(scheduleDto.getEndTime()));
		if (reserveDto.getGuestId() != null) {
			logger.info("insie guest not null");
			EventGuestUserDTO guestDto = evntOrganizerService
					.getEventGuestById(reserveDto.getGuestId());
			logger.info("guestDto is : " + guestDto);
			scheduleDto.setUserName(guestDto.getUserName());
			scheduleDto.setEmail(guestDto.getEmail());
			scheduleDto.setPhoneNumber(guestDto.getPhoneNumber());
		} else {
			logger.info("inside user not null : ");
			UserDTO userDto = userService.getUserById(reserveDto.getUserID());
			logger.info("userDto is : " + userDto);
			scheduleDto.setUserName(userDto.getUserName());
			scheduleDto.setEmail(userDto.getEmail());
			scheduleDto.setPhoneNumber(userDto.getPhoneNumber());
		}
		boolean status = evntOrganizerService.cancelEventReservation(reserveId);
		logger.info("orgDto : " + orgDto);
		logger.info("scheduleDto : " + scheduleDto);
		logger.info("real path of image : "
				+ servletContext.getRealPath(emailTemplate));
		logger.info("imagepath map : " + realPathImgMap);

		if (status) {
			result = "Reservation with reservation ID : " + reserveId
					+ " is cancelled!";
			String subject = "Confirmation of you reservation cancellation!";
			EventCancelReservationMailThread mailThread = new EventCancelReservationMailThread(orgDto, scheduleDto,
						servletContext.getRealPath(emailTemplate), subject,
						realPathImgMap);
			mailThread.start();

		} else {
			result = "Failed to cancel the reservation for the record ID : "
					+ reserveId;
		}
		logger.info("returning result : " + result);

		return result;
	}
	
	@RequestMapping(value = "exportEventPDF.htm", method = RequestMethod.GET)
	public void exportPDF(Model model, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {
		logger.info("exportPDF method : start");
		String pdfPathVal = (String) session.getAttribute("eventPdfPathVal");
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

	@RequestMapping(value = "/returnToServiceEvent.htm", method = RequestMethod.GET)
	public String returnToSrviceHme(Model model, HttpServletRequest request,
			HttpSession session) {
		model.addAttribute("getSPUser", new UserBean());
		return "serviceprovider_event";
	}

	@RequestMapping(value = "/toChngeEvntReservation.htm", method = RequestMethod.GET)
	public String goToEvntChangeReservation(Model model,
			HttpServletRequest request, HttpSession session) {
		UserBean userBean = new UserBean();
		model.addAttribute("adminLogin", userBean);
		model.addAttribute("userLogin", userBean);
		model.addAttribute("registerUser", userBean);
		model.addAttribute("getUser", userBean);
		model.addAttribute("login", "login");
		model.addAttribute("registerAdmin", userBean);
		model.addAttribute("quickReservation", userBean);
		model.addAttribute("getSPUser", new UserBean());
		return "serviceprovider_event_change";
	}
	
	@RequestMapping(value = "/downloadLocationInfoPDF.htm", method = RequestMethod.GET)
	public String downloadPDF(Model model, HttpSession session)
			throws Exception {
		logger.info("downloadPDF : start ");
		EventsOrganizerBean eventInfomrtn = (EventsOrganizerBean) session
				.getAttribute("event");
		List<EventsOrganizerBean> eventInfo=new ArrayList<EventsOrganizerBean>();
		eventInfo.add(eventInfomrtn);

		model.addAttribute("listOfAPIs", eventInfo);
		model.addAttribute("reportType", "Address");
		logger.info("downloadPDF : end ");
		return "locationAddressPDF";
	}

	@ModelAttribute("languages")
	public Map<String, String> languages() {
		Map<String, String> languages = new LinkedHashMap<String, String>();
		languages.put("", "Select Language");
		languages.put("English", "English");
		languages.put("Germany", "Germany");
		languages.put("Deutch", "Deutch");
		languages.put("Other Language", "Other Language");
		return languages;
	}

	@ModelAttribute("title")
	public Map<String, String> title() {
		Map<String, String> title = new LinkedHashMap<String, String>();
		title.put("", "Select Title");
		title.put("Mr", "Mr");
		title.put("Miss", "Miss");
		title.put("Mrs", "Mrs");
		return title;
	}

	@ModelAttribute("countryList")
	public Map<String, String> country() {
		Map<String, String> countryList = new LinkedHashMap<String, String>();
		countryList.put("", "Select Country");
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
		notificationPeriod.put("", "Notification Period");
		notificationPeriod.put("24 Hours", "24 Hours");
		notificationPeriod.put("48 Hours", "48 Hours");
		notificationPeriod.put("72 Hours", "72 Hours");
		return notificationPeriod;
	}

}
