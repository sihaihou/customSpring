package com.reyco.customSpring.core.factory;

import com.reyco.customSpring.core.exception.BeansException;
import com.reyco.customSpring.core.exception.NoSuchBeanDefinitionException;

/**
*@author reyco
*@date  2021年5月24日---下午8:17:31
*<pre>
*
*<pre> 
*/
public interface BeanFactory {
	
	String FACTORY_BEAN_PREFIX = "&";
	
	Object getBean(String name) throws BeansException;
	
	<T> T getBean(String name,Class<T> requiredType) throws BeansException;
	
	Object getBean(String name, Object... args) throws BeansException;
	
	<T> T getBean(Class<T> requiredType) throws BeansException;
	
	<T> T getBean(Class<T> requiredType, Object... args) throws BeansException;
	
	public <T> T getBean(String name, Class<T> requiredType, Object... args);
	
	BeanFactory getParentBeanFactory();
	
	boolean containsBean(String name);
	
	boolean isSingleton(String name) throws NoSuchBeanDefinitionException;
	
	boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;
	
	Class<?> getType(String name) throws NoSuchBeanDefinitionException;
	
	String[] getAliases(String name);
}
