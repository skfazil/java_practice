package com.events.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.cushina.common.dto.UserDTO;
import com.cushina.web.bean.UserBean;
import com.events.service.EventUserEmailVisibleService;
import com.events.web.bean.EventsOrganizerBean;
import com.events.web.bean.EventsReservationBean;

@Controller
public class EventUserEmailVisibleController {
	
	@Autowired
	private EventUserEmailVisibleService eventEmailVisibleService;
	/**
	 * (email customer list)in eventorg green icon side who are there in this event we are showing all
	 * email list. 
	 */
		
	@RequestMapping(value = "/getEventEmailListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getEmailListDemo(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		EventsOrganizerBean bean =(EventsOrganizerBean) session.getAttribute("event");
		Integer orgID = bean.getEventOrgId();
		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("servUserId");
		 if (userInfo != null){
			userId = userInfo.getUserId();
		}

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<EventsOrganizerBean> eventorgbean = eventEmailVisibleService.getEmailListDetails(orgID, userId);	
		System.out.println("bean det :::"+eventorgbean);
		List<String> eventOrgName=new ArrayList<String>();
		List<EventsOrganizerBean> uniqueList = new ArrayList<EventsOrganizerBean>();
		
		for (EventsOrganizerBean eventInfo : eventorgbean) {
			EventsOrganizerBean eventorgBean3 = new EventsOrganizerBean();
			BeanUtils.copyProperties(eventInfo, eventorgBean3);
			System.out.println("bean det3 :::"+eventorgBean3);
			if(eventOrgName.contains(eventorgBean3.getEventOrgName())){
				continue;
			}
			eventOrgName.add(eventorgBean3.getEventOrgName());
			uniqueList.add(eventorgBean3);		
		}
		json = mapper.writeValueAsString(uniqueList);
		return json;
	}
	
	@RequestMapping(value = "/getEventUserEmailListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUserEmailListDemo(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		EventsOrganizerBean bean =(EventsOrganizerBean) session.getAttribute("event");
		Integer orgID = bean.getEventOrgId();
		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("userId");
		if (userInfo != null) {
			userId = userInfo.getUserId();
		} 
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<EventsOrganizerBean> eventorgbean = eventEmailVisibleService.getEmailListDetails(orgID, userId);	
		System.out.println("bean det :::"+eventorgbean);
		List<String> eventOrgName=new ArrayList<String>();
		List<EventsOrganizerBean> uniqueList = new ArrayList<EventsOrganizerBean>();
		
		for (EventsOrganizerBean eventInfo : eventorgbean) {
			EventsOrganizerBean eventorgBean3 = new EventsOrganizerBean();
			BeanUtils.copyProperties(eventInfo, eventorgBean3);
			System.out.println("bean det3 :::"+eventorgBean3);
			if(eventOrgName.contains(eventorgBean3.getEventOrgName())){
				continue;
			}
			eventOrgName.add(eventorgBean3.getEventOrgName());
			uniqueList.add(eventorgBean3);		
		}
		json = mapper.writeValueAsString(uniqueList);
		return json;
	}
	
	@RequestMapping(value = "/getEventOrgUpdateEmail.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUpdateEmail(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("servUserId");
		 if (userInfo != null){
			userId = userInfo.getUserId();
		}
		//Integer userId =1;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsReservationBean bean = new EventsReservationBean();
		eventEmailVisibleService.getEventOrgUpdateEmail(userId);		
		System.out.println("bean :::"+bean);
		json = mapper.writeValueAsString(bean);
		return json;

	}
	
	@RequestMapping(value = "/getEventUserOrgUpdateEmail.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUserUpdateEmail(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		Integer userId = null;
		UserDTO userInfo = null;
		userInfo = (UserDTO) session.getAttribute("userId");
		if (userInfo != null) {
			userId = userInfo.getUserId();
		}
		//Integer userId =1;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		EventsReservationBean bean = new EventsReservationBean();
		eventEmailVisibleService.getEventOrgUpdateEmail(userId);		
		System.out.println("bean :::"+bean);
		json = mapper.writeValueAsString(bean);
		return json;

	}
	/**
	 * (email customer list)in eventorg red icon side who are there in this event we are showing all
	 * email list. 
	 */
	
	@RequestMapping(value = "eventAdminCustmrList.htm", method = RequestMethod.POST)
	@ResponseBody
	public String adminCustmrList(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();
         
		List<UserBean> cstmrLst = new ArrayList<UserBean>();
		UserBean cstmr = null;
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<UserDTO> customersList = eventEmailVisibleService.getCustomersList(orgId);
		
		for (UserDTO userDTO : customersList) {
			cstmr = new UserBean();
			BeanUtils.copyProperties(userDTO, cstmr);
			cstmrLst.add(cstmr);
		}
		
		json = mapper.writeValueAsString(cstmrLst);
		return json;
	}
	
	
	@RequestMapping(value = "getEventCustomerInfo.htm", method = RequestMethod.POST)
	@ResponseBody
	public String getCustomerInfo(
			@RequestParam("custmrName") String custmrName, Model model)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userDetail = null;
		UserBean custmrInfo = null;
		try {
			userDetail = eventEmailVisibleService.getUserDetail(custmrName);
			custmrInfo = new UserBean();
			BeanUtils.copyProperties(userDetail, custmrInfo);
			json = mapper.writeValueAsString(custmrInfo);
		} catch (Exception e) {
			custmrInfo = new UserBean();
			custmrInfo.setStatus("no record with this user name");
			json = mapper.writeValueAsString(custmrInfo);
		}
		
		return json;
	}
	
	@RequestMapping(value = "exportEventCustmrEmailCSV.htm", method = RequestMethod.GET)
	public void exportCustmrEmailCSV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		

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
			userDetail = eventEmailVisibleService.getUserDetail(custmrName);
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

		
	}
	
	@RequestMapping(value = "exportAllEventCustmrEmailCSV.htm", method = RequestMethod.GET)
	public void exportAllCustmrEmailCSV(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		
		EventsOrganizerBean bean =(EventsOrganizerBean)session.getAttribute("event");
		System.out.println("bean id:::"+bean.getEventOrgId());
         Integer orgId=bean.getEventOrgId();
		//String custmrName = (String) request.getParameter("un");
		//UserDTO userDetail = null;
		UserBean cstmr = null;

		String csvFileName = "CustomersEmailList.csv";
		response.setContentType("text/csv");
		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				csvFileName);
		response.setHeader(headerKey, headerValue);
		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "firstName", "lastName", "email", "userName",
				"streetName", "city", "country", "postalCode", "phoneNumber" };

		csvWriter.writeHeader(header);
		
		try {
			List<UserDTO> customersList = eventEmailVisibleService.getCustomersList(orgId);
			
			for (UserDTO userDTO : customersList) {
				cstmr = new UserBean();
				BeanUtils.copyProperties(userDTO, cstmr);
				//cstmrLst.add(cstmr);
				csvWriter.write(cstmr, header);
			}
			
		} catch (Exception e) {
			cstmr = new UserBean();
			cstmr.setStatus("no record with this user name");
		}

	
		csvWriter.close();

		
	}


}
