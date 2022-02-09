package com.cushina.model.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blacklist_users")
public class BlackListUsersEntity {

	@Id
	@GeneratedValue
	private Long blackListID;
	private Integer userId;
	private Long hotelID;
	private Date startDate;
	private Integer guestUserId;

	public Long getBlackListID() {
		return blackListID;
	}

	public void setBlackListID(Long blackListID) {
		this.blackListID = blackListID;
	}

	public Long getHotelID() {
		return hotelID;
	}

	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getGuestUserId() {
		return guestUserId;
	}

	public void setGuestUserId(Integer guestUserId) {
		this.guestUserId = guestUserId;
	}

}
