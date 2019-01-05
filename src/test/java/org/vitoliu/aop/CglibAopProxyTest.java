package org.vitoliu.aop;

import org.junit.Test;
import org.vitoliu.HelloService;
import org.vitoliu.HelloServiceImpl;
import org.vitoliu.context.ApplicationContext;
import org.vitoliu.context.ClassPathXmlApplicationContext;

/**
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class CglibAopProxyTest {

	@Test
	public void testInterceptor() throws Exception {
		// --------- HelloService without AOP
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("vsioc.xml");
		HelloService HelloService = (HelloService) applicationContext.getBean("HelloService");
		HelloService.hello();

		// --------- HelloService with AOP
		// 1. 设置被代理对象(Joinpoint)
		AdvisedSupport advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(HelloService, HelloServiceImpl.class,
				HelloService.class);
		advisedSupport.setTargetSource(targetSource);

		// 2. 设置拦截器(Advice)
		TimerInterceptor timerInterceptor = new TimerInterceptor();
		advisedSupport.setMethodInterceptor(timerInterceptor);

		// 补：没有设置MethodMatcher，所以拦截该类的所有方法
		// 3. 创建代理(Proxy)
		CglibAopProxy CglibAopProxy = new CglibAopProxy(advisedSupport);
		HelloService HelloServiceProxy = (HelloService) CglibAopProxy.getProxy();

		// 4. 基于AOP的调用
		HelloServiceProxy.hello();

	}
}
