package com.cushina.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.model.pojo.EventOrganizerEntity;

public class MailUtil {

	static Logger logger = Logger.getLogger(MailUtil.class);
	
	
	static final String PDF_BG_IMAGE = "pdfReceipt.png";

	public static boolean sendMail(HotelDTO hotelDTO, UserDTO userDTO,
			String letterTemplatePath, String activationLink, String subject)
			throws FileNotFoundException, IOException {
		logger.debug("sendMail in MailUtil : start");
		boolean isMessageSent = false;
		logger.info("letter template path in send mail :" + letterTemplatePath);
		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(userDTO.getEmail()));
			message.setText(activationLink);
			message.setSubject(PropUtil.MAIL_SUBJECT);
			MimeBodyPart bodyPart = new MimeBodyPart();

			String rawHTML = "";
			rawHTML = readTemplate(letterTemplatePath);
			rawHTML = rawHTML.replace("${userName}", userDTO.getUserName());
			rawHTML = rawHTML.replace("${hotelName}", hotelDTO.getHotelName());
			rawHTML = rawHTML.replace("${hotelAddress}",
					hotelDTO.getHotelAddress());
			rawHTML = rawHTML.replace("${phoneNumber}",
					String.valueOf(hotelDTO.getPhoneNumber()));
			rawHTML = rawHTML.replace("${activationLink}", activationLink);

			bodyPart.setContent(rawHTML, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);

