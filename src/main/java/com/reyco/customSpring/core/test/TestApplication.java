package com.reyco.customSpring.core.test;

import com.reyco.customSpring.core.GeneralApplicationContext;
import com.reyco.customSpring.core.factory.ApplicationContext;

/**
*@author reyco
*@date  2021年5月24日---下午8:59:08
*<pre>
*
*<pre> 
*/
public class TestApplication {
	
	public static void main(String[] args) throws Exception {
		
		ApplicationContext applicationContent = new GeneralApplicationContext(AppConfig.class);
		TestService testService = applicationContent.getBean(TestServiceImpl.class);
		System.out.println("testService:"+testService.get("1"));
		
	}
	
}
