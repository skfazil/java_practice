package com.cushina.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.model.dataaccess.EmailVisibleDAO;
import com.cushina.service.EmailVisibleService;
import com.cushina.web.bean.HotelBean;

@Service
public class EmailVisibleServiceImpl implements EmailVisibleService {

	@Autowired
	private EmailVisibleDAO emailVisibleDAO;

	@Transactional
	public List<HotelBean> getEmailListDetails(Long hotelID, Integer userId) {
		return emailVisibleDAO.getEmailListDetails(hotelID, userId);
	}

	@Transactional
	public void getUpdateEmail(Integer userId) {
		 emailVisibleDAO.getUpdateEmail(userId);
	}

	@Transactional
	public List<BookingHistoryDTO> getServiceEmailListDetails(Long hotelID) {
		return emailVisibleDAO.getServiceEmailListDetails(hotelID);
	}

}
