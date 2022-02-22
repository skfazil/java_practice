package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.service.EntryService;

@Service
public class EntryServiceImpl implements EntryService{

	@Override
	public String getName() {
		return "Shaik Faziluddin";
	}
	
	

}
