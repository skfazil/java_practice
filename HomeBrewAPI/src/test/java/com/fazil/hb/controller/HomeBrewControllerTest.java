
package com.fazil.hb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazil.hb.models.HBResponseBean;
import com.fazil.hb.models.HomeBrewAPIException;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class HomeBrewControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Test
	public void testGetMetaDataByFormulaForSuccess() throws Exception {

		HBResponseBean resp = mapper.readValue(mockMvc.perform(get("/formula").param("name", "wget"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), HBResponseBean.class);

		assertTrue(resp.getDescription().equalsIgnoreCase("Internet file retriever"));
		assertTrue(resp.getVersion().equalsIgnoreCase("1.21.3"));
		assertTrue(resp.getGenerated_date().equalsIgnoreCase("2022-03-01"));
	}

	@Test
	public void testGetMetaDataByFormulaForNotFound() throws Exception {

		HomeBrewAPIException resp = mapper.readValue(mockMvc.perform(get("/formula").param("name", "jhgjfghjhjhjh"))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString(),
				HomeBrewAPIException.class);

		assertTrue(resp.getStatus().equals("404"));
	}
}
