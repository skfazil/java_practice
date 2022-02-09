package com.cushina.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.model.dataaccess.BookingHistoryDao;
import com.cushina.model.pojo.AvailabilityByDateEntity;
import com.cushina.model.pojo.BookingHistoryEntity;
import com.cushina.model.pojo.HotelEntity;

@Repository
public class BookingHistoryDaoImpl implements BookingHistoryDao {

	private Logger logger = Logger.getLogger(BookingHistoryDaoImpl.class
			.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Map<HotelDTO, BookingHistoryDTO>> getBookingHistoryDetails(
			Integer userId,Integer quickUserId, Long hotelID) {
		logger.info("getBookingHistoryDetails in Dao : start");

		List<Map<HotelDTO, BookingHistoryDTO>> historyMap = new ArrayList<Map<HotelDTO, BookingHistoryDTO>>();
		Map<HotelDTO, BookingHistoryDTO> map = new HashMap<HotelDTO, BookingHistoryDTO>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		if(userId != 0 || userId != null){
			criteria.add(Restrictions.eq("userId", new Integer(userId)));
			criteria.add(Restrictions.ne("status", "delete"));
		}else{
			criteria.add(Restrictions.eq("quickUserId", new Integer(quickUserId)));
			criteria.add(Restrictions.ne("status", "delete"));
		}
		List<BookingHistoryEntity> list = criteria.list();

		BookingHistoryDTO dto = null;
		HotelDTO hotelDTO = null;
		for (BookingHistoryEntity bookingHistoryEntity : list) {
			dto = new BookingHistoryDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, dto);
			hotelDTO = new HotelDTO();
			HotelEntity hotel = (HotelEntity) session.load(HotelEntity.class,
					dto.getHotelID());
			BeanUtils.copyProperties(hotel, hotelDTO);
			map.put(hotelDTO, dto);
			historyMap.add(map);
		}
		logger.info("history map :"+historyMap);
		logger.info("getBookingHistoryDetails in Dao : end");
		return historyMap;
	}

	public AvailabilityByDateDTO getAvailblityInfo(Long availabilityID) {
		Session session = sessionFactory.getCurrentSession();
		AvailabilityByDateDTO dto = new AvailabilityByDateDTO();
		AvailabilityByDateEntity availabilityByDateEntity = (AvailabilityByDateEntity) session
				.load(AvailabilityByDateEntity.class, availabilityID);
		BeanUtils.copyProperties(availabilityByDateEntity, dto);
		return dto;
	}

}
