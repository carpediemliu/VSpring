package org.vitoliu.aop;

/**
 * 切点
 * 确定对某个类的某个方法进行AOP
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface Pointcut {

	/**
	 * 类匹配
	 * @return classFilter
	 */
	ClassFilter getClassFilter();

	/**
	 * 方法匹配
	 * @return methodMatcher
	 */
	MethodMatcher getMethodMatcher();
}
