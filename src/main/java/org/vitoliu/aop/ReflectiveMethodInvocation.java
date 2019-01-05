package org.vitoliu.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 封装被代理对象的方法
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@AllArgsConstructor
public class ReflectiveMethodInvocation implements MethodInvocation {

	/**
	 * 原对象
	 */
	protected Object target;

	/**
	 * 代理的方法
	 */
	protected Method method;

	/**
	 * 方法入参
	 */
	protected Object[] arguments;

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public Object getThis() {
		return target;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return method;
	}

	/**
	 * 调用被拦截对象的方法
	 * @return
	 * @throws Throwable 异常
	 */
	@Override
	public Object proceed() throws Throwable {

		return method.invoke(target, arguments);
	}
}
