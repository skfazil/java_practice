package com.events.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.naming.event.EventDirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.UserDTO;
import com.cushina.model.dataaccess.UserDao;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.model.dataaccess.EventBookingDao;
import com.events.model.dataaccess.EventWhitelistUserDao;
import com.events.service.EventBookingService;

@Service
public class EventBookingServiceImpl implements EventBookingService {
	@Autowired
	private EventBookingDao eventBookingDao;
	
	@Autowired
	private EventWhitelistUserDao whitelistUserDao;
	
	@Autowired
	UserDao userDao;

	@Transactional
	public List<EventsReservationDTO> getEventBookingHistoryDetails(
			Integer userId, Integer eventOrgID) {
		return eventBookingDao.getEventBookingHistoryDetails(userId,eventOrgID);
	}

	@Transactional
	public EventScheduleDTO getScheduleInforamtion(Integer eventScheduleId) {
		return eventBookingDao.getEventBookingHistoryDetails(eventScheduleId);
	}

	@Transactional
	public Integer sameEventReservtn(Integer bookingID) {
		return eventBookingDao.sameEventReservtn(bookingID);
	}

	@Transactional
	public Long cancelEventReservation(Integer bookingID) {
		return eventBookingDao.cancelEventReservation(bookingID);
	}

	@Transactional
	public Long deleteEventReservation(Integer bookingID) {
		return eventBookingDao.deleteEventReservation(bookingID);
	}
    /**
     * In event side showing all customer list by using this method
     */
	@Transactional
	public List<UserDTO> getCustomersList(Integer orgId) {
		List<UserDTO> customersList = eventBookingDao.getCustomersList(orgId);
		return customersList;
	}

	@Transactional
	public List<EventsReservationDTO> getCustmrsHistry(Integer orgId) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		List<EventsReservationDTO> custmrsHistory= eventBookingDao.getCustmrsHistry(orgId);
		
