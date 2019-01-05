package org.vitoliu.context;

import java.util.Map;

import org.vitoliu.beans.BeanDefinition;
import org.vitoliu.beans.factory.AbstractBeanFactory;
import org.vitoliu.beans.factory.AutowireCapableBeanFactory;
import org.vitoliu.beans.io.UrlResourceLoader;
import org.vitoliu.beans.xml.XmlBeanDefinitionReader;

/**
 *
 * 从类路径加载XML资源的具体实现
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	private String configLocation;

	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation, new AutowireCapableBeanFactory());
	}

	private ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation = configLocation;
		refresh();
	}

	/**
	 * 加载出bean的定义，并保存到beanFactory中
	 * 在VSpring中，先用 BeanDefinitionReader 读取 BeanDefinition 后，保存在内置的
	 * registry （键值对为 String - BeanDefinition 的哈希表，通过 getRegistry() 获取）中，然后由
	 *  ApplicationContext 把 BeanDefinitionReader 中 registry 的键值对一个个赋值给 BeanFactory
	 *  中保存的 beanDefinitionConcurrentMap。
	 *  而在 Spring 的实现中，BeanDefinitionReader 直接操作 BeanDefinition
	 *  ，它的 getRegistry() 获取的不是内置的 registry，而是 BeanFactory 的实例。
	 *  以 DefaultListableBeanFactory 为例，它实现了一个 BeanDefinitionRegistry 接口，该接口
	 *  把 BeanDefinition 的 注册 、获取 等方法都暴露了出来，这样，BeanDefinitionReader 可以直接通过这些
	 *  方法把 BeanDefinition 直接加载到 BeanFactory 中去。
	 */
	@Override
	protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
		//从类路径加载xml文件中的bean定义并注册到AbstractBeanDefinitionReader的registry中
		xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
		//将加载出的bean定义从registry赋值到BeanFactory
		for (Map.Entry<String, BeanDefinition> entry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
			beanFactory.registerBeanDefinition(entry.getKey(), entry.getValue());
		}
	}
}
