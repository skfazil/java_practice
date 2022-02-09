package com.cushina.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class CushinaUtil {

	static Logger logger = Logger.getLogger(CushinaUtil.class);
	private static final String PDF_BG_IMAGE = "cushinaReceipt.gif";

	public static String getEnctryptedText(String email) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(email.getBytes("UTF-8"));

			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			result = sb.toString();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

	public static String generateRandomPassword() {
		RandomStringNumber random = new RandomStringNumber(12);
		return random.nextString();
	}

	public static String removeCommaFromString(String valueOf,
			String replacement) {
		if (valueOf != null && valueOf.startsWith(",")) {
			valueOf = valueOf.replaceFirst(",", replacement);
		}
		return valueOf;
	}

	public static Date getCurrentDate(String dateFomat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFomat);
		String currentDate = sdf.format(new Date());
		return getDate(sdf, currentDate);
	}

	/**
	 * getDate
	 * 
	 * @param sdf
	 * @param date
	 * @return
	 */
	public static Date getDate(SimpleDateFormat sdf, String date) {
		Date startDateValue = null;
		try {
			startDateValue = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDateValue;
	}

	/**
	 * getDate
	 * 
	 * @param sdf
	 * @param date
	 * @return
	 */
	public static Date getDate(SimpleDateFormat sdf, Date date) {
		Date startDateValue = null;
		try {
			startDateValue = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDateValue;
	}

	/**
	 * getDate
	 * 
	 * @param sdf
	 * @param date
	 * @return
	 */
	public static Date getDate(String dateFomat, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFomat);
		Date startDateValue = null;
		try {
			startDateValue = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDateValue;
	}

	/**
	 * getDate
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * getDate
	 * 
	 * @param dateFomat
	 * @param date
	 * @return
	 */
	public static Date getDate(String dateFomat, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFomat);
		Date startDateValue = null;
		try {
			startDateValue = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDateValue;
	}

	public static long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public static boolean validateTime(long timeStamp, long currentTimeStamp) {
		long timeDiff = currentTimeStamp - timeStamp;
		return (timeDiff < PropUtil.PASS_RESET_LINK_DDEFAULT_VALID_TIME);
	}

	public static Date formatDate(Date date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date parsed = format.parse("20110210");
		java.sql.Date sql = new java.sql.Date(parsed.getTime());

		return sql;
	}

	public static int generateRandomReferenceNumber() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100000000);
		System.out.println("Generated reference number : " + randomInt);
		return randomInt;
	}

	public static String generatePDF(HttpServletRequest request,
			HotelBean hotelInfomrtn, BookingHistoryBean historyBean,
			String userName) throws DocumentException, MalformedURLException,
			IOException {

		OutputStream file = null;
		String absolutePath = null;

		String relativeWebPathBgImage = "/receiptBgImg" + "/"
				+ MailUtil.PDF_BG_IMAGE;
		logger.info("inside mailutil() relativeWebPathBgImage : "
				+ relativeWebPathBgImage);

		String bgImgAbsPath = request.getSession().getServletContext()
				.getRealPath(relativeWebPathBgImage);
		logger.info("bgImgAbsPath :" + bgImgAbsPath);

		try {
			File tempFile = null;
			tempFile = File.createTempFile("file1", ".pdf");// pdf
			absolutePath = tempFile.getAbsolutePath();
			logger.info("absolute path :" + absolutePath);
			file = new FileOutputStream(tempFile);
			Document doc = new Document();
			PdfWriter.getInstance(doc, file);

			Image img = Image.getInstance(bgImgAbsPath);
			img.scaleAbsolute(550f, 700f);
			System.out.println("image instance is created successfully");
			doc.open();
			doc.add(img);
			doc.close();
			file.close();
			logger.info("pdf is created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		File pdfCertificate = null;
		String absolutePathVal = null;
		try {
			logger.info("pdf created successfully");
			PdfReader pdfReader = new PdfReader(absolutePath);

			pdfCertificate = File.createTempFile("reservation_", ".pdf");// pdf
			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream(pdfCertificate));

			/**
			 * parameters : hotel details, checkIn,checkOut,user Name, reference
			 * number.
			 */
			PdfContentByte context;

			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_BOLDITALIC,
					BaseFont.WINANSI, BaseFont.EMBEDDED);

			BaseFont bf1 = BaseFont.createFont(BaseFont.TIMES_ROMAN,
					BaseFont.WINANSI, BaseFont.EMBEDDED);

			context = pdfStamper.getOverContent(1);
			String hotelAddress = hotelInfomrtn.getHotelAddress();
			String hotelName = hotelInfomrtn.getHotelName();
			String phoneNo = String.valueOf(hotelInfomrtn.getPhoneNumber());
			String checkIn = historyBean.getChckedInDate();
			String checkOut = historyBean.getChckedOutDate();
			String referenceNo = String.valueOf(historyBean
					.getReservationNumber());

			context.beginText();
			context.setFontAndSize(bf, 20);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "RESERVATION",
					80, 630, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 20);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT,
					"Catch your spot", 385, 630, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 16);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "Reference : ",
					80, 600, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 16);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, referenceNo,
					170, 600, 0);
			context.endText();

			// powered by cultime.com
			context.beginText();
			context.setFontAndSize(bf, 16);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT,
					"powered by cultmine.com", 350, 600, 0);
			context.endText();

			// Hotel address
			context.beginText();
			context.setFontAndSize(bf1, 14);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, hotelName + ','
					+ hotelAddress, 80, 560, 0);
			context.endText();

			// room details
			context.beginText();
			context.setFontAndSize(bf, 14);
			context.showTextAligned(
					PdfContentByte.ALIGN_LEFT,
					"Room No :" + historyBean.getRoomId() + " ,"
							+ historyBean.getCategoryName() + "", 100, 540, 0);
			context.endText();

			// hotel contact
			context.beginText();
			context.setFontAndSize(bf, 14);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "Contact  : ",
					100, 520, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 14);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, phoneNo, 170,
					520, 0);
			context.endText();

			// QR Code :
			BarcodeQRCode qrcode = new BarcodeQRCode(referenceNo.trim(), 1, 1,
					null);
			Image qrcodeImage = qrcode.getImage();
			qrcodeImage.scaleAbsolute(85, 85);
			qrcodeImage.setAbsolutePosition(440, 480);
			context.addImage(qrcodeImage);

			// checkIn :
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT,
					"CheckIn     : ", 155, 480, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, checkIn, 220,
					480, 0);
			context.endText();

			// CheckOut
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "CheckOut  : ",
					155, 460, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, checkOut, 220,
					460, 0);
			context.endText();

			// hotel Name
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, hotelName, 155,
					430, 0);
			context.endText();

			// hotel address
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, hotelAddress,
					155, 410, 0);
			context.endText();

			// user Name
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, userName, 155,
					380, 0);
			context.endText();

			// no of Nights.
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT,
					+historyBean.getNumberOfDays() + " Night(s)", 155, 348, 0);
			context.endText();

			// Bar code.
			Barcode128 code128 = new Barcode128();
			code128.setGenerateChecksum(true);
			code128.setCode(referenceNo);
			Image barCodeImg = code128.createImageWithBarcode(context, null,
					null);
			barCodeImg.setAbsolutePosition(90f, 275f);
			barCodeImg.scaleAbsolute(120, 55);
			context.addImage(barCodeImg);

			absolutePathVal = pdfCertificate.getAbsolutePath();
			logger.info("pdf certificate absolute path is :" + absolutePathVal);
			pdfStamper.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return absolutePathVal;
	}

	public static String generatePDF(HttpServletRequest request,
			EventsOrganizerDTO orgDto, EventScheduleDTO scheduleDto, String pdfRealPath)
			throws DocumentException, MalformedURLException, IOException {

		OutputStream file = null;
		String absolutePath = null;

		/*String relativeWebPathBgImage = "/receiptBgImg" + "/"
				+ MailUtil.PDF_BG_IMAGE;
		logger.info("inside mailutil() relativeWebPathBgImage : "
				+ relativeWebPathBgImage);

		String pdfRealPath = request.getSession().getServletContext()
				.getRealPath(relativeWebPathBgImage);
		logger.info("bgImgAbsPath :" + pdfRealPath);*/

		try {
			File tempFile = null;
			tempFile = File.createTempFile("file1", ".pdf");// pdf
			absolutePath = tempFile.getAbsolutePath();
			logger.info("absolute path :" + absolutePath);
			file = new FileOutputStream(tempFile);
			Document doc = new Document();
			PdfWriter.getInstance(doc, file);

			Image img = Image.getInstance(pdfRealPath);
			img.scaleAbsolute(550f, 700f);
			System.out.println("image instance is created successfully");
			doc.open();
			doc.add(img);
			doc.close();
			file.close();
			logger.info("pdf is created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		File pdfCertificate = null;
		String absolutePathVal = null;
		try {
			logger.info("pdf created successfully");
			PdfReader pdfReader = new PdfReader(absolutePath);

			pdfCertificate = File.createTempFile("reservation_", ".pdf");// pdf
			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream(pdfCertificate));

			/**
			 * parameters : hotel details, checkIn,checkOut,user Name, reference
			 * number.
			 */
			PdfContentByte context;

			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_BOLDITALIC,
					BaseFont.WINANSI, BaseFont.EMBEDDED);

			BaseFont bf1 = BaseFont.createFont(BaseFont.TIMES_ROMAN,
					BaseFont.WINANSI, BaseFont.EMBEDDED);

			context = pdfStamper.getOverContent(1);
			String eventOrgAddress = orgDto.getEventOrgAddress();
			String eventOrgName = orgDto.getEventOrgName();
			String eventOrgPhone = orgDto.getEventOrgPhoneNumber();
			String checkIn = scheduleDto.getStrtTme();
			String checkOut = scheduleDto.getEndTme();
			String userName = scheduleDto.getUserName();
			String refNumber = String.valueOf(scheduleDto.getRefNumber());
			String eventName = scheduleDto.getEventName();
			String guideName = scheduleDto.getGuideName();
			String bookedSeats = String.valueOf(scheduleDto.getBookedSeats())
					+ " Seat(s)";

			context.beginText();
			context.setFontAndSize(bf, 20);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "RESERVATION",
					80, 630, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 20);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT,
					"Catch your spot", 385, 630, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 16);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "Reference : ",
					80, 600, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 16);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, refNumber, 170,
					600, 0);
			context.endText();

			// powered by cultime.com
			context.beginText();
			context.setFontAndSize(bf, 16);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT,
					"powered by cultmine.com", 350, 600, 0);
			context.endText();

			// Hotel address
			context.beginText();
			context.setFontAndSize(bf1, 14);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, eventOrgName
					+ ',' + eventOrgAddress, 80, 560, 0);
			context.endText();

			// hotel name
			context.beginText();
			context.setFontAndSize(bf, 14);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, eventName, 100,
					540, 0);
			context.endText();

			// hotel contact
			context.beginText();
			context.setFontAndSize(bf, 14);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "Contact  : ",
					100, 520, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf, 14);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, eventOrgPhone,
					170, 520, 0);
			context.endText();

			// QR Code :
			BarcodeQRCode qrcode = new BarcodeQRCode(refNumber.trim(), 1, 1,
					null);
			Image qrcodeImage = qrcode.getImage();
			qrcodeImage.scaleAbsolute(85, 85);
			qrcodeImage.setAbsolutePosition(440, 480);
			context.addImage(qrcodeImage);

			// checkIn :
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "Start Time : ",
					155, 480, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, checkIn, 220,
					480, 0);
			context.endText();

			// CheckOut
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, "End Time  : ",
					155, 467, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, checkOut, 220,
					467, 0);
			context.endText();

			// Guide Name
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT,
					"Guide        : ", 155, 454, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, guideName, 220,
					454, 0);
			context.endText();

			// platazer Helden
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, eventOrgName,
					155, 430, 0);
			context.endText();

			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, eventOrgAddress,
					155, 410, 0);
			context.endText();

			// user Name
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, userName, 155,
					380, 0);
			context.endText();

			// no of Nights.
			context.beginText();
			context.setFontAndSize(bf1, 12);
			context.showTextAligned(PdfContentByte.ALIGN_LEFT, bookedSeats,
					155, 348, 0);
			context.endText();

			// bar code :
			Barcode128 code128 = new Barcode128();
			code128.setGenerateChecksum(true);
			code128.setCode(refNumber);
			Image bcImage = code128.createImageWithBarcode(context, null, null);
			bcImage.setAbsolutePosition(90f, 275f);
			bcImage.scaleAbsolute(120, 55);
			context.addImage(bcImage);

			absolutePathVal = pdfCertificate.getAbsolutePath();
			logger.info("absolute path in event generate pdf : "+absolutePathVal);
			pdfStamper.close();
			// doc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return absolutePathVal;
	}

}
