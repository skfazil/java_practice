package com.massage.model.pojo;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="massageperson")
public class MassagePersonEntity {

	@Id
	@GeneratedValue
	private Integer massagePersonId;
	private String massagePesronName;
	private String massagescheduledId;
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
	public String getMassagescheduledId() {
		return massagescheduledId;
	}
	public void setMassagescheduledId(String massagescheduledId) {
		this.massagescheduledId = massagescheduledId;
	}
	
	
	
	
	
}
