package com.cushina.model.dataaccess;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.CategoryDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.RoomInfoDTO;
import com.cushina.web.bean.HotelBean;

public interface PickHotelDao {

	public List<HotelDTO> getHotelDetails(String hotelName);

	public List<HotelAvailabilityDTO> getDatesList(Long hotelID);

	public List<HotelAvailabilityDTO> getDatesListBsdOnCatgry(Long hotelID,
			Long categoryID);

	public List<HotelBean> getHotelDetail(Long hotelId);

	public List<RoomInfoDTO> getRoomInfoByHotel(Long hotelID);

	public List<AvailabilityByDateDTO> getRoomAvailInfo(Long hotelId);

	public List<AvailabilityByDateDTO> getNextRoomAvailInfo(Long lastRecordId);

	public List<CategoryDTO> getCategoryList(Long hotelID);

	public List<Long> getRoomsList(Long hotelID, Long categoryID);

	public List<Long> getWhiteRoomsList(Long hotelID, Long categoryID);

	//
	public List<RoomInfoDTO> getRoomsListOfAllCategories(Long hotelId);

	public List<AvailabilityByDateDTO> getSelectedDateAvailRooms(
			String slectedDate, Long hotelID);

	public Map<Long, String> getCategoryListByMap(Long hotelId);

	public List<AvailabilityByDateDTO> getRoomAvailByCategory(Long categoryId,
			Long hotelID, Date selectedDt);

	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsBasedOnCatgeory(
			String selectedDate, Long hotelID, Long categoryID, Integer dayCount);

	public List<AvailabilityByDateDTO> getLoggedInUserReservedDates(
			Long hotelID, Integer userId);

	public List<AvailabilityByDateDTO> getLoggedInUserReservedDatesOnCategoryType(
			Long hotelID, Integer userId, Long categoryID);

	public boolean isRowsInserted();

	public List<HotelAvailabilityDTO> getDatesBasedOnNoOfDays(Long hotelID,
			Integer noOfDays, Long categoryID);

	public List<AvailabilityByDateDTO> getWhiteRoomsLst(Long hotelId);

	public List<AvailabilityByDateDTO> getWhiteRoomsLstOnCategryType(
			Long hotelId, Long categoryID);
	
	public List<Long> getRoomsAvailList(Long hotelID, Long categoryID);

	

	public List<HotelAvailabilityDTO> getSearchDatesList(String selectedDate,
			Long hotelID);

	public List<HotelAvailabilityDTO> getSearchDatesListBsdOnCatgry(
			String selectedDate, Long hotelID, Long categoryID);

	
}
