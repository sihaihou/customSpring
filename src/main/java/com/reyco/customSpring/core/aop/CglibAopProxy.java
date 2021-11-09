package com.reyco.customSpring.core.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author reyco
 * @date 2020年11月17日---下午3:50:04
 *  <pre>
 *		cglib代理
 *  <pre>
 */
@SuppressWarnings("all")
public class CglibAopProxy implements ProxyFactory {

	@Override
	public Object getProxy(Class<?> serviceClass) {
		Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setCallback(new MethodInterceptor(){
    		@Override
    		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    			 System.out.println("before method run...");
                 Object result = proxy.invokeSuper(obj, args);
                 System.out.println("after method run...");
                 return result;
    		}
    	});
       return enhancer.create();
	}
}
