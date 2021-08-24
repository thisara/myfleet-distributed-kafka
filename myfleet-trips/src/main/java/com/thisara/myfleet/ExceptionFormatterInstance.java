package com.thisara.myfleet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thisara.utils.ExceptionFormatter;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Configuration
public class ExceptionFormatterInstance {

	@Bean
	public ExceptionFormatter getExceptionFormatter() {
		return new ExceptionFormatter();
	}
}
