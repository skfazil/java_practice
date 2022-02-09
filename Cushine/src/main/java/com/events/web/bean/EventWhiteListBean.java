package com.events.web.bean;

import java.util.Date;

public class EventWhiteListBean {

	private Integer whiteListId;
	private Integer orgId;
	private Integer scheduleId;
	private Date addedDate;
	private Integer userId;
	private Integer guestId;
	private String startDate;
	private String userName;
	private String email;
	private String phoneNumber;
	private String hotelName;
	private String hotelAddress;
	private String city;
	private String statusMsg;
	public Integer getWhiteListId() {
		return whiteListId;
	}
	public void setWhiteListId(Integer whiteListId) {
		this.whiteListId = whiteListId;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String toString() {
		return "EventWhiteListBean [whiteListId=" + whiteListId + ", orgId="
				+ orgId + ", scheduleId=" + scheduleId + ", addedDate="
				+ addedDate + ", userId=" + userId + ", guestId=" + guestId
				+ ", startDate=" + startDate + ", userName=" + userName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", hotelName=" + hotelName + ", hotelAddress=" + hotelAddress
				+ ", city=" + city + ", statusMsg=" + statusMsg + "]";
	}
	
}
