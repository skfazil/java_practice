package com.events.model.dataaccess;

import java.util.List;

import com.cushina.common.dto.UserDTO;
import com.events.web.bean.EventsOrganizerBean;

public interface EventUserEmailVisibleDao {

	List<EventsOrganizerBean> getEmailListDetails(Integer orgID, Integer userId);

	void getEventOrgUpdateEmail(Integer userId);

	List<UserDTO> getCustomersList(Integer orgId);

	

}
