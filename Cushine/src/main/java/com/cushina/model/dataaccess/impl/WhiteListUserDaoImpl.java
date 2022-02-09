package com.cushina.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.model.dataaccess.WhiteListUserDao;
import com.cushina.model.pojo.BlackListUsersEntity;
import com.cushina.model.pojo.BookingHistoryEntity;
import com.cushina.model.pojo.GuestUserEntity;
import com.cushina.model.pojo.HotelEntity;
import com.cushina.model.pojo.UserEntity;
import com.cushina.model.pojo.WhiteListServiceEntity;
import com.cushina.model.pojo.WhiteListUsersEntity;

@Repository
public class WhiteListUserDaoImpl implements WhiteListUserDao {

	private Logger logger = Logger.getLogger(WhiteListUserDaoImpl.class);

	@Autowired
	public SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BookingHistoryEntity updateEmailShare(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<BookingHistoryEntity> list = criteria.list();
		BookingHistoryEntity userEntity = list.get(0);

		userEntity.setEmailShare("InActive");
		session.saveOrUpdate(userEntity);
		return userEntity;
	}

	@SuppressWarnings("unchecked")
	public List<WhiteListUsersDTO> getWhiteListStartDate(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<WhiteListUsersEntity> whiteList = criteria.list();
		List<WhiteListUsersDTO> whitelistDtos = new ArrayList<WhiteListUsersDTO>();
		WhiteListUsersDTO whitelistDto = null;
		for (WhiteListUsersEntity whiteListUsersEntity : whiteList) {
			whitelistDto = new WhiteListUsersDTO();
			BeanUtils.copyProperties(whiteListUsersEntity, whitelistDto);
			whitelistDtos.add(whitelistDto);
		}
		return whitelistDtos;
	}

	@SuppressWarnings("unchecked")
	public HotelDTO getServiceWhiteListHotelInfo(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HotelEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<HotelEntity> hotelList = criteria.list();
		HotelDTO hoteldto = null;
		for (HotelEntity hotelEntity : hotelList) {
			hoteldto = new HotelDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
		}
		return hoteldto;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> getUserWhiteListHotelInfo(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<UserEntity> hotelList = criteria.list();
		List<UserDTO> userdtos = new ArrayList<UserDTO>();
		System.out.println("hotelist size :" + hotelList.size());
		UserDTO hoteldto = null;
		for (UserEntity hotelEntity : hotelList) {
			hoteldto = new UserDTO();
			hoteldto.setUserName(hotelEntity.getUserName());
			BeanUtils.copyProperties(hotelEntity, hoteldto);
			userdtos.add(hoteldto);
		}

		return userdtos;
	}

	@SuppressWarnings("unchecked")
	public List<WhiteListUsersDTO> getUserServiceWhiteListStartDate(
			Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(WhiteListServiceEntity.class);
		criteria.add(Restrictions.eq("userId", userId));

		List<WhiteListServiceEntity> whiteList = criteria.list();
		List<WhiteListUsersDTO> whitelistDtos = new ArrayList<WhiteListUsersDTO>();
		WhiteListUsersDTO whitelistDto = null;
		for (WhiteListServiceEntity whiteListUsersEntity : whiteList) {
			whitelistDto = new WhiteListUsersDTO();
			BeanUtils.copyProperties(whiteListUsersEntity, whitelistDto);
			whitelistDtos.add(whitelistDto);
		}
		return whitelistDtos;
	}

	public UserDTO getServiceProfileUserDetail(String userName) {
		Integer userId = getUserIdByUserName(userName);

		Session session = sessionFactory.getCurrentSession();
		UserEntity userEntity = (UserEntity) session.load(UserEntity.class,
				userId);
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userEntity, userDTO);
		return userDTO;
	}

