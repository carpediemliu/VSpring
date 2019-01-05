package org.vitoliu.beans.io;

/**
 *
 * 资源加载器
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface ResourceLoader {

	/**
	 * 根据资源名称获取资源对象
	 * @param location
	 * @return
	 */
	Resource getResource(String location);
}
