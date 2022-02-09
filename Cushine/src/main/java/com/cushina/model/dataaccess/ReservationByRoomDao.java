package com.cushina.model.dataaccess;

import java.util.Date;
import java.util.List;

import com.cushina.common.dto.AvailabilityByDateDTO;

public interface ReservationByRoomDao {

	public List<AvailabilityByDateDTO> getRoomAvailByCategoryAndRoomNum(
			Long categoryId, Long hotelID, Long roomNO,Date currDt);

	public List<AvailabilityByDateDTO> getRoomAvailByDaysCount(Long categoryId,
			Long hotelID, Long roomNO, Integer dayCount, Date selectedDt);

	public List<AvailabilityByDateDTO> getDateListByRoomNo(Long roomNum,
			Date selectedDt, Long hotelID, Long categoryId);
}
