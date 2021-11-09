package com.reyco.customSpring.core.beanPostProcessor;

import com.reyco.customSpring.core.exception.BeansException;

/**
*@author reyco
*@date  2021年5月24日---下午5:15:11
*<pre>
*
*<pre> 
*/
public interface BeanPostProcessor {
	
	
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	
	default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	
}
