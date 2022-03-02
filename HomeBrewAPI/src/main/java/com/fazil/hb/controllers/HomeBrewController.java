package com.fazil.hb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fazil.hb.models.HBResponseBean;
import com.fazil.hb.service.HomeBrewService;

@RestController
public class HomeBrewController {

	@Autowired
	HomeBrewService homeBrewService;

	@GetMapping("/formula")
	public ResponseEntity<HBResponseBean> getMetaDataByFormulaName(@RequestParam String name) {
		HBResponseBean hbResponse = homeBrewService.getMetaDataByFormulaName(name);

		return new ResponseEntity<HBResponseBean>(hbResponse, HttpStatus.OK);
	}

}
