package com.cushina.common.dto;

public class RoomInfoDTO {

	private Long Id;
	private long roomId;
	private Long hotelID; 
	private Long categoryId;
	private String roomName;
	private Integer WhiteAndBlack;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public long getRoomId() {
		return roomId;
	}
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getWhiteAndBlack() {
		return WhiteAndBlack;
	}
	public void setWhiteAndBlack(Integer whiteAndBlack) {
		WhiteAndBlack = whiteAndBlack;
	}

	public String toString() {
		return "RoomInfoDTO [Id=" + Id + ", roomId=" + roomId + ", hotelID="
				+ hotelID + ", categoryId=" + categoryId + ", roomName="
				+ roomName + ", WhiteAndBlack=" + WhiteAndBlack + "]";
	}
	
}
