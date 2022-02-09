package com.events.model.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="event_user")
public class EventUserEntity {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String isServiceProvider;
	private Integer userId;
	private Integer eventOrgId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIsServiceProvider() {
		return isServiceProvider;
	}
	public void setIsServiceProvider(String isServiceProvider) {
		this.isServiceProvider = isServiceProvider;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	
	public String toString() {
		return "EventUserEntity [id=" + id + ", isServiceProvider="
				+ isServiceProvider + ", userId=" + userId + ", eventOrgId="
				+ eventOrgId + "]";
	}
	
	
	
		
}
