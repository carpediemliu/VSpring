import java.util.Map;

import org.junit.Test;
import org.vitoliu.HelloService;
import org.vitoliu.beans.BeanDefinition;
import org.vitoliu.beans.factory.AbstractBeanFactory;
import org.vitoliu.beans.factory.AutowireCapableBeanFactory;
import org.vitoliu.beans.io.UrlResourceLoader;
import org.vitoliu.beans.xml.XmlBeanDefinitionReader;

/**
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class BeanFactoryTest {

	@Test
	public void testInit()throws Exception{
		// read xml
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("vsioc.xml");

		//初始化beanFactory并实例化bean
		AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
		for (Map.Entry<String, BeanDefinition> entry : xmlBeanDefinitionReader.getRegistry().entrySet()){
			beanFactory.registerBeanDefinition(entry.getKey(), entry.getValue());
		}

		//获取bean
		HelloService helloService = (HelloService) beanFactory.getBean("helloService");
		helloService.hello();
		}

}
