package com.reyco.customSpring.core.beanPostProcessor;

import com.reyco.customSpring.core.aware.ApplicationContextAware;
import com.reyco.customSpring.core.aware.Aware;
import com.reyco.customSpring.core.aware.EnvironmentAware;
import com.reyco.customSpring.core.exception.BeansException;
import com.reyco.customSpring.core.factory.ApplicationContext;

/**
*@author reyco
*@date  2021年5月25日---上午9:46:09
*<pre>
*
*<pre> 
*/
public class ApplicationContextAwareProcessor implements BeanPostProcessor{
	
	private ApplicationContext applicationContext;
	
	public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		//执行Aware接口
		invokeAwareInterfaces(bean);
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	public void invokeAwareInterfaces(Object bean) {
		if(bean instanceof Aware) {
			if(bean instanceof EnvironmentAware) {
				((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
			}
			if(bean instanceof ApplicationContextAware) {
				((ApplicationContextAware) bean).setApplicationContext(applicationContext);
			}
			//其它Aware接口...
		}
	}
}
