package com.reyco.customSpring.core.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.reyco.customSpring.core.BeanDefinition;
import com.reyco.customSpring.core.BeanDefinitionRegistry;
import com.reyco.customSpring.core.ComponentScan;
import com.reyco.customSpring.core.Environment;
import com.reyco.customSpring.core.aop.AspectJAutoProxy;
import com.reyco.customSpring.core.aware.Aware;
import com.reyco.customSpring.core.aware.BeanFactoryAware;
import com.reyco.customSpring.core.aware.BeanNameAware;
import com.reyco.customSpring.core.beanPostProcessor.AspectJAwareAdvisorAutoProxyCreator;
import com.reyco.customSpring.core.beanPostProcessor.BeanPostProcessor;
import com.reyco.customSpring.core.commons.BeanUtils;
import com.reyco.customSpring.core.commons.StringUtils;
import com.reyco.customSpring.core.exception.BeanCreationException;
import com.reyco.customSpring.core.exception.BeansException;
import com.reyco.customSpring.core.exception.NoSuchBeanDefinitionException;
import com.reyco.customSpring.core.test.TestServiceImpl;

/**
 * @author reyco
 * @date 2021年5月24日---下午9:05:04
 * 
 *       <pre>
 *
 *       <pre>
 */
@SuppressWarnings("all")
public class DefaultListableBeanFactory extends AbstractBeanFactory implements BeanDefinitionRegistry {

	private static final String APPLICATION_CONTEXT = "applicationContext";

	private boolean allowCircularReferences = true;

	private ApplicationContext applicationContext;

	private Environment environment;

	protected static ThreadLocal<Map<String, Object>> exposeProxyMapThreadLocal = new ThreadLocal<>();

	public DefaultListableBeanFactory() throws Exception {
		this(Object.class);
	}

