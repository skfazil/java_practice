package com.events.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="event_blacklist")
public class EventBlackListEntity {

	@Id
	@GeneratedValue
	private Integer blackListId;
	private Integer orgId;
	private Integer scheduleId;
	private Date addedDate;
	private Integer userId;
	private Integer guestId;
	public Integer getBlackListId() {
		return blackListId;
	}
	public void setBlackListId(Integer blackListId) {
		this.blackListId = blackListId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGuestId() {
		return guestId;
	}
	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}
	@Override
	public String toString() {
		return "EventBlackListEntity [blackListId=" + blackListId + ", orgId="
				+ orgId + ", scheduleId=" + scheduleId + ", addedDate="
				+ addedDate + ", userId=" + userId + ", guestId=" + guestId
				+ "]";
	}
	
	
}
