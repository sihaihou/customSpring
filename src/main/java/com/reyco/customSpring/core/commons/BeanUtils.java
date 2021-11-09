package com.reyco.customSpring.core.commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.reyco.customSpring.core.exception.BeanInstantiationException;

/**
*@author reyco
*@date  2021年5月25日---上午11:18:03
*<pre>
*
*<pre> 
*/
public class BeanUtils {
	/**
	 * 缓存beanName
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> String getBeanName(Class<T> clazz) {
		String name = clazz.getSimpleName();
		String beanName = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toLowerCase());
		return beanName;
	}
	/**
	 * 
	 * @param ctor
	 * @param args
	 * @return
	 * @throws BeanInstantiationException
	 */
	public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeanInstantiationException {
		Assert.notNull(ctor, "Constructor must not be null");
		try {
			//开启权限
			ReflectionUtils.makeAccessible(ctor);
			//反射创建实例
			return ctor.newInstance(args);
		}catch (InstantiationException ex) {
			throw new BeanInstantiationException();
		}catch (IllegalAccessException ex) {
			throw new BeanInstantiationException();
		}catch (IllegalArgumentException ex) {
			throw new BeanInstantiationException();
		}catch (InvocationTargetException ex) {
			throw new BeanInstantiationException();
		}
	}
	
}
