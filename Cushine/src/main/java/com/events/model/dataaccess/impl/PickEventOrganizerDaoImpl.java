package com.events.model.dataaccess.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.UserDTO;
import com.cushina.model.pojo.UserEntity;
import com.events.common.dto.EventBlackListDTO;
import com.events.common.dto.EventGuestUserDTO;
import com.events.common.dto.EventScheduleDTO;
import com.events.common.dto.EventWhiteListDTO;
import com.events.common.dto.EventsDTO;
import com.events.common.dto.EventsGuideDTO;
import com.events.common.dto.EventsOrganizerDTO;
import com.events.common.dto.EventsReservationDTO;
import com.events.model.dataaccess.PickEventOrganizerDao;
import com.events.model.pojo.EventBlackListEntity;
import com.events.model.pojo.EventGuestUserEntity;
import com.events.model.pojo.EventOrganizerEntity;
import com.events.model.pojo.EventScheduleEntity;
import com.events.model.pojo.EventWhiteListEntity;
import com.events.model.pojo.EventsEntity;
import com.events.model.pojo.EventsGuideEntity;
import com.events.model.pojo.EventsReservationEntity;
import com.events.web.bean.EventsOrganizerBean;
import com.massage.common.dto.MassageGuestUserDTO;
import com.massage.common.dto.MassagePersonDTO;
import com.massage.common.dto.MassageReservationDTO;
import com.massage.common.dto.MassageServiceScheduleDTO;
import com.massage.model.pojo.MassageGuestUserEntity;
import com.massage.model.pojo.MassagePersonEntity;
import com.massage.model.pojo.MassageReservationEntity;
import com.massage.model.pojo.MassageServiceScheduleEntity;

@Repository
public class PickEventOrganizerDaoImpl implements PickEventOrganizerDao {

