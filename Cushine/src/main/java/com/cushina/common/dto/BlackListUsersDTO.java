package com.cushina.common.dto;

import java.util.Date;

public class BlackListUsersDTO {	
	
	private Long blackListID;
	private Integer userId;
	private Integer guestUserId;
	private Long hotelID;
	private Date startDate;
	private String strtDate;
	private String userName;
	private String email;
	private String phoneNumber;
	private String hotelName;
	private String hotelAddress;
	private String city;
	private Integer eventOrgId;		
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
	public Integer getGuestUserId() {
		return guestUserId;
	}
	public void setGuestUserId(Integer guestUserId) {
		this.guestUserId = guestUserId;
	}
	public String getStrtDate() {
		return strtDate;
	}
	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	@Override
	public String toString() {
		return "BlackListUsersDTO [blackListID=" + blackListID + ", userId="
				+ userId + ", guestUserId=" + guestUserId + ", hotelID="
				+ hotelID + ", startDate=" + startDate + ", strtDate="
				+ strtDate + ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", hotelName=" + hotelName
				+ ", hotelAddress=" + hotelAddress + ", city=" + city
				+ ", eventOrgId=" + eventOrgId + "]";
	}
	
	
}
