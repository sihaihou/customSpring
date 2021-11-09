package com.reyco.customSpring.core.exception;
/**
*@author reyco
*@date  2021年5月27日---上午11:36:45
*<pre>
*
*<pre> 
*/
public class BeanCreationException extends BeansException {
	public BeanCreationException() {
		super();
	}
	public BeanCreationException(String beanName) {
		this(beanName, "bean创建异常");
	}
	public BeanCreationException(String beanName,String msg) {
		super(beanName,msg);
	}
}
