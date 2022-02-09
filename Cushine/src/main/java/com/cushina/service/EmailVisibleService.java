package com.cushina.service;

import java.util.List;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.web.bean.HotelBean;

public interface EmailVisibleService {

	public List<HotelBean> getEmailListDetails(Long hotelID, Integer userId);

	public void getUpdateEmail(Integer userId);

	public List<BookingHistoryDTO> getServiceEmailListDetails(Long hotelID);
}
