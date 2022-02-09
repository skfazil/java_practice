package com.events.model.dataaccess;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsReservationDTO;

public interface ServiceEventByGuideDao {

	
	public List<EventScheduleDTO> getEventsDatesList(Integer eventId);

	public List<EventScheduleDTO> getCarouselDatesList(Integer eventId);

	public List<EventScheduleDTO> getStrtTmeAndEndTme(Integer eventId,
			String currDt);

	public List<EventsDTO> getAllTours(Integer eventId);

	public List<EventsGuideDTO> getGuideList(Integer tourId, Integer eventOrgId);

	public List<EventScheduleDTO> getEventSchedulesBsdOnDt(Date date,
			Integer eventOrgId, Integer eventId);
	
	public Map<Date,List<EventScheduleDTO>> getDataByEventId(Integer eventId);
	
	public Map<EventScheduleDTO,List<EventsReservationDTO>> getEventReservedUsersMap(Integer scheduleId);

	public Integer saveReservedGuest(EventGuestUserDTO guestDto);

	public Long saveEventReservation(EventsReservationDTO reservationDto);

	public Integer updateAvailSeats(Integer scheduleId, Integer diffOfSeats);

	public Map<Date, List<EventScheduleDTO>> getDataMap(Date date, Integer eventId,
			Integer guideId);

	public EventsReservationDTO getReservationById(Integer reserveId);

	public boolean isBlackListGuest(Integer guestId);

	public boolean isBlackListUser(Integer userID);

	public boolean addToWhiteList(EventWhiteListDTO whiteDto);

	public boolean isWhiteListGuest(Integer guestId);

	public boolean isWhiteListUser(Integer userID);

	public boolean addToBlackList(EventBlackListDTO blackDto);

	public boolean setAsPaid(EventsReservationDTO reserveDto);

	public boolean setAsArrived(EventsReservationDTO reserveDto);

	
}
