package com.cushina.web.bean;

import java.util.Date;

public class GuestAndHotelReservationInfoBean {

	private Integer guestId;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String phoneNumber;
	private Long hotelId;	
	private Long reservationNumber;	
	private Date checkInDate;
	private Date checkOutDate;
	private Long numberOfDays;	
	private Long roomId;
	private Long categoryId;
	private String emailShare;
	private Date bookingDate;
	
	
	
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Integer getGuestId() {
		return guestId;
	}
	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
	public Long getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(Long reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public Long getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(Long numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}	
	public String getEmailShare() {
		return emailShare;
	}
	public void setEmailShare(String emailShare) {
		this.emailShare = emailShare;
	}
	@Override
	public String toString() {
		return "GuestAndHotelReservationInfoBean [guestId=" + guestId
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", userName=" + userName
				+ ", phoneNumber=" + phoneNumber + ", hotelId=" + hotelId
				+ ", reservationNumber=" + reservationNumber + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate
				+ ", numberOfDays=" + numberOfDays + ", roomId=" + roomId
				+ ", categoryId=" + categoryId + ", emailShare=" + emailShare
				+ ", bookingDate=" + bookingDate + "]";
	}
	
	
	
}
