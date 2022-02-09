package com.cushina.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel_reservations")
public class BookingHistoryEntity {
	
	@Id
	@GeneratedValue
	private Long bookingId;
	private Long reservationNumber;
	private Long hotelID;
	private Date checkInDate;
	private Date checkOutDate;
	private Long numberOfDays;
	private String status;
	private Integer userId;
	private Long roomId;
	private Long categoryId;
	private String notes;
	private String emailShare;
	private boolean isArrived;
	private boolean isPaid;
	private Date bookingDate;
	private Integer guestUserId;
	
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public Long getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(Long reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getEmailShare() {
		return emailShare;
	}
	public void setEmailShare(String emailShare) {
		this.emailShare = emailShare;
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
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Integer getGuestUserId() {
		return guestUserId;
	}
	public void setGuestUserId(Integer guestUserId) {
		this.guestUserId = guestUserId;
	}
	
}
