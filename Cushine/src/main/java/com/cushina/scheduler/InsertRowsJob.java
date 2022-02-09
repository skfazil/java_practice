package com.cushina.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class InsertRowsJob extends QuartzJobBean {

	private Logger logger = Logger.getLogger(InsertRowsJob.class);
	
	private InsertRowsTask insertRowsTask;

	public void setRunMeTask(InsertRowsTask insertRowsTask) {
		this.insertRowsTask = insertRowsTask;
	}

	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.info("executeInternal : start");
		insertRowsTask.insertRows();
		logger.info("executeInternal : end");
	}
}
