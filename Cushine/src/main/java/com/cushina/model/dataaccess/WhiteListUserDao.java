package com.cushina.model.dataaccess;

import java.util.List;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;

public interface WhiteListUserDao {

	public List<WhiteListUsersDTO> getWhiteListStartDate(Integer userId);

	public HotelDTO getServiceWhiteListHotelInfo(Long hotelID);

	public List<UserDTO> getUserWhiteListHotelInfo(Long hotelID);

	public List<WhiteListUsersDTO> getUserServiceWhiteListStartDate(
			Integer userId, Long hotelID);

	public UserDTO getServiceProfileUserDetail(String userName);

	public List<UserDTO> getUserListHotelInfo(Long hotelID);

	public List<UserDTO> getUserListHotelInfo(String userName);

	public List<WhiteListUsersDTO> getServiceListStartDate(Long hotelID);

	public UserDTO getServiceListUserInfo(Integer userId);

	public UserDTO getServiceUserNameList(String userName);

	public boolean isWhitListUser(Integer userId);

	public List<WhiteListUsersDTO> getWhiteListCategoryUsrs(Integer userId);

	public List<BlackListUsersDTO> getBlackListCategoryUsrs(Integer userId);

	public HotelDTO getHotelDetails(Long hotelId);

}
