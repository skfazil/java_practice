package com.cushina.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.exception.EmailNotUniqueException;
import com.cushina.common.exception.UserNotUniqueException;
import com.cushina.model.dataaccess.UserDao;
import com.cushina.service.UserService;
import com.events.common.dto.EventUserDTO;

@Service
public class UserServiceImpl implements UserService {
	private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

	@Autowired
	UserDao userDao;

	@Transactional
	public boolean isUserNameExists(String userName) {
		return userDao.isUserNameExists(userName);
	}

	@Transactional
	public boolean saveUser(UserDTO userDto) throws UserNotUniqueException,
			EmailNotUniqueException {
		logger.info("saveUser service : start");

		boolean result = false;
		String userName = userDto.getUserName();
		String email = userDto.getEmail();
		boolean userNameExists = userDao.isUserNameExists(userName);
		boolean emailExists = userDao.isEmailExists(email);
		if (userNameExists) {
			throw new UserNotUniqueException("Username '" + userName
					+ "' is already exists");
		} else if (emailExists) {
			throw new EmailNotUniqueException("Email '" + email
					+ "' is already exists");
		} else {
			logger.info("save guest user");
			result = userDao.saveUser(userDto);
		}
		logger.info("saveUser service : end");
		return result;
	}

	@Transactional
	public UserDTO getLoginDetails(String email, String password) {
		return userDao.getLoginDetails(email, password);
	}

	@Transactional
	public boolean isUserTokenExists(String token) {
		logger.info("inside userServiceImpl token is  :" + token);
		return userDao.isUserTokenExists(token);
	}

	@Transactional
	public boolean updateUser(UserDTO userDTO) throws EmailNotUniqueException {
		boolean result = false;
		Integer userId = userDTO.getUserId();
		String email = userDTO.getEmail();
		boolean emailExists = userDao.isProfEmailExists(userId, email);
		if (emailExists) {
			throw new EmailNotUniqueException("Email '" + email
					+ "' is already exists");
		} else {
			result = userDao.updateUser(userDTO);
		}
		return result;
	}

	@Transactional
	public UserDTO getUserDetail(String userName) {
		return userDao.getUserDetail(userName);
	}

	@Transactional
	public boolean saveToken(Integer userId, String newToken,
			Date mailResentDate) {
		return userDao.saveToken(userId, newToken, mailResentDate);
	}

	@Transactional
	public UserDTO getUserByToken(String token) {
		return userDao.getUserByToken(token);
	}

	@Transactional
	public boolean isResendMailTokenExists(String token) {
		logger.info("resend token in service : " + token);
		return userDao.isResendMailTokenExists(token);
	}

	@Transactional
	public boolean isEmailExists(String email) {
		logger.info("inside isEmailExists() of serviceImpl");
		return userDao.isEmailExists(email);
	}

	@Transactional
	public boolean isProfEmailExists(Integer userId, String email) {
		return userDao.isProfEmailExists(userId, email);
	}

	@Transactional
	public UserDTO getPassWord(String email) {
		return userDao.getPassWord(email);
	}

	@Transactional
	public boolean isSaveQuickUser(UserDTO userDTO) {
		return userDao.isSaveQuickUser(userDTO);
	}

	@Transactional
	public Integer saveGuestUser(GuestUserDTO userDto) {
		return userDao.saveGuestUser(userDto);
	}

	@Transactional
	public GuestUserDTO getGuestUserDetail(String email) {
		return userDao.getGuestUserDetail(email);
	}

	@Transactional
	public boolean isUserTokenExistsCheck(String token) {
		logger.info("inside userServiceImpl token is  :" + token);
		return userDao.isUserTokenExistsCheck(token);
	}

	@Transactional
	public boolean isResendMailTokenExistsCheck(String token) {
		logger.info("resend token in service : " + token);
		return userDao.isResendMailTokenExistsCheck(token);
	}

	@Transactional
	public GuestUserDTO getGuestUserDetailsById(Integer guestuserId) {
		return userDao.getGuestUsrDetailsByID(guestuserId);
	}

	@Transactional
	public List<BookingHistoryDTO> getReservationsWithSpecifiedDayDifference(
			int dayDifference) {
		return userDao.getReservationsWithSpecifiedDayDifference(dayDifference);
	}

	@Transactional
	public UserDTO getUserById(Integer id) {
		return userDao.getUserById(id);
	}

	@Transactional
	public List<UserDTO> getAllCustomrsLst(Long hoteId) {
		logger.info("getAllCustomrsLst serviceImpl : start");
		List<UserDTO> allCustomrsLst = userDao.getAllCustomrsLst(hoteId);
		Iterator<UserDTO> iterator = allCustomrsLst.iterator();
		while(iterator.hasNext()){
				UserDTO custmr = iterator.next();
				if(custmr.getHotelID() != null){
					iterator.remove();
				}
		}
		logger.info("getAllCustomrsLst serviceImpl : end");
		return allCustomrsLst;
	}
	@Transactional
	public EventUserDTO isEventServiceProvider(Integer userId) {
		return userDao.isEventServiceProvider(userId);
	}

	@Transactional
	public boolean saveEventUser(UserDTO userDto) throws UserNotUniqueException, EmailNotUniqueException {

		logger.info("saveUser service : start");

		boolean result = false;
		String userName = userDto.getUserName();
		String email = userDto.getEmail();
		boolean userNameExists = userDao.isUserNameExists(userName);
		boolean emailExists = userDao.isEmailExists(email);
		if (userNameExists) {
			throw new UserNotUniqueException("Username '" + userName
					+ "' is already exists");
		} else if (emailExists) {
			throw new EmailNotUniqueException("Email '" + email
					+ "' is already exists");
		} else {
			logger.info("save guest user");
			result = userDao.saveEventUser(userDto);
		}
		logger.info("saveUser service : end");
		return result;
	
	}

	@Transactional
	public boolean saveEventServiceUser(UserDTO userDto)
			throws UserNotUniqueException, EmailNotUniqueException {
		logger.info("saveUser service : start");

		boolean result = false;
		String userName = userDto.getUserName();
		String email = userDto.getEmail();
		boolean userNameExists = userDao.isUserNameExists(userName);
		boolean emailExists = userDao.isEmailExists(email);
		if (userNameExists) {
			throw new UserNotUniqueException("Username '" + userName
					+ "' is already exists");
		} else if (emailExists) {
			throw new EmailNotUniqueException("Email '" + email
					+ "' is already exists");
		} else {
			logger.info("save guest user");
			result = userDao.saveEventServiceUser(userDto);
		}
		logger.info("saveUser service : end");
		return result;
	}


}
