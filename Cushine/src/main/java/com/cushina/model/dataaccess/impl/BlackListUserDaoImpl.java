package com.cushina.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.model.dataaccess.BlackListUserDao;
import com.cushina.model.pojo.BlackListServiceEntity;
import com.cushina.model.pojo.BlackListUsersEntity;
import com.cushina.model.pojo.HotelEntity;
import com.cushina.model.pojo.RoomInfoEntity;
import com.cushina.model.pojo.UserEntity;
import com.cushina.service.impl.PickHotelServiceImpl;

@Repository
public class BlackListUserDaoImpl implements BlackListUserDao {

	private Logger logger = Logger.getLogger(PickHotelServiceImpl.class
			.getName());

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<BlackListUsersDTO> getUsersBlackListStartDate(Integer userId,
			Long hotelID) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(BlackListServiceEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<BlackListServiceEntity> blackList = criteria.list();
		List<BlackListUsersDTO> blacklistDtos = new ArrayList<BlackListUsersDTO>();
		BlackListUsersDTO blacklistDto = null;
		for (BlackListServiceEntity blackListServiceEntity : blackList) {
			blacklistDto = new BlackListUsersDTO();
			BeanUtils.copyProperties(blackListServiceEntity, blacklistDto);
			blacklistDtos.add(blacklistDto);
		}
		return blacklistDtos;

	}

	@SuppressWarnings("unchecked")
	public HotelDTO getUserBlackListHotelDetail(Long hotelID) {
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
	public List<BlackListUsersDTO> getBlackListStartDate(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<BlackListUsersEntity> blackListUsersEntities = criteria.list();
		List<BlackListUsersDTO> blackListUsersDTOs = new ArrayList<BlackListUsersDTO>();
		BlackListUsersDTO blackListUsersDTO = null;
		for (BlackListUsersEntity blackListUsersEntity : blackListUsersEntities) {
			blackListUsersDTO = new BlackListUsersDTO();
			BeanUtils.copyProperties(blackListUsersEntity, blackListUsersDTO);
			blackListUsersDTOs.add(blackListUsersDTO);
		}
		return blackListUsersDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> getServiceBlackListHotelInfo(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<UserEntity> hotelList = criteria.list();
		List<UserDTO> hoteldtos = new ArrayList<UserDTO>();
		UserDTO hoteldto = null;
		for (UserEntity hotelEntity : hotelList) {
			hoteldto = new UserDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
			hoteldtos.add(hoteldto);
		}
		return hoteldtos;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> getUserBlackListHotelInfo(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<UserEntity> hotelList = criteria.list();
		List<UserDTO> hoteldtos = new ArrayList<UserDTO>();
		UserDTO hoteldto = null;
		for (UserEntity hotelEntity : hotelList) {
			hoteldto = new UserDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
			hoteldtos.add(hoteldto);
		}
		return hoteldtos;
	}

	@SuppressWarnings("unchecked")
	public List<BlackListUsersDTO> getServiceBlackListStartDate(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<BlackListUsersEntity> blackListUsersEntities = criteria.list();
		List<BlackListUsersDTO> blackListUsersDTOs = new ArrayList<BlackListUsersDTO>();
		BlackListUsersDTO blackListUsersDTO = null;
		for (BlackListUsersEntity blackListUsersEntity : blackListUsersEntities) {
			blackListUsersDTO = new BlackListUsersDTO();
			BeanUtils.copyProperties(blackListUsersEntity, blackListUsersDTO);
			blackListUsersDTOs.add(blackListUsersDTO);
		}
		return blackListUsersDTOs;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public UserDTO getBlackServiceUserInfo(Integer userId) {
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
	public BlackListUsersDTO isBlackUsr(Integer userId) {
		logger.info("isBlackUsr daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<BlackListUsersEntity> blackUsrRecrd = criteria.list();
		BlackListUsersDTO blackListUsr = new BlackListUsersDTO();
		if (blackUsrRecrd.size() != 0)
			BeanUtils.copyProperties(blackUsrRecrd.get(0), blackListUsr);
		logger.info("isBlackUsr daoImpl : end");
		return blackListUsr;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public boolean isBlackRoom(Long roomNo) {
		logger.info("isBlackRoom daoImpl : start");
		boolean isBlackRoom = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RoomInfoEntity.class);
		criteria.add(Restrictions.eq("roomId", roomNo));
		Integer whitRoomId = new Integer(1);
		criteria.add(Restrictions.eq("WhiteAndBlack", whitRoomId));
		List<RoomInfoEntity> roomInfo = criteria.list();
		logger.info("isBlackRoom daoImpl : end");
		return isBlackRoom = (roomInfo.size() > 0 ? false : true);
	}

}
