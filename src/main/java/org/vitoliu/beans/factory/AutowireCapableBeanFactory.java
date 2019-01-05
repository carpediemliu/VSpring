package org.vitoliu.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.NoArgsConstructor;
import org.vitoliu.aop.BeanFactoryAware;
import org.vitoliu.beans.BeanDefinition;
import org.vitoliu.beans.BeanReference;
import org.vitoliu.beans.PropertyValue;

/**
 * BeanFactory自动装配的实现
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@NoArgsConstructor
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

	/**
	 * 通过反射自动装配所有bean
	 * @param bean
	 * @param beanDefinition
	 * @return
	 * @throws Exception
	 */
	@Override
	protected void injectPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
		if (bean instanceof BeanFactoryAware) {
			((BeanFactoryAware) bean).setBeanFactory(this);
		}
		//获取bean的所有属性，如果属性为ref，先实例化ref这个bean，再装载到这个bean里
		for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
			Object value = propertyValue.getValue();
			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}
//			 例如：<bean id="mail" class="org.vitoliu.Mail">
//					<property name="Id" value="1"></property>
//			     </bean>
//			 先获取Mail对象的setId方法，然后通过反射调用该方法将value设置进去
//			  getDeclaredMethod方法的第一个参数是方法名，第二个参数是该方法的参数列表

			try {
				Method declaredMethod = bean.getClass().getDeclaredMethod(
						// 拼接方法名
						"set" + propertyValue.getName().substring(0, 1).toUpperCase()
								+ propertyValue.getName().substring(1),
						value.getClass());

				declaredMethod.setAccessible(true);
				declaredMethod.invoke(bean, value);
			}
			catch (NoSuchMethodException e) {
				// 如果该bean没有setXXX的类似方法，就直接将value设置到相应的属性域内
				Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				declaredField.setAccessible(true);
				declaredField.set(bean, value);
			}
		}
	}
}
