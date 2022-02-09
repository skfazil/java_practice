package com.events.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events_schedule")
public class EventScheduleEntity {

	@Id
	@GeneratedValue
	private Integer scheduleId;
	private Integer eventId;
	private Integer eventOrgId;
	private Date date;
	private Date startTime;
	private Date endTime;
	private Long waitingCapacity;
	private Long maxCapacity;
	private String isGuideAvialble;
	private String duration;
	private Long availableSeats;
	private String colorCode;
	private Integer guideId;
	
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

	public Integer getEventOrgId() {
		return eventOrgId;
	}

	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}

	public Integer getGuideId() {
		return guideId;
	}

	public void setGuideId(Integer guideId) {
		this.guideId = guideId;
	}
	

}
