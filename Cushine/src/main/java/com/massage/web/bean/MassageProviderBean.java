package com.massage.web.bean;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class MassageProviderBean {

	private Integer massageProvId;
	private String massageProvName;
	private String massageProvAddress;
	private String massageProvCity;
	private String massageProvPostalCode;
	private String massageProvEmail;
	private String massageProvWebiste;
	private String massageProvPhoneNumber;
	public Integer getMassageProvId() {
		return massageProvId;
	}
	public void setMassageProvId(Integer massageProvId) {
		this.massageProvId = massageProvId;
	}
	public String getMassageProvName() {
		return massageProvName;
	}
	public void setMassageProvName(String massageProvName) {
		this.massageProvName = massageProvName;
	}
	public String getMassageProvAddress() {
		return massageProvAddress;
	}
	public void setMassageProvAddress(String massageProvAddress) {
		this.massageProvAddress = massageProvAddress;
	}
	public String getMassageProvCity() {
		return massageProvCity;
	}
	public void setMassageProvCity(String massageProvCity) {
		this.massageProvCity = massageProvCity;
	}
	public String getMassageProvPostalCode() {
		return massageProvPostalCode;
	}
	public void setMassageProvPostalCode(String massageProvPostalCode) {
		this.massageProvPostalCode = massageProvPostalCode;
	}
	public String getMassageProvEmail() {
		return massageProvEmail;
	}
	public void setMassageProvEmail(String massageProvEmail) {
		this.massageProvEmail = massageProvEmail;
	}
	public String getMassageProvWebiste() {
		return massageProvWebiste;
	}
	public void setMassageProvWebiste(String massageProvWebiste) {
		this.massageProvWebiste = massageProvWebiste;
	}
	public String getMassageProvPhoneNumber() {
		return massageProvPhoneNumber;
	}
	public void setMassageProvPhoneNumber(String massageProvPhoneNumber) {
		this.massageProvPhoneNumber = massageProvPhoneNumber;
	}
	
	
	
	
}
