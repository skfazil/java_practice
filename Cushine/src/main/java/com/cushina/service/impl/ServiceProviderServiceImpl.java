package com.cushina.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BlackListServiceDTO;
import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestAndHotelReservationInfoDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListServiceDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.common.util.CushinaUtil;
import com.cushina.model.dataaccess.PickHotelDao;
import com.cushina.model.dataaccess.ServiceProviderDao;
import com.cushina.model.dataaccess.UserDao;
import com.cushina.service.PickHotelService;
import com.cushina.service.ServiceProviderService;
import com.cushina.web.bean.BookingHistoryBean;
import com.cushina.web.bean.HotelBean;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

	private Logger logger = Logger.getLogger(ServiceProviderServiceImpl.class
			.getName());
	@Autowired
	private ServiceProviderDao providerDao;

	@Autowired
	private PickHotelDao dao;

	@Autowired
	private PickHotelService service;

	@Autowired
	UserDao userDao;

	@Autowired
	private PickHotelService hotelService;

	@Transactional
	public List<AvailabilityByDateDTO> getRoomsOnCurrentDate(Long hotelId,
			Integer noOfDays) {
		logger.info("getRoomsOnCurrentDate serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currDt = new Date();
		String currentDt = format.format(currDt);
		logger.info("inside serviceDaoImpl getRoomsonCurrentDt : hotelId :"
				+ hotelId);

		List<AvailabilityByDateDTO> roomsOnCurrentDate = providerDao
				.getRoomsOnCurrentDate(hotelId);

		for (int strt = 0; strt < roomsOnCurrentDate.size(); strt++) {
			Integer count = 1;
			int listSize = roomsOnCurrentDate.size() - 1;
			if (strt == (roomsOnCurrentDate.size() - 1))
				roomsOnCurrentDate.get(strt).setAvailcnt(count);

			for (int next = strt + 1; next < roomsOnCurrentDate.size(); next++) {
				if (roomsOnCurrentDate.get(strt).getStatusCode()
						.equals(roomsOnCurrentDate.get(next).getStatusCode())
						&& roomsOnCurrentDate
								.get(strt)
								.getRoomId()
								.equals(roomsOnCurrentDate.get(next)
										.getRoomId())) {
					++count;
					if (listSize == next)
						roomsOnCurrentDate.get(strt).setAvailcnt(count);
				} else {
					roomsOnCurrentDate.get(strt).setAvailcnt(count);
					break;
				}

			}
		}
		if (noOfDays == null) {
			noOfDays = 0;
		}
		List<AvailabilityByDateDTO> cuurDtRoomsList = new ArrayList<AvailabilityByDateDTO>();
		if (noOfDays != 0) {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomsOnCurrentDate) {

				if (currentDt.equals(format.format(availabilityByDateDTO
						.getDate()))
						&& availabilityByDateDTO.getAvailcnt() >= noOfDays) {
					cuurDtRoomsList.add(availabilityByDateDTO);

				} else if (currentDt.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtRoomsList.add(availabilityByDateDTO);

			}
		} else {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomsOnCurrentDate) {

				if (currentDt.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtRoomsList.add(availabilityByDateDTO);
			}
		}

		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelId);

		DateFormat formt = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateDTO> avilRooms = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO availRoom = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtRoomsList) {
			availRoom = new AvailabilityByDateDTO();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categoryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categoryName);
			BeanUtils.copyProperties(availabilityByDateDTO, availRoom);
			Date date = availRoom.getDate();
			String roomAvalDt = formt.format(date);
			availRoom.setRoomAvailDate(roomAvalDt);
			avilRooms.add(availRoom);
		}
		logger.info("getRoomsOnCurrentDate serviceImpl : end");
		return avilRooms;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsForProvider(
			String slectedDate, Long hotelID) {
		logger.info("getSelectedDateAvailRooms service : start");
		List<AvailabilityByDateDTO> roomAvailInfo = providerDao
				.getSelectedDateAvailRoomsForProvider(slectedDate, hotelID);
		List<AvailabilityByDateDTO> selectedDateRoomsList = getSelectedDateAvailableRoomsListForProvider(
				roomAvailInfo, slectedDate, hotelID);
		logger.info("getSelectedDateAvailRooms service : end");
		return selectedDateRoomsList;
	}

	private List<AvailabilityByDateDTO> getSelectedDateAvailableRoomsListForProvider(
			List<AvailabilityByDateDTO> roomAvailInfo, String slectedDate,
			Long hotelID) {
		logger.info("getSelectedDateAvailableRoomsListForProvider method : start");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();
		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}
		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
			if (slectedDate.equals(format.format(availabilityByDateDTO
					.getDate())))
				cuurDtAvailList.add(availabilityByDateDTO);
		}
		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);
		List<AvailabilityByDateDTO> selectedDateRoomsList = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO selectedDtRoom = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtAvailList) {
			selectedDtRoom = new AvailabilityByDateDTO();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categoryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categoryName);
			BeanUtils.copyProperties(availabilityByDateDTO, selectedDtRoom);
			Date date = selectedDtRoom.getDate();
			String roomAvalDt = format.format(date);
			selectedDtRoom.setRoomAvailDate(roomAvalDt);
			selectedDateRoomsList.add(selectedDtRoom);
		}
		logger.info("getSelectedDateAvailableRoomsListForProvider method : end");
		return selectedDateRoomsList;

	}

	@Transactional
	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsBasedOnCatgeoryForProvider(
			String selectedDate, Long hotelID, Long categoryID, Integer dayCount) {
		logger.info("getSelectedDateAvailRoomsBasedOnCatgeoryForProvider serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();
		List<AvailabilityByDateDTO> roomAvailInfo = providerDao
				.getSelectedDateAvailRoomsBasedOnCatgeoryForProvider(
						selectedDate, hotelID, categoryID, dayCount);

		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		if (dayCount == null)
			dayCount = 0;
		if (dayCount != 0) {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selectedDate.equals(format.format(availabilityByDateDTO
						.getDate()))
						&& availabilityByDateDTO.getAvailcnt() >= dayCount) {
					cuurDtAvailList.add(availabilityByDateDTO);
				} else if (selectedDate.equals(format
						.format(availabilityByDateDTO.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		} else {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selectedDate.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		}

		/**
		 * This method is used to get type of categories available for
		 * particular hotel.
		 */
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);
		List<AvailabilityByDateDTO> selectedDateRoomsList = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO selectedDtRoom = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtAvailList) {
			selectedDtRoom = new AvailabilityByDateDTO();
			Long categoryId = availabilityByDateDTO.getCategoryId();
			String categoryName = categoryListByMap.get(categoryId);
			availabilityByDateDTO.setCategoryName(categoryName);
			BeanUtils.copyProperties(availabilityByDateDTO, selectedDtRoom);
			Date date = selectedDtRoom.getDate();
			String roomAvalDt = format.format(date);
			selectedDtRoom.setRoomAvailDate(roomAvalDt);
			selectedDateRoomsList.add(selectedDtRoom);
		}
		logger.info("getSelectedDateAvailRoomsBasedOnCatgeoryForProvider serviceImpl : end");
		return selectedDateRoomsList;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getRoomAvailByCategoryForProvider(
			Long categoryId, Long hotelID, Date selectedDt) {
		logger.info("getRoomAvailByCategoryForProvider serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
		SimpleDateFormat srcFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDt = format.format(selectedDt);
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();
		List<AvailabilityByDateDTO> roomAvailInfo = providerDao
				.getRoomAvailByCategoryForProvider(categoryId, hotelID,
						selectedDt);

		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
			if (currentDt
					.equals(format.format(availabilityByDateDTO.getDate())))
				cuurDtAvailList.add(availabilityByDateDTO);
		}
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);

		List<AvailabilityByDateDTO> selectedDateRoomsList = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO selectedDtRoom = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtAvailList) {
			selectedDtRoom = new AvailabilityByDateDTO();
			Long catgryId = availabilityByDateDTO.getCategoryId();
			String categoryName = categoryListByMap.get(catgryId);
			availabilityByDateDTO.setCategoryName(categoryName);
			BeanUtils.copyProperties(availabilityByDateDTO, selectedDtRoom);
			Date date = selectedDtRoom.getDate();
			String roomAvalDt = srcFormat.format(date);
			selectedDtRoom.setRoomAvailDate(roomAvalDt);
			selectedDateRoomsList.add(selectedDtRoom);
		}
		logger.info("getRoomAvailByCategoryForProvider serviceImpl : end");
		return selectedDateRoomsList;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getDateListOnSelectedRoomNo(
			Long roomNum, Date selectedDt, Long hotelID, Long categoryId) {
		logger.info("getDateListOnSelectedRoomNo serviceImpl : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateDTO> dateListOnSelectedRoomNo = providerDao
				.getDateListOnSelectedRoomNo(roomNum, selectedDt, hotelID,
						categoryId);
		List<AvailabilityByDateDTO> datesList = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO dates = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : dateListOnSelectedRoomNo) {
			dates = new AvailabilityByDateDTO();
			BeanUtils.copyProperties(availabilityByDateDTO, dates);
			Date date = dates.getDate();
			String roomAvailDate = destFrmt.format(date);
			dates.setRoomAvailDate(roomAvailDate);
			datesList.add(dates);
		}
		logger.info("getDateListOnSelectedRoomNo serviceImpl : end");
		return datesList;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getRoomAvailByCategoryAndRoomNum(
			Long categoryId, Long hotelID, Long roomNO, Date currDt) {
		logger.info("getRoomAvailByCategoryAndRoomNum serviceImpl : start");
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateDTO> roomAvailBYRoomNumList = new ArrayList<AvailabilityByDateDTO>();
		AvailabilityByDateDTO roomAvail = null;
		List<AvailabilityByDateDTO> roomAvailByCategoryAndRoomNum = providerDao
				.getRoomAvailByCategoryAndRoomNum(categoryId, hotelID, roomNO,
						currDt);

		for (int strt = 0; strt < roomAvailByCategoryAndRoomNum.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailByCategoryAndRoomNum.size() - 1;
			if (strt == (roomAvailByCategoryAndRoomNum.size() - 1))
				roomAvailByCategoryAndRoomNum.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailByCategoryAndRoomNum
					.size(); next++) {
				if (roomAvailByCategoryAndRoomNum
						.get(strt)
						.getStatusCode()
						.equals(roomAvailByCategoryAndRoomNum.get(next)
								.getStatusCode())
						&& roomAvailByCategoryAndRoomNum
								.get(strt)
								.getRoomId()
								.equals(roomAvailByCategoryAndRoomNum.get(next)
										.getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailByCategoryAndRoomNum.get(strt).setAvailcnt(
								count);
				} else {
					roomAvailByCategoryAndRoomNum.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelID);

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailByCategoryAndRoomNum) {
			roomAvail = new AvailabilityByDateDTO();
			Long catgryId = availabilityByDateDTO.getCategoryId();
			String categoryName = categoryListByMap.get(catgryId);
			availabilityByDateDTO.setCategoryName(categoryName);
			BeanUtils.copyProperties(availabilityByDateDTO, roomAvail);
			Date date = roomAvail.getDate();
			String roomAvailDate = destFrmt.format(date);
			roomAvail.setRoomAvailDate(roomAvailDate);
			roomAvail.setCategoryId(categoryId);
			roomAvail.setRoomId(roomNO);
			roomAvailBYRoomNumList.add(roomAvail);
		}
		logger.info("getRoomAvailByCategoryAndRoomNum serviceImpl : end");
		return roomAvailBYRoomNumList;
	}

	@Transactional
	public BookingHistoryBean getUserReservedRoomDetails(Integer userId,
			Integer gustUsrId, String dateVal, Integer roomIDval,
			Integer catgryId, Long reservationNumber, Long hotelID) {
		logger.info("getUserReservedRoomDetails serviceImpl : start");
		UserDTO userDetails = null;
		if (userId != null) {
			userDetails = providerDao.getUserDetails(userId);
		} else {
			userDetails = new UserDTO();
			GuestUserDTO guestUserDetails = providerDao
					.getGuestUserDetails(gustUsrId);
			userDetails.setUserName(guestUserDetails.getUserName());
			userDetails.setPhoneNumber(guestUserDetails.getPhoneNumber());
			userDetails.setEmail(guestUserDetails.getEmail());
		}

		BookingHistoryBean bookingHistory = new BookingHistoryBean();
		DateFormat srcFrmt = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat srcFormat = new SimpleDateFormat("dd MMMM, yyyy");

		Date checkInDate = null;
		try {
			checkInDate = srcFrmt.parse(dateVal);
		} catch (ParseException e) {
			logger.info("came to catch while parsing date in getUserReservedRoomDetails in sserviceImpl");
		}
		logger.info("user id : " + userId + ", guestUserId  :" + gustUsrId);

		BookingHistoryDTO userReservationHIstory = providerDao
				.getUserReservationHistory(userId, gustUsrId, checkInDate,
						roomIDval, catgryId, reservationNumber);

		List<HotelBean> hotelDetail = service.getHotelDetail(Long
				.valueOf(String.valueOf(hotelID)));

		HotelBean hotelBean = hotelDetail.get(0);
		BeanUtils.copyProperties(userReservationHIstory, bookingHistory);
		bookingHistory.setUserName(userDetails.getUserName());
		bookingHistory.setEmail(userDetails.getEmail());
		bookingHistory.setContactNumber(userDetails.getContactNumber());
		bookingHistory.setPhoneNumber(userDetails.getPhoneNumber());

		bookingHistory.setHotelName(hotelBean.getHotelName());
		bookingHistory.setHotelAddress(hotelBean.getHotelAddress());
		bookingHistory.setCity(hotelBean.getCity());
		bookingHistory.setHotelPhneNumber(hotelBean.getPhoneNumber());

		Date checkedInDate = bookingHistory.getCheckInDate();
		Date checkOutDate = bookingHistory.getCheckOutDate();
		bookingHistory.setCheckInDt(srcFormat.format(checkedInDate));
		bookingHistory.setCheckOutDt(srcFormat.format(checkOutDate));
		logger.info("getUserReservedRoomDetails serviceImpl : start");
		return bookingHistory;
	}

	@Transactional
	public boolean isPaymentPaid(Integer userId, Long hotelId,
			Integer guestUsrId, Long reservtnNum) {
		return providerDao.isPaymentPaid(userId, hotelId, guestUsrId,
				reservtnNum);
	}

	@Transactional
	public boolean isArrived(Integer userId, Long hotelId, Integer guestUsrId,
			Long reservtnNum) {
		return providerDao.isArrived(userId, hotelId, guestUsrId, reservtnNum);
	}

	@SuppressWarnings("unused")
	@Transactional
	public BookingHistoryDTO saveManualReservation(
			BookingHistoryDTO historyDTO, GuestUserDTO userDTO,
			Integer guestUserId) {
		logger.info("isSaveManualReservation serviceImpl : start");
		SimpleDateFormat srcFrmat = new SimpleDateFormat("dd/MM/yyyy");
		logger.info("historyDTO $$$$ :" + historyDTO);
		boolean saveManualReservation = false;
		GuestUserDTO userDetails = userDao.getGuestUsrDetailsByID(guestUserId);
		Integer guestUsrId = userDetails.getUserId();
		Map<Long, String> categoryListByMap = hotelService
				.getCategoryListByMap(historyDTO.getHotelID());
		String categryType = categoryListByMap.get(historyDTO.getCategoryId());
		historyDTO.setCategory(categryType);
		historyDTO.setGuestUserId(guestUsrId);
		long diff = historyDTO.getCheckOutDate().getTime()
				- historyDTO.getCheckInDate().getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		historyDTO.setNumberOfDays(diffDays);
		historyDTO.setStatus("active");
		String refNum = String.valueOf(CushinaUtil
				.generateRandomReferenceNumber());
		historyDTO.setReservationNumber(Long.valueOf(refNum));
		Date checkInDate = historyDTO.getCheckInDate();
		String checkInDt = srcFrmat.format(checkInDate);//30/10/2015
		Date checkOutDate = historyDTO.getCheckOutDate();
		String checkOutDt = srcFrmat.format(checkOutDate);//30/10/2015
		historyDTO.setCheckInDt(checkInDt);
		historyDTO.setCheckOutDt(checkOutDt);

		if (guestUsrId != null) {
			saveManualReservation = providerDao
					.isSaveManualReservation(historyDTO);
		}
		logger.info("isSaveManualReservation serviceImpl : end");
		return historyDTO;
	}

	@Transactional
	public String isCustmrAddToWhtLst(Integer userId, Long hotelId,
			Integer guestUsrId) {
		String status = null;
		boolean isBlackUser = providerDao.isBlackUser(userId, guestUsrId,
				hotelId);
		if (isBlackUser) {
			status = "Customer has been already added to Black List category";
		} else {
			boolean custmrAddToWhtLst = providerDao.isCustmrAddToWhtLst(userId,
					hotelId, guestUsrId);
			if (custmrAddToWhtLst) {
				status = "Customer has been successfully added to white list category";
			} else {
				status = "Customer has been already added to white list category";
			}
		}
		return status;
	}

	@Transactional
	public String isCustmrAddToBlckLst(Integer userId, Long hotelId,
			Integer guestUsrId) {
		String status = null;
		boolean whiteUser = providerDao
				.isWhiteUser(userId, guestUsrId, hotelId);
		if (whiteUser) {
			status = "Customer has been already added to White List category";
		} else {
			boolean custmrAddToBlckLst = providerDao.isCustmrAddToBlckLst(
					userId, hotelId, guestUsrId);
			if (custmrAddToBlckLst) {
				status = "Customer has been successfully added to Black list category";
			} else {
				status = "Customer has been already added to Black list category";
			}
		}
		return status;
	}

	@Transactional
	public List<UserDTO> getCustomersList(Long hotelId) {
		List<UserDTO> customersList = providerDao.getCustomersList(hotelId);
		return customersList;
	}

	@Transactional
	public List<BookingHistoryDTO> getCustmrReservedHistry(Integer userId,
			Long hotelId) {
		logger.info("getCustmrReservedHistry serviceImpl : start");
		List<BookingHistoryDTO> custmrReservedHistry = providerDao
				.getCustmrReservedHistry(userId);
		DateFormat formt = new SimpleDateFormat("dd/MM/yyyy");

		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelId);
		logger.info("custmrReservedHistry :" + custmrReservedHistry);
		for (BookingHistoryDTO bookingHistoryDTO : custmrReservedHistry) {
			Long categoryId = bookingHistoryDTO.getCategoryId();
			String category = categoryListByMap.get(categoryId);
			Date bookingDate = bookingHistoryDTO.getBookingDate();
			String roomAvalDt = formt.format(bookingDate);
			bookingHistoryDTO.setCheckInDt(roomAvalDt);
			bookingHistoryDTO.setCategory(category);
		}
		logger.info("getCustmrReservedHistry serviceImpl : end");
		return custmrReservedHistry;
	}

	@Transactional
	public List<BookingHistoryDTO> getCustmrsHistry(Long hotelId) {
		logger.info("getCustmrsHistry serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		List<BookingHistoryDTO> custmrsHistry = providerDao
				.getCustmrsHistry(hotelId);

		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelId);

		for (BookingHistoryDTO bookingHistoryDTO : custmrsHistry) {
			bookingHistoryDTO.setCheckInDt(format.format(bookingHistoryDTO
					.getCheckInDate()));
			bookingHistoryDTO.setCheckOutDt(format.format(bookingHistoryDTO
					.getCheckOutDate()));
			Integer userId = bookingHistoryDTO.getUserId();
			Integer guestUserId = bookingHistoryDTO.getGuestUserId();
			Long categoryId = bookingHistoryDTO.getCategoryId();
			String catgryId = categoryListByMap.get(categoryId);
			bookingHistoryDTO.setCategory(catgryId);

			if (userId != null) {

				UserDTO userDetails = userDao.getUsrDetailsByID(userId);
				String userName = userDetails.getUserName();
				String phoneNumber = userDetails.getPhoneNumber();
				String email = userDetails.getEmail();
				bookingHistoryDTO.setUserName(userName);
				bookingHistoryDTO.setPhoneNumber(phoneNumber);
				bookingHistoryDTO.setEmail(email);

			} else if (guestUserId != null) {
				GuestUserDTO guestUsrDetails = userDao
						.getGuestUsrDetailsByID(guestUserId);
				String userName = guestUsrDetails.getUserName();
				String phoneNumber = guestUsrDetails.getPhoneNumber();
				String email = guestUsrDetails.getEmail();

				bookingHistoryDTO.setUserName(userName);
				bookingHistoryDTO.setPhoneNumber(phoneNumber);
				bookingHistoryDTO.setEmail(email);
			}
		}
		logger.info("getCustmrsHistry serviceImpl : end");
		return custmrsHistry;
	}

	@Transactional
	public List<BookingHistoryDTO> getCustomerReservtnHistryRecords(
			Long hotelId, String userName) {
		logger.info("getCustomerReservtnRecords serviceImpl : start");

		List<BookingHistoryDTO> guestUserReservtnHistory = null;
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
		Map<Long, String> categoryListByMap = service
				.getCategoryListByMap(hotelId);
		try {
			Integer userId = null;
			UserDTO userDetail = userDao.getUserDetail(userName);
			userId = userDetail.getUserId();
			guestUserReservtnHistory = getUsrReservtnHistry(userId);

			for (BookingHistoryDTO bookingHistoryDTO : guestUserReservtnHistory) {

				Long categoryId = bookingHistoryDTO.getCategoryId();
				String categryName = categoryListByMap.get(categoryId);
				bookingHistoryDTO.setCategory(categryName);

				bookingHistoryDTO.setCheckInDt(format.format(bookingHistoryDTO
						.getCheckInDate()));
				bookingHistoryDTO.setCheckOutDt(format.format(bookingHistoryDTO
						.getCheckOutDate()));
				String usrName = userDetail.getUserName();
				String phoneNumber = userDetail.getPhoneNumber();
				String email = userDetail.getEmail();
				bookingHistoryDTO.setUserName(usrName);
				bookingHistoryDTO.setPhoneNumber(phoneNumber);
				bookingHistoryDTO.setEmail(email);
			}
		} catch (Exception e) {
			logger.info("came to catch, Bcz no one user have this name in user_profile table");
			e.getMessage();
			GuestUserDTO guestUserDetail = null;
			try {
				guestUserDetail = userDao.getGuestUserDetail(userName);
				Integer guestUserId = guestUserDetail.getUserId();
				guestUserReservtnHistory = getGuestUserReservtnHistory(guestUserId);
				for (BookingHistoryDTO bookingHistoryDTO : guestUserReservtnHistory) {

					Long categoryId = bookingHistoryDTO.getCategoryId();
					String categryName = categoryListByMap.get(categoryId);
					bookingHistoryDTO.setCategory(categryName);

					bookingHistoryDTO.setCheckInDt(format
							.format(bookingHistoryDTO.getCheckInDate()));
					bookingHistoryDTO.setCheckOutDt(format
							.format(bookingHistoryDTO.getCheckOutDate()));

					String usrName = guestUserDetail.getUserName();
					String phoneNumber = guestUserDetail.getPhoneNumber();
					String email = guestUserDetail.getEmail();
					bookingHistoryDTO.setUserName(usrName);
					bookingHistoryDTO.setPhoneNumber(phoneNumber);
					bookingHistoryDTO.setEmail(email);
				}
			} catch (Exception ex) {
				logger.info("came to catach user name not exits in both user_profile table and guest_usr table");
			}

		}
		logger.info("getCustomerReservtnRecords serviceImpl : end");
		return guestUserReservtnHistory;
	}

	@Transactional
	public List<WhiteListUsersDTO> getAllWhiteLstUsrs(Long hotelId) {
		// 02-09-15
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<WhiteListUsersDTO> allWhiteLstUsrs = providerDao
				.getAllWhiteLstUsrs(hotelId);

		for (WhiteListUsersDTO whiteListUsersDTO : allWhiteLstUsrs) {
			whiteListUsersDTO.setStrtDate(format.format(whiteListUsersDTO
					.getStartDate()));
			Integer userId = whiteListUsersDTO.getUserId();
			Integer guestUserId = whiteListUsersDTO.getGuestUserId();
			if (userId != null) {
				UserDTO usrDetailsByID = userDao.getUsrDetailsByID(userId);
				whiteListUsersDTO.setUserName(usrDetailsByID.getUserName());
				whiteListUsersDTO.setEmail(usrDetailsByID.getEmail());
				whiteListUsersDTO.setPhoneNumber(usrDetailsByID
						.getPhoneNumber());
			} else if (guestUserId != null) {
				GuestUserDTO guestUsrDetailsByID = userDao
						.getGuestUsrDetailsByID(guestUserId);
				whiteListUsersDTO
						.setUserName(guestUsrDetailsByID.getUserName());
				whiteListUsersDTO.setEmail(guestUsrDetailsByID.getEmail());
				whiteListUsersDTO.setPhoneNumber(guestUsrDetailsByID
						.getPhoneNumber());
			}

		}
		return allWhiteLstUsrs;
	}

	@Transactional
	public WhiteListUsersDTO getWhiteCustmrRecord(Long hotelId, String userName) {
		logger.info("getWhiteCustmrRecord serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		WhiteListUsersDTO whiteLstCustmr = null;
		try {
			UserDTO userDetail = userDao.getUserDetail(userName);
			whiteLstCustmr = providerDao.getWhiteLstCustmr(
					userDetail.getUserId(), hotelId);
			whiteLstCustmr.setUserName(userDetail.getUserName());
			whiteLstCustmr.setEmail(userDetail.getEmail());
			whiteLstCustmr.setPhoneNumber(userDetail.getPhoneNumber());
			whiteLstCustmr.setStrtDate(format.format(whiteLstCustmr
					.getStartDate()));
		} catch (Exception e) {
			logger.info("no user not exists in user_profile table");
			try {
				logger.info("no user exists in user table");
				GuestUserDTO guestUserDetail = userDao
						.getGuestUserDetail(userName);
				whiteLstCustmr = providerDao.getWhiteLstGuestCustmr(
						guestUserDetail.getUserId(), hotelId);
				whiteLstCustmr.setUserName(guestUserDetail.getUserName());
				whiteLstCustmr.setEmail(guestUserDetail.getEmail());
				whiteLstCustmr.setPhoneNumber(guestUserDetail.getPhoneNumber());
				whiteLstCustmr.setStrtDate(format.format(whiteLstCustmr
						.getStartDate()));
			} catch (Exception ex) {
				logger.info("came to catach user name not exits in both user_profile table and guest_usr table");
				whiteLstCustmr = null;
			}
		}
		logger.info("getWhiteCustmrRecord serviceImpl : start");
		return whiteLstCustmr;
	}

	@Transactional
	public List<BlackListUsersDTO> getAllBlackLstUsrs(Long hotelId) {
		logger.info("getAllBlackLstUsrs serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<BlackListUsersDTO> allBlackLstUsrs = providerDao
				.getAllBlackLstUsrs(hotelId);
		for (BlackListUsersDTO blackListUsersDTO : allBlackLstUsrs) {
			blackListUsersDTO.setStrtDate(format.format(blackListUsersDTO
					.getStartDate()));
			Integer userId = blackListUsersDTO.getUserId();
			Integer guestUserId = blackListUsersDTO.getGuestUserId();
			if (userId != null) {
				UserDTO usrDetailsByID = userDao.getUsrDetailsByID(userId);
				blackListUsersDTO.setUserName(usrDetailsByID.getUserName());
				blackListUsersDTO.setEmail(usrDetailsByID.getEmail());
				blackListUsersDTO.setPhoneNumber(usrDetailsByID
						.getPhoneNumber());
			} else if (guestUserId != null) {
				GuestUserDTO guestUsrDetailsByID = userDao
						.getGuestUsrDetailsByID(guestUserId);
				blackListUsersDTO
						.setUserName(guestUsrDetailsByID.getUserName());
				blackListUsersDTO.setEmail(guestUsrDetailsByID.getEmail());
				blackListUsersDTO.setPhoneNumber(guestUsrDetailsByID
						.getPhoneNumber());
			}
		}
		logger.info("getAllBlackLstUsrs serviceImpl : end");
		return allBlackLstUsrs;
	}

	@Transactional
	public BlackListUsersDTO getBlackCustmrRecord(Long hotelId, String userName) {
		logger.info("getBlackCustmrRecord serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		BlackListUsersDTO blackLstCustmr = null;
		try {
			UserDTO userDetail = userDao.getUserDetail(userName);

			blackLstCustmr = providerDao.getBlackLstCustmr(
					userDetail.getUserId(), hotelId);
			blackLstCustmr.setUserName(userDetail.getUserName());
			blackLstCustmr.setEmail(userDetail.getEmail());
			blackLstCustmr.setPhoneNumber(userDetail.getPhoneNumber());
			blackLstCustmr.setStrtDate(format.format(blackLstCustmr
					.getStartDate()));
			logger.info("whte list customer list ::: " + blackLstCustmr);

		} catch (Exception e) {
			logger.info("no user not exists in user_profile table");
			try {
				logger.info("no user exists in user table");
				GuestUserDTO guestUserDetail = userDao
						.getGuestUserDetail(userName);

				blackLstCustmr = providerDao.getBlackLstGuestCustmr(
						guestUserDetail.getUserId(), hotelId);
				blackLstCustmr.setUserName(guestUserDetail.getUserName());
				blackLstCustmr.setEmail(guestUserDetail.getEmail());
				blackLstCustmr.setPhoneNumber(guestUserDetail.getPhoneNumber());
				blackLstCustmr.setStrtDate(format.format(blackLstCustmr
						.getStartDate()));
				logger.info("black list guest cusotmer list ::: "
						+ blackLstCustmr);
			} catch (Exception ex) {
				logger.info("came to catach user name not exits in both user_profile table and guest_usr table");
				blackLstCustmr = null;
			}
		}

		logger.info("getBlackCustmrRecord serviceImpl : end");
		return blackLstCustmr;
	}

	@Transactional
	public Boolean addRoomTOWhteLst(Long catVal, Long roomNo, Long hotelId) {
		return providerDao.addRoomTOWhteLst(catVal, roomNo, hotelId);
	}

	@Transactional
	private List<BookingHistoryDTO> getUsrReservtnHistry(Integer userId) {
		return providerDao.getCustmrReservedHistry(userId);

	}

	@Transactional
	private List<BookingHistoryDTO> getGuestUserReservtnHistory(
			Integer guestUserId) {
		return providerDao.getGuestUserReservtnHistry(guestUserId);
	}

	@Transactional
	public List<WhiteListServiceDTO> getServiceWhiteListStartDate(Long hotelId) {
		return providerDao.getServiceWhiteListStartDate(hotelId);
	}

	@Transactional
	public List<BlackListServiceDTO> getServiceBlackListStartDate(Long hotelId) {
		return providerDao.getServiceBlackListStartDate(hotelId);
	}

	@Transactional
	public UserDTO getUserById(Integer userId) {
		return providerDao.getUserById(userId);
	}

	@Transactional
	public List<GuestAndHotelReservationInfoDTO> getSPReservedList(
			Long hotelId, Long userId) {
		return providerDao.getSPReservedList(hotelId, userId);
	}

	@Transactional
	public List<BookingHistoryDTO> getSPEmailSharedList(Long hotelId,
			Long userId) {
		return providerDao.getSPEmailSharedList(hotelId, userId);
	}

	@Transactional
	public List<BookingHistoryDTO> getBookingHistoryDetails(Long userId,
			Long hotelId) {
		return providerDao.getBookingHistoryDetails(userId, hotelId);
	}

	@Transactional
	public BookingHistoryDTO getBookingHistoryById(Long bookingId) {
		return providerDao.getBookingHistoryById(bookingId);
	}

	@Transactional
	public boolean updatePreviousReservations(BookingHistoryDTO dto) {
		return providerDao.updatePreviousReservations(dto);
	}

	@Transactional
	public boolean stopEmailShare(BookingHistoryDTO dto) {
		return providerDao.stopEmailShare(dto);
	}

	@Transactional
	public boolean isRemoveWhteCustmrFrmLst(Integer userId) {
		return providerDao.isRemoveWhteCustmrFrmLst(userId);
	}

	@Transactional
	public boolean isRemoveWhteGuestCustmrFrmLst(Integer userId) {
		return providerDao.isRemoveWhteGuestCustmrFrmLst(userId);
	}

	@Transactional
	public boolean isRemoveBlackCustmrFrmLst(Integer userId) {
		return providerDao.isRemoveBlackCustmrFrmLst(userId);
	}

	@Transactional
	public boolean isRemoveBlackGuestCustmrFrmLst(Integer userId) {
		return providerDao.isRemoveBlackGuestCustmrFrmLst(userId);
	}

	@Transactional
	public List<HotelAvailabilityDTO> getDatesListBasedOnNoOfDays(Long hotelID,
			Integer noOfDays, Long categoryID) {
		logger.info("getDatesListBasedOnNoOfDays serviceProviderImpl : start");

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		List<HotelAvailabilityDTO> datesList = null;
		if (categoryID == 0) {
			datesList = dao.getDatesList(hotelID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = format.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);
				// calling getRoomInfo
				String roomInfo = getRoomInfo(selctedDate, hotelID, noOfDays);
				if (roomInfo == null) {
					logger.info("roomInfo is null:");
					roomInfo = "B";
				}
				hotelAvailabilityDTO.setStatus(roomInfo);
			}// dates list for each close.
		} else {
			datesList = dao.getDatesListBsdOnCatgry(hotelID, categoryID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = format.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);
				// calling getRoomInfo
				String roomInfo = getRoomInfoByCategry(selctedDate, hotelID,
						noOfDays, categoryID);

				if (roomInfo == null) {
					roomInfo = "B";
				}
				hotelAvailabilityDTO.setStatus(roomInfo);
			}
		}
		logger.info("getDatesListBasedOnNoOfDays serviceProviderImpl : end");
		return datesList;

	}

	private String getRoomInfo(String slectedDate, Long hotelID,
			Integer noOfDays) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");// 22/09/2015
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();
		List<AvailabilityByDateDTO> roomAvailInfo = dao
				.getSelectedDateAvailRooms(slectedDate, hotelID);
		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 0;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {

			if (slectedDate.equals(format.format(availabilityByDateDTO
					.getDate())))
				cuurDtAvailList.add(availabilityByDateDTO);
		}
		String status = null;

		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtAvailList) {
			Integer availcnt = availabilityByDateDTO.getAvailcnt();
			if (availcnt >= noOfDays) {
				status = "A";
			}
		}
		return status;
	}

	private String getRoomInfoByCategry(String selctedDate, Long hotelID,
			Integer noOfDays, Long categoryID) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");// 22/09/2015
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();

		List<AvailabilityByDateDTO> roomAvailInfo = dao
				.getSelectedDateAvailRoomsBasedOnCatgeory(selctedDate, hotelID,
						categoryID, noOfDays);

		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 0;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		if (noOfDays == null)
			noOfDays = 0;
		if (noOfDays != 0) {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selctedDate.equals(format.format(availabilityByDateDTO
						.getDate()))
						&& availabilityByDateDTO.getAvailcnt() >= noOfDays) {
					cuurDtAvailList.add(availabilityByDateDTO);
				} else if (selctedDate.equals(format
						.format(availabilityByDateDTO.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		} else {
			logger.info("days list ::");
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selctedDate.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		}

		String status = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtAvailList) {
			Integer availcnt = availabilityByDateDTO.getAvailcnt();
			if (availcnt >= noOfDays) {
				status = "A";
			}
		}
		return status;

	}

	/*
	 * @Transactional public List<BookingHistoryDTO> getEmailSharedList(Long
	 * hotelId) {
	 * 
	 * return null; }
	 */

}
