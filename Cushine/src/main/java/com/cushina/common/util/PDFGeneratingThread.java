package com.cushina.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;
import com.cushina.web.controller.ReservationController;

public class PDFGeneratingThread extends Thread {

	private static Logger logger = Logger.getLogger(ReservationController.class
			.getName());

	HttpServletRequest request = null;
	HotelBean hotelInfomrtn = null;
	BookingHistoryBean historyBean = null;
	String userName = null;
	String emailTemplateRealPath = null;
	String email = null;
	HttpSession session = null;

	public PDFGeneratingThread(HttpServletRequest request,
			HotelBean hotelInfomrtn, BookingHistoryBean historyBean,
			String userName, String emailTemplateRealPath, String email,
			HttpSession session) {
		this.request = request;
		this.hotelInfomrtn = hotelInfomrtn;
		this.historyBean = historyBean;
		this.userName = userName;
		this.emailTemplateRealPath = emailTemplateRealPath;
		this.email = email;
		this.session = session;

	}

	@SuppressWarnings("static-access")
	public void run() {
		logger.info("start ::  PDFGeneratingThread  ");
		String generatePDF = null;
		CushinaUtil util = new CushinaUtil();
		try {
			generatePDF = util.generatePDF(request, hotelInfomrtn, historyBean,
					userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("pdfPathVal", generatePDF);
		historyBean.setPdfPath(generatePDF);
		MailThread mailThread = new MailThread(generatePDF,
				emailTemplateRealPath, hotelInfomrtn, historyBean, userName,
				email);
		mailThread.start();
		logger.info("end ::  PDFGeneratingThread  ");
	}

}
