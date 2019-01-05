package org.vitoliu.aop;

/**
 *
 * 通过该接口获取一个aop的代理对象
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface AopProxy {

	/**
	 * 获取代理对象
	 * @return object
	 */
	Object getProxy();
}
