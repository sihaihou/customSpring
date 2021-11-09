package com.reyco.customSpring.core.factory;

import java.util.ArrayList;
import java.util.List;

import com.reyco.customSpring.core.BeanDefinition;
import com.reyco.customSpring.core.BeanDefinitionRegistry;
import com.reyco.customSpring.core.Environment;
import com.reyco.customSpring.core.beanFactoryPostProcessor.BeanDefinitionRegistryPostProcessor;
import com.reyco.customSpring.core.beanFactoryPostProcessor.BeanFactoryPostProcessor;
import com.reyco.customSpring.core.beanFactoryPostProcessor.ConfigurationClassPostProcessor;
import com.reyco.customSpring.core.beanPostProcessor.ApplicationContextAwareProcessor;
import com.reyco.customSpring.core.beanPostProcessor.AspectJAwareAdvisorAutoProxyCreator;
import com.reyco.customSpring.core.beanPostProcessor.BeanPostProcessor;
import com.reyco.customSpring.core.test.Test;
import com.reyco.customSpring.core.test.TestServiceImpl;
/**
 * @author reyco
 * @date 2021年5月25日---上午11:35:57
 * 
 *       <pre>
 *
 *       <pre>
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

	private String id = "123456789";

	private ApplicationContext parent;

	public DefaultListableBeanFactory beanFactory;

	private Environment environment;

	private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

	public AbstractApplicationContext() {
	}

	public AbstractApplicationContext(ApplicationContext parent) {
		this();
		this.parent = parent;
	}

	protected void refresh() {
		try {
			// 1.创建BeanFactory
			DefaultListableBeanFactory beanFactory = obtainFreshBeanFactory();
			// 2.准备BeanFactory
			prepareBeanFactory(beanFactory);
			// 3.提供接口处理beanFactory
			postProcessBeanFactory(beanFactory);
			// 4.执行BeanFactoryPostProcessor
			invokeBeanFactoryPostProcessors(beanFactory);
			// 5.注册BeanPostProcessors
			registerBeanPostProcessors(beanFactory);
			// 6.实例化单例bean
			finishBeanFactoryInitialization(beanFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) throws Exception {
		beanFactory.preInstantiateSingletons();
	}

	/**
	 * 注册BeanPostProcessors
	 * @param beanFactory
	 */
	private void registerBeanPostProcessors(DefaultListableBeanFactory beanFactory) {
		registerBeanPostProcessors(beanFactory, this);
	}
	/**
	 * 注册beanPostProcessors
	 * @param beanFactory
	 * @param abstractApplicationContext
	 */
	private void registerBeanPostProcessors(DefaultListableBeanFactory beanFactory,
			AbstractApplicationContext abstractApplicationContext) {
		try {
			System.out.println("注册BeanPostProcessor...");
			List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
			String[] postProcessorNames = beanFactory.getBeanPostProcessorNames(BeanPostProcessor.class);
			for (String ppName : postProcessorNames) {
				BeanPostProcessor beanPostProcessor = beanFactory.getBean(ppName,BeanPostProcessor.class);
				beanPostProcessors.add(beanPostProcessor);
			}
			registerBeanPostProcessors(beanFactory,beanPostProcessors);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 注册beanPostProcessors
	 * @param beanFactory
	 * @param beanPostProcessors
	 * @throws Exception 
	 */
	private void registerBeanPostProcessors(DefaultListableBeanFactory beanFactory,
			List<BeanPostProcessor> beanPostProcessors) throws Exception {
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			beanFactory.registerBeanPostProcessor(beanPostProcessor);
		}
	}

	/**
	 * 执行BeanFactoryPostProcessor
	 * 
	 * @param beanFactory
	 * @throws Exception
	 */
	private void invokeBeanFactoryPostProcessors(DefaultListableBeanFactory beanFactory) throws Exception {
		System.out.println("执行BeanFactoryPostProcessor...");
		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();
			for (BeanFactoryPostProcessor beanFactoryPostProcessor : getBeanFactoryPostProcessors()) {
				if (beanFactoryPostProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor = (BeanDefinitionRegistryPostProcessor) beanFactoryPostProcessor;
					registryProcessor.postProcessBeanFactory(registry);
					registryProcessors.add(registryProcessor);
					beanFactoryPostProcessors.add(beanFactoryPostProcessor);
				} else {
					beanFactoryPostProcessors.add(beanFactoryPostProcessor);
				}
				invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
			}
		} else {
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}
	}

	/**
	 * 
	 * @param beanFactoryPostProcessors
	 * @param beanFactory
	 * @throws Exception
	 */
	private void invokeBeanFactoryPostProcessors(List<BeanFactoryPostProcessor> beanFactoryPostProcessors,
			DefaultListableBeanFactory beanFactory) throws Exception {
		for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	/**
	 * 提供接口处理beanFactory-----扩展
	 * 
	 * @param beanFactory2
	 */
	protected void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {
		System.out.println("提供接口处理beanFactory-----扩展");
	}

	private List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
		return this.beanFactoryPostProcessors;
	}

	/**
	 * 准备beanFactory
	 * 
	 * @param beanFactory2
	 * @throws Exception 
	 */
	private void prepareBeanFactory(DefaultListableBeanFactory beanFactory) throws Exception {
		System.out.println("准备beanFactory工厂");
		this.beanFactoryPostProcessors.add(new ConfigurationClassPostProcessor());
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));;
		beanFactory.registerBeanDefinition(beanFactory.createBeanDefinition(AspectJAwareAdvisorAutoProxyCreator.class));
		beanFactory.registerBean("environment", getEnvironment());
	}

	protected DefaultListableBeanFactory obtainFreshBeanFactory() {
		this.beanFactory = getBeanFactory();
		return beanFactory;
	}

	public DefaultListableBeanFactory getBeanFactory() {
		try {
			if(beanFactory==null) {
				beanFactory = new DefaultListableBeanFactory();
			}
			loadBeanDefinitions(beanFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanFactory;
	}
	/**
	 * 加载BeanDefinition
	 * @param beanFactory
	 * @throws Exception
	 */
	private void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception {
		System.out.println("加载XMl解析为BeanDefinition对象");
		
		BeanDefinition testBeanDefinition = beanFactory.createBeanDefinition(Test.class);
		beanFactory.registerBeanDefinition(testBeanDefinition);
		
		BeanDefinition testServiceImplBeanDefinition = beanFactory.createBeanDefinition(TestServiceImpl.class);
		beanFactory.registerBeanDefinition(testServiceImplBeanDefinition);
	}

	public void setParent(ApplicationContext parent) {
		this.parent = parent;
		if (parent != null) {
			this.environment = getEnvironment().merge(parent.getEnvironment());
		}
	}

	public Environment getEnvironment() {
		if (this.environment == null) {
			this.environment = createEnvironment();
		}
		return this.environment;
	}
	
	private Environment createEnvironment() {
		return new Environment();
	}

	@Override
	public ApplicationContext getParent() {
		return this.parent;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}
}
