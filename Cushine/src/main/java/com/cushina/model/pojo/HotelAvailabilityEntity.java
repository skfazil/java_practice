package com.cushina.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel_availability")
public class HotelAvailabilityEntity {
	
	@Id
	@GeneratedValue
	private Long hotelAvailbleId;
	private Long hotelID;
	private Long categoryId;
	private Long available;
	private Long booked;
	private Long noOFRooms;	
	private Date date;
	
	public Long getHotelAvailbleId() {
		return hotelAvailbleId;
	}
	public void setHotelAvailbleId(Long hotelAvailbleId) {
		this.hotelAvailbleId = hotelAvailbleId;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	public Long getNoOFRooms() {
		return noOFRooms;
	}
	public void setNoOFRooms(Long noOFRooms) {
		this.noOFRooms = noOFRooms;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String toString() {
		return "HotelAvailabilityEntity [hotelAvailbleId=" + hotelAvailbleId
				+ ", hotelID=" + hotelID + ", categoryId=" + categoryId
				+ ", available=" + available + ", booked=" + booked
				+ ", noOFRooms=" + noOFRooms + ", date=" + date + "]";
	}

}
