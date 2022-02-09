package com.events.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.model.pojo.BlackListUsersEntity;
import com.cushina.model.pojo.GuestUserEntity;
import com.cushina.model.pojo.HotelEntity;
import com.cushina.model.pojo.UserEntity;
import com.cushina.model.pojo.WhiteListUsersEntity;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.model.dataaccess.EventWhitelistUserDao;
import com.events.model.pojo.EventBlackListEntity;
import com.events.model.pojo.EventGuestUserEntity;
import com.events.model.pojo.EventOrganizerEntity;
import com.events.model.pojo.EventWhiteListEntity;

@Repository
public class EventWhitelistUserDaoImpl implements EventWhitelistUserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<EventWhiteListDTO> getEventWhiteListStartDate(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<EventWhiteListEntity> whiteList = criteria.list();
		System.out.println("entity whitelist ::"+whiteList);
		List<EventWhiteListDTO> whitelistDtos = new ArrayList<EventWhiteListDTO>();
		EventWhiteListDTO whitelistDto = null;
		for (EventWhiteListEntity whiteListUsersEntity : whiteList) {
			System.out.println("whitelist entity :::"+whiteListUsersEntity);
			whitelistDto = new EventWhiteListDTO();
			BeanUtils.copyProperties(whiteListUsersEntity, whitelistDto);
			whitelistDtos.add(whitelistDto);
		}
		return whitelistDtos;
	}

	@Transactional
	public EventsOrganizerDTO getEventServiceWhiteListHotelInfo(Integer eventOrgID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventOrganizerEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", eventOrgID));
		List<EventOrganizerEntity> hotelList = criteria.list();
		EventsOrganizerDTO hoteldto = null;
		for (EventOrganizerEntity hotelEntity : hotelList) {
			hoteldto = new EventsOrganizerDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
		}
		return hoteldto;
	}

	@Transactional
	public List<EventWhiteListDTO> getEventWhiteListCategoryUsrs(Integer userId) {
		
		List<EventWhiteListDTO> whiteUsersList = new ArrayList<EventWhiteListDTO>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<EventWhiteListEntity> whiteListCustmrs = criteria.list();
		for (EventWhiteListEntity whiteListUsersEntity : whiteListCustmrs) {
			EventWhiteListDTO whteLstUsr = new EventWhiteListDTO();
			BeanUtils.copyProperties(whiteListUsersEntity, whteLstUsr);
			whiteUsersList.add(whteLstUsr);
		}
		
		return whiteUsersList;
	}

	@Transactional
	public EventsOrganizerDTO getHotelDetails(Integer orgId) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventOrganizerEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", orgId));
		EventOrganizerEntity orgEntity = (EventOrganizerEntity) session.load(EventOrganizerEntity.class,
				orgId);
		EventsOrganizerDTO orgInfo = new EventsOrganizerDTO();
		BeanUtils.copyProperties(orgEntity, orgInfo);
		
		return orgInfo;
	}

	@Transactional
	public List<EventBlackListDTO> getEventBlackListCategoryUsrs(Integer userId) {
		
		List<EventBlackListDTO> blackUsersList = new ArrayList<EventBlackListDTO>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<EventBlackListEntity> whiteListCustmrs = criteria.list();
		for (EventBlackListEntity blackListUsersEntity : whiteListCustmrs) {
			EventBlackListDTO blackUsr = new EventBlackListDTO();
			BeanUtils.copyProperties(blackListUsersEntity, blackUsr);
			blackUsersList.add(blackUsr);
		}
		
		return blackUsersList;
	}

	@Transactional
	public List<EventWhiteListDTO> getAllWhiteLstUsrs(Integer orgId) {
	
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("orgId", orgId));
		List<EventWhiteListDTO> whiteUsrs = new ArrayList<EventWhiteListDTO>();
		EventWhiteListDTO whiteUsr = null;
		List<EventWhiteListEntity> whiteListUsrs = criteria.list();
		for (EventWhiteListEntity whiteListUsersEntity : whiteListUsrs) {
			whiteUsr = new EventWhiteListDTO();
			BeanUtils.copyProperties(whiteListUsersEntity, whiteUsr);
			whiteUsrs.add(whiteUsr);
		}
		
		return whiteUsrs;
	}

	@Transactional
	public UserDTO getUsrDetailsByID(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<UserEntity> list = criteria.list();
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(list.get(0), userDTO);
		return userDTO;
	}

	@Transactional
	public EventGuestUserDTO getGuestUsrDetailsByID(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.add(Restrictions.eq("guestUserId", userId));
		List<EventGuestUserEntity> list = criteria.list();
		EventGuestUserDTO guestUserDTO = new EventGuestUserDTO();
		BeanUtils.copyProperties(list.get(0), guestUserDTO);
		return guestUserDTO;
	}

	@Transactional
	public UserDTO getUserDetail(String userName) {
		Integer userId = getUserIdByUserName(userName);
		UserDTO userDTO = null;
		if(userId!=0){
			Session session = sessionFactory.getCurrentSession();
			UserEntity userEntity = (UserEntity) session.load(UserEntity.class,
					userId);
			userDTO=new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			
		}
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

	@Transactional
	public EventWhiteListDTO getWhiteLstCustmr(Integer userId, Integer orgId) {
		EventWhiteListDTO whtUsr = new EventWhiteListDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));// hotelID
		criteria.add(Restrictions.eq("orgId", orgId));
		List<EventWhiteListEntity> whiteCustmr = criteria.list();
		if (whiteCustmr.size() > 0)
			BeanUtils.copyProperties(whiteCustmr.get(0), whtUsr);
		return whtUsr;
	}

	@Transactional
	public EventGuestUserDTO getGuestUserDetail(String userName) {
		Integer userId = getGuestUserIdByUserName(userName);
		EventGuestUserDTO userDTO = null;;
		if(userId!=0){
			Session session = sessionFactory.getCurrentSession();
			EventGuestUserEntity userEntity = (EventGuestUserEntity) session.load(
					EventGuestUserEntity.class, userId);
			 userDTO = new EventGuestUserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
		}
		
		
		return userDTO;
	}

	@SuppressWarnings({ "unchecked" })
	private Integer getGuestUserIdByUserName(String UserName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.add(Restrictions.eq("userName", UserName));
		List<EventGuestUserEntity> list = criteria.list();
		Integer userId = 0;
		for (EventGuestUserEntity eventGuestUserEntity : list) {
			userId = eventGuestUserEntity.getGuestUserId();
		}
		
		return userId;
	}

	@Transactional
	public EventWhiteListDTO getWhiteLstGuestCustmr(Integer userId, Integer orgId) {
		EventWhiteListDTO whtUsr = new EventWhiteListDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("guestId", userId));
		criteria.add(Restrictions.eq("orgId", orgId));
		List<EventWhiteListEntity> whiteCustmr = criteria.list();
		if (whiteCustmr.size() > 0)
			BeanUtils.copyProperties(whiteCustmr.get(0), whtUsr);
		return whtUsr;
	
	}

	@Transactional
	public boolean isRemoveWhteCustmrFrmLst(Integer userId) {
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<EventWhiteListEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			EventWhiteListEntity whiteListUsersEntity = whtLst.get(0);
			session.delete(whiteListUsersEntity);
			isDelete = true;
		}
		return isDelete;
	}

	public boolean isRemoveWhteGuestCustmrFrmLst(Integer userId) {
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("guestId", userId));
		List<EventWhiteListEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			EventWhiteListEntity whiteListUsersEntity = whtLst.get(0);
			session.delete(whiteListUsersEntity);
			isDelete = true;
		}
		return isDelete;
	}

}
