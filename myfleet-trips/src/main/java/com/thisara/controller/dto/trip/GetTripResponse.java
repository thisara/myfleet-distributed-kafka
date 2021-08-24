package com.thisara.controller.dto.trip;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class GetTripResponse {

	private int tripId;
	private String tripNumber;
	private String distance;
	private String carRegistrationNumber;
	private String startTimestamp;
	private String endTimestamp;
	private String recordAge;
	private DriverDTO driver;

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(String startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public String getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(String endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public String getRecordAge() {
		return recordAge;
	}

	public void setRecordAge(String recordAge) {
		this.recordAge = recordAge;
	}

	public DriverDTO getDriver() {
		return driver;
	}

	public void setDriver(DriverDTO driver) {
		this.driver = driver;
	}

	public String getCarRegistrationNumber() {
		return carRegistrationNumber;
	}

	public void setCarRegistrationNumber(String carRegistrationNumber) {
		this.carRegistrationNumber = carRegistrationNumber;
	}

	@Override
	public String toString() {
		return "GetTripResponse [tripId=" + tripId + ", tripNumber=" + tripNumber + ", distance=" + distance
				+ ", carRegistrationNumber=" + carRegistrationNumber + ", driver=" + driver + ", startTimestamp="
				+ startTimestamp + ", endTimestamp=" + endTimestamp + ", recordAge=" + recordAge + "]";
	}

}
