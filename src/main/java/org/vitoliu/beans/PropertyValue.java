package org.vitoliu.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 封装bean的属性
 * @author yukun.liu
 * @since 23 十一月 2018
 */
@Getter
@AllArgsConstructor
public class PropertyValue<T> {

	/**
	 * 属性名
	 */
	private final String name;

	/**
	 * 在 Spring 的 XML 中的 property标签 中，键是 key ，值是 value 或者 ref。对于 value 只要直接注入属性就行
	 * 了，但是 ref 要先进行解析,转化为对应的实际 Object。
	 */
	private final T value;

}
