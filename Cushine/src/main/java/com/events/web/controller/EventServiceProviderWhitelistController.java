package com.events.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.web.bean.BlackListUsersBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.WhiteListUsersBean;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.service.EventWhiteListUserService;
import com.events.web.bean.EventBlackListBean;
import com.events.web.bean.EventWhiteListBean;
import com.events.web.bean.EventsOrganizerBean;

@Controller
public class EventServiceProviderWhitelistController {
	
	@Autowired
	private EventWhiteListUserService whitelistService;
	
	private Logger logger = Logger.getLogger(EventServiceProviderWhitelistController.class.getClass());
	
	/**
	 * (white list)in green icon side white list customers are showing in this method
	 */
	
	@RequestMapping(value = "getEventWhiteListCategoryCustmrs.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getWhiteListCategory(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getWhiteListCategory controller : start");
		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("userId");
		if (userInfo != null) {
			userId = userInfo.getUserId();
		} else {
			userInfo = (UserDTO) session.getAttribute("servUserId");
			userId = userInfo.getUserId();
		}

		logger.info("userId ::: " + userId);
		//Integer userId = 1;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		DateFormat dateFrmt = new SimpleDateFormat("dd.MM.yyyy");
		List<EventWhiteListBean> whteListUsers = new ArrayList<EventWhiteListBean>();
		List<EventWhiteListDTO> whiteListCategoryUsrs = whitelistService.getEventWhiteListCategoryUsrs(userId);
		System.out.println("white list sp ::::"+whiteListCategoryUsrs);
		for (EventWhiteListDTO whiteListUsersDTO : whiteListCategoryUsrs) {
			EventWhiteListBean whiteListUser = new EventWhiteListBean();
			BeanUtils.copyProperties(whiteListUsersDTO, whiteListUser);
			Date strtDt = whiteListUsersDTO.getAddedDate();
			String startDate = dateFrmt.format(strtDt);
			whiteListUser.setStartDate(startDate);
			whteListUsers.add(whiteListUser);
		}
		json = mapper.writeValueAsString(whteListUsers);
		logger.info("getWhiteListCategory controller : end");
		return json;
	}
	
	/**
	 * (black list)in green icon side black list customers are showing in this method
	 */
	
	@RequestMapping(value = "getEventBlackListCategoryCustmrs.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getBlackListCategoryCustmrs(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("getBlackListCategoryCustmrs controller : start");

		ObjectMapper mapper = new ObjectMapper();
		String json = null;

		DateFormat dateFrmt = new SimpleDateFormat("dd.MM.yyyy");
		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("userId");
		if (userInfo != null) {
			userId = userInfo.getUserId();
		} else {
			userInfo = (UserDTO) session.getAttribute("servUserId");
			userId = userInfo.getUserId();
		}
		//Integer userId =1;
		List<EventBlackListBean> blckListUsers = new ArrayList<EventBlackListBean>();
		List<EventBlackListDTO> blackListCategoryUsrs = whitelistService.getEventBlackListCategoryUsrs(userId);
		System.out.println("black list sp ::::"+blackListCategoryUsrs);
		for (EventBlackListDTO blackListUsersDTO : blackListCategoryUsrs) {
			EventBlackListBean blackListUser = new EventBlackListBean();
			BeanUtils.copyProperties(blackListUsersDTO, blackListUser);
			Date strtDt = blackListUsersDTO.getAddedDate();
			String startDt = dateFrmt.format(strtDt);
			blackListUser.setStrtDate(startDt);
			blckListUsers.add(blackListUser);
		}
		json = mapper.writeValueAsString(blckListUsers);
		logger.info("getBlackListCategoryCustmrs controller : end");
		return json;
	}
	/**
	 * (white list customer)in red icon side whitelist customers are showing in this method
	 */
	
	@RequestMapping(value = "eventWhiteListAdmin.htm", method = RequestMethod.POST)
	@ResponseBody
	public String adminWhiteList(HttpSession session, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("adminWhiteList controller : start");
		/*HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelId = hotelInfomrtn.getHotelID();*/
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();
		List<EventWhiteListBean> whiteUsrsLst = new ArrayList<EventWhiteListBean>();
		EventWhiteListBean whiteUsr = null;

		try {
			List<EventWhiteListDTO> allWhiteLstUsrs = whitelistService.getAllEventWhiteLstUsrs(orgId);
			for (EventWhiteListDTO whiteListUsersDTO : allWhiteLstUsrs) {
				whiteUsr = new EventWhiteListBean();
				BeanUtils.copyProperties(whiteListUsersDTO, whiteUsr);
				whiteUsrsLst.add(whiteUsr);
			}
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
	
	@RequestMapping(value = "getEventWhitCustmrRecrd.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getWhitCustmrRecrd(@RequestParam("userName") String userName,
			Model model, HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getWhitCustmrRecrd controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();
         EventWhiteListBean whteCustmr = new EventWhiteListBean();
		try {
			EventWhiteListDTO whiteCustmrRecord = whitelistService.getEventWhiteCustmrRecord(orgId, userName);
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
	
	
	@RequestMapping(value = "removeEventWhteCustmrFrmLst.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeWhteCustmrFrmLst(
			@RequestParam("userId") Integer userId, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("removeWhteCustmrFrmLst controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = whitelistService.isRemoveWhteCustmrFrmLst(userId);
		logger.info("removeWhteCustmrFrmLst controller : end");
		json = mapper.writeValueAsString(isRemove);
		return json;
	}
	@RequestMapping(value = "removeEventWhteGuestUsrFrmLst.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeWhteGuestUstmrFrmLst(
			@RequestParam("userId") Integer userId, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("removeWhteCustmrFrmLst controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = whitelistService
				.isRemoveWhteGuestCustmrFrmLst(userId);
		logger.info("removeWhteCustmrFrmLst controller : end");
		json = mapper.writeValueAsString(isRemove);
		return json;
	}
	
	@RequestMapping(value = "exportEventWhiteCustmrCSV.htm", method = RequestMethod.GET)
	public void exportWhiteCustmrCSV(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session)
			throws IOException {
		logger.info("exportWhiteCustmrCSV controller : start");
		String userName = request.getParameter("userName");

		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();

		String csvFileName = "whiteCustomer.csv";
		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);

		EventWhiteListDTO whiteCustmrRecord = whitelistService
				.getEventWhiteCustmrRecord(orgId, userName);
		EventWhiteListBean whteCustmr = new EventWhiteListBean();
		BeanUtils.copyProperties(whiteCustmrRecord, whteCustmr);

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "userName", "email", "startDate", "phoneNumber", };

		csvWriter.writeHeader(header);

		csvWriter.write(whteCustmr, header);
		csvWriter.close();

		logger.info("exportWhiteCustmrCSV controller : end");
	}
	
	
	
	@RequestMapping(value = "exportAllEventWhiteCustmrCSV.htm", method = RequestMethod.GET)
	public void exportAllWhiteCustmrCSV(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session)
			throws IOException {
		logger.info("exportWhiteCustmrCSV controller : start");
		//String userName = request.getParameter("userName");

		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();

		String csvFileName = "whiteCustomer.csv";
		response.setContentType("text/csv");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
		
		// uses the Super CSV API to generate CSV data from the model data
				ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
						CsvPreference.STANDARD_PREFERENCE);

				String[] header = { "userName", "email", "startDate", "phoneNumber", };

				csvWriter.writeHeader(header);
		EventWhiteListBean whiteUsr = null;
		List<EventWhiteListDTO> allWhiteLstUsrs = whitelistService.getAllEventWhiteLstUsrs(orgId);
		for (EventWhiteListDTO whiteListUsersDTO : allWhiteLstUsrs) {
			whiteUsr = new EventWhiteListBean();
			BeanUtils.copyProperties(whiteListUsersDTO, whiteUsr);
			//whiteUsrsLst.add(whiteUsr);
			csvWriter.write(whiteUsr, header);
		}

		csvWriter.close();

		logger.info("exportWhiteCustmrCSV controller : end");
	}
}
