package com.cushina.web.bean;

import java.util.Date;

public class BlackListServiceBean {

	private Long blackListID;
	private Long hotelID;
	private Integer userId;
	private Date startDate;
	private String strtDate;

	public String getStrtDate() {
		return strtDate;
	}

	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getBlackListID() {
		return blackListID;
	}

	public void setBlackListID(Long blackListID) {
		this.blackListID = blackListID;
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

	public String toString() {
		return "BlackListUsersBean [blackListID=" + blackListID + ", hotelID="
				+ hotelID + ", userId=" + userId + ", startDate=" + startDate
				+ ", strtDate=" + strtDate + "]";
	}
	
}
