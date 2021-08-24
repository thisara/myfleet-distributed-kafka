package com.thisara.myfleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@ComponentScan(basePackages = {
		"com.thisara.controller",
		"com.thisara.service",
		"com.thisara.dao",
		"com.thisara.myfleet",
		"com.thisara.exception"
})
@EnableJpaRepositories(basePackages = {
		"com.thisara.dao"
})
@EntityScan(basePackages = {
		"com.thisara.domain"
})
@EnableWebMvc
@SpringBootApplication
public class MyfleetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfleetApplication.class, args);
	}
}
