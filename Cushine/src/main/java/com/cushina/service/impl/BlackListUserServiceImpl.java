package com.cushina.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.model.dataaccess.BlackListUserDao;
import com.cushina.service.BlackListUserService;

@Service
public class BlackListUserServiceImpl implements BlackListUserService {

	private Logger logger = Logger.getLogger(PickHotelServiceImpl.class
			.getName());
	
	@Autowired
	private BlackListUserDao blackListdao;

	@Transactional
	public List<UserDTO> getUserBlackListHotelInfo(Long hotelID) {

		return blackListdao.getUserBlackListHotelInfo(hotelID);
	}

	@Transactional
	public HotelDTO getUserBlackListHotelDetail(Long hotelID) {

		return blackListdao.getUserBlackListHotelDetail(hotelID);
	}

	@Transactional
	public List<BlackListUsersDTO> getBlackListStartDate(Integer userId) {

		return blackListdao.getBlackListStartDate(userId);
	}

	@Transactional
	public List<UserDTO> getServiceBlackListHotelInfo(Long hotelID) {

		return blackListdao.getServiceBlackListHotelInfo(hotelID);
	}

	@Transactional
	public List<BlackListUsersDTO> getUsersBlackListStartDate(Integer userId,
			Long hotelID) {
		return blackListdao.getUsersBlackListStartDate(userId, hotelID);
	}

	@Transactional
	public List<BlackListUsersDTO> getServiceBlackListStartDate(Long hotelID) {
		return blackListdao.getServiceBlackListStartDate(hotelID);
	}

	@Transactional
	public UserDTO getBlackServiceUserInfo(Integer userId) {
		return blackListdao.getBlackServiceUserInfo(userId);
	}

	@Transactional
	public boolean isBlackUsr(Integer userId,Long roomNo) {
		boolean isBlackRoom = false;
		BlackListUsersDTO blackUsr = blackListdao.isBlackUsr(userId);
		if (blackUsr.getUserId() != null) {
			isBlackRoom = blackListdao.isBlackRoom(roomNo);
			if (isBlackRoom)
				isBlackRoom = true;
		} else {
			isBlackRoom = true;
		}
		return isBlackRoom;
	}

	

}
