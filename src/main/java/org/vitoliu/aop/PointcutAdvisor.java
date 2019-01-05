package org.vitoliu.aop;



/**
 *
 * 切点通知器
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface PointcutAdvisor extends Advisor{

	/**
	 * 获取切点
	 * @return Pointcut
	 */
	Pointcut getPointcut();
}
