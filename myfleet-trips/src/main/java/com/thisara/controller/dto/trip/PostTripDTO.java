package com.thisara.controller.dto.trip;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.thisara.validators.IsDateTime;
import com.thisara.validators.IsTimezone;
import com.thisara.validators.Max;
import com.thisara.validators.Size;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class PostTripDTO {

	@NotNull private long driverId;
	@NotBlank private String tripNumber;
	@NotBlank @Size(maxProperty = "trip.number.length.max") private String carRegistrationNumber;
	@Positive @Max(maxProperty = "trip.distance.max") private double distance;
	@IsTimezone private String tripStartTimezone;
	@IsTimezone private String tripEndTimezone;
	@NotNull @IsDateTime(dateTimeFormatProperty = "trip.datetime.format") private String tripStartTimestamp;
	@NotNull @IsDateTime(dateTimeFormatProperty = "trip.datetime.format") private String tripEndTimestamp;
	@NotBlank private String user;

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getCarRegistrationNumber() {
		return carRegistrationNumber;
	}

	public void setCarRegistrationNumber(String carRegistrationNumber) {
		this.carRegistrationNumber = carRegistrationNumber;
	}

	public String getTripStartTimezone() {
		return tripStartTimezone;
	}

	public void setTripStartTimezone(String tripStartTimezone) {
		this.tripStartTimezone = tripStartTimezone;
	}

	public String getTripEndTimezone() {
		return tripEndTimezone;
	}

	public void setTripEndTimezone(String tripEndTimezone) {
		this.tripEndTimezone = tripEndTimezone;
	}

	public String getTripStartTimestamp() {
		return tripStartTimestamp;
	}

	public void setTripStartTimestamp(String tripStartTimestamp) {
		this.tripStartTimestamp = tripStartTimestamp;
	}

	public String getTripEndTimestamp() {
		return tripEndTimestamp;
	}

	public void setTripEndTimestamp(String tripEndTimestamp) {
		this.tripEndTimestamp = tripEndTimestamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