	private Logger logger = Logger.getLogger(PickEventOrganizerDaoImpl.class
			.getName());

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<EventsOrganizerDTO> getEventOrganizationDetails(
			String eventOrgName) {
		List<EventsOrganizerDTO> eventsOrganizerDTOs = new ArrayList<EventsOrganizerDTO>();
		Session session = sessionFactory.getCurrentSession();
		Criteria createCriteria = session
				.createCriteria(EventOrganizerEntity.class);
		createCriteria.add(Restrictions.eq("eventOrgName", eventOrgName));
		List<EventOrganizerEntity> list = createCriteria.list();
		for (EventOrganizerEntity eventOrganizerEntity : list) {
			EventsOrganizerDTO eventsOrganizerDTO = new EventsOrganizerDTO();
			BeanUtils.copyProperties(eventOrganizerEntity, eventsOrganizerDTO);
			eventsOrganizerDTOs.add(eventsOrganizerDTO);

		}
		return eventsOrganizerDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<EventsOrganizerBean> getEventOrganizationDetailsById(
			Integer eventOrgId) {
		List<EventsOrganizerBean> eventsOrganizerDTOs = new ArrayList<EventsOrganizerBean>();
		Session session = sessionFactory.getCurrentSession();
		Criteria createCriteria = session
				.createCriteria(EventOrganizerEntity.class);
		createCriteria.add(Restrictions.eq("eventOrgId", eventOrgId));
		List<EventOrganizerEntity> list = createCriteria.list();
		for (EventOrganizerEntity eventOrganizerEntity : list) {
			EventsOrganizerBean eventsOrganizerDTO = new EventsOrganizerBean();
			BeanUtils.copyProperties(eventOrganizerEntity, eventsOrganizerDTO);
			eventsOrganizerDTOs.add(eventsOrganizerDTO);
		}
		return eventsOrganizerDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<EventScheduleDTO> getEventsDatesList(Integer eventId) {
		logger.info("getEventsDatesList daoImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		String previousDt = format.format(previousDate);
		List<EventScheduleDTO> evntDtsLst = new ArrayList<EventScheduleDTO>();
		String evntEndDate = getEndDateOfEvent(eventId);
		Session session = sessionFactory.getCurrentSession();
		/*
		 * Criteria criteria = session.createCriteria(EventsEntity.class);
		 * criteria.add(Restrictions.eq("eventOrgId", eventId));
		 * List<EventsEntity> list = criteria.list();
		 */

		String SQL_QUERY = "SELECT SUM(availableSeats),DATE,SUM(maxCapacity),eventId FROM EventScheduleEntity  WHERE  eventId = "
				+ eventId
				+ " AND DATE  BETWEEN '"
				+ previousDt
				+ "' AND '"
				+ evntEndDate + "' GROUP BY DATE ";
		Query query = session.createQuery(SQL_QUERY);
		List<Object[]> evntScheduleList = query.list();
		for (Object[] objects : evntScheduleList) {
			EventScheduleDTO scheduleDt = new EventScheduleDTO();
			scheduleDt.setAvailableSeats((Long) objects[0]);
			scheduleDt.setDate((Date) objects[1]);
			scheduleDt.setMaxCapacity((Long) objects[2]);
			scheduleDt.setEventId((Integer) objects[3]);
			evntDtsLst.add(scheduleDt);
		}
		logger.info("getEventsDatesList daoImpl : end");
		return evntDtsLst;
	}

	@SuppressWarnings("unchecked")
	public List<EventScheduleDTO> getStrtTmeAndEndTme(Integer eventId,
			String currDt) {
		logger.info("getStrtTmeAndEndTme daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		List<EventScheduleDTO> scheduleTimeList = new ArrayList<EventScheduleDTO>();
		EventScheduleDTO schedleTme = null;
		String query = "select date,startTime,endTime from EventScheduleEntity where eventId="
				+ eventId + " and date=' " + currDt + " ' ";
		Query createQuery = session.createQuery(query);
		List<Object[]> list = createQuery.list();
		for (Object[] objects : list) {
			schedleTme = new EventScheduleDTO();
			schedleTme.setDate((Date) objects[0]);
			schedleTme.setStartTime((Date) objects[1]);
			schedleTme.setEndTime((Date) objects[2]);
			scheduleTimeList.add(schedleTme);
		}
		logger.info("getStrtTmeAndEndTme daoImpl : end");
		return scheduleTimeList;
	}

	@SuppressWarnings("unchecked")
	private String getEndDateOfEvent(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "SELECT  date FROM EventScheduleEntity where eventId="
				+ eventId + "";
		Query createQuery = session.createQuery(query);
		List<Object> list = createQuery.list();
		String lastDate = null;
		if (list != null && !list.isEmpty()) {
			Object object = list.get(list.size() - 1);
			lastDate = String.valueOf(object);
		}
		return lastDate;
	}

	@SuppressWarnings("unchecked")
	public List<EventsDTO> getAllTours(Integer eventId) {
		logger.info("getAllTours daoImpl : start");
		List<EventsDTO> toursList = new ArrayList<EventsDTO>();
		EventsDTO tour = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", eventId));
		List<EventsEntity> toursLst = criteria.list();
		for (EventsEntity eventsEntity : toursLst) {
			tour = new EventsDTO();
			BeanUtils.copyProperties(eventsEntity, tour);
			toursList.add(tour);
		}
		logger.info("getAllTours daoImpl : end");
		return toursList;
	}

	@SuppressWarnings("unchecked")
	public List<EventsGuideDTO> getGuideList(Integer tourId, Integer eventOrgId) {
		logger.info("getGuideList daoImpl : start");
		List<EventsGuideDTO> guidesList = new ArrayList<EventsGuideDTO>();
		EventsGuideDTO guide = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsGuideEntity.class);
		criteria.add(Restrictions.eq("eventOrgId", eventOrgId));
		criteria.add(Restrictions.eq("eventId", tourId));
		List<EventsGuideEntity> guideLst = criteria.list();
		for (EventsGuideEntity eventsGuideEntity : guideLst) {
			guide = new EventsGuideDTO();
			BeanUtils.copyProperties(eventsGuideEntity, guide);
			guidesList.add(guide);
		}
		logger.info("getGuideList daoImpl : end");
		return guidesList;
	}

	@SuppressWarnings("unchecked")
	public List<EventScheduleDTO> getCarouselDatesList(Integer eventId) {
		logger.info("getEventsDatesList daoImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		String previousDt = format.format(previousDate);
		List<EventScheduleDTO> evntDtsLst = new ArrayList<EventScheduleDTO>();
		String evntEndDate = getEndDateOfEvent(eventId);
		Session session = sessionFactory.getCurrentSession();
		/*
		 * Criteria criteria = session.createCriteria(EventsEntity.class);
		 * criteria.add(Restrictions.eq("eventOrgId", eventId));
		 * List<EventsEntity> list = criteria.list();
		 */

		String SQL_QUERY = "SELECT SUM(availableSeats),DATE,SUM(maxCapacity),eventId FROM EventScheduleEntity  WHERE  eventId = "
				+ eventId
				+ " AND DATE  BETWEEN '"
				+ previousDt
				+ "' AND '"
				+ evntEndDate + "' GROUP BY DATE ";
		Query query = session.createQuery(SQL_QUERY);
		List<Object[]> evntScheduleList = query.list();
		for (Object[] objects : evntScheduleList) {
			EventScheduleDTO scheduleDt = new EventScheduleDTO();
			scheduleDt.setAvailableSeats((Long) objects[0]);
			scheduleDt.setDate((Date) objects[1]);
			scheduleDt.setMaxCapacity((Long) objects[2]);
			scheduleDt.setEventId((Integer) objects[3]);
			evntDtsLst.add(scheduleDt);
		}
		logger.info("getEventsDatesList daoImpl : end");
		return evntDtsLst;
	}

	@SuppressWarnings("unchecked")
	public List<EventScheduleDTO> getEventSchedulesBsdOnDt(Date date,
			Integer eventOrgId, Integer eventId) {
		logger.info("getEventSchedulesBsdOnDt daoImpl : start");
		List<EventScheduleDTO> scheduleLst = new ArrayList<EventScheduleDTO>();
		EventScheduleDTO schedule = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		criteria.add(Restrictions.eq("eventOrgId", eventOrgId));
		criteria.add(Restrictions.eq("date", date));
		List<EventScheduleEntity> list = criteria.list();
		for (EventScheduleEntity eventScheduleEntity : list) {
			schedule = new EventScheduleDTO();
			BeanUtils.copyProperties(eventScheduleEntity, schedule);
			scheduleLst.add(schedule);
		}
		logger.info("getEventSchedulesBsdOnDt daoImpl : end");
		return scheduleLst;
	}

	public Map<EventScheduleDTO, List<EventsReservationDTO>> getEventReservedUsersMap(
			Integer scheduleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("scheduleId", scheduleId));
		List<EventScheduleEntity> list = criteria.list();
		EventScheduleDTO dto = new EventScheduleDTO();
		Map<EventScheduleDTO, List<EventsReservationDTO>> map = new HashMap<EventScheduleDTO, List<EventsReservationDTO>>();
		for (EventScheduleEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		EventsGuideDTO guideDto = getGuideById(dto.getGuideId());
		EventsDTO eventDto = getEventById(dto.getEventId());
		dto.setGuideName(guideDto.getGuideName());
		dto.setEventName(eventDto.getEventName());
		List<EventsReservationDTO> reserveList = getReservedListByScheduleId(scheduleId);
		map.put(dto, reserveList);

		return map;
	}

	public EventsDTO getEventById(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsEntity.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		List<EventsEntity> list = criteria.list();
		EventsDTO dto = new EventsDTO();
		for (EventsEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	public List<EventsReservationDTO> getReservedListByScheduleId(
			Integer scheduleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		List<EventsReservationEntity> list = criteria.list();
		List<EventsReservationDTO> listDto = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO dto = null;
		for (EventsReservationEntity entity : list) {
			dto = new EventsReservationDTO();
			BeanUtils.copyProperties(entity, dto);

			if (dto.getGuestId() != null) {
				EventGuestUserDTO guestDto = getGuestById(dto.getGuestId());
				dto.setUserName(guestDto.getUserName());
				dto.setEmail(guestDto.getEmail());
				dto.setPhoneNumber(guestDto.getPhoneNumber());
			} else {
				UserDTO usrDto = getUserById(dto.getUserID());
				dto.setUserName(usrDto.getUserName());
				dto.setEmail(usrDto.getEmail());
				dto.setPhoneNumber(usrDto.getPhoneNumber());
				dto.setNotificationPeriod(usrDto.getNotificationDuration());
			}
			listDto.add(dto);
		}
		return listDto;
	}
	
	

	private List<EventsReservationDTO> getBlueReservedList(Integer scheduleId,
			Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		criteria.add(Restrictions.eq("userID", userId));
		List<EventsReservationEntity> list = criteria.list();
		List<EventsReservationDTO> listDto = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO dto = null;
		for (EventsReservationEntity entity : list) {
			dto = new EventsReservationDTO();
			BeanUtils.copyProperties(entity, dto);

			if (dto.getGuestId() != null) {
				EventGuestUserDTO guestDto = getGuestById(dto.getGuestId());
				dto.setUserName(guestDto.getUserName());
				dto.setEmail(guestDto.getEmail());
				dto.setPhoneNumber(guestDto.getPhoneNumber());
			} else {
				UserDTO usrDto = getUserById(dto.getUserID());
				dto.setUserName(usrDto.getUserName());
				dto.setEmail(usrDto.getEmail());
				dto.setPhoneNumber(usrDto.getPhoneNumber());
			}
			listDto.add(dto);
		}
		return listDto;
	}

	private UserDTO getUserById(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<UserEntity> list = criteria.list();
		UserDTO dto = new UserDTO();
		for (UserEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	private EventGuestUserDTO getGuestById(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.add(Restrictions.eq("guestUserId", guestId));
		List<EventGuestUserEntity> list = criteria.list();
		EventGuestUserDTO dto = new EventGuestUserDTO();
		for (EventGuestUserEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}

		return dto;
	}

	public EventsGuideDTO getGuideById(Integer guideId) {
		Session session = sessionFactory.getCurrentSession();

		EventsGuideEntity entity = (EventsGuideEntity) session.load(
				EventsGuideEntity.class, guideId);
		EventsGuideDTO dto = new EventsGuideDTO();
		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

	private List<EventScheduleDTO> getScheduleByDate(Date date,
			Integer eventId, Integer guideId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("date", date));
		if (eventId != 0) {
			criteria.add(Restrictions.eq("eventId", eventId));
		}

		if (guideId != 0) {
			criteria.add(Restrictions.eq("guideId", guideId));
		}

		List<EventScheduleEntity> list = criteria.list();
		List<EventScheduleDTO> listDto = new ArrayList<EventScheduleDTO>();
		EventScheduleDTO dto = null;
		for (EventScheduleEntity entity : list) {
			dto = new EventScheduleDTO();
			BeanUtils.copyProperties(entity, dto);
			Date strtTime = dto.getStartTime();
			Date endTime = dto.getEndTime();
			String[] sArr = getStrtEndTimeString(strtTime, endTime);
			dto.setEvntStrtTme(sArr[0]);
			dto.setEvntEndTme(sArr[1]);
			listDto.add(dto);
		}
		logger.info("getScheduleByDate==> listDto : " + listDto);
		return listDto;
	}
	

	private String[] getStrtEndTimeString(Date t1, Date t2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(t1);
		String h1 = null, m1 = null, h2 = null, m2 = null;
		int hh1 = cal.get(Calendar.HOUR_OF_DAY);
		int mm1 = cal.get(Calendar.MINUTE);
		if (hh1 < 10) {
			h1 = "0" + hh1;
		} else {
			h1 = String.valueOf(hh1);
		}
		if (mm1 == 0) {
			m1 = "0" + mm1;
		} else {
			m1 = String.valueOf(mm1);
		}
		String sTime = h1 + ":" + m1;
		cal.setTime(t2);
		int hh2 = cal.get(Calendar.HOUR_OF_DAY);
		int mm2 = cal.get(Calendar.MINUTE);
		if (mm2 == 59) {
			hh2++;
			mm2 = 0;
		} else {
			mm2++;
		}
		if (hh2 < 10) {
			h2 = "0" + hh2;
		} else {
			h2 = String.valueOf(hh2);
		}
		if (mm2 == 0) {
			m2 = "0" + mm2;
		} else {
			m2 = String.valueOf(mm2);
		}
		String eTime = h2 + ":" + m2;

		return new String[] { sTime, eTime };
	}

	public Map<Date, List<EventScheduleDTO>> getDataByEventId(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Date currDt = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDt);
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date lessDay = cal.getTime();
		logger.info("lessDay : " + lessDay);
		Map<Date, List<EventScheduleDTO>> map = new LinkedHashMap<Date, List<EventScheduleDTO>>();
		String query_string = "select e.date from EventScheduleEntity e where e.date > :lessDt group by e.date";
		Query query = session.createQuery(query_string);
		query.setParameter("lessDt", lessDay);
		List<Object> objList = query.list();
		Integer guideId = 0;
		for (Object obj : objList) {
			Date date = (Date) obj;
			List<EventScheduleDTO> listDto = getScheduleByDate(date, eventId,
					guideId);
			map.put(date, listDto);
		}
		// logger.info("before returning map value is : "+map);
		return map;
	}

	public Integer saveReservedGuest(EventGuestUserDTO guestDto) {
		Session session = sessionFactory.getCurrentSession();
		EventGuestUserEntity entity = new EventGuestUserEntity();
		BeanUtils.copyProperties(guestDto, entity);
		session.save(entity);
		Integer guestId = 0;
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.setProjection(Projections.max("guestUserId"));
		List<Integer> list = criteria.list();
		for (Integer integer : list) {
			guestId = integer;
		}

		return guestId;
	}

	public Long saveEventReservation(EventsReservationDTO reservationDto) {

		Session session = sessionFactory.getCurrentSession();
		Long refNumber = reservationDto.getReferenceNumber();
		EventsReservationEntity entity = new EventsReservationEntity();
		BeanUtils.copyProperties(reservationDto, entity);
		logger.info("reservationDto : before saving : " + entity);
		session.save(entity);

		return refNumber;
	}

	public Integer updateAvailSeats(Integer scheduleId, Integer diffOfSeats) {
		Session session = sessionFactory.getCurrentSession();
		Integer isUpdate = 0;
		/*
		 * String query = "update events_schedule set availableSeats=" +
		 * diffOfSeats + " WHERE scheduleId=" + scheduleId + "' ";
		 */
		Long diff = new Long(diffOfSeats);
		String query_string = "update EventScheduleEntity e set e.availableSeats = :diff where e.scheduleId = :scheduleId";
		Query query = session.createQuery(query_string);
		query.setParameter("diff", diff);
		query.setParameter("scheduleId", scheduleId);
		isUpdate = query.executeUpdate();

		return isUpdate;
	}

	public Map<Date, List<EventScheduleDTO>> getDataMap(Date date,
			Integer eventId, Integer guideId) {
		Session session = sessionFactory.getCurrentSession();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date lessDay = cal.getTime();
		logger.info("lessDay : " + lessDay);
		Map<Date, List<EventScheduleDTO>> map = new LinkedHashMap<Date, List<EventScheduleDTO>>();
		String query_string = "select e.date from EventScheduleEntity e where e.date > :lessDt group by e.date";
		Query query = session.createQuery(query_string);
		query.setParameter("lessDt", lessDay);
		List<Object> objList = query.list();
		for (Object obj : objList) {
			Date dt = (Date) obj;
			List<EventScheduleDTO> listDto = getScheduleByDate(dt, eventId,
					guideId);
			map.put(dt, listDto);
		}
		return map;
	}
	
	public EventsReservationDTO getReservationById(Integer reserveId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("reserveId", reserveId));
		List<EventsReservationEntity> list = criteria.list();
		EventsReservationDTO dto = new EventsReservationDTO();
		for (EventsReservationEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	public boolean isBlackListGuest(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("guestId", guestId));
		List<EventBlackListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isBlackListUser(Integer userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("userId", userID));
		List<EventBlackListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean addToWhiteList(EventWhiteListDTO whiteDto) {
		Session session = sessionFactory.getCurrentSession();
		EventWhiteListEntity entity = new EventWhiteListEntity();
		BeanUtils.copyProperties(whiteDto, entity);
		session.saveOrUpdate(entity);
		return true;
	}

	public boolean isWhiteListGuest(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("guestId", guestId));
		List<EventWhiteListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isWhiteListUser(Integer userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("userId", userID));
		List<EventWhiteListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean addToBlackList(EventBlackListDTO blackDto) {
		Session session = sessionFactory.getCurrentSession();
		EventBlackListEntity entity = new EventBlackListEntity();
		BeanUtils.copyProperties(blackDto, entity);
		session.saveOrUpdate(entity);
		return true;
	}

	public boolean setAsPaid(EventsReservationDTO reserveDto) {
		Session session = sessionFactory.getCurrentSession();
		String query_string = "update EventsReservationEntity e set e.isPaid = true where e.reserveId = :rsrvId";
		Query query = session.createQuery(query_string);
		query.setParameter("rsrvId", reserveDto.getReserveId());
		int update = query.executeUpdate();
		return true;
	}

	public boolean setAsArrived(EventsReservationDTO reserveDto) {
		Session session = sessionFactory.getCurrentSession();
		String query_string = "update EventsReservationEntity e set e.isArrived = true where e.reserveId = :rsrvId";
		Query query = session.createQuery(query_string);
		query.setParameter("rsrvId", reserveDto.getReserveId());
		int update = query.executeUpdate();
		return true;
	}

	public List<Date> getPrimaryDates(Date frmtDate) {
		Session session = sessionFactory.getCurrentSession();
		String query_string = "select e.date from EventScheduleEntity e where e.date >= :date group by e.date";
		Query query = session.createQuery(query_string);
		query.setParameter("date", frmtDate);
		List<Object> list = query.list();
		List<Date> dtList = new ArrayList<Date>();
		for (Object obj : list) {
			Date date = (Date) obj;
			dtList.add(date);
		}
		logger.info("getPrimaryDates daoimpl : before returning dtlist : "
				+ dtList);
		return dtList;
	}

	public Map<String, List<EventScheduleDTO>> getEvntWidgetData(Date date) {
		Session session = sessionFactory.getCurrentSession();
		String query_string = "select e.eventId from EventScheduleEntity e where e.date = :date group by e.eventId";
		Query query = session.createQuery(query_string);
		query.setParameter("date", date);
		List<Object> list = query.list();
		List<Integer> idList = new ArrayList<Integer>();
		Map<String, List<EventScheduleDTO>> eventMap = new LinkedHashMap<String, List<EventScheduleDTO>>();
		Integer eventId = null;
		for (Object obj : list) {
			eventId = (Integer) obj;
			idList.add(eventId);
		}
		logger.info("getEvntWidgetData daoimpl : idlist " + idList);
		for (Integer id : idList) {
			EventsDTO dto = getEventById(id);
			List<EventScheduleDTO> sDto = getScheduleListByEventIdAndDate(id,
					date);
			eventMap.put(dto.getEventName(), sDto);
		}
		logger.info("daoimpl getEvntWidgetData : before returning eventMap : "
				+ eventMap);
		return eventMap;
	}
	
	
	

	private List<EventScheduleDTO> getScheduleListByEventIdAndDate(Integer id,
			Date date) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("eventId", id));
		criteria.add(Restrictions.eq("date", date));
		List<EventScheduleEntity> list = criteria.list();
		List<EventScheduleDTO> listDto = new ArrayList<EventScheduleDTO>();
		EventScheduleDTO dto = null;
		for (EventScheduleEntity entity : list) {
			dto = new EventScheduleDTO();
			BeanUtils.copyProperties(entity, dto);
			String[] sArr = getStrtEndTimeString(dto.getStartTime(),
					dto.getEndTime());
			EventsGuideDTO gDto = getGuideById(dto.getGuideId());
			dto.setGuideName(gDto.getGuideName());
			dto.setEvntStrtTme(sArr[0]);
			dto.setEvntEndTme(sArr[1]);
			listDto.add(dto);
		}
		logger.info("getScheduleListByEventIdAndDate before returning: listDto : "
				+ listDto);
		return listDto;
	}

	public boolean haveReservation(Integer scheduleId, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		criteria.add(Restrictions.eq("userID", userId));
		criteria.add(Restrictions.eq("status", "active"));
		List<EventScheduleEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public Map<EventScheduleDTO, List<EventsReservationDTO>> getBlueReservedUsersMap(
			Integer scheduleId, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("scheduleId", scheduleId));
		List<EventScheduleEntity> list = criteria.list();
		EventScheduleDTO dto = new EventScheduleDTO();
		Map<EventScheduleDTO, List<EventsReservationDTO>> map = new HashMap<EventScheduleDTO, List<EventsReservationDTO>>();
		for (EventScheduleEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		EventsGuideDTO guideDto = getGuideById(dto.getGuideId());
		EventsDTO eventDto = getEventById(dto.getEventId());
		dto.setGuideName(guideDto.getGuideName());
		dto.setEventName(eventDto.getEventName());
		List<EventsReservationDTO> reserveList = getBlueReservedList(
				scheduleId, userId);
		map.put(dto, reserveList);

		return map;
	}

	public List<EventsGuideDTO> getEventGuidesByIds(List<Integer> guideIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsGuideEntity.class);
		criteria.add(Restrictions.in("guideId", guideIds));
		List<EventsGuideEntity> list = criteria.list();

		List<EventsGuideDTO> result = new ArrayList<EventsGuideDTO>();
		for (EventsGuideEntity entity : list) {
			EventsGuideDTO dto = new EventsGuideDTO();
			BeanUtils.copyProperties(entity, dto);
			result.add(dto);
		}

		return result;

	}
	


	public boolean cancelEventReservation(Integer reserveId) {
		Session session = sessionFactory.getCurrentSession();
		EventsReservationDTO reserveDto = getReservationById(reserveId);
		String updateStatusString = "update EventsReservationEntity e set e.status = :status where reserveId = :reserveId";
		Query updateStatusQuery = session.createQuery(updateStatusString);
		updateStatusQuery.setParameter("status", "cancel");
		updateStatusQuery.setParameter("reserveId", reserveId);
		int isUpdateStatus = updateStatusQuery.executeUpdate();
		if (isUpdateStatus > 0) {
			String updateAvailSeatsString = "update EventScheduleEntity e set e.availableSeats = e.availableSeats + :availSeats where e.scheduleId = :scheduleId";
			Query updateAvailSeatsQuery = session
					.createQuery(updateAvailSeatsString);
			updateAvailSeatsQuery.setParameter("availSeats", new Long(
					reserveDto.getNoOfPeople()));
			updateAvailSeatsQuery.setParameter("scheduleId",
					reserveDto.getEventScheduleId());
			int isUpdateAvailSeats = updateAvailSeatsQuery.executeUpdate();
			if (isUpdateAvailSeats > 0) {
				return true;
			}
		}
		return false;
	}

	public EventScheduleDTO getScheduleById(Integer eventScheduleId) {
		Session session = sessionFactory.getCurrentSession();
		EventScheduleDTO scheduleDto = new EventScheduleDTO();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("scheduleId", eventScheduleId));
		List<EventScheduleEntity> list = criteria.list();
		EventScheduleEntity entity = list.get(0);
		BeanUtils.copyProperties(entity, scheduleDto);

		return scheduleDto;
	}

	public EventGuestUserDTO getEventGuestById(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.add(Restrictions.eq("guestUserId", guestId));
		List<EventGuestUserEntity> list = criteria.list();
		EventGuestUserDTO guestDto = new EventGuestUserDTO();
		BeanUtils.copyProperties(list.get(0), guestDto);

		return guestDto;
	}

	public boolean isGuestExistInWhiteList(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("guestId", guestId));
		List<EventWhiteListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUserExistInWhiteList(Integer userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("userId", userID));
		List<EventWhiteListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isGuestExistInBlackList(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("guestId", guestId));
		List<EventBlackListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUserExistInBlackList(Integer userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("userId", userID));
		List<EventBlackListEntity> list = criteria.list();
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<EventsReservationDTO> getReservationsWithinFourDays() {
		logger.info("inside daoimpl : getReservationsWithinFourDays");
		Session session = sessionFactory.getCurrentSession();
		Date currDt = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDt);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND, 0);
		currDt = cal.getTime();
		cal.add(Calendar.DATE,4);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND, 59);
		Date lastDt = cal.getTime();
		logger.info("currdt : "+currDt+"lastDt : "+lastDt);
		List<EventsReservationDTO> reservationList = new ArrayList<EventsReservationDTO>();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.between("date", currDt, lastDt));
		List<EventScheduleEntity> list = criteria.list();
		logger.info("daoimpl : eventschedule list after criteria : "+list);
		List<Integer> eventIds = new ArrayList<Integer>();
		List<Integer> guideIds = new ArrayList<Integer>();
		List<EventScheduleDTO> dtoList = new ArrayList<EventScheduleDTO>();
		EventScheduleDTO scheduleDto = null;
		for(EventScheduleEntity entity : list) {
			scheduleDto = new EventScheduleDTO();
			BeanUtils.copyProperties(entity, scheduleDto);
			eventIds.add(scheduleDto.getEventId());
			guideIds.add(scheduleDto.getGuideId());
			dtoList.add(scheduleDto);
		}
		logger.info("daoimpl : eventscheduledto list : "+dtoList);
		
		Map<Integer,EventsDTO> eventsMap = new HashMap<Integer, EventsDTO>();
		for(Integer id : eventIds) {
			EventsDTO dto = getEventById(id);
			eventsMap.put(id, dto);
		}
		logger.info("eventsmap : "+eventsMap);
		
		Map<Integer,EventsGuideDTO> guidesMap = new HashMap<Integer, EventsGuideDTO>();
		for(Integer id : guideIds){
			EventsGuideDTO dto = getGuideById(id);
			guidesMap.put(id, dto);
		}
		logger.info("guidesmap : "+guidesMap);
		
		for(EventScheduleDTO dto : dtoList) {
			List<EventsReservationDTO> reserveList = getReservedListByScheduleId(dto.getScheduleId());
			for(EventsReservationDTO reserveDto : reserveList) {
				reserveDto.setEventDate(dto.getDate());
				reserveDto.setStartTme(dto.getStartTime());
				reserveDto.setEndTme(dto.getEndTime());
				reserveDto.setEventName(eventsMap.get(dto.getEventId()).getEventName());
				reserveDto.setGuideName(guidesMap.get(dto.getGuideId()).getGuideName());
				reservationList.add(reserveDto);
			}
		}
		logger.info("daoimpl before returning reservationList : "+reservationList);
		
		return reservationList;
	}

	public List<EventsReservationDTO> getReservedUserRecords(Integer scheduleId) {
		Session session = sessionFactory.getCurrentSession();
		List<EventsReservationDTO> reservtnList = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO reservtnRecord = null;
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		List<EventsReservationEntity> eventSchedules = criteria.list();
		for (EventsReservationEntity eventReservtn : eventSchedules) {
			reservtnRecord = new EventsReservationDTO();
			BeanUtils.copyProperties(eventReservtn, reservtnRecord);
			reservtnList.add(reservtnRecord);
		}
		return reservtnList;
	}

	public UserDTO getUserDetails(Integer userID) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userId", userID));
		List<UserEntity> list=criteria.list();
		
		UserDTO userDTO=new UserDTO();
		for (UserEntity userEntity : list) {
			BeanUtils.copyProperties(userEntity, userDTO);
		}
				
		return userDTO;
	}

