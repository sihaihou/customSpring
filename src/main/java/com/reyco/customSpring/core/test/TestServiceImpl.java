package com.reyco.customSpring.core.test;

import com.reyco.customSpring.core.Component;
import com.reyco.customSpring.core.aware.ApplicationContextAware;
import com.reyco.customSpring.core.factory.ApplicationContext;

/**
*@author reyco
*@date  2020年11月17日---下午4:00:40
*<pre>
*
*<pre> 
*/
@Component
public class TestServiceImpl implements TestService,ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public Test get(String id) {
		System.out.println("执行目标方法：id="+id);
		System.out.println("applicationContext："+applicationContext);
		return new Test(id,"name"+id);
	}

}
