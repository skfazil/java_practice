package com.cushina.model.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hotel")
public class HotelEntity {
	
	@Id
	@GeneratedValue
	private Long hotelID; 
	private Long bookingAllowedDuration;
	private Long categoryId;
	private String hotelAddress;	
	private String hotelName;
	private Long phoneNumber;	
	private String email;
	private String image;
	private String webSiteURL;
	private String city;
	private String postalCode;
	private Integer userId;
	
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
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getBookingAllowedDuration() {
		return bookingAllowedDuration;
	}
	public void setBookingAllowedDuration(Long bookingAllowedDuration) {
		this.bookingAllowedDuration = bookingAllowedDuration;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebSiteURL() {
		return webSiteURL;
	}
	public void setWebSiteURL(String webSiteURL) {
		this.webSiteURL = webSiteURL;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
		
}
