package com.cushina.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.cushina.common.dto.UserDTO;
import com.cushina.service.BlackListUserService;
import com.cushina.service.EmailVisibleService;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;

@Controller
public class EmailVisibleController {

	private static final Logger LOGGER = Logger
			.getLogger(EmailVisibleController.class);

	@Autowired
	private EmailVisibleService emailVisibleService;

	@Autowired
	private BlackListUserService userservice;

	@RequestMapping(value = "/getEmailListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getEmailListDemo(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		if(userInfo == null){
			userInfo = (UserDTO) session.getAttribute("servUserId");
		}
		Integer userId = userInfo.getUserId();

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<HotelBean> hotelbean = emailVisibleService.getEmailListDetails(
				hotelID, userId);
		List<String> hotelName = new ArrayList<String>();
		List<HotelBean> uniqueList = new ArrayList<HotelBean>();

		for (HotelBean hotelInfo : hotelbean) {
			HotelBean hotelBean3 = new HotelBean();
			BeanUtils.copyProperties(hotelInfo, hotelBean3);
			if (hotelName.contains(hotelBean3.getHotelName())) {
				continue;
			}
			hotelName.add(hotelBean3.getHotelName());
			uniqueList.add(hotelBean3);
		}
		json = mapper.writeValueAsString(uniqueList);
		return json;
	}

	@RequestMapping(value = "/getProviderEmailListDet.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getProviderEmailListDemo(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		HotelBean hotelInfomrtn = (HotelBean) session.getAttribute("hotel");
		Long hotelID = hotelInfomrtn.getHotelID();
		UserDTO userInfo = (UserDTO) session.getAttribute("servUserId");
		Integer userId = userInfo.getUserId();

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		List<HotelBean> hotelbean = emailVisibleService.getEmailListDetails(
				hotelID, userId);
		List<String> hotelName = new ArrayList<String>();
		List<HotelBean> uniqueList = new ArrayList<HotelBean>();

		for (HotelBean hotelInfo : hotelbean) {
			HotelBean hotelBean3 = new HotelBean();
			BeanUtils.copyProperties(hotelInfo, hotelBean3);
			if (hotelName.contains(hotelBean3.getHotelName())) {
				continue;
			}
			hotelName.add(hotelBean3.getHotelName());
			uniqueList.add(hotelBean3);
		}
		Iterator<HotelBean> iterator = uniqueList.iterator();
		while (iterator.hasNext()) {
			HotelBean hotel = iterator.next();
			if(hotel.getHotelID().equals(hotelID)){
				iterator.remove();
			}
		}
		json = mapper.writeValueAsString(uniqueList);
		return json;
	}

	@RequestMapping(value = "/getUpdateEmail.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getUpdateEmail(Model model, HttpSession session)
			throws JsonGenerationException, JsonMappingException, IOException {

		UserDTO userInfo = (UserDTO) session.getAttribute("userId");
		Integer userId = userInfo.getUserId();
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		BookingHistoryBean bean = new BookingHistoryBean();
		emailVisibleService.getUpdateEmail(userId);
		LOGGER.info("bean updated :" + bean);
		json = mapper.writeValueAsString(bean);
		return json;

	}
}
