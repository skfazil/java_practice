package com.cushina.model.dataaccess.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.cushina.common.dto.BlackListServiceDTO;
import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestAndHotelReservationInfoDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListServiceDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.model.dataaccess.ServiceProviderDao;
import com.cushina.model.pojo.AvailabilityByDateEntity;
import com.cushina.model.pojo.BlackListServiceEntity;
import com.cushina.model.pojo.BlackListUsersEntity;
import com.cushina.model.pojo.BookingHistoryEntity;
import com.cushina.model.pojo.GuestUserEntity;
import com.cushina.model.pojo.HotelAvailabilityEntity;
import com.cushina.model.pojo.RoomInfoEntity;
import com.cushina.model.pojo.UserEntity;
import com.cushina.model.pojo.WhiteListServiceEntity;
import com.cushina.model.pojo.WhiteListUsersEntity;

@Repository
public class ServiceProviderDaoImpl implements ServiceProviderDao {

	private Logger logger = Logger.getLogger(ServiceProviderDaoImpl.class
			.getName());

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getRoomsOnCurrentDate(Long hotelId) {
		logger.info("getRoomsOnCurrentDate daoImpl : start");
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date currentDate = c.getTime();

		List<AvailabilityByDateDTO> roomOnCurrDtList = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO byDateDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(AvailabilityByDateEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		criteria.add(Restrictions.ge("date", currentDate));
		List<AvailabilityByDateEntity> roomsOnCurrentDate = criteria.list();
		for (AvailabilityByDateEntity availabilityByDateEntity : roomsOnCurrentDate) {
			byDateDTO = new AvailabilityByDateDTO();
			Integer userId = availabilityByDateEntity.getUserId();
			Integer guestUsrId = availabilityByDateEntity.getGuestUsrId();
			Long reservationNumber = availabilityByDateEntity
					.getReservationNumber();
			if (userId != null) {
				UserEntity user = (UserEntity) session.load(UserEntity.class,
						userId);
				byDateDTO.setUserName(user.getUserName());
				byDateDTO.setPhoneNumber(user.getPhoneNumber());
				byDateDTO.setUserId(user.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						reservationNumber));
				List<BookingHistoryEntity> list = createCriteria.list();

				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				byDateDTO.setPaid(bookingHistoryEntity.isPaid());
				byDateDTO.setArrived(bookingHistoryEntity.isArrived());
				byDateDTO.setNotes(bookingHistoryEntity.getNotes());
			} else if (guestUsrId != null) {
				GuestUserEntity guestUsr = (GuestUserEntity) session.load(
						GuestUserEntity.class, guestUsrId);

				byDateDTO.setUserName(guestUsr.getUserName());
				byDateDTO.setPhoneNumber(guestUsr.getPhoneNumber());
				byDateDTO.setUserId(guestUsr.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						reservationNumber));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				byDateDTO.setPaid(bookingHistoryEntity.isPaid());
				byDateDTO.setArrived(bookingHistoryEntity.isArrived());
				byDateDTO.setNotes(bookingHistoryEntity.getNotes());
			}
			BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
			roomOnCurrDtList.add(byDateDTO);
		}
		logger.info("getRoomsOnCurrentDate daoImpl : end");
		return roomOnCurrDtList;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsForProvider(
			String slectedDate, Long hotelID) {
		logger.info("getSelectedDateRooms in dao : start");
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		Date sltDate = null;
		try {
			sltDate = sd.parse(slectedDate);
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
		List<AvailabilityByDateEntity> availList = criteria.list();

		for (AvailabilityByDateEntity availabilityByDateEntity : availList) {
			dateDTO = new AvailabilityByDateDTO();
			Integer userId = availabilityByDateEntity.getUserId();
			Integer guestUsrId = availabilityByDateEntity.getGuestUsrId();
			if (userId != null) {
				UserEntity user = (UserEntity) session.load(UserEntity.class,
						userId);
				dateDTO.setUserName(user.getUserName());
				dateDTO.setPhoneNumber(user.getPhoneNumber());
				dateDTO.setUserId(user.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						availabilityByDateEntity.getReservationNumber()));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				dateDTO.setPaid(bookingHistoryEntity.isPaid());
				dateDTO.setArrived(bookingHistoryEntity.isArrived());
				dateDTO.setNotes(bookingHistoryEntity.getNotes());
			} else if (guestUsrId != null) {
				GuestUserEntity guestUsr = (GuestUserEntity) session.load(
						GuestUserEntity.class, guestUsrId);

				dateDTO.setUserName(guestUsr.getUserName());
				dateDTO.setPhoneNumber(guestUsr.getPhoneNumber());
				dateDTO.setUserId(guestUsr.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						availabilityByDateEntity.getReservationNumber()));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				dateDTO.setPaid(bookingHistoryEntity.isPaid());
				dateDTO.setArrived(bookingHistoryEntity.isArrived());
				dateDTO.setNotes(bookingHistoryEntity.getNotes());
			}
			BeanUtils.copyProperties(availabilityByDateEntity, dateDTO);
			byDateDTOs.add(dateDTO);
		}
		logger.info("getSelectedDateRooms in dao : end");
		return byDateDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsBasedOnCatgeoryForProvider(
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
			Integer userId = availabilityByDateEntity.getUserId();
			Integer guestUsrId = availabilityByDateEntity.getGuestUsrId();
			if (userId != null) {
				UserEntity user = (UserEntity) session.load(UserEntity.class,
						userId);
				dateDTO.setUserName(user.getUserName());
				dateDTO.setPhoneNumber(user.getPhoneNumber());
				dateDTO.setUserId(user.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						availabilityByDateEntity.getReservationNumber()));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				dateDTO.setPaid(bookingHistoryEntity.isPaid());
				dateDTO.setArrived(bookingHistoryEntity.isArrived());
				dateDTO.setNotes(bookingHistoryEntity.getNotes());
			} else if (guestUsrId != null) {
				GuestUserEntity guestUsr = (GuestUserEntity) session.load(
						GuestUserEntity.class, guestUsrId);
				dateDTO.setUserName(guestUsr.getUserName());
				dateDTO.setPhoneNumber(guestUsr.getPhoneNumber());
				dateDTO.setUserId(guestUsr.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						availabilityByDateEntity.getReservationNumber()));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				dateDTO.setPaid(bookingHistoryEntity.isPaid());
				dateDTO.setArrived(bookingHistoryEntity.isArrived());
				dateDTO.setNotes(bookingHistoryEntity.getNotes());
			}
			BeanUtils.copyProperties(availabilityByDateEntity, dateDTO);
			byDateDTOs.add(dateDTO);
		}
		logger.info("getSelectedDateRooms in dao : end");
		return byDateDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getRoomAvailByCategoryForProvider(
			Long categoryId, Long hotelID, Date selectedDt) {

		logger.info("getRoomAvailByCategoryForProvider in daoImpl : start");
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
			Integer userId = availabilityByDateEntity.getUserId();
			Integer guestUsrId = availabilityByDateEntity.getGuestUsrId();
			if (userId != null) {
				UserEntity user = (UserEntity) session.load(UserEntity.class,
						userId);
				byDateDTO.setUserName(user.getUserName());
				byDateDTO.setPhoneNumber(user.getPhoneNumber());
				byDateDTO.setUserId(user.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						availabilityByDateEntity.getReservationNumber()));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				byDateDTO.setPaid(bookingHistoryEntity.isPaid());
				byDateDTO.setArrived(bookingHistoryEntity.isArrived());
				byDateDTO.setNotes(bookingHistoryEntity.getNotes());
			} else if (guestUsrId != null) {
				GuestUserEntity guestUsr = (GuestUserEntity) session.load(
						GuestUserEntity.class, guestUsrId);
				byDateDTO.setUserName(guestUsr.getUserName());
				byDateDTO.setPhoneNumber(guestUsr.getPhoneNumber());
				byDateDTO.setUserId(guestUsr.getUserId());
				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						availabilityByDateEntity.getReservationNumber()));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				byDateDTO.setPaid(bookingHistoryEntity.isPaid());
				byDateDTO.setArrived(bookingHistoryEntity.isArrived());
				byDateDTO.setNotes(bookingHistoryEntity.getNotes());
			}
			BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
			byDateDTOs.add(byDateDTO);
		}
		logger.info("getRoomAvailByCategoryForProvider in daoImpl : end");
		return byDateDTOs;

	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getDateListOnSelectedRoomNo(
			Long roomNum, Date selectedDt, Long hotelID, Long categoryId) {

		logger.info("getDateListOnSelectedRoomNo daoImpl : start");
		logger.info("selected date is :" + selectedDt);
		List<AvailabilityByDateDTO> dateDTOs = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO byDateDTO = null;
		if (roomNum == 0) {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session
					.createCriteria(AvailabilityByDateEntity.class);
			criteria.add(Restrictions.eq("categoryId", categoryId));
			criteria.add(Restrictions.eq("hotelID", hotelID));
			criteria.add(Restrictions.ge("date", selectedDt));
			List<AvailabilityByDateEntity> list = criteria.list();
			for (AvailabilityByDateEntity availabilityByDateEntity : list) {
				byDateDTO = new AvailabilityByDateDTO();
				Integer userId = availabilityByDateEntity.getUserId();
				Integer guestUsrId = availabilityByDateEntity.getGuestUsrId();
				if (userId != null) {
					UserEntity user = (UserEntity) session.load(
							UserEntity.class, userId);
					byDateDTO.setUserName(user.getUserName());
					byDateDTO.setPhoneNumber(user.getPhoneNumber());
					byDateDTO.setUserId(user.getUserId());
				} else if (guestUsrId != null) {
					GuestUserEntity guestUsr = (GuestUserEntity) session.load(
							GuestUserEntity.class, guestUsrId);
					byDateDTO.setUserName(guestUsr.getUserName());
					byDateDTO.setPhoneNumber(guestUsr.getPhoneNumber());
					byDateDTO.setGuestUsrId(guestUsr.getUserId());
				}
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
				Integer userId = availabilityByDateEntity.getUserId();
				Integer guestUsrId = availabilityByDateEntity.getGuestUsrId();
				if (userId != null) {
					UserEntity user = (UserEntity) session.load(
							UserEntity.class, userId);
					byDateDTO.setUserName(user.getUserName());
					byDateDTO.setPhoneNumber(user.getPhoneNumber());
					byDateDTO.setUserId(user.getUserId());
				} else if (guestUsrId != null) {
					GuestUserEntity guestUsr = (GuestUserEntity) session.load(
							GuestUserEntity.class, guestUsrId);
					byDateDTO.setUserName(guestUsr.getUserName());
					byDateDTO.setPhoneNumber(guestUsr.getPhoneNumber());
					byDateDTO.setGuestUsrId(guestUsr.getUserId());
				}
				BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
				dateDTOs.add(byDateDTO);
			}
		}
		logger.info("getDateListOnSelectedRoomNo daoImpl : end");
		return dateDTOs;

	}

	@SuppressWarnings("unchecked")
	public List<AvailabilityByDateDTO> getRoomAvailByCategoryAndRoomNum(
			Long categoryId, Long hotelID, Long roomNO, Date currDt) {
		logger.info("getRoomAvailByCategoryAndRoomNum in daoImpl : start");

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
			Integer userId = availabilityByDateEntity.getUserId();
			Integer guestUsrId = availabilityByDateEntity.getGuestUsrId();
			Long reservationNumber = availabilityByDateEntity
					.getReservationNumber();
			if (userId != null) {
				UserEntity user = (UserEntity) session.load(UserEntity.class,
						userId);
				byDateDTO.setUserName(user.getUserName());
				byDateDTO.setPhoneNumber(user.getPhoneNumber());
				byDateDTO.setUserId(user.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						reservationNumber));
				List<BookingHistoryEntity> list = createCriteria.list();

				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				byDateDTO.setPaid(bookingHistoryEntity.isPaid());
				byDateDTO.setArrived(bookingHistoryEntity.isArrived());
				byDateDTO.setNotes(bookingHistoryEntity.getNotes());

			} else if (guestUsrId != null) {
				GuestUserEntity guestUsr = (GuestUserEntity) session.load(
						GuestUserEntity.class, guestUsrId);
				byDateDTO.setUserName(guestUsr.getUserName());
				byDateDTO.setPhoneNumber(guestUsr.getPhoneNumber());
				byDateDTO.setGuestUsrId(guestUsr.getUserId());

				Criteria createCriteria = session
						.createCriteria(BookingHistoryEntity.class);
				createCriteria.add(Restrictions.eq("reservationNumber",
						reservationNumber));
				List<BookingHistoryEntity> list = createCriteria.list();
				BookingHistoryEntity bookingHistoryEntity = list.get(0);
				byDateDTO.setPaid(bookingHistoryEntity.isPaid());
				byDateDTO.setArrived(bookingHistoryEntity.isArrived());
				byDateDTO.setNotes(bookingHistoryEntity.getNotes());
			}
			BeanUtils.copyProperties(availabilityByDateEntity, byDateDTO);
			byDateDTOs.add(byDateDTO);
		}
		logger.info("getRoomAvailByCategoryAndRoomNum in daoImpl : end");
		return byDateDTOs;
	}

	public UserDTO getUserDetails(Integer userId) {
		logger.info("getUserDetails daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		UserEntity user = (UserEntity) session.load(UserEntity.class, userId);
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		logger.info("getUserDetails daoImpl : end");
		return userDTO;
	}

	public GuestUserDTO getGuestUserDetails(Integer userId) {
		logger.info("getGuestUserDetails daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		GuestUserEntity guestUser = (GuestUserEntity) session.load(
				GuestUserEntity.class, userId);
		GuestUserDTO guestUserDTO = new GuestUserDTO();
		BeanUtils.copyProperties(guestUser, guestUserDTO);
		logger.info("getGuestUserDetails daoImpl : end");
		return guestUserDTO;
	}

	@SuppressWarnings("unchecked")
	public BookingHistoryDTO getUserReservationHistory(Integer userId,
			Integer gustUsrId, Date checkInDate, Integer roomId,
			Integer categryId, Long reservationNumber) {
		logger.info("getUserReservationHIstory daoImpl : start");

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("reservationNumber", reservationNumber));

		List<BookingHistoryEntity> resevtnDetails = criteria.list();
		BookingHistoryDTO histryDetails = null;
		for (BookingHistoryEntity bookingHistoryEntity : resevtnDetails) {
			histryDetails = new BookingHistoryDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, histryDetails);
		}
		logger.info("getUserReservationHIstory daoImpl : end");
		return histryDetails;
	}

	@SuppressWarnings("unchecked")
	public boolean isPaymentPaid(Integer userId, Long hotelId,
			Integer guestUsrId, Long reservtnNum) {
		logger.info(" isPaymentPaid  daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		if (userId != 0)
			criteria.add(Restrictions.eq("userId", userId));
		else if (guestUsrId != 0)
			criteria.add(Restrictions.eq("guestUserId", guestUsrId));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		criteria.add(Restrictions.eq("reservationNumber", reservtnNum));
		List<BookingHistoryEntity> bookingHistry = criteria.list();
		BookingHistoryEntity bookingHistoryEntity = bookingHistry.get(0);
		bookingHistoryEntity.setPaid(true);
		session.saveOrUpdate(bookingHistoryEntity);
		logger.info(" isPaymentPaid  daoImpl: end");
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean isArrived(Integer userId, Long hotelId, Integer guestUsrId,
			Long reservtnNum) {
		logger.info("isArrived  daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		if (userId != 0)
			criteria.add(Restrictions.eq("userId", userId));
		else if (guestUsrId != 0)
			criteria.add(Restrictions.eq("guestUserId", guestUsrId));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		criteria.add(Restrictions.eq("reservationNumber", reservtnNum));
		List<BookingHistoryEntity> bookingHistry = criteria.list();
		BookingHistoryEntity bookingHistoryEntity = bookingHistry.get(0);
		bookingHistoryEntity.setArrived(true);
		session.saveOrUpdate(bookingHistoryEntity);
		logger.info("isArrived  daoImpl: end");
		return true;
	}

	public Integer saveUserDetails(UserDTO userDTO) {
		logger.info("saveUserDetails daoImpl : start");
		Integer userId = null;
		UserEntity userDetails = new UserEntity();
		BeanUtils.copyProperties(userDTO, userDetails);
		Session session = sessionFactory.getCurrentSession();
		Serializable save = session.save(userDetails);
		if (save != null) {
			userId = getUserIdByUserName(userDTO.getUserName());
		}
		logger.info("saveUserDetails daoImpl : end");
		return userId;
	}

	@SuppressWarnings("unchecked")
	private Integer getUserIdByUserName(String userName) {
		logger.info("getUserIdByUserName dao : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userName", userName));
		List<UserEntity> list = criteria.list();
		Integer userId = 0;
		for (UserEntity userEntity : list) {
			userId = userEntity.getUserId();
		}
		logger.info("userId:::::::::::::" + userId);
		logger.info("getUserIdByUserName dao : end");
		return userId;
	}

	@SuppressWarnings("unchecked")
	public boolean isSaveManualReservation(BookingHistoryDTO historyDTO) {
		logger.info("isSaveManualReservation : start");
		Long reservationNumber = historyDTO.getReservationNumber();
		boolean isSave = false;
		Session session = sessionFactory.getCurrentSession();
		BookingHistoryEntity historyEntity = new BookingHistoryEntity();
		BeanUtils.copyProperties(historyDTO, historyEntity);
		session.save(historyEntity);
		Date checkOutDate = historyDTO.getCheckOutDate();

		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDate);
		cal.add(Calendar.DATE, -1);
		checkOutDate = cal.getTime();
		Date checkInDate = historyDTO.getCheckInDate();
		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");
		String checkin = srcForamt.format(checkInDate);
		String checkoutDt = srcForamt.format(checkOutDate);

		Integer guestUserId = historyDTO.getGuestUserId();
		int executeUpdate = 0;

		Session currentSession = sessionFactory.getCurrentSession();
		String getList = "From HotelAvailabilityEntity where categoryId="
				+ historyDTO.getCategoryId() + " and hotelID="
				+ historyDTO.getHotelID() + " AND date BETWEEN '" + checkin

				+ "' AND '" + checkoutDt + "' ";

		Query cuery = currentSession.createQuery(getList);
		List<HotelAvailabilityEntity> list = cuery.list();
		Long available = 0L;
		Long booked = 0L;
		for (HotelAvailabilityEntity hotelAvailabilityDTO : list) {
			logger.info("inside foreach hoteAvailabilityDTO : "
					+ hotelAvailabilityDTO);
			available = hotelAvailabilityDTO.getAvailable();
			booked = hotelAvailabilityDTO.getBooked();
			Date availDt = hotelAvailabilityDTO.getDate();
			String availableDt = srcForamt.format(availDt);
			String query = "update hotel_availability set booked=" + booked
					+ "+1,available=" + available + "-1 WHERE categoryId="
					+ historyDTO.getCategoryId() + " AND  hotelID="
					+ historyDTO.getHotelID() + " AND date='" + availableDt
					+ "' ";
			Query createQuery = currentSession.createSQLQuery(query);
			executeUpdate = createQuery.executeUpdate();
		}

		if (executeUpdate > 0) {
			Session currentSession1 = sessionFactory.getCurrentSession();
			String query1 = "update AvailabilityByDateEntity set statusCode='B',colourCode='gray',userId=?,reservationNumber=?,guestUsrId=? where roomId="
					+ historyDTO.getRoomId()
					+ " and categoryId="
					+ historyDTO.getCategoryId()
					+ " and hotelID="
					+ historyDTO.getHotelID() + " AND date BETWEEN '" + checkin

					+ "' AND '" + checkoutDt + "' ";

			Query createQuery1 = currentSession1.createQuery(query1);

			if (guestUserId != null) {
				createQuery1.setString(0, null);
				createQuery1.setLong(1, reservationNumber);
				createQuery1.setInteger(2, guestUserId);
			} else {
				createQuery1.setString(0, null);
			}

			int update = createQuery1.executeUpdate();
			if (update > 0) {
				isSave = true;
			}
		}
		logger.info("isSaveManualReservation : end");
		return isSave;
	}

	public boolean isCustmrAddToWhtLst(Integer userId, Long hotelId,
			Integer guestUsrId) {
		logger.info("isCustmrAddToWhtLst daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		boolean isAdded = false;
		WhiteListUsersEntity whiteLst = new WhiteListUsersEntity();
		boolean isCustmrExists = isCustmrExistsInWhiteList(userId, hotelId,
				guestUsrId);
		if (isCustmrExists) {
			isAdded = false;// customer already added to black list category.
		} else {
			whiteLst.setHotelID(hotelId);
			if (userId != 0) {
				whiteLst.setUserId(userId);
			}
			if (guestUsrId != 0) {
				whiteLst.setGuestUserId(guestUsrId);
			}
			Date dt = new Date();
			whiteLst.setStartDate(dt);
			session.save(whiteLst);
			isAdded = true;
		}
		logger.info("isCustmrAddToWhtLst daoImpl : end");
		return isAdded;
	}

	public boolean isCustmrAddToBlckLst(Integer userId, Long hotelId,
			Integer guestUsrId) {
		logger.info("isCustmrAddToBlckLst daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		boolean isAdded = false;
		BlackListUsersEntity blckLst = new BlackListUsersEntity();
		boolean isBlackCustmrExists = isCustmrExistsInBlackList(userId,
				hotelId, guestUsrId);
		if (isBlackCustmrExists) {
			isAdded = false;// customer already added to black list category.
		} else {
			blckLst.setHotelID(hotelId);
			if (userId != 0) {
				blckLst.setUserId(userId);
			}
			if (guestUsrId != 0) {
				blckLst.setGuestUserId(guestUsrId);
			}
			Date dt = new Date();
			blckLst.setStartDate(dt);
			session.save(blckLst);
			isAdded = true;// customer added to black list category.
		}
		logger.info("isCustmrAddToBlckLst daoImpl : end");
		return isAdded;
	}

	@SuppressWarnings("unchecked")
	public boolean isCustmrExistsInWhiteList(Integer userId, Long hotelId,
			Integer guestUsrId) {
		logger.info("isCustmrExistsWhiteList method : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		if (userId != 0) {
			criteria.add(Restrictions.eq("userId", userId));
			criteria.add(Restrictions.eq("hotelID", hotelId));
		} else if (guestUsrId != 0) {
			criteria.add(Restrictions.eq("guestUserId", guestUsrId));
			criteria.add(Restrictions.eq("hotelID", hotelId));
		}
		List<WhiteListUsersEntity> list = criteria.list();
		logger.info("isCustmrExistsWhiteList methid : end");
		return list.size() > 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	public boolean isCustmrExistsInBlackList(Integer userId, Long hotelId,
			Integer guestUsrId) {
		logger.info("isBlackCustmrExists method : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		if (userId != 0) {
			criteria.add(Restrictions.eq("userId", userId));
			criteria.add(Restrictions.eq("hotelID", hotelId));
		} else if (guestUsrId != 0) {
			criteria.add(Restrictions.eq("guestUserId", guestUsrId));
			criteria.add(Restrictions.eq("hotelID", hotelId));
		}
		List<BlackListUsersEntity> list = criteria.list();
		logger.info("isWhiteCustmrExists methid : end");
		return list.size() > 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> getCustomersList(Long hotelId) {
		logger.info("getCustomersList daoImpl : start");
		List<UserDTO> custmrLst = new ArrayList<UserDTO>();
		UserDTO custmr = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		// criteria.add(Restrictions.eq("hotelID", hotelId));
		List<UserEntity> custmrList = criteria.list();
		for (UserEntity custmrEntity : custmrList) {
			custmr = new UserDTO();
			BeanUtils.copyProperties(custmrEntity, custmr);
			custmrLst.add(custmr);
		}
		logger.info("customer list in daoImpl :" + custmrLst);
		logger.info("getCustomersList daoImpl : end");
		return custmrLst;
	}

	/**
	 * This method is used to get particular customer history,the which he
	 * entered customer name in search box.
	 */
	@SuppressWarnings("unchecked")
	public List<BookingHistoryDTO> getCustmrReservedHistry(Integer userId) {
		logger.info("getCustmrReservedHistry daoIml : start");
		List<BookingHistoryDTO> histryDTOList = new ArrayList<BookingHistoryDTO>();
		BookingHistoryDTO hstryDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<BookingHistoryEntity> histryList = criteria.list();
		for (BookingHistoryEntity bookingHistoryEntity : histryList) {
			hstryDTO = new BookingHistoryDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, hstryDTO);
			histryDTOList.add(hstryDTO);
		}
		logger.info("getCustmrReservedHistry daoIml : end");
		return histryDTOList;
	}

	/**
	 * this method is used to get all customers history based on hotelId.
	 */
	@SuppressWarnings("unchecked")
	public List<BookingHistoryDTO> getCustmrsHistry(Long hotelId) {
		logger.info("getCustmrsHistry daoImpl : start");
		List<BookingHistoryDTO> histryDTOLst = new ArrayList<BookingHistoryDTO>();
		BookingHistoryDTO historyDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<BookingHistoryEntity> histryLst = criteria.list();
		for (BookingHistoryEntity bookingHistoryEntity : histryLst) {
			historyDTO = new BookingHistoryDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, historyDTO);
			histryDTOLst.add(historyDTO);
		}
		logger.info("getCustmrsHistry daoImpl : end");
		return histryDTOLst;
	}

	@SuppressWarnings("unchecked")
	public List<BookingHistoryDTO> getGuestUserReservtnHistry(
			Integer guestUserId) {
		logger.info("getGuestUserReservtnHistry  daoImpl : Start");
		List<BookingHistoryDTO> histryDTOList = new ArrayList<BookingHistoryDTO>();
		BookingHistoryDTO hstryDTO = null;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("guestUserId", guestUserId));
		List<BookingHistoryEntity> histryList = criteria.list();
		for (BookingHistoryEntity bookingHistoryEntity : histryList) {
			hstryDTO = new BookingHistoryDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, hstryDTO);
			histryDTOList.add(hstryDTO);
		}
		logger.info("getGuestUserReservtnHistry  daoImpl : end");
		return histryDTOList;
	}

	@SuppressWarnings("unchecked")
	public List<WhiteListUsersDTO> getAllWhiteLstUsrs(Long hotelId) {
		logger.info("getAllWhiteLstUsrs daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<WhiteListUsersDTO> whiteUsrs = new ArrayList<WhiteListUsersDTO>();
		WhiteListUsersDTO whiteUsr = null;
		List<WhiteListUsersEntity> whiteListUsrs = criteria.list();
		for (WhiteListUsersEntity whiteListUsersEntity : whiteListUsrs) {
			whiteUsr = new WhiteListUsersDTO();
			BeanUtils.copyProperties(whiteListUsersEntity, whiteUsr);
			whiteUsrs.add(whiteUsr);
		}
		logger.info("getAllWhiteLstUsrs daoImpl : end");
		return whiteUsrs;
	}

	@SuppressWarnings("unchecked")
	public WhiteListUsersDTO getWhiteLstCustmr(Integer userId, Long hotelId) {
		logger.info("getWhiteLstCustmr daoImpl : start");
		WhiteListUsersDTO whtUsr = new WhiteListUsersDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));// hotelID
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<WhiteListUsersEntity> whiteCustmr = criteria.list();
		if (whiteCustmr.size() > 0)
			BeanUtils.copyProperties(whiteCustmr.get(0), whtUsr);
		logger.info("getWhiteLstCustmr daoImpl : end");
		return whtUsr;
	}

	@SuppressWarnings("unchecked")
	public WhiteListUsersDTO getWhiteLstGuestCustmr(Integer userId, Long hotelId) {
		logger.info("getWhiteLstGuestCustmr daoImpl : start");
		WhiteListUsersDTO whtUsr = new WhiteListUsersDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("guestUserId", userId));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<WhiteListUsersEntity> whiteCustmr = criteria.list();
		if (whiteCustmr.size() > 0)
			BeanUtils.copyProperties(whiteCustmr.get(0), whtUsr);
		logger.info("getWhiteLstGuestCustmr daoImpl : end");
		return whtUsr;
	}

	@SuppressWarnings("unchecked")
	public List<BlackListUsersDTO> getAllBlackLstUsrs(Long hotelId) {
		logger.info("getAllBlackLstUsrs daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<BlackListUsersDTO> blackUsrs = new ArrayList<BlackListUsersDTO>();
		BlackListUsersDTO blackUsr = null;
		List<BlackListUsersEntity> blackListUsrs = criteria.list();
		for (BlackListUsersEntity blackListUsersEntity : blackListUsrs) {
			blackUsr = new BlackListUsersDTO();
			BeanUtils.copyProperties(blackListUsersEntity, blackUsr);
			blackUsrs.add(blackUsr);
		}
		logger.info("getAllBlackLstUsrs daoImpl : end");
		return blackUsrs;
	}

	@SuppressWarnings("unchecked")
	public BlackListUsersDTO getBlackLstCustmr(Integer userId, Long hotelId) {
		logger.info("getBlackLstCustmr daoImpl : start");
		BlackListUsersDTO blckUsr = new BlackListUsersDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<BlackListUsersEntity> blckCustmr = criteria.list();
		if (blckCustmr.size() > 0)
			BeanUtils.copyProperties(blckCustmr.get(0), blckUsr);
		logger.info("getBlackLstCustmr daoImpl : end");
		return blckUsr;
	}

	@SuppressWarnings("unchecked")
	public BlackListUsersDTO getBlackLstGuestCustmr(Integer userId, Long hotelId) {
		logger.info("getBlackLstGuestCustmr daoImpl : start");
		BlackListUsersDTO blckUsr = new BlackListUsersDTO();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("guestUserId", userId));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<BlackListUsersEntity> blackCustmr = criteria.list();
		if (blackCustmr.size() > 0)
			BeanUtils.copyProperties(blackCustmr.get(0), blckUsr);
		logger.info("getBlackLstGuestCustmr daoImpl : end");
		return blckUsr;
	}

	@SuppressWarnings("unchecked")
	public Boolean addRoomTOWhteLst(Long catVal, Long roomNo, Long hotelId) {
		logger.info("addRoomTOWhteLst daoImpl : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(RoomInfoEntity.class);
		criteria.add(Restrictions.eq("roomId", roomNo));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		criteria.add(Restrictions.eq("categoryId", catVal));
		List<RoomInfoEntity> roomList = criteria.list();
		RoomInfoEntity roomInfoEntity = roomList.get(0);
		roomInfoEntity.setCategoryId(catVal);
		roomInfoEntity.setRoomId(roomNo);
		roomInfoEntity.setHotelID(hotelId);
		roomInfoEntity.setWhiteAndBlack(new Integer(1));
		session.save(roomInfoEntity);
		logger.info("addRoomTOWhteLst daoImpl : end");
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<WhiteListServiceDTO> getServiceWhiteListStartDate(Long hotelId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(WhiteListServiceEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<WhiteListServiceEntity> whiteList = criteria.list();
		List<WhiteListServiceDTO> whitelistDtos = new ArrayList<WhiteListServiceDTO>();
		WhiteListServiceDTO whitelistDto = null;
		for (WhiteListServiceEntity whiteListServiceEntity : whiteList) {
			whitelistDto = new WhiteListServiceDTO();
			BeanUtils.copyProperties(whiteListServiceEntity, whitelistDto);
			whitelistDtos.add(whitelistDto);
		}
		return whitelistDtos;
	}

	@SuppressWarnings("unchecked")
	public List<BlackListServiceDTO> getServiceBlackListStartDate(Long hotelId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(BlackListServiceEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<BlackListServiceEntity> blackList = criteria.list();
		List<BlackListServiceDTO> blacklistDtos = new ArrayList<BlackListServiceDTO>();
		BlackListServiceDTO blacklistDto = null;
		for (BlackListServiceEntity blackListServiceEntity : blackList) {
			blacklistDto = new BlackListServiceDTO();
			BeanUtils.copyProperties(blackListServiceEntity, blacklistDto);
			blacklistDtos.add(blacklistDto);
		}
		return blacklistDtos;
	}

	@SuppressWarnings("unchecked")
	public UserDTO getUserById(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<UserEntity> list = criteria.list();
		UserDTO userDto = new UserDTO();
		for (UserEntity entity : list) {
			BeanUtils.copyProperties(entity, userDto);
		}
		return userDto;
	}

	@SuppressWarnings("unchecked")
	public List<GuestAndHotelReservationInfoDTO> getSPReservedList(
			Long hotelId, Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		logger.info("inside getSPReservedList method");
		criteria.add(Restrictions.eq("hotelId", hotelId));
		criteria.add(Restrictions.eq("servProId", userId));
		List<GuestUserEntity> list = criteria.list();
		logger.info("number of objects in the list:" + list.size());
		List<GuestAndHotelReservationInfoDTO> dtoList = new ArrayList<GuestAndHotelReservationInfoDTO>();
		GuestAndHotelReservationInfoDTO totDto = null;
		for (GuestUserEntity entity : list) {
			totDto = new GuestAndHotelReservationInfoDTO();
			BookingHistoryDTO bookingDto = getHotelReservationsByGuestId(entity
					.getUserId());
			totDto.setGuestId(entity.getUserId());
			totDto.setFirstName(entity.getFirstName());
			totDto.setLastName(entity.getLastName());
			totDto.setEmail(entity.getEmail());
			totDto.setUserName(entity.getUserName());
			totDto.setPhoneNumber(entity.getPhoneNumber());
			totDto.setHotelId(entity.getHotelId());
			totDto.setReservationNumber(bookingDto.getReservationNumber());
			totDto.setCheckInDate(bookingDto.getCheckInDate());
			totDto.setCheckOutDate(bookingDto.getCheckOutDate());
			totDto.setNumberOfDays(bookingDto.getNumberOfDays());
			totDto.setCategoryId(bookingDto.getCategoryId());
			totDto.setRoomId(bookingDto.getRoomId());
			dtoList.add(totDto);
		}
		logger.info("Inside serviceDaoImpl before returning dtoList : "
				+ dtoList);
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	public List<BookingHistoryDTO> getSPEmailSharedList(Long hotelId,
			Long userId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		logger.info("inside getSPEmailSharedList method");
		criteria.add(Restrictions.eq("hotelId", hotelId));
		criteria.add(Restrictions.eq("servProId", userId));
		List<GuestUserEntity> list = criteria.list();
		logger.info("number of objects in the list:" + list.size());
		List<BookingHistoryDTO> dtoList = new ArrayList<BookingHistoryDTO>();
		BookingHistoryDTO histryDTO = null;
		for (GuestUserEntity entity : list) {
			BookingHistoryDTO bookingDto = getHotelReservationsByGuestId(entity
					.getUserId());
			if (bookingDto.getEmailShare().equals("Y")) {
				logger.info("inside if condition start");
				histryDTO = new BookingHistoryDTO();
				histryDTO.setBookingId(bookingDto.getBookingId());
				histryDTO.setGuestUserId(entity.getUserId());
				histryDTO.setFname(entity.getFirstName());
				histryDTO.setLname(entity.getLastName());
				histryDTO.setEmail(entity.getEmail());
				histryDTO.setUserName(entity.getUserName());
				histryDTO.setPhoneNumber(entity.getPhoneNumber());
				histryDTO.setHotelID(entity.getHotelId());
				histryDTO.setReservationNumber(bookingDto
						.getReservationNumber());
				histryDTO.setCheckInDate(bookingDto.getCheckInDate());
				histryDTO.setCheckOutDate(bookingDto.getCheckOutDate());
				histryDTO.setNumberOfDays(bookingDto.getNumberOfDays());
				histryDTO.setCategoryId(bookingDto.getCategoryId());
				histryDTO.setRoomId(bookingDto.getRoomId());
				histryDTO.setEmailShare(bookingDto.getEmailShare());
				histryDTO.setBookingDate(bookingDto.getBookingDate());
				dtoList.add(histryDTO);
				logger.info("inside if condition : totDto : " + histryDTO);
			}
		}
		logger.info("Inside getSPEmailSharedList serviceDaoImpl before returning dtoList : "
				+ dtoList);
		return dtoList;
	}

	@SuppressWarnings("unchecked")
	private BookingHistoryDTO getHotelReservationsByGuestId(Integer guestId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("guestUserId", guestId));
		List<BookingHistoryEntity> list = criteria.list();
		BookingHistoryDTO dto = new BookingHistoryDTO();
		for (BookingHistoryEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;

	}

	@SuppressWarnings("unchecked")
	public List<BookingHistoryDTO> getBookingHistoryDetails(Long userId,
			Long hotelId) {
		logger.info("serviceDaoImpl:getBookingHistoryDetails:start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		criteria.add(Restrictions.eq("servProId", userId));
		criteria.add(Restrictions.eq("hotelId", hotelId));
		List<GuestUserEntity> list = criteria.list();
		logger.info("Number of objects in the list" + list.size());
		logger.info("guestUserEntity list : " + list);
		BookingHistoryDTO bookingDtoFull = null;
		List<BookingHistoryDTO> bookingDtoList = new ArrayList<BookingHistoryDTO>();
		for (GuestUserEntity entity : list) {
			BookingHistoryDTO bookingDto = getHotelReservationsByGuestId(entity
					.getUserId());
			if (!bookingDto.getStatus().equals("delete")) {
				logger.info("inside for each bookingDto : " + bookingDto);
				bookingDtoFull = new BookingHistoryDTO();
				bookingDtoFull.setBookingId(bookingDto.getBookingId());
				bookingDtoFull.setEmail(entity.getEmail());
				bookingDtoFull.setPhoneNumber(entity.getPhoneNumber());
				bookingDtoFull.setReservationNumber(bookingDto
						.getReservationNumber());
				bookingDtoFull.setHotelID(entity.getHotelId());
				bookingDtoFull.setCheckInDate(bookingDto.getCheckInDate());
				bookingDtoFull.setCheckOutDate(bookingDto.getCheckOutDate());
				bookingDtoFull.setNumberOfDays(bookingDto.getNumberOfDays());
				bookingDtoFull.setStatus(bookingDto.getStatus());
				bookingDtoFull.setGuestUserId(entity.getUserId());
				bookingDtoFull.setUserName(entity.getUserName());
				bookingDtoFull.setRoomNumber(bookingDto.getRoomNumber());
				bookingDtoFull.setCategory(bookingDto.getCategory());
				bookingDtoFull.setNotes(bookingDto.getNotes());
				bookingDtoFull.setEmailShare(bookingDto.getEmailShare());
				bookingDtoFull.setArrived(bookingDto.isArrived());
				bookingDtoFull.setPaid(bookingDto.isPaid());
				bookingDtoFull.setRoomId(bookingDto.getRoomId());
				bookingDtoFull.setCategoryId(bookingDto.getCategoryId());
				bookingDtoFull.setBookingDate(bookingDto.getBookingDate());
				bookingDtoFull.setGuestUserId(bookingDto.getGuestUserId());
				bookingDtoFull.setServProId(bookingDto.getServProId());
				bookingDtoList.add(bookingDtoFull);
			}
		}
		logger.info("before returning : " + bookingDtoList);
		logger.info("serviceDaoImpl:getBookingHistoryDetails:end");
		return bookingDtoList;
	}

	@SuppressWarnings("unchecked")
	public BookingHistoryDTO getBookingHistoryById(Long bookingId) {
		logger.info("inside getBookingHistoryById : start");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BookingHistoryEntity.class);
		criteria.add(Restrictions.eq("bookingId", bookingId));
		List<BookingHistoryEntity> list = criteria.list();
		BookingHistoryDTO histryDTO = new BookingHistoryDTO();
		for (BookingHistoryEntity entity : list) {
			BeanUtils.copyProperties(entity, histryDTO);
		}
		GuestUserDTO guestDto = getGuestByGuestId(histryDTO.getGuestUserId());
		histryDTO.setUserName(guestDto.getUserName());
		logger.info("inside getBookingHistoryById before returning BookingHistoryDto : "
				+ histryDTO);
		return histryDTO;
	}

	@SuppressWarnings("unchecked")
	private GuestUserDTO getGuestByGuestId(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GuestUserEntity.class);
		criteria.add(Restrictions.eq("userId", id));
		List<GuestUserEntity> list = criteria.list();
		GuestUserDTO dto = new GuestUserDTO();
		for (GuestUserEntity entity : list) {
			BeanUtils.copyProperties(entity, dto);
		}
		return dto;
	}

	@SuppressWarnings({ "unchecked" })
	public boolean updatePreviousReservations(BookingHistoryDTO histryDTO) {
		logger.info("inside updatePreviousReservations() : start");
		logger.info("dto :" + histryDTO);
		Date checkOutDate = histryDTO.getCheckOutDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(checkOutDate);
		cal.add(Calendar.DATE, -1);
		checkOutDate = cal.getTime();
		
		BookingHistoryEntity historyEntity = new BookingHistoryEntity();
		BeanUtils.copyProperties(histryDTO, historyEntity);
		
		Date checkInDate = histryDTO.getCheckInDate();
		boolean isSave = false;
		SimpleDateFormat srcForamt = new SimpleDateFormat("yyyy-MM-dd");
		String checkin = srcForamt.format(checkInDate);
		String date = srcForamt.format(checkOutDate);
		Integer guestUserId = histryDTO.getGuestUserId();
		int executeUpdate = 0;

		Session currentSession = sessionFactory.getCurrentSession();
		String getList = "From HotelAvailabilityEntity where categoryId="
				+ histryDTO.getCategoryId() + " and hotelID="
				+ histryDTO.getHotelID() + " AND date BETWEEN '" + checkin
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
					+ "-1,available=" + available + "+1 WHERE categoryId="
					+ histryDTO.getCategoryId() + " AND  hotelID="
					+ histryDTO.getHotelID() + " AND date='" + availableDt
					+ "' ";
			Query createQuery = currentSession.createSQLQuery(query);
			executeUpdate = createQuery.executeUpdate();
		}

		if (executeUpdate > 0) {
			Session currentSession1 = sessionFactory.getCurrentSession();
			String query1 = "update AvailabilityByDateEntity set statusCode='A',colourCode='green',userId=?,reservationNumber=?,guestUsrId=? where roomId="
					+ histryDTO.getRoomId()
					+ " and categoryId="
					+ histryDTO.getCategoryId()
					+ " and hotelID="
					+ histryDTO.getHotelID()
					+ " AND date BETWEEN '"
					+ checkin
					+ "' AND '" + date + "' ";

			Query createQuery1 = currentSession1.createQuery(query1);

			if (guestUserId != null) {
				createQuery1.setString(0, null);
				createQuery1.setString(1, null);
				createQuery1.setString(2, null);
			} else {
				createQuery1.setString(0, null);
			}

			int update = createQuery1.executeUpdate();
			if (update > 0) {
				isSave = true;
				historyEntity.setStatus("cancel");
				currentSession.saveOrUpdate(historyEntity);
			}
		}
		logger.info("isSave : " + isSave);
		logger.info("inside updatePreviousReservations() : End");
		return isSave;
	}

	public boolean stopEmailShare(BookingHistoryDTO dto) {
		Session session = sessionFactory.getCurrentSession();
		dto.setEmailShare("N");
		BookingHistoryEntity entity = new BookingHistoryEntity();
		BeanUtils.copyProperties(dto, entity);
		session.update(entity);
		return true;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public boolean isBlackUser(Integer userId, Integer guestUserId, Long hotelId) {
		logger.info("isBlackUser daoImpl : start");
		boolean isBlackUsr;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		if (userId != 0)
			criteria.add(Restrictions.eq("userId", userId));
		else if (guestUserId != 0)
			criteria.add(Restrictions.eq("guestUserId", guestUserId));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<BlackListUsersEntity> list = criteria.list();
		logger.info("isBlackUser daoImpl : end");
		return isBlackUsr = (list.size() > 0 ? true : false);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public boolean isWhiteUser(Integer userId, Integer guestUserId, Long hotelId) {
		logger.info("isWhiteUser daoImpl : start");
		boolean isBlackUsr;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		if (userId != 0)
			criteria.add(Restrictions.eq("userId", userId));
		else if (guestUserId != 0)
			criteria.add(Restrictions.eq("guestUserId", guestUserId));
		criteria.add(Restrictions.eq("hotelID", hotelId));
		List<BlackListUsersEntity> list = criteria.list();
		logger.info("isWhiteUser daoImpl : end");
		return isBlackUsr = (list.size() > 0 ? true : false);
	}

	@SuppressWarnings("unchecked")
	public boolean isRemoveWhteCustmrFrmLst(Integer userId) {
		logger.info("removeWhteCustmrFrmLst daoImpl : start");
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<WhiteListUsersEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			WhiteListUsersEntity whiteListUsersEntity = whtLst.get(0);
			session.delete(whiteListUsersEntity);
			isDelete = true;
		}
		logger.info("removeWhteCustmrFrmLst daoImpl : end");
		return isDelete;
	}

	@SuppressWarnings("unchecked")
	public boolean isRemoveWhteGuestCustmrFrmLst(Integer userId) {
		logger.info("isRemoveWhteGuestCustmrFrmLst daoImpl : start");
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(WhiteListUsersEntity.class);
		criteria.add(Restrictions.eq("guestUserId", userId));
		List<WhiteListUsersEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			WhiteListUsersEntity whiteListUsersEntity = whtLst.get(0);
			session.delete(whiteListUsersEntity);
			isDelete = true;
		}
		logger.info("isRemoveWhteGuestCustmrFrmLst daoImpl : end");
		return isDelete;
	}

	@SuppressWarnings("unchecked")
	public boolean isRemoveBlackCustmrFrmLst(Integer userId) {
		logger.info("isRemoveBlackCustmrFrmLst daoImpl : start");
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<BlackListUsersEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			BlackListUsersEntity blckListUsersEntity = whtLst.get(0);
			session.delete(blckListUsersEntity);
			isDelete = true;
		}
		logger.info("isRemoveBlackCustmrFrmLst daoImpl : end");
		return isDelete;

	}

	@SuppressWarnings("unchecked")
	public boolean isRemoveBlackGuestCustmrFrmLst(Integer userId) {
		logger.info("isRemoveBlackGuestCustmrFrmLst daoImpl : start");
		boolean isDelete = false;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BlackListUsersEntity.class);
		criteria.add(Restrictions.eq("guestUserId", userId));
		List<BlackListUsersEntity> whtLst = criteria.list();
		if (whtLst.size() > 0) {
			BlackListUsersEntity blackListUsersEntity = whtLst.get(0);
			session.delete(blackListUsersEntity);
			isDelete = true;
		}
		logger.info("isRemoveBlackGuestCustmrFrmLst daoImpl : end");
		return isDelete;
	}

	/*
	 * @SuppressWarnings("unchecked") public List<BookingHistoryDTO>
	 * getEmailSharedUsrsList(Long hotelId) {
	 * logger.info("getEmailSharedUsrsList daoImpl : start"); Session session =
	 * sessionFactory.getCurrentSession(); List<BookingHistoryDTO>
	 * emailShredUsrsLst = new ArrayList<BookingHistoryDTO>(); Criteria criteria
	 * = session.createCriteria(BookingHistoryEntity.class);
	 * criteria.add(Restrictions.eq("hotelID", hotelId)); List
	 * <BookingHistoryEntity> emailSharedUSrs = criteria.list(); for
	 * (BookingHistoryEntity bookingHistoryEntity : emailSharedUSrs) {
	 * BookingHistoryDTO emailShredUsr = new BookingHistoryDTO();
	 * BeanUtils.copyProperties(bookingHistoryEntity, emailShredUsr);
	 * emailShredUsrsLst.add(emailShredUsr); }
	 * logger.info("getEmailSharedUsrsList daoImpl : end"); return
	 * emailShredUsrsLst; }
	 */

}
