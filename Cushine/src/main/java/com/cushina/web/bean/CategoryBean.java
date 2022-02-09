package com.cushina.web.bean;


public class CategoryBean {
	
	private Long catId;
	private Long categoryId;
	private Long hotelID;
	private String categoryName;
	public Long getCatId() {
		return catId;
	}
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String toString() {
		return "CategoryBean [catId=" + catId + ", categoryId=" + categoryId
				+ ", hotelID=" + hotelID + ", categoryName=" + categoryName
				+ "]";
	}
	
}
