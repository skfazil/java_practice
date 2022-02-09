package com.cushina.web.bean;

public class HotelBean {

	private Long hotelID; 
	private Long bookingAllowedDuration;
	private Long categoryId;
	private String hotelAddress;
	private Long phoneNumber;
	private String email;
	private String webSiteURL;
	private String hotelName;
	private String image;
	private Integer userId;
    private String strtDate;
	private String city;
	public String getStrtDate() {
		return strtDate;
	}
	public void setStrtDate(String strtDate) {
		this.strtDate = strtDate;
	}
	private String postalCode;
	
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
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
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String toString() {
		return "HotelBean [hotelID=" + hotelID + ", bookingAllowedDuration="
				+ bookingAllowedDuration + ", categoryId=" + categoryId
				+ ", hotelAddress=" + hotelAddress + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", webSiteURL="
				+ webSiteURL + ", hotelName=" + hotelName + ", image=" + image
				+ ", userId=" + userId + ", city=" + city + ", postalCode="
				+ postalCode + "]";
	}
}
