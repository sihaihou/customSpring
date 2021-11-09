package com.reyco.customSpring.core.factory;

import com.reyco.customSpring.core.beanPostProcessor.BeanPostProcessor;
import com.reyco.customSpring.core.exception.BeanDefinitionException;
import com.reyco.customSpring.core.exception.NoSuchBeanDefinitionException;

/**
*@author reyco
*@date  2021年5月27日---上午10:42:14
*<pre>
*
*<pre> 
*/
public interface HierarchicalBeanFactory extends BeanFactory{
	
	String SCOPE_SINGLETON = "singleton";
	
	BeanFactory getParentBeanFactory();
	
	void setParentBeanFactory(BeanFactory parentBeanFactory) throws RuntimeException;
	
	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
	
	int getBeanPostProcessorCount();
	
	void registerAlias(String beanName, String alias) throws BeanDefinitionException;
	
	boolean isFactoryBean(String name) throws NoSuchBeanDefinitionException;
	
	void setCurrentlyInCreation(String beanName, boolean inCreation);
	
	boolean isCurrentlyInCreation(String beanName);
	
	void destroySingletons();
}	
