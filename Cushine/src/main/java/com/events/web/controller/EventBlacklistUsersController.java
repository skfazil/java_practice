package com.events.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.service.EventBlacklistUserService;
import com.events.web.bean.EventsOrganizerBean;


@Controller
public class EventBlacklistUsersController {

	@Autowired
	private EventBlacklistUserService blacklistService;
	

	@RequestMapping(value = "/getEventBlackListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getEventUserBlackListDemo(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		Integer userId = userInfo.getUserId();
		//Integer userId =1;
		
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<EventsOrganizerBean> orgbeanList = new ArrayList<EventsOrganizerBean>();	
		List<EventBlackListDTO> blacklistdto = blacklistService.getEventBlackListStartDate(userId);
		System.out.println("black list::"+blacklistdto);
		for (EventBlackListDTO blackListUsersDTO : blacklistdto) {
			EventsOrganizerBean orgbean = new EventsOrganizerBean();
			Integer orgID = blackListUsersDTO.getOrgId();
			Date date = blackListUsersDTO.getAddedDate();
			String strtDate = destFrmt.format(date);
			EventsOrganizerDTO orgDTO = blacklistService	.getEventUserBlackListHotelDetail(orgID);
			System.out.println("orgDTO::"+orgDTO);
				BeanUtils.copyProperties(orgDTO, orgbean);
				orgbean.setStrtDate(strtDate);
				orgbeanList.add(orgbean);			
		}

		json = mapper.writeValueAsString(orgbeanList);
		return json;
	}
}
