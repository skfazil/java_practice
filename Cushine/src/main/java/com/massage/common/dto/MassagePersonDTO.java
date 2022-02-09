package com.massage.common.dto;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class MassagePersonDTO{


	private Integer massagePersonId;
	private String massagePesronName;
	private String massageProvId;
	public Integer getMassagePersonId() {
		return massagePersonId;
	}
	public void setMassagePersonId(Integer massagePersonId) {
		this.massagePersonId = massagePersonId;
	}
	public String getMassagePesronName() {
		return massagePesronName;
	}
	public void setMassagePesronName(String massagePesronName) {
		this.massagePesronName = massagePesronName;
	}
	public String getMassageProvId() {
		return massageProvId;
	}
	public void setMassageProvId(String massageProvId) {
		this.massageProvId = massageProvId;
	}
	
	
	
	
	
}
