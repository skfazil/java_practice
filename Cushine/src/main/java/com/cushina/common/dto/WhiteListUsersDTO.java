package com.cushina.common.dto;

import java.util.Date;

public class WhiteListUsersDTO {
	private Long whiteListID;
	private Integer userId;
	private Long hotelID;
	private Date startDate;
	private Integer guestUserId;
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

	public Long getHotelID() {
		return hotelID;
	}

	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
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
		return "WhiteListUsersDTO [whiteListID=" + whiteListID + ", userId="
				+ userId + ", hotelID=" + hotelID + ", startDate=" + startDate
				+ ", guestUserId=" + guestUserId + ", strtDate=" + strtDate
				+ ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", hotelName=" + hotelName
				+ ", hotelAddress=" + hotelAddress + ", city=" + city
				+ ", eventOrgId=" + eventOrgId + "]";
	}

}
