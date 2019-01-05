package org.vitoliu.aop;

import org.aopalliance.aop.Advice;

/**
 *
 * 实现此接口可以获取通知器
 * 通知器用于实现具体的方法拦截，对应Spring中的前置、后置、环绕通知等
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface Advisor {

	/**
	 * 获取通知器
	 * @return {@link Advice}
	 *
	 */
	Advice getAdvice();
}
