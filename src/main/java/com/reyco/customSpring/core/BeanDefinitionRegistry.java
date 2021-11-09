package com.reyco.customSpring.core;

/**
*@author reyco
*@date  2021年5月24日---下午5:07:29
*<pre>
*
*<pre> 
*/
public interface BeanDefinitionRegistry {
	
	void registerBeanDefinition(BeanDefinition beanDefinition) throws Exception;
	
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception;
	
	void removeBeanDefinition(String beanName) throws Exception;

	BeanDefinition getBeanDefinition(String beanName) throws Exception;

	boolean containsBeanDefinition(String beanName);

	String[] getBeanDefinitionNames();

	int getBeanDefinitionCount();
}
