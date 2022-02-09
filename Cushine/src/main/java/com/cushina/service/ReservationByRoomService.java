package com.cushina.service;

import java.util.Date;
import java.util.List;

import com.cushina.common.dto.AvailabilityByDateDTO;

public interface ReservationByRoomService {

	public List<AvailabilityByDateDTO> getRoomAvailByCategoryAndRoomNum(
			Long categoryId, Long hotelID, Long roomNO, Date currDt);

	public List<AvailabilityByDateDTO> getRoomAvailByDaysCount(Long categoryID,
			Long hotelID, Long roomNum, Integer dayCount, Date currDt);

	public List<AvailabilityByDateDTO> getDateListByRoomNo(Long roomNum,
			Date selectedDt, Long hotelID, Long categoryId);

}
