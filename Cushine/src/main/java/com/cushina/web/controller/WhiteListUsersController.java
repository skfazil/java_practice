package com.cushina.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.service.PickHotelService;
import com.cushina.service.WhiteListUserService;
import com.cushina.web.bean.HotelBean;

@Controller
@SessionAttributes(value = { "userName", "hotel", "userId", "hotelDetails",
		"currentDate" })
public class WhiteListUsersController {

	@Autowired
	private WhiteListUserService userservice;

	@Autowired
	private PickHotelService service;

	private Logger logger = Logger.getLogger(BookingHistoryController.class
			.getClass());

	@RequestMapping(value = "/getWhiteListDet.htm", method = RequestMethod.GET)
	public @ResponseBody
	String getUserWhiteListDemo(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		if(userInfo == null){
			userInfo =  (UserDTO) session.getAttribute("servUserId");
		}
		Integer userId = userInfo.getUserId();
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<HotelBean> hotelbeanList = new ArrayList<HotelBean>();

		List<WhiteListUsersDTO> whitelistdto = userservice
				.getWhiteListStartDate(userId);

		for (WhiteListUsersDTO whiteListUsersDTO : whitelistdto) {
			HotelBean hotelbean = new HotelBean();
			Long hotelID = whiteListUsersDTO.getHotelID();
			Date startDate = whiteListUsersDTO.getStartDate();
			String date = destFrmt.format(startDate);
			HotelDTO hotelDTO = userservice
					.getServiceWhiteListHotelInfo(hotelID);
			BeanUtils.copyProperties(hotelDTO, hotelbean);
			hotelbean.setStrtDate(date);
			hotelbeanList.add(hotelbean);
		}
		json = mapper.writeValueAsString(hotelbeanList);
		model.addAttribute("image", hotelInfomrtn.getImage());
		return json;
	}

	@RequestMapping(value = "getAddressDetails.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getHotelDetail(HttpSession session, Model model)
			throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");

		String json = null;
		json = mapper.writeValueAsString(hotelInfomrtn);
		logger.info("getHotel Details In Reservation controller : end");
		return json;
	}

}
