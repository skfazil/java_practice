package com.cushina.model.dataaccess;

import java.util.List;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.web.bean.HotelBean;

public interface EmailVisibleDAO {

	public List<HotelBean> getEmailListDetails(Long hotelID, Integer userId);

	public void getUpdateEmail(Integer userId);

	public List<BookingHistoryDTO> getServiceEmailListDetails(Long hotelID);
}
