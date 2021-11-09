package com.reyco.customSpring.core.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.reyco.customSpring.core.BeanDefinition;
import com.reyco.customSpring.core.beanPostProcessor.BeanPostProcessor;
import com.reyco.customSpring.core.commons.Assert;
import com.reyco.customSpring.core.commons.BeanUtils;
import com.reyco.customSpring.core.commons.StringUtils;
import com.reyco.customSpring.core.exception.BeanCreationException;
import com.reyco.customSpring.core.exception.BeansException;
import com.reyco.customSpring.core.exception.NoSuchBeanDefinitionException;
import com.reyco.customSpring.core.factory.SingletonBeanRegister.FactoryBeanRegistrySupport;

/**
*@author reyco
*@date  2021年5月26日---下午5:48:51
*<pre>
*
*<pre> 
*/
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements HierarchicalBeanFactory {
	
	private BeanFactory parentBeanFactory;
	
	protected String[] componentScans;
	
	private Boolean proxyTargetClass = false;

	private Boolean exposeProxy = false;
	
	protected final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();
	
	protected final Map<String, Object> singlonObjects = new ConcurrentHashMap<>(128);
	
	protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(128);

	protected final List<String> beanDefinitionNames = new ArrayList<>();
	
	protected volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);
	/**
	 * bean
	 */
	private final ThreadLocal<Object> prototypesCurrentlyInCreation = new ThreadLocal<>();
	/**
	 * Create a new AbstractBeanFactory.
	 */
	public AbstractBeanFactory() {
	}
	public AbstractBeanFactory(BeanFactory parentBeanFactory) {
		this.parentBeanFactory = parentBeanFactory;
	}
	@Override
	public BeanFactory getParentBeanFactory() {
		return this.parentBeanFactory;
	}
	
	@Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null);
	}
	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return doGetBean(name, requiredType);
	}
	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return doGetBean(name, null);
	}
	@Override
	public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		return doGetBean(BeanUtils.getBeanName(requiredType), requiredType,args);
	}
	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		return doGetBean(BeanUtils.getBeanName(requiredType),requiredType);
	}
	@Override
	public <T> T getBean(String name, Class<T> requiredType,Object...args) throws BeansException {
		return doGetBean(name, requiredType,args);
	}
	protected <T> T doGetBean(final String name, Class<T> requiredType,Object...args) throws BeansException {
		final String beanName = transformedBeanName(name);
		Object bean = null;
		Object sharedInstance = getSingleton(beanName);
		if (sharedInstance != null) {
			bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
		}else {
			try {
				BeanDefinition mbd = getBeanDefinition(beanName);
				if (mbd.isSingleton()) {
					sharedInstance = getSingleton(beanName, () -> {
						try {
							return createBean(beanName, mbd, args);
						}
						catch (BeansException ex) {
							destroySingleton(beanName);
							throw ex;
						}
					});
					bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
				}
			}catch (BeansException ex) {
				throw ex;
			}
		}
		return (T) bean;
	}
	@Override
	public boolean containsBean(String name) {
		String beanName = transformedBeanName(name);
		if (containsSingleton(beanName) || containsBeanDefinition(beanName)) {
			return true;
		}
		return false;
	}
	public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		String beanName = transformedBeanName(name);
		Object beanInstance = getSingleton(beanName, false);
		if (beanInstance != null) {
			return !(name !=null && name.startsWith(FACTORY_BEAN_PREFIX));
		}
		BeanFactory parentBeanFactory = getParentBeanFactory();
		if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
			return parentBeanFactory.isSingleton(originalBeanName(name));
		}
		BeanDefinition mbd = getBeanDefinition(beanName);
		return mbd.isSingleton();
	}
	@Override
	public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
		String beanName = transformedBeanName(name);
		Object beanInstance = getSingleton(beanName, false);
		if (beanInstance != null && beanInstance.getClass() != null) {
			return typeToMatch.isInstance(beanInstance);
		}
		return false;
	}
	@Override
	public String[] getAliases(String name) {
		String beanName = transformedBeanName(name);
		List<String> aliases = new ArrayList<>();
		if (!beanName.equals(name)) {
			aliases.add(beanName);
		}
		String[] retrievedAliases = super.getAliases(beanName);
		for (String alias : retrievedAliases) {
			if (!alias.equals(name)) {
				aliases.add(alias);
			}
		}
		if (!containsSingleton(beanName) && !containsBeanDefinition(beanName)) {
			BeanFactory parentBeanFactory = getParentBeanFactory();
			if (parentBeanFactory != null) {
				aliases.addAll(Arrays.asList(parentBeanFactory.getAliases(beanName)));
			}
		}
		return StringUtils.toStringArray(aliases);
	}
	@Override
	public void setParentBeanFactory(BeanFactory parentBeanFactory) {
		if (this.parentBeanFactory != null && this.parentBeanFactory != parentBeanFactory) {
			throw new IllegalStateException("Already associated with parent BeanFactory: " + this.parentBeanFactory);
		}
		this.parentBeanFactory = parentBeanFactory;
	}
	
	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		Assert.notNull(beanPostProcessor, "BeanPostProcessor must not be null");
		this.beanPostProcessors.remove(beanPostProcessor);
		this.beanPostProcessors.add(beanPostProcessor);
	}
	@Override
	public int getBeanPostProcessorCount() {
		return this.beanPostProcessors.size();
	}
	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}
	@Override
	public boolean isFactoryBean(String name) {
		return false;
	}
	@Override
	protected void destroyBean(String beanName, Object beanInstance) {
		removeSingleton(beanName);
	}
	protected String transformedBeanName(String beanName) {
		return beanName;
	}
	
	protected String originalBeanName(String name) {
		String beanName = transformedBeanName(name);
		if (name.startsWith(FACTORY_BEAN_PREFIX)) {
			beanName = FACTORY_BEAN_PREFIX + beanName;
		}
		return beanName;
	}
	protected boolean isFactoryBean(String beanName, BeanDefinition mbd) {
		return false;
	}
	protected Class<?> getTypeForFactoryBean(String beanName, BeanDefinition mbd){
		if (!mbd.isSingleton()) {
			return null;
		}
		FactoryBean<?> factoryBean = doGetBean(FACTORY_BEAN_PREFIX + beanName,null);
		return getTypeForFactoryBean(factoryBean);
		
	}
	protected Object getObjectForBeanInstance(Object beanInstance, String name, String beanName,BeanDefinition mbd) {
		return beanInstance;
	}
	/**
	 * 
	 * @param beanName
	 * @return
	 */
	protected boolean isPrototypeCurrentlyInCreation(String beanName) {
		Object curVal = this.prototypesCurrentlyInCreation.get();
		return (curVal != null && curVal.equals(beanName));
	}
	
	public String[] getComponentScans() {
		return componentScans;
	}
	public void setComponentScans(String[] componentScans) {
		this.componentScans = componentScans;
	}
	public Boolean getProxyTargetClass() {
		return proxyTargetClass;
	}
	public void setProxyTargetClass(Boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}
	public Boolean getExposeProxy() {
		return exposeProxy;
	}
	public void setExposeProxy(Boolean exposeProxy) {
		this.exposeProxy = exposeProxy;
	}
	protected abstract boolean containsBeanDefinition(String beanName);
	
	protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
	
	protected abstract Object createBean(String beanName, BeanDefinition mbd,Object[] args)throws BeanCreationException;
}
