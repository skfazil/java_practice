package com.cushina.model.dataaccess.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.model.dataaccess.ServiceProviderChangeRoomDao;
import com.cushina.model.pojo.BookingHistoryEntity;
import com.cushina.model.pojo.HotelAvailabilityEntity;

@Repository
public class ServiceProviderChangeRoomDaoImpl implements
		ServiceProviderChangeRoomDao {

	private Logger logger = Logger
			.getLogger(ServiceProviderChangeRoomDaoImpl.class.getName());

	@Autowired
	private SessionFactory sessionFactory;

	
	/*Long available = 0L;
	Long booked = 0L;
	@SuppressWarnings({ "unused" })
	public boolean isChangeReservation(AvailabilityByDateDTO draggableInfo,
			BookingHistoryDTO droppableInfo) {
		logger.info("isChangeReservation daoImpl : Start");
		boolean isDropable = false;
		Long reservationNumber = draggableInfo.getReservationNumber();
		Session session = sessionFactory.getCurrentSession();
		BookingHistoryDTO histryRecrd = new BookingHistoryDTO();
		histryRecrd.setReservationNumber(reservationNumber);
		
		boolean isUpdate = isUpdateDraggableRecrd(histryRecrd);
		if (isUpdate) {
			BookingHistoryDTO histryDetails = getHistryDetails(draggableInfo.getReservationNumber());
			String notes = histryDetails.getNotes();
			if(notes != null){
				droppableInfo.setNotes(notes);
			}else{
				droppableInfo.setNotes("");
			}
			droppableInfo.setArrived(histryDetails.isArrived());
			droppableInfo.setPaid(histryDetails.isPaid());
			droppableInfo.setEmailShare(histryDetails.getEmailShare());
			
			isDropable = isDroppableRoom(droppableInfo);
		}

		logger.info("isChangeReservation daoImpl : end");
		return isDropable;
	}*/

	/**
	 * This method is used to update status as "delete" in hotel_reservations
	 * table and reset values in hotel_availibility and availability_by_date
	 * tables.
	 * 
	 * @param histryRecrd
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public boolean isUpdateDraggableRecrd(BookingHistoryDTO histryInfo) {
		logger.info("isUpdateDraggableRecrd method : start");
		Long reservationNumber = histryInfo.getOldReservtnNumber();
		logger.info("old reservationNumber ::: *** "+reservationNumber);
		BookingHistoryEntity histryRecrd = new BookingHistoryEntity();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("reservationNumber", reservationNumber));
		List<BookingHistoryEntity> usrBkdRecrd = criteria.list();
		histryRecrd = usrBkdRecrd.get(0);
		boolean isUpdate = false;
		int executeUpdate = 0;
		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");
		String checkin = srcForamt.format(histryRecrd.getCheckInDate());
		Date checkOutDate = histryRecrd.getCheckOutDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDate);
		cal.add(Calendar.DATE, -1);
		checkOutDate = cal.getTime();
		String checkedOutDate = srcForamt.format(checkOutDate);
		String getList = "From HotelAvailabilityEntity where categoryId="
				+ histryRecrd.getCategoryId() + " and hotelID="
				+ histryRecrd.getHotelID() + " AND date BETWEEN '" + checkin
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
			String query = "update hotel_availability set booked=:booked ,"
					+ "available=:available WHERE categoryId="
					+ histryRecrd.getCategoryId() + " AND  hotelID="
					+ histryRecrd.getHotelID() + " AND date='" + availableDt
					+ "' ";
			Query createQuery = session.createSQLQuery(query);
			createQuery.setLong("booked", (booked - 1));
			createQuery.setLong("available", (available + 1));
			executeUpdate = createQuery.executeUpdate();

		}
		if (executeUpdate > 0) {
			String query = "update AvailabilityByDateEntity set statusCode='A',colourCode='green',userId=?,reservationNumber=?,guestUsrId=?  where roomId="
					+ histryRecrd.getRoomId()
					+ " and categoryId="
					+ histryRecrd.getCategoryId()
					+ " and hotelID="
					+ histryRecrd.getHotelID()
					+ " AND date BETWEEN '"
					+ checkin + "' AND '" + checkedOutDate + "' ";
			Query createQuery1 = session.createQuery(query);
			createQuery1.setString(0, null);
			createQuery1.setString(1, null);
			createQuery1.setString(2, null);

			int update = createQuery1.executeUpdate();
			if (update > 0) {
				histryRecrd.setStatus("delete");
				session.saveOrUpdate(histryRecrd);
				isUpdate = true;
			}
		}
		logger.info("isUpdateDraggableRecrd method : end");
		return isUpdate;
	}

	@SuppressWarnings("unchecked")
	public boolean isDroppableRoom(BookingHistoryDTO droppable) {
		logger.info("isDroppableRoom method : start");
		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");
		BookingHistoryEntity droppableInfo = new BookingHistoryEntity();
		BeanUtils.copyProperties(droppable, droppableInfo);
		Long reservationNumber = droppableInfo.getReservationNumber();
		logger.info("new reservationNumber ::: *** "+reservationNumber);
		logger.info("droppable info :"+droppable);
		boolean isSave = false;
		Integer userId = droppableInfo.getUserId();
		Integer guestUserId = droppableInfo.getGuestUserId();

		Session session = sessionFactory.getCurrentSession();
		BookingHistoryEntity historyEntity = new BookingHistoryEntity();
		BeanUtils.copyProperties(droppableInfo, historyEntity);
		Serializable save = session.save(historyEntity);

		Date checkInDate = droppableInfo.getCheckInDate();
		Date checkOutDate = droppableInfo.getCheckOutDate();

		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDate);
		cal.add(Calendar.DATE, -1);
		Date checkedOutDate = cal.getTime();

		String checkin = srcForamt.format(checkInDate);
		String checkOut = srcForamt.format(checkedOutDate);
		
		int executeUpdate = 0;
		if (save != null) {
			// session = sessionFactory.getCurrentSession();
			String getList = "From HotelAvailabilityEntity where categoryId="
					+ droppableInfo.getCategoryId() + " and hotelID="
					+ droppableInfo.getHotelID() + " AND date BETWEEN '"
					+ checkin + "' AND '" + checkOut + "' ";
			Query cuery = session.createQuery(getList);
			List<HotelAvailabilityEntity> list = cuery.list();
			Long available = 0L;
			Long booked = 0L;
			for (HotelAvailabilityEntity hotelAvailabilityDTO : list) {
				available = hotelAvailabilityDTO.getAvailable();
				booked = hotelAvailabilityDTO.getBooked();
				Date availDt = hotelAvailabilityDTO.getDate();
				String availableDt = srcForamt.format(availDt);
				String query = "update hotel_availability set booked=:booked,"
						+ "available=:available WHERE categoryId="
						+ droppableInfo.getCategoryId() + " AND  hotelID="
						+ droppableInfo.getHotelID() + " AND date='"
						+ availableDt + "' ";
				Query createQuery = session.createSQLQuery(query);
				createQuery.setLong("booked", (booked + 1));
				createQuery.setLong("available", (available - 1));
				executeUpdate = createQuery.executeUpdate();
				logger.info("droppable query :" + query);
			}
		}
		if (executeUpdate > 0) {
			// session = sessionFactory.getCurrentSession();

			String query1 = "update AvailabilityByDateEntity set statusCode='B',colourCode='gray',userId=?,guestUsrId=?,reservationNumber=? where roomId="
					+ droppableInfo.getRoomId()
					+ " and categoryId="
					+ droppableInfo.getCategoryId()
					+ " and hotelID="
					+ droppableInfo.getHotelID()
					+ " AND date BETWEEN '"
					+ checkin + "' AND '" + checkOut + "' ";
			Query createQuery = session.createQuery(query1);

			if (userId != null) {
				createQuery.setInteger(0, userId);
				createQuery.setString(1, null);
				createQuery.setLong(2, reservationNumber);
			} else {
				createQuery.setString(0, null);
				createQuery.setInteger(1, guestUserId);
				createQuery.setLong(2, reservationNumber);
			}

			int update = createQuery.executeUpdate();
			if (update > 0) {
				isSave = true;
			}
		}
		logger.info("isDroppableRoom daoImpl : end");
		return isSave;

	}

	@SuppressWarnings("unchecked")
	public BookingHistoryDTO getHistryDetails(Long reservationNumber) {
		logger.info("getHistryDetails daoImpl : start");
		BookingHistoryDTO histryDetail = new BookingHistoryDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("reservationNumber", reservationNumber));
		List<BookingHistoryEntity> usrBkdRecrd = criteria.list();
		BeanUtils.copyProperties( usrBkdRecrd.get(0), histryDetail);
		logger.info("getHistryDetails daoImpl : end");
		return histryDetail;
	}

}
