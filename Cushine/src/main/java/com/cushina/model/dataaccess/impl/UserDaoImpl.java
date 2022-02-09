package com.cushina.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.model.dataaccess.UserDao;
import com.cushina.model.pojo.BookingHistoryEntity;
import com.cushina.model.pojo.GuestUserEntity;
import com.cushina.model.pojo.UserEntity;
import com.events.common.dto.EventUserDTO;
import com.events.model.pojo.EventUserEntity;

@Repository
public class UserDaoImpl implements UserDao {

	Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Autowired
	public SessionFactory sessionFactory;
	Session session;

	public boolean saveUser(UserDTO userDto) {
		logger.info("saveUser dao : start");
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userEntity);
		logger.info("saveUser dao : end");
		return true;
	}

	@SuppressWarnings("unchecked")
	public UserDTO getLoginDetails(String email, String password) {
		logger.info("getLoginDetails dao : start");
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(UserEntity.class);
		Criterion emailId = Restrictions.eq("email", email);
		Criterion userName = Restrictions.eq("userName", email);
		LogicalExpression emailorUserName = Restrictions.or(emailId, userName);
		criteria.add(emailorUserName);
		criteria.add(Restrictions.eq("password", password));

		List<UserEntity> list = criteria.list();
		UserDTO usersDTO = null;
		for (UserEntity userEntity : list) {
			usersDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, usersDTO);

		}
		logger.info("getLoginDetails dao : end");
		return usersDTO;
	}

	public boolean isUserNameExists(String userName) {
		logger.info("isUserNameExists dao : start");
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> list = criteria.list();
		logger.info("isUserNameExists dao : end");
		return list != null && list.size() > 0;
	}

	public boolean isEmailExists(String email) {
		logger.info("isEmailExists : start");
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("email", email));
		List<UserEntity> list = criteria.list();
		logger.info("isEmailExists : end");
		return list != null && list.size() > 0;
	}

	@SuppressWarnings("unchecked")
	public boolean isUserTokenExistsCheck(String token) {
		boolean isUserTokenExists = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("token", token));
		List<UserEntity> list = criteria.list();
		if (list.size() > 0) {
			isUserTokenExists = true;
		}
		return isUserTokenExists;
	}

	@SuppressWarnings("unchecked")
	public boolean isUserTokenExists(String token) {
		logger.info("inside isUserNameExists dao : start");
		logger.info("token in userDaoImpl : " + token);
		boolean isUserTokenExists = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("token", token));
		List<UserEntity> list = criteria.list();
		UserDTO usersDTO = null;
		for (UserEntity userEntity : list) {
			usersDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, usersDTO);
		}

		Date addedDate = usersDTO.getJoinDate();
		String notificationDuration = usersDTO.getNotificationDuration();
		logger.info("notification duration :: " + notificationDuration);

		Date currentDate = new Date();
		long currentTime = currentDate.getTime();
		long addedTime = addedDate.getTime();

		logger.info("notificationDuration  :" + notificationDuration);
		long validTime = currentTime - addedTime;

		/**
		 * below if condition validate users within 24 hours of registered time.
		 * 24 hours is equal to 86400000 milliseconds.
		 */
		// test : (jayadev) I change to one min(60000 milliseconds).
		logger.info("valid Time : " + validTime);
		if (validTime < 60000) {
			UserEntity entity = new UserEntity();
			usersDTO.setStatus("Active");
			BeanUtils.copyProperties(usersDTO, entity);
			UserEntity user = (UserEntity) session.load(UserEntity.class,
					entity.getUserId());
			user.setStatus(usersDTO.getStatus());
			session.saveOrUpdate(user);
			logger.info("your registered before your notification duration ...");
			isUserTokenExists = true;

		} else {
			logger.info("sorry!!!, your notification duration was expired...");
			isUserTokenExists = false;
		}

		logger.info("inside isUserNameExists : end");
		return isUserTokenExists;
	}

	@SuppressWarnings("unused")
	public boolean updateUser(UserDTO userDTO) {

		boolean isUpdated = false;
		logger.info("updteUser dao : start");
		logger.info("user Id is :" + userDTO.getUserId());//:1
		Session session = sessionFactory.getCurrentSession();
		UserEntity userEntity = (UserEntity) session.load(UserEntity.class,
				userDTO.getUserId());
		logger.info("userenetity :" + userEntity.getToken());
		userDTO.setJoinDate(userEntity.getJoinDate());
		userDTO.setStatus(userEntity.getStatus());
		userDTO.setToken(userEntity.getToken());
		userDTO.setMailResentDate(userEntity.getMailResentDate());
		userDTO.setHotelID(userEntity.getHotelID());
		userDTO.setIsServiceProvider(userEntity.getIsServiceProvider());
		BeanUtils.copyProperties(userDTO, userEntity);

		session.saveOrUpdate(userEntity);

		if (userEntity != null) {
			logger.info("userUpdated successfully...!");
			BeanUtils.copyProperties(userEntity, userDTO);
			logger.info("updated use details :" + userDTO);
			isUpdated = true;
		} else {
			return isUpdated;
		}
		logger.info("updteUser dao : end");
		return isUpdated;
	}

	public UserDTO getUserDetail(String userName) {
		logger.info("getUserDetails dao : start");
		Integer userId = getUserIdByUserName(userName);
		logger.info("user name :" + userId);
		Session session = sessionFactory.getCurrentSession();
		UserEntity userEntity = (UserEntity) session.load(UserEntity.class,
				userId);
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userEntity, userDTO);
		logger.info("getUserDetails dao : end");
		return userDTO;
	}

	@SuppressWarnings("unchecked")
	private Integer getUserIdByUserName(String userName) {
		logger.info("getUserIdByUserName dao : start");
		logger.info("service provider user name :"+userName);
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> list = criteria.list();
		Integer userId = 0;
		userId = list.get(0).getUserId();
		logger.info("userId:::::::::::::" + userId);
		logger.info("getUserIdByUserName dao : end");
		return userId;
	}

	public boolean saveToken(Integer userID, String newToken,
			Date mailResentDate) {
		logger.info("inside savetoken method dao : start");
		logger.info(" @@@@ --> userId :" + userID + " newToken :" + newToken
				+ " mailREserntDate :" + mailResentDate);

		Session session = sessionFactory.getCurrentSession();
		UserEntity userEntity = (UserEntity) session.load(UserEntity.class,
				userID);

		logger.info("mailResentDate in saveToken dao : " + mailResentDate);
		logger.info("checking userEntity userName: " + userEntity.getUserName());
		userEntity.setToken(newToken);
		userEntity.setMailResentDate(mailResentDate);
		session.save(userEntity);
		/*
		 * Query query =
		 * session.createQuery("Update UserEntity set token= ?  where  userId= "
		 * +userID); query.setString(0, newToken); query.executeUpdate();
		 */
		logger.info("inside savetoken method dao : end");
		return true;
	}

	public UserDTO getUserByToken(String token) {
		Session session = sessionFactory.getCurrentSession();
		UserDTO userDTO = new UserDTO();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("token", token));
		List<UserEntity> list = criteria.list();
		for (UserEntity userEntity : list) {
			BeanUtils.copyProperties(userEntity, userDTO);
		}

		return userDTO;
	}

	public boolean isResendMailTokenExistsCheck(String token) {
		boolean isUserTokenExists = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("token", token));
		List<UserEntity> list = criteria.list();
		if (list.size() > 0) {
			isUserTokenExists = true;
		}
		return isUserTokenExists;
	}

	public boolean isResendMailTokenExists(String token) {
		logger.info("inside isUserNameExists in resend acticity ");

		boolean isUserTokenExists = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("token", token));
		List<UserEntity> list = criteria.list();
		UserDTO userDTO = null;
		for (UserEntity userEntity : list) {
			logger.info("printing resend mail date in userentity :"
					+ userEntity.getMailResentDate());
			userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
		}

		Date mailResentDate = userDTO.getMailResentDate();
		logger.info("mail resent date :" + mailResentDate);
		String notificationDuration = userDTO.getNotificationDuration();
		logger.info("notification duration :: " + notificationDuration);

		Date currentDate = new Date();

		long currentTime = currentDate.getTime();

		long addedTime = mailResentDate.getTime();
		logger.info("mail resent time :" + addedTime);

		logger.info("notificationDuration  :" + notificationDuration);
		long validTime = currentTime - addedTime;

		/**
		 * below if condition validate users within 24 hours of registered time.
		 * 
		 */
		if (validTime < 60000) {
			UserEntity entity = new UserEntity();

			userDTO.setStatus("Active");
			BeanUtils.copyProperties(userDTO, entity);

			UserEntity user = (UserEntity) session.load(UserEntity.class,
					entity.getUserId());
			user.setStatus(userDTO.getStatus());
			session.saveOrUpdate(user);
			logger.info("your registered before your notification duration ...");
			isUserTokenExists = true;

		} else {
			logger.info("sorry!!!, your notification duration was expired...");
			isUserTokenExists = false;
		}
		return isUserTokenExists;
	}

	@SuppressWarnings("unchecked")
	public boolean isProfEmailExists(Integer userId, String email) {
		logger.info("isProfEmailExists dao : start");
		logger.info("supplied userId is : " + userId);
		logger.info("supplied email is : " + email);
		UserDTO userDTO = new UserDTO();
		boolean result = false;
		session = sessionFactory.getCurrentSession();
		Criteria criteria1 = session.createCriteria(UserEntity.class);
		criteria1.add(Restrictions.eq("userId", userId));
		List<UserEntity> list1 = criteria1.list();
		for (UserEntity userEntity : list1) {
			BeanUtils.copyProperties(userEntity, userDTO);
		}
		if (userDTO.getEmail().equals(email)) {
			logger.info("inside if : " + userDTO.getEmail().equals(email));
			Criteria criteria2 = session.createCriteria(UserEntity.class);
			criteria2.add(Restrictions.eq("email", email));
			List<UserEntity> list2 = criteria2.list();
			result = (list2 != null && list2.size() > 1);
			logger.info("inside if list.size() = " + list2.size());
		} else {
			logger.info("inside else");
			Criteria criteria3 = session.createCriteria(UserEntity.class);
			criteria3.add(Restrictions.eq("email", email));
			List<UserEntity> list3 = criteria3.list();
			result = (list3 != null && list3.size() > 0);
			logger.info("inside else list.size() = " + list3.size());
		}
		logger.info("inside isProfEmailExists() returning : " + result);
		logger.info("isProfEmailExists dao : end");
		return result;
	}

	@SuppressWarnings("unchecked")
	public UserDTO getPassWord(String email) {
		logger.info("getPassWord : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		Criterion emailId = Restrictions.eq("email", email);
		Criterion userName = Restrictions.eq("userName", email);
		LogicalExpression emailorUserName = Restrictions.or(emailId, userName);
		criteria.add(emailorUserName);
		List<UserEntity> list = criteria.list();
		UserDTO usersDTO = null;
		for (UserEntity userEntity : list) {
			usersDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, usersDTO);

		}
		logger.info("getPassWord : end");
		return usersDTO;
	}

	public boolean isSaveQuickUser(UserDTO userDTO) {
		logger.info("isSaveQuickUser dao : start");
		Session session = sessionFactory.getCurrentSession();
		UserEntity quickUser = new UserEntity();
		userDTO.setUserName(userDTO.getFirstName());
		BeanUtils.copyProperties(userDTO, quickUser);
		session.saveOrUpdate(quickUser);
		logger.info("isSaveQuickUser dao : end");
		return true;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public Integer saveGuestUser(GuestUserDTO userDto) {
		logger.info("saveGuestUser daoImpl : start");
		GuestUserEntity userEntity = new GuestUserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userEntity);
		String userName = userEntity.getUserName();
		logger.info("isUpdate value : "+userDto.getIsUpdate());
		if(!userDto.getIsUpdate()) {
			logger.info("coming to if condition");
			Criteria criteria = session.createCriteria(GuestUserEntity.class);
			criteria.setProjection(Projections.max("userId"));
			List<Integer> list = criteria.list();
			Integer userId = 0;
			for (Integer integer : list) {
				userId = integer;
			}
			logger.info("saveGuestUser daoImpl : end");
			return userId;
		}
		else {
			return userDto.getUserId();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isGuestUserNameExists(String userName) {
		logger.info("isGuestUserNameExists daoImpl : start");
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		criteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> list = criteria.list();
		logger.info("isGuestUserNameExists daoImpl : end");
		return list != null && list.size() > 0;
	}

	@SuppressWarnings("unchecked")
	public boolean isGuestUserEmailExists(String email) {
		logger.info("isGuestUserEmailExists daoImpl : start");
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		criteria.add(Restrictions.eq("email", email));
		List<UserEntity> list = criteria.list();
		logger.info("isGuestUserEmailExists daoImpl : end");
		return list != null && list.size() > 0;
	}

	public GuestUserDTO getGuestUserDetail(String userName) {
		logger.info("getGuestUserDetail dao : start");
		Integer userId = getGuestUserIdByUserName(userName);
		Session session = sessionFactory.getCurrentSession();
		GuestUserEntity userEntity = (GuestUserEntity) session.load(
				GuestUserEntity.class, userId);
		GuestUserDTO userDTO = new GuestUserDTO();
		BeanUtils.copyProperties(userEntity, userDTO);
		logger.info("getGuestUserDetail dao : end");
		return userDTO;
	}

	@SuppressWarnings({ "unchecked" })
	private Integer getGuestUserIdByUserName(String UserName) {
		logger.info("getUserIdByUserName dao : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		criteria.add(Restrictions.eq("userName", UserName));
		List<GuestUserEntity> list = criteria.list();
		Integer userId = 0;
		userId = list.get(0).getUserId();
		logger.info("getUserIdByUserName dao : end");
		return userId;
	}

	// I think this method is not using any where....
	@SuppressWarnings({ "unchecked" })
	private Integer getGuestUserIdByEmail(String email) {
		logger.info("getUserIdByUserName dao : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		criteria.add(Restrictions.eq("email", email));
		List<GuestUserEntity> list = criteria.list();
		Integer userId = 0;
		for (GuestUserEntity GuestUserEntity : list) {
			userId = GuestUserEntity.getUserId();
		}
		logger.info("getUserIdByUserName dao : end");
		return userId;
	}

	@SuppressWarnings("unchecked")
	public UserDTO getUsrDetailsByID(Integer userId) {
		logger.info("getUsrDetails daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<UserEntity> list = criteria.list();
		UserDTO userDTO = new UserDTO();
		logger.info("list size --> " + list.size());
		BeanUtils.copyProperties(list.get(0), userDTO);
		logger.info("getUsrDetails daoImpl : end");
		return userDTO;
	}

	@SuppressWarnings("unchecked")
	public GuestUserDTO getGuestUsrDetailsByID(Integer userId) {
		logger.info("getGuestUsrDetailsByID daoImpl : start");
		logger.info("guest user Id = " + userId);
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<UserEntity> list = criteria.list();
		GuestUserDTO guestUserDTO = new GuestUserDTO();
		BeanUtils.copyProperties(list.get(0), guestUserDTO);
		logger.info("getGuestUsrDetailsByID daoImpl : end");
		return guestUserDTO;
	}

	@SuppressWarnings("unchecked")
	public List<BookingHistoryDTO> getReservationsWithSpecifiedDayDifference(
			int dayDifference) {
		logger.info("inside getReservationsWithSpecifiedDayDifference() : start ");
		Session session = sessionFactory.getCurrentSession();
		logger.info("dayDifference : " + dayDifference);
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		List<BookingHistoryEntity> list = criteria.list();
		logger.info("list size is : " + list.size());

		BookingHistoryDTO dto = null;
		List<BookingHistoryDTO> listDto = new ArrayList<BookingHistoryDTO>();

		for (BookingHistoryEntity entity : list) {
			Date currentDate = new Date();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(currentDate);
			int currDay = cal1.get(Calendar.DAY_OF_MONTH);
			int currMnth = cal1.get(Calendar.MONTH);
			int currYear = cal1.get(Calendar.YEAR);
			int currIncrDay = currDay + dayDifference;
			logger.info("day:" + currDay + "month:" + currMnth + "year:"
					+ currYear);

			Date checkIn = entity.getCheckInDate();
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(checkIn);
			int checkInDay = cal2.get(Calendar.DAY_OF_MONTH);
			int checkInMonth = cal2.get(Calendar.MONTH);
			int checkInYear = cal2.get(Calendar.YEAR);
			logger.info("from db: day:" + checkInDay + "month:" + checkInMonth
					+ "year:" + checkInYear);
			if (currYear == checkInYear && currMnth == checkInMonth
					&& currIncrDay == checkInDay) {
				dto = new BookingHistoryDTO();
				BeanUtils.copyProperties(entity, dto);
				logger.info("inside if condition dto : " + dto);
				listDto.add(dto);
			}
		}
		logger.info("inside getReservationsWithSpecifiedDayDifference() : listDto : "
				+ listDto);
		return listDto;
	}

	@SuppressWarnings("unchecked")
	public UserDTO getUserById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userId", id));
		UserDTO dto = new UserDTO();
		List<UserEntity> list = criteria.list();
		for (UserEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		logger.info("inside getuserbyid : userDto :" + dto);
		return dto;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> getAllCustomrsLst(Long hoteId) {
		logger.info("getAllCustomrsLst daoImpl : start");
		List<UserDTO> customerList = new ArrayList<UserDTO>();
		UserDTO custmr  = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		List<UserEntity> custmrsLst = criteria.list();
		for (UserEntity userEntity : custmrsLst) {
			custmr = new UserDTO();
			BeanUtils.copyProperties(userEntity, custmr);
			customerList.add(custmr);
		}
		logger.info("custmr list :"+customerList);
		logger.info("getAllCustomrsLst daoImpl : end");
		return customerList;
	}
	public EventUserDTO isEventServiceProvider(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventUserEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		EventUserDTO dto = null;
		List<EventUserEntity> list = criteria.list();
		for (EventUserEntity entity : list) {
			dto=new EventUserDTO();
			BeanUtils.copyProperties(entity, dto);
		
		}
		return dto;
	}
	public boolean saveEventUser(UserDTO userDto) {
		logger.info("saveUser dao : start");
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userEntity);
		String userName = userDto.getUserName();
		Criteria createCriteria = session.createCriteria(UserEntity.class);
		createCriteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> list = createCriteria.list();
		Integer userId=0;
		for (UserEntity userEntity2 : list) {
			userId=userEntity2.getUserId();
		}
		EventUserEntity eventUserEntity=new EventUserEntity();
		eventUserEntity.setEventOrgId(userDto.getEventOrgId());
		eventUserEntity.setUserId(userId);
		eventUserEntity.setIsServiceProvider("N");
		session.save(eventUserEntity);
		logger.info("saveUser dao : end");
		return true;
	}

	public boolean saveEventServiceUser(UserDTO userDto) {
		logger.info("saveUser dao : start");
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userEntity);
		String userName = userDto.getUserName();
		Criteria createCriteria = session.createCriteria(UserEntity.class);
		createCriteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> list = createCriteria.list();
		Integer userId=0;
		for (UserEntity userEntity2 : list) {
			userId=userEntity2.getUserId();
		}
		EventUserEntity eventUserEntity=new EventUserEntity();
		eventUserEntity.setEventOrgId(userDto.getEventOrgId());
		eventUserEntity.setUserId(userId);
		eventUserEntity.setIsServiceProvider("Y");
		session.save(eventUserEntity);
		logger.info("saveUser dao : end");
		return true;
	}
	

}
