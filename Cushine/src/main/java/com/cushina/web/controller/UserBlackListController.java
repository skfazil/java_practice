package com.cushina.web.controller;

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
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.service.BlackListUserService;
import com.cushina.service.PickHotelService;
import com.cushina.web.bean.HotelBean;

@Controller
public class UserBlackListController {

	@Autowired
	private BlackListUserService userservice;

	@Autowired
	private PickHotelService service;

	@RequestMapping(value = "/getBlackListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUserBlackListDemo(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		if(userInfo ==  null){
			userInfo = (UserDTO) session.getAttribute("servUserId");
		}
		Integer userId = userInfo.getUserId();
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<HotelBean> hotelBeanList = new ArrayList<HotelBean>();		
		List<BlackListUsersDTO> blacklistdto = userservice.getBlackListStartDate(userId);
		for (BlackListUsersDTO blackListUsersDTO : blacklistdto) {
			HotelBean hotelbean = new HotelBean();
			Long hotelID = blackListUsersDTO.getHotelID();
			Date date = blackListUsersDTO.getStartDate();
			String strtDate = destFrmt.format(date);
			HotelDTO hotelDTO = userservice	.getUserBlackListHotelDetail(hotelID);			
				BeanUtils.copyProperties(hotelDTO, hotelbean);
				hotelbean.setStrtDate(strtDate);
				hotelBeanList.add(hotelbean);			
		}

		json = mapper.writeValueAsString(hotelBeanList);
		model.addAttribute("image", hotelInfomrtn.getImage());
		return json;
	}

}
