package com.events.model.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="events_guide")
public class EventsGuideEntity {
	
	@Id
	@GeneratedValue
	private Integer guideId;	
	private Integer eventOrgId;	
	private Integer eventId;
	private Integer whiteAndBlack;
	private String guideName;
	public Integer getGuideId() {
		return guideId;
	}
	public void setGuideId(Integer guideId) {
		this.guideId = guideId;
	}
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Integer getWhiteAndBlack() {
		return whiteAndBlack;
	}
	public void setWhiteAndBlack(Integer whiteAndBlack) {
		this.whiteAndBlack = whiteAndBlack;
	}
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	
	
		
}
