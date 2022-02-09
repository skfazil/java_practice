package com.events.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.service.EventWhiteListUserService;
import com.events.web.bean.EventsOrganizerBean;


@Controller
public class EventWhitelistUsersController {

	@Autowired
	private EventWhiteListUserService whitelstService;

	@RequestMapping(value = "/getEventWhiteListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getEventUserwhiteListDemo(Model model, HttpSession session)throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userDto = (UserDTO) session.getAttribute("userId");
		Integer userId = userDto.getUserId();
		//Integer userId =1;
		
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<EventsOrganizerBean> orgbeanList = new ArrayList<EventsOrganizerBean>();	
		List<EventWhiteListDTO> whitelistdto = whitelstService.getEventWhiteListStartDate(userId);
		System.out.println("black list::"+whitelistdto);
		for (EventWhiteListDTO whiteListUsersDTO : whitelistdto) {
			EventsOrganizerBean orgbean = new EventsOrganizerBean();
			Integer orgID = whiteListUsersDTO.getOrgId();
			Date date = whiteListUsersDTO.getAddedDate();
			String strtDate = destFrmt.format(date);
			EventsOrganizerDTO orgDTO = whitelstService	.getEventServiceWhiteListHotelInfo(orgID);
			System.out.println("orgDTO::"+orgDTO);
			BeanUtils.copyProperties(orgDTO, orgbean);
				orgbean.setStrtDate(strtDate);
				orgbeanList.add(orgbean);			
		}

		json = mapper.writeValueAsString(orgbeanList);
		return json;
	}
}
