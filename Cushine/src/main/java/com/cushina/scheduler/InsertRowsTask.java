package com.cushina.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cushina.service.PickHotelService;

@Component
public class InsertRowsTask {

	private Logger logger = Logger.getLogger(InsertRowsTask.class);

	@Autowired
	private PickHotelService service;
	
	public void insertRows() {
		logger.info("insertRows : start");
		logger.info("insert rows automatically...");
		try{
			//boolean isInsert = service.isRowsInserted();
			logger.info("run me task :::");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		logger.info("insertRows : end");
	}
}
