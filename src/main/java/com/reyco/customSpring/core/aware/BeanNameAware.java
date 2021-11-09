package com.reyco.customSpring.core.aware;
/**
*@author reyco
*@date  2021年5月25日---上午9:28:32
*<pre>
*
*<pre> 
*/
public interface BeanNameAware extends Aware {
	
	void setBeanName(String beanName);
	
}
