package com.reyco.customSpring.core.aware;

import com.reyco.customSpring.core.factory.BeanFactory;

/**
*@author reyco
*@date  2021年5月25日---上午9:31:59
*<pre>
*
*<pre> 
*/
public interface BeanFactoryAware extends Aware{
	
	void setBeanFactory(BeanFactory beanFactory);
	
}
