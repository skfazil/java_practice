package com.events.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsReservationDTO;

public interface EventUserByObjectService {
	
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

	public Map<Date, List<EventScheduleDTO>> getDataMap(Date date, Integer eventId, UserDTO userDto);

	public String addToWhiteList(Integer reserveId,Integer scheduleId,Integer orgId);

	public String addToBlackList(Integer reserveId, Integer scheduleId, Integer orgId);

	public String setPaid(Integer reserveId);

	public String setArrived(Integer reserveId);

	public String getEventName(Integer eventId);


}
