package com.cushina.model.dataaccess.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.CategoryDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.RoomInfoDTO;
import com.cushina.model.dataaccess.PickHotelDao;
import com.cushina.model.pojo.AvailabilityByDateEntity;
import com.cushina.model.pojo.CategoryEntity;
import com.cushina.model.pojo.HotelEntity;
import com.cushina.model.pojo.RoomInfoEntity;
import com.cushina.web.bean.HotelBean;

@Repository
public class PickHotelDaoImpl implements PickHotelDao {

	private Logger logger = Logger.getLogger(PickHotelDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<HotelDTO> getHotelDetails(String hotelName) {
		logger.info("getHotelDetails in dao : start" + hotelName);
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = null;
		List<HotelDTO> hotelDTOs = new ArrayList<HotelDTO>();
		HotelDTO dto = null;
		criteria = session.createCriteria(HotelEntity.class);
		criteria.add(Restrictions.eq("hotelName", hotelName));
		List<HotelEntity> list = criteria.list();
		for (HotelEntity hotelEntity : list) {
			dto = new HotelDTO();
			BeanUtils.copyProperties(hotelEntity, dto);
			hotelDTOs.add(dto);
		}
		logger.info("getHotelDetails in dao : end");
		return hotelDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<HotelBean> getHotelDetail(Long hotelId) {
		logger.info("getHotelDetail daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria createCriteria = session.createCriteria(HotelEntity.class);
		createCriteria.add(Restrictions.eq("hotelID", hotelId));
		List<HotelEntity> queryList = createCriteria.list();
		List<HotelBean> hotelList = new ArrayList<HotelBean>();
		for (HotelEntity hotelentity : queryList) {
			HotelBean hoteldto = new HotelBean();
			BeanUtils.copyProperties(hotelentity, hoteldto);
			hotelList.add(hoteldto);
		}
		logger.info("getHotelDetail daoImpl : end");
		return hotelList;
	}

	@SuppressWarnings("unchecked")
	public List<RoomInfoDTO> getRoomInfoByHotel(Long hotelID) {
		logger.info("getRoomInfoByHotel dao : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RoomInfoEntity.class);
		List<RoomInfoDTO> roomInfoDTOList = new ArrayList<RoomInfoDTO>();
		List<RoomInfoEntity> roomInfoList = criteria.list();
		RoomInfoDTO dto = null;
		for (RoomInfoEntity roomInfoByHotelAndCategory : roomInfoList) {
			dto = new RoomInfoDTO();
			BeanUtils.copyProperties(roomInfoByHotelAndCategory, dto);
			roomInfoDTOList.add(dto);
		}
		logger.info("getRoomInfoByHotel dao : end");
		return roomInfoDTOList;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public List<HotelAvailabilityDTO> getDatesList(Long hotelId) {
		logger.info("getDatesList : start");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<HotelAvailabilityDTO> availabilitDTOs = new ArrayList<HotelAvailabilityDTO>();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		cal.add(Calendar.DATE, 60); // add 39 days
		Date time = cal.getTime();
		String previousDt = format.format(previousDate);
		String endDt = format.format(time);
		String currDt = format.format(date);
		Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY = "SELECT SUM(booked),DATE,SUM(noOFRooms),categoryId FROM HotelAvailabilityEntity  WHERE  hotelID = "
				+ hotelId
				+ " AND DATE  BETWEEN '"
				+ previousDt
				+ "' AND '"
				+ endDt + "' GROUP BY DATE ";
		Query query = session.createQuery(SQL_QUERY);

		List<Object[]> list = query.list();
		for (Object[] objects : list) {
			HotelAvailabilityDTO availabilityDTO = new HotelAvailabilityDTO();
			availabilityDTO.setBooked((Long) objects[0]);
			availabilityDTO.setDate((Date) objects[1]);
			availabilityDTO.setNoOFRooms((Long) objects[2]);
			availabilitDTOs.add(availabilityDTO);
		}
		logger.info("getDatesList : end");
		return availabilitDTOs;
	}

	
	@SuppressWarnings({ "unchecked", "unused" })
	public List<HotelAvailabilityDTO> getDatesListBsdOnCatgry(Long hotelID,
			Long categoryID) {
		logger.info("getDatesListBsdOnCatgry : start");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("hotelID ::"+hotelID+" , cate Id :: "+categoryID);
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		cal.add(Calendar.DATE, 60); // add 39 days
		Date time = cal.getTime();
		String previousDt = format.format(previousDate);
		String endDt = format.format(time);
		String currDt = format.format(date);
		Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY = "SELECT booked,DATE,noOFRooms,categoryId FROM HotelAvailabilityEntity  WHERE  hotelID = "
				+ hotelID
				+ " AND categoryId = "
				+ categoryID
				+ " AND DATE  BETWEEN '"
				+ previousDt
				+ "' AND '"
				+ endDt + "' ";
		Query query = session.createQuery(SQL_QUERY);
		List<HotelAvailabilityDTO> availabilitDTOs = new ArrayList<HotelAvailabilityDTO>();
		HotelAvailabilityDTO availabilityDTO = null;
		List<Object[]> list = query.list();
		logger.info("list size ::"+list.size());
		for (Object[] objects : list) {
			availabilityDTO = new HotelAvailabilityDTO();
			availabilityDTO.setBooked((Long) objects[0]);
			availabilityDTO.setDate((Date) objects[1]);
			availabilityDTO.setNoOFRooms((Long) objects[2]);
			availabilitDTOs.add(availabilityDTO);
		}
		logger.info("availabilitDTOs :: "+availabilitDTOs); 
		
		logger.info("getDatesListBsdOnCatgry : end");
		return availabilitDTOs;
	}
	
	
	/**
	 * this method is used to get room availability info and shows it on room
	 * availability info grid.
	 */
	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getRoomAvailInfo(Long hotelId) {
		logger.info("getRoomAvailInfo in dao : start");

		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date currentDate = c.getTime();
		List<AvailabilityByDateDTO> byDateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.ge("date", currentDate));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<AvailabilityByDateEntity> availList = criteria.list();
		for (AvailabilityByDateEntity availabilityByDateEntity : availList) {
			dateDTO = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, dateDTO);
			String statusCode = availabilityByDateEntity.getStatusCode();
			dateDTO.setStatusCode(statusCode);
			byDateDTOs.add(dateDTO);
		}
		logger.info("getRoomAvailInfo in dao : end");
		return byDateDTOs;
	}

	/**
	 * this method used to get next .(not used)
	 */
	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getNextRoomAvailInfo(Long lastRecordId) {

		lastRecordId = lastRecordId + 1;
		List<AvailabilityByDateDTO> byDateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.between("availabilityID", lastRecordId,
				lastRecordId + 15));
		List<AvailabilityByDateEntity> list = criteria.list();
		for (AvailabilityByDateEntity availabilityByDateEntity : list) {
			dateDTO = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, dateDTO);
			byDateDTOs.add(dateDTO);
		}
		logger.info("next avail room info :" + byDateDTOs);
		return byDateDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<CategoryDTO> getCategoryList(Long hotelID) {
		List<CategoryDTO> categoryList = new ArrayList<CategoryDTO>();
		Session session = sessionFactory.getCurrentSession();
		CategoryDTO categoryDTO = null;
		Query query = session
				.createQuery("SELECT DISTINCT C.categoryId,C.categoryName FROM CategoryEntity C WHERE C.hotelID="
						+ hotelID);
		List<Object> categoryNames = query.list();
		for (Object obj : categoryNames) {
			Object[] data = (Object[]) obj;
			categoryDTO = new CategoryDTO();
			categoryDTO.setCategoryId(Long.parseLong(data[0].toString()));
			categoryDTO.setCategoryName(data[1].toString());
			categoryList.add(categoryDTO);
		}
		return categoryList;
	}

	@SuppressWarnings("unchecked")
	public List<Long> getRoomsList(Long hotelID, Long categoryID) {
		logger.info("getRoomsList daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("SELECT R.roomId from RoomInfoEntity R where R.hotelID="
						+ hotelID + " AND R.categoryId=" + categoryID + "");
		logger.info("getRoomsList daoImpl : end");
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Long> getRoomsAvailList(Long hotelID, Long categoryID) {
		logger.info("getRoomsAvailList daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("SELECT R.roomId from RoomInfoEntity R where R.hotelID="
						+ hotelID + " AND R.categoryId=" + categoryID + "");
		logger.info("getRoomsAvailList daoImpl : end");
		return query.list();
	}
	@SuppressWarnings("unchecked")
	public List<Long> getWhiteRoomsList(Long hotelID, Long categoryID) {
		logger.info("getWhiteRoomsList daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Integer whiteOrBlack = new Integer(1);
		Query query = session
				.createQuery("SELECT R.roomId from RoomInfoEntity R where R.hotelID="
						+ hotelID
						+ " AND R.categoryId="
						+ categoryID
						+ " AND R.WhiteAndBlack=" + whiteOrBlack);

		logger.info("getWhiteRoomsList daoImpl : end");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getSelectedDateAvailRooms(
			String slectedDate, Long hotelID) {
		logger.info("getSelectedDateRooms in dao : start");
		logger.info("selected date format :" + slectedDate);// 27/07/2015
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		Date sltDate = null;
		try {
			sltDate = sd.parse(slectedDate);
			logger.info("selected date @@@ :" + sltDate);
		} catch (Exception e) {
			logger.debug("came to catch block while parsing date in getSelectedDateRooms method");
		}
		List<AvailabilityByDateDTO> byDateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.ge("date", sltDate));
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<AvailabilityByDateEntity> availList = criteria.list();

		for (AvailabilityByDateEntity availabilityByDateEntity : availList) {
			dateDTO = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, dateDTO);
			byDateDTOs.add(dateDTO);

		}
		logger.info("getSelectedDateAvailRooms dao :" + byDateDTOs);
		logger.info("getSelectedDateRooms in dao : end");
		return byDateDTOs;
	}

	@SuppressWarnings("unchecked")
	public Map<Long, String> getCategoryListByMap(Long hotelId) {
		logger.info("getCategoryListByMap in dao : start");
		Map<Long, String> categoryMap = new HashMap<Long, String>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CategoryEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<CategoryEntity> categoryList = criteria.list();
		for (CategoryEntity categoryEntity : categoryList) {
			categoryMap.put(categoryEntity.getCategoryId(),
					categoryEntity.getCategoryName());
		}
		logger.info("getCategoryListByMap in dao : end");
		return categoryMap;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getRoomAvailByCategory(Long categoryId,
			Long hotelID, Date selectedDt) {
		logger.info("getRoomAvailByCategory in dao : start");
		List<AvailabilityByDateDTO> byDateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO byDateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.eq("categoryId", categoryId));
		criteria.add(Restrictions.eq("hotelID", hotelID));
		criteria.add(Restrictions.ge("date", selectedDt));
		List<AvailabilityByDateEntity> dateEntities = criteria.list();

		for (AvailabilityByDateEntity availabilityByDateEntity : dateEntities) {
			byDateDTO = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
			byDateDTOs.add(byDateDTO);
		}
		logger.info("getRoomAvailByCategory in dao : end");
		return byDateDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsBasedOnCatgeory(
			String selectedDate, Long hotelID, Long categoryID, Integer dayCount) {
		logger.info("getSelectedDateRooms in dao : start");
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		Date sltDate = null;
		try {
			sltDate = sd.parse(selectedDate);
		} catch (Exception e) {
			logger.debug("came to catch block while parsing date in getSelectedDateRooms method");
			e.printStackTrace();
		}
		List<AvailabilityByDateDTO> byDateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.ge("date", sltDate));
		criteria.add(Restrictions.eq("hotelID", hotelID));
		criteria.add(Restrictions.eq("categoryId", categoryID));
		List<AvailabilityByDateEntity> availList = criteria.list();

		for (AvailabilityByDateEntity availabilityByDateEntity : availList) {
			dateDTO = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, dateDTO);
			byDateDTOs.add(dateDTO);

		}
		logger.info("getSelectedDateRooms in dao : end");
		return byDateDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getLoggedInUserReservedDates(
			Long hotelID, Integer userId) {
		logger.info("getLoggedInUserReservedDates dao : start");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		Session sessoion = sessionFactory.getCurrentSession();
		Criteria criteria = sessoion
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.ge("date", previousDate));
		criteria.add(Restrictions.eq("hotelID", hotelID));
		criteria.add(Restrictions.eq("userId", userId));
		List<AvailabilityByDateDTO> loggedInUserRecrds = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO userRecrds = null;
		List<AvailabilityByDateEntity> userRecords = criteria.list();

		for (AvailabilityByDateEntity availabilityByDateEntity : userRecords) {
			userRecrds = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, userRecrds);
			loggedInUserRecrds.add(userRecrds);
		}
		logger.info("getLoggedInUserReservedDates dao : end");
		return loggedInUserRecrds;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getLoggedInUserReservedDatesOnCategoryType(
			Long hotelID, Integer userId, Long categoryID) {
		logger.info("getLoggedInUserReservedDatesOnCategoryType daoImpl : start");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		Session sessoion = sessionFactory.getCurrentSession();
		Criteria criteria = sessoion
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.ge("date", previousDate));
		criteria.add(Restrictions.eq("hotelID", hotelID));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("categoryId", categoryID));
		List<AvailabilityByDateDTO> loggedInUserRecrds = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO userRecrds = null;
		List<AvailabilityByDateEntity> userRecords = criteria.list();
		for (AvailabilityByDateEntity availabilityByDateEntity : userRecords) {
			userRecrds = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, userRecrds);
			loggedInUserRecrds.add(userRecrds);
		}
		logger.info("getLoggedInUserReservedDatesOnCategoryType daoImpl : end");
		return loggedInUserRecrds;
	}

	

	@SuppressWarnings({ "unchecked", "unused" })
	public boolean isRowsInserted() {
		logger.info("getLastDate : start");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = null;
		List<HotelDTO> hotelList =new ArrayList<HotelDTO>();
		HotelDTO hotelDTO = null;
	    criteria = session.createCriteria(HotelEntity.class);
		List <HotelEntity> hotelLst = criteria.list();
		for (HotelEntity hotelEntity : hotelLst) {
			hotelDTO = new HotelDTO();
			BeanUtils.copyProperties(hotelEntity, hotelDTO);
			hotelList.add(hotelDTO);
		}
		logger.info("hote List : "+hotelList);
		
		
		List<CategoryDTO> catList = new ArrayList<CategoryDTO>();
		CategoryDTO catInfo = null;
		
		criteria = session.createCriteria(CategoryEntity.class);
		criteria.add(Restrictions.eq("hotelID", new Long(1)));
		List<CategoryEntity> catListInfo = criteria.list();
		for (CategoryEntity categoryEntity : catListInfo) {
			catInfo = new CategoryDTO();
			BeanUtils.copyProperties(categoryEntity, catInfo);
			catList.add(catInfo);
		}
		
		
		List<RoomInfoDTO> roomLst = new ArrayList<RoomInfoDTO>();
		RoomInfoDTO roomInfo =  null;
		// we have to insert hotelId dynamically...
		criteria =  session.createCriteria(RoomInfoEntity.class);
		criteria.add(Restrictions.eq("hotelID", new Long(1)));
		criteria.add(Restrictions.eq("categoryId", new Long(1)));
		List <RoomInfoEntity> roomInfoList = criteria.list();
		for (RoomInfoEntity roomInfoEntity : roomInfoList) {
			roomInfo = new RoomInfoDTO();
			BeanUtils.copyProperties(roomInfoEntity, roomInfo);
			roomLst.add(roomInfo);
		}
		logger.info("room List : "+roomLst);
		
		
		
		
		//get MAX record from DB.
		/*Criteria criteria = session
				.createCriteria(HotelAvailabilityEntity.class);
		criteria.addOrder(Order.desc("hotelAvailbleId"));
		criteria.setMaxResults(1);
		HotelAvailabilityEntity uniqueResult = (HotelAvailabilityEntity) criteria
				.uniqueResult();
		logger.info("uniqueResult : "+uniqueResult);
		
		Date lastDate = uniqueResult.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastDate);
		cal.add(Calendar.DATE, 1);
		Date nextDate = cal.getTime();*/// get next date
		
		logger.info("getLastDate : end");
		return false;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public List<AvailabilityByDateDTO> getWhiteRoomsLst(Long hotelId) {
		logger.info("getWhiteRoomsLst daoImpl : start");
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date currentDate = c.getTime();

		List<AvailabilityByDateDTO> availLst = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO availabilityByDateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RoomInfoEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		criteria.add(Restrictions.eq("WhiteAndBlack", new Integer(1)));
		List<RoomInfoEntity> roomInfoLst = criteria.list();
		for (RoomInfoEntity roomInfoEntity : roomInfoLst) {
			Long roomId = roomInfoEntity.getRoomId();
			criteria = session.createCriteria(AvailabilityByDateEntity.class);
			criteria.add(Restrictions.eq("hotelID", hotelId));
			criteria.add(Restrictions.eq("roomId", roomId));
			criteria.add(Restrictions.ge("date", currentDate));
			List<AvailabilityByDateEntity> roomInfoByRoomId = criteria.list();
			for (AvailabilityByDateEntity availabilityByDateEntity : roomInfoByRoomId) {
				availabilityByDateDTO = new AvailabilityByDateDTO();
				BeanUtils.copyProperties(availabilityByDateEntity,
						availabilityByDateDTO);
				availLst.add(availabilityByDateDTO);
			}
		}
		logger.info("getWhiteRoomsLst daoImpl : end");
		return availLst;
	}
	
	

	public List<HotelAvailabilityDTO> getDatesBasedOnNoOfDays(Long hotelID,
			Integer noOfDays, Long categoryID) {
		
		logger.info("getDatesBadedOnNoOfDays daoImpl : start");
		
		
		logger.info("getDatesBadedOnNoOfDays daoImpl : end");
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<RoomInfoDTO> getRoomsListOfAllCategories(Long hotelId) {
		logger.info("getRoomsListOfAllCategories daoImpl : start");
		
		List<RoomInfoDTO> roomsLst = new ArrayList<RoomInfoDTO>();
		RoomInfoDTO room = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RoomInfoEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<RoomInfoEntity> list = criteria.list();
		for (RoomInfoEntity roomInfoEntity : list) {
			room = new RoomInfoDTO();
			BeanUtils.copyProperties(roomInfoEntity, room);
			roomsLst.add(room);
		}
		logger.info("getRoomsListOfAllCategories daoImpl : end");
		return roomsLst;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getWhiteRoomsLstOnCategryType(
			Long hotelId, Long categoryID) {
		logger.info("getWhiteRoomsLstOnCategryType daoImpl : start");
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date currentDate = c.getTime();

		List<AvailabilityByDateDTO> availLst = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO availabilityByDateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RoomInfoEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		criteria.add(Restrictions.eq("categoryId", categoryID));
		criteria.add(Restrictions.eq("WhiteAndBlack", new Integer(1)));
		List<RoomInfoEntity> roomInfoLst = criteria.list();
		for (RoomInfoEntity roomInfoEntity : roomInfoLst) {
			Long roomId = roomInfoEntity.getRoomId();
			criteria = session.createCriteria(AvailabilityByDateEntity.class);
			criteria.add(Restrictions.eq("hotelID", hotelId));
			criteria.add(Restrictions.eq("categoryId", categoryID));
			criteria.add(Restrictions.eq("roomId", roomId));
			criteria.add(Restrictions.ge("date", currentDate));
			List<AvailabilityByDateEntity> roomInfoByRoomId = criteria.list();
			for (AvailabilityByDateEntity availabilityByDateEntity : roomInfoByRoomId) {
				availabilityByDateDTO = new AvailabilityByDateDTO();
				BeanUtils.copyProperties(availabilityByDateEntity,
						availabilityByDateDTO);
				availLst.add(availabilityByDateDTO);
			}
		}
		logger.info("getWhiteRoomsLstOnCategryType daoImpl : end");
		return availLst;
	}

	@SuppressWarnings("unchecked")
	public List<HotelAvailabilityDTO> getSearchDatesList(String selectedDate,
			Long hotelID) {
		logger.info("getDatesList : start");
		logger.info("show dates start In daoImpl : "+System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date			
		String previousDt = format.format(previousDate);		
		List<HotelAvailabilityDTO> availabilitDTOs = new ArrayList<HotelAvailabilityDTO>();		
		String endDateByHotel = getEndDateByHotel(hotelID);	
		
		Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY = "SELECT SUM(booked),DATE,SUM(noOFRooms),categoryId FROM HotelAvailabilityEntity  WHERE  hotelID = "
				+ hotelID
				+ " AND DATE  BETWEEN '"
				+ previousDt
				+ "' AND '"
				+ endDateByHotel + "' GROUP BY DATE ";
		Query query = session.createQuery(SQL_QUERY);

		List<Object[]> list = query.list();
		for (Object[] objects : list) {
			HotelAvailabilityDTO availabilityDTO = new HotelAvailabilityDTO();
			availabilityDTO.setBooked((Long) objects[0]);
			availabilityDTO.setDate((Date) objects[1]);
			availabilityDTO.setNoOFRooms((Long) objects[2]);
			availabilitDTOs.add(availabilityDTO);
		}
		logger.info("show dates end In daoImpl : "+System.currentTimeMillis());
		logger.info("getDatesList : end");
		return availabilitDTOs;
	}

	@SuppressWarnings("unchecked")
	private String getEndDateByHotel(Long hotelID) {		
		Session session = sessionFactory.getCurrentSession();		
		String query="SELECT  date FROM HotelAvailabilityEntity where hotelID="+hotelID+"";		
		Query createQuery = session.createQuery(query);		
		List<Object> list = createQuery.list();
		 String lastDate=null;
		if (list != null && !list.isEmpty()) {
			  Object object = list.get(list.size()-1);
			   lastDate = String.valueOf(object);
			}		
		return lastDate;		
	}

	@SuppressWarnings("unchecked")
	public List<HotelAvailabilityDTO> getSearchDatesListBsdOnCatgry(
			String selectedDate, Long hotelID, Long categoryID) {
		logger.info("getDatesListBsdOnCatgry : start");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date			
		String previousDt = format.format(previousDate);		
		String endDateByHotel = getEndDateByHotel(hotelID);		
		Session session = sessionFactory.getCurrentSession();
		String SQL_QUERY = "SELECT booked,DATE,noOFRooms,categoryId FROM HotelAvailabilityEntity  WHERE  hotelID = "
				+ hotelID
				+ " AND categoryId = "
				+ categoryID
				+ " AND DATE  BETWEEN '"
				+ previousDt
				+ "' AND '"
				+ endDateByHotel + "' ";
		Query query = session.createQuery(SQL_QUERY);
		List<HotelAvailabilityDTO> availabilitDTOs = new ArrayList<HotelAvailabilityDTO>();
		HotelAvailabilityDTO availabilityDTO = null;
		List<Object[]> list = query.list();
		logger.info("list size ::"+list.size());
		for (Object[] objects : list) {
			availabilityDTO = new HotelAvailabilityDTO();
			availabilityDTO.setBooked((Long) objects[0]);
			availabilityDTO.setDate((Date) objects[1]);
			availabilityDTO.setNoOFRooms((Long) objects[2]);
			availabilityDTO.setCategoryId((Long) objects[3]);
			availabilitDTOs.add(availabilityDTO);
		}
		logger.info("availabilitDTOs :: "+availabilitDTOs);		
		logger.info("getDatesListBsdOnCatgry : end");
		return availabilitDTOs;
	}

		


}
