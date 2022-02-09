package com.events.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cushina.common.util.MailUtil;
import com.cushina.scheduler.MyServletContextListener;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.service.PickEventOrganizerService;
import com.events.web.bean.EventsOrganizerBean;

@Component
public class SendEventReservationReminderJob {

	Logger logger = Logger.getLogger(SendEventReservationReminderJob.class);

	@Autowired
	private PickEventOrganizerService pickEventService;

	@Scheduled(cron = "59 59 23 * * ?")
	public void sendEventReservationReminder() throws FileNotFoundException,
			IOException, ParseException {
		logger.info("cron is running :: sendEventReservationReminder");
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd MMMM, yyyy - HH:mm");
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currDt = new Date();
		String strDt = dtFormat.format(currDt);
		Date currentDt = dtFormat.parse(strDt);
		logger.info("currentDt after formatted : " + currentDt);
		String rootPath = MyServletContextListener.returnContextPath();
		Map<String, String> map = MyServletContextListener
				.returnMapForImgContext();
		logger.info("eventreservationreminder: imagePathMap  : " + map);
		String realPath = rootPath
				+ "letterTemplate\\evntReservationReminder.html";
		logger.info("eventreservationreminder realPath : " + realPath);
		Map<EventsReservationDTO, Integer> reservationWithDurationMap = pickEventService
				.getMapOfReservationsWithDurationWithinFourDays();
		logger.info("evntresrvtnreminder reservationWithDurationMap : "
				+ reservationWithDurationMap);
		Map<Integer, EventsOrganizerDTO> orgDtoMap = new HashMap<Integer, EventsOrganizerDTO>();
		Set<EventsReservationDTO> set = reservationWithDurationMap.keySet();
		EventsOrganizerDTO orgDto = null;
		String subject = "This is a reminder of your reservation!!!";
		for (EventsReservationDTO dto : set) {
			List<EventsOrganizerBean> listOrg = pickEventService
					.getEventOrganizationDetailsById(dto.getEventOrgId());
			EventsOrganizerBean orgBean = listOrg.get(0);
			BeanUtils.copyProperties(orgBean, orgDto);
			orgDtoMap.put(dto.getEventOrgId(), orgDto);
		}
		logger.info("orgDtoMap value : " + orgDtoMap);

		logger.info("sendEventReservationReminder : reservationDurationMap : "
				+ reservationWithDurationMap);
		for (Map.Entry<EventsReservationDTO, Integer> reserveMap : reservationWithDurationMap
				.entrySet()) {
			EventsReservationDTO reservationDto = reserveMap.getKey();
			Date startTime = reservationDto.getStartTme();
			Date endTime = reservationDto.getEndTme();
			Date eventDate = reservationDto.getEventDate();
			reservationDto.setStartTime(dateFormat.format(startTime));
			reservationDto.setEndTime(dateFormat.format(endTime));
			Integer notificationDuration = reserveMap.getValue();
			EventsOrganizerDTO organizerDto = orgDtoMap.get(reservationDto
					.getEventOrgId());

			long diff = eventDate.getTime() - currentDt.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			long notificationVal = notificationDuration / 24;

			if (diffDays == notificationVal) {
				boolean isSent = MailUtil.sendMail(organizerDto,
						reservationDto, realPath, subject, map);
			}
		}
	}
}
