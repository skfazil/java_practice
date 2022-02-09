package com.events.common.dto;


public class EventsGuideDTO {
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
	@Override
	public String toString() {
		return "EventsGuideDTO [guideId=" + guideId + ", eventOrgId="
				+ eventOrgId + ", eventId=" + eventId + ", whiteAndBlack="
				+ whiteAndBlack + ", guideName=" + guideName + "]";
	}
	
	
	
}
