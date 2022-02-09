package com.cushina.service;

import com.cushina.common.dto.BookingHistoryDTO;

public interface ServiceProviderChangeRoomService {

	public BookingHistoryDTO changeReservation(String draggableVal,
			String droppableVal, Long hotelId);

}
