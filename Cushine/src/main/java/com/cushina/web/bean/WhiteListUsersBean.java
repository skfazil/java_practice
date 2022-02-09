package com.cushina.web.bean;

import java.util.Date;

public class WhiteListUsersBean {
	private Date startDate;
	private Long whiteListID;
	private Integer userId;
	private Integer guestUserId;
	private Long hotelID;
	private String strtDate;
	private String userName;
	private String email;
	private String phoneNumber;
	private String statusMsg;
	private String hotelName;
	private String hotelAddress;
	private String city;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStrtDate() {
		return strtDate;
	}

	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}

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

	public Integer getGuestUserId() {
		return guestUserId;
	}

	public void setGuestUserId(Integer guestUserId) {
		this.guestUserId = guestUserId;
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

	public String toString() {
		return "WhiteListUsersBean [startDate=" + startDate + ", whiteListID="
				+ whiteListID + ", userId=" + userId + ", guestUserId="
				+ guestUserId + ", hotelID=" + hotelID + ", strtDate="
				+ strtDate + ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", statusMsg=" + statusMsg
				+ ", hotelName=" + hotelName + ", hotelAddress=" + hotelAddress
				+ ", city=" + city + "]";
	}
}
