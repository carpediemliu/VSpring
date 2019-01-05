package org.vitoliu.beans;

/**
 *	property标签中ref引用的对象
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class BeanReference<T> {

	private String name;

	private T ref;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getRef() {
		return ref;
	}

	public void setRef(T ref) {
		this.ref = ref;
	}

	public BeanReference(String name) {
		this.name = name;
	}
}
