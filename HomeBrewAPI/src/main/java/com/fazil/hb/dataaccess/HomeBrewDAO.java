package com.fazil.hb.dataaccess;

import org.springframework.web.client.RestClientException;

import com.fazil.hb.models.Root;

public interface HomeBrewDAO {
	
	Root getMetaDataByFormulaName(String name) throws RestClientException;

}
