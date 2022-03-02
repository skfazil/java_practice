package com.fazil.hb.dataaccess.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fazil.hb.dataaccess.HomeBrewDAO;
import com.fazil.hb.models.Root;

@Repository
public class HomeBrewDAOImpl implements HomeBrewDAO{
	
	private final String baseUrl = "https://formulae.brew.sh";
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public Root getMetaDataByFormulaName(String name) throws RestClientException {
		String formulaRelUrl = "/api/formula/";
		String url = baseUrl+formulaRelUrl+name+".json";
		
		ResponseEntity<Root> resEnt =  restTemplate.getForEntity(url, Root.class);
		System.out.println(resEnt.getBody());
		return resEnt.getBody();
	}

}
