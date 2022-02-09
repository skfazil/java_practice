package com.events.model.dataaccess.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.UserDTO;
import com.cushina.model.pojo.UserEntity;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.model.dataaccess.EventUserEmailVisibleDao;
import com.events.model.pojo.EventOrganizerEntity;
import com.events.model.pojo.EventUserEntity;
import com.events.model.pojo.EventsReservationEntity;
import com.events.web.bean.EventsOrganizerBean;
@Repository
public class EventUserEmailVisibleDaoImpl implements EventUserEmailVisibleDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<EventsOrganizerBean> getEmailListDetails(Integer orgID, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "from EventsReservationEntity " + "where userId=" + userId
				+ "" + "and emailShare='Y' and status='active'";
		List<EventsReservationEntity> list = session.createQuery(query).list();
		List<EventsOrganizerBean> dtoList = new ArrayList<EventsOrganizerBean>();
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		EventsOrganizerBean hotelBean = null;
		for (EventsReservationEntity eventReservEntity : list) {
			hotelBean = new EventsOrganizerBean();
			Integer reservID2 = eventReservEntity.getEventOrgId();
			Date bookingDate = eventReservEntity.getReservedDate();
			String strtDate = destFrmt.format(bookingDate);
			EventsOrganizerDTO hotelInfo = getHotelInfo(reservID2);
			BeanUtils.copyProperties(hotelInfo, hotelBean);
			hotelBean.setStrtDate(strtDate);
			dtoList.add(hotelBean);
		}
		return dtoList;
	}
	
	@SuppressWarnings("unchecked")
	public EventsOrganizerDTO getHotelInfo(Integer reservID2) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventOrganizerEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", reservID2));
		List<EventOrganizerEntity> hotelList = criteria.list();
		EventsOrganizerDTO hoteldto = null;
		for (EventOrganizerEntity hotelEntity : hotelList) {
			hoteldto = new EventsOrganizerDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
		}
		return hoteldto;
	}

	@Transactional
	public void getEventOrgUpdateEmail(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "update EventsReservationEntity set emailShare='N' where userId=:userId";
		session.createQuery(query).setInteger("userId", userId).executeUpdate();
	}

	@Transactional
	public List<UserDTO> getCustomersList(Integer orgId) {
		List<UserDTO> custmrLst = new ArrayList<UserDTO>();
		UserDTO custmr = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventUserEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", orgId));
		List<EventUserEntity> eventUserList = criteria.list();
		System.out.println("criterialist :::"+eventUserList);
		Integer userId=null;
		for (EventUserEntity eventUserEntity : eventUserList) {
			 userId=eventUserEntity.getUserId();
			 System.out.println("userid :::::"+userId);
			 criteria = session.createCriteria(UserEntity.class);
			criteria.add(Restrictions.eq("userId", userId));
			List<UserEntity> custmrList = criteria.list();
			for (UserEntity custmrEntity : custmrList) {
				custmr = new UserDTO();
				BeanUtils.copyProperties(custmrEntity, custmr);
				custmrLst.add(custmr);
			}
			System.out.println("customer list in daoImpl :" + custmrLst);
		}
		return custmrLst;
	}

}
