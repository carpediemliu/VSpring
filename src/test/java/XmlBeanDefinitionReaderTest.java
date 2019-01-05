import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.vitoliu.beans.BeanDefinition;
import org.vitoliu.beans.io.UrlResourceLoader;
import org.vitoliu.beans.xml.XmlBeanDefinitionReader;

/**
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class XmlBeanDefinitionReaderTest {

	@Test
	public void TestReader() throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("vsioc.xml");
		Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
		System.out.println(registry);
		Assert.assertTrue(registry.size() > 0);
	}
}