		for (EventsReservationDTO eventsReservationDTO : custmrsHistory) {
			
			Integer userId=eventsReservationDTO.getUserID();
			Integer scheduleId=eventsReservationDTO.getEventScheduleId();
			Integer	guestUserId=eventsReservationDTO.getGuestId();
			EventScheduleDTO scheduleddto=eventBookingDao.getEventScheduleDates(scheduleId);
			EventsDTO eventDto = eventBookingDao.getEventName(scheduleddto.getEventId());
		 if (guestUserId != null) {
			EventGuestUserDTO guestUsrDetails = whitelistUserDao
					.getGuestUsrDetailsByID(guestUserId);
			String userName = guestUsrDetails.getUserName();
			String phoneNumber = guestUsrDetails.getPhoneNumber();
			String email = guestUsrDetails.getEmail();
			eventsReservationDTO.setUserName(userName);
			eventsReservationDTO.setPhoneNumber(phoneNumber);
			eventsReservationDTO.setEmail(email);
			eventsReservationDTO.setStartTime(format.format(scheduleddto.getStartTime()));
			eventsReservationDTO.setEndTime(format.format(scheduleddto.getEndTime()));
			eventsReservationDTO.setDuration(scheduleddto.getDuration());
			eventsReservationDTO.setEventName(eventDto.getEventName());
		}else if (userId != null) {

			UserDTO userDetails = userDao.getUsrDetailsByID(userId);
			String userName = userDetails.getUserName();
			String phoneNumber = userDetails.getPhoneNumber();
			String email = userDetails.getEmail();
			eventsReservationDTO.setUserName(userName);
			eventsReservationDTO.setPhoneNumber(phoneNumber);
			eventsReservationDTO.setEmail(email);
			eventsReservationDTO.setStartTime(format.format(scheduleddto.getStartTime()));
			eventsReservationDTO.setEndTime(format.format(scheduleddto.getEndTime()));
			eventsReservationDTO.setDuration(scheduleddto.getDuration());
			eventsReservationDTO.setEventName(eventDto.getEventName());
		}
		
		}
		return custmrsHistory;
	}

	@Transactional
	public List<EventsReservationDTO> getCustomerReservtnHistryRecords(
			Integer orgId, String userName) {
		

		List<EventsReservationDTO> guestUserReservtnHistory = null;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		try {
			Integer userId = null;
			UserDTO userDetail = userDao.getUserDetail(userName);
			userId = userDetail.getUserId();
			guestUserReservtnHistory = eventBookingDao.getCustomerReservtnHistryRecords(userId);
			

			for (EventsReservationDTO eventsReservationDTO : guestUserReservtnHistory) {
				Integer scheduleId=eventsReservationDTO.getEventScheduleId();
				EventScheduleDTO scheduleddto=eventBookingDao.getEventScheduleDates(scheduleId);
				EventsDTO eventDto = eventBookingDao.getEventName(scheduleddto.getEventId());
				
				eventsReservationDTO.setStartTime(format.format(scheduleddto.getStartTime()));
				eventsReservationDTO.setEndTime(format.format(scheduleddto.getEndTime()));
				eventsReservationDTO.setDuration(scheduleddto.getDuration());
				String usrName = userDetail.getUserName();
				String phoneNumber = userDetail.getPhoneNumber();
				String email = userDetail.getEmail();
				eventsReservationDTO.setUserName(usrName);
				eventsReservationDTO.setPhoneNumber(phoneNumber);
				eventsReservationDTO.setEmail(email);
				eventsReservationDTO.setEventName(eventDto.getEventName());
			}
		} catch (Exception e) {
			e.getMessage();
			EventGuestUserDTO guestUserDetail = null;
			try {
				guestUserDetail = eventBookingDao.getGuestUserDetail(userName);
				Integer guestUserId = guestUserDetail.getGuestUserId();
				guestUserReservtnHistory = eventBookingDao.getCustomerReservtnHistryGuestRecords(guestUserId);
				for (EventsReservationDTO eventsReservationDTO : guestUserReservtnHistory) {
					Integer scheduleId=eventsReservationDTO.getEventScheduleId();
					EventScheduleDTO scheduleddto=eventBookingDao.getEventScheduleDates(scheduleId);
					EventsDTO eventDto = eventBookingDao.getEventName(scheduleddto.getEventId());
					eventsReservationDTO.setStartTime(format.format(scheduleddto.getStartTime()));
					eventsReservationDTO.setEndTime(format.format(scheduleddto.getEndTime()));
					eventsReservationDTO.setDuration(scheduleddto.getDuration());
					String usrName = guestUserDetail.getUserName();
					String phoneNumber = guestUserDetail.getPhoneNumber();
					String email = guestUserDetail.getEmail();
					eventsReservationDTO.setUserName(usrName);
					eventsReservationDTO.setPhoneNumber(phoneNumber);
					eventsReservationDTO.setEmail(email);
					eventsReservationDTO.setEventName(eventDto.getEventName());
				}
			} catch (Exception ex) {
				
			}
		}
		
		return guestUserReservtnHistory;
	}

	@Transactional
	public List<EventsDTO> getCategoryList(Integer eventOrgID) {
		
		return eventBookingDao.getCategoryList(eventOrgID);
	}

	@Transactional
	public List<EventsGuideDTO> getRoomsAvailList(Integer eventOrgID, Integer catVal) {
		
		return eventBookingDao.getRoomsAvailList(eventOrgID, catVal);
	}

	@Transactional
	public Boolean addRoomTOWhteLst(Integer eventId, String guideName,
			Integer eventOrgID) {
		
		return eventBookingDao.addRoomTOWhteLst(eventId,guideName,eventOrgID);
	}
	@Transactional
	public List<EventsReservationDTO> getCustomerReservationHistryRecords(
			Integer orgId) {
		List<EventsReservationDTO> guestUserReservtnHistory = null;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		try {
			//Integer userId = null;
			guestUserReservtnHistory = eventBookingDao.getCustomerReservationHistryRecords(orgId);
				for (EventsReservationDTO eventsReservationDTO : guestUserReservtnHistory) {
					Integer scheduleId=eventsReservationDTO.getEventScheduleId();
					EventScheduleDTO scheduleddto=eventBookingDao.getEventScheduleDates(scheduleId);
					eventsReservationDTO.setStartTime(format.format(scheduleddto.getStartTime()));
					eventsReservationDTO.setEndTime(format.format(scheduleddto.getEndTime()));
					eventsReservationDTO.setDuration(scheduleddto.getDuration());
					/*List<UserDTO> userDetail = eventBookingDao.getUserDetail(orgId);
					for (UserDTO userDTO : userDetail) {
					String usrName = userDTO.getUserName();
					String phoneNumber = userDTO.getPhoneNumber();
					String email = userDTO.getEmail();
					eventsReservationDTO.setUserName(usrName);
					eventsReservationDTO.setPhoneNumber(phoneNumber);
					eventsReservationDTO.setEmail(email);
					}*/
				}
			
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return guestUserReservtnHistory;
	}

	@Transactional
	public EventsDTO getEventName(Integer eventId) {
		return eventBookingDao.getEventName(eventId);
	}

	

}
