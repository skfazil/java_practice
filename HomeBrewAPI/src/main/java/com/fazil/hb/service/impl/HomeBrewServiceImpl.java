package com.fazil.hb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.fazil.hb.dataaccess.HomeBrewDAO;
import com.fazil.hb.models.HBResponseBean;
import com.fazil.hb.models.Root;
import com.fazil.hb.models.Versions;
import com.fazil.hb.service.HomeBrewService;

@Service
public class HomeBrewServiceImpl implements HomeBrewService {

	@Autowired
	HomeBrewDAO homeBrewDao;

	@Override
	public HBResponseBean getMetaDataByFormulaName(String name) throws RestClientException {
		Root root = homeBrewDao.getMetaDataByFormulaName(name);

		HBResponseBean respBean = new HBResponseBean();

		respBean.setDependencies(root.getDependencies());
		//To avoid NPE
		respBean.setVersion(root.getVersions().map(Versions::getStable).orElse("0.0.0"));
		respBean.setDescription(root.getDesc());
		respBean.setGenerated_date(root.getGenerated_date());

		System.out.println(respBean);
		return respBean;
	}

}
