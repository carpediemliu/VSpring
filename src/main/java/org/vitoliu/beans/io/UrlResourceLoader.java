package org.vitoliu.beans.io;

import java.net.URL;

/**
 * 通过Url的方式加载资源
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class UrlResourceLoader implements ResourceLoader {


	@Override
	public Resource getResource(String location) {
		//根据资源名称查找资源
		URL url = getClass().getClassLoader().getResource(location);
		return new UrlResource(url);
	}
}
