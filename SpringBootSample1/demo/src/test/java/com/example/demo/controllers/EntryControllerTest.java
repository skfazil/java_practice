package com.example.demo.controllers;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.service.EntryService;

@SpringBootTest
@ActiveProfiles("test")
public class EntryControllerTest {
	
	@Autowired
	EntryService eService;
	
	@Test
	public void testMockTest() {
		Assert<Assert<SELF,ACTUAL>, ACTUAL>
	}
}
