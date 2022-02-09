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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import com.events.common.dto.EventsReservationDTO;
import com.events.model.dataaccess.EventUserByObjectServiceDao;
import com.events.model.pojo.EventBlackListEntity;
import com.events.model.pojo.EventGuestUserEntity;
import com.events.model.pojo.EventScheduleEntity;
import com.events.model.pojo.EventWhiteListEntity;
import com.events.model.pojo.EventsEntity;
import com.events.model.pojo.EventsGuideEntity;
import com.events.model.pojo.EventsReservationEntity;

@Repository
public class EventUserByObjectServiceDaoImpl implements EventUserByObjectServiceDao{

	
	Logger logger = Logger.getLogger(EventUserByObjectServiceDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	

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
	
	public Map<EventScheduleDTO,List<EventsReservationDTO>> getEventReservedUsersMap(Integer scheduleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("scheduleId", scheduleId));
		List<EventScheduleEntity> list = criteria.list();
		EventScheduleDTO dto = new EventScheduleDTO();
		Map<EventScheduleDTO,List<EventsReservationDTO>> map = new HashMap<EventScheduleDTO, List<EventsReservationDTO>>();
		for(EventScheduleEntity entity : list) {
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
	
	private EventsDTO getEventById(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsEntity.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		List<EventsEntity> list = criteria.list();
		EventsDTO dto = new EventsDTO();
		for(EventsEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}
	
	private List<EventsReservationDTO> getReservedListByScheduleId(Integer scheduleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		List<EventsReservationEntity> list = criteria.list();
		List<EventsReservationDTO> listDto = new ArrayList<EventsReservationDTO>();
		EventsReservationDTO dto = null;
		for(EventsReservationEntity entity : list) {
			dto = new EventsReservationDTO();
			BeanUtils.copyProperties(entity, dto);
			
			if(dto.getGuestId() != null) {	
				EventGuestUserDTO guestDto = getGuestById(dto.getGuestId());
				dto.setUserName(guestDto.getUserName());
				dto.setEmail(guestDto.getEmail());
				dto.setPhoneNumber(guestDto.getPhoneNumber());
			}
			else {				
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
		for(UserEntity entity : list) {
			BeanUtils.copyProperties(entity,dto);
		}
		return dto;
	}
	
	private EventGuestUserDTO getGuestById(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventGuestUserEntity.class);
		criteria.add(Restrictions.eq("guestUserId",guestId));
		List<EventGuestUserEntity> list = criteria.list();
		EventGuestUserDTO dto = new EventGuestUserDTO();
		for(EventGuestUserEntity entity : list) {
			BeanUtils.copyProperties(entity,dto);
		}
		
		return dto;
	}
	
	private EventsGuideDTO getGuideById(Integer guideId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsGuideEntity.class);
		criteria.add(Restrictions.eq("guideId", guideId));
		List<EventsGuideEntity> list = criteria.list();
		EventsGuideDTO dto = new EventsGuideDTO();
		for(EventsGuideEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}
	
	private List<EventScheduleDTO> getScheduleByDate(Date date,Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventScheduleEntity.class);
		criteria.add(Restrictions.eq("date", date));
		String eventName=null;
		String	guideName=null;
		if(eventId != 0) {
			criteria.add(Restrictions.eq("eventId", eventId));
			 eventName=getEvntName(eventId);
		}
		
	/*	if(guideId != 0) {
			criteria.add(Restrictions.eq("guideId", guideId));
			guideName=getGuideName(guideId);
		}*/
		
		List<EventScheduleEntity> list = criteria.list();
		List<EventScheduleDTO> listDto = new ArrayList<EventScheduleDTO>();
		EventScheduleDTO dto = null;
		for(EventScheduleEntity entity : list) {
			dto = new EventScheduleDTO();
			BeanUtils.copyProperties(entity, dto);
			Date strtTime = dto.getStartTime();
			Date endTime = dto.getEndTime();
			String[] sArr = getStrtEndTimeString(strtTime, endTime);
			dto.setEvntStrtTme(sArr[0]);
			dto.setEvntEndTme(sArr[1]);
			dto.setEventName(eventName);
			dto.setGuideName(guideName);
			listDto.add(dto);
		}
		logger.info("getScheduleByDate==> listDto : "+listDto);
		return listDto;
	}
	
	private String getGuideName(Integer guideId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsGuideEntity.class);
		criteria.add(Restrictions.eq("guideId", guideId));
		List<EventsGuideEntity> list=criteria.list();
		String GuideName=null;
		for (EventsGuideEntity eventsGuideEntity : list) {
			GuideName=eventsGuideEntity.getGuideName();
		}
		return GuideName;
	}

	private String getEvntName(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsEntity.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		List<EventsEntity> list=criteria.list();
		String evntname=null;
		for (EventsEntity eventsEntity : list) {
			evntname =eventsEntity.getEventName();
		}
		return evntname;
	}

	private String[] getStrtEndTimeString(Date t1, Date t2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(t1);
		String h1=null,m1=null,h2=null,m2=null;
		int hh1 = cal.get(Calendar.HOUR_OF_DAY);
		int mm1 = cal.get(Calendar.MINUTE);
		if(hh1<10) {
			h1 = "0"+hh1;
		}else {
			h1 = String.valueOf(hh1);
		}
		if(mm1==0) {
			m1 = "0"+mm1;
		}else {
			m1 = String.valueOf(mm1);
		}
		String sTime = h1+":"+m1;
		cal.setTime(t2);
		int hh2 = cal.get(Calendar.HOUR_OF_DAY);
		int mm2 = cal.get(Calendar.MINUTE);
		if(mm2==59) {
			hh2++;
			mm2=0;
		}else {
			mm2++;
		}
		if(hh2<10) {
			h2 = "0"+hh2;
		}else {
			h2 = String.valueOf(hh2);
		}
		if(mm2==0) {
			m2 = "0"+mm2;
		}else {
			m2 = String.valueOf(mm2);
		}
		String eTime = h2+":"+m2;
		
		return new String[]{sTime,eTime};
	}

	public Map<Date,List<EventScheduleDTO>> getDataByEventId(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Date currDt = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDt);
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date lessDay = cal.getTime();
		logger.info("lessDay : "+lessDay);
		Map<Date,List<EventScheduleDTO>> map = new LinkedHashMap<Date, List<EventScheduleDTO>>();
		String query_string = "select e.date from EventScheduleEntity e where e.date > :lessDt group by e.date";
		Query query = session.createQuery(query_string);
		query.setParameter("lessDt", lessDay);
		List<Object> objList = query.list();
		Integer guideId = 0;
		for(Object obj : objList) {
			Date date = (Date) obj;
			List<EventScheduleDTO> listDto = getScheduleByDate(date,eventId);
		
			map.put(date, listDto);
		}
		//logger.info("before returning map value is : "+map);
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
		for(Integer integer : list) {
			guestId = integer;
		}
		
		return guestId;
	}

	public Long saveEventReservation(EventsReservationDTO reservationDto) {
		Session session = sessionFactory.getCurrentSession();
		Long refNumber = reservationDto.getReferenceNumber();
		EventsReservationEntity entity = new EventsReservationEntity();
		BeanUtils.copyProperties(reservationDto, entity);
		session.save(entity);
		
		return refNumber;
	}

	public Integer updateAvailSeats(Integer scheduleId, Integer diffOfSeats) {
		Session session = sessionFactory.getCurrentSession();
		Integer isUpdate = 0;
		/*String query = "update events_schedule set availableSeats=" + diffOfSeats
				+ " WHERE scheduleId="
				+ scheduleId 
				+ "' ";*/
		Long diff = new Long(diffOfSeats);
		String query_string = "update EventScheduleEntity e set e.availableSeats = :diff where e.scheduleId = :scheduleId";
		Query query = session.createQuery(query_string);
		query.setParameter("diff", diff);
		query.setParameter("scheduleId", scheduleId);
		isUpdate = query.executeUpdate();
		
		return isUpdate;
	}

	public Map<Date, List<EventScheduleDTO>> getDataMap(Date date,
			Integer eventId, UserDTO userDto) {
		Session session = sessionFactory.getCurrentSession();		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date lessDay = cal.getTime();
		logger.info("lessDay : "+lessDay);
		Map<Date,List<EventScheduleDTO>> map = new LinkedHashMap<Date, List<EventScheduleDTO>>();
		String query_string = "select e.date from EventScheduleEntity e where e.date > :lessDt group by e.date";
		Query query = session.createQuery(query_string);
		query.setParameter("lessDt", lessDay);
		List<Object> objList = query.list();
		for(Object obj : objList) {
			Date dt = (Date) obj;
			List<EventScheduleDTO> listDto = getScheduleByDate(dt,eventId);
			//System.out.println("list"+listDto);
			for (EventScheduleDTO eventScheduleDTO : listDto) {
				Integer evntId=eventScheduleDTO.getEventId();
				Integer guideid=eventScheduleDTO.getGuideId();
				String eventName=getEvntName(evntId);
				String guideName=getGuideName(guideid);
				eventScheduleDTO.setEventName(eventName);
				eventScheduleDTO.setGuideName(guideName);
				if(userDto != null){
				boolean status=getUserResvInfo(userDto.getUserId(),eventScheduleDTO.getScheduleId());
				eventScheduleDTO.setHaveReservation(status);
				}else{
					eventScheduleDTO.setHaveReservation(false);
				}
				
				List<EventsReservationDTO> dto=getUserNotesInUserReservationTable(eventScheduleDTO.getScheduleId());
				for (EventsReservationDTO eventsReservationDTO : dto) {
					eventScheduleDTO.setUserNotes(eventsReservationDTO.getNotes());
					eventScheduleDTO.setIsArrived(eventsReservationDTO.isArrived());
					eventScheduleDTO.setIsPaid(eventsReservationDTO.isPaid());
				}
			}
			map.put(dt, listDto);
		}
		return map;
	}

	

	private boolean getUserResvInfo(Integer userId, Integer scheduleId) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		if(userId != null){
		criteria.add(Restrictions.eq("userID", userId));
		}
		List<EventsReservationEntity> list=criteria.list();
	     System.out.println("list"+list.size());
	     if(list.size() != 0){
	    	 
	    	 return true;
	     }else{
		return false;
	     }
	}

