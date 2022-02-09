package com.events.web.bean;

import java.util.Date;



public class EventsReservationBean {

	private Integer reserveId;
	private Long referenceNumber;
	private Integer userID;
	private Integer noOfPeople;
	private Integer noOfCancellations;
	private String emailShare;
	private boolean isArrived;
	private boolean isPaid;
	private Date reservedDate;
	private String status;
	private Integer eventScheduleId;
	private String eventOrgName;
	private String eventOrgAddress;
	private String eventOrgCity;
	private String eventOrgPostalCode;
	private String eventOrgEmail;
	private String eventOrgWebiste;
	private String eventOrgPhoneNumber;
	private String checkInTime;
	private String checkOutTime;
	private String numberOfDays;
	private String startTime;
	private String endTime;
	private String userName;
	private String phoneNumber;
	private String email;
	private String duration;
	private String notes;
	private String guideName;
	private String address;
	private String orgName;
	private Long availableSeats;
	private String eventName;
	private Integer guestId;
	private Date eventDate;
	private Date startTme;
	private Date endTme;
	private String notificationPeriod;
	
	
	
	public String getNotificationPeriod() {
		return notificationPeriod;
	}
	public void setNotificationPeriod(String notificationPeriod) {
		this.notificationPeriod = notificationPeriod;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Date getStartTme() {
		return startTme;
	}
	public void setStartTme(Date startTme) {
		this.startTme = startTme;
	}
	public Date getEndTme() {
		return endTme;
	}
	public void setEndTme(Date endTme) {
		this.endTme = endTme;
	}
	public Integer getGuestId() {
		return guestId;
	}
	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public String getEventOrgName() {
		return eventOrgName;
	}
	public void setEventOrgName(String eventOrgName) {
		this.eventOrgName = eventOrgName;
	}
	public String getEventOrgAddress() {
		return eventOrgAddress;
	}
	public void setEventOrgAddress(String eventOrgAddress) {
		this.eventOrgAddress = eventOrgAddress;
	}
	public String getEventOrgCity() {
		return eventOrgCity;
	}
	public void setEventOrgCity(String eventOrgCity) {
		this.eventOrgCity = eventOrgCity;
	}
	public String getEventOrgPostalCode() {
		return eventOrgPostalCode;
	}
	public void setEventOrgPostalCode(String eventOrgPostalCode) {
		this.eventOrgPostalCode = eventOrgPostalCode;
	}
	public String getEventOrgEmail() {
		return eventOrgEmail;
	}
	public void setEventOrgEmail(String eventOrgEmail) {
		this.eventOrgEmail = eventOrgEmail;
	}
	public String getEventOrgWebiste() {
		return eventOrgWebiste;
	}
	public void setEventOrgWebiste(String eventOrgWebiste) {
		this.eventOrgWebiste = eventOrgWebiste;
	}
	public String getEventOrgPhoneNumber() {
		return eventOrgPhoneNumber;
	}
	public void setEventOrgPhoneNumber(String eventOrgPhoneNumber) {
		this.eventOrgPhoneNumber = eventOrgPhoneNumber;
	}
	public Integer getReserveId() {
		return reserveId;
	}
	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}
	public Long getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(Long referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getNoOfPeople() {
		return noOfPeople;
	}
	public void setNoOfPeople(Integer noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
	public Integer getNoOfCancellations() {
		return noOfCancellations;
	}
	public void setNoOfCancellations(Integer noOfCancellations) {
		this.noOfCancellations = noOfCancellations;
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
	public Date getReservedDate() {
		return reservedDate;
	}
	public void setReservedDate(Date reservedDate) {
		this.reservedDate = reservedDate;
	}
	public Integer getEventScheduleId() {
		return eventScheduleId;
	}
	public void setEventScheduleId(Integer eventScheduleId) {
		this.eventScheduleId = eventScheduleId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(Long availableSeats) {
		this.availableSeats = availableSeats;
	}
	@Override
	public String toString() {
		return "EventsReservationBean [reserveId=" + reserveId
				+ ", referenceNumber=" + referenceNumber + ", userID=" + userID
				+ ", noOfPeople=" + noOfPeople + ", noOfCancellations="
				+ noOfCancellations + ", emailShare=" + emailShare
				+ ", isArrived=" + isArrived + ", isPaid=" + isPaid
				+ ", reservedDate=" + reservedDate + ", status=" + status
				+ ", eventScheduleId=" + eventScheduleId + ", eventOrgName="
				+ eventOrgName + ", eventOrgAddress=" + eventOrgAddress
				+ ", eventOrgCity=" + eventOrgCity + ", eventOrgPostalCode="
				+ eventOrgPostalCode + ", eventOrgEmail=" + eventOrgEmail
				+ ", eventOrgWebiste=" + eventOrgWebiste
				+ ", eventOrgPhoneNumber=" + eventOrgPhoneNumber
				+ ", checkInTime=" + checkInTime + ", checkOutTime="
				+ checkOutTime + ", numberOfDays=" + numberOfDays
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", userName=" + userName + ", phoneNumber=" + phoneNumber
				+ ", email=" + email + ", duration=" + duration + ", notes="
				+ notes + ", guideName=" + guideName + ", address=" + address
				+ ", orgName=" + orgName + ", availableSeats=" + availableSeats
				+ ", eventName=" + eventName + ", guestId=" + guestId
				+ ", eventDate=" + eventDate + ", startTme=" + startTme
				+ ", endTme=" + endTme + ", notificationPeriod="
				+ notificationPeriod + "]";
	}
	
	
	
	
		
}
