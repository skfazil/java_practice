package com.events.common.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cushina.common.util.CushinaUtil;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsOrganizerDTO;

public class EventReceiptPDFGeneratingThread extends Thread {

	private static Logger logger = Logger
			.getLogger(EventReceiptPDFGeneratingThread.class.getName());

	HttpServletRequest request = null;
	EventsOrganizerDTO orgDto = null;
	EventScheduleDTO scheduleDto = null;
	String emailTemplateRealPath = null;
	String subject = null;
	Map<String, String> imgRealPathMap = null;
	String pdfRealPath = null;
	HttpSession session = null;

	public EventReceiptPDFGeneratingThread(HttpServletRequest request,
			EventsOrganizerDTO orgDto, EventScheduleDTO scheduleDto,
			String emailTemplateRealPath, String subject,
			Map<String, String> imgRealPathMap, String pdfRealPath, HttpSession session) {
		this.request = request;
		this.orgDto = orgDto;
		this.scheduleDto = scheduleDto;
		this.emailTemplateRealPath = emailTemplateRealPath;
		this.subject = subject;
		this.imgRealPathMap = imgRealPathMap;
		this.pdfRealPath = pdfRealPath;
		this.session = session;

	}

	@SuppressWarnings("static-access")
	public void run() {
		logger.info("start ::  PDFGeneratingThread  ");
		String generatePDF = null;
		CushinaUtil util = new CushinaUtil();
		try {
			generatePDF = util.generatePDF(request, orgDto, scheduleDto, pdfRealPath);
			logger.info("The generated absolute path of generatedPDF : "+generatePDF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("eventPdfPathVal", generatePDF);
		scheduleDto.setPdfPath(generatePDF);
		EventMailSendingThread mailThread = new EventMailSendingThread(
				generatePDF, emailTemplateRealPath, orgDto, scheduleDto,
				subject, imgRealPathMap);
		mailThread.start();
		logger.info("end ::  PDFGeneratingThread  ");
	}
}
