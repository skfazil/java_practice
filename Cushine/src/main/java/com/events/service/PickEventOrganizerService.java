package com.events.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.web.bean.EventsOrganizerBean;
import com.massage.common.dto.MassagePersonDTO;
import com.massage.common.dto.MassageServiceScheduleDTO;

public interface PickEventOrganizerService {

	List<EventsOrganizerDTO> getEventOrganizationDetails(String eventOrgName);

	List<EventsOrganizerBean> getEventOrganizationDetailsById(Integer eventOrgId);

	public List<EventScheduleDTO> getEventsDatesList(Integer eventId);
	
	public List<EventScheduleDTO> getCarouselDatesList(Integer eventId);
	
	public EventScheduleDTO getStrtTmeAndEndTme(Integer eventId, String currDt);
	
	public List<EventsDTO> getAllTours(Integer eventId);
	
	public List<EventsGuideDTO> getGuideList(Integer tourId,Integer eventOrgId);

	public Map<Date,List<EventScheduleDTO>> getDataByEventId(Integer eventId);
	
	public Integer getDivCount(String t1,String t2);
	
	public Map<EventScheduleDTO,List<EventsReservationDTO>> getEventReservedUsersMap(Integer scheduleId);
	
	public Integer saveReservedGuest(EventGuestUserDTO guestDto);
	
	public Long saveEventReservation(EventsReservationDTO reservationDto);

	public Integer updateAvailSeats(Integer scheduleId, Integer diffOfSeats);

	public Map<Date, List<EventScheduleDTO>> getDataMap(Date date, Integer eventId,
			Integer guideId);

	public String addToWhiteList(Integer reserveId,Integer scheduleId,Integer orgId);

	public String addToBlackList(Integer reserveId, Integer scheduleId, Integer orgId);

	public String setPaid(Integer reserveId);

	public String setArrived(Integer reserveId);
	
	public EventsReservationDTO getReservationById(Integer reserveId);

	public EventsGuideDTO getGuideById(Integer guideId);

	public EventsDTO getEventById(Integer eventId);
	
	public List<Date> getPrimaryDates(Date frmtDate);

	public Map<String, List<EventScheduleDTO>> getEvntWidgetData(Date date);

	public boolean haveReservation(Integer scheduleId, Integer userId);

	public Map<EventScheduleDTO, List<EventsReservationDTO>> getBlueReservedUsersMap(
			Integer scheduleId, Integer userId);

	public Map<String, String> getImageMap();

	List<EventsGuideDTO> getEventGuidesByIds(List<Integer> guideIds);

	public boolean cancelEventReservation(Integer reserveId);

	public EventScheduleDTO getScheduleById(Integer eventScheduleId);

	public EventGuestUserDTO getEventGuestById(Integer guestId);

	public Boolean[] getIsNoteAndIsPaid(Integer id);

	public Map<EventsReservationDTO, Integer> getMapOfReservationsWithDurationWithinFourDays();

	public List<EventsReservationDTO> getReservedUserRecords(Integer scheduleId);

	public EventsReservationDTO reserveSeatsForUser(
			EventsReservationDTO eventsReservationDTO, Integer scheduledId,
			Integer eventOrgtnId, Integer evntId);

	public UserDTO getUserDetails(Integer userID);

	public List<EventsReservationDTO> updateUsersReservtnStatus(Integer scheduleId);

	public boolean isUpdateEventScheduleRecord(Integer scheduleId);

	
}