	@SuppressWarnings("unchecked")
	private Integer getUserIdByUserName(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> list = criteria.list();
		Integer userId = 0;
		for (UserEntity userEntity : list) {
			userId = userEntity.getUserId();
		}
		return userId;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> getUserListHotelInfo(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<UserEntity> hotelList = criteria.list();
		List<UserDTO> userdtos = new ArrayList<UserDTO>();
		System.out.println("hotelist size :" + hotelList.size());
		UserDTO hoteldto = null;
		for (UserEntity hotelEntity : hotelList) {
			hoteldto = new UserDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
			userdtos.add(hoteldto);
		}
		return userdtos;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> getUserListHotelInfo(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria createCriteria = session.createCriteria(UserEntity.class);
		createCriteria.add(Restrictions.eq("userName", userName));

		List<UserEntity> queryList = createCriteria.list();
		List<UserDTO> hotelList = new ArrayList<UserDTO>();
		for (UserEntity hotelentity : queryList) {
			UserDTO hoteldto = new UserDTO();
			BeanUtils.copyProperties(hotelentity, hoteldto);
			System.out.println("hotel address" + hoteldto.getUserName());
			hotelList.add(hoteldto);
		}
		return hotelList;
	}

	public List<WhiteListUsersDTO> getUserServiceWhiteListStartDate(
			Integer userId, Long hotelID) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<WhiteListUsersDTO> getServiceListStartDate(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<WhiteListUsersEntity> whitelist = criteria.list();
		List<WhiteListUsersDTO> whitelistDtos = new ArrayList<WhiteListUsersDTO>();
		WhiteListUsersDTO whitelistDto = null;
		for (WhiteListUsersEntity whiteListServiceEntity : whitelist) {
			whitelistDto = new WhiteListUsersDTO();
			BeanUtils.copyProperties(whiteListServiceEntity, whitelistDto);
			whitelistDtos.add(whitelistDto);
		}
		return whitelistDtos;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public UserDTO getServiceListUserInfo(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<UserEntity> hotelList = criteria.list();
		UserDTO userdto = null;
		for (UserEntity userEntity : hotelList) {
			userdto = new UserDTO();
			String userName = userdto.getUserName();
			BeanUtils.copyProperties(userEntity, userdto);
		}
		return userdto;
	}

	@SuppressWarnings("unchecked")
	public UserDTO getServiceUserNameList(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> hotelList = criteria.list();
		UserDTO userdto = null;
		for (UserEntity userEntity : hotelList) {
			userdto = new UserDTO();
			BeanUtils.copyProperties(userEntity, userdto);

		}
		return userdto;
	}

	public boolean isWhitListUser(Integer userId) {
		logger.info("isWhitListUser daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		logger.info("isWhitListUser daoImpl : end");
		return criteria.list().size() > 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	public List<WhiteListUsersDTO> getWhiteListCategoryUsrs(Integer userId) {
		logger.info("getWhiteListCategoryUsrs daoImpl : start");
		List<WhiteListUsersDTO> whiteUsersList = new ArrayList<WhiteListUsersDTO>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<WhiteListUsersEntity> whiteListCustmrs = criteria.list();
		for (WhiteListUsersEntity whiteListUsersEntity : whiteListCustmrs) {
			WhiteListUsersDTO whteLstUsr = new WhiteListUsersDTO();
			BeanUtils.copyProperties(whiteListUsersEntity, whteLstUsr);
			whiteUsersList.add(whteLstUsr);
		}
		logger.info("getWhiteListCategoryUsrs daoImpl : end");
		return whiteUsersList;
	}

	@SuppressWarnings("unchecked")
	public List<BlackListUsersDTO> getBlackListCategoryUsrs(Integer userId) {
		logger.info("getBlackListCategoryUsrs daoImpl : start");
		List<BlackListUsersDTO> blackUsersList = new ArrayList<BlackListUsersDTO>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<BlackListUsersEntity> whiteListCustmrs = criteria.list();
		for (BlackListUsersEntity blackListUsersEntity : whiteListCustmrs) {
			BlackListUsersDTO blackUsr = new BlackListUsersDTO();
			BeanUtils.copyProperties(blackListUsersEntity, blackUsr);
			blackUsersList.add(blackUsr);
		}
		logger.info("getBlackListCategoryUsrs daoImpl : end");
		return blackUsersList;
	}

	public HotelDTO getHotelDetails(Long hotelId) {
		logger.info("getHotelDetails daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HotelEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		HotelEntity hotelEntity = (HotelEntity) session.load(HotelEntity.class,
				hotelId);
		HotelDTO hotelInfo = new HotelDTO();
		BeanUtils.copyProperties(hotelEntity, hotelInfo);
		logger.info("getHotelDetails daoImpl : end");
		return hotelInfo;
	}
}
