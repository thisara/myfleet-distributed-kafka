package com.thisara.utils;

import java.util.UUID;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class ErrorIDGenerator {

	public synchronized static String getErrorId() {
		
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
	}
}
