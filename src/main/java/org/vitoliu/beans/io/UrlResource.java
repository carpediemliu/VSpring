package org.vitoliu.beans.io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import lombok.AllArgsConstructor;

/**
 * {@link Resource}的url实现
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@AllArgsConstructor
public class UrlResource implements Resource {

	/**
	 * 通过该属性加载资源
	 */
	private final URL url;

	/**
	 * 通过URL加载资源
	 * @return
	 * @throws Exception
	 */
	@Override
	public InputStream getInputStream() throws Exception {
		URLConnection urlConnection = url.openConnection();
		urlConnection.connect();
		return urlConnection.getInputStream();
	}
}
