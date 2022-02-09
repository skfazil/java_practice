package com.events.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cushina.common.util.MailUtil;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsOrganizerDTO;

public class EventCancelReservationMailThread extends Thread {
	
	private static Logger logger = Logger
			.getLogger(EventCancelReservationMailThread.class.getName());
	
	EventsOrganizerDTO orgDto = null;
	EventScheduleDTO scheduleDto = null;
	String emailTemplateRealPath = null;
	String subject = null;
	Map<String,String> imgRealPathMap = null;
	
	public EventCancelReservationMailThread(EventsOrganizerDTO orgDto,EventScheduleDTO scheduleDto,String emailTemplateRealPath,
			String subject,Map<String,String> imgRealPathMap) {
		this.orgDto = orgDto;
		this.scheduleDto = scheduleDto;
		this.emailTemplateRealPath = emailTemplateRealPath;
		this.subject = subject;
		this.imgRealPathMap = imgRealPathMap;
		
	}
	
	@SuppressWarnings("static-access")
	public void run() {
		
		try {
			boolean isSent = MailUtil.sendMail(orgDto, scheduleDto, emailTemplateRealPath, subject, imgRealPathMap);
			logger.info("cancel reservation mail is sent : "+isSent);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
