package org.vitoliu.aop;

/**
 *	动态代理 cglib方式
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class CglibProxyFactory extends AbstractProxyFactory {


	@Override
	protected AopProxy createProxy() {
		return new CglibAopProxy(this);
	}
}
