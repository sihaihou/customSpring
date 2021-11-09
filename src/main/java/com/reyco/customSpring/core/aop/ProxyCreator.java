package com.reyco.customSpring.core.aop;

import java.io.Serializable;

/**
*@author reyco
*@date  2020年11月17日---下午3:50:38
*<pre>
*
*<pre> 
*/
public class ProxyCreator{
	/**
	 * 是否开启cglib代理：默认不开启
	 * 	proxyTargetClass=false：1,有接口时使用jdk动态代理；没有接口使用cglib代理
	 *  proxyTargetClass=true：1,使用cglib代理
	 */
	private Boolean proxyTargetClass = false;
	
	public Object createProxy(Class<?> clazz) {
		if(proxyTargetClass) {
			CglibAopProxy cglibAopProxy = new CglibAopProxy();
			return cglibAopProxy.getProxy(clazz);
		}
		if(clazz.isInterface() || (clazz.getInterfaces()!=null && clazz.getInterfaces().length>1) || (clazz.getInterfaces().length==1 && !clazz.getInterfaces()[0].equals(Serializable.class))) {
			JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy();
			return jdkDynamicAopProxy.getProxy(clazz);
		}else {
			CglibAopProxy cglibAopProxy = new CglibAopProxy();
			return cglibAopProxy.getProxy(clazz);
		}
	}
	
	public void setProxyTargetClass(Boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}
}
