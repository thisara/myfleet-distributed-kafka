package com.thisara.utils;

import org.springframework.web.client.RestTemplate;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class ExternalService {

	public Object getResponseObject(String url, Class<?> response) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		Object responseObject = restTemplate.getForObject(url, response);

		return responseObject;
	}
}
