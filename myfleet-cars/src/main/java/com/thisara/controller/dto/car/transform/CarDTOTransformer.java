package com.thisara.controller.dto.car.transform;

import java.util.List;

import com.thisara.controller.dto.car.GetCarResponse;
import com.thisara.domain.Car;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public interface CarDTOTransformer {

	public List<GetCarResponse> getCarResponses(List<Car> carList);
	
	public GetCarResponse getCarResponse(Car car);
}
