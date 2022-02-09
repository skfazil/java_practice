package com.events.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.UserDTO;
import com.events.model.dataaccess.EventUserEmailVisibleDao;
import com.events.model.dataaccess.EventWhitelistUserDao;
import com.events.service.EventUserEmailVisibleService;
import com.events.web.bean.EventsOrganizerBean;

@Service
public class EventEmailVisibleUserServiceImpl implements EventUserEmailVisibleService{
	
	@Autowired
	private EventUserEmailVisibleDao userEmailVisibleDao;
	
	@Autowired
	private EventWhitelistUserDao whitelistUserDao;


	@Transactional
	public List<EventsOrganizerBean> getEmailListDetails(Integer orgID, Integer userId) {
		
		return userEmailVisibleDao.getEmailListDetails( orgID, userId);
	}

	@Transactional
	public void getEventOrgUpdateEmail(Integer userId) {
		userEmailVisibleDao.getEventOrgUpdateEmail(userId);
		
	}

	@Transactional
	public List<UserDTO> getCustomersList(Integer orgId) {
		
		return userEmailVisibleDao.getCustomersList(orgId);
	}

	@Transactional
	public UserDTO getUserDetail(String userName) {
		
		return whitelistUserDao.getUserDetail(userName);
	}

	

}
