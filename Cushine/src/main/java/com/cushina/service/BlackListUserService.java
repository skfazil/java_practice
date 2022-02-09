package com.cushina.service;

import java.util.List;
import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;

public interface BlackListUserService {

	public List<BlackListUsersDTO> getUsersBlackListStartDate(Integer userId,
			Long hotelID);

	public HotelDTO getUserBlackListHotelDetail(Long hotelID);

	public List<BlackListUsersDTO> getBlackListStartDate(Integer userId);

	public List<UserDTO> getServiceBlackListHotelInfo(Long hotelID);

	public List<BlackListUsersDTO> getServiceBlackListStartDate(Long hotelID);

	public UserDTO getBlackServiceUserInfo(Integer userId);

	public boolean isBlackUsr(Integer userId,Long roomNo);
	
	

}
