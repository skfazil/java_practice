package com.events.model.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="event_organizer")
public class EventOrganizerEntity {
	
	@Id
	@GeneratedValue
	private Integer eventOrgId;
	private String eventOrgName;
	private String eventOrgAddress;
	private String eventOrgCity;
	private String eventOrgPostalCode;
	private String eventOrgEmail;
	private String eventOrgWebiste;
	private String eventOrgPhoneNumber;
	
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	public String getEventOrgName() {
		return eventOrgName;
	}
	public void setEventOrgName(String eventOrgName) {
		this.eventOrgName = eventOrgName;
	}
	public String getEventOrgAddress() {
		return eventOrgAddress;
	}
	public void setEventOrgAddress(String eventOrgAddress) {
		this.eventOrgAddress = eventOrgAddress;
	}
	public String getEventOrgCity() {
		return eventOrgCity;
	}
	public void setEventOrgCity(String eventOrgCity) {
		this.eventOrgCity = eventOrgCity;
	}
	public String getEventOrgPostalCode() {
		return eventOrgPostalCode;
	}
	public void setEventOrgPostalCode(String eventOrgPostalCode) {
		this.eventOrgPostalCode = eventOrgPostalCode;
	}
	public String getEventOrgEmail() {
		return eventOrgEmail;
	}
	public void setEventOrgEmail(String eventOrgEmail) {
		this.eventOrgEmail = eventOrgEmail;
	}
	public String getEventOrgWebiste() {
		return eventOrgWebiste;
	}
	public void setEventOrgWebiste(String eventOrgWebiste) {
		this.eventOrgWebiste = eventOrgWebiste;
	}
	public String getEventOrgPhoneNumber() {
		return eventOrgPhoneNumber;
	}
	public void setEventOrgPhoneNumber(String eventOrgPhoneNumber) {
		this.eventOrgPhoneNumber = eventOrgPhoneNumber;
	}
	
		
}
