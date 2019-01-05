package org.vitoliu.aop;

import org.aopalliance.aop.Advice;

/**
 * AspectJ表达式切点通知器
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class AspectExpressionPointcutAdvisor implements PointcutAdvisor {
	/**
	 *  AspectJ表达式切点匹配器
	 *  AspectJ表达式匹配的切点，默认有初始化。也默认有了MethodMatcher(AspectJExpressionPointcut实现了MethodMatcher接口)
	 */
	private AspectExpressionPointcut pointcut = new AspectExpressionPointcut();

	/**
	 * 	方法拦截器
	 * 	这个要用户自己去xml文件里配置方法拦截器(MethodInterceptor继承了Advice接口)，
	 *  在AspectJAwareAdvisorAutoProxyCreator设置代理对象的方法拦截器时将Advispr强转为MethodInterceptor
	 */
	private Advice advice;


	@Override
	public Advice getAdvice() {
		return advice;
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}
}
