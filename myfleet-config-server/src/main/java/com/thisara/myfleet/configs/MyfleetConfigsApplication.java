package com.thisara.myfleet.configs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@SpringBootApplication
@EnableConfigServer
public class MyfleetConfigsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfleetConfigsApplication.class, args);
	}

}
