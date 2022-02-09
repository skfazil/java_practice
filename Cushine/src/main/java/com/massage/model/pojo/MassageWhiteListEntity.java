package com.massage.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "massage_whitelist")
public class MassageWhiteListEntity {

	@Id
	@GeneratedValue
	private Integer whiteListId;
	private Integer massageProvId;
	private Integer scheduleId;
	private Date addedDate;
	private Integer userId;
	private Integer guestId;

	public Integer getWhiteListId() {
		return whiteListId;
	}

	public void setWhiteListId(Integer whiteListId) {
		this.whiteListId = whiteListId;
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
