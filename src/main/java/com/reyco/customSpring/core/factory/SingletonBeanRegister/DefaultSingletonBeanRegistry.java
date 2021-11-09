package com.reyco.customSpring.core.factory.SingletonBeanRegister;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.reyco.customSpring.core.commons.Assert;
import com.reyco.customSpring.core.commons.StringUtils;
import com.reyco.customSpring.core.exception.BeanCurrentlyInCreationException;
import com.reyco.customSpring.core.exception.BeansException;
import com.reyco.customSpring.core.factory.ObjectFactory;
import com.reyco.customSpring.core.factory.alias.SimpleAliasRegistry;

/**
*@author reyco
*@date  2021年5月26日---下午6:02:17
*<pre>
*
*<pre> 
*/
public abstract class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry{
	/**
	 * 一级缓存
	 */
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
	/**
	 * 二级缓存
	 */
	private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
	/**
	 * 三级缓存
	 */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
	/**
	 * 缓存bean是否注册
	 */
	private final Set<String> registeredSingletons = new LinkedHashSet<>(256);
	/**
	 * 缓存bean是否正在被创建
	 */
	private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));
	/**
	 * 排除正在被创建的bean
	 */
	private final Set<String> inCreationCheckExclusions = Collections.newSetFromMap(new ConcurrentHashMap<>(16));
	
	private boolean singletonsCurrentlyInDestruction = false;
	
	@Override
	public void registerSingleton(String beanName, Object singletonObject) throws BeansException {
		Assert.notNull(beanName, "Bean name must not be null");
		Assert.notNull(singletonObject, "Singleton object must not be null");
		synchronized (this.singletonObjects) {
			Object oldObject = this.singletonObjects.get(beanName);
			if (oldObject != null) {
				throw new BeansException(beanName+":已存在");
			}
			addSingleton(beanName, singletonObject);
		}
	}
	/**
	 * 添加bean
	 * @param beanName
	 * @param singletonObject
	 */
	protected void addSingleton(String beanName, Object singletonObject) {
		synchronized (this.singletonObjects) {
			this.singletonObjects.put(beanName, singletonObject);
			this.singletonFactories.remove(beanName);
			this.earlySingletonObjects.remove(beanName);
			this.registeredSingletons.add(beanName);
		}
	}
	protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
		Assert.notNull(singletonFactory, "Singleton factory must not be null");
		synchronized (this.singletonObjects) {
			if (!this.singletonObjects.containsKey(beanName)) {
				this.singletonFactories.put(beanName, singletonFactory);
				this.earlySingletonObjects.remove(beanName);
				this.registeredSingletons.add(beanName);
			}
		}
	}
	@Override
	public Object getSingleton(String beanName) {
		return getSingleton(beanName, true);
	}
	protected Object getSingleton(String beanName, boolean allowEarlyReference) {
		Object singletonObject = this.singletonObjects.get(beanName);
		if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {//一级缓存当前没有
			synchronized (this.singletonObjects) {
				singletonObject = this.earlySingletonObjects.get(beanName);
				if (singletonObject == null && allowEarlyReference) {  //二级缓存当前没有
					ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
					if (singletonFactory != null) {
						try {
							singletonObject = singletonFactory.getObject();
						} catch (BeansException e) {
							e.printStackTrace();
						}
						//三级缓存取放入二级缓存，移除三级缓存
						this.earlySingletonObjects.put(beanName, singletonObject);
						this.singletonFactories.remove(beanName);
					}
				}
			}
		}
		return singletonObject;
	}
	/**
	 * 
	 * @param beanName
	 * @param singletonFactory
	 * @return
	 */
	public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory){
		synchronized (this.singletonObjects) {
			Object singletonObject = this.singletonObjects.get(beanName);
			if (singletonObject == null) {
				if (this.singletonsCurrentlyInDestruction) {
					try {
						throw new BeanCurrentlyInCreationException(beanName,
								"Singleton bean creation not allowed while singletons of this factory are in destruction " +
								"(Do not request a bean from a BeanFactory in a destroy method implementation!)");
					} catch (BeanCurrentlyInCreationException e) {
						e.printStackTrace();
					}
				}
				//创建前调用
				beforeSingletonCreation(beanName);
				boolean newSingleton = false;
				try {
					singletonObject = singletonFactory.getObject();
					newSingleton = true;
				}catch (IllegalStateException ex) {
					singletonObject = this.singletonObjects.get(beanName);
					if (singletonObject == null) {
						throw ex;
					}
				}catch (BeansException ex) {
					try {
						throw ex;
					} catch (BeansException e) {
						e.printStackTrace();
					}
				}finally {
					//创建后调用
					afterSingletonCreation(beanName);
				}
				if (newSingleton) {
					addSingleton(beanName, singletonObject);
				}
			}
			return singletonObject;
		}
	}
	/**
	 * 移除bean
	 * @param beanName
	 */
	protected void removeSingleton(String beanName) {
		synchronized (this.singletonObjects) {
			this.singletonObjects.remove(beanName);
			this.singletonFactories.remove(beanName);
			this.earlySingletonObjects.remove(beanName);
			this.registeredSingletons.remove(beanName);
		}
	}
	@Override
	public boolean containsSingleton(String beanName) {
		return this.singletonObjects.containsKey(beanName);
	}
	@Override
	public String[] getSingletonNames() {
		synchronized (this.singletonObjects) {
			return StringUtils.toStringArray(this.registeredSingletons);
		}
	}
	@Override
	public int getSingletonCount() {
		synchronized (this.singletonObjects) {
			return this.registeredSingletons.size();
		}
	}
	/**
	 * 设置当前bean正在被创建
	 * @param beanName
	 * @param inCreation
	 */
	public void setCurrentlyInCreation(String beanName, boolean inCreation) {
		Assert.notNull(beanName, "Bean name must not be null");
		if (!inCreation) {
			this.inCreationCheckExclusions.add(beanName);
		}
		else {
			this.inCreationCheckExclusions.remove(beanName);
		}
	}
	/**
	 * 当前是否在创建
	 * @param beanName
	 * @return
	 */
	public boolean isCurrentlyInCreation(String beanName) {
		Assert.notNull(beanName, "Bean name must not be null");
		return (!this.inCreationCheckExclusions.contains(beanName) && isSingletonCurrentlyInCreation(beanName));
	}
	/**
	 * 当前单例bean是否在创建
	 * @param beanName
	 * @return
	 */
	public boolean isSingletonCurrentlyInCreation(String beanName) {
		return this.singletonsCurrentlyInCreation.contains(beanName);
	}
	/**
	 * bean创建前回调
	 * @param beanName
	 * @throws BeanCurrentlyInCreationException 
	 */
	protected void beforeSingletonCreation(String beanName){
		if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.add(beanName)) {
			try {
				throw new BeanCurrentlyInCreationException(beanName);
			} catch (BeanCurrentlyInCreationException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * bean创建后回调
	 * @param beanName
	 */
	protected void afterSingletonCreation(String beanName) {
		if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.remove(beanName)) {
			throw new IllegalStateException("Singleton '" + beanName + "' isn't currently in creation");
		}
	}
	/**
	 * 销毁
	 * @param beanName
	 */
	public void destroySingleton(String beanName) {
		removeSingleton(beanName);
	}
	protected void destroyBean(String beanName, Object beanInstance) {
		destroySingleton(beanName);
	}
	protected void clearSingletonCache() {
		synchronized (this.singletonObjects) {
			this.singletonObjects.clear();
			this.singletonFactories.clear();
			this.earlySingletonObjects.clear();
			this.registeredSingletons.clear();
			this.singletonsCurrentlyInDestruction = false;
		}
	}
	/**
	 * 获取互斥锁
	 */
	public final Object getSingletonMutex() {
		return this.singletonObjects;
	}
}
