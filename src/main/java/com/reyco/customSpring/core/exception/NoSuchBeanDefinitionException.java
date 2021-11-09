package com.reyco.customSpring.core.exception;
/**
*@author reyco
*@date  2021年5月28日---上午11:05:24
*<pre>
*
*<pre> 
*/
public class NoSuchBeanDefinitionException extends BeanDefinitionException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5900182617475896584L;

	public NoSuchBeanDefinitionException() {
		super();
	}
	public NoSuchBeanDefinitionException(String beanName) {
		this(beanName, "当前BeanDefinition为定义");
	}
	public NoSuchBeanDefinitionException(String beanName,String msg) {
		super(beanName,msg);
	}
}
