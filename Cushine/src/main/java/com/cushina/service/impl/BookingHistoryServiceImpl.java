package com.cushina.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.model.dataaccess.BookingHistoryDao;
import com.cushina.service.BookingHistoryService;

@Service
public class BookingHistoryServiceImpl implements BookingHistoryService {

	@Autowired
	private BookingHistoryDao dao;

	@Transactional
	public List<Map<HotelDTO, BookingHistoryDTO>> getBookingHistoryDetails(
			Integer userId, Integer quickUserId, Long hotelID) {
		return dao.getBookingHistoryDetails(userId, userId, hotelID);
	}

	@Transactional
	public AvailabilityByDateDTO getAvailblityInfo(Long availabilityID) {
		return dao.getAvailblityInfo(availabilityID);
	}
}
