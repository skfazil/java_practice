package com.cushina.model.dataaccess.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cushina.common.dto.BookingHistoryDTO;
import com.cushina.common.dto.HotelDTO;
import com.cushina.model.dataaccess.EmailVisibleDAO;
import com.cushina.model.pojo.BookingHistoryEntity;
import com.cushina.model.pojo.HotelEntity;
import com.cushina.web.bean.HotelBean;

@Repository
public class EmailVisibleDaoImpl implements EmailVisibleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<HotelBean> getEmailListDetails(Long hotelID, Integer userId) {

		Session session = sessionFactory.getCurrentSession();
		String query = "from BookingHistoryEntity " + "where userId=" + userId
				+ "" + "and emailShare='Y' and status='active'";
		List<BookingHistoryEntity> list = session.createQuery(query).list();
		List<HotelBean> dtoList = new ArrayList<HotelBean>();
		DateFormat destFrmt = new SimpleDateFormat("dd/MM/yyyy");
		HotelBean hotelBean = null;
		for (BookingHistoryEntity bookingHistoryEntity : list) {
			hotelBean = new HotelBean();
			Long hotelID2 = bookingHistoryEntity.getHotelID();
			Date bookingDate = bookingHistoryEntity.getBookingDate();
			String strtDate = destFrmt.format(bookingDate);
			HotelDTO hotelInfo = getHotelInfo(hotelID2);
			BeanUtils.copyProperties(hotelInfo, hotelBean);
			hotelBean.setStrtDate(strtDate);
			dtoList.add(hotelBean);
		}
		return dtoList;
	}

	public void getUpdateEmail(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		String query = "update BookingHistoryEntity set emailShare='N',status='inActive' where userId=:userId";
		session.createQuery(query).setInteger("userId", userId).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	public HotelDTO getHotelInfo(Long hotelID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(HotelEntity.class);
		criteria.add(Restrictions.eq("hotelID", hotelID));
		List<HotelEntity> hotelList = criteria.list();
		HotelDTO hoteldto = null;
		for (HotelEntity hotelEntity : hotelList) {
			hoteldto = new HotelDTO();
			BeanUtils.copyProperties(hotelEntity, hoteldto);
		}
		return hoteldto;
	}

	@SuppressWarnings("unchecked")
	public List<BookingHistoryDTO> getServiceEmailListDetails(Long hotelID) {
		List<BookingHistoryDTO> dtoList = new ArrayList<BookingHistoryDTO>();
		Session session = sessionFactory.getCurrentSession();
		String query = "from BookingHistoryEntity  " + "where hotelID="
				+ hotelID + "and emailShare='Y' and status='active'";
		System.out.println("query should be executed" + query);

		List<BookingHistoryEntity> list = session.createQuery(query).list();

		for (BookingHistoryEntity bookingHistoryEntity : list) {
			BookingHistoryDTO emailListDto = new BookingHistoryDTO();
			BeanUtils.copyProperties(bookingHistoryEntity, emailListDto);
			dtoList.add(emailListDto);
		}
		return dtoList;
	}

}
