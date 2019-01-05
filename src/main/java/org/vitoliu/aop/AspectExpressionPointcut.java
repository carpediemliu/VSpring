package org.vitoliu.aop;

import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.Sets;
import lombok.Data;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

/**
 *
 * 通过AspectJ表达式识别切点
 * @author yukun.liu
 * @since 24 十一月 2018
 */
@Data
public class AspectExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

	/**
	 * 表达式解析器
	 */
	private PointcutParser parser;

	/**
	 * 表达式
	 */
	private String expression;

	/**
	 * 切点表达式
	 */
	private PointcutExpression pointcutExpression;

	private static final Set<PointcutPrimitive> DEFAULT_PRIMITIVES = Sets.newHashSet();

	static {
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.ARGS);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.THIS);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.TARGET);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		DEFAULT_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
	}

	AspectExpressionPointcut() {
		this(DEFAULT_PRIMITIVES);
	}

	private AspectExpressionPointcut(Set<PointcutPrimitive> supportedPrimitives) {
		parser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(supportedPrimitives);
	}

	private void checkReadyToMatch() {
		if (pointcutExpression == null) {
			pointcutExpression = buildPointcutExpression();
		}
	}

	private PointcutExpression buildPointcutExpression() {
		return parser.parsePointcutExpression(expression);
	}

	@Override
	public boolean matches(Class targetClass) {
		checkReadyToMatch();
		return pointcutExpression.couldMatchJoinPointsInType(targetClass);
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		checkReadyToMatch();
		ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
		//别的情况就不判断了。
		return shadowMatch.alwaysMatches();
	}

	@Override
	public ClassFilter getClassFilter() {
		return this;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return this;
	}
}
