package org.vitoliu.aop;

import java.lang.reflect.Method;

import lombok.Data;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.vitoliu.beans.factory.BeanFactory;

/**
 * 环绕通知
 * 通过AspectJ表达式匹配
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@Data
public class AspectAroundAdvice implements Advice, MethodInterceptor {

	private BeanFactory beanFactory;

	private Method aspectJMethod;

	private String aspectJInstanceName;

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		return aspectJMethod.invoke(beanFactory.getBean(aspectJInstanceName), methodInvocation);
	}
}
