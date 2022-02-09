package com.cushina.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.model.dataaccess.WhiteListUserDao;
import com.cushina.service.WhiteListUserService;

@Service
public class WhiteListUserServiceImpl implements WhiteListUserService {

	@Autowired
	private WhiteListUserDao whitelistdao;

	@Transactional
	public HotelDTO getServiceWhiteListHotelInfo(Long hotelID) {
		return whitelistdao.getServiceWhiteListHotelInfo(hotelID);
	}

	@Transactional
	public List<WhiteListUsersDTO> getWhiteListStartDate(Integer userId) {
		return whitelistdao.getWhiteListStartDate(userId);
	}

	@Transactional
	public List<UserDTO> getUserWhiteListHotelInfo(Long hotelID) {
		return whitelistdao.getUserWhiteListHotelInfo(hotelID);
	}

	@Transactional
	public List<WhiteListUsersDTO> getUserServiceWhiteListStartDate(
			Integer userId, Long hotelID) {
		return whitelistdao.getUserServiceWhiteListStartDate(userId, hotelID);
	}

	@Transactional
	public UserDTO getServiceProfileUserDetail(String userName) {
		return whitelistdao.getServiceProfileUserDetail(userName);
	}

	@Transactional
	public List<UserDTO> getUserListHotelInfo(Long hotelID) {
		return whitelistdao.getUserListHotelInfo(hotelID);
	}

	@Transactional
	public List<UserDTO> getProfileUserDetail(String userName) {
		return whitelistdao.getUserListHotelInfo(userName);
	}

	@Transactional
	public List<WhiteListUsersDTO> getServiceListStartDate(Long hotelID) {
		return whitelistdao.getServiceListStartDate(hotelID);
	}

	@Transactional
	public UserDTO getServiceListUserInfo(Integer userId) {
		return whitelistdao.getServiceListUserInfo(userId);
	}

	@Transactional
	public UserDTO getServiceUserNameList(String userName) {
		return whitelistdao.getServiceUserNameList(userName);
	}

	@Transactional
	public List<WhiteListUsersDTO> getWhiteListCategoryUsrs(Integer userId) {
		List<WhiteListUsersDTO> whiteListCategoryUsrs = whitelistdao
				.getWhiteListCategoryUsrs(userId);
		for (WhiteListUsersDTO whiteListUsersDTO : whiteListCategoryUsrs) {
			Long hotelId = whiteListUsersDTO.getHotelID();
			HotelDTO hotelDetails = whitelistdao.getHotelDetails(hotelId);
			whiteListUsersDTO.setHotelName(hotelDetails.getHotelName());
			whiteListUsersDTO.setHotelAddress(hotelDetails.getHotelAddress());
			whiteListUsersDTO.setCity(hotelDetails.getCity());
			whiteListUsersDTO.setPhoneNumber(String.valueOf(hotelDetails
					.getPhoneNumber()));
		}
		return whiteListCategoryUsrs;
	}

	@Transactional
	public List<BlackListUsersDTO> getBlackListCategoryUsrs(Integer userId) {
		List<BlackListUsersDTO> blackListCategoryUsrs = whitelistdao
				.getBlackListCategoryUsrs(userId);
		for (BlackListUsersDTO blackListUsersDTO : blackListCategoryUsrs) {
			Long hotelId = blackListUsersDTO.getHotelID();
			HotelDTO hotelDetails = whitelistdao.getHotelDetails(hotelId);
			blackListUsersDTO.setHotelName(hotelDetails.getHotelName());
			blackListUsersDTO.setHotelAddress(hotelDetails.getHotelAddress());
			blackListUsersDTO.setCity(hotelDetails.getCity());
			blackListUsersDTO.setPhoneNumber(String.valueOf(hotelDetails
					.getPhoneNumber()));
		}
		return blackListCategoryUsrs;
	}

}
