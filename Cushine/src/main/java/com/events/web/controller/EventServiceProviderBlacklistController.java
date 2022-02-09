package com.events.web.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.cushina.web.bean.BlackListUsersBean;
import com.events.common.dto.EventBlackListDTO;
import com.events.service.EventBlacklistUserService;
import com.events.web.bean.EventBlackListBean;
import com.events.web.bean.EventsOrganizerBean;


@Controller
public class EventServiceProviderBlacklistController {
	
	@Autowired
	private EventBlacklistUserService blacklistService;
	
	private Logger logger = Logger.getLogger(EventServiceProviderBlacklistController.class.getClass());
	
	@RequestMapping(value = "eventBlackListAdmin.htm", method = RequestMethod.POST)
	@ResponseBody
	public String adminBlackList(HttpSession session, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("adminBlackList cntroller : start");
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<EventBlackListBean> blackUsrsLst = new ArrayList<EventBlackListBean>();
		EventBlackListBean blckUsr = null;

		try {
			List<EventBlackListDTO> allBlackLstUsrs = blacklistService.getAllBlackLstUsrs(orgId);
			for (EventBlackListDTO blackListUsersDTO : allBlackLstUsrs) {
				blckUsr = new EventBlackListBean();
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
	
	
	@RequestMapping(value = "getEventBlackCustmrRecrd.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getBlackCustmrRecrd(
			@RequestParam("userName") String userName, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("getBlackCustmrRecrd controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		BlackListUsersBean blckCusmr = new BlackListUsersBean();
		
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();
         try{
         EventBlackListDTO blackCustmrRecord = blacklistService.getBlackCustmrRecord(orgId, userName);
     	System.out.println("blacklist info"+blackCustmrRecord);
		
			if (blackCustmrRecord == null) {
				String status = "no customer exists in black list";
				blckCusmr.setStatusMsg(status);
				json = mapper.writeValueAsString(blckCusmr);
			}
			BeanUtils.copyProperties(blackCustmrRecord, blckCusmr);
			json = mapper.writeValueAsString(blackCustmrRecord);
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
	
	
	@RequestMapping(value = "exportEventBlckCustmrCSV.htm", method = RequestMethod.GET)
	public void exportElckCustmrCSV(HttpServletRequest request,
			HttpSession session, HttpServletResponse response)
			throws IOException {
		logger.info("exportElckCustmrCSV controller : start");
		String userName = request.getParameter("userName");
		EventBlackListBean blckCusmr = new EventBlackListBean();
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();

		String csvFileName = "blackCustomer.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
		EventBlackListDTO blackCustmrRecord = blacklistService.getBlackCustmrRecord(orgId, userName);
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

	
	@RequestMapping(value = "exportAllEventBlckCustmrCSV.htm", method = RequestMethod.GET)
	public void exportAllElckCustmrCSV(HttpServletRequest request,
			HttpSession session, HttpServletResponse response)
			throws IOException {
		logger.info("exportElckCustmrCSV controller : start");
		String userName = request.getParameter("userName");
		EventBlackListBean blckCusmr = new EventBlackListBean();
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();

		String csvFileName = "blackCustomer.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
		
		// uses the Super CSV API to generate CSV data from the model data
				ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
						CsvPreference.STANDARD_PREFERENCE);

				String[] header = { "userName", "email", "strtDate", "phoneNumber", };

				csvWriter.writeHeader(header);
			EventBlackListBean blckUsr = null;
			List<EventBlackListDTO> allBlackLstUsrs = blacklistService.getAllBlackLstUsrs(orgId);
			for (EventBlackListDTO blackListUsersDTO : allBlackLstUsrs) {
				blckUsr = new EventBlackListBean();
				BeanUtils.copyProperties(blackListUsersDTO, blckUsr);
				csvWriter.write(blckUsr, header);
			}

		csvWriter.close();
		logger.info("exportElckCustmrCSV controller : end");
	}

	
	
	
	

	@RequestMapping(value = "removeEventBlackUser.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeBlackUser(@RequestParam("userId") Integer userId,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("removeBlackUser controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = blacklistService.isRemoveBlackCustmrFrmLst(userId);
		json = mapper.writeValueAsString(isRemove);
		logger.info("removeBlackUser controller : end");
		return json;
	}

	@RequestMapping(value = "removeEventBlackGuestUser.htm", method = RequestMethod.GET)
	@ResponseBody
	public String removeBlackGuestUser(@RequestParam("userId") Integer userId,
			Model model) throws JsonGenerationException, JsonMappingException,
			IOException {
		logger.info("removeBlackGuestUser controller : start");
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		boolean isRemove = blacklistService
				.isRemoveBlackGuestCustmrFrmLst(userId);
		json = mapper.writeValueAsString(isRemove);
		logger.info("removeBlackGuestUser controller : end");
		return json;
	}
}
