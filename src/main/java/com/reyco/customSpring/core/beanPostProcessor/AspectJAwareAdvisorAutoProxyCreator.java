package com.reyco.customSpring.core.beanPostProcessor;

import com.reyco.customSpring.core.aop.ProxyCreator;
import com.reyco.customSpring.core.aware.BeanFactoryAware;
import com.reyco.customSpring.core.exception.BeansException;
import com.reyco.customSpring.core.factory.BeanFactory;
import com.reyco.customSpring.core.factory.DefaultListableBeanFactory;

/**
*@author reyco
*@date  2021年5月24日---下午5:21:07
*<pre>
*
*<pre> 
*/
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor,BeanFactoryAware{
	
	private BeanFactory beanFactory;
	
	public AspectJAwareAdvisorAutoProxyCreator() {
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return wrapIfNecessary(bean, beanName);
	}
	
	private Object wrapIfNecessary(Object bean, String beanName) {
		Object[] advisorInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName);
		if (advisorInterceptors != null) {
			bean = createProxy(bean);
		}
		return bean;
	}
	
	private Object[] getAdvicesAndAdvisorsForBean(Class<? extends Object> clazz, String beanName) {
		return new Object[] {};
	}
	
	private Object createProxy(Object bean) {
		if(beanFactory instanceof DefaultListableBeanFactory) {
			DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)beanFactory;
			Boolean proxyTargetClass = defaultListableBeanFactory.getProxyTargetClass();
			ProxyCreator proxyCreator = new ProxyCreator();
			proxyCreator.setProxyTargetClass(proxyTargetClass);
			return proxyCreator.createProxy(bean.getClass());
		}
		return bean;
	}
}
