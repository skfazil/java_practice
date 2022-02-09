package com.events.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.model.dataaccess.EventBlacklistUserDao;
import com.events.model.dataaccess.EventWhitelistUserDao;
import com.events.service.EventBlacklistUserService;

@Service
public class EventBlacklistUserServiceImpl implements EventBlacklistUserService{

	@Autowired
	private EventBlacklistUserDao blacklistUserDao;
	
	@Autowired
	private EventWhitelistUserDao whitelistUserDao;

    @Transactional
	public List<EventBlackListDTO> getEventBlackListStartDate(Integer userId) {
	
		return blacklistUserDao.getEventBlackListStartDate(userId);
	}

    @Transactional
	public EventsOrganizerDTO getEventUserBlackListHotelDetail(Integer orgID) {
		
		return blacklistUserDao.getEventUserBlackListHotelDetail(orgID);
	}

    @Transactional
	public List<EventBlackListDTO> getAllBlackLstUsrs(Integer orgId) {
    	
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<EventBlackListDTO> allBlackLstUsrs = blacklistUserDao
				.getAllBlackLstUsrs(orgId);
		for (EventBlackListDTO blackListUsersDTO : allBlackLstUsrs) {
			blackListUsersDTO.setStrtDate(format.format(blackListUsersDTO
					.getAddedDate()));
			Integer userId = blackListUsersDTO.getUserId();
			Integer guestUserId = blackListUsersDTO.getGuestId();
			if (userId != null) {
				UserDTO usrDetailsByID = whitelistUserDao.getUsrDetailsByID(userId);
				blackListUsersDTO.setUserName(usrDetailsByID.getUserName());
				blackListUsersDTO.setEmail(usrDetailsByID.getEmail());
				blackListUsersDTO.setPhoneNumber(usrDetailsByID
						.getPhoneNumber());
			} else if (guestUserId != null) {
				EventGuestUserDTO guestUsrDetailsByID = whitelistUserDao
						.getGuestUsrDetailsByID(guestUserId);
				blackListUsersDTO
						.setUserName(guestUsrDetailsByID.getUserName());
				blackListUsersDTO.setEmail(guestUsrDetailsByID.getEmail());
				blackListUsersDTO.setPhoneNumber(guestUsrDetailsByID
						.getPhoneNumber());
			}
		}
		
		return allBlackLstUsrs;
	}

    @Transactional
	public EventBlackListDTO getBlackCustmrRecord(Integer orgId, String userName) {
    	
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		EventBlackListDTO blackLstCustmr = null;
		UserDTO userDetail = whitelistUserDao.getUserDetail(userName);
		EventGuestUserDTO guestUserDetail = whitelistUserDao.getGuestUserDetail(userName);
		if(userDetail!= null ){
		blackLstCustmr = blacklistUserDao.getBlackLstCustmr(userDetail.getUserId(), orgId);
		blackLstCustmr.setUserName(userDetail.getUserName());
		blackLstCustmr.setEmail(userDetail.getEmail());
		blackLstCustmr.setPhoneNumber(userDetail.getPhoneNumber());
		blackLstCustmr.setStrtDate(format.format(blackLstCustmr
				.getAddedDate()));
		}
		if(guestUserDetail!= null){
		
		blackLstCustmr = blacklistUserDao.getBlackLstGuestCustmr(guestUserDetail.getGuestUserId(), orgId);
		System.out.println("blacl lisy"+blackLstCustmr);
		blackLstCustmr.setUserName(guestUserDetail.getUserName());
		blackLstCustmr.setEmail(guestUserDetail.getEmail());
		blackLstCustmr.setPhoneNumber(guestUserDetail.getPhoneNumber());
		blackLstCustmr.setStrtDate(format.format(blackLstCustmr.getAddedDate()));
		blackLstCustmr.setGuestId(guestUserDetail.getGuestUserId());
		System.out.println("blacllist custmr list"+blackLstCustmr);
		System.out.println("blacl lisy"+blackLstCustmr);
		}
		return blackLstCustmr;
	}

    @Transactional
	public boolean isRemoveBlackCustmrFrmLst(Integer userId) {
		
		return blacklistUserDao.isRemoveBlackCustmrFrmLst(userId);
	}

    @Transactional
	public boolean isRemoveBlackGuestCustmrFrmLst(Integer userId) {
		
		return blacklistUserDao.isRemoveBlackGuestCustmrFrmLst(userId);
	}
}
