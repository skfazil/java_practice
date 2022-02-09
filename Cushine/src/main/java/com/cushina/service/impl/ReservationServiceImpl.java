package com.cushina.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.model.dataaccess.ReservationDao;
import com.cushina.service.ReservationService;
import com.cushina.web.bean.BookingHistoryBean;

@Service
public class ReservationServiceImpl implements ReservationService {

	private Logger logger = Logger.getLogger(ReservationServiceImpl.class
			.getName());

	@Autowired
	ReservationDao reservationDao;

	@Transactional
	public boolean isSave(BookingHistoryBean bean) {
		return reservationDao.isSave(bean);
	}

	@Transactional
	public AvailabilityByDateDTO reserveSelectedRoom(Long id) {
		return reservationDao.reserveSelectedRoom(id);
	}

	@SuppressWarnings("unused")
	@Transactional
	public List<AvailabilityByDateDTO> getAvailDatesToReserveRoom(Long hotelId,
			Date selectedDt, Long roomNum) {
		List<AvailabilityByDateDTO> availDatesToReserveRoom = reservationDao
				.getAvailDatesToReserveRoom(hotelId, selectedDt, roomNum);

		logger.info("avail list :::: " + availDatesToReserveRoom);
		List<AvailabilityByDateDTO> availDateDTOs = new ArrayList<AvailabilityByDateDTO>();

		for (int strt = 0; strt < availDatesToReserveRoom.size(); strt++) {
			/*if(strt == 0)
			availDateDTOs.add(availDatesToReserveRoom.get(0));*/
			for (int next = strt + 1; next < availDatesToReserveRoom.size(); next++) {
				int listSize = availDatesToReserveRoom.size() - 1;
				if (availDatesToReserveRoom
						.get(strt)
						.getStatusCode()
						.equals(availDatesToReserveRoom.get(next)
								.getStatusCode())
						&& availDatesToReserveRoom
								.get(strt)
								.getRoomId()
								.equals(availDatesToReserveRoom.get(next)
										.getRoomId())) {
					availDateDTOs.add(availDatesToReserveRoom.get(next));
					
				} else {
					break;
				}
			}
			break;
		}
		if(availDateDTOs.size() == 0){
			logger.info("availDateDTOs size is zero :");
			Date date = availDatesToReserveRoom.get(0).getDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			date = cal.getTime();
			logger.info("after increment time *** :"+date);
			availDatesToReserveRoom.get(0).setDate(date);
			logger.info("set date after increment :"+availDatesToReserveRoom.get(0).getDate());
			availDateDTOs.add(availDatesToReserveRoom.get(0));
		}
		logger.info("avail rooms to reserved ::: " + availDateDTOs);
		return availDateDTOs;
	}

	@Transactional
	public Long deleteReservation(Long bookingID) {
		return reservationDao.deleteReservation(bookingID);
	}

	@Transactional
	public Long cancelReservation(Long bookingID) {
		return reservationDao.cancelReservation(bookingID);
	}

	@Transactional
	public boolean isReservationProgress(Long hotelID, Long categoryId,
			Long roomId, String checkedInDt, String checkedoutdate) {
		logger.info("isReservationProgress serviceImpl : start");
		SimpleDateFormat srcFormat = new SimpleDateFormat("dd MMMM,yyyy");
		Date checkIn = null, checkOut = null;
		try {
			checkIn = srcFormat.parse(checkedInDt);
			checkOut = srcFormat.parse(checkedoutdate);
		} catch (ParseException e) {
			logger.info("came to catch while parsing date in isReservationProgress serviceImpl");
		}
		boolean isProgress = reservationDao.isReservationProgress(hotelID,
				categoryId, roomId, checkIn, checkOut);
		logger.info("isReservationProgress serviceImpl : end");
		return isProgress;
	}

	@Transactional
	public boolean isChangeReservaion(Long bookingID) {
		return reservationDao.isChangeReservaion(bookingID);
	}

	@Transactional
	public Long sameHotelReservtn(Long bookingID) {
		return reservationDao.sameHotelReservtn(bookingID);
	}

}
