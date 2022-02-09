package com.events.common.dto;

import java.util.Date;



public class EventsReservationDTO {

	private Integer reserveId;
	private Long referenceNumber;
	private Integer userID;
	private Integer noOfPeople;
	private Integer noOfCancellations;
	private String emailShare;
	private boolean isArrived;
	private boolean isPaid;
	private Date reservedDate;
	private Integer eventScheduleId;
	private Integer eventOrgId;
	private String status;
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
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
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
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	@Override
	public String toString() {
		return "EventsReservationDTO [reserveId=" + reserveId
				+ ", referenceNumber=" + referenceNumber + ", userID=" + userID
				+ ", noOfPeople=" + noOfPeople + ", noOfCancellations="
				+ noOfCancellations + ", emailShare=" + emailShare
				+ ", isArrived=" + isArrived + ", isPaid=" + isPaid
				+ ", reservedDate=" + reservedDate + ", eventScheduleId="
				+ eventScheduleId + ", eventOrgId=" + eventOrgId + ", status="
				+ status + ", startTime=" + startTime + ", endTime=" + endTime
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
