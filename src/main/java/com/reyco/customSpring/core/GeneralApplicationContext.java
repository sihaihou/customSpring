package com.reyco.customSpring.core;

import com.reyco.customSpring.core.beanPostProcessor.BeanPostProcessor;
import com.reyco.customSpring.core.exception.BeanDefinitionException;
import com.reyco.customSpring.core.exception.BeansException;
import com.reyco.customSpring.core.exception.NoSuchBeanDefinitionException;
import com.reyco.customSpring.core.factory.AbstractApplicationContext;
import com.reyco.customSpring.core.factory.ApplicationContext;
import com.reyco.customSpring.core.factory.BeanFactory;
import com.reyco.customSpring.core.factory.DefaultListableBeanFactory;

/**
 * @author reyco
 * @date 2020年11月17日---下午3:41:17
 * 
 *       <pre>
 *
 *       <pre>
 */
public class GeneralApplicationContext extends AbstractApplicationContext{
	
	public GeneralApplicationContext(Class<?> clazz) throws Exception {
		this.beanFactory = new DefaultListableBeanFactory(clazz);
		this.beanFactory.setApplicationContext(this);
		this.setParent(this);
		refresh();
	}
	@Override
	public String getId() {
		return getId();
	}
	@Override
	public String getApplicationName() {
		return this.getClass().getName();
	}
	@Override
	public ApplicationContext getParent() {
		return this;
	}
	@Override
	public BeanFactory getParentBeanFactory() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setParentBeanFactory(BeanFactory parentBeanFactory) throws RuntimeException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getBeanPostProcessorCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void registerAlias(String beanName, String alias) throws BeanDefinitionException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isFactoryBean(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setCurrentlyInCreation(String beanName, boolean inCreation) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isCurrentlyInCreation(String beanName) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void destroySingletons() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}
	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(name, requiredType);
	}
	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return getBeanFactory().getBean(name, args);
	}
	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		// TODO Auto-generated method stub
		return getBeanFactory().getBean(requiredType);
	}
	@Override
	public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return getBeanFactory().getBean(requiredType, args);
	}
	@Override
	public <T> T getBean(String name, Class<T> requiredType, Object... args) {
		return getBeanFactory().getBean(name, requiredType, args);
	}
	@Override
	public boolean containsBean(String name) {
		// TODO Auto-generated method stub
		return getBeanFactory().containsBean(name);
	}
	@Override
	public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return getBeanFactory().isSingleton(name);
	}
	@Override
	public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return getBeanFactory().isTypeMatch(name, typeToMatch);
	}
	@Override
	public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return getBeanFactory().getType(name);
	}
	@Override
	public String[] getAliases(String name) {
		// TODO Auto-generated method stub
		return getBeanFactory().getAliases(name);
	}

}
