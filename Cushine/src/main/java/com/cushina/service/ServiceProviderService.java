package com.cushina.service;

import java.util.Date;
import java.util.List;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BlackListServiceDTO;
import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListServiceDTO;
import com.cushina.common.dto.WhiteListUsersDTO;
import com.cushina.web.bean.BookingHistoryBean;

public interface ServiceProviderService {

	public List<AvailabilityByDateDTO> getRoomsOnCurrentDate(Long hotelId,
			Integer noOfDays);

	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsForProvider(
			String slectedDate, Long hotelID);

	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsBasedOnCatgeoryForProvider(
			String selectedDate, Long hotelID, Long categoryID, Integer dayCount);

	public List<AvailabilityByDateDTO> getRoomAvailByCategoryForProvider(
			Long categoryId, Long hotelID, Date selectedDt);

	public List<AvailabilityByDateDTO> getDateListOnSelectedRoomNo(
			Long roomNum, Date selectedDt, Long hotelID, Long categoryId);

	public List<AvailabilityByDateDTO> getRoomAvailByCategoryAndRoomNum(
			Long categoryId, Long hotelID, Long roomNO, Date currDt);

	public BookingHistoryBean getUserReservedRoomDetails(Integer userId,
			Integer gustUsrId, String dateVal, Integer roomIDval,
			Integer catgryId, Long reservationNumber, Long hotelID);

	public String isCustmrAddToWhtLst(Integer userId, Long hotelId,
			Integer guestUsrId);

	public String isCustmrAddToBlckLst(Integer userId, Long hotelId,
			Integer guestUsrId);

	public boolean isPaymentPaid(Integer userId, Long hotelId,
			Integer guestUsrId, Long reservtnNum);

	public boolean isArrived(Integer userId, Long hotelId, Integer guestUsrId,
			Long reservtnNum);

	public BookingHistoryDTO saveManualReservation(
			BookingHistoryDTO historyDTO, GuestUserDTO userDTO,
			Integer guestUserId);

	public List<UserDTO> getCustomersList(Long hotelId);

	// implemented in nested ajax call but not diaplaying data in jsp.
	public List<BookingHistoryDTO> getCustmrReservedHistry(Integer userId,
			Long hotelId);

	public List<BookingHistoryDTO> getCustmrsHistry(Long hotelId);

	public List<BookingHistoryDTO> getCustomerReservtnHistryRecords(
			Long hotelId, String userName);

	public List<WhiteListUsersDTO> getAllWhiteLstUsrs(Long hotelId);

	public WhiteListUsersDTO getWhiteCustmrRecord(Long hotelId, String userName);

	public List<BlackListUsersDTO> getAllBlackLstUsrs(Long hotelId);

	public BlackListUsersDTO getBlackCustmrRecord(Long hotelId, String userName);

	public Boolean addRoomTOWhteLst(Long catVal, Long roomNo, Long hotelId);

	public List<WhiteListServiceDTO> getServiceWhiteListStartDate(Long hotelId);

	public List<BlackListServiceDTO> getServiceBlackListStartDate(Long hotelId);

	public UserDTO getUserById(Integer userId);

	public List<BookingHistoryDTO> getSPEmailSharedList(Long hotelId,
			Long userId);
	////
	//public List<BookingHistoryDTO> getEmailSharedList(Long hotelId);

	public List<BookingHistoryDTO> getBookingHistoryDetails(Long userId,
			Long hotelId);

	public BookingHistoryDTO getBookingHistoryById(Long bookingId);

	public boolean updatePreviousReservations(BookingHistoryDTO dto);

	public boolean stopEmailShare(BookingHistoryDTO dto);

	public boolean isRemoveWhteCustmrFrmLst(Integer userId);

	public boolean isRemoveWhteGuestCustmrFrmLst(Integer userId);

	public boolean isRemoveBlackCustmrFrmLst(Integer userId);

	public boolean isRemoveBlackGuestCustmrFrmLst(Integer userId);
	
	public List<HotelAvailabilityDTO> getDatesListBasedOnNoOfDays(Long hotelID,
			Integer noOfDays, Long categoryID);

}
