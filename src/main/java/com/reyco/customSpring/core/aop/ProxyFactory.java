package com.reyco.customSpring.core.aop;
/**
*@author reyco
*@date  2020年11月17日---下午3:51:01
*<pre>
*
*<pre> 
*/
public interface ProxyFactory {
	
	/**
	 * 获取代理对象
	 * @param serviceClass
	 * @return
	 */
	Object getProxy(Class<?> serviceClass);
	
}
