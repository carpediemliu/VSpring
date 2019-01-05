package org.vitoliu.beans;

/**
 * 用于在bean定义初始化时嵌入相关操作
 * 例如：在 postProcessorAfterInitialization 方法中，
 * 使用动态代理的方式，返回一个对象的代理对象。
 * 解决了在 IoC 容器的何处植入 AOP 的问题。
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface BeanPostProcessor {

	/**
	 * 前置处理
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

	/**
	 * 后置处理
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
}
