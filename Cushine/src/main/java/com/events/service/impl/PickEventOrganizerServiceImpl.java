package com.events.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.model.dataaccess.PickEventOrganizerDao;
import com.events.model.pojo.EventBlackListEntity;
import com.events.service.PickEventOrganizerService;
import com.events.web.bean.EventsOrganizerBean;
import com.events.web.controller.PickEventOrganizerController;
import com.massage.common.dto.MassagePersonDTO;
import com.massage.common.dto.MassageReservationDTO;
import com.massage.common.dto.MassageServiceScheduleDTO;

@Service
public class PickEventOrganizerServiceImpl implements PickEventOrganizerService {

	private Logger logger = Logger.getLogger(PickEventOrganizerController.class
			.getName());

	@Autowired
	private PickEventOrganizerDao pickEventOrganizerDao;

	@Transactional
	public List<EventsOrganizerDTO> getEventOrganizationDetails(
			String eventOrgName) {
		return pickEventOrganizerDao.getEventOrganizationDetails(eventOrgName);
	}

	@Transactional
	public List<EventsOrganizerBean> getEventOrganizationDetailsById(
			Integer eventOrgId) {
		return pickEventOrganizerDao
				.getEventOrganizationDetailsById(eventOrgId);
	}

	@Transactional
	public List<EventScheduleDTO> getEventsDatesList(Integer eventId) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<EventScheduleDTO> eventsDatesList = pickEventOrganizerDao
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

