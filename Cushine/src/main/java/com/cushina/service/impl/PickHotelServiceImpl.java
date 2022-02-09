package com.cushina.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cushina.common.dto.AvailabilityByDateDTO;
import com.cushina.common.dto.CategoryDTO;
import com.cushina.common.dto.HotelAvailabilityDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.common.dto.RoomInfoDTO;
import com.cushina.common.dto.UserDTO;
import com.cushina.model.dataaccess.PickHotelDao;
import com.cushina.model.dataaccess.UserDao;
import com.cushina.model.dataaccess.WhiteListUserDao;
import com.cushina.service.PickHotelService;
import com.cushina.web.bean.HotelBean;

@Service
public class PickHotelServiceImpl implements PickHotelService {

	private Logger logger = Logger.getLogger(PickHotelServiceImpl.class
			.getName());

	@Autowired
	private PickHotelDao dao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WhiteListUserDao whiteListUserDao;

	@Transactional
	public List<HotelDTO> getHotelDetails(String hotelName) {
		return dao.getHotelDetails(hotelName);
	}

	@Transactional
	public List<HotelAvailabilityDTO> getDatesList(Long hotelId) {
		return dao.getDatesList(hotelId);
	}

	@Transactional
	public List<HotelAvailabilityDTO> getDatesListBsdOnCatgry(Long hotelID,
			Long categoryID) {
		return dao.getDatesListBsdOnCatgry(hotelID, categoryID);
	}

	@Transactional
	public List<HotelBean> getHotelDetail(Long hotelId) {
		return dao.getHotelDetail(hotelId);
	}

	@Transactional
	public List<RoomInfoDTO> getRoomInfoByHotel(Long hotelID) {
		return dao.getRoomInfoByHotel(hotelID);
	}

	@Transactional
	public List<AvailabilityByDateDTO> getNextRoomAvailInfo(Long lastRecordId) {
		return dao.getNextRoomAvailInfo(lastRecordId);
	}

	@Transactional
	public List<CategoryDTO> getCategoryList(Long hotelID) {
		return dao.getCategoryList(hotelID);
	}

	@Transactional
	public List<Long> getRoomsList(Long hotelID, Long categoryID,
			String userName) {
		logger.info("getRoomsList serviceImpl : start");
		List<Long> roomsList = null;
		if (userName != null) {
			UserDTO userDetail = userDao.getUserDetail(userName);
			Integer userId = userDetail.getUserId();
			boolean isWhitListUser = whiteListUserDao.isWhitListUser(userId);
			if (isWhitListUser) {
				roomsList = dao.getWhiteRoomsList(hotelID, categoryID);
				if (roomsList.size() == 0) {
					roomsList = dao.getRoomsList(hotelID, categoryID);
				}
			} else {
				roomsList = dao.getRoomsList(hotelID, categoryID);
			}
		} else {
			roomsList = dao.getRoomsList(hotelID, categoryID);
		}
		logger.info("getRoomsList serviceImpl : end");
		return roomsList;
	}

	@Transactional
	public List<Long> getRoomsAvailList(Long hotelID, Long categoryID) {
		return dao.getRoomsAvailList(hotelID, categoryID);
	}

	@Transactional
	public List<AvailabilityByDateDTO> getRoomAvailInfo(Long hotelId,
			Integer noOfDays, String userName) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currDt = new Date();
		String currentDt = format.format(currDt);
		logger.info("fomatted date is :" + currentDt);

		List<AvailabilityByDateDTO> roomAvailInfo = null;
		List<AvailabilityByDateDTO> roomsAvailList = null;

		roomAvailInfo = dao.getRoomAvailInfo(hotelId);
		roomsAvailList = getRoomsAvailList(roomAvailInfo, noOfDays, currentDt);

