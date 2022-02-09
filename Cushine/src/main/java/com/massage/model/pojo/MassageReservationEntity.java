package com.massage.model.pojo;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="massage_reservation")
public class MassageReservationEntity {

	@Id
	@GeneratedValue
	private Integer reserveId;
	private  Integer massagePersonId;
	private String emailShare;
	private boolean isArried;
	private boolean isPaid;
	private Integer scheduleId;
	private Integer noOfpeople;
	private Long referenceNumber;
	private Date reservedDate;
	private String notes;
	private String status;
    private Integer userId;
    private Integer guestId;
	private Integer massageProvId;

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

	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}
	
	
	
}
