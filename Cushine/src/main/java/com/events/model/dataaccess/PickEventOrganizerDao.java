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
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.web.bean.EventsOrganizerBean;
import com.massage.common.dto.MassagePersonDTO;
import com.massage.common.dto.MassageReservationDTO;
import com.massage.common.dto.MassageServiceScheduleDTO;

public interface PickEventOrganizerDao {

	List<EventsOrganizerDTO> getEventOrganizationDetails(String eventOrgName);

	List<EventsOrganizerBean> getEventOrganizationDetailsById(Integer eventOrgId);

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
	
	public EventsGuideDTO getGuideById(Integer guideId);
	
	public EventsDTO getEventById(Integer eventId);

	public boolean isBlackListGuest(Integer guestId);

	public boolean isBlackListUser(Integer userID);

	public boolean addToWhiteList(EventWhiteListDTO whiteDto);

	public boolean isWhiteListGuest(Integer guestId);

	public boolean isWhiteListUser(Integer userID);

	public boolean addToBlackList(EventBlackListDTO blackDto);

	public boolean setAsPaid(EventsReservationDTO reserveDto);

	public boolean setAsArrived(EventsReservationDTO reserveDto);
	
	public List<Date> getPrimaryDates(Date frmtDate);

	public Map<String, List<EventScheduleDTO>> getEvntWidgetData(Date date);
	
	public List<EventsReservationDTO> getReservedListByScheduleId(Integer scheduleId);

	public boolean haveReservation(Integer scheduleId, Integer userId);

	public Map<EventScheduleDTO, List<EventsReservationDTO>> getBlueReservedUsersMap(
			Integer scheduleId, Integer userId);

	List<EventsGuideDTO> getEventGuidesByIds(List<Integer> guideIds);

	public boolean cancelEventReservation(Integer reserveId);

	public EventScheduleDTO getScheduleById(Integer eventScheduleId);

	public EventGuestUserDTO getEventGuestById(Integer guestId);

	public boolean isGuestExistInWhiteList(Integer guestId);

	public boolean isUserExistInWhiteList(Integer userID);

	public boolean isGuestExistInBlackList(Integer guestId);

	public boolean isUserExistInBlackList(Integer userID);

	public List<EventsReservationDTO> getReservationsWithinFourDays();

	List<EventsReservationDTO> getReservedUserRecords(Integer scheduleId);

	UserDTO getUserDetails(Integer userID);

	boolean isUpdateEventScheduleRecord(Integer scheduleId);

	List<EventsReservationDTO> updateUsersReservtnStatus(Integer scheduleId);

	EventsReservationDTO reserveSeatsForUser(
			EventsReservationDTO eventsReservationDTO, Integer scheduledId,
			Integer eventOrgtnId, Integer evntId);


}