	public boolean isUpdateEventScheduleRecord(Integer scheduleId) {
		logger.info("isUpdateEventScheduleRecord daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		EventScheduleEntity scheduleRecrd = (EventScheduleEntity) session.load(
				EventScheduleEntity.class, scheduleId);
		scheduleRecrd.setAvailableSeats(scheduleRecrd.getMaxCapacity());
		session.saveOrUpdate(scheduleRecrd);
		logger.info("isUpdateEventScheduleRecord daoImpl : end");
		return true;
	}

	public List<EventsReservationDTO> updateUsersReservtnStatus(
			Integer scheduleId) {
		logger.info("updateUsersReservtnStatus daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		List<EventsReservationDTO> reservtnList = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO reservtnRecord = null;
		Criteria criteria = session
				.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		List<EventsReservationEntity> eventSchedules = criteria.list();
		for (EventsReservationEntity eventReservtn : eventSchedules) {
			reservtnRecord = new EventsReservationDTO();
			BeanUtils.copyProperties(eventReservtn, reservtnRecord);
			reservtnList.add(reservtnRecord);
			eventReservtn.setStatus("delete");
			session.saveOrUpdate(eventReservtn);
		}
		logger.info("updateUsersReservtnStatus daoImpl : end");
		return reservtnList;
	}

	public EventsReservationDTO reserveSeatsForUser(
			EventsReservationDTO eventsReservationDTO, Integer scheduledId,
			Integer eventOrgtnId, Integer evntId) {
		logger.info("isReserveSeatsForUser daoImpl : start");
		//update record in event_schedule table
		updateScheduleRecord(scheduledId, eventsReservationDTO.getNoOfPeople());
		
		//reserve event for user and insert record in event_reservation table.
		EventsReservationDTO eventReservtnRecrd = reserveEventForUser(
				eventsReservationDTO, scheduledId);
		logger.info("isReserveSeatsForUser daoImpl : end");
		return eventReservtnRecrd;
	}

	private EventsReservationDTO reserveEventForUser(
			EventsReservationDTO eventsReservationDTO, Integer scheduledId) {
		// create new reference number
				Session session = sessionFactory.getCurrentSession();
				EventsReservationEntity resevtnEntity = new EventsReservationEntity();
				EventsReservationDTO reservtnRecrd = new EventsReservationDTO();
				BeanUtils.copyProperties(eventsReservationDTO, resevtnEntity);
				resevtnEntity.setEventScheduleId(scheduledId);
				session.save(resevtnEntity);
				BeanUtils.copyProperties(resevtnEntity, reservtnRecrd);
				return reservtnRecrd;
	}

	private void updateScheduleRecord(Integer scheduledId, Integer noOfPeople) {
		Session session = sessionFactory.getCurrentSession();
		Long availability=(long) 0;
		EventScheduleEntity scheduleEntity = (EventScheduleEntity) session
				.load(EventScheduleEntity.class, scheduledId);
		//scheduleEntity.setAvailableSeats(scheduleEntity.getAvailableSeats()- noOfPeople);
		scheduleEntity.setAvailableSeats(availability);
		session.saveOrUpdate(scheduleEntity);
	}
	
}
