package com.cushina.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.model.dataaccess.ReservationByRoomDao;
import com.cushina.service.ReservationByRoomService;

@Service
public class ReservationByRoomServiceImpl implements ReservationByRoomService {

	private Logger logger = Logger.getLogger(ReservationByRoomServiceImpl.class
			.getName());

	@Autowired
	private ReservationByRoomDao roomServiceDao;

	@Transactional
	public List<AvailabilityByDateDTO> getRoomAvailByDaysCount(Long categoryId,
			Long hotelID, Long roomNO, Integer dayCount, Date selectedDt) {
		logger.info("getRoomAvailByDaysCount daoImpl : start");
		
		List<AvailabilityByDateDTO> availDtList = new ArrayList<AvailabilityByDateDTO>();

		List<AvailabilityByDateDTO> roomAvailByDaysCount = roomServiceDao
				.getRoomAvailByDaysCount(categoryId, hotelID, roomNO, dayCount,
						selectedDt);
		
		if(roomNO != 0 && dayCount!= 0){
			for (int i = 0; i < roomAvailByDaysCount.size(); i++) {
				Integer count = 0;
				for (int j = i + 1; j < roomAvailByDaysCount.size(); j++) {
					if (roomAvailByDaysCount.get(i).getStatusCode()
							.equals(roomAvailByDaysCount.get(j).getStatusCode())) {
						++count;
						if (count.equals(dayCount)) {
							AvailabilityByDateDTO availDt = roomAvailByDaysCount
									.get(i);
							availDtList.add(availDt);
							logger.info("String : " + roomAvailByDaysCount.get(i));
						}
					} else {
						break;
					}
				}
			}
			return availDtList;
		}
		logger.info("getRoomAvailByDaysCount daoImpl : start");
		return roomAvailByDaysCount;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getRoomAvailByCategoryAndRoomNum(
			Long categoryId, Long hotelID, Long roomNO,Date currDt) {
		return roomServiceDao.getRoomAvailByCategoryAndRoomNum(categoryId,
				hotelID, roomNO, currDt);
	}

	@Transactional
	public List<AvailabilityByDateDTO> getDateListByRoomNo(Long roomNum,
			Date selectedDt, Long hotelID, Long categoryId) {
		return roomServiceDao.getDateListByRoomNo(roomNum, selectedDt, hotelID,
				categoryId);
	}
}
