package com.events.common.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cushina.common.util.MailUtil;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsOrganizerDTO;

public class EventMailSendingThread extends Thread {

	private static Logger logger = Logger.getLogger(EventMailSendingThread.class
			.getName());
	
	HttpServletRequest request = null;
	EventsOrganizerDTO orgDto = null;
	EventScheduleDTO scheduleDto = null;
	String subject = null;
	String emailTemplateRealPath = null;
	String generatePDF = null;
	Map<String,String> imgRealPathMap = null;

	public EventMailSendingThread(String generatePDF, String emailTemplateRealPath,
			EventsOrganizerDTO orgDto, EventScheduleDTO scheduleDto,
			String subject, Map<String,String> imgRealPathMap) {
		this.generatePDF = generatePDF;
		this.emailTemplateRealPath = emailTemplateRealPath;
		this.orgDto = orgDto;
		this.scheduleDto = scheduleDto;
		this.subject = subject;
		this.imgRealPathMap = imgRealPathMap;

	}

	
	@SuppressWarnings("static-access")
	public void run() {
		logger.info("start ::  MailThread  ");

		MailUtil util = new MailUtil();
		try {
			util.sendMailWithReceipt(generatePDF, emailTemplateRealPath,
					orgDto, scheduleDto, subject, imgRealPathMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("end ::  MailThread  ");
	}
}
