package com.events.common.dto;

public class EventsDTO {
	private Integer eventId;
	private String eventName;
	private Integer eventOrgId;
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Integer getEventOrgId() {
		return eventOrgId;
	}
	public void setEventOrgId(Integer eventOrgId) {
		this.eventOrgId = eventOrgId;
	}
	
	public String toString() {
		return "EventsDTO [eventId=" + eventId + ", eventName=" + eventName
				+ ", eventOrgId=" + eventOrgId + "]";
	}
		
}
