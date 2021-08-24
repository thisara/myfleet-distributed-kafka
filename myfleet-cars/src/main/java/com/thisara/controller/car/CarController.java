package com.thisara.controller.car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thisara.controller.dto.car.GetCarResponse;
import com.thisara.controller.dto.car.PostCarDTO;
import com.thisara.controller.dto.car.transform.CarDTOTransformer;
import com.thisara.domain.Car;
import com.thisara.service.CarService;
import com.thisara.service.exception.ServiceException;
import com.thisara.utils.response.SuccessResponse;
import com.thisara.validators.Size;

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
@RequestMapping("/cars")
public class CarController {

	private static Logger logger = Logger.getLogger(CarController.class.getName());

	@Autowired
	private CarService carService;

	@Autowired
	private CarDTOTransformer carDTOTransformer;

	@GetMapping("/search/{registrationNumber}")
	public ResponseEntity<List<GetCarResponse>> search(
			@Size(maxProperty = "car.registration.number.length.max")
			@PathVariable("registrationNumber") String registrationNumber)
			throws ServiceException {

		List<Car> carList = carService.search(registrationNumber);

		List<GetCarResponse> carDtos = carDTOTransformer.getCarResponses(carList);

		return new ResponseEntity<List<GetCarResponse>>(carDtos, HttpStatus.OK);
	}

	@GetMapping("/{registrationNumber}")
	public ResponseEntity<GetCarResponse> get(
			@Size(maxProperty = "car.registration.number.length.max")
			@PathVariable("registrationNumber") String registrationNumber) 
			throws ServiceException{

		Car car = carService.getCar(registrationNumber);

		GetCarResponse carDTO = carDTOTransformer.getCarResponse(car);

		return new ResponseEntity<GetCarResponse>(carDTO, HttpStatus.OK); 
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> persist(
			@RequestBody @Valid PostCarDTO postCarDTO) 
			throws ServiceException, Exception {
	
		carService.save(postCarDTO);

		return new ResponseEntity<SuccessResponse>(
				new SuccessResponse("Successfully created.", HttpStatus.OK.toString(), LocalDateTime.now().toString()),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/{carId}")
	public ResponseEntity<SuccessResponse> remove(
			@PathVariable("carId") String carId) 
			throws ServiceException {

		carService.remove(carId);

		return new ResponseEntity<SuccessResponse>(
				new SuccessResponse("Removed", HttpStatus.OK.toString(), LocalDateTime.now().toString()),
				HttpStatus.OK);
	}
}
