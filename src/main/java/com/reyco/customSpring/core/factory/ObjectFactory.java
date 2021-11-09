package com.reyco.customSpring.core.factory;

import com.reyco.customSpring.core.exception.BeansException;

/**
*@author reyco
*@date  2021年5月27日---上午10:23:01
*<pre>
*
*<pre> 
*/
public interface ObjectFactory<T> {
	
	T getObject() throws BeansException ;
	
}
