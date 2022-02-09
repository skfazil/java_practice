package com.cushina.web.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class permanentRreservationController {

	private Logger logger = Logger.getLogger(permanentRreservationController.class
			.getName());
	
	@RequestMapping(value = "permanentRreservation.htm", method = RequestMethod.GET)
	public String intiPermanentRreservation(Model model){
		logger.info("intiPermanentRreservation controller : start");
		logger.info("intiPermanentRreservation controller : end");
		return "permanent_reservation";
	}
}