		logger.info("current date list :" + roomsAvailList);
		return roomsAvailList;
	}

	private List<AvailabilityByDateDTO> getRoomsAvailList(
			List<AvailabilityByDateDTO> roomAvailInfo, Integer noOfDays,
			String currentDt) {
		logger.info("getRoomsAvailList method : start");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();
		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		if (noOfDays == null) {
			noOfDays = 0;
		}
		if (noOfDays != 0) {
			logger.info("noOfDays not equal to zero");
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {

				if (currentDt.equals(format.format(availabilityByDateDTO
						.getDate()))
						&& availabilityByDateDTO.getAvailcnt() >= noOfDays) {
					cuurDtAvailList.add(availabilityByDateDTO);

				} else if (currentDt.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		} else {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {

				if (currentDt.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		}
		logger.info("getRoomsAvailList method : end");
		return cuurDtAvailList;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getSelectedDateAvailRooms(
			String slectedDate, Long hotelID, String userName) {
		logger.info("getSelectedDateAvailRooms serviceImpl : start");// 22/09/2015
		List<AvailabilityByDateDTO> roomAvailInfo = null;
		List<AvailabilityByDateDTO> seleectedDateAvailableRoomsList = null;

		roomAvailInfo = dao.getSelectedDateAvailRooms(slectedDate, hotelID);
		seleectedDateAvailableRoomsList = getSelectedDateAvailableRoomsList(
				roomAvailInfo, slectedDate, hotelID);
		logger.info("---->" + roomAvailInfo);
		logger.info("getSelectedDateAvailRooms serviceImpl : end");
		return seleectedDateAvailableRoomsList;
	}

	private List<AvailabilityByDateDTO> getSelectedDateAvailableRoomsList(
			List<AvailabilityByDateDTO> roomAvailInfo, String slectedDate,
			Long hotelID) {
		logger.info("getSeleectedDateAvailableRoomsList method : start");

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();

		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {

			if (slectedDate.equals(format.format(availabilityByDateDTO
					.getDate())))
				cuurDtAvailList.add(availabilityByDateDTO);
		}
		logger.info("getSeleectedDateAvailableRoomsList method : end");
		return cuurDtAvailList;
	}

	@Transactional
	public Map<Long, String> getCategoryListByMap(Long hotelId) {
		return dao.getCategoryListByMap(hotelId);
	}

	@Transactional
	public List<AvailabilityByDateDTO> getRoomAvailByCategory(Long categoryId,
			Long hotelID, Date selectedDt, String userName) {
		logger.info("getRoomAvailByCategory serviceImpl : start");
		List<AvailabilityByDateDTO> roomAvailInfo = null;
		List<AvailabilityByDateDTO> roomAvailByCatgry = null;

		roomAvailInfo = dao.getRoomAvailByCategory(categoryId, hotelID,
				selectedDt);
		roomAvailByCatgry = getRoomAvailByCatgry(roomAvailInfo, categoryId,
				hotelID, selectedDt);
		logger.info("getRoomAvailByCategory serviceImpl : end");
		return roomAvailByCatgry;
	}

	private List<AvailabilityByDateDTO> getRoomAvailByCatgry(
			List<AvailabilityByDateDTO> roomAvailInfo, Long categoryId,
			Long hotelID, Date selectedDt) {
		logger.info("getRoomAvailByCatgry method : start");
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
		String currentDt = format.format(selectedDt);

		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();

		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
			if (currentDt
					.equals(format.format(availabilityByDateDTO.getDate())))
				cuurDtAvailList.add(availabilityByDateDTO);
		}
		logger.info("getRoomAvailByCatgry method : end");
		return cuurDtAvailList;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getSelectedDateAvailRoomsBasedOnCatgeory(
			String selectedDate, Long hotelID, Long categoryID,
			Integer dayCount, String userName) {
		logger.info("selected date format :" + selectedDate);// yyyy-mm-dd

		List<AvailabilityByDateDTO> roomAvailInfo = null;
		List<AvailabilityByDateDTO> selectedDateAvailRoomsOnCategory = null;

		roomAvailInfo = dao.getSelectedDateAvailRoomsBasedOnCatgeory(
				selectedDate, hotelID, categoryID, dayCount);
		selectedDateAvailRoomsOnCategory = getSelectedDateAvailRoomsOnCatgeory(
				roomAvailInfo, selectedDate, hotelID, categoryID, dayCount);

		logger.info("selected date aval rooms :"
				+ selectedDateAvailRoomsOnCategory);
		return selectedDateAvailRoomsOnCategory;
	}

	private List<AvailabilityByDateDTO> getSelectedDateAvailRoomsOnCatgeory(
			List<AvailabilityByDateDTO> roomAvailInfo, String selectedDate,
			Long hotelID, Long categoryID, Integer dayCount) {
		logger.info("getSelectedDateAvailRoomsOnCatgeory method : start");
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		if (dayCount == null)
			dayCount = 0;
		if (dayCount != 0) {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selectedDate.equals(format.format(availabilityByDateDTO
						.getDate()))
						&& availabilityByDateDTO.getAvailcnt() >= dayCount) {
					cuurDtAvailList.add(availabilityByDateDTO);
				} else if (selectedDate.equals(format
						.format(availabilityByDateDTO.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
			logger.info("day count  list :" + cuurDtAvailList);
		} else {
			logger.info("days list ::");
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selectedDate.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		}

		logger.info("getSelectedDateAvailRoomsOnCatgeory method : end");
		return cuurDtAvailList;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getLoggedInUserReservedDates(
			Long hotelID, Integer userId) {
		List<AvailabilityByDateDTO> loggedInUserReservedDates = dao
				.getLoggedInUserReservedDates(hotelID, userId);
		int size = loggedInUserReservedDates.size();
		for (int firstRecrd = 0; firstRecrd < size; firstRecrd++) {
			for (int nextRecrd = firstRecrd + 1; nextRecrd < size; nextRecrd++) {
				if (loggedInUserReservedDates
						.get(firstRecrd)
						.getDate()
						.equals(loggedInUserReservedDates.get(nextRecrd)
								.getDate())) {
					loggedInUserReservedDates.remove(nextRecrd);
					nextRecrd--;
					size--;
				}
			}
		}
		logger.info("sorted list dates :" + loggedInUserReservedDates);
		return loggedInUserReservedDates;
	}

	@Transactional
	public List<AvailabilityByDateDTO> getLoggedInUserReservedDatesOnCategoryType(
			Long hotelID, Integer userId, Long categoryID) {
		logger.info("getLoggedInUserReservedDatesOnCategoryType serviceImpl : start");
		List<AvailabilityByDateDTO> loggedInUserReservedDatesOnCategoryType = dao
				.getLoggedInUserReservedDatesOnCategoryType(hotelID, userId,
						categoryID);
		int size = loggedInUserReservedDatesOnCategoryType.size();
		for (int firstRecrd = 0; firstRecrd < size; firstRecrd++) {
			for (int nextRecrd = firstRecrd + 1; nextRecrd < size; nextRecrd++) {
				if (loggedInUserReservedDatesOnCategoryType
						.get(firstRecrd)
						.getDate()
						.equals(loggedInUserReservedDatesOnCategoryType.get(
								nextRecrd).getDate())) {
					loggedInUserReservedDatesOnCategoryType.remove(nextRecrd);
					nextRecrd--;
					size--;
				}
			}
		}
		logger.info("getLoggedInUserReservedDatesOnCategoryType serviceImpl : end");
		return loggedInUserReservedDatesOnCategoryType;
	}

	@Transactional
	public boolean isRowsInserted() {
		return dao.isRowsInserted();
	}

	@Transactional
	public List<HotelAvailabilityDTO> getDatesBadedOnNoOfDays(Long hotelID,
			Integer noOfDays, Long categoryID) {
		logger.info("getDatesBadedOnNoOfDays serviceImpl : start");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");// 22/09/2015

		List<HotelAvailabilityDTO> datesList = null;
		if (categoryID == 0) {
			datesList = dao.getDatesList(hotelID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = format.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);
				// calling getRoomInfo
				String roomInfo = getRoomInfo(selctedDate, hotelID, noOfDays);
				if (roomInfo == null) {
					logger.info("roomInfo is null:");
					roomInfo = "B";
				}
				hotelAvailabilityDTO.setStatus(roomInfo);
			}// dates list for each close.
		} else {
			datesList = dao.getDatesListBsdOnCatgry(hotelID, categoryID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = format.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);
				// calling getRoomInfo
				String roomInfo = getRoomInfoByCategry(selctedDate, hotelID,
						noOfDays, categoryID);

				if (roomInfo == null) {
					roomInfo = "B";
				}
				hotelAvailabilityDTO.setStatus(roomInfo);
			}
		}
		logger.info("getDatesBadedOnNoOfDays serviceImpl : end");
		return datesList;
	}

	private String getRoomInfoByCategry(String selctedDate, Long hotelID,
			Integer noOfDays, Long categoryID) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");// 22/09/2015
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();

		List<AvailabilityByDateDTO> roomAvailInfo = dao
				.getSelectedDateAvailRoomsBasedOnCatgeory(selctedDate, hotelID,
						categoryID, noOfDays);

		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		if (noOfDays == null)
			noOfDays = 0;
		if (noOfDays != 0) {
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selctedDate.equals(format.format(availabilityByDateDTO
						.getDate()))
						&& availabilityByDateDTO.getAvailcnt() >= noOfDays) {
					cuurDtAvailList.add(availabilityByDateDTO);
				} else if (selctedDate.equals(format
						.format(availabilityByDateDTO.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		} else {
			logger.info("days list ::");
			for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {
				if (selctedDate.equals(format.format(availabilityByDateDTO
						.getDate())))
					cuurDtAvailList.add(availabilityByDateDTO);
			}
		}

		String status = null;
		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtAvailList) {
			Integer availcnt = availabilityByDateDTO.getAvailcnt();
			if (availcnt >= noOfDays) {
				status = "A";
			}
		}
		return status;

	}

	private String getRoomInfo(String slectedDate, Long hotelID,
			Integer noOfDays) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");// 22/09/2015
		List<AvailabilityByDateDTO> cuurDtAvailList = new ArrayList<AvailabilityByDateDTO>();
		List<AvailabilityByDateDTO> roomAvailInfo = dao
				.getSelectedDateAvailRooms(slectedDate, hotelID);
		for (int strt = 0; strt < roomAvailInfo.size(); strt++) {
			Integer count = 1;
			int listSize = roomAvailInfo.size() - 1;
			if (strt == (roomAvailInfo.size() - 1))
				roomAvailInfo.get(strt).setAvailcnt(count);
			for (int next = strt + 1; next < roomAvailInfo.size(); next++) {
				if (roomAvailInfo.get(strt).getStatusCode()
						.equals(roomAvailInfo.get(next).getStatusCode())
						&& roomAvailInfo.get(strt).getRoomId()
								.equals(roomAvailInfo.get(next).getRoomId())) {
					++count;
					if (listSize == next)
						roomAvailInfo.get(strt).setAvailcnt(count);
				} else {
					roomAvailInfo.get(strt).setAvailcnt(count);
					break;
				}
			}
		}

		for (AvailabilityByDateDTO availabilityByDateDTO : roomAvailInfo) {

			if (slectedDate.equals(format.format(availabilityByDateDTO
					.getDate())))
				cuurDtAvailList.add(availabilityByDateDTO);
		}
		String status = null;

		for (AvailabilityByDateDTO availabilityByDateDTO : cuurDtAvailList) {
			Integer availcnt = availabilityByDateDTO.getAvailcnt();
			if (availcnt >= noOfDays) {
				status = "A";
			}
		}
		return status;
	}

	@SuppressWarnings("unused")
	@Transactional
	public List<HotelAvailabilityDTO> getDatesBadedOnNoOfDaysAndSelectedDate(
			String selectedDate, Long hotelID, Integer noOfDays, Long categoryID) {
		logger.info("getDatesBadedOnNoOfDays serviceImpl : start");
		// SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");//
		// 22/09/2015
		List<HotelAvailabilityDTO> datesList = null;
		SimpleDateFormat formt = new SimpleDateFormat("dd/MM/yyyy");

		if (categoryID == 0 && noOfDays == 0) {
			datesList = dao.getSearchDatesList(selectedDate, hotelID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = formt.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);
				Date selectedDt = null;
				Date currentDate = null;
				try {
					selectedDt = formt.parse(selectedDate);
					currentDate = formt.parse(selctedDate);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

		} else if (categoryID != 0 && noOfDays == 0) {
			datesList = dao.getSearchDatesListBsdOnCatgry(selectedDate,
					hotelID, categoryID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = formt.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);
				Date selectedDt = null;
				Date currentDate = null;
				try {
					selectedDt = formt.parse(selectedDate);
					currentDate = formt.parse(selctedDate);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		} else if (categoryID == 0 && noOfDays != 0) {
			datesList = dao.getSearchDatesList(selectedDate, hotelID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = formt.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);
				// calling getRoomInfo

				Date selectedDt = null;
				Date currentDate = null;
				try {
					selectedDt = formt.parse(selectedDate);
					currentDate = formt.parse(selctedDate);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if (currentDate.compareTo(selectedDt) >= 0) {
					String roomInfo = getRoomInfo(selctedDate, hotelID,
							noOfDays);
					if (roomInfo == null) {
						roomInfo = "B";
					}
					hotelAvailabilityDTO.setStatus(roomInfo);
				}
			}// dates list for each close.
		} else {
			datesList = dao.getSearchDatesListBsdOnCatgry(selectedDate,
					hotelID, categoryID);
			for (HotelAvailabilityDTO hotelAvailabilityDTO : datesList) {
				Date date = hotelAvailabilityDTO.getDate();
				String selctedDate = formt.format(date);
				hotelAvailabilityDTO.setSelecteDt(selctedDate);

				Date selectedDt = null;
				Date currentDate = null;
				try {
					selectedDt = formt.parse(selectedDate);
					currentDate = formt.parse(selctedDate);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if (currentDate.compareTo(selectedDt) >= 0) {// calling
																// getRoomInfo
					String roomInfo = getRoomInfoByCategry(selctedDate,
							hotelID, noOfDays, categoryID);
					if (roomInfo == null) {
						logger.info("roomInfo is null:");
						roomInfo = "B";
					}
					hotelAvailabilityDTO.setStatus(roomInfo);
				}
			}
		}
		logger.info("getDatesBadedOnNoOfDays serviceImpl : end");
		return datesList;
	}

}
