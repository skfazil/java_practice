package com.cushina.model.dataaccess;

import java.util.Date;
import java.util.List;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BlackListServiceDTO;
import com.cushina.common.dto.BlackListUsersDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.GuestAndHotelReservationInfoDTO;
import com.cushina.common.dto.GuestUserDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.common.dto.WhiteListServiceDTO;
import com.cushina.common.dto.WhiteListUsersDTO;

public interface ServiceProviderDao {

	public List<AvailabilityByDateDTO> getRoomsOnCurrentDate(Long hotelId);

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

	public UserDTO getUserDetails(Integer userId);

	public GuestUserDTO getGuestUserDetails(Integer userId);

	public BookingHistoryDTO getUserReservationHistory(Integer userId,
			Integer gustUsrId, Date checkInDate, Integer roomId,
			Integer categryId, Long reservationNumber);

	public boolean isCustmrAddToWhtLst(Integer userId, Long hotelId,
			Integer guestUsrId);

	public boolean isCustmrAddToBlckLst(Integer userId, Long hotelId,
			Integer guestUsrId);

	public boolean isPaymentPaid(Integer userId, Long hotelId,
			Integer guestUsrId, Long reservtnNum);

	public boolean isArrived(Integer userId, Long hotelId, Integer guestUsrId,
			Long reservtnNum);

	public Integer saveUserDetails(UserDTO userDTO);

	public boolean isSaveManualReservation(BookingHistoryDTO historyDTO);

	public List<UserDTO> getCustomersList(Long hotelId);

	public List<BookingHistoryDTO> getCustmrReservedHistry(Integer userId);

	public List<BookingHistoryDTO> getCustmrsHistry(Long hotelId);

	public List<BookingHistoryDTO> getGuestUserReservtnHistry(
			Integer guestUserId);

	public List<WhiteListUsersDTO> getAllWhiteLstUsrs(Long hotelId);

	public WhiteListUsersDTO getWhiteLstCustmr(Integer userId, Long hotelId);

	public WhiteListUsersDTO getWhiteLstGuestCustmr(Integer userId, Long hotelId);

	public List<BlackListUsersDTO> getAllBlackLstUsrs(Long hotelId);

	public BlackListUsersDTO getBlackLstCustmr(Integer userId, Long hotelId);

	public BlackListUsersDTO getBlackLstGuestCustmr(Integer userId, Long hotelId);

	public Boolean addRoomTOWhteLst(Long catVal, Long roomNo, Long hotelId);

	public List<WhiteListServiceDTO> getServiceWhiteListStartDate(Long hotelId);

	public List<BlackListServiceDTO> getServiceBlackListStartDate(Long hotelId);

	public UserDTO getUserById(Integer userId);

	public List<GuestAndHotelReservationInfoDTO> getSPReservedList(
			Long hotelId, Long userId);

	public List<BookingHistoryDTO> getSPEmailSharedList(Long hotelId,
			Long userId);
	
	//public List<BookingHistoryDTO> getEmailSharedUsrsList(Long hotelId);

	public List<BookingHistoryDTO> getBookingHistoryDetails(Long userId,
			Long hotelId);

	public BookingHistoryDTO getBookingHistoryById(Long bookingId);

	public boolean updatePreviousReservations(BookingHistoryDTO dto);

	public boolean stopEmailShare(BookingHistoryDTO dto);

	public boolean isBlackUser(Integer userId, Integer guestUserId, Long hotelId);

	public boolean isWhiteUser(Integer userId, Integer guestUserId, Long hotelId);

	public boolean isRemoveWhteCustmrFrmLst(Integer userId);

	public boolean isRemoveWhteGuestCustmrFrmLst(Integer userId);

	public boolean isRemoveBlackCustmrFrmLst(Integer userId);

	public boolean isRemoveBlackGuestCustmrFrmLst(Integer userId);
}
