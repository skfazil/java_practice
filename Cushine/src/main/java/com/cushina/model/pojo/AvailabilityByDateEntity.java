package com.cushina.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="availability_bydate")
public class AvailabilityByDateEntity {
	
	@Id
	@GeneratedValue
	private Long availabilityID;
	private Long categoryId;
	private Long roomId;
	private Date date;
	private Long hotelID;
	private String statusCode;
	private String colourCode;
	private Integer userId;
	private Long reservationNumber;
	private Integer guestUsrId;
	
	public Long getAvailabilityID() {
		return availabilityID;
	}
	public void setAvailabilityID(Long availabilityID) {
		this.availabilityID = availabilityID;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getColourCode() {
		return colourCode;
	}
	public void setColourCode(String colourCode) {
		this.colourCode = colourCode;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Long getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(Long reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public Integer getGuestUsrId() {
		return guestUsrId;
	}
	public void setGuestUsrId(Integer guestUsrId) {
		this.guestUsrId = guestUsrId;
	}
	
}