	private List<EventsReservationDTO> getUserNotesInUserReservationTable(
			Integer scheduleId) {

		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("eventScheduleId", scheduleId));
		List<EventsReservationEntity> list=criteria.list();
		List<EventsReservationDTO> resvlist= new ArrayList<EventsReservationDTO>();
		EventsReservationDTO dto=null;
		for (EventsReservationEntity eventsReservationEntity : list) {
			dto=new EventsReservationDTO();
			BeanUtils.copyProperties(eventsReservationEntity, dto);
			resvlist.add(dto);
		}
		return resvlist;
	}

	public EventsReservationDTO getReservationById(Integer reserveId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsReservationEntity.class);
		criteria.add(Restrictions.eq("reserveId", reserveId));
		List<EventsReservationEntity> list = criteria.list();
		EventsReservationDTO dto = new EventsReservationDTO();
		for(EventsReservationEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	public boolean isBlackListGuest(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("guestId", guestId));
		List<EventBlackListEntity> list = criteria.list();
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	public boolean isBlackListUser(Integer userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventBlackListEntity.class);
		criteria.add(Restrictions.eq("userId", userID));
		List<EventBlackListEntity> list = criteria.list();
		if(list.size() > 0) {
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
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	public boolean isWhiteListUser(Integer userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventWhiteListEntity.class);
		criteria.add(Restrictions.eq("userId", userID));
		List<EventWhiteListEntity> list = criteria.list();
		if(list.size() > 0) {
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
		//EventsReservationEntity entity = new EventsReservationEntity();
		String query_string = "update EventsReservationEntity e set e.isPaid = true where e.reserveId = :rsrvId";
		Query query = session.createQuery(query_string);
		query.setParameter("rsrvId", reserveDto.getReserveId());
		int update = query.executeUpdate();
		/*BeanUtils.copyProperties(reserveDto, entity);
		session.update(entity);*/
		return true;
	}

	public boolean setAsArrived(EventsReservationDTO reserveDto) {
		Session session = sessionFactory.getCurrentSession();
		String query_string = "update EventsReservationEntity e set e.isArrived = true where e.reserveId = :rsrvId";
		Query query = session.createQuery(query_string);
		query.setParameter("rsrvId", reserveDto.getReserveId());
		int update = query.executeUpdate();
		/*EventsReservationEntity entity = new EventsReservationEntity();
		BeanUtils.copyProperties(reserveDto, entity);
		session.update(entity);*/
		return true;
	}

	@SuppressWarnings("unchecked")
	public String getEventName(Integer eventId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EventsEntity.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		List<EventsEntity> list=criteria.list();
		String evntname=null;
		for (EventsEntity eventsEntity : list) {
			evntname =eventsEntity.getEventName();
		}
		return evntname;
	}

}
