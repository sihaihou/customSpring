package com.reyco.customSpring.core.exception;
/**
*@author reyco
*@date  2021年5月27日---上午9:35:04
*<pre>
*
*<pre> 
*/
public class BeanCurrentlyInCreationException extends BeansException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7128227460760911857L;

	public BeanCurrentlyInCreationException(String beanName) {
		super(beanName+"Requested bean is currently in creation: Is there an unresolvable circular reference?");
	}
	
	public BeanCurrentlyInCreationException(String beanName,String msg) {
		this(beanName+":"+msg);
	}
	
}
