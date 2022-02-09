package com.cushina.common.dto;

public class GuestUserDTO {

	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String phoneNumber;
	private Long hotelId;
	private Long servProId;
	private Boolean isUpdate;
	
	
	
	public Boolean getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public Long getServProId() {
		return servProId;
	}
	public void setServProId(Long servProId) {
		this.servProId = servProId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	@Override
	public String toString() {
		return "GuestUserDTO [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", userName="
				+ userName + ", phoneNumber=" + phoneNumber + ", hotelId="
				+ hotelId + ", servProId=" + servProId + ", isUpdate="
				+ isUpdate + "]";
	}
	
	
	
	
}
