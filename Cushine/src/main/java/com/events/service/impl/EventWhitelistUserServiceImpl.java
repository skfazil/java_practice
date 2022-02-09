package com.events.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.model.dataaccess.EventWhitelistUserDao;
import com.events.service.EventWhiteListUserService;


@Service
public class EventWhitelistUserServiceImpl implements EventWhiteListUserService{

	@Autowired
	private EventWhitelistUserDao whitelistUserDao;

	@Transactional
	public List<EventWhiteListDTO> getEventWhiteListStartDate(Integer userId) {
	
		return whitelistUserDao.getEventWhiteListStartDate(userId);
	}

	@Transactional
	public EventsOrganizerDTO getEventServiceWhiteListHotelInfo(Integer eventOrgID) {
		
		return whitelistUserDao.getEventServiceWhiteListHotelInfo(eventOrgID);
	}

	@Transactional
	public List<EventWhiteListDTO> getEventWhiteListCategoryUsrs(Integer userId) {
		List<EventWhiteListDTO> whiteListCategoryUsrs = whitelistUserDao
				.getEventWhiteListCategoryUsrs(userId);
		for (EventWhiteListDTO whiteListUsersDTO : whiteListCategoryUsrs) {
			Integer orgId = whiteListUsersDTO.getOrgId();
			EventsOrganizerDTO orgDetails = whitelistUserDao.getHotelDetails(orgId);
			whiteListUsersDTO.setHotelName(orgDetails.getEventOrgName());
			whiteListUsersDTO.setHotelAddress(orgDetails.getEventOrgAddress());
			whiteListUsersDTO.setCity(orgDetails.getEventOrgCity());
			whiteListUsersDTO.setPhoneNumber(String.valueOf(orgDetails
					.getEventOrgPhoneNumber()));
		}
		return whiteListCategoryUsrs;
	
	}

	@Transactional
	public List<EventBlackListDTO> getEventBlackListCategoryUsrs(Integer userId) {
		List<EventBlackListDTO> blackListCategoryUsrs = whitelistUserDao.getEventBlackListCategoryUsrs(userId);
		for (EventBlackListDTO blackListUsersDTO : blackListCategoryUsrs) {
			Integer orgId = blackListUsersDTO.getOrgId();
			EventsOrganizerDTO orgDetails = whitelistUserDao.getHotelDetails(orgId);
			blackListUsersDTO.setHotelName(orgDetails.getEventOrgName());
			blackListUsersDTO.setHotelAddress(orgDetails.getEventOrgAddress());
			blackListUsersDTO.setCity(orgDetails.getEventOrgCity());
			blackListUsersDTO.setPhoneNumber(String.valueOf(orgDetails
					.getEventOrgPhoneNumber()));
		}
		return blackListCategoryUsrs;
	}

	@Transactional
	public List<EventWhiteListDTO> getAllEventWhiteLstUsrs(Integer orgId) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<EventWhiteListDTO> allWhiteLstUsrs = whitelistUserDao
				.getAllWhiteLstUsrs(orgId);

		for (EventWhiteListDTO whiteListUsersDTO : allWhiteLstUsrs) {
			whiteListUsersDTO.setStartDate(format.format(whiteListUsersDTO
					.getAddedDate()));
			Integer userId = whiteListUsersDTO.getUserId();
			Integer guestUserId = whiteListUsersDTO.getGuestId();
			if (userId != null) {
				UserDTO usrDetailsByID = whitelistUserDao.getUsrDetailsByID(userId);
				whiteListUsersDTO.setUserName(usrDetailsByID.getUserName());
				whiteListUsersDTO.setEmail(usrDetailsByID.getEmail());
				whiteListUsersDTO.setPhoneNumber(usrDetailsByID
						.getPhoneNumber());
			} else if (guestUserId != null) {
				EventGuestUserDTO guestUsrDetailsByID = whitelistUserDao
						.getGuestUsrDetailsByID(guestUserId);
				whiteListUsersDTO
						.setUserName(guestUsrDetailsByID.getUserName());
				whiteListUsersDTO.setEmail(guestUsrDetailsByID.getEmail());
				whiteListUsersDTO.setPhoneNumber(guestUsrDetailsByID
						.getPhoneNumber());
			}

		}
		return allWhiteLstUsrs;
	}

	@Transactional
	public EventWhiteListDTO getEventWhiteCustmrRecord(Integer orgId,
			String userName) {
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		EventWhiteListDTO whiteLstCustmr = null;
		try {
			UserDTO userDetail = whitelistUserDao.getUserDetail(userName);
			whiteLstCustmr = whitelistUserDao.getWhiteLstCustmr(
					userDetail.getUserId(), orgId);
			whiteLstCustmr.setUserName(userDetail.getUserName());
			whiteLstCustmr.setEmail(userDetail.getEmail());
			whiteLstCustmr.setPhoneNumber(userDetail.getPhoneNumber());
			whiteLstCustmr.setStartDate(format.format(whiteLstCustmr
					.getAddedDate()));
		} catch (Exception e) {
			
			try {
				
				EventGuestUserDTO guestUserDetail = whitelistUserDao
						.getGuestUserDetail(userName);
				whiteLstCustmr = whitelistUserDao.getWhiteLstGuestCustmr(
						guestUserDetail.getGuestUserId(), orgId);
				whiteLstCustmr.setUserName(guestUserDetail.getUserName());
				whiteLstCustmr.setEmail(guestUserDetail.getEmail());
				whiteLstCustmr.setPhoneNumber(guestUserDetail.getPhoneNumber());
				whiteLstCustmr.setStartDate(format.format(whiteLstCustmr
						.getAddedDate()));
			} catch (Exception ex) {
				
				whiteLstCustmr = null;
			}
		}
		
		return whiteLstCustmr;
	}

	@Transactional
	public boolean isRemoveWhteCustmrFrmLst(Integer userId) {
		
		return whitelistUserDao.isRemoveWhteCustmrFrmLst(userId);
	}
	@Transactional
	public boolean isRemoveWhteGuestCustmrFrmLst(Integer userId) {
		
		return whitelistUserDao.isRemoveWhteGuestCustmrFrmLst( userId);
	}
}
