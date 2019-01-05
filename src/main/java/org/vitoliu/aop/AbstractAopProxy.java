package org.vitoliu.aop;

/**
 * 继承该类拥有获取代理类的能力，同时也有AdvisedSupport的支持
 * @author yukun.liu
 * @since 24 十一月 2018
 */
abstract class AbstractAopProxy implements AopProxy {
	/**
	 * 封装了对代理对象的某些操作
	 */
	AdvisedSupport advisedSupport;

	AbstractAopProxy(AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}
}
