package org.vitoliu.aop;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.vitoliu.beans.BeanPostProcessor;
import org.vitoliu.beans.factory.AbstractBeanFactory;
import org.vitoliu.beans.factory.BeanFactory;

/**
 * 实现了BeanFactoryAware接口，虽然该bean也会存在IOC容器中，但其包含了对BeanFactory的引用。
 * 即可以获取IOC容器的引用，进而获取容器中所有的切点对象，决定对那些对象的哪些方法进行代理。
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class AspectAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

	private AbstractBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory)  {
		this.beanFactory = (AbstractBeanFactory) beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)  {
		return bean;
	}

	/**
	 * 可以取看看AbstractBeanFactory的getBean()的实现
	 * bean实例化后要进行初始化操作，会经过这个方法满足条件则生成相关的代理类并返回
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
		// 如果是切点通知器，则直接返回
		if (bean instanceof AspectExpressionPointcutAdvisor) {
			return bean;
		}
		// 如果bean是方法拦截器，则直接返回
		if (bean instanceof MethodInterceptor) {
			return bean;
		}
		// 通过getBeansForType方法加载BeanFactory 中所有的 PointcutAdvisor（保证了 PointcutAdvisor 的实例化顺序优于普通 Bean。)
		// AspectJ方式实现织入,这里它会扫描所有Pointcut，并对bean做织入。
		List<AspectExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectExpressionPointcutAdvisor.class);
		for (AspectExpressionPointcutAdvisor advisor : advisors) {
			// 匹配要拦截的类
			// 使用AspectJExpressionPointcut的matches匹配器，判断当前对象是不是要拦截的类的对象。
			if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
				// ProxyFactory继承了AdvisedSupport，所以内部封装了TargetSource和MethodInterceptor的元数据对象
				AbstractProxyFactory advisedSupport = new CglibProxyFactory();
				// 设置切点的方法拦截器
				advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
				// 设置切点的方法匹配器
				// 利用AspectJ表达式进行方法匹配
				// AspectJExpressionPointcutAdvisor里的AspectJExpressionPointcut的getMethodMatcher()方法
				advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
				// 是要拦截的类, 生成一个 TargetSource（要拦截的对象和其类型）(被代理对象)
				TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
				advisedSupport.setTargetSource(targetSource);
				// 交给实现了 AopProxy接口的getProxy方法的ProxyFactory去生成代理对象
				return advisedSupport.getProxy();
			}
		}
		return bean;
	}

}
