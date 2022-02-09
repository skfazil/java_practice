package com.events.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.UserDTO;
import com.cushina.common.util.CushinaUtil;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.model.dataaccess.EventUserByObjectServiceDao;
import com.events.model.dataaccess.ServiceEventByGuideDao;
import com.events.service.EventUserByObjectService;

@Service
public class EventUserByObjectServiceImpl implements EventUserByObjectService {

	Logger logger = Logger.getLogger(EventUserByObjectServiceImpl.class);

	@Autowired
	public EventUserByObjectServiceDao eventUserByObject;

	
	
	@Transactional
	public List<EventScheduleDTO> getEventsDatesList(Integer eventId) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<EventScheduleDTO> eventsDatesList = eventUserByObject
				.getEventsDatesList(eventId);
		for (EventScheduleDTO eventScheduleDTO : eventsDatesList) {
			Date date = eventScheduleDTO.getDate();
			String evntDt = format.format(date);
			eventScheduleDTO.setEvntDt(evntDt);
		}
		return eventsDatesList;
	}

	@Transactional
	public EventScheduleDTO getStrtTmeAndEndTme(Integer eventId, String currDt) {
		logger.info("getStrtTmeAndEndTme serviceImpl : start");
		EventScheduleDTO evntStrtAndEndTme = new EventScheduleDTO();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat srcFrmt = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hoursFrmt = new SimpleDateFormat("HH:mm");
		String currentDt = null;
		try {
			Date parsedDt = format.parse(currDt);
			currentDt = srcFrmt.format(parsedDt);
		} catch (ParseException e) {
			logger.info("came to catch block while parsing date");
			e.printStackTrace();
		}

		List<EventScheduleDTO> strtTmeAndEndTme = eventUserByObject
				.getStrtTmeAndEndTme(eventId, currentDt);
		evntStrtAndEndTme.setStartTime(strtTmeAndEndTme.get(0).getStartTime());
		evntStrtAndEndTme.setDate(strtTmeAndEndTme.get(0).getDate());
		evntStrtAndEndTme.setEvntDt(format.format(strtTmeAndEndTme.get(0)
				.getDate()));
		evntStrtAndEndTme.setEndTime(strtTmeAndEndTme.get(
				strtTmeAndEndTme.size() - 1).getEndTime());
		evntStrtAndEndTme.setEvntStrtTme(hoursFrmt.format(evntStrtAndEndTme
				.getStartTime()));
		evntStrtAndEndTme.setEvntEndTme(hoursFrmt.format(evntStrtAndEndTme
				.getEndTime()));
		logger.info("getStrtTmeAndEndTme serviceImpl : end");
		return evntStrtAndEndTme;
	}

	@Transactional
	public List<EventsDTO> getAllTours(Integer eventId) {
		return eventUserByObject.getAllTours(eventId);
	}

	@Transactional
	public List<EventsGuideDTO> getGuideList(Integer tourId, Integer eventOrgId) {
		return eventUserByObject.getGuideList(tourId, eventOrgId);
	}

	@Transactional
	public List<EventScheduleDTO> getCarouselDatesList(Integer eventId) {
		List<EventScheduleDTO> scheduleInfo = new ArrayList<EventScheduleDTO>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dtFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EventScheduleDTO> carouselDatesList = eventUserByObject
				.getCarouselDatesList(eventId);
		for (EventScheduleDTO eventScheduleDTO : carouselDatesList) {
			Date date = eventScheduleDTO.getDate();
			String srcDt = format.format(date);
			eventScheduleDTO.setEvntDt(srcDt);
			List<EventScheduleDTO> eventSchedulesBsdOnDt = eventUserByObject
					.getEventSchedulesBsdOnDt(date, eventId, eventId);
			for (EventScheduleDTO eventSchedulesDTO : eventSchedulesBsdOnDt) {
				Date startTime = eventSchedulesDTO.getStartTime();
				String strtTme = dtFrmt.format(startTime);
				eventSchedulesDTO.setEvntStrtTme(strtTme);
				Date endTime = eventSchedulesDTO.getEndTime();
				String endTme = dtFrmt.format(endTime);
				eventSchedulesDTO.setEvntEndTme(endTme);
			}
			eventScheduleDTO.setEventScheduleDTOs(eventSchedulesBsdOnDt);
			scheduleInfo.add(eventScheduleDTO);
		}
		logger.info("scheduleInfo --> "+scheduleInfo);
		return scheduleInfo;
	}
	
	@Transactional
	public Map<Date,List<EventScheduleDTO>> getDataByEventId(Integer eventId) {		
		return eventUserByObject.getDataByEventId(eventId);
	}
	
	@Transactional
	public Map<EventScheduleDTO,List<EventsReservationDTO>> getEventReservedUsersMap(Integer scheduleId) {
		return eventUserByObject.getEventReservedUsersMap(scheduleId);
	}
	
	public Integer getDivCount(String t1,String t2) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date d1 = null;
		try {
			d1 = formatter.parse(t1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2 = null;
		try {
			d2 = formatter.parse(t2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long diffInMillies = d2.getTime() - d1.getTime();
		Long min = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
		Integer divCount = (int) (min/30) ;
		return divCount;
	}

	@Transactional
	public Integer saveReservedGuest(EventGuestUserDTO guestDto) {
		
		return eventUserByObject.saveReservedGuest(guestDto);
	}

	@Transactional
	public Long saveEventReservation(EventsReservationDTO reservationDto) {
		Date reservedDt = new Date();
		reservationDto.setNoOfCancellations(0);
		//reservationDto.setEmailShare("N");
		reservationDto.setArrived(false);
		reservationDto.setPaid(false);
		reservationDto.setReservedDate(reservedDt);
		reservationDto.setStatus("Active");
		Long refNumber = (long) CushinaUtil.generateRandomReferenceNumber();
		reservationDto.setReferenceNumber(refNumber);
		
		return eventUserByObject.saveEventReservation(reservationDto);
	}

	@Transactional
	public Integer updateAvailSeats(Integer scheduleId, Integer diffOfSeats) {
		
		return eventUserByObject.updateAvailSeats(scheduleId,diffOfSeats);
	}

	@Transactional
	public Map<Date, List<EventScheduleDTO>> getDataMap(Date date,Integer eventId, UserDTO userDto) {
		return eventUserByObject.getDataMap(date,eventId, userDto);
	}

	@Transactional
	public String addToWhiteList(Integer reserveId,Integer scheduleId,Integer orgId) {
		String status = null;
		boolean isAdded = false;
		EventsReservationDTO reserveDto = eventUserByObject.getReservationById(reserveId);
		boolean isBlack = false;
		if(reserveDto.getGuestId() != null) {
			logger.info("coming to reserveDto.getGuestId() != null");
			isBlack =  eventUserByObject.isBlackListGuest(reserveDto.getGuestId());
		}
		else {
			logger.info("coming to else");
			isBlack = eventUserByObject.isBlackListUser(reserveDto.getUserID());
		}
		
		if(isBlack) {
			logger.info("coming to if(isBlack) ");
			status = "Cannot add blacklist user to whitelist!";
			logger.info("status : "+status);
		}else {
			logger.info("coming to else");
			EventWhiteListDTO whiteDto = new EventWhiteListDTO();
			whiteDto.setAddedDate(new Date());
			whiteDto.setOrgId(orgId);
			whiteDto.setScheduleId(scheduleId);
			if(reserveDto.getGuestId()!= null) {
				whiteDto.setGuestId(reserveDto.getGuestId());
				whiteDto.setUserId(reserveDto.getUserID());
			}else {
				whiteDto.setUserId(reserveDto.getUserID());
			}
			isAdded = eventUserByObject.addToWhiteList(whiteDto);
			if(isAdded) {
				status = "User is added to whitelist!";
				logger.info("status : "+status);
			}else {
				status = "Failed to add user to whitelist!";
				logger.info("status : "+status);
			}
		}		
		return status;
	}

	@Transactional
	public String addToBlackList(Integer reserveId, Integer scheduleId,
			Integer orgId) {
		String status = null;
		boolean isAdded = false;
		EventsReservationDTO reserveDto = eventUserByObject.getReservationById(reserveId);
		boolean isWhite = false;
		if(reserveDto.getGuestId() != null) {
			isWhite =  eventUserByObject.isWhiteListGuest(reserveDto.getGuestId());
		}
		else {
			isWhite = eventUserByObject.isWhiteListUser(reserveDto.getUserID());
		}
		
		if(isWhite) {
			status = "Cannot add whitelist user to blacklist!";
		}else {
			EventBlackListDTO blackDto = new EventBlackListDTO();
			blackDto.setAddedDate(new Date());
			blackDto.setOrgId(orgId);
			blackDto.setScheduleId(scheduleId);
			if(reserveDto.getGuestId()!= null) {
				blackDto.setGuestId(reserveDto.getGuestId());
				blackDto.setUserId(reserveDto.getUserID());
			}else {
				blackDto.setUserId(reserveDto.getUserID());
			}
			isAdded = eventUserByObject.addToBlackList(blackDto);
			if(isAdded) {
				status = "User is added to blacklist!";
			}else {
				status = "Failed to add user to blacklist!";
			}
		}		
		return status;
	}

	@Transactional
	public String setPaid(Integer reserveId) {
		String status = null;
		boolean isMarked = false;
		EventsReservationDTO reserveDto = eventUserByObject.getReservationById(reserveId);
		if(reserveDto.isPaid()) {
			status = "User is already marked as paid!";
		}else {
			reserveDto.setPaid(true);
			isMarked = eventUserByObject.setAsPaid(reserveDto);
			if(isMarked) {
				status = "User is marked as paid!";
			}else {
				status = "Failed to mark user as paid!";
			}
		}
		
		return status;
	}

	@Transactional
	public String setArrived(Integer reserveId) {
		String status = null;
		boolean isMarked = false;
		EventsReservationDTO reserveDto = eventUserByObject.getReservationById(reserveId);
		if(reserveDto.isArrived()) {
			status = "User is already marked as Arrived!";
		}else {
			reserveDto.setArrived(true);
			isMarked = eventUserByObject.setAsArrived(reserveDto);
			if(isMarked) {
				status = "User is marked as Arrived!";
			}else {
				status = "Failed to mark user as Arrived!";
			}
		}
		
		return status;
	}
	@Transactional
	public String getEventName(Integer eventId) {
		
		return eventUserByObject.getEventName(eventId);
	}

}
