package com.events.common.dto;

import java.util.Date;

public class EventListDTO {

	private Integer scheduleId;
	private Integer eventId;
	private Date date;
	private String eventName;
	private String guideName;
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

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
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

	@Override
	public String toString() {
		return "EventList [scheduleId=" + scheduleId + ", eventId=" + eventId
				+ ", date=" + date + ", eventName=" + eventName
				+ ", guideName=" + guideName + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", waitingCapacity="
				+ waitingCapacity + ", maxCapacity=" + maxCapacity
				+ ", isGuideAvialble=" + isGuideAvialble + ", duration="
				+ duration + ", availableSeats=" + availableSeats
				+ ", colorCode=" + colorCode + ", evntDt=" + evntDt
				+ ", evntStrtTme=" + evntStrtTme + ", evntEndTme=" + evntEndTme
				+ ", divCount=" + divCount + "]";
	}

}
