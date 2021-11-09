package com.reyco.customSpring.core.factory;
/**
*@author reyco
*@date  2021年5月28日---上午10:51:32
*<pre>
*
*<pre> 
*/
public interface FactoryBean<T> {
	
	T getObject() throws Exception;
	
	Class<?> getObjectType();
	
	default boolean isSingleton() {
		return true;
	}
}
