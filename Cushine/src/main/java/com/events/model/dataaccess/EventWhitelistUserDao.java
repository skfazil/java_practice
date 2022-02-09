package com.events.model.dataaccess;

import java.util.List;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsOrganizerDTO;

public interface EventWhitelistUserDao {

	List<EventWhiteListDTO> getEventWhiteListStartDate(Integer userId);

	EventsOrganizerDTO getEventServiceWhiteListHotelInfo(Integer eventOrgID);

	List<EventWhiteListDTO> getEventWhiteListCategoryUsrs(Integer userId);

	EventsOrganizerDTO getHotelDetails(Integer orgId);

	List<EventBlackListDTO> getEventBlackListCategoryUsrs(Integer userId);

	List<EventWhiteListDTO> getAllWhiteLstUsrs(Integer orgId);

	UserDTO getUsrDetailsByID(Integer userId);

	EventGuestUserDTO getGuestUsrDetailsByID(Integer guestUserId);

	UserDTO getUserDetail(String userName);

	EventWhiteListDTO getWhiteLstCustmr(Integer userId, Integer orgId);

	EventGuestUserDTO getGuestUserDetail(String userName);

	EventWhiteListDTO getWhiteLstGuestCustmr(Integer userId, Integer orgId);

	boolean isRemoveWhteCustmrFrmLst(Integer userId);

	boolean isRemoveWhteGuestCustmrFrmLst(Integer userId);

}
