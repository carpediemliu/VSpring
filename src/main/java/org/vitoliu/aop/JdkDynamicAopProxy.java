package org.vitoliu.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;

/**
 *
 * 基于JDK的动态代理
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class JdkDynamicAopProxy extends AbstractAopProxy implements InvocationHandler {

	JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
		super(advisedSupport);
	}

	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(getClass().getClassLoader(), advisedSupport.getTargetSource().getInterfaceClasses(), this);
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
		if (advisedSupport.getMethodMatcher() != null
				&& advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())) {
			return methodInterceptor.invoke(new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(),
					method, args));
		}
		else {
			return method.invoke(advisedSupport.getTargetSource().getTarget(), args);
		}
	}

}
