package com.events.web.bean;

import java.util.Date;
import java.util.List;

import com.events.common.dto.EventScheduleDTO;

public class EventScheduleBean {
	private Integer scheduleId;
	private Integer eventOrgId;
	private Integer eventId;
	private Date date;
	private Date startTime;
	private Date endTime;
	private Long waitingCapacity;
	private Long maxCapacity;
	private String isGuideAvialble;
	private String duration;
	private Long availableSeats;
	private String colorCode;
	private String evntDt;
	private String evntStrtTme;
	private String evntEndTme;
	private Integer divCount;
	private String eventName;
	private String guideName;
	private Integer guideId;
	private String userNotes;
	private Boolean isPaid;
	private Boolean isArrived;
	private List<EventScheduleBean> eventScheduleBeans;
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
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
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
	public Long getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(Long refNumber) {
		this.refNumber = refNumber;
	}
	public boolean isHaveReservation() {
		return haveReservation;
	}
	public void setHaveReservation(boolean haveReservation) {
		this.haveReservation = haveReservation;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
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
	public Long getWaitingCapacity() {
		return waitingCapacity;
	}
	public void setWaitingCapacity(Long waitingCapacity) {
		this.waitingCapacity = waitingCapacity;
	}
	public Long getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(Long maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public String getIsGuideAvialble() {
		return isGuideAvialble;
	}
	public void setIsGuideAvialble(String isGuideAvialble) {
		this.isGuideAvialble = isGuideAvialble;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Long getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(Long availableSeats) {
		this.availableSeats = availableSeats;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getEvntDt() {
		return evntDt;
	}
	public void setEvntDt(String evntDt) {
		this.evntDt = evntDt;
	}
	public String getEvntStrtTme() {
		return evntStrtTme;
	}
	public void setEvntStrtTme(String evntStrtTme) {
		this.evntStrtTme = evntStrtTme;
	}
	
	public List<EventScheduleBean> getEventScheduleBeans() {
		return eventScheduleBeans;
	}
	public void setEventScheduleBeans(List<EventScheduleBean> eventScheduleBeans) {
		this.eventScheduleBeans = eventScheduleBeans;
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
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public Integer getGuideId() {
		return guideId;
	}
	public void setGuideId(Integer guideId) {
		this.guideId = guideId;
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
	@Override
	public String toString() {
		return "EventScheduleBean [scheduleId=" + scheduleId + ", eventOrgId="
				+ eventOrgId + ", eventId=" + eventId + ", date=" + date
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", waitingCapacity=" + waitingCapacity + ", maxCapacity="
				+ maxCapacity + ", isGuideAvialble=" + isGuideAvialble
				+ ", duration=" + duration + ", availableSeats="
				+ availableSeats + ", colorCode=" + colorCode + ", evntDt="
				+ evntDt + ", evntStrtTme=" + evntStrtTme + ", evntEndTme="
				+ evntEndTme + ", divCount=" + divCount + ", eventName="
				+ eventName + ", guideName=" + guideName + ", guideId="
				+ guideId + ", userNotes=" + userNotes + ", isPaid=" + isPaid
				+ ", isArrived=" + isArrived + ", eventScheduleBeans="
				+ eventScheduleBeans + ", haveReservation=" + haveReservation
				+ ", refNumber=" + refNumber + ", userName=" + userName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", bookedSeats=" + bookedSeats + ", strtTme=" + strtTme
				+ ", endTme=" + endTme + ", pdfPath=" + pdfPath + ", haveNote="
				+ haveNote + ", havePaid=" + havePaid + "]";
	}
	
		
	

}