	public DefaultListableBeanFactory(Class<?> clazz) {
		try {
			parseComponentScan(clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Object createBean(String beanName, BeanDefinition mbd, Object[] args) throws BeanCreationException {
		Object beanInstance = doCreateBean(beanName, mbd, args);
		return beanInstance;
	}
	/**
	 * 重点方法
	 * @param beanName
	 * @param mbd
	 * @param args
	 * @return
	 * @throws BeanCreationException
	 */
	protected Object doCreateBean(final String beanName, final BeanDefinition mbd, final Object[] args) throws BeanCreationException {
		Object instanceWrapper = null;
		if (instanceWrapper == null) {
			//创建实例
			instanceWrapper = createBeanInstance(beanName, mbd, args);
		}
		final Object bean = instanceWrapper;
		//是否单利、是否允许循环依赖、是否正在被创建
		boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences && isSingletonCurrentlyInCreation(beanName));
		if (earlySingletonExposure) {
			addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
		}
		Object exposedObject = bean;
		try {
			//设置属性
			populateBean(beanName, bean);
			//初始化bean对象
			exposedObject = initializeBean(beanName, exposedObject, mbd);
		} catch (Throwable ex) {
			if (ex instanceof BeanCreationException) {
				throw (BeanCreationException) ex;
			} else {
				throw new BeanCreationException(beanName, "Initialization of bean failed" + ex);
			}
		}
		if (earlySingletonExposure) {
			Object earlySingletonReference = getSingleton(beanName, true);
			if (earlySingletonReference != null) {
				if (exposedObject == bean) {
					exposedObject = earlySingletonReference;
				}
			}
		}
		registerDisposableBeanIfNecessary(beanName, bean, mbd);
		return exposedObject;
	}

	private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition mbd) {
		destroyBean(beanName, bean);
	}

	private Object createBeanInstance(String beanName, BeanDefinition mbd, Object[] args) {
		try {
			//
			Class<?> beanClass = mbd.getBeanClass();
			//获取构造器
			Constructor<?> declaredConstructor = beanClass.getDeclaredConstructor();
			//创建实例
			return BeanUtils.instantiateClass(declaredConstructor, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Object getEarlyBeanReference(String beanName, BeanDefinition mbd, Object bean) {
		Object exposedObject = bean;
		return exposedObject;
	}

	protected Object initializeBean(final String beanName, final Object bean, BeanDefinition mbd) {
		// 执行Aware借口
		invokeAwareMethods(beanName, bean);
		// 执行BeanPostProcessor.postProcessBeforeInitialization()
		Object wrappedBean = bean;
		if (mbd != null) {
			wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
		}
		try {
			//执行初始化方法
			invokeInitMethods(beanName, wrappedBean, mbd);
		} catch (Throwable ex) {
			throw new BeanCreationException(beanName, "Invocation of init method failed");
		}
		// 执行BeanPostProcessor.postProcessAfterInitialization()
		if (mbd != null) {
			wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		}

		return wrappedBean;
	}

	private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition mbd) {

	}

	private void invokeAwareMethods(String beanName, Object bean) {
		if (bean instanceof Aware) {
			if (bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(beanName);
			}
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(DefaultListableBeanFactory.this);
			}
		}
	}

	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}

	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (current == null) {
				return result;
			}
			result = current;
		}
		return result;
	}

	@Override
	public boolean containsBean(String name) {
		Object bean = singlonObjects.get(name);
		if (bean == null) {
			return false;
		}
		return true;
	}

	/**
	 * 注册BeanPostProcessor
	 * 
	 * @param beanPostProcessor
	 * @throws Exception
	 */
	protected void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
		BeanDefinition beanDefinition = createBeanDefinition(beanPostProcessor.getClass());
		if (!this.beanDefinitionMap.containsKey(beanDefinition.getBeanName())) {
			this.beanDefinitionMap.put(beanDefinition.getBeanName(), beanDefinition);
			this.beanDefinitionNames.add(beanDefinition.getBeanName());
			this.beanPostProcessors.add(beanPostProcessor);
			this.singlonObjects.put(beanDefinition.getBeanName(), beanPostProcessor);
		} else {
			this.beanPostProcessors.add(beanPostProcessor);
			this.singlonObjects.put(beanDefinition.getBeanName(), beanPostProcessor);
		}
	}

	private void populateBean(String beanName, Object bean) {

	}

	protected void registerBean(String beanName, Object bean) {
		singlonObjects.put(beanName, bean);
		if (bean instanceof BeanPostProcessor) {
			try {
				registerBeanPostProcessor((BeanPostProcessor) bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected String transformedBeanName(String beanName) {
		return beanName;
	}

	private void parseComponentScan(Class<?> clazz) {
		// 包扫描
		ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
		if (componentScan != null) {
			String[] value = componentScan.value();
			if (value == null) {
				String className = clazz.getName();
				value = new String[] { className.substring(0, className.lastIndexOf(".")) };
			}
			this.setComponentScans(value);
		}
		// 解析AspectJAutoProxy
		AspectJAutoProxy aspectJAutoProxy = clazz.getAnnotation(AspectJAutoProxy.class);
		if (aspectJAutoProxy != null) {
			this.setProxyTargetClass(aspectJAutoProxy.proxyTargetClass());
			this.setExposeProxy(aspectJAutoProxy.exposeProxy());
		}
	}

	/**
	 * 解析加载beanDefinition
	 * 
	 * @throws Exception
	 */
	private void loadBeanDefinition() throws Exception {
		BeanDefinition beanDefinition = createBeanDefinition(AspectJAwareAdvisorAutoProxyCreator.class);
		registerBeanDefinition(beanDefinition.getBeanName(), beanDefinition);

		BeanDefinition beanDefinition1 = createBeanDefinition(TestServiceImpl.class);
		registerBeanDefinition(beanDefinition1.getBeanName(), beanDefinition1);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return beanDefinitionMap.get(beanName);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return StringUtils.toStringArray(this.beanDefinitionNames);
	}

	@Override
	public int getBeanDefinitionCount() {
		return beanDefinitionNames.size();
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitionMap.containsKey(beanName);
	}

	@Override
	public void registerBeanDefinition(BeanDefinition beanDefinition) throws Exception {
		if (!beanDefinitionMap.containsKey(beanDefinition.getBeanName())) {
			beanDefinitionMap.put(beanDefinition.getBeanName(), beanDefinition);
			beanDefinitionNames.add(beanDefinition.getBeanName());
		}
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception {
		if (!beanDefinitionMap.containsKey(beanName)) {
			beanDefinitionMap.put(beanName, beanDefinition);
			beanDefinitionNames.add(beanName);
		}
	}

	public BeanDefinition createBeanDefinition(Class<?> clazz) throws Exception {
		String beanName = BeanUtils.getBeanName(clazz);
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanName(beanName);
		beanDefinition.setBeanClassName(clazz.getName());
		beanDefinition.setBeanClass(clazz);
		return beanDefinition;
	}

	@Override
	public void removeBeanDefinition(String beanName) throws Exception {
		if (beanDefinitionMap.containsKey(beanName)) {
			beanDefinitionNames.remove(beanName);
			beanDefinitionMap.remove(beanName);
		}
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public String[] getBeanPostProcessorNames(Class<BeanPostProcessor> clazz) {
		List<String> beanPostProcessorNames = new ArrayList<>();
		for (String beanName : getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = getBeanDefinition(beanName);
			Class<?> beanClass = beanDefinition.getBeanClass();
			if (clazz.isAssignableFrom(beanClass)) {
				beanPostProcessorNames.add(beanName);
			}
		}
		String[] beanPostProcessorNameArray = new String[beanPostProcessorNames.size()];
		beanPostProcessorNames.toArray(beanPostProcessorNameArray);
		return beanPostProcessorNameArray;
	}

	protected void preInstantiateSingletons() throws Exception {
		System.out.println("根据BeanDefinition,实例化所有的SingletonBean");
		List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
		for (String beanName : beanNames) {
			Object bean = getBean(beanName);
		}
	}

	@Override
	public void destroySingletons() {

	}

	@Override
	public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return null;
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return null;
	}

	@Override
	public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		return null;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
