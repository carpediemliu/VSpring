package org.vitoliu;

import lombok.Setter;

/**
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@Setter
public class HelloServiceImpl implements HelloService {

	private String output;

	private OutputService outputService;

	@Override
	public void hello() {
		outputService.output(output);
	}
}
