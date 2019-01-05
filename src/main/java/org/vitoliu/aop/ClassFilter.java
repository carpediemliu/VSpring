package org.vitoliu.aop;

/**
 *
 * 类匹配器
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface ClassFilter {

	/**
	 * 用于匹配tagetClass是否是要拦截的类
	 * @param targetClass
	 * @return
	 */
	boolean matches(Class targetClass);
}
