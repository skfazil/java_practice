package com.cushina.web.controller;

import java.io.IOException;
import java.text.DateFormat;
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

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.service.BlackListUserService;
import com.cushina.service.PickHotelService;
import com.cushina.service.WhiteListUserService;
import com.cushina.service.impl.PickHotelServiceImpl;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;

@Controller
public class BlackListUserController {

	private Logger logger = Logger.getLogger(PickHotelServiceImpl.class
			.getName());
	@Autowired
	private BlackListUserService blacklistservice;

	@Autowired
	private WhiteListUserService whitelistservice;

	@Autowired
	private PickHotelService service;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getServiceBlackListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getServiceBlackList(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<UserBean> hotelBeanList = new ArrayList<UserBean>();
		List<BlackListUsersDTO> blacklistdto = blacklistservice
				.getServiceBlackListStartDate(hotelID);
		for (BlackListUsersDTO blackListUsersDTO : blacklistdto) {
			UserBean hotelbean = new UserBean();
			Date date = blackListUsersDTO.getStartDate();
			Integer userId = blackListUsersDTO.getUserId();

			String strtDate = destFrmt.format(date);
			UserDTO userDTO = blacklistservice.getBlackServiceUserInfo(userId);

			UserDTO userdto = whitelistservice.getServiceUserNameList(userDTO
					.getUserName());
			System.out.println("userName:" + userDTO.getUserName());
			BeanUtils.copyProperties(userdto, hotelbean);
			hotelbean.setStrtDate(strtDate);
			hotelBeanList.add(hotelbean);
		}

		json = mapper.writeValueAsString(hotelBeanList);
		model.addAttribute("image", hotelInfomrtn.getImage());
		return json;
	}

	@RequestMapping(value = "isBlackUsr.htm", method = RequestMethod.POST)
	@ResponseBody
	public String isBlackUsr(@RequestParam("roomId") Long roomNo, Model model,
			HttpSession session) throws JsonGenerationException,
			JsonMappingException, IOException {
		logger.info("isBlackUsr controller : start");
		ObjectMapper mapper = new ObjectMapper();
		boolean isBlackRoom = false;
		String json = null;
		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		if(userInfo == null){
			userInfo = (UserDTO) session.getAttribute("servUserId");
		}
		if (userInfo != null)
			isBlackRoom = blacklistservice.isBlackUsr(userInfo.getUserId(),
					roomNo);
		json = mapper.writeValueAsString(isBlackRoom);
		logger.info("isBlackUsr controller : end");
		return json;
	}

}
