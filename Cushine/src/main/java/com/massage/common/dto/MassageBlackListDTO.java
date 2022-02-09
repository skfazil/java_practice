package com.massage.common.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


public class MassageBlackListDTO {


	private Integer blackListId;
	private Integer massageProvId;
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

}
