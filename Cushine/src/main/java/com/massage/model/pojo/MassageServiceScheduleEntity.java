package com.massage.model.pojo;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "massageservice_schedule")
public class MassageServiceScheduleEntity {

	@Id
	@GeneratedValue
	private Integer scheduleId;
	private Date date;
	private Date startTime;
	private Date endTime;
	private  Integer massagePersonId;
	private String colorCode;
	private String duration;
	private Integer userId;
	private Integer massageProvId;
	
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
	
	
	
	
	
}
