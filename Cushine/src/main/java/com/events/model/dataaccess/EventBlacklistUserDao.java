package com.events.model.dataaccess;

import java.util.List;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventsOrganizerDTO;

public interface EventBlacklistUserDao {

	List<EventBlackListDTO> getEventBlackListStartDate(Integer userId);

	EventsOrganizerDTO getEventUserBlackListHotelDetail(Integer orgID);

	List<EventBlackListDTO> getAllBlackLstUsrs(Integer orgId);

	EventBlackListDTO getBlackLstCustmr(Integer userId, Integer orgId);

	EventBlackListDTO getBlackLstGuestCustmr(Integer userId, Integer orgId);

	boolean isRemoveBlackCustmrFrmLst(Integer userId);

	boolean isRemoveBlackGuestCustmrFrmLst(Integer userId);

}
