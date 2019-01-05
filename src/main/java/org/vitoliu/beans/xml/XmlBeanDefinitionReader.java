package org.vitoliu.beans.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.vitoliu.beans.AbstractBeanDefinitionReader;
import org.vitoliu.beans.BeanDefinition;
import org.vitoliu.beans.BeanReference;
import org.vitoliu.beans.PropertyValue;
import org.vitoliu.beans.io.ResourceLoader;
import org.vitoliu.constant.BeanLabelConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
	/**
	 * 实例化父类，启动时加载资源，解析出BeanDefinition后放入父类的registry中保存
	 * @param resourceLoader
	 */
	public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		//获取资源输入流
		InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
		doLoadBeanDefinitions(inputStream);
	}

	/**
	 * 从xml文件中读取Bean定义。注意此时Bean定义里未实例化Bean
	 * @param in
	 * @throws Exception
	 */
	private void doLoadBeanDefinitions(InputStream in) throws Exception {
		//获取工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//获取生成器
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		//解析为document
		Document doc = documentBuilder.parse(in);
		//解析并注册其中的Bean
		registerBeanDefinitions(doc);
		//关闭流
		in.close();
	}

	/**
	 * 解析并注册其中的Bean
	 * @param doc
	 */
	private void registerBeanDefinitions(Document doc) {
		//获取根标签<beans>
		Element beans = doc.getDocumentElement();
		//解析
		parseBeanDefinitions(beans);
	}

	private void parseBeanDefinitions(Element beans) {
		//获取<beans>下所有的<bean>
		NodeList beanNodeList = beans.getChildNodes();
		for (int i = 0; i < beanNodeList.getLength(); i++) {
			Node beanNode = beanNodeList.item(i);
			if (beanNode instanceof Element) {
				Element bean = (Element) beanNode;
				//解析<bean>标签
				processBeanDefinitions(bean);
			}
		}
	}

	private void processBeanDefinitions(Element bean) {
		//获取bean标签的id属性作为bean的Name
		String name = bean.getAttribute(BeanLabelConstants.ID);
		String className = bean.getAttribute(BeanLabelConstants.CLAZZ);
		BeanDefinition beanDefinition = new BeanDefinition();
		//解析bean标签下的property子标签
		processProperty(bean, beanDefinition);
		//填充beanDefinition属性
		beanDefinition.setBeanClassName(className);
		//注册类定义
		getRegistry().put(name, beanDefinition);
	}

	/**
	 * 解析bean标签下的property子标签
	 * @param bean
	 * @param beanDefinition
	 */
	private void processProperty(Element bean, BeanDefinition beanDefinition) {
		// 获取所有property标签
		NodeList propertyNodes = bean.getElementsByTagName(BeanLabelConstants.PROPERTY);
		for (int i = 0; i < propertyNodes.getLength(); i++) {
			Node node = propertyNodes.item(i);
			if (node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute(BeanLabelConstants.NAME);
				String value = propertyEle.getAttribute(BeanLabelConstants.VALUE);
				// 如果是value型的属性值
				if (!StringUtils.isEmpty(value)) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue<>(name, value));
				}
				else {
					// 否则是ref型的属性值
					String ref = propertyEle.getAttribute(BeanLabelConstants.REF);
					if (StringUtils.isEmpty(ref)) {
						throw new IllegalArgumentException("Configuration problem: <property> element for property '"
								+ name + "' must specify a ref or value");
					}
					BeanReference beanReference = new BeanReference(ref);
					// 保存一个ref型属性值的属性
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue<>(name, beanReference));
				}
			}
		}
	}
}
