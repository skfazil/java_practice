package com.events.service;

import java.util.List;

import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsOrganizerDTO;

public interface EventWhiteListUserService {

	List<EventWhiteListDTO> getEventWhiteListStartDate(Integer userId);

	EventsOrganizerDTO getEventServiceWhiteListHotelInfo(Integer eventOrgID);

	List<EventWhiteListDTO> getEventWhiteListCategoryUsrs(Integer userId);

	List<EventBlackListDTO> getEventBlackListCategoryUsrs(Integer userId);

	List<EventWhiteListDTO> getAllEventWhiteLstUsrs(Integer orgId);

	EventWhiteListDTO getEventWhiteCustmrRecord(Integer orgId, String userName);

	boolean isRemoveWhteCustmrFrmLst(Integer userId);

	boolean isRemoveWhteGuestCustmrFrmLst(Integer userId);

}
