package com.cushina.model.pojo;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_profile")
public class UserEntity {
		
	@Id
	@GeneratedValue
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
    private String token;
    private Date joinDate;
	private String status;	 
	private String dob;
	private Long contactNumber;
	private String phoneNumber;
	private boolean userTokenExists;
	private Long hotelID;
	private Date mailResentDate;
	private Boolean isServiceProvider;
	
   	public Boolean getIsServiceProvider() {
		return isServiceProvider;
	}
	public void setIsServiceProvider(Boolean isServiceProvider) {
		this.isServiceProvider = isServiceProvider;
	}
   	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	public String toString() {
		return "UserEntity [userId=" + userId + ", language=" + language
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", userName=" + userName + ", password="
				+ password + ", confirmPassWord=" + confirmPassWord
				+ ", notificationDuration=" + notificationDuration + ", title="
				+ title + ", streetName=" + streetName + ", postalCode="
				+ postalCode + ", city=" + city + ", country=" + country
				+ ", dateOfBirth=" + dateOfBirth + ", token=" + token
				+ ", joinDate=" + joinDate + ", status=" + status + ", dob="
				+ dob + ", contactNumber=" + contactNumber + ", phoneNumber="
				+ phoneNumber + ", userTokenExists=" + userTokenExists
				+ ", hotelID=" + hotelID + ", mailResentDate=" + mailResentDate
				+ "]";
	}
	
}
