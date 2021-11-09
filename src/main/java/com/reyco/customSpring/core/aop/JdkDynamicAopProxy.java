package com.reyco.customSpring.core.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author reyco
 * @date 2020年11月17日---下午3:49:33
 *  <pre>
 *	jdk动态代理
 *  <pre>
 */
@SuppressWarnings("all")
public class JdkDynamicAopProxy implements ProxyFactory {
	
	@Override
	public Object getProxy(Class<?> serviceClass) {
		return Proxy.newProxyInstance(serviceClass.getClassLoader(),serviceClass.getInterfaces(),new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				 System.out.println("before method run...");
				 Object obj = method.invoke(serviceClass.newInstance(), args);
				 System.out.println("after method run...");
				 return obj;
			}
		});
	}
	
}
