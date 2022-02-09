package com.cushina.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.CategoryDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.RoomInfoDTO;
import com.cushina.web.bean.HotelBean;

public interface PickHotelService {

	public List<HotelDTO> getHotelDetails(String hotelName);

	public List<HotelAvailabilityDTO> getDatesList(Long hotelID);

	public List<HotelAvailabilityDTO> getDatesListBsdOnCatgry(Long hotelID,
			Long categoryID);

	public List<HotelBean> getHotelDetail(Long hotelId);

	public List<RoomInfoDTO> getRoomInfoByHotel(Long hotelId);

	public List<AvailabilityByDateDTO> getRoomAvailInfo(Long hotelID,
			Integer noOfDays,String UserName);

	public List<AvailabilityByDateDTO> getNextRoomAvailInfo(Long lastRecordId);

	public List<AvailabilityByDateDTO> getSelectedDateAvailRooms(
			String slectedDate, Long hotelID,String userName);

	public List<CategoryDTO> getCategoryList(Long hotelID);

	public Map<Long, String> getCategoryListByMap(Long hotelId);

	public List<Long> getRoomsList(Long hotelID, Long categoryID,String userName);
	
	public List<Long> getRoomsAvailList(Long hotelID, Long categoryID);

	public List<AvailabilityByDateDTO> getRoomAvailByCategory(Long categoryId,
			Long hotelID, Date selectedDt,String usrName);

	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsBasedOnCatgeory(
			String selectedDate, Long hotelID, Long categoryID, Integer dayCount,String userName);

	public List<AvailabilityByDateDTO> getLoggedInUserReservedDates(
			Long hotelID, Integer userId);

	public List<AvailabilityByDateDTO> getLoggedInUserReservedDatesOnCategoryType(
			Long hotelID, Integer userId, Long categoryID);

	public boolean isRowsInserted();

	public List<HotelAvailabilityDTO> getDatesBadedOnNoOfDays(Long hotelID,
			Integer noOfDays, Long categoryID);
	
	
	public List<HotelAvailabilityDTO> getDatesBadedOnNoOfDaysAndSelectedDate(String selectedDate,Long hotelID,
			Integer noOfDays, Long categoryID);

}
