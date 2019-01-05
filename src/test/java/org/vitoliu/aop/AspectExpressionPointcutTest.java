package org.vitoliu.aop;

import org.junit.Assert;
import org.junit.Test;
import org.vitoliu.HelloServiceImpl;

/**
 *
 * @author yukun.liu
 * @since 24 十一月 2018
 */
public class AspectExpressionPointcutTest {


	@Test
	public void testMethodInterceptor() throws Exception {
		String expression = "execution(* org.vitoliu.*.*(..))";
		AspectExpressionPointcut aspectJExpressionPointcut = new AspectExpressionPointcut();
		aspectJExpressionPointcut.setExpression(expression);
		boolean matches = aspectJExpressionPointcut.getMethodMatcher()
				.matches(HelloServiceImpl.class.getDeclaredMethod("hello"), HelloServiceImpl.class);
		Assert.assertTrue(matches);
	}
}
