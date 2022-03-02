package com.fazil.hb.service;

import org.springframework.web.client.RestClientException;

import com.fazil.hb.models.HBResponseBean;

public interface HomeBrewService {
	
	HBResponseBean getMetaDataByFormulaName(String name) throws RestClientException;

}
