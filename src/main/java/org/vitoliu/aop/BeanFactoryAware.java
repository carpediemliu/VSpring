package org.vitoliu.aop;


import org.vitoliu.beans.factory.BeanFactory;

/**
 * 定义操作BeanFactory的行为
 *
 * 容器的引用传入到 Bean 中去，
 * 这样，Bean 将获取容器的引用，获取对容器操作的权限，
 * 也就允许了编写扩展 IoC 容器的功能的 Bean。
 * 例如：获取容器中所有的 切点对象，决定对哪些对象的哪些方法进行代理。
 * 解决了为哪些对象提供 AOP 的织入的问题。
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface BeanFactoryAware {

	/**
	 * 注入BeanFactory
	 * @param beanFactory
	 * @throws Exception
	 */
	void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