			Transport.send(message);
			isMessageSent = true;

		} catch (MessagingException e) {
			logger.info("came to catch block : send mail throws exception");
			isMessageSent = false;
			e.printStackTrace();
		}
		logger.debug("sendMail in MailUtil : start");
		return isMessageSent;
	}
	
	public static boolean sendMail(EventsOrganizerDTO orgDto, EventScheduleDTO scheduleDto,
			String letterTemplatePath,String subject,Map<String,String> map)
			throws FileNotFoundException, IOException {
		logger.debug("sendMail in MailUtil : start");
		boolean isMessageSent = false;
		logger.info("letter template path in send mail :" + letterTemplatePath);
		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});
		

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(scheduleDto.getEmail()));
			logger.info("sending mail to : "+scheduleDto.getEmail());
			message.setSubject(subject);
			
			MimeBodyPart bodyPart = new MimeBodyPart();
			

			String rawHTML = "";
			rawHTML = readTemplate(letterTemplatePath);
			rawHTML = rawHTML.replace("${userName}", scheduleDto.getUserName());
			rawHTML = rawHTML.replace("${phoneNumber}", scheduleDto.getPhoneNumber());
			rawHTML = rawHTML.replace("${orgName}", orgDto.getEventOrgName());
			rawHTML = rawHTML.replace("${orgAddress}",
					orgDto.getEventOrgAddress());
			rawHTML = rawHTML.replace("${orgPhoneNumber}",
					orgDto.getEventOrgPhoneNumber());
			rawHTML = rawHTML.replace("${orgCity}", orgDto.getEventOrgCity());
			rawHTML = rawHTML.replace("${orgPin}", orgDto.getEventOrgPostalCode());
			rawHTML = rawHTML.replace("${startTime}", scheduleDto.getStrtTme());
			rawHTML = rawHTML.replace("${endTime}", scheduleDto.getEndTme());
			rawHTML = rawHTML.replace("${seatsBooked}", String.valueOf(scheduleDto.getBookedSeats()));
			rawHTML = rawHTML.replace("${startTime}", scheduleDto.getStrtTme());
			rawHTML = rawHTML.replace("${endTime}", scheduleDto.getEndTme());
			rawHTML = rawHTML.replace("${refNumber}", String.valueOf(scheduleDto.getRefNumber()));
			rawHTML = rawHTML.replace("${eventName}", scheduleDto.getEventName());

			bodyPart.setContent(rawHTML, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			
			// adds inline image attachments
	        if (map != null && map.size() > 0) {
	            Set<String> setImageID = map.keySet();
	             
	            for (String contentId : setImageID) {
	                MimeBodyPart imagePart = new MimeBodyPart();
	                imagePart.setHeader("Content-ID", "<" + contentId + ">");
	                imagePart.setDisposition(MimeBodyPart.INLINE);
	                 
	                String imageFilePath = map.get(contentId);
	                try {
	                    imagePart.attachFile(imageFilePath);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	 
	                multipart.addBodyPart(imagePart);
	            }
	        }
			
			message.setContent(multipart);

			Transport.send(message);
			isMessageSent = true;

		} catch (MessagingException e) {
			logger.info("came to catch block : send mail throws exception");
			isMessageSent = false;
			e.printStackTrace();
		}
		logger.debug("sendMail in MailUtil : start");
		return isMessageSent;
	}
	
	//For remainder mail.......................
	public static boolean sendMail(HotelDTO hotelDto, BookingHistoryDTO bookingDto,
			String letterTemplatePath,String subject,Map<String,String> map)
			throws FileNotFoundException, IOException {
		logger.debug("sendMail in MailUtil : start");
		boolean isMessageSent = false;
		logger.info("letter template path in send mail :" + letterTemplatePath);
		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});
		

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(bookingDto.getEmail()));
			logger.info("sending mail to : "+bookingDto.getEmail());
			message.setSubject(subject);
			
			MimeBodyPart bodyPart = new MimeBodyPart();
			

			String rawHTML = "";
			rawHTML = readTemplate(letterTemplatePath);
			rawHTML = rawHTML.replace("${hotelName}", hotelDto.getHotelName());
			rawHTML = rawHTML.replace("${hotelAddress}", hotelDto.getHotelAddress());
			rawHTML = rawHTML.replace("${hotelPin}", hotelDto.getPostalCode());
			rawHTML = rawHTML.replace("${hotelCity}",
					hotelDto.getCity());
			rawHTML = rawHTML.replace("${hotelPhoneNumber}",
					String.valueOf(hotelDto.getPhoneNumber()));
			
			rawHTML = rawHTML.replace("${checkInDate}", bookingDto.getCheckInDt());
			rawHTML = rawHTML.replace("${checkOutDate}", bookingDto.getCheckOutDt());
			rawHTML = rawHTML.replace("${roomCategory}", bookingDto.getCategory());
			rawHTML = rawHTML.replace("${roomNumber}", String.valueOf(bookingDto.getRoomId()));
			rawHTML = rawHTML.replace("${numberOfNights}", String.valueOf(bookingDto.getNumberOfDays()));
			rawHTML = rawHTML.replace("${userName}", bookingDto.getUserName());
			rawHTML = rawHTML.replace("${refNumber}", String.valueOf(bookingDto.getReservationNumber()));

			bodyPart.setContent(rawHTML, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			
			// adds inline image attachments
	        if (map != null && map.size() > 0) {
	            Set<String> setImageID = map.keySet();
	             
	            for (String contentId : setImageID) {
	                MimeBodyPart imagePart = new MimeBodyPart();
	                imagePart.setHeader("Content-ID", "<" + contentId + ">");
	                imagePart.setDisposition(MimeBodyPart.INLINE);
	                 
	                String imageFilePath = map.get(contentId);
	                try {
	                    imagePart.attachFile(imageFilePath);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	 
	                multipart.addBodyPart(imagePart);
	            }
	        }
			
			message.setContent(multipart);

			Transport.send(message);
			isMessageSent = true;

		} catch (MessagingException e) {
			logger.info("came to catch block : send mail throws exception");
			isMessageSent = false;
			e.printStackTrace();
		}
		logger.debug("sendMail in MailUtil : start");
		return isMessageSent;
	}
	
	
	//Reminder mail for Events Reservation..................
		public static boolean sendMail(EventsOrganizerDTO orgDto, EventsReservationDTO reservationDto,
				String letterTemplatePath,String subject,Map<String,String> map)
				throws FileNotFoundException, IOException {
			logger.debug("sendMail in MailUtil : start");
			boolean isMessageSent = false;
			logger.info("letter template path in send mail :" + letterTemplatePath);
			Properties props = new Properties();
			props.put("mail.smtp.host", PropUtil.MAIL_HOST);
			props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
			props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
			props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
			props.put("mail.smtp.port", PropUtil.MAIL_PORT);

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
									PropUtil.MAIL_PASSWORD);
						}
					});
			

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(reservationDto.getEmail()));
				logger.info("sending mail to : "+reservationDto.getEmail());
				message.setSubject(subject);
				
				MimeBodyPart bodyPart = new MimeBodyPart();
				

				String rawHTML = "";
				rawHTML = readTemplate(letterTemplatePath);
				rawHTML = rawHTML.replace("${orgName}", orgDto.getEventOrgName());
				rawHTML = rawHTML.replace("${orgAddress}", orgDto.getEventOrgAddress());
				rawHTML = rawHTML.replace("${orgPin}", orgDto.getEventOrgPostalCode());
				rawHTML = rawHTML.replace("${orgCity}",
						orgDto.getEventOrgCity());
				rawHTML = rawHTML.replace("${orgPhoneNumber}",orgDto.getEventOrgPhoneNumber());
				
				rawHTML = rawHTML.replace("${startTime}", reservationDto.getStartTime());
				rawHTML = rawHTML.replace("${endTime}", reservationDto.getEndTime());
				rawHTML = rawHTML.replace("${eventName}", reservationDto.getEventName());
				rawHTML = rawHTML.replace("${guideName}", reservationDto.getGuideName());
				rawHTML = rawHTML.replace("${bookedSeats}", String.valueOf(reservationDto.getNoOfPeople()));
				rawHTML = rawHTML.replace("${userName}", reservationDto.getUserName());
				rawHTML = rawHTML.replace("${refNumber}", String.valueOf(reservationDto.getReferenceNumber()));

				bodyPart.setContent(rawHTML, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(bodyPart);
				
				// adds inline image attachments
		        if (map != null && map.size() > 0) {
		            Set<String> setImageID = map.keySet();
		             
		            for (String contentId : setImageID) {
		                MimeBodyPart imagePart = new MimeBodyPart();
		                imagePart.setHeader("Content-ID", "<" + contentId + ">");
		                imagePart.setDisposition(MimeBodyPart.INLINE);
		                 
		                String imageFilePath = map.get(contentId);
		                try {
		                    imagePart.attachFile(imageFilePath);
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		 
		                multipart.addBodyPart(imagePart);
		            }
		        }
				
				message.setContent(multipart);

				Transport.send(message);
				isMessageSent = true;

			} catch (MessagingException e) {
				logger.info("came to catch block : send mail throws exception");
				isMessageSent = false;
				e.printStackTrace();
			}
			logger.debug("sendMail in MailUtil : start");
			return isMessageSent;
		}
	

	public static boolean sendMail(String toMail, String body, String subject) {
		logger.debug("MailUtil Start: Sending email");
		boolean isMessageSent = false;

		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toMail));
			message.setText(body);
			message.setSubject(subject);
			Transport.send(message);
			isMessageSent = true;

		} catch (MessagingException e) {
			isMessageSent = false;
			e.printStackTrace();
		}
		logger.debug("End: Email is Successfully sent");
		return isMessageSent;
	}

	public static boolean resendMail(UserDTO userDTO,
			String letterTemplatePath, String activationLink, String subject)
			throws FileNotFoundException, IOException {
		logger.debug("MailUtil Start: Sending email");

		boolean isMessageSent = false;
		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);
		logger.info("after setting properties ");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});

		try {
			logger.info("inside try block");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			String email = userDTO.getEmail();
			logger.info("recipients mail " + email);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setText(activationLink);
			message.setSubject(PropUtil.MAIL_SUBJECT);
			MimeBodyPart bodyPart = new MimeBodyPart();

			String rawHTML = "";
			rawHTML = readTemplate(letterTemplatePath);
			rawHTML = rawHTML.replace("${userName}", userDTO.getUserName());

			rawHTML = rawHTML.replace("${activationLink}", activationLink);

			bodyPart.setContent(rawHTML, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);

			Transport.send(message);
			isMessageSent = true;

		} catch (MessagingException e) {
			isMessageSent = false;
			e.printStackTrace();
		}
		logger.debug("End: Email is Successfully sent");
		logger.debug("MailUtil End: Sening email");
		return isMessageSent;
	}

	public static boolean sendMailWithReceipt(String pdfPath,
			String reservationTemplatePath, HotelBean hotelInfomrtn,
			BookingHistoryBean historyBean, String userName, String email)
			throws FileNotFoundException, IOException {
		logger.info("sendMailWithReceipt : start");
		boolean isSuccess = false;
		logger.info("historyBean info in sendmail ###" + historyBean);
		logger.info("letter template path in send mail :"
				+ reservationTemplatePath);
		logger.info("user email :" + email);
		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			// message.setText(activationLink);
			message.setSubject("Thank you, for your reservation with hotel name : "
					+ hotelInfomrtn.getHotelName());
			BodyPart bodyPart = new MimeBodyPart();

			String rawHTML = "";
			rawHTML = readTemplate(reservationTemplatePath);//

			if (historyBean.getOldReservtnNumber() != null) {
				logger.info("oldreservation number is : "
						+ historyBean.getOldReservtnNumber());
				rawHTML = rawHTML.replace("${oldRererenceNumber}",
						String.valueOf(historyBean.getOldReservtnNumber()));
			}

			rawHTML = rawHTML.replace("${referenceNumber}",
					String.valueOf(historyBean.getReservationNumber()));
			rawHTML = rawHTML.replace("${userName}", userName);
			rawHTML = rawHTML.replace("${hotelName}",
					hotelInfomrtn.getHotelName());
			rawHTML = rawHTML.replace("${hotelAddress}",
					hotelInfomrtn.getHotelAddress());// ${checkedIn},chckedInDate
			rawHTML = rawHTML.replace("${checkedIn}",
					historyBean.getChckedInDate());
			rawHTML = rawHTML.replace("${checkOut}",
					historyBean.getChckedOutDate());// ${noOfNights}
			rawHTML = rawHTML.replace("${noOfNights}",
					String.valueOf(historyBean.getNumberOfDays()));

			bodyPart.setContent(rawHTML, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);

			bodyPart = attachFile(pdfPath);
			multipart.addBodyPart(bodyPart);

			message.setContent(multipart);
			Transport.send(message);
			isSuccess = true;

		} catch (MessagingException e) {
			logger.info("came to catch block : send mail with receipt throws exception");
			isSuccess = false;
		}
		logger.info("sendMailWithReceipt : end");
		return isSuccess;
	}

	/**
	 * attach file to mail.
	 * 
	 * @param filename
	 * @return
	 * @throws MessagingException
	 */
	private static BodyPart attachFile(String filename)
			throws MessagingException {
		logger.info(" pdf file path is :" + filename);
		BodyPart bodyPart;
		bodyPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(filename);
		bodyPart.setFileName(fds.getName());
		bodyPart.setDataHandler(new DataHandler(fds));
		return bodyPart;
	}

	private static String readTemplate(String letterTemplatePath)
			throws IOException, FileNotFoundException {
		BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(letterTemplatePath)));
		StringBuilder buffer = new StringBuilder();
		while (input.ready()) {
			buffer.append(input.readLine());
		}
		input.close();
		return buffer.toString();
	}

	public static boolean sendMail(EventsOrganizerDTO eventsOrganizerDTO,
			UserDTO userDTO, String realPath, String confirmationUrl,
			String subject) throws FileNotFoundException, IOException {


		logger.debug("sendMail in MailUtil : start");
		boolean isMessageSent = false;
		logger.info("letter template path in send mail :" + realPath);
		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(userDTO.getEmail()));
			message.setText(confirmationUrl);
			message.setSubject(PropUtil.MAIL_SUBJECT);
			MimeBodyPart bodyPart = new MimeBodyPart();

			String rawHTML = "";
			rawHTML = readTemplate(realPath);
			rawHTML = rawHTML.replace("${userName}", userDTO.getUserName());
			rawHTML = rawHTML.replace("${hotelName}",eventsOrganizerDTO.getEventOrgName());
			rawHTML = rawHTML.replace("${hotelAddress}",
					eventsOrganizerDTO.getEventOrgAddress());
			rawHTML = rawHTML.replace("${phoneNumber}",
					String.valueOf(eventsOrganizerDTO.getEventOrgPhoneNumber()));
			rawHTML = rawHTML.replace("${activationLink}", confirmationUrl);

			bodyPart.setContent(rawHTML, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);

			Transport.send(message);
			isMessageSent = true;

		} catch (MessagingException e) {
			logger.info("came to catch block : send mail throws exception");
			isMessageSent = false;
			e.printStackTrace();
		}
		logger.debug("sendMail in MailUtil : start");
		return isMessageSent;
	
	
	}

	public void sendMailWithReceipt(String pdfPath,
			String emailTemplateRealPath, EventsOrganizerDTO orgDto,
			EventScheduleDTO scheduleDto, String subject,
			Map<String, String> imgRealPathMap) throws FileNotFoundException, IOException {
		logger.debug("sendMail in MailUtil : start");
		boolean isMessageSent = false;
		logger.info("letter template path in send mail :" + emailTemplateRealPath);
		Properties props = new Properties();
		props.put("mail.smtp.host", PropUtil.MAIL_HOST);
		props.put("mail.smtp.socketFactory.port", PropUtil.MAIL_PORT);
		props.put("mail.smtp.socketFactory.class", PropUtil.MAIL_CLASS);
		props.put("mail.smtp.auth", PropUtil.MAIL_AUTH);
		props.put("mail.smtp.port", PropUtil.MAIL_PORT);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(PropUtil.MAIL_EMAIL,
								PropUtil.MAIL_PASSWORD);
					}
				});
		

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(PropUtil.MAIL_EMAIL));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(scheduleDto.getEmail()));
			logger.info("sending mail to : "+scheduleDto.getEmail());
			message.setSubject(subject);
			
			BodyPart bodyPart = new MimeBodyPart();
			

			String rawHTML = "";
			rawHTML = readTemplate(emailTemplateRealPath);
			rawHTML = rawHTML.replace("${userName}", scheduleDto.getUserName());
			rawHTML = rawHTML.replace("${phoneNumber}", scheduleDto.getPhoneNumber());
			rawHTML = rawHTML.replace("${orgName}", orgDto.getEventOrgName());
			rawHTML = rawHTML.replace("${orgAddress}",
					orgDto.getEventOrgAddress());
			rawHTML = rawHTML.replace("${orgPhoneNumber}",
					orgDto.getEventOrgPhoneNumber());
			rawHTML = rawHTML.replace("${orgCity}", orgDto.getEventOrgCity());
			rawHTML = rawHTML.replace("${orgPin}", orgDto.getEventOrgPostalCode());
			rawHTML = rawHTML.replace("${startTime}", scheduleDto.getStrtTme());
			rawHTML = rawHTML.replace("${endTime}", scheduleDto.getEndTme());
			rawHTML = rawHTML.replace("${seatsBooked}", String.valueOf(scheduleDto.getBookedSeats()));
			rawHTML = rawHTML.replace("${startTime}", scheduleDto.getStrtTme());
			rawHTML = rawHTML.replace("${endTime}", scheduleDto.getEndTme());
			rawHTML = rawHTML.replace("${refNumber}", String.valueOf(scheduleDto.getRefNumber()));
			rawHTML = rawHTML.replace("${eventName}", scheduleDto.getEventName());

			bodyPart.setContent(rawHTML, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			
			// adds inline image attachments
	        if (imgRealPathMap != null && imgRealPathMap.size() > 0) {
	            Set<String> setImageID = imgRealPathMap.keySet();
	             
	            for (String contentId : setImageID) {
	                MimeBodyPart imagePart = new MimeBodyPart();
	                imagePart.setHeader("Content-ID", "<" + contentId + ">");
	                imagePart.setDisposition(MimeBodyPart.INLINE);
	                 
	                String imageFilePath = imgRealPathMap.get(contentId);
	                try {
	                    imagePart.attachFile(imageFilePath);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	 
	                multipart.addBodyPart(imagePart);
	            }
	        }
	        
	        bodyPart = attachFile(pdfPath);
			multipart.addBodyPart(bodyPart);
			
			message.setContent(multipart);

			Transport.send(message);
			isMessageSent = true;

		} catch (MessagingException e) {
			logger.info("came to catch block : send mail throws exception");
			isMessageSent = false;
			e.printStackTrace();
		}
		logger.debug("sendMail in MailUtil : start");
		
	}

}
