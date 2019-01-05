package org.vitoliu.aop;

import lombok.Getter;

/**
 * 封装被代理的类的基本信息
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@Getter
class TargetSource {

	/**
	 * 原对象
	 */
	private Object target;

	/**
	 * 原对象类信息
	 */
	private Class<?> targetClass;

	/**
	 * 对象实现的接口数组
	 */
	private Class<?>[] interfaceClasses;

	TargetSource(Object target, Class<?> targetClass, Class<?>... interfaceClasses) {
		this.target = target;
		this.targetClass = targetClass;
		this.interfaceClasses = interfaceClasses;
	}
}
