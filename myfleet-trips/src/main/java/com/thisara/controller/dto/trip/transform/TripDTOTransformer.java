package com.thisara.controller.dto.trip.transform;

import java.util.List;

import com.thisara.controller.dto.trip.GetTripResponse;
import com.thisara.domain.Trip;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface TripDTOTransformer {

	public List<GetTripResponse> carPageTripList(List<Trip> tripList, String timezone);
}
