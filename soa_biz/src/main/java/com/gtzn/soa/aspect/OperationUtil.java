package com.gtzn.soa.aspect;

import com.gtzn.common.annotation.Source;
import com.gtzn.common.config.Global;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @info 提供多数据源的切换,对webservice方式进行补充
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-05-02
 * @version 1.0.0
 */
@Component
@Aspect
public class OperationUtil {

	private String defaultDataSource = Global.getConfig("datasource.default.switchKey");

	//定义切入点后，从连接点取命中对象,事实证明不能二次代理
	@Before(value = "execution(* com.gtzn..*.service.impl..*.*(..))")
	public void getDataSource(JoinPoint jp) {
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		if (method.isAnnotationPresent(Source.class)) {
			Source source = method.getAnnotation(Source.class);
			MultipleDataSource.setDataSourceKey(source.value());
		} else {
			Object target = jp.getTarget();
			if (hasSourceAnnotation(target)) {
				Source source = target.getClass().getAnnotation(Source.class);
				MultipleDataSource.setDataSourceKey(source.value());
			} else {
				MultipleDataSource.setDataSourceKey(defaultDataSource);
			}
		}
	}


	private boolean hasSourceAnnotation(Object object) {
		if (object != null) {
			return object.getClass().isAnnotationPresent(Source.class);
		}
		return false;
	}
}
