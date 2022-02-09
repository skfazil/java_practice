package com.cushina.web.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


public class WhiteListServiceBean {
	
	
	private Long whiteListID;
	private Integer userId;
	private Long hotelID;
	private Date startDate;
	
	public Long getWhiteListID() {
		return whiteListID;
	}
	public void setWhiteListID(Long whiteListID) {
		this.whiteListID = whiteListID;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	

}
