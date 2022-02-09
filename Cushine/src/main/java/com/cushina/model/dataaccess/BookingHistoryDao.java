package com.cushina.model.dataaccess;

import java.util.List;
import java.util.Map;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.HotelDTO;

public interface BookingHistoryDao {

	public List<Map<HotelDTO, BookingHistoryDTO>> getBookingHistoryDetails(
			Integer userId, Integer quickUserId, Long hotelID);

	public AvailabilityByDateDTO getAvailblityInfo(Long availabilityID);

}
