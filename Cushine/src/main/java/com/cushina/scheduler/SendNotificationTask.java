package com.cushina.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.util.MailUtil;
import com.cushina.service.PickHotelService;
import com.cushina.service.UserService;
import com.cushina.web.bean.HotelBean;

@Component
public class SendNotificationTask {

	Logger logger = Logger.getLogger(SendNotificationTask.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PickHotelService hotelService;

	@Scheduled(cron = "59 59 23 * * ?")
	public void sendReminder() throws FileNotFoundException, IOException {
		logger.info("inside sendReminder() : start");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd MMMM, yyyy - HH:mm");
		String rootPath = MyServletContextListener.returnContextPath();
		Map<String, String> map = MyServletContextListener
				.returnMapForImgContext();
		logger.info("map for image context : " + map);
		String realPath = rootPath + "letterTemplate\\remainder.html";
		logger.info("template context path : " + realPath);
		List<BookingHistoryDTO> listDto = userService
				.getReservationsWithSpecifiedDayDifference(3);
		logger.info("sendReminder() : listDto " + listDto.size());
		for (BookingHistoryDTO dto : listDto) {
			logger.info("current reservation Dto in list : " + dto);
			Map<Long, String> catgryMap = hotelService.getCategoryListByMap(dto
					.getHotelID());
			String categryName = catgryMap.get(dto.getCategoryId());
			dto.setCategory(categryName);
			Date checkIn = dto.getCheckInDate();
			Date checkOut = dto.getCheckOutDate();
			dto.setCheckInDt(dateFormat.format(checkIn));
			dto.setCheckOutDt(dateFormat.format(checkOut));
			HotelDTO hotelDto = new HotelDTO();
			List<HotelBean> list = hotelService
					.getHotelDetail(dto.getHotelID());
			for (HotelBean bean : list) {
				BeanUtils.copyProperties(bean, hotelDto);
			}

			if (dto.getGuestUserId() != null) {
				logger.info("came to if");
				GuestUserDTO guestDto = userService.getGuestUserDetailsById(dto
						.getGuestUserId());
				logger.info("guestUserDto : " + guestDto);
				dto.setUserName(guestDto.getUserName());
				dto.setEmail(guestDto.getEmail());
			} else {
				logger.info("came to else");
				UserDTO userDto = userService.getUserById(dto.getUserId());
				logger.info("userDto : " + userDto);
				dto.setUserName(userDto.getUserName());
				dto.setEmail(userDto.getEmail());
			}

			String subject = "This is a due reminder of your reservation !";

			boolean isSent = MailUtil.sendMail(hotelDto, dto, realPath,
					subject, map);

			logger.info("sendReminder(): mail is sent : " + isSent);
			logger.info("inside sendReminder() : end");

		}
	}
}
