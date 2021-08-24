package com.thisara.exception.handler;

import org.aspectj.lang.annotation.Pointcut;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class ExceptionPointCuts {

	@Pointcut("execution(* com.thisara.controller.exception.ExceptionController.handleException(..)) "
			+ "|| execution(* com.thisara.controller.car.exception.CarExceptionController.handleException(..))")
	public void exceptionHandller() {}
	
	@Pointcut("execution(* com.thisara.controller.car.exception.CarExceptionController.handleException(..))")
	public void carExceptionHandller() {}

}
