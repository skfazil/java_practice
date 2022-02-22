package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exceptions.UserException;

@ControllerAdvice
public class GlobalExceptionController {
	
	@ExceptionHandler(value=UserException.class)
	public ResponseEntity<Object> exception(UserException ex){
		return new ResponseEntity<Object>("{\"reason\":\"CSAT Survey01\",\"classOfService\":\"CONSUMER\",\"product\":\"IP_DATA\",\"agent\":\"AC92516\",\"scheduledTime\":\"2021-08-04 13:12:17\",\"isFutureScheduledMessage\":\"N\",\"receiverName\":\"HIGGINS INDUSTRIAL\",\"timezone\":\"CDT\",\"sessionEndTime\":\"2021-08-04 01:08:53\",\"creationApplication\":\"RX\",\"marketType\":\"CRIS\",\"inputChannel\":\"API\",\"wtn\":\"719/630/5466\",\"senderId\":\"rxdsl\",\"receiverId\":\"719/630/5466\",\"templateName\":\"Repair Resolved Verification\",\"rxSessionId\":\"RX-168890010\"}", HttpStatus.NOT_FOUND);
	}

}
