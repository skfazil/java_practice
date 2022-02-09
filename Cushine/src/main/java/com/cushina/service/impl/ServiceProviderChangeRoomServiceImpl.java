package com.cushina.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.util.CushinaUtil;
import com.cushina.model.dataaccess.ServiceProviderChangeRoomDao;
import com.cushina.model.dataaccess.UserDao;
import com.cushina.service.ServiceProviderChangeRoomService;

@Service
public class ServiceProviderChangeRoomServiceImpl implements
		ServiceProviderChangeRoomService {

	private Logger logger = Logger
			.getLogger(ServiceProviderChangeRoomServiceImpl.class.getName());

	@Autowired
	private ServiceProviderChangeRoomDao changeRoomDao;

	@Autowired
	private UserDao userDao;

	@SuppressWarnings("unused")
	@Transactional
	public BookingHistoryDTO changeReservation(String draggableVal,
			String droppableVal, Long hotelId) {
		logger.info("isSaveReservation serviceImpl : start");
		String usrName = null, email = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		AvailabilityByDateDTO draggableInfo = new AvailabilityByDateDTO();
		String[] dragabble = draggableVal.split(",");
		draggableInfo.setRoomAvailDate(dragabble[0]);
		draggableInfo.setRoomId(Long.valueOf(dragabble[1]));
		draggableInfo.setAvailcnt(Integer.valueOf(dragabble[2]));
		draggableInfo.setCategoryId(Long.valueOf(dragabble[3]));

		if (!dragabble[4].equals("null"))
			draggableInfo.setUserId(Integer.valueOf(dragabble[4]));// null
		draggableInfo.setReservationNumber(Long.valueOf(dragabble[5]));

		if (!dragabble[6].equals("null"))
			draggableInfo.setGuestUsrId(Integer.valueOf(dragabble[6]));

		String[] droppable = droppableVal.split(",");

		// generate random number and set it to the droppableInfo.
		Long reservtnNum = Long.valueOf(String.valueOf(CushinaUtil
				.generateRandomReferenceNumber()));
		BookingHistoryDTO historyDTO = new BookingHistoryDTO();
		historyDTO.setRoomId(Long.valueOf(droppable[1]));
		historyDTO.setCategoryId(Long.valueOf(droppable[3]));

		if (!dragabble[4].equals("null"))
			historyDTO.setUserId(Integer.valueOf(dragabble[4]));

		if (!dragabble[6].equals("null"))
			historyDTO.setGuestUserId(Integer.valueOf(dragabble[6]));

		if (!dragabble[4].equals("null")) {
			UserDTO usrDetails = userDao.getUsrDetailsByID(historyDTO
					.getUserId());
			usrName = usrDetails.getUserName();
			email = usrDetails.getEmail();
		} else if (!dragabble[6].equals("null")) {
			GuestUserDTO guestUsrDetails = userDao
					.getGuestUsrDetailsByID(historyDTO.getGuestUserId());
			usrName = guestUsrDetails.getUserName();
			email = guestUsrDetails.getEmail();
		}
		/*
		 * historyDTO.setUserName(usrName); historyDTO.setEmail(email);
		 */
		historyDTO.setReservationNumber(reservtnNum);// new one
		historyDTO.setCheckInDt(dragabble[0]);
		Date dt = new Date();
		historyDTO.setBookingDate(dt);
		String checkInDt = historyDTO.getCheckInDt();
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(checkInDt));
			historyDTO.setCheckInDate(sdf.parse(historyDTO.getCheckInDt()));

		} catch (ParseException e) {
			logger.info("came to catch while parsing date in isChangeReservation controller in ServiceProviderChangeRoomServiceImpl :");
			e.printStackTrace();
		}
		cal.add(Calendar.DATE, (Integer.valueOf(dragabble[2]) + 1));
		// cal.add(Calendar.DATE,1);
		cal.add(Calendar.HOUR_OF_DAY, 12);
		Date checkOutDt = cal.getTime();
		historyDTO.setCheckOutDate(checkOutDt);
		historyDTO.setHotelID(hotelId);
		historyDTO.setStatus("active");

		long diff = historyDTO.getCheckOutDate().getTime()
				- historyDTO.getCheckInDate().getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		historyDTO.setNumberOfDays(diffDays);

		logger.info("droppable Info :" + droppable);

		/**
		 * update draggable Information in hotel_availability,
		 * availability_by_date and hotel_reservations.
		 * 
		 */
		// old reservation number.
		Long reservationNumber = draggableInfo.getReservationNumber();
		BookingHistoryDTO histryRecrd = new BookingHistoryDTO();
		// setting new reservation number.
		histryRecrd.setReservationNumber(reservtnNum);
		historyDTO.setReservationNumber(reservtnNum);
		histryRecrd.setOldReservtnNumber(reservationNumber);
		historyDTO.setOldReservtnNumber(reservationNumber);
		histryRecrd.setUserName(usrName);
		histryRecrd.setEmail(email);
		historyDTO.setUserName(usrName);
		historyDTO.setEmail(email);

		boolean isDropable = false;
		boolean isUpdate = changeRoomDao.isUpdateDraggableRecrd(histryRecrd);
		if (isUpdate) {
			BookingHistoryDTO histryDetails = changeRoomDao
					.getHistryDetails(draggableInfo.getReservationNumber());
			String notes = histryDetails.getNotes();
			if (notes != null) {
				historyDTO.setNotes(notes);
			} else {
				historyDTO.setNotes("");
			}
			historyDTO.setArrived(histryDetails.isArrived());
			historyDTO.setPaid(histryDetails.isPaid());
			historyDTO.setEmailShare(histryDetails.getEmailShare());
			isDropable = changeRoomDao.isDroppableRoom(historyDTO);
		}
		// checkOutDate
		historyDTO.setCheckOutDt(sdf.format(historyDTO.getCheckOutDate()));
		/*
		 * boolean isUpdate = changeRoomDao.isChangeReservation(draggableInfo,
		 * historyDTO);
		 */

		logger.info("historyDTO ::" + historyDTO);
		logger.info("isSaveReservation serviceImpl : end");
		return historyDTO;
	}

}
