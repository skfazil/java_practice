package com.cushina.web.bean;

import java.util.Date;

public class HotelAvailabilityBean {

	private Long hotelAvailbleId;
	private Long available;
	private Long booked;
	private Long categoryId;
	private Long charges;
	private Date date;
	private Long hotelID;
	private Long noOFRooms;
	private String selecteDt;
	private String status;
	
	public Long getHotelAvailbleId() {
		return hotelAvailbleId;
	}
	public void setHotelAvailbleId(Long hotelAvailbleId) {
		this.hotelAvailbleId = hotelAvailbleId;
	}
	public Long getAvailable() {
		return available;
	}
	public void setAvailable(Long available) {
		this.available = available;
	}
	public Long getBooked() {
		return booked;
	}
	public void setBooked(Long booked) {
		this.booked = booked;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getCharges() {
		return charges;
	}
	public void setCharges(Long charges) {
		this.charges = charges;
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
	public Long getNoOFRooms() {
		return noOFRooms;
	}
	public void setNoOFRooms(Long noOFRooms) {
		this.noOFRooms = noOFRooms;
	}
	public String getSelecteDt() {
		return selecteDt;
	}
	public void setSelecteDt(String selecteDt) {
		this.selecteDt = selecteDt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HotelAvailabilityBean [hotelAvailbleId=" + hotelAvailbleId
				+ ", available=" + available + ", booked=" + booked
				+ ", categoryId=" + categoryId + ", charges=" + charges
				+ ", date=" + date + ", hotelID=" + hotelID + ", noOFRooms="
				+ noOFRooms + ", selecteDt=" + selecteDt + ", status=" + status
				+ "]";
	}
	
}
