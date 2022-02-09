package com.cushina.scheduler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.events.service.PickEventOrganizerService;

public class MyServletContextListener implements ServletContextListener {
	
	public static String realPath = null;
	public static Map<String,String> map = new LinkedHashMap<String, String>();
	
	@Autowired
	private PickEventOrganizerService pickEventService;

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Servlet context is initialized");
		ServletContext servletContext = sce.getServletContext();
		realPath = servletContext.getRealPath("/");
		System.out.println("Printing realPath : "+realPath);
		
		//Loop for getting the Image context path......
		String clock = servletContext.getRealPath("/images/clock.png");
		String flag = servletContext.getRealPath("/images/flag.png");
		String hash = servletContext.getRealPath("/images/hash.png");
		String mailuser = servletContext.getRealPath("/images/mailuser.png");
		String woofre = servletContext.getRealPath("/images/woofre.png");
		
		map.put("clock", clock);
		map.put("flag", flag);
		map.put("hash", hash);
		map.put("mailuser", mailuser);
		map.put("woofre", woofre);
		System.out.println("image map in listenere class"+map);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Servlet context is destroyed");		
	}
	
	public static String returnContextPath() {
		return realPath;
	}
	
	public static Map<String,String> returnMapForImgContext() {
		return map;
	}

}
