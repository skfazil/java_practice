package com.events.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="events_reservation")
public class EventsReservationEntity {
	
	@Id
	@GeneratedValue
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
	private String notes;
	private Integer guestId;
	
	
	
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
	@Override
	public String toString() {
		return "EventsReservationEntity [reserveId=" + reserveId
				+ ", referenceNumber=" + referenceNumber + ", userID=" + userID
				+ ", noOfPeople=" + noOfPeople + ", noOfCancellations="
				+ noOfCancellations + ", emailShare=" + emailShare
				+ ", isArrived=" + isArrived + ", isPaid=" + isPaid
				+ ", reservedDate=" + reservedDate + ", eventScheduleId="
				+ eventScheduleId + ", eventOrgId=" + eventOrgId + ", status="
				+ status + ", notes=" + notes + ", guestId=" + guestId + "]";
	}
	
	
		
}
