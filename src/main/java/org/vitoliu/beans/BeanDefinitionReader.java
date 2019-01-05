package org.vitoliu.beans;

/**
 *
 * 定义从配置解析BeanDefinition的接口
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface BeanDefinitionReader {

	/**
	 * 根据资源路径加载BeanDefinition
	 * @param location
	 * @throws Exception
	 */
	void loadBeanDefinitions(String location) throws Exception;
}
