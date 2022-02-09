package com.events.model.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity(name="event_guest")
public class EventGuestUserEntity {

	@Id
	@GeneratedValue
	private Integer guestUserId;
	private String firstName;
	private String lastName;
	private String userName;
	private Integer eventOrgId;
	private Integer scheduleId;
	private Integer servProId;
	private String email;
	private String phoneNumber;
	
	public Integer getGuestUserId() {
		return guestUserId;
	}
	public void setGuestUserId(Integer guestUserId) {
		this.guestUserId = guestUserId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Integer getServProId() {
		return servProId;
	}
	public void setServProId(Integer servProId) {
		this.servProId = servProId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "EventGuestUserEntity [guestUserId=" + guestUserId
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + ", eventOrgId=" + eventOrgId
				+ ", scheduleId=" + scheduleId + ", servProId=" + servProId
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}
	
	
	
}
