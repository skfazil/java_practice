package com.cushina.model.dataaccess;

import java.util.Date;
import java.util.List;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.events.common.dto.EventUserDTO;

public interface UserDao {

	public boolean saveUser(UserDTO userDto);

	public UserDTO getLoginDetails(String email, String password);

	public boolean isUserNameExists(String userName);

	public boolean isEmailExists(String email);

	public boolean isProfEmailExists(Integer userId, String email);

	public boolean isUserTokenExists(String token);

	public boolean updateUser(UserDTO userDTO);

	public UserDTO getUserDetail(String userName);

	public GuestUserDTO getGuestUserDetail(String userName);

	public boolean saveToken(Integer userId, String token, Date mailResentDate);

	public UserDTO getUserByToken(String token);

	public boolean isResendMailTokenExists(String token);

	public UserDTO getPassWord(String email);

	public boolean isSaveQuickUser(UserDTO userDTO);

	public Integer saveGuestUser(GuestUserDTO userDto);

	public boolean isGuestUserNameExists(String userName);

	public boolean isGuestUserEmailExists(String email);
	
	public UserDTO getUsrDetailsByID(Integer userId);
	
	public GuestUserDTO getGuestUsrDetailsByID(Integer userId);
	
	public boolean isUserTokenExistsCheck(String token);
	
	public boolean isResendMailTokenExistsCheck(String token);
	
	public List<BookingHistoryDTO> getReservationsWithSpecifiedDayDifference(int dayDifference);
	 
	public UserDTO getUserById(Integer id);
	
	public List<UserDTO> getAllCustomrsLst(Long hoteId);
	public EventUserDTO isEventServiceProvider(Integer userId);
	public boolean saveEventUser(UserDTO userDto);

	public boolean saveEventServiceUser(UserDTO userDto);

}
