package com.events.web.bean;

import java.util.Date;

public class EventBlackListBean {

	private Integer blackListId;
	private Integer orgId;
	private Integer scheduleId;
	private Date addedDate;
	private Integer userId;
	private Integer guestId;
	private String userName;
	private String email;
	private String phoneNumber;
	private String statusMsg;
	private String hotelName;
	private String hotelAddress;
	private String city;
	private String strtDate;
	public Integer getBlackListId() {
		return blackListId;
	}
	public void setBlackListId(Integer blackListId) {
		this.blackListId = blackListId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGuestId() {
		return guestId;
	}
	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
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
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
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
	
	public String getStrtDate() {
		return strtDate;
	}
	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}
	public String toString() {
		return "EventBlackListBean [blackListId=" + blackListId + ", orgId="
				+ orgId + ", scheduleId=" + scheduleId + ", addedDate="
				+ addedDate + ", userId=" + userId + ", guestId=" + guestId
				+ ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", statusMsg=" + statusMsg
				+ ", hotelName=" + hotelName + ", hotelAddress=" + hotelAddress
				+ ", city=" + city + "]";
	}
	
}
