package org.vitoliu.aop;

import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;

/**
 *
 * 封装了Target对象，methodInterceptor和MethodMatcher
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@Data
class AdvisedSupport {

	/**
	 * 要拦截的对象
	 */
	private TargetSource targetSource;

	/**
	 * Spring的AOP仅针对方法级别，所以再AopProxy里可以直接拿来放入对象的方法调用即可
	 */
	private MethodInterceptor methodInterceptor;


	private MethodMatcher methodMatcher;
}
