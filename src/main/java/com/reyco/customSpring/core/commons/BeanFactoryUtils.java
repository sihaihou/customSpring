package com.reyco.customSpring.core.commons;

import com.reyco.customSpring.core.factory.BeanFactory;

/**
*@author reyco
*@date  2021年5月28日---下午1:41:47
*<pre>
*
*<pre> 
*/
public class BeanFactoryUtils {
	
	
	public static boolean isFactoryDereference(String name) {
		return (name != null && name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX));
	}
	
}
