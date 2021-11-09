package com.reyco.customSpring.core.exception;
/**
*@author reyco
*@date  2021年5月27日---上午10:23:21
*<pre>
*
*<pre> 
*/
public class BeansException extends RuntimeException{
	public BeansException() {
		super();
	}
	public BeansException(String BeanName) {
		super(BeanName+"：bean异常");
	}
	public BeansException(String BeanName,String msg) {
		super(BeanName+":"+msg);
	}
}