		List<EventScheduleDTO> strtTmeAndEndTme = pickEventOrganizerDao
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
		return pickEventOrganizerDao.getAllTours(eventId);
	}

	@Transactional
	public List<EventsGuideDTO> getGuideList(Integer tourId, Integer eventOrgId) {
		return pickEventOrganizerDao.getGuideList(tourId, eventOrgId);
	}

	@Transactional
	public List<EventScheduleDTO> getCarouselDatesList(Integer eventId) {
		List<EventScheduleDTO> scheduleInfo = new ArrayList<EventScheduleDTO>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat dtFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<EventScheduleDTO> carouselDatesList = pickEventOrganizerDao
				.getCarouselDatesList(eventId);
		for (EventScheduleDTO eventScheduleDTO : carouselDatesList) {
			Date date = eventScheduleDTO.getDate();
			String srcDt = format.format(date);
			eventScheduleDTO.setEvntDt(srcDt);
			List<EventScheduleDTO> eventSchedulesBsdOnDt = pickEventOrganizerDao
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
		logger.info("scheduleInfo --> " + scheduleInfo);
		return scheduleInfo;
	}

	@Transactional
	public Map<Date, List<EventScheduleDTO>> getDataByEventId(Integer eventId) {
		return pickEventOrganizerDao.getDataByEventId(eventId);
	}

	@Transactional
	public Map<EventScheduleDTO, List<EventsReservationDTO>> getEventReservedUsersMap(
			Integer scheduleId) {
		return pickEventOrganizerDao.getEventReservedUsersMap(scheduleId);
	}

	public Integer getDivCount(String t1, String t2) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date d1 = null;
		try {
			d1 = formatter.parse(t1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d2 = null;
		try {
			d2 = formatter.parse(t2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long diffInMillies = d2.getTime() - d1.getTime();
		Long min = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
		Integer divCount = (int) (min / 30);
		return divCount;
	}

	@Transactional
	public Integer saveReservedGuest(EventGuestUserDTO guestDto) {

		return pickEventOrganizerDao.saveReservedGuest(guestDto);
	}

	@Transactional
	public Long saveEventReservation(EventsReservationDTO reservationDto) {
		Date reservedDt = new Date();
		reservationDto.setNoOfCancellations(0);
		if (reservationDto.getEmailShare() == null) {
			reservationDto.setEmailShare("N");
		}
		reservationDto.setArrived(false);
		reservationDto.setPaid(false);
		reservationDto.setReservedDate(reservedDt);
		reservationDto.setStatus("active");
		Long refNumber = (long) CushinaUtil.generateRandomReferenceNumber();
		reservationDto.setReferenceNumber(refNumber);

		return pickEventOrganizerDao.saveEventReservation(reservationDto);
	}

	@Transactional
	public Integer updateAvailSeats(Integer scheduleId, Integer diffOfSeats) {

		return pickEventOrganizerDao.updateAvailSeats(scheduleId, diffOfSeats);
	}

	@Transactional
	public Map<Date, List<EventScheduleDTO>> getDataMap(Date date,
			Integer eventId, Integer guideId) {
		return pickEventOrganizerDao.getDataMap(date, eventId, guideId);
	}

	@Transactional
	public String addToWhiteList(Integer reserveId, Integer scheduleId,
			Integer orgId) {
		String status = null;
		boolean isAdded = false;
		EventsReservationDTO reserveDto = pickEventOrganizerDao
				.getReservationById(reserveId);
		boolean isBlack = false;
		if (reserveDto.getGuestId() != null) {
			logger.info("coming to reserveDto.getGuestId() != null");
			isBlack = pickEventOrganizerDao.isBlackListGuest(reserveDto
					.getGuestId());
		} else {
			logger.info("coming to else");
			isBlack = pickEventOrganizerDao.isBlackListUser(reserveDto
					.getUserID());
		}

		if (isBlack) {
			logger.info("coming to if(isBlack) ");
			status = "Customer has already been added to blacklist!";
			logger.info("status : " + status);
		} else {
			logger.info("coming to else");
			EventWhiteListDTO whiteDto = new EventWhiteListDTO();
			whiteDto.setAddedDate(new Date());
			whiteDto.setOrgId(orgId);
			whiteDto.setScheduleId(scheduleId);
			if (reserveDto.getGuestId() != null) {
				boolean isGuestExist = pickEventOrganizerDao
						.isGuestExistInWhiteList(reserveDto.getGuestId());
				if (isGuestExist) {
					status = "Customer has already been added to  whitelist!";
				} else {
					whiteDto.setGuestId(reserveDto.getGuestId());
					//whiteDto.setUserId(reserveDto.getUserID());
					isAdded = pickEventOrganizerDao.addToWhiteList(whiteDto);
					if (isAdded) {
						status = "User is added to whitelist!";
						logger.info("status : " + status);
					} else {
						status = "Failed to add user to whitelist!";
						logger.info("status : " + status);
					}
				}

			} else {
				boolean isUserExist = pickEventOrganizerDao
						.isUserExistInWhiteList(reserveDto.getUserID());
				if (isUserExist) {
					status = "Customer has already been added to  whitelist!";
				} else {
					whiteDto.setUserId(reserveDto.getUserID());
					isAdded = pickEventOrganizerDao.addToWhiteList(whiteDto);
					if (isAdded) {
						status = "User is added to whitelist!";
						logger.info("status : " + status);
					} else {
						status = "Failed to add user to whitelist!";
						logger.info("status : " + status);
					}
				}

			}
		}
		return status;
	}

	@Transactional
	public String addToBlackList(Integer reserveId, Integer scheduleId,
			Integer orgId) {
		String status = null;
		boolean isAdded = false;
		EventsReservationDTO reserveDto = pickEventOrganizerDao
				.getReservationById(reserveId);
		boolean isWhite = false;
		if (reserveDto.getGuestId() != null) {
			isWhite = pickEventOrganizerDao.isWhiteListGuest(reserveDto
					.getGuestId());
		} else {
			isWhite = pickEventOrganizerDao.isWhiteListUser(reserveDto
					.getUserID());
		}

		if (isWhite) {
			status = "Customer has already been added to whitelist!";
		} else {
			EventBlackListDTO blackDto = new EventBlackListDTO();
			blackDto.setAddedDate(new Date());
			blackDto.setOrgId(orgId);
			blackDto.setScheduleId(scheduleId);
			if (reserveDto.getGuestId() != null) {
				boolean isGuestExist = pickEventOrganizerDao
						.isGuestExistInBlackList(reserveDto.getGuestId());
				if (isGuestExist) {
					status = "Customer has already been added to blacklist!";
				} else {
					blackDto.setGuestId(reserveDto.getGuestId());
					//blackDto.setUserId(reserveDto.getUserID());
					isAdded = pickEventOrganizerDao.addToBlackList(blackDto);
					if (isAdded) {
						status = "User is added to blacklist!";
					} else {
						status = "Failed to add user to blacklist!";
					}
				}

			} else {
				boolean isUserExist = pickEventOrganizerDao
						.isUserExistInBlackList(reserveDto.getUserID());
				if (isUserExist) {
					status = "Customer has already been added to blacklist!";
				} else {
					blackDto.setUserId(reserveDto.getUserID());
					isAdded = pickEventOrganizerDao.addToBlackList(blackDto);
					if (isAdded) {
						status = "User is added to blacklist!";
					} else {
						status = "Failed to add user to blacklist!";
					}
				}

			}

		}
		return status;
	}

	@Transactional
	public String setPaid(Integer reserveId) {
		String status = null;
		boolean isMarked = false;
		EventsReservationDTO reserveDto = pickEventOrganizerDao
				.getReservationById(reserveId);
		if (reserveDto.isPaid()) {
			status = "User is already marked as paid!";
		} else {
			reserveDto.setPaid(true);
			isMarked = pickEventOrganizerDao.setAsPaid(reserveDto);
			if (isMarked) {
				status = "User is marked as paid!";
			} else {
				status = "Failed to mark user as paid!";
			}
		}

		return status;
	}

	@Transactional
	public String setArrived(Integer reserveId) {
		String status = null;
		boolean isMarked = false;
		EventsReservationDTO reserveDto = pickEventOrganizerDao
				.getReservationById(reserveId);
		if (reserveDto.isArrived()) {
			status = "User is already marked as Arrived!";
		} else {
			reserveDto.setArrived(true);
			isMarked = pickEventOrganizerDao.setAsArrived(reserveDto);
			if (isMarked) {
				status = "User is marked as Arrived!";
			} else {
				status = "Failed to mark user as Arrived!";
			}
		}

		return status;
	}

	@Transactional
	public EventsReservationDTO getReservationById(Integer reserveId) {
		return pickEventOrganizerDao.getReservationById(reserveId);
	}

	@Transactional
	public EventsGuideDTO getGuideById(Integer guideId) {
		return pickEventOrganizerDao.getGuideById(guideId);
	}

	@Transactional
	public EventsDTO getEventById(Integer eventId) {
		return pickEventOrganizerDao.getEventById(eventId);
	}

	@Transactional
	public List<Date> getPrimaryDates(Date frmtDate) {
		return pickEventOrganizerDao.getPrimaryDates(frmtDate);
	}

	@Transactional
	public Map<String, List<EventScheduleDTO>> getEvntWidgetData(Date date) {
		return pickEventOrganizerDao.getEvntWidgetData(date);
	}

	@Transactional
	public boolean haveReservation(Integer scheduleId, Integer userId) {
		return pickEventOrganizerDao.haveReservation(scheduleId, userId);
	}

	@Transactional
	public Map<EventScheduleDTO, List<EventsReservationDTO>> getBlueReservedUsersMap(
			Integer scheduleId, Integer userId) {

		return pickEventOrganizerDao
				.getBlueReservedUsersMap(scheduleId, userId);
	}

	public Map<String, String> getImageMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("clock", "/images/clock.png");
		map.put("flag", "/images/flag.png");
		map.put("hash", "/images/hash.png");
		map.put("mailuser", "/images/mailuser.png");
		map.put("woofre", "/images/woofre.png");

		return map;

	}

	@Transactional
	public List<EventsGuideDTO> getEventGuidesByIds(List<Integer> guideIds) {
		return pickEventOrganizerDao.getEventGuidesByIds(guideIds);
	}

	@Transactional
	public boolean cancelEventReservation(Integer reserveId) {
		return pickEventOrganizerDao.cancelEventReservation(reserveId);
	}

	@Transactional
	public EventScheduleDTO getScheduleById(Integer eventScheduleId) {
		return pickEventOrganizerDao.getScheduleById(eventScheduleId);
	}

	@Transactional
	public EventGuestUserDTO getEventGuestById(Integer guestId) {
		return pickEventOrganizerDao.getEventGuestById(guestId);
	}

	@Transactional
	public Boolean[] getIsNoteAndIsPaid(Integer id) {
		List<EventsReservationDTO> list = pickEventOrganizerDao.getReservedListByScheduleId(id);
		boolean haveNote = false;
		boolean havePaid = false;
		Boolean[] bool = new Boolean[2];
		for(EventsReservationDTO dto : list) {
			if(dto.getNotes() != null) {
				haveNote = true;
			}
		}
		bool[0] = haveNote;
		for(EventsReservationDTO dto : list) {
			if(dto.isPaid() != false) {
				havePaid = true;
			}
		}
		bool[1] = havePaid;
		
		return bool;
	}

	public Map<EventsReservationDTO, Integer> getMapOfReservationsWithDurationWithinFourDays() {
		logger.info("inside serviceimpl : getMapOfReservationsWithDurationWithinFourDays");
		List<EventsReservationDTO> reservationList = pickEventOrganizerDao.getReservationsWithinFourDays();
		logger.info("in serviceimpl : reservationlist : "+reservationList);
		Map<EventsReservationDTO,Integer> reservationDurationMap = new HashMap<EventsReservationDTO, Integer>();		
		
		for(EventsReservationDTO dto : reservationList) {
			Integer duration = 24;
			String notificationDuration = dto.getNotificationPeriod();
			if(notificationDuration != null){
				notificationDuration = notificationDuration.substring(0,2);
				duration = Integer.valueOf(notificationDuration);
			}
			reservationDurationMap.put(dto, duration);
		}
		logger.info("inside serviceimpl : before returning reservationDurationMap  :"+reservationDurationMap);
		return reservationDurationMap;
	}

	@Transactional
	public List<EventsReservationDTO> getReservedUserRecords(Integer scheduleId) {
		return  pickEventOrganizerDao.getReservedUserRecords(scheduleId);
	}

	@Transactional
	public EventsReservationDTO reserveSeatsForUser(
			EventsReservationDTO eventsReservationDTO, Integer scheduledId,
			Integer eventOrgtnId, Integer evntId) {
		Long newReferenceNum = (long) CushinaUtil.generateRandomReferenceNumber();
				
		//eventsReservationDTO.setReferenceNumber(Long.valueOf(newReferenceNum));
		eventsReservationDTO.setReferenceNumber(newReferenceNum);
		return pickEventOrganizerDao.reserveSeatsForUser(
				eventsReservationDTO, scheduledId, eventOrgtnId, evntId);
	}

	@Transactional
	public List<EventsReservationDTO> updateUsersReservtnStatus(
			Integer scheduleId) {
		return pickEventOrganizerDao.updateUsersReservtnStatus(scheduleId);
	}

	@Transactional
	public boolean isUpdateEventScheduleRecord(Integer scheduleId) {
		return pickEventOrganizerDao.isUpdateEventScheduleRecord(scheduleId);
	}

	@Transactional
	public UserDTO getUserDetails(Integer userID) {
		return pickEventOrganizerDao.getUserDetails(userID);
	}
	

	
}
