package com.reyco.customSpring.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*@author reyco
*@date  2021年5月24日---下午9:25:36
*<pre>
*
*<pre> 
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentScan {
	
	String[] value() default "";
	
}
