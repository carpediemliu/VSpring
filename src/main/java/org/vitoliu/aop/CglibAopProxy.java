package org.vitoliu.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * cglib实现动态代理，他的原理是生成一个继承目标类的子类，通过重写父类某些方法进行增强处理
 * 但是因为采用的是继承，所以不能对定义为final的类进行代理
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class CglibAopProxy extends AbstractAopProxy {

	CglibAopProxy(AdvisedSupport advisedSupport) {
		super(advisedSupport);
	}

	@Override
	public Object getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(advisedSupport.getTargetSource().getTargetClass());
		enhancer.setInterfaces(advisedSupport.getTargetSource().getInterfaceClasses());
		//设置callBack
		enhancer.setCallback(new DynamicProxyInterceptor(advisedSupport));
		return null;
	}

	/**
	 * {@link net.sf.cglib.proxy.MethodInterceptor}
	 * extends
	 * {@link net.sf.cglib.proxy}
	 *
	 */
	private static class DynamicProxyInterceptor implements MethodInterceptor {
		/**
		 * 代理支持类
		 */
		private AdvisedSupport advisedSupport;

		/**
		 * 自定义的方法拦截器
		 */
		private org.aopalliance.intercept.MethodInterceptor delegateMethodInterceptor;

		DynamicProxyInterceptor(AdvisedSupport advisedSupport) {
			this.advisedSupport = advisedSupport;
			this.delegateMethodInterceptor = advisedSupport.getMethodInterceptor();
		}

		/**
		 * 如果未自定义方法匹配器，拦截代理对象的所有方法
		 * 若定义，匹配方法成功，拦截指定方法
		 * @param obj
		 * @param method
		 * @param args
		 * @param proxy
		 * @return Object
		 * @throws Throwable 异常
		 */
		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			if (advisedSupport.getMethodMatcher() == null || advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTargetClass())) {
				//delegateMethodInterceptor为自定义AOP方法拦截器
				return delegateMethodInterceptor.invoke(new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args, proxy));
			}
			//有AspectJ表达式，但并未匹配到方法，返回通过methodProxy调用原始对象的该方法
			return new CglibMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args, proxy).proceed();
		}

		/**
		 * 方法代理的封装
		 */
		private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
			/**
			 * 方法代理
			 */
			private final MethodProxy methodProxy;

			CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
				super(target, method, arguments);
				this.methodProxy = methodProxy;
			}

			@Override
			public Object proceed() throws Throwable {
				return this.methodProxy.invoke(this.target, this.arguments);
			}
		}
	}
}
