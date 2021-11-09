package com.reyco.customSpring.core.factory;

import com.reyco.customSpring.core.EnvironmentCapable;

/**
*@author reyco
*@date  2021年5月25日---上午11:36:56
*<pre>
*
*<pre> 
*/
public interface ApplicationContext extends EnvironmentCapable,HierarchicalBeanFactory{
	
	String getId();
	
	String getApplicationName();

	ApplicationContext getParent();
}
