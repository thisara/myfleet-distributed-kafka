package com.thisara.myfleet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thisara.exception.json.ExceptionJSON;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-07-04
 */
@Configuration
public class ExceptionJSONFactory {

	@Bean
	public ExceptionJSON getExceptionJson() {
		return new ExceptionJSON();
	}
}
