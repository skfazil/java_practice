package com.cushina.model.dataaccess.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.model.dataaccess.ReservationByRoomDao;
import com.cushina.model.pojo.AvailabilityByDateEntity;

@Repository
public class ReservationByRoomDaoImpl implements ReservationByRoomDao {

	private Logger logger = Logger.getLogger(ReservationByRoomDaoImpl.class
			.getName());

	@Autowired
	private SessionFactory sessionFactory;

	public List<AvailabilityByDateDTO> getRoomAvailByCategoryAndRoomNum(
			Long categoryId, Long hotelID, Long roomNO, Date currDt) {
		logger.info("getRoomAvailByCategory in dao : start");
		logger.info("room number in getRoomAvailByCategoryAndRoomNum dao  :"
				+ roomNO + " and selected date is :" + currDt);
		List<AvailabilityByDateDTO> byDateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO byDateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.eq("categoryId", categoryId));
		criteria.add(Restrictions.eq("hotelID", hotelID));
		criteria.add(Restrictions.eq("roomId", roomNO));
		criteria.add(Restrictions.ge("date", currDt));
		List<AvailabilityByDateEntity> dateEntities = criteria.list();

		for (AvailabilityByDateEntity availabilityByDateEntity : dateEntities) {
			byDateDTO = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
			byDateDTOs.add(byDateDTO);
		}
		logger.info("room avail list info :" + byDateDTOs);

		logger.info("inside getRoomAvailByCategory dao :" + byDateDTOs);
		logger.info("getRoomAvailByCategory in dao : end");
		return byDateDTOs;
	}

	public List<AvailabilityByDateDTO> getRoomAvailByDaysCount(Long categoryId,
			Long hotelID, Long roomNO, Integer dayCount, Date selectedDt) {
		logger.info("getRoomAvailByDaysCount dao : start");
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDt);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date
		List<AvailabilityByDateDTO> availabilityByDateCount = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dateCountDTO = null;

		if (roomNO == 0 && dayCount == 0) {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session
					.createCriteria(AvailabilityByDateEntity.class);
			criteria.add(Restrictions.eq("categoryId", categoryId));
			criteria.add(Restrictions.eq("hotelID", hotelID));
			criteria.add(Restrictions.ge("date", previousDate));
			List<AvailabilityByDateEntity> availList = criteria.list();
			for (AvailabilityByDateEntity availabilityByDate : availList) {
				dateCountDTO = new AvailabilityByDateDTO();
				BeanUtils.copyProperties(availabilityByDate, dateCountDTO);
				availabilityByDateCount.add(dateCountDTO);
			}
		} else {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session
					.createCriteria(AvailabilityByDateEntity.class);
			criteria.add(Restrictions.eq("categoryId", categoryId));
			criteria.add(Restrictions.eq("hotelID", hotelID));
			criteria.add(Restrictions.eq("roomId", roomNO));
			criteria.add(Restrictions.ge("date", previousDate));
			List<AvailabilityByDateEntity> availList = criteria.list();
			for (AvailabilityByDateEntity availabilityByDate : availList) {
				dateCountDTO = new AvailabilityByDateDTO();
				BeanUtils.copyProperties(availabilityByDate, dateCountDTO);
				availabilityByDateCount.add(dateCountDTO);
			}
		}

		logger.info("getRoomAvailByDaysCount dao : end");
		return availabilityByDateCount;
	}

	public List<AvailabilityByDateDTO> getDateListByRoomNo(Long roomNum,
			Date selectedDt, Long hotelID, Long categoryId) {
		logger.info("getDateListByRoomNo dao : start");
		logger.info("selected date is :" + selectedDt);
		List<AvailabilityByDateDTO> dateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO byDateDTO = null;
		if (roomNum == 0) {
			logger.info("room num is not selected");
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session
					.createCriteria(AvailabilityByDateEntity.class);
			criteria.add(Restrictions.eq("categoryId", categoryId));
			criteria.add(Restrictions.eq("hotelID", hotelID));
			criteria.add(Restrictions.ge("date", selectedDt));
			List<AvailabilityByDateEntity> list = criteria.list();
			for (AvailabilityByDateEntity availabilityByDateEntity : list) {
				byDateDTO = new AvailabilityByDateDTO();
				BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
				dateDTOs.add(byDateDTO);
			}
		} else {
			logger.info("room num selected");
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session
					.createCriteria(AvailabilityByDateEntity.class);
			criteria.add(Restrictions.eq("categoryId", categoryId));
			criteria.add(Restrictions.eq("hotelID", hotelID));
			criteria.add(Restrictions.eq("roomId", roomNum));
			criteria.add(Restrictions.ge("date", selectedDt));
			List<AvailabilityByDateEntity> list = criteria.list();
			for (AvailabilityByDateEntity availabilityByDateEntity : list) {
				byDateDTO = new AvailabilityByDateDTO();
				BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
				dateDTOs.add(byDateDTO);
			}
		}
		logger.info("avail dates by room No :" + dateDTOs);
		logger.info("getDateListByRoomNo dao : end");

		return dateDTOs;
	}

}
