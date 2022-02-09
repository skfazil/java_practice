package com.events.service;

import java.util.List;
import java.util.Map;

import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;




public interface EventBookingService {

	List<EventsReservationDTO> getEventBookingHistoryDetails(
			Integer userId, Integer eventOrgID);

	EventScheduleDTO getScheduleInforamtion(Integer eventScheduleId);

	Integer sameEventReservtn(Integer bookingID);

	Long cancelEventReservation(Integer bookingID);

	Long deleteEventReservation(Integer bookingID);

	/**
     * In event side showing all customer list by using this method
     */
	List<UserDTO> getCustomersList(Integer orgId);

	List<EventsReservationDTO> getCustmrsHistry(Integer orgId);

	List<EventsReservationDTO> getCustomerReservtnHistryRecords(Integer orgId,
			String userName);

	List<EventsDTO> getCategoryList(Integer eventOrgID);

	List<EventsGuideDTO> getRoomsAvailList(Integer eventOrgID, Integer catVal);

	Boolean addRoomTOWhteLst(Integer eventId, String guideName,
			Integer eventOrgID);

	List<EventsReservationDTO> getCustomerReservationHistryRecords(Integer orgId);

	public EventsDTO getEventName(Integer eventId);


}
