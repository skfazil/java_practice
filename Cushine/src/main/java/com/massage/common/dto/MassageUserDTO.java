package com.massage.common.dto;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class MassageUserDTO {

	private Integer massageUserId;
	private Integer userId;
	private String isServiceProvider;
	private Integer massageProvId;
	public Integer getMassageUserId() {
		return massageUserId;
	}
	public void setMassageUserId(Integer massageUserId) {
		this.massageUserId = massageUserId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getIsServiceProvider() {
		return isServiceProvider;
	}
	public void setIsServiceProvider(String isServiceProvider) {
		this.isServiceProvider = isServiceProvider;
	}
	public Integer getMassageProvId() {
		return massageProvId;
	}
	public void setMassageProvId(Integer massageProvId) {
		this.massageProvId = massageProvId;
	}
	
	
}
