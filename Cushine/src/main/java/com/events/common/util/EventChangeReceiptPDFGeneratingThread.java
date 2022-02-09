package com.events.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cushina.common.util.CushinaUtil;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsOrganizerDTO;

public class EventChangeReceiptPDFGeneratingThread extends Thread{

	
	HttpServletRequest request = null;
	EventsOrganizerDTO orgDto = null;
	List<EventScheduleDTO> scheduleDtoList = null;
	String emailTemplateRealPath = null;
	String subject = null;
	Map<String, String> imgRealPathMap = null;
	String pdfRealPath = null;
	HttpSession session = null;
	//List<String> generatePdfList=null;
	
	
	public EventChangeReceiptPDFGeneratingThread(HttpServletRequest request,
			EventsOrganizerDTO orgDto,List<EventScheduleDTO> scheduleDtoList,String emailTemplateRealPath, String subject,
			Map<String, String> imgRealPathMap, String pdfRealPath, HttpSession session) {
		this.request=request;
		this.orgDto=orgDto;
		this.scheduleDtoList=scheduleDtoList;
		this.subject=subject;
		this.emailTemplateRealPath=emailTemplateRealPath;
		this.imgRealPathMap=imgRealPathMap;
		this.pdfRealPath=pdfRealPath;
		this.session=session;
	}




	@Override
	public void run() {
		String generatePDF = null;
		CushinaUtil util = null;
		List<EventScheduleDTO> eventScheduleDTOList=new ArrayList<EventScheduleDTO>();
		EventScheduleDTO eventScheduleDTO=null;
		for(EventScheduleDTO scheduleDto:scheduleDtoList) {
			eventScheduleDTO=new EventScheduleDTO();
			util = new CushinaUtil();
			try {
				generatePDF = util.generatePDF(request, orgDto, scheduleDto, pdfRealPath);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("eventPdfPathVal", generatePDF);
			scheduleDto.setPdfPath(generatePDF);
			eventScheduleDTO.setPdfPath(scheduleDto.getPdfPath());
			eventScheduleDTO.setScheduleId(scheduleDto.getScheduleId());
			eventScheduleDTO.setEmail(scheduleDto.getEmail());
			eventScheduleDTO.setPhoneNumber(scheduleDto.getPhoneNumber());
			eventScheduleDTO.setUserName(scheduleDto.getUserName());
			eventScheduleDTO.setRefNumber(scheduleDto.getRefNumber());
			eventScheduleDTO.setBookedSeats(scheduleDto.getBookedSeats());
			eventScheduleDTO.setStartTime(scheduleDto.getStartTime());
			eventScheduleDTO.setEndTime(scheduleDto.getEndTime());
			eventScheduleDTO.setStrtTme(scheduleDto.getStrtTme());
			eventScheduleDTO.setEndTme(scheduleDto.getEndTme());
			eventScheduleDTO.setEventName(scheduleDto.getEventName());
			eventScheduleDTO.setGuideName(scheduleDto.getGuideName());
			eventScheduleDTOList.add(eventScheduleDTO);
			
		}
		EventChangeMailSendingThread mailThread=new EventChangeMailSendingThread(generatePDF,emailTemplateRealPath,orgDto,
				eventScheduleDTOList,subject,imgRealPathMap);
		mailThread.start();
		
	}
	
	
	
}
