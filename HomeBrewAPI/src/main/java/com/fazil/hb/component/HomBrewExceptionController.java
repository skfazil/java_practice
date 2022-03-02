package com.fazil.hb.component;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import com.fazil.hb.models.HomeBrewAPIException;

@ControllerAdvice
public class HomBrewExceptionController {

	@ExceptionHandler(RestClientException.class)
	public ResponseEntity<HomeBrewAPIException> onException(RestClientException ex) {

		HomeBrewAPIException hbException = new HomeBrewAPIException("404", ex.getMessage(), new Date().toString());

		return new ResponseEntity<HomeBrewAPIException>(hbException, HttpStatus.NOT_FOUND);

	}

}
