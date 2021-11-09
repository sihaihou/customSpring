package com.reyco.customSpring.core.beanFactoryPostProcessor;

import com.reyco.customSpring.core.factory.DefaultListableBeanFactory;

/**
*@author reyco
*@date  2021年5月24日---下午4:55:06
*<pre>
*
*<pre> 
*/
public interface BeanFactoryPostProcessor {
	
	void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) throws Exception;
	
}
