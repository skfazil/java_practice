package com.cushina.common.dto;

import java.util.Date;

/**
 * @author VT-SANTHOSH
 * 
 */
public class UserDTO {

	private Integer userId;
	private String language;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private String confirmPassWord;
	private String notificationDuration;
	private String title;
	private String streetName;
	private String postalCode;
	private String city;
	private String country;
	private Date dateOfBirth;
	private String dob;
	private String status;
	private Long contactNumber;
	private String token;
	private Date joinDate;
	private boolean userTokenExists;
	private Long hotelID;
	private Date mailResentDate;
	private Integer guestUserId;
	private String strtDate;
	private String phoneNumber;
	private String isSP;
	private Boolean isServiceProvider;
	private String dateJoin;
	private Long refNumber;
	private Integer eventOrgId;
	
	
	public Long getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(Long refNumber) {
		this.refNumber = refNumber;
	}
	
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	public String getIsSP() {
		return isSP;
	}
	public void setIsSP(String isSP) {
		this.isSP = isSP;
	}
	public Boolean getIsServiceProvider() {
		return isServiceProvider;
	}
	public void setIsServiceProvider(Boolean isServiceProvider) {
		this.isServiceProvider = isServiceProvider;
	}	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassWord() {
		return confirmPassWord;
	}
	public void setConfirmPassWord(String confirmPassWord) {
		this.confirmPassWord = confirmPassWord;
	}
	public String getNotificationDuration() {
		return notificationDuration;
	}
	public void setNotificationDuration(String notificationDuration) {
		this.notificationDuration = notificationDuration;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public boolean isUserTokenExists() {
		return userTokenExists;
	}
	public void setUserTokenExists(boolean userTokenExists) {
		this.userTokenExists = userTokenExists;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	public Date getMailResentDate() {
		return mailResentDate;
	}
	public void setMailResentDate(Date mailResentDate) {
		this.mailResentDate = mailResentDate;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDateJoin() {
		return dateJoin;
	}
	public void setDateJoin(String dateJoin) {
		this.dateJoin = dateJoin;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", language=" + language
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", userName=" + userName + ", password="
				+ password + ", confirmPassWord=" + confirmPassWord
				+ ", notificationDuration=" + notificationDuration + ", title="
				+ title + ", streetName=" + streetName + ", postalCode="
				+ postalCode + ", city=" + city + ", country=" + country
				+ ", dateOfBirth=" + dateOfBirth + ", dob=" + dob + ", status="
				+ status + ", contactNumber=" + contactNumber + ", token="
				+ token + ", joinDate=" + joinDate + ", userTokenExists="
				+ userTokenExists + ", hotelID=" + hotelID
				+ ", mailResentDate=" + mailResentDate + ", guestUserId="
				+ guestUserId + ", strtDate=" + strtDate + ", phoneNumber="
				+ phoneNumber + ", isSP=" + isSP + ", isServiceProvider="
				+ isServiceProvider + ", dateJoin=" + dateJoin + ", refNumber="
				+ refNumber + "]";
	}
	
	
	

}
