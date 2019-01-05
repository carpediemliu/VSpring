package org.vitoliu.beans;

import java.util.List;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 封装一个对象所有的PropertyValue,以集合方式存储
 * @author yukun.liu
 * @since 23 十一月 2018
 */
@NoArgsConstructor
@Getter
public class PropertyValues {

	private final List<PropertyValue> propertyValueList = Lists.newArrayList();

	public void addPropertyValue(PropertyValue pv) {
		this.propertyValueList.add(pv);
	}

}