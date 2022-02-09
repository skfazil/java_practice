package com.cushina.model.dataaccess.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.cushina.model.dataaccess.ReservationDao;
import com.cushina.model.pojo.AvailabilityByDateEntity;
import com.cushina.model.pojo.BookingHistoryEntity;
import com.cushina.model.pojo.HotelAvailabilityEntity;
import com.cushina.web.bean.BookingHistoryBean;

@Repository
public class ReservationDaoImpl implements ReservationDao {

	private Logger logger = Logger
			.getLogger(ReservationDaoImpl.class.getName());

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked" })
	public boolean isSave(BookingHistoryBean bean) {
		logger.info("isSave daoImpl : start");
		logger.info("bean::::" + bean);
		Long reservationNumber = bean.getReservationNumber();
		boolean isSave = false;
		Integer userId = bean.getUserId();
		Integer guestUserId = bean.getGuestUserId();

		Session session = sessionFactory.getCurrentSession();
		BookingHistoryEntity historyEntity = new BookingHistoryEntity();
		BeanUtils.copyProperties(bean, historyEntity);
		Serializable save = session.save(historyEntity);

		Date checkInDate = bean.getCheckInDate();
		Date checkOutDate = bean.getCheckOutDate();

		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDate);
		cal.add(Calendar.DATE, -1);
		Date checkedOutDate = cal.getTime();

		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");
		String checkin = srcForamt.format(checkInDate);
		String date = srcForamt.format(checkedOutDate);
		logger.info("checked out date :: ---> " + date);

		int executeUpdate = 0;
		if (save != null) {
			Session currentSession = sessionFactory.getCurrentSession();
			String getList = "From HotelAvailabilityEntity where categoryId="
					+ bean.getCategoryId() + " and hotelID="
					+ bean.getHotelID() + " AND date BETWEEN '" + checkin
					+ "' AND '" + date + "' ";
			Query cuery = currentSession.createQuery(getList);
			List<HotelAvailabilityEntity> list = cuery.list();
			Long available = 0L;
			Long booked = 0L;
			for (HotelAvailabilityEntity hotelAvailabilityDTO : list) {
				available = hotelAvailabilityDTO.getAvailable();
				booked = hotelAvailabilityDTO.getBooked();
				Date availDt = hotelAvailabilityDTO.getDate();
				String availableDt = srcForamt.format(availDt);
				String query = "update hotel_availability set booked=" + booked
						+ "+1,available=" + available + "-1 WHERE categoryId="
						+ bean.getCategoryId() + " AND  hotelID="
						+ bean.getHotelID() + " AND date='" + availableDt
						+ "' ";
				Query createQuery = currentSession.createSQLQuery(query);
				executeUpdate = createQuery.executeUpdate();
			}
			logger.info("executeUpdate::::::::::::::" + executeUpdate);
		}
		if (executeUpdate > 0) {
			Session currentSession1 = sessionFactory.getCurrentSession();

			String query1 = "update AvailabilityByDateEntity set statusCode='B',colourCode='gray',userId=?,guestUsrId=?,reservationNumber=? where roomId="
					+ bean.getRoomId()
					+ " and categoryId="
					+ bean.getCategoryId()
					+ " and hotelID="
					+ bean.getHotelID()
					+ " AND date BETWEEN '"
					+ checkin
					+ "' AND '" + date + "' ";
			logger.info("query ---> ::: " + query1);
			Query createQuery1 = currentSession1.createQuery(query1);

			logger.info("userId  = " + userId);
			logger.info("guest userId  = " + guestUserId);
			if (userId != null) {// .equqls()null
				createQuery1.setInteger(0, userId);
				createQuery1.setString(1, null);
				createQuery1.setLong(2, reservationNumber);
			} else {
				createQuery1.setString(0, null);
				createQuery1.setInteger(1, guestUserId);
				createQuery1.setLong(2, reservationNumber);
			}

			logger.info("query :" + createQuery1);
			int update = createQuery1.executeUpdate();
			if (update > 0) {
				isSave = true;
			}
		}
		logger.info("isSave daoImpl : end");
		return isSave;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getRoomAvailByDaysCount(Long categoryId,
			Long hotelID, Long roomNO, Integer dayCount, Date selectedDt) {
		logger.info("getRoomAvailByDaysCount dao : start");
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDt);
		cal.add(Calendar.DATE, -1);
		Date previousDate = cal.getTime();// get previous date

