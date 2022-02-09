package com.cushina.web.bean;

import java.util.Date;

public class BookingHistoryBean{

	private Long bookingId;
	private Long reservationNumber;
	private Long oldReservtnNumber;
	private Long hotelID;
	private Date checkInDate;
	private Date checkOutDate;
	private Long numberOfDays;
	private String status;
	private Integer userId;
	private Integer roomNumber;
	private String category;
	private Boolean isSuccess;
	private String notes;
	private String emailShare;
	private boolean isArrived;
	private boolean isPaid;
	private Long roomId;
	private Long categoryId;
	private String chckedInDate;
	private String chckedOutDate;
	private Date bookingDate;
	private String bookeddate;
	private String hotelName;
	private String hotelAddress;
	private Long hotelPhneNumber; 
	private String city; 
	private String postalCode;
	private String checkInDt;
	private String checkOutDt;
	private String pdfPath;
	private Integer guestUserId;
	private String userName;
	private String email;
	private Long contactNumber;
	private String categoryName;
	private String arrivedVal;
	private String phoneNumber;
	private String chckedInDt; 
	private Integer servProId;
	private String fname;
	private String lname;
	private String bookingDt;
	
	
	public String getBookingDt() {
		return bookingDt;
	}
	public void setBookingDt(String bookingDt) {
		this.bookingDt = bookingDt;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public void setServProId(Integer servProId) {
		this.servProId = servProId;
	}
	public Integer getServProId() {
		return servProId;
	}
	
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public Long getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(Long reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public Long getHotelID() {
		return hotelID;
	}
	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public Long getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(Long numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getEmailShare() {
		return emailShare;
	}
	public void setEmailShare(String emailShare) {
		this.emailShare = emailShare;
	}
	public boolean isArrived() {
		return isArrived;
	}
	public void setArrived(boolean isArrived) {
		this.isArrived = isArrived;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getChckedInDate() {
		return chckedInDate;
	}
	public void setChckedInDate(String chckedInDate) {
		this.chckedInDate = chckedInDate;
	}
	public String getChckedOutDate() {
		return chckedOutDate;
	}
	public void setChckedOutDate(String chckedOutDate) {
		this.chckedOutDate = chckedOutDate;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getBookeddate() {
		return bookeddate;
	}
	public void setBookeddate(String bookeddate) {
		this.bookeddate = bookeddate;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCheckInDt() {
		return checkInDt;
	}
	public void setCheckInDt(String checkInDt) {
		this.checkInDt = checkInDt;
	}
	public String getCheckOutDt() {
		return checkOutDt;
	}
	public void setCheckOutDt(String checkOutDt) {
		this.checkOutDt = checkOutDt;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public Integer getGuestUserId() {
		return guestUserId;
	}
	public void setGuestUserId(Integer guestUserId) {
		this.guestUserId = guestUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getArrivedVal() {
		return arrivedVal;
	}
	public void setArrivedVal(String arrivedVal) {
		this.arrivedVal = arrivedVal;
	}
	
	public Long getHotelPhneNumber() {
		return hotelPhneNumber;
	}
	public void setHotelPhneNumber(Long hotelPhneNumber) {
		this.hotelPhneNumber = hotelPhneNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getChckedInDt() {
		return chckedInDt;
	}
	public void setChckedInDt(String chckedInDt) {
		this.chckedInDt = chckedInDt;
	}
	public Long getOldReservtnNumber() {
		return oldReservtnNumber;
	}
	public void setOldReservtnNumber(Long oldReservtnNumber) {
		this.oldReservtnNumber = oldReservtnNumber;
	}
	
	public String toString() {
		return "BookingHistoryBean [bookingId=" + bookingId
				+ ", reservationNumber=" + reservationNumber
				+ ", oldReservtnNumber=" + oldReservtnNumber + ", hotelID="
				+ hotelID + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", numberOfDays=" + numberOfDays + ", status="
				+ status + ", userId=" + userId + ", roomNumber=" + roomNumber
				+ ", category=" + category + ", isSuccess=" + isSuccess
				+ ", notes=" + notes + ", emailShare=" + emailShare
				+ ", isArrived=" + isArrived + ", isPaid=" + isPaid
				+ ", roomId=" + roomId + ", categoryId=" + categoryId
				+ ", chckedInDate=" + chckedInDate + ", chckedOutDate="
				+ chckedOutDate + ", bookingDate=" + bookingDate
				+ ", bookeddate=" + bookeddate + ", hotelName=" + hotelName
				+ ", hotelAddress=" + hotelAddress + ", hotelPhneNumber="
				+ hotelPhneNumber + ", city=" + city + ", postalCode="
				+ postalCode + ", checkInDt=" + checkInDt + ", checkOutDt="
				+ checkOutDt + ", pdfPath=" + pdfPath + ", guestUserId="
				+ guestUserId + ", userName=" + userName + ", email=" + email
				+ ", contactNumber=" + contactNumber + ", categoryName="
				+ categoryName + ", arrivedVal=" + arrivedVal
				+ ", phoneNumber=" + phoneNumber + ", chckedInDt=" + chckedInDt
				+ ", servProId=" + servProId + ", fname=" + fname + ", lname="
				+ lname + ", bookingDt=" + bookingDt + "]";
	}
	
}
