package com.cushina.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;


public class MailThread extends Thread {
	
	private static Logger logger = Logger.getLogger(MailThread.class
			.getName());
	
	HttpServletRequest request = null;
	HotelBean hotelInfomrtn = null;
	BookingHistoryBean historyBean = null;
	String userName = null;
	String emailTemplateRealPath = null;
	String email = null;
	String generatePDF = null;

	public MailThread(String generatePDF, String emailTemplateRealPath,
			HotelBean hotelInfomrtn, BookingHistoryBean historyBean,
			String userName, String email) {
		this.generatePDF = generatePDF;
		this.emailTemplateRealPath = emailTemplateRealPath;
		this.hotelInfomrtn = hotelInfomrtn;
		this.historyBean = historyBean;
		this.email = email;
		this.userName = userName;

	}

	
	@SuppressWarnings("static-access")
	public void run() {
		logger.info("start ::  MailThread  ");

		MailUtil util = new MailUtil();
		try {
			util.sendMailWithReceipt(generatePDF, emailTemplateRealPath,
					hotelInfomrtn, historyBean, userName, email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("end ::  MailThread  ");
	}

}
