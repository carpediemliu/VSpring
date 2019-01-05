package org.vitoliu.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface MethodMatcher {

	/**
	 * 匹配是否需要拦截该方法，true表示需要
	 * @param method
	 * @param targetClass
	 * @return
	 */
	boolean matches(Method method,Class<?> targetClass);
}
