package org.vitoliu.aop;

/**
 * 抽象工厂，定义生成代理类的规范
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public abstract class AbstractProxyFactory extends AdvisedSupport implements AopProxy {


	@Override
	public Object getProxy() {
		return createProxy().getProxy();
	}

	/**
	 * 创建代理工厂
	 * @return AopProxy
	 */
	protected abstract AopProxy createProxy();

}
