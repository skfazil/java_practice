package com.events.service;

import java.util.List;

import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventsOrganizerDTO;

public interface EventBlacklistUserService {

	List<EventBlackListDTO> getEventBlackListStartDate(Integer userId);

	EventsOrganizerDTO getEventUserBlackListHotelDetail(Integer orgID);

	List<EventBlackListDTO> getAllBlackLstUsrs(Integer orgId);

	EventBlackListDTO getBlackCustmrRecord(Integer orgId, String userName);

	boolean isRemoveBlackCustmrFrmLst(Integer userId);

	boolean isRemoveBlackGuestCustmrFrmLst(Integer userId);

}
