package com.events.common.dto;

import java.util.Date;

public class GuideSchedulesDTO {

	private Integer guideScheduleId;	
	private Date startTime;
	private Date endTime;
	private String duration;
	private Integer guideId;
	public Integer getGuideScheduleId() {
		return guideScheduleId;
	}
	public void setGuideScheduleId(Integer guideScheduleId) {
		this.guideScheduleId = guideScheduleId;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Integer getGuideId() {
		return guideId;
	}
	public void setGuideId(Integer guideId) {
		this.guideId = guideId;
	}
	
}
