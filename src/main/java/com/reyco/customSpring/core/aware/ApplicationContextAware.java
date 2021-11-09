package com.reyco.customSpring.core.aware;

import com.reyco.customSpring.core.factory.ApplicationContext;

/**
*@author reyco
*@date  2021年5月24日---下午10:07:18
*<pre>
*
*<pre> 
*/
public interface ApplicationContextAware extends Aware{
	
	void setApplicationContext(ApplicationContext applicationContext);
	
}
