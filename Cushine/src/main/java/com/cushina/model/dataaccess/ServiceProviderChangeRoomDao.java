package com.cushina.model.dataaccess;

import com.cushina.common.dto.BookingHistoryDTO;

public interface ServiceProviderChangeRoomDao {

	/*public boolean isChangeReservation(AvailabilityByDateDTO draggableInfo,
			BookingHistoryDTO droppableInfo);
	 */
	public boolean isUpdateDraggableRecrd(BookingHistoryDTO histryInfo);
	
	public BookingHistoryDTO getHistryDetails(Long reservationNumber);
	
	public boolean isDroppableRoom(BookingHistoryDTO droppable); 
}
