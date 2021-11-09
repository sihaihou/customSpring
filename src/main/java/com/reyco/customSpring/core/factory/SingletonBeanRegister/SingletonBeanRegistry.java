package com.reyco.customSpring.core.factory.SingletonBeanRegister;

import com.reyco.customSpring.core.exception.BeansException;

/**
*@author reyco
*@date  2021年5月26日---下午6:03:53
*<pre>
*
*<pre> 
*/
public interface SingletonBeanRegistry {
	
	/**
	 * 注册singletonObject
	 * @param beanName
	 * @param singletonObject
	 */
	void registerSingleton(String beanName, Object singletonObject) throws BeansException ;
	/**
	 * 获取singletonObject
	 * @param beanName
	 * @return
	 */
	Object getSingleton(String beanName);
	/**
	 * 是否存在singletonObject
	 * @param beanName
	 * @return
	 */
	boolean containsSingleton(String beanName);
	/**
	 * 获取所有的singletonObject名称
	 * @return
	 */
	String[] getSingletonNames();
	/**
	 * 获取singletonObject数量
	 * @return
	 */
	int getSingletonCount();
	
	Object getSingletonMutex();
}
