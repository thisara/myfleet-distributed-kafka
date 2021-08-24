package com.thisara.controller.trip;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thisara.controller.dto.driver.PostDriverDTO;
import com.thisara.service.DriverService;
import com.thisara.service.exception.ServiceException;
import com.thisara.utils.response.SuccessResponse;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Validated
@RestController
@RefreshScope
@RequestMapping("/drivers")
public class DriverController {

	@Autowired
	DriverService driverService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> persist(@RequestBody @Valid PostDriverDTO postDriverDTO) throws ServiceException {

		driverService.save(postDriverDTO);

		return new ResponseEntity<SuccessResponse>(
				new SuccessResponse("Created", HttpStatus.OK.toString(), LocalDateTime.now().toString()),
				HttpStatus.OK);
	}
}
