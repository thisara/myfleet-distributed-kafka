package com.thisara.controller.trip;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thisara.controller.dto.trip.GetTripResponse;
import com.thisara.controller.dto.trip.PostTripDTO;
import com.thisara.controller.dto.trip.transform.TripDTOTransformer;
import com.thisara.domain.Trip;
import com.thisara.service.TripService;
import com.thisara.service.exception.ServiceException;
import com.thisara.utils.response.SuccessResponse;
import com.thisara.validators.IsDate;
import com.thisara.validators.IsTime;
import com.thisara.validators.IsTimezone;
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
@RequestMapping("/trips")
public class TripController {
	
	@Autowired
	public TripService tripService;

	@Autowired
	public TripDTOTransformer tripDTOTransformer;

	@GetMapping("/search/{registrationNumber}")
	public ResponseEntity<List<GetTripResponse>> search(
			@Size(maxProperty = "car.registration.number.length.max")
			@PathVariable("registrationNumber") String carRegistrationNumber,
			@IsDate(dateFormatProperty = "trip.date.format") @RequestParam("fromDate") String fromDate, 
			@IsTime(timeFormatProperty = "trip.time.format") @RequestParam("fromTime") String fromTime, 
			@IsTimezone @RequestParam("timezone") String timezone)
			throws ServiceException {

		List<Trip> tripList = tripService.search(fromDate, fromTime, carRegistrationNumber);

		List<GetTripResponse> tripListDtos = tripDTOTransformer.carPageTripList(tripList, timezone);

		return new ResponseEntity<List<GetTripResponse>>(tripListDtos, HttpStatus.OK);
	}

	@GetMapping("/{registrationNumber}")
	public ResponseEntity<List<GetTripResponse>> get(
			@Size(maxProperty = "car.registration.number.length.max")
			@PathVariable("registrationNumber") String registrationNumber, 
			@IsTimezone @RequestParam("timezone") String timezone) throws ServiceException{

		List<Trip> tripList = tripService.get(registrationNumber);

		List<GetTripResponse> tripListDtos = tripDTOTransformer.carPageTripList(tripList, timezone);

		return new ResponseEntity<List<GetTripResponse>>(tripListDtos, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> persist(@RequestBody @Valid PostTripDTO postTripDTO) throws ServiceException {

		tripService.save(postTripDTO);

		return new ResponseEntity<SuccessResponse>(
				new SuccessResponse("Created", HttpStatus.OK.toString(), LocalDateTime.now().toString()),
				HttpStatus.OK);
	}
}
