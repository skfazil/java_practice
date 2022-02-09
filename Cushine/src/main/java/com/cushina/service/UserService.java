package com.cushina.service;

import java.util.Date;
import java.util.List;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.exception.EmailNotUniqueException;
import com.cushina.common.exception.UserNotUniqueException;
import com.events.common.dto.EventUserDTO;

public interface UserService {

	public boolean saveUser(UserDTO userDto) throws UserNotUniqueException,
			EmailNotUniqueException;

	public UserDTO getLoginDetails(String email, String password);

	public boolean isUserNameExists(String userName);

	public boolean isEmailExists(String email);

	public boolean isProfEmailExists(Integer userId, String email);

	public boolean isUserTokenExists(String token);

	public boolean updateUser(UserDTO userDTO) throws EmailNotUniqueException;

	public UserDTO getUserDetail(String userName);

	public GuestUserDTO getGuestUserDetail(String email);

	public boolean saveToken(Integer userId, String token, Date mailResentDate);

	public UserDTO getUserByToken(String token);

	public boolean isResendMailTokenExists(String token);

	public UserDTO getPassWord(String email);

	public boolean isSaveQuickUser(UserDTO userDTO);

	public Integer saveGuestUser(GuestUserDTO userDto);

	public boolean isUserTokenExistsCheck(String token);

	public boolean isResendMailTokenExistsCheck(String token);

	public GuestUserDTO getGuestUserDetailsById(Integer guestuserId);

	public List<BookingHistoryDTO> getReservationsWithSpecifiedDayDifference(
			int dayDifference);

	public UserDTO getUserById(Integer id);
	
	public List<UserDTO> getAllCustomrsLst(Long hoteId);

	public EventUserDTO isEventServiceProvider(Integer userId);
	public boolean saveEventUser(UserDTO userDto)throws UserNotUniqueException,EmailNotUniqueException;

	public boolean saveEventServiceUser(UserDTO userDTO)throws UserNotUniqueException,EmailNotUniqueException;
	

}
