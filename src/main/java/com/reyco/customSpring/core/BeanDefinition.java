package com.reyco.customSpring.core;
/**
*@author reyco
*@date  2021年5月24日---下午4:15:47
*<pre>
*
*<pre> 
*/
public class BeanDefinition {
	 
	private String beanName;
	private String beanClassName;
	private Class<?> beanClass;
	private Boolean isSingleton = true;
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getBeanClassName() {
		return beanClassName;
	}
	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}
	public Class<?> getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	public Boolean isSingleton() {
		return isSingleton;
	}
	public void setIsSingleton(Boolean isSingleton) {
		this.isSingleton = isSingleton;
	}
}
