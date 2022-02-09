package com.massage.common.dto;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class MassageReservationDTO {

	private Integer reserveId;
	private  Integer massagePersonId;
	private String emailShare;
	private Integer scheduleId;
	private Integer noOfpeople;
	private Long referenceNumber;
	private Date reservedDate;
	private String notes;
	private String status;
	private Integer userId;
	private Integer massageProvId;
	private boolean isArried;
	private boolean isPaid;
	private Integer guestId;
	private String startTime;
	private String endTime;
	private String userName;
	private String phoneNumber;
	private String email;
	private String duration;
	private String personName;
	private String address;
	private String massageName;
	
	
	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}

	public boolean isArried() {
		return isArried;
	}

	public void setArried(boolean isArried) {
		this.isArried = isArried;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public Integer getReserveId() {
		return reserveId;
	}

	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}

	public Integer getMassagePersonId() {
		return massagePersonId;
	}

	public void setMassagePersonId(Integer massagePersonId) {
		this.massagePersonId = massagePersonId;
	}

	public String getEmailShare() {
		return emailShare;
	}

	public void setEmailShare(String emailShare) {
		this.emailShare = emailShare;
	}



	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Integer getNoOfpeople() {
		return noOfpeople;
	}

	public void setNoOfpeople(Integer noOfpeople) {
		this.noOfpeople = noOfpeople;
	}

	public Long getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(Long referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getReservedDate() {
		return reservedDate;
	}

	public void setReservedDate(Date reservedDate) {
		this.reservedDate = reservedDate;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMassageProvId() {
		return massageProvId;
	}

	public void setMassageProvId(Integer massageProvId) {
		this.massageProvId = massageProvId;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMassageName() {
		return massageName;
	}

	public void setMassageName(String massageName) {
		this.massageName = massageName;
	}
	
	
	
}
