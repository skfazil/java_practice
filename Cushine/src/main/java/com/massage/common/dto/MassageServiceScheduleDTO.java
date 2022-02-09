package com.massage.common.dto;



import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.events.common.dto.EventListDTO;
import com.events.common.dto.EventScheduleDTO;


public class MassageServiceScheduleDTO{

	private Integer scheduleId;
	private Date date;
	private Date startTime;
	private Date endTime;
	private  Integer massagePersonId;
	private String colorCode;
	private String duration;
	private Integer userId;
	private Integer massageProvId;
	private String evntStrtTme;
	private String evntEndTme;
	private Integer divCount;
	private String evntDt;
	private String personName;
	private String userNotes;
	private Boolean isPaid;
	private Boolean isArrived;
	private boolean haveReservation;
	private Long refNumber;
	private String userName;
	private String email;
	private String phoneNumber;
	private Integer bookedSeats;
	private String strtTme;
	private String endTme;
	private String pdfPath;
	private boolean haveNote;
	private boolean havePaid;
	
	
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
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getMassagePersonId() {
		return massagePersonId;
	}
	public void setMassagePersonId(Integer massagePersonId) {
		this.massagePersonId = massagePersonId;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEvntStrtTme() {
		return evntStrtTme;
	}
	public void setEvntStrtTme(String evntStrtTme) {
		this.evntStrtTme = evntStrtTme;
	}
	public String getEvntEndTme() {
		return evntEndTme;
	}
	public void setEvntEndTme(String evntEndTme) {
		this.evntEndTme = evntEndTme;
	}
	public Integer getDivCount() {
		return divCount;
	}
	public void setDivCount(Integer divCount) {
		this.divCount = divCount;
	}
	public String getEvntDt() {
		return evntDt;
	}
	public void setEvntDt(String evntDt) {
		this.evntDt = evntDt;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getUserNotes() {
		return userNotes;
	}
	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}
	public Boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}
	public Boolean getIsArrived() {
		return isArrived;
	}
	public void setIsArrived(Boolean isArrived) {
		this.isArrived = isArrived;
	}
	public boolean isHaveReservation() {
		return haveReservation;
	}
	public void setHaveReservation(boolean haveReservation) {
		this.haveReservation = haveReservation;
	}
	public Long getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(Long refNumber) {
		this.refNumber = refNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public Integer getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(Integer bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public String getStrtTme() {
		return strtTme;
	}
	public void setStrtTme(String strtTme) {
		this.strtTme = strtTme;
	}
	public String getEndTme() {
		return endTme;
	}
	public void setEndTme(String endTme) {
		this.endTme = endTme;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public boolean isHaveNote() {
		return haveNote;
	}
	public void setHaveNote(boolean haveNote) {
		this.haveNote = haveNote;
	}
	public boolean isHavePaid() {
		return havePaid;
	}
	public void setHavePaid(boolean havePaid) {
		this.havePaid = havePaid;
	}
	
	public String toString() {
		return "MassageServiceScheduleDTO [scheduleId=" + scheduleId
				+ ", date=" + date + ", startTime=" + startTime + ", endTime="
				+ endTime + ", massagePersonId=" + massagePersonId
				+ ", colorCode=" + colorCode + ", duration=" + duration
				+ ", userId=" + userId + ", massageProvId=" + massageProvId
				+ ", evntStrtTme=" + evntStrtTme + ", evntEndTme=" + evntEndTme
				+ ", divCount=" + divCount + ", evntDt=" + evntDt
				+ ", personName=" + personName + ", userNotes=" + userNotes
				+ ", isPaid=" + isPaid + ", isArrived=" + isArrived
				+ ", haveReservation=" + haveReservation + ", refNumber="
				+ refNumber + ", userName=" + userName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", bookedSeats="
				+ bookedSeats + ", strtTme=" + strtTme + ", endTme=" + endTme
				+ ", pdfPath=" + pdfPath + ", haveNote=" + haveNote
				+ ", havePaid=" + havePaid + "]";
	}
	
	
	
	
	
}
