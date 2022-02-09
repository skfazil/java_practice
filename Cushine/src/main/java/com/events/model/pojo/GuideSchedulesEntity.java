package com.events.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Guide_Schedules")
public class GuideSchedulesEntity {
	@Id
	@GeneratedValue
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
