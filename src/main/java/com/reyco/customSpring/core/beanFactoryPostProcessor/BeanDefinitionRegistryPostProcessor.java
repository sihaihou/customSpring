package com.reyco.customSpring.core.beanFactoryPostProcessor;

import com.reyco.customSpring.core.BeanDefinitionRegistry;

/**
*@author reyco
*@date  2021年5月24日---下午5:05:47
*<pre>
*
*<pre> 
*/
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
	
	void postProcessBeanFactory(BeanDefinitionRegistry registry) throws Exception;
	
}
