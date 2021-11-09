package com.reyco.customSpring.core.factory.SingletonBeanRegister;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.reyco.customSpring.core.factory.FactoryBean;

/**
 * @author reyco
 * @date 2021年5月28日---上午10:45:07
 * 
 *       <pre>
 *
 *       <pre>
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);

	protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
		try {
			return factoryBean.getObjectType();
		} catch (Throwable ex) {
			return null;
		}
	}
	
	protected Object getCachedObjectForFactoryBean(String beanName) {
		return this.factoryBeanObjectCache.get(beanName);
	}

	@Override
	protected void removeSingleton(String beanName) {
		synchronized (getSingletonMutex()) {
			super.removeSingleton(beanName);
			this.factoryBeanObjectCache.remove(beanName);
		}
	}

	/**
	 * Overridden to clear the FactoryBean object cache as well.
	 */
	@Override
	protected void clearSingletonCache() {
		synchronized (getSingletonMutex()) {
			super.clearSingletonCache();
			this.factoryBeanObjectCache.clear();
		}
	}
}
