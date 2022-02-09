package com.events.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.UserDTO;
import com.cushina.model.pojo.UserEntity;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.model.dataaccess.EventBookingDao;
import com.events.model.pojo.EventGuestUserEntity;
import com.events.model.pojo.EventOrganizerEntity;
import com.events.model.pojo.EventScheduleEntity;
import com.events.model.pojo.EventUserEntity;
import com.events.model.pojo.EventsEntity;
import com.events.model.pojo.EventsGuideEntity;
import com.events.model.pojo.EventsReservationEntity;

@Repository
public class EventBookingDaoImpl implements EventBookingDao {
	private final Logger logger = Logger.getLogger(EventBookingDaoImpl.class
			.getName());

	@Autowired
	private SessionFactory sessionFactory;

	public List<EventsReservationDTO> getEventBookingHistoryDetails(
			Integer userId, Integer eventOrgID) {
		List<EventsReservationDTO> historyMap = new ArrayList<EventsReservationDTO>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);

		if (userId != 0 || userId != null) {
			criteria.add(Restrictions.eq("userID", new Integer(userId)));
			criteria.add(Restrictions.ne("status", "delete"));
		}
		List<EventsReservationEntity> list = criteria.list();
		for (EventsReservationEntity eventsReservationEntity : list) {
			EventsReservationDTO dto = new EventsReservationDTO();
			BeanUtils.copyProperties(eventsReservationEntity, dto);
			historyMap.add(dto);
		}
		return historyMap;
	}

	public EventScheduleDTO getEventBookingHistoryDetails(
			Integer eventScheduleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);

		criteria.add(Restrictions.eq("scheduleId", eventScheduleId));
		List<EventScheduleEntity> list = criteria.list();
		EventScheduleDTO eventScheduleDTO = null;
		for (EventScheduleEntity eventScheduleEntity : list) {
			eventScheduleDTO = new EventScheduleDTO();
			BeanUtils.copyProperties(eventScheduleEntity, eventScheduleDTO);

		}
		return eventScheduleDTO;
	}

	public Integer sameEventReservtn(Integer bookingID) {
		Session session = sessionFactory.getCurrentSession();
		EventsReservationEntity histryRecord = (EventsReservationEntity) session
				.load(EventsReservationEntity.class, bookingID);
		Integer eventOrgID = histryRecord.getEventOrgId();
		return eventOrgID;
	}

	public Long cancelEventReservation(Integer bookingID) {

		logger.info("cancelReservation daoImpl : start");

		Session session = sessionFactory.getCurrentSession();
		EventsReservationEntity bkngHistry = (EventsReservationEntity) session
				.load(EventsReservationEntity.class, bookingID);
		bkngHistry.setStatus("cancel");
		session.update(bkngHistry);
		Integer scheduleId = bkngHistry.getEventScheduleId();
		Integer availableSeats = bkngHistry.getNoOfPeople();
		boolean update = getUpdateAvailableSeats(scheduleId,
				Long.valueOf(availableSeats));

		logger.info("cancelReservation daoImpl : end" + update);
		return bkngHistry.getReferenceNumber();

	}

	private boolean getUpdateAvailableSeats(Integer scheduleId,
			Long availableSeats) {
		Session session = sessionFactory.getCurrentSession();
		EventScheduleEntity bkngHistry = (EventScheduleEntity) session.load(
				EventScheduleEntity.class, scheduleId);
		Long count = availableSeats + bkngHistry.getAvailableSeats();
		bkngHistry.setAvailableSeats(count);
		session.update(bkngHistry);
		return true;
	}

	public Long deleteEventReservation(Integer bookingID) {
		logger.info("deleteEventReservation daoImpl : start");

		Session session = sessionFactory.getCurrentSession();
		EventsReservationEntity bkngHistry = (EventsReservationEntity) session
				.load(EventsReservationEntity.class, bookingID);
		bkngHistry.setStatus("delete");
		session.update(bkngHistry);

		logger.info("deleteEventReservation daoImpl : end");
		return bkngHistry.getReferenceNumber();

	}

	public List<UserDTO> getCustomersList(Integer orgId) {
		logger.info("getCustomersList daoImpl : start");
		List<UserDTO> custmrLst = new ArrayList<UserDTO>();
		UserDTO custmr = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventUserEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", orgId));
		List<EventUserEntity> eventUserList = criteria.list();
		System.out.println("criterialist :::" + eventUserList);
		Integer userId = null;
		for (EventUserEntity eventUserEntity : eventUserList) {
			userId = eventUserEntity.getUserId();
			System.out.println("userid :::::" + userId);
			criteria = session.createCriteria(UserEntity.class);
			criteria.add(Restrictions.eq("userId", userId));
			List<UserEntity> custmrList = criteria.list();
			for (UserEntity custmrEntity : custmrList) {
				custmr = new UserDTO();
				BeanUtils.copyProperties(custmrEntity, custmr);
				custmrLst.add(custmr);
			}
			logger.info("customer list in daoImpl :" + custmrLst);
		}

		logger.info("customer list in daoImpl :" + custmrLst);
		logger.info("getCustomersList daoImpl : end");
		return custmrLst;
	}

	public List<EventsReservationDTO> getCustmrsHistry(Integer orgId) {
		logger.info("getCustmrsHistry daoImpl : start");
		List<EventsReservationDTO> histryDTOLst = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO historyDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", orgId));
		List<EventsReservationEntity> histryLst = criteria.list();
		for (EventsReservationEntity reservHistoryEntity : histryLst) {
			historyDTO = new EventsReservationDTO();
			BeanUtils.copyProperties(reservHistoryEntity, historyDTO);
			histryDTOLst.add(historyDTO);
		}
		logger.info("getCustmrsHistry daoImpl : end");
		return histryDTOLst;
	}

	public EventScheduleDTO getEventScheduleDates(Integer scheduleId) {

		EventScheduleDTO scheduleDto = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("scheduleId", scheduleId));
		List<EventScheduleEntity> scheduleLst = criteria.list();
		for (EventScheduleEntity eventScheduleEntity : scheduleLst) {
			scheduleDto = new EventScheduleDTO();
			BeanUtils.copyProperties(eventScheduleEntity, scheduleDto);
		}
		return scheduleDto;
	}

	public List<EventsReservationDTO> getCustomerReservtnHistryRecords(
			Integer userId) {
		logger.info("getCustmrReservedHistry daoIml : start");
		List<EventsReservationDTO> histryDTOList = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO hstryDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("userID", userId));
		List<EventsReservationEntity> histryList = criteria.list();
		for (EventsReservationEntity bookingHistoryEntity : histryList) {
			hstryDTO = new EventsReservationDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, hstryDTO);
			histryDTOList.add(hstryDTO);
		}
		logger.info("getCustmrReservedHistry daoIml : end");
		return histryDTOList;
	}

	public List<EventsDTO> getCategoryList(Integer eventOrgID) {
		List<EventsDTO> categoryList = new ArrayList<EventsDTO>();
		Session session = sessionFactory.getCurrentSession();
		EventsDTO categoryDTO = null;
		Query query = session
				.createQuery("SELECT DISTINCT C.eventId,C.eventName FROM EventsEntity C WHERE C.eventOrgId="
						+ eventOrgID);
		List<Object> categoryNames = query.list();
		for (Object obj : categoryNames) {
			Object[] data = (Object[]) obj;
			categoryDTO = new EventsDTO();
			categoryDTO.setEventId(Integer.parseInt(data[0].toString()));
			categoryDTO.setEventName(data[1].toString());
			categoryList.add(categoryDTO);
		}
		return categoryList;
	}

	public List<EventsGuideDTO> getRoomsAvailList(Integer eventOrgID,
			Integer catVal) {
		logger.info("getRoomsAvailList daoImpl : start");
		List<EventsGuideDTO> categoryList = new ArrayList<EventsGuideDTO>();
		Session session = sessionFactory.getCurrentSession();
		EventsGuideDTO categoryDTO = null;
		Criteria criteria = session.createCriteria(EventsGuideEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", eventOrgID));
		criteria.add(Restrictions.eq("eventId", catVal));
		List<EventsGuideEntity> histryList = criteria.list();
		for (EventsGuideEntity bookingHistoryEntity : histryList) {
			categoryDTO = new EventsGuideDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, categoryDTO);
			categoryList.add(categoryDTO);
		}
		return categoryList;
	}

	public Boolean addRoomTOWhteLst(Integer eventId, String guideName,
			Integer eventOrgID) {
		logger.info("addRoomTOWhteLst daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsGuideEntity.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		criteria.add(Restrictions.eq("guideName", guideName));
		criteria.add(Restrictions.eq("eventOrgId", eventOrgID));
		List<EventsGuideEntity> roomList = criteria.list();
		EventsGuideEntity roomInfoEntity = roomList.get(0);
		roomInfoEntity.setEventId(eventId);
		roomInfoEntity.setGuideName(guideName);
		roomInfoEntity.setEventOrgId(eventOrgID);
		roomInfoEntity.setWhiteAndBlack(new Integer(1));
		session.save(roomInfoEntity);
		logger.info("addRoomTOWhteLst daoImpl : end");
		return true;
	}

	public List<EventsReservationDTO> getCustomerReservationHistryRecords(
			Integer orgId) {
		logger.info("getCustmrsHistry daoImpl : start");
		List<EventsReservationDTO> histryDTOLst = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO historyDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", orgId));
		List<EventsReservationEntity> histryLst = criteria.list();
		for (EventsReservationEntity reservHistoryEntity : histryLst) {
			historyDTO = new EventsReservationDTO();
			BeanUtils.copyProperties(reservHistoryEntity, historyDTO);
			Integer userId = historyDTO.getUserID();
			criteria = session.createCriteria(UserEntity.class);
			criteria.add(Restrictions.eq("userId", userId));
			UserEntity userentity = (UserEntity) criteria.list().get(0);
			historyDTO.setUserName(userentity.getUserName());
			historyDTO.setEmail(userentity.getEmail());
			historyDTO.setPhoneNumber(userentity.getPhoneNumber());
			histryDTOLst.add(historyDTO);
		}
		logger.info("getCustmrsHistry daoImpl : end");
		return histryDTOLst;
	}

	public List<UserDTO> getUserDetail(Integer orgId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", orgId));
		List<EventsReservationEntity> histryLst = criteria.list();
		List<UserDTO> userlist = new ArrayList<UserDTO>();
		UserDTO userDto = null;
		for (EventsReservationEntity reservHistoryEntity : histryLst) {
			Integer userId = reservHistoryEntity.getUserID();
			criteria = session.createCriteria(UserEntity.class);
			criteria.add(Restrictions.eq("userId", userId));
			List<UserEntity> userLst = criteria.list();
			for (UserEntity userEntity : userLst) {
				userDto = new UserDTO();
				BeanUtils.copyProperties(userEntity, userDto);
				userlist.add(userDto);
			}
		}
		return userlist;
	}

	public List<EventGuestUserDTO> getGuestUserDetailsByID(Integer guestUserId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.add(Restrictions.eq("guestUserId", guestUserId));
		List<EventGuestUserEntity> histryLst = criteria.list();
		List<EventGuestUserDTO> guestlist = new ArrayList<EventGuestUserDTO>();
		EventGuestUserDTO guestDto = null;
		for (EventGuestUserEntity eventGuestUserEntity : histryLst) {
			guestDto = new EventGuestUserDTO();
			BeanUtils.copyProperties(eventGuestUserEntity, guestDto);
			guestlist.add(guestDto);
		}
		return guestlist;
	}

	public EventGuestUserDTO getGuestUserDetail(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.add(Restrictions.eq("userName", userName));
		EventGuestUserEntity histryLst = (EventGuestUserEntity) criteria.list()
				.get(0);
		EventGuestUserDTO guestDto = new EventGuestUserDTO();
		BeanUtils.copyProperties(histryLst, guestDto);
		return guestDto;
	}

	public List<EventsReservationDTO> getCustomerReservtnHistryGuestRecords(
			Integer guestUserId) {
		logger.info("getCustmrReservedHistry daoIml : start");
		List<EventsReservationDTO> histryDTOList = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO hstryDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("guestId", guestUserId));
		List<EventsReservationEntity> histryList = criteria.list();
		for (EventsReservationEntity bookingHistoryEntity : histryList) {
			hstryDTO = new EventsReservationDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, hstryDTO);
			histryDTOList.add(hstryDTO);
		}
		logger.info("getCustmrReservedHistry daoIml : end");
		return histryDTOList;
	}

	public EventsDTO getEventName(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsEntity.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		EventsEntity events = (EventsEntity) criteria.list().get(0);
		EventsDTO dto = new EventsDTO();
		BeanUtils.copyProperties(events, dto);
		return dto;
	}

}
