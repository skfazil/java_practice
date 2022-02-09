package com.events.common.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cushina.common.util.MailUtil;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsOrganizerDTO;

public class EventChangeMailSendingThread extends Thread{
	
	HttpServletRequest request = null;
	EventsOrganizerDTO orgDto = null;
	List<EventScheduleDTO> scheduleDtoList = null;
	String subject = null;
	String emailTemplateRealPath = null;
	String generatePDF=null;
	Map<String,String> imgRealPathMap = null;
	
	
	public EventChangeMailSendingThread(String generatePDF, String emailTemplateRealPath,
			EventsOrganizerDTO orgDto, List<EventScheduleDTO> scheduleDtoList,
			String subject, Map<String,String> imgRealPathMap) {
		this.generatePDF = generatePDF;
		this.emailTemplateRealPath = emailTemplateRealPath;
		this.orgDto = orgDto;
		this.scheduleDtoList = scheduleDtoList;
		this.subject = subject;
		this.imgRealPathMap = imgRealPathMap;
	}


	@Override
	public void run() {
		
		/*MailUtil util = new MailUtil();
		try {
			util.sendMailWithReceipt(generatePDF, emailTemplateRealPath,
					orgDto, scheduleDto, subject, imgRealPathMap);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		MailUtil util =null;
		for(EventScheduleDTO scheduleDTO:scheduleDtoList) {
			util = new MailUtil();
			try {
				util.sendMailWithReceipt(generatePDF, emailTemplateRealPath,
						orgDto, scheduleDTO, subject, imgRealPathMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	
	}


}
