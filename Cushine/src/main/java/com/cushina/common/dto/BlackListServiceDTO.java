package com.cushina.common.dto;

import java.util.Date;

public class BlackListServiceDTO {	
	
	private Long blackListID;
	private Integer userId;
	private Long hotelID;
	private Date startDate;
		
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
		return "BlackListUsersDTO [blackListID=" + blackListID + ", userId="
				+ userId + ", hotelID="
				+ hotelID + ", startDate=" + startDate + "]";
	}	
	
}
