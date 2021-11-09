package com.reyco.customSpring.core.exception;
/**
*@author reyco
*@date  2021年5月28日---上午11:01:21
*<pre>
*
*<pre> 
*/
public class BeanDefinitionException extends RuntimeException{
	public BeanDefinitionException() {
		// TODO Auto-generated constructor stub
	}
	public BeanDefinitionException(String beanName) {
		this(beanName,"bean定义异常");
	}
	
	public BeanDefinitionException(String beanName,String msg) {
		super(beanName+":"+msg);
	}
}
