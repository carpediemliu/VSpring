package org.vitoliu.beans.factory;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.vitoliu.beans.BeanDefinition;
import org.vitoliu.beans.BeanPostProcessor;

/**
 * BeanFactory 的一种抽象类实现，规范了 IoC 容器的基本结构。
 * IoC 容器的结构：AbstractBeanFactory
 * 维护一个 beanDefinitionMap 哈希表用于保存类的定义信息（BeanDefinition）。
 * @author vito.liu
 * @since 23 十一月 2018
 */
public abstract class AbstractBeanFactory implements BeanFactory {

	/**
	 * key:bean的名称
	 * value:bean的定义信息
	 * threadSafe
	 */
	private ConcurrentMap<String, BeanDefinition> beanDefinitionConcurrentMap = Maps.newConcurrentMap();

	/**
	 * 保存完成注册的bean的name
	 */
	private final List<String> beanDefinitionNames = Lists.newArrayList();

	/**
	 * 增加bean处理程序：
	 * 例如通过AspectJAwareAdvisorAutoProxyCreator#postProcessAfterInitialization()实现AOP的织入
	 */
	private List<BeanPostProcessor> beanPostProcessors = Lists.newArrayList();

	/**
	 * 根据名字获取bean实例(实例化并初始化bean)
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getBean(String name) throws Exception {
		//获取该bean的定义
		BeanDefinition beanDefinition = beanDefinitionConcurrentMap.get(name);
		//若没有，抛异常
		if (beanDefinition == null) {
			throw new IllegalArgumentException("No bean named " + name + "is defined");
		}
		Object bean = beanDefinition.getBean();
		//如果该bean还没被装配
		if (bean == null) {
			//装配bean(初始化+注入属性）
			bean = doCreateBean(beanDefinition);
			//生成相关代理类,用于实现AOP织入
			bean = initializeBean(bean, name);
			beanDefinition.setBean(bean);
		}
		return bean;
	}

	/**
	 * 初始化bean
	 * 可以在此做AOP处理，返回的是一个代理对象
	 * @param bean
	 * @param name
	 * @return Object
	 * @throws Exception
	 */
	private Object initializeBean(Object bean, String name) throws Exception {
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
		}
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
		}
		return bean;
	}

	private Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
		//实例化bean
		Object bean = createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		injectPropertyValues(bean, beanDefinition);
		return bean;
	}

	/**
	 * 模板模式
	 * 具体的属性注入方法由子类实现
	 * @param bean
	 * @param beanDefinition
	 * @return Object
	 * @throws Exception
	 */
	protected abstract void injectPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception;

	private Object createBeanInstance(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException {
		return beanDefinition.getBeanClass().newInstance();
	}

	/**
	 * 预处理bean的定义，将bean的名字提前存好，实现IOC容器中的单例bean
	 * @throws Exception
	 */
	public void preInstantiateSingletons() throws Exception {
		for (String beanName : beanDefinitionNames) {
			getBean(beanName);
		}
	}

	/**
	 * 根据类型获取所有实例化的bean
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getBeansOfType(Class<?> type) throws Exception {
		List beans = Lists.newArrayList();
		for (String beanDefinitionName : beanDefinitionNames) {
//			boolean isAssignableFrom(Class<?> cls)
//			判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
			if (type.isAssignableFrom(beanDefinitionConcurrentMap.get(beanDefinitionName).getBeanClass())) {
				beans.add(getBean(beanDefinitionName));
			}
		}
		return beans;
	}

	/**
	 * 注册某个beanName的定义
	 * @param name
	 * @param beanDefinition
	 */
	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
		beanDefinitionConcurrentMap.put(name, beanDefinition);
		beanDefinitionNames.add(name);
	}

	/**
	 * 添加处理器
	 * @param beanPostProcessor
	 */
	public void addBeanPostProcessors(BeanPostProcessor beanPostProcessor) {
		this.beanPostProcessors.add(beanPostProcessor);
	}
}
