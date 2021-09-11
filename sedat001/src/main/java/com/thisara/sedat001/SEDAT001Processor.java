package com.thisara.sedat001;

import java.util.function.Function;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-08-30
 */
@Configuration
public class SEDAT001Processor {

	@Bean
	public Function<KStream<String, String>, KStream<String, String>> sedatCode(){
		
		return kstream -> kstream.filter((key, message) -> {

			return message.contains("SEDAT001");
		});
	}
}