package com.cushina.common.dto;

import java.util.Date;

public class AvailabilityByDateDTO {
	
	private Long availabilityID;
	private Long categoryId;
	private Long roomId;
	private Date date;
	private Long hotelID;
	private String statusCode;
	private String colourCode;
	private String categoryName;
	private Integer availcnt;
	private Integer userId;
	private String userName;
	private String phoneNumber;
	private String roomAvailDate;
	private Long reservationNumber;
	private String notes;
	private boolean isArrived;
	private boolean isPaid;
	private Integer guestUsrId;
	
	public Long getAvailabilityID() {
		return availabilityID;
	}
	public void setAvailabilityID(Long availabilityID) {
		this.availabilityID = availabilityID;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getColourCode() {
		return colourCode;
	}
	public void setColourCode(String colourCode) {
		this.colourCode = colourCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getAvailcnt() {
		return availcnt;
	}
	public void setAvailcnt(Integer availcnt) {
		this.availcnt = availcnt;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRoomAvailDate() {
		return roomAvailDate;
	}
	public void setRoomAvailDate(String roomAvailDate) {
		this.roomAvailDate = roomAvailDate;
	}
	public Long getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(Long reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public boolean isArrived() {
		return isArrived;
	}
	public void setArrived(boolean isArrived) {
		this.isArrived = isArrived;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public Integer getGuestUsrId() {
		return guestUsrId;
	}
	public void setGuestUsrId(Integer guestUsrId) {
		this.guestUsrId = guestUsrId;
	}
	
	public String toString() {
		return "AvailabilityByDateDTO [availabilityID=" + availabilityID
				+ ", categoryId=" + categoryId + ", roomId=" + roomId
				+ ", date=" + date + ", hotelID=" + hotelID + ", statusCode="
				+ statusCode + ", colourCode=" + colourCode + ", categoryName="
				+ categoryName + ", availcnt=" + availcnt + ", userId="
				+ userId + ", userName=" + userName + ", phoneNumber="
				+ phoneNumber + ", roomAvailDate=" + roomAvailDate
				+ ", reservationNumber=" + reservationNumber + ", notes="
				+ notes + ", isArrived=" + isArrived + ", isPaid=" + isPaid
				+ ", guestUsrId=" + guestUsrId + "]";
	}
	
	
}
