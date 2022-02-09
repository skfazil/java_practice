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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.service.PickHotelService;
import com.cushina.service.WhiteListUserService;
import com.cushina.web.bean.BlackListUsersBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.bean.UserBean;
import com.cushina.web.bean.WhiteListUsersBean;

@Controller
public class WhiteListServiceController {

	private Logger logger = Logger.getLogger(WhiteListServiceController.class
			.getClass());

	@Autowired
	private WhiteListUserService whitelistService;

	@Autowired
	private PickHotelService service;

	@RequestMapping(value = "getWhiteListCategoryCustmrs.htm", method = RequestMethod.POST)
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
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		DateFormat dateFrmt = new SimpleDateFormat("dd.MM.yyyy");
		List<WhiteListUsersBean> whteListUsers = new ArrayList<WhiteListUsersBean>();
		List<WhiteListUsersDTO> whiteListCategoryUsrs = whitelistService
				.getWhiteListCategoryUsrs(userId);
		for (WhiteListUsersDTO whiteListUsersDTO : whiteListCategoryUsrs) {
			WhiteListUsersBean whiteListUser = new WhiteListUsersBean();
			BeanUtils.copyProperties(whiteListUsersDTO, whiteListUser);
			Date strtDt = whiteListUsersDTO.getStartDate();
			String startDate = dateFrmt.format(strtDt);
			whiteListUser.setStrtDate(startDate);
			whteListUsers.add(whiteListUser);
		}
		json = mapper.writeValueAsString(whteListUsers);
		logger.info("getWhiteListCategory controller : end");
		return json;
	}

	@RequestMapping(value = "getBlackListCategoryCustmrs.htm", method = RequestMethod.POST)
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
		List<BlackListUsersBean> blckListUsers = new ArrayList<BlackListUsersBean>();
		List<BlackListUsersDTO> blackListCategoryUsrs = whitelistService
				.getBlackListCategoryUsrs(userId);
		for (BlackListUsersDTO blackListUsersDTO : blackListCategoryUsrs) {
			BlackListUsersBean blackListUser = new BlackListUsersBean();
			BeanUtils.copyProperties(blackListUsersDTO, blackListUser);
			Date strtDt = blackListUsersDTO.getStartDate();
			String startDt = dateFrmt.format(strtDt);
			blackListUser.setStrtDate(startDt);
			blckListUsers.add(blackListUser);
		}
		json = mapper.writeValueAsString(blckListUsers);
		logger.info("getBlackListCategoryCustmrs controller : end");
		return json;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getWhiteListserviceDet.htm", method = RequestMethod.GET)
	public @ResponseBody String getUserWhiteListDemo(Model model,
			HttpSession session)

	throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		// Integer userId = userInfo.getUserId();
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<UserBean> hotelbeanList = new ArrayList<UserBean>();

		List<WhiteListUsersDTO> whitelistdto = whitelistService
				.getServiceListStartDate(hotelID);

		for (WhiteListUsersDTO whiteListUsersDTO : whitelistdto) {
			UserBean hotelbean = new UserBean();
			// Long hotelID = whiteListUsersDTO.getHotelID();
			Integer userId = whiteListUsersDTO.getUserId();
			Date startDate = whiteListUsersDTO.getStartDate();
			String date = destFrmt.format(startDate);

			UserDTO userDTO = whitelistService.getServiceListUserInfo(userId);
			// UserDTO userdtoo= whitelistservice.getServiceUserInfo(hotelID);
			String userName = userDTO.getUserName();
			System.out.println("userName:" + userName);
			UserDTO userdto = whitelistService.getServiceUserNameList(userName);
			BeanUtils.copyProperties(userdto, hotelbean);
			hotelbean.setStrtDate(date);
			hotelbeanList.add(hotelbean);

		}

		json = mapper.writeValueAsString(hotelbeanList);
		model.addAttribute("image", hotelInfomrtn.getImage());
		return json;
	}

}
