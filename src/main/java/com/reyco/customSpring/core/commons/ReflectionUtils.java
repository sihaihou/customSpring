package com.reyco.customSpring.core.commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ReflectionUtils {
	
	/**
	 * 开启访问权限
	 * @param ctor
	 */
	public static void makeAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) ||
				!Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}
	
}
