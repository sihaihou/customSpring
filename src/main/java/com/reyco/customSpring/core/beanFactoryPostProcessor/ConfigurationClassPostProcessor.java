package com.reyco.customSpring.core.beanFactoryPostProcessor;

import com.reyco.customSpring.core.BeanDefinition;
import com.reyco.customSpring.core.BeanDefinitionRegistry;
import com.reyco.customSpring.core.factory.DefaultListableBeanFactory;
import com.reyco.customSpring.core.test.Test;

/**
*@author reyco
*@date  2021年5月24日---下午5:50:19
*<pre>
*
*<pre> 
*/
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor{

	@Override
	public void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) throws Exception {
		String[] componentScans = beanFactory.getComponentScans();
		for (String componentScan : componentScans) {
			System.out.println("扫描：'"+componentScan+"'包下的所有包含component注解的类并解析成BeanDefinition注入到BeanDefinitionMap中");
		}
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
		postProcessBeanFactory(registry);
	}
	@Override
	public void postProcessBeanFactory(BeanDefinitionRegistry registry) throws Exception {
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanName("test");
		beanDefinition.setBeanClass(Test.class);
		beanDefinition.setBeanClassName(Test.class.getName());
		registry.registerBeanDefinition(beanDefinition.getBeanName(), beanDefinition);
	}

}