		List<AvailabilityByDateDTO> availabilityByDateCount = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dateCountDTO = null;
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
		logger.info("getRoomAvailByDaysCount dao : end");
		return availabilityByDateCount;
	}

	public AvailabilityByDateDTO reserveSelectedRoom(Long id) {
		logger.info("reserveSelectedRoom dao : start");
		Session session = sessionFactory.getCurrentSession();
		AvailabilityByDateEntity availEntity = (AvailabilityByDateEntity) session
				.load(AvailabilityByDateEntity.class, id);
		AvailabilityByDateDTO availRecord = new AvailabilityByDateDTO();
		BeanUtils.copyProperties(availEntity, availRecord);

		logger.info("reserveSelectedRoom dao : end");
		return availRecord;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public List<AvailabilityByDateDTO> getAvailDatesToReserveRoom(Long hotelId,
			Date selectedDt, Long roomNum) {
		logger.info("getAvailDatesToReserveRoom : start");
		logger.info("selected date is :" + selectedDt);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<AvailabilityByDateDTO> byDateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.ge("date", selectedDt));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		criteria.add(Restrictions.eq("roomId", roomNum));
		List<AvailabilityByDateEntity> availList = criteria.list();
		for (AvailabilityByDateEntity availabilityByDateEntity : availList) {
			dateDTO = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateEntity, dateDTO);
			byDateDTOs.add(dateDTO);
		}
		logger.info("getAvailDatesToReserveRoom : end");
		return byDateDTOs;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public Long deleteReservation(Long bookingID) {
		logger.info("isDeleteReservation daoImpl : start");
		boolean isCancel = false;
		int executeUpdate = 0;
		Session session = sessionFactory.getCurrentSession();
		BookingHistoryEntity bkngHistry = (BookingHistoryEntity) session.load(
				BookingHistoryEntity.class, bookingID);
		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");

		Date checkOutDateVal = bkngHistry.getCheckOutDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDateVal);
		cal.add(Calendar.DATE, -1);
		Date checkOutDate = cal.getTime();

		String checkin = srcForamt.format(bkngHistry.getCheckInDate());

		String checkedOutDate = srcForamt.format(checkOutDate);
		String getList = "From HotelAvailabilityEntity where categoryId="
				+ bkngHistry.getCategoryId() + " and hotelID="
				+ bkngHistry.getHotelID() + " AND date BETWEEN '" + checkin
				+ "' AND '" + checkedOutDate + "' ";
		Query cuery = session.createQuery(getList);
		List<HotelAvailabilityEntity> list = cuery.list();
		Long available = 0L;
		Long booked = 0L;
		for (HotelAvailabilityEntity hotelAvailabilityDTO : list) {
			available = hotelAvailabilityDTO.getAvailable();
			booked = hotelAvailabilityDTO.getBooked();
			Date availDt = hotelAvailabilityDTO.getDate();
			String availableDt = srcForamt.format(availDt);
			String query = "update hotel_availability set booked=" + booked
					+ "-1,available=" + available + "+1 WHERE categoryId="
					+ bkngHistry.getCategoryId() + " AND  hotelID="
					+ bkngHistry.getHotelID() + " AND date='" + availableDt
					+ "' ";
			Query createQuery = session.createSQLQuery(query);
			executeUpdate = createQuery.executeUpdate();
		}
		Long reservationNumber = bkngHistry.getReservationNumber();
		if (executeUpdate > 0) {
			String query = "update AvailabilityByDateEntity set statusCode='A',colourCode='green',userId=?,reservationNumber=?,guestUsrId=?  where roomId="
					+ bkngHistry.getRoomId()
					+ " and categoryId="
					+ bkngHistry.getCategoryId()
					+ " and hotelID="
					+ bkngHistry.getHotelID()
					+ " AND date BETWEEN '"
					+ checkin
					+ "' AND '" + checkedOutDate + "' ";
			Query createQuery1 = session.createQuery(query);
			createQuery1.setString(0, null);
			createQuery1.setString(1, null);
			createQuery1.setString(2, null);
			int update = createQuery1.executeUpdate();
			// set status as delete.
			bkngHistry.setStatus("delete");
			String status = bkngHistry.getStatus();
			
			logger.info("booking status after cancel reservation *** :"+status);
			session.saveOrUpdate(bkngHistry);
			logger.info("reservation number ::: "
					+ reservationNumber);
			isCancel = true;
		}
		logger.info("isDeleteReservation daoImpl : end");
		return reservationNumber;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public Long cancelReservation(Long bookingID) {
		logger.info("cancelReservation daoImpl : start");
		boolean isCancel = false;
		int executeUpdate = 0;
		Session session = sessionFactory.getCurrentSession();
		BookingHistoryEntity bkngHistry = (BookingHistoryEntity) session.load(
				BookingHistoryEntity.class, bookingID);
		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");
		Date checkOutDateVal = bkngHistry.getCheckOutDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDateVal);
		cal.add(Calendar.DATE, -1);
		Date checkOutDate = cal.getTime();
		String checkin = srcForamt.format(bkngHistry.getCheckInDate());
		String checkedOutDate = srcForamt.format(checkOutDate);

		String getList = "From HotelAvailabilityEntity where categoryId="
				+ bkngHistry.getCategoryId() + " and hotelID="
				+ bkngHistry.getHotelID() + " AND date BETWEEN '" + checkin
				+ "' AND '" + checkedOutDate + "' ";
		Query cuery = session.createQuery(getList);
		List<HotelAvailabilityEntity> list = cuery.list();
		Long available = 0L;
		Long booked = 0L;
		for (HotelAvailabilityEntity hotelAvailabilityDTO : list) {
			available = hotelAvailabilityDTO.getAvailable();
			booked = hotelAvailabilityDTO.getBooked();
			Date availDt = hotelAvailabilityDTO.getDate();
			String availableDt = srcForamt.format(availDt);
			String query = "update hotel_availability set booked=" + booked
					+ "-1,available=" + available + "+1 WHERE categoryId="
					+ bkngHistry.getCategoryId() + " AND  hotelID="
					+ bkngHistry.getHotelID() + " AND date='" + availableDt
					+ "' ";
			Query createQuery = session.createSQLQuery(query);
			executeUpdate = createQuery.executeUpdate();
		}
		if (executeUpdate > 0) {
			String query = "update AvailabilityByDateEntity set statusCode='A',colourCode='green',userId=?,reservationNumber=?,guestUsrId=?  where roomId="
					+ bkngHistry.getRoomId()
					+ " and categoryId="
					+ bkngHistry.getCategoryId()
					+ " and hotelID="
					+ bkngHistry.getHotelID()
					+ " AND date BETWEEN '"
					+ checkin
					+ "' AND '" + checkedOutDate + "' ";
			Query createQuery1 = session.createQuery(query);
			createQuery1.setString(0, null);
			createQuery1.setString(1, null);
			createQuery1.setString(2, null);
			int update = createQuery1.executeUpdate();
			// cancel bookinHistory record from hotel_reservation table.
			bkngHistry.setStatus("cancel");
			String status = bkngHistry.getStatus();
			session.saveOrUpdate(bkngHistry);
			logger.info("reservation number ::: "
					+ bkngHistry.getReservationNumber());
			isCancel = true;
		}

		logger.info("cancelReservation daoImpl : end");
		return bkngHistry.getReservationNumber();
	}

	@SuppressWarnings("unchecked")
	public boolean isReservationProgress(Long hotelID, Long categoryId,
			Long roomId, Date checkedInDt, Date checkedoutdate) {
		logger.info("isReservationProgress daoImpl : start");
		boolean isProgress = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkedoutdate);
		cal.add(Calendar.DATE, -1);
		checkedoutdate = cal.getTime();

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		criteria.add(Restrictions.eq("categoryId", categoryId));
		criteria.add(Restrictions.eq("roomId", roomId));
		criteria.add(Restrictions.between("date", checkedInDt, checkedoutdate));
		List<AvailabilityByDateEntity> resvtnPrgrsDts = criteria.list();
		logger.info("resvtnPrgrsDts :: " + resvtnPrgrsDts);
		for (AvailabilityByDateEntity availabilityByDateEntity : resvtnPrgrsDts) {
			String statusCode = availabilityByDateEntity.getStatusCode();
			if (statusCode.equals("Y") || statusCode.equals("B")) {
				isProgress = false;
				break;
			}
			availabilityByDateEntity.setStatusCode("Y");
			session.saveOrUpdate(availabilityByDateEntity);
			isProgress = true;
		}
		logger.info("isReservationProgress daoImpl : end");
		return isProgress;
	}

	@SuppressWarnings("unchecked")
	public boolean isChangeReservaion(Long bookingID) {
		logger.info("isChangeReservaion daoImpl : start");
		boolean isChange = false;
		int executeUpdate = 0;
		Session session = sessionFactory.getCurrentSession();
		BookingHistoryEntity bkngHistry = (BookingHistoryEntity) session.load(
				BookingHistoryEntity.class, bookingID);

		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");
		String checkin = srcForamt.format(bkngHistry.getCheckInDate());

		Date checkOutDate = bkngHistry.getCheckOutDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDate);
		cal.add(Calendar.DATE, -1);
		checkOutDate = cal.getTime();
		logger.info("checkedOutDate (previous date) ::: " + checkOutDate);
		String checkedOutDate = srcForamt.format(checkOutDate);
		String getList = "From HotelAvailabilityEntity where categoryId="
				+ bkngHistry.getCategoryId() + " and hotelID="
				+ bkngHistry.getHotelID() + " AND date BETWEEN '" + checkin
				+ "' AND '" + checkedOutDate + "' ";
		Query cuery = session.createQuery(getList);
		List<HotelAvailabilityEntity> list = cuery.list();
		Long available = 0L;
		Long booked = 0L;
		for (HotelAvailabilityEntity hotelAvailabilityDTO : list) {
			available = hotelAvailabilityDTO.getAvailable();
			booked = hotelAvailabilityDTO.getBooked();
			Date availDt = hotelAvailabilityDTO.getDate();
			String availableDt = srcForamt.format(availDt);
			String query = "update hotel_availability set booked=" + booked
					+ "-1,available=" + available + "+1 WHERE categoryId="
					+ bkngHistry.getCategoryId() + " AND  hotelID="
					+ bkngHistry.getHotelID() + " AND date='" + availableDt
					+ "' ";
			Query createQuery = session.createSQLQuery(query);
			executeUpdate = createQuery.executeUpdate();
		}
		if (executeUpdate > 0) {
			String query = "update AvailabilityByDateEntity set statusCode='A',colourCode='green',userId=?,reservationNumber=? where roomId="
					+ bkngHistry.getRoomId()
					+ " and categoryId="
					+ bkngHistry.getCategoryId()
					+ " and hotelID="
					+ bkngHistry.getHotelID()
					+ " AND date BETWEEN '"
					+ checkin
					+ "' AND '" + checkedOutDate + "' ";
			Query createQuery1 = session.createQuery(query);
			createQuery1.setString(0, null);
			createQuery1.setString(1, null);

			int update = createQuery1.executeUpdate();
			// deleting bookinHistory record from hotel_reservation table.
			if (update > 0) {
				session.delete(bkngHistry);
				isChange = true;
			}
		}
		logger.info("isChangeReservaion daoImpl : end");
		return isChange;
	}

	public Long sameHotelReservtn(Long bookingID) {
		logger.info("sameHotelReservtn daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		BookingHistoryEntity histryRecord = (BookingHistoryEntity) session.load(
				BookingHistoryEntity.class, bookingID);
		Long hotelID = histryRecord.getHotelID();
		logger.info("sameHotelReservtn daoImpl : end");
		return hotelID;
	}

}
