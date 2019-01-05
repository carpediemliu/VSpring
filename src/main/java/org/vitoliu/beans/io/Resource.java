package org.vitoliu.beans.io;

import java.io.InputStream;

/**
 *
 * 外部资源的抽象，实现该接口读取资源以存入IOC容器
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public interface Resource {

	/**
	 * 获取资源的输入流
	 * @return
	 * @throws Exception
	 */
	InputStream getInputStream() throws Exception;
}
