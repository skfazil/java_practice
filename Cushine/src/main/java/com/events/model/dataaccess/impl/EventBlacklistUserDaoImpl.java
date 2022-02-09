package com.events.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.model.dataaccess.EventBlacklistUserDao;
import com.events.model.pojo.EventBlackListEntity;
import com.events.model.pojo.EventOrganizerEntity;

@Repository
public class EventBlacklistUserDaoImpl implements EventBlacklistUserDao{

	@Autowired
	private SessionFactory sessionFactory;

	
	public List<EventBlackListDTO> getEventBlackListStartDate(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<EventBlackListEntity> blackListUsersEntities = criteria.list();
		List<EventBlackListDTO> blackListUsersDTOs = new ArrayList<EventBlackListDTO>();
		EventBlackListDTO blackListUsersDTO = null;
		for (EventBlackListEntity blackListUsersEntity : blackListUsersEntities) {
			blackListUsersDTO = new EventBlackListDTO();
			BeanUtils.copyProperties(blackListUsersEntity, blackListUsersDTO);
			blackListUsersDTOs.add(blackListUsersDTO);
		}
		return blackListUsersDTOs;
	}

	
	public EventsOrganizerDTO getEventUserBlackListHotelDetail(Integer orgID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventOrganizerEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", orgID));
		List<EventOrganizerEntity> hotelList = criteria.list();
		EventsOrganizerDTO hoteldto = null;
		for (EventOrganizerEntity hotelEntity : hotelList) {
			hoteldto = new EventsOrganizerDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
		}
		return hoteldto;
	}


	public List<EventBlackListDTO> getAllBlackLstUsrs(Integer orgId) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("orgId", orgId));
		List<EventBlackListDTO> blackUsrs = new ArrayList<EventBlackListDTO>();
		EventBlackListDTO blackUsr = null;
		List<EventBlackListEntity> blackListUsrs = criteria.list();
		for (EventBlackListEntity blackListUsersEntity : blackListUsrs) {
			blackUsr = new EventBlackListDTO();
			BeanUtils.copyProperties(blackListUsersEntity, blackUsr);
			blackUsrs.add(blackUsr);
		}
		
		return blackUsrs;
	}

	
	public EventBlackListDTO getBlackLstCustmr(Integer userId, Integer orgId) {
		EventBlackListDTO blckUsr = new EventBlackListDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("orgId", orgId));
		List<EventBlackListEntity> blckCustmr = criteria.list();
		if (blckCustmr.size() > 0)
			BeanUtils.copyProperties(blckCustmr.get(0), blckUsr);
		return blckUsr;
	}


	@SuppressWarnings("unchecked")
	public EventBlackListDTO getBlackLstGuestCustmr(Integer userId,Integer orgId) {
		EventBlackListDTO blckUsr = new EventBlackListDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("guestId", userId));
		criteria.add(Restrictions.eq("orgId", orgId));
		List<EventBlackListEntity> blackCustmr = criteria.list();
		System.out.println("list sizze"+blackCustmr.size());
		if (blackCustmr.size() > 0)
			BeanUtils.copyProperties(blackCustmr.get(0), blckUsr);
		System.out.println("list sizze"+blckUsr);
		return blckUsr;
	}
	public boolean isRemoveBlackCustmrFrmLst(Integer userId) {
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<EventBlackListEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			EventBlackListEntity blckListUsersEntity = whtLst.get(0);
			session.delete(blckListUsersEntity);
			isDelete = true;
		}
		return isDelete;

	}

	@SuppressWarnings("unchecked")
	public boolean isRemoveBlackGuestCustmrFrmLst(Integer userId) {
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("guestId", userId));
		List<EventBlackListEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			EventBlackListEntity blackListUsersEntity = whtLst.get(0);
			session.delete(blackListUsersEntity);
			isDelete = true;
		}
		return isDelete;
	}


}
