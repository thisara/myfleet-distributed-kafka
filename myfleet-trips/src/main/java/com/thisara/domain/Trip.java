package com.thisara.domain;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

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
@Entity
@Table(name = "fl_trip")
public class Trip extends Audit{
	
	@Id
	@GeneratedValue
	@Column(name = "ft_id")
	private Long id;

	@NotBlank
	@Size(maxProperty = "trip.number.length.max", minProperty = "trip.number.length.min")
	@Column(name = "ft_number")
	private String number;

	@Positive
	@Max(maxProperty = "trip.distance.max")
	@Column(name = "ft_distance")
	private Double distance;

	@NotBlank
	@Column(name= "ft_car_reg_number")
	private String carRegistrationNumber;
	
	@NotBlank
	@Size(maxProperty = "trip.timezone.length.max")
	@Column(name = "ft_trip_start_timezone")
	private String tripStartTimezone;
	
	@NotBlank
	@Size(maxProperty = "trip.timezone.length.max")
	@Column(name = "ft_trip_end_timezone")
	private String tripEndTimezone;

	@NotNull
	@PastOrPresent
	@Column(name = "ft_trip_start_timestamp")
	private OffsetDateTime tripStartTimestamp;
	
	@NotNull
	@PastOrPresent	
	@Column(name = "ft_trip_end_timestamp")
	private OffsetDateTime tripEndTimestamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fd_id")
	private Driver driver;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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

	public OffsetDateTime getTripStartTimestamp() {
		return tripStartTimestamp;
	}

	public void setTripStartTimestamp(OffsetDateTime tripStartTimestamp) {
		this.tripStartTimestamp = tripStartTimestamp;
	}

	public OffsetDateTime getTripEndTimestamp() {
		return tripEndTimestamp;
	}

	public void setTripEndTimestamp(OffsetDateTime tripEndTimestamp) {
		this.tripEndTimestamp = tripEndTimestamp;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Driver))
			return false;

		return id != null && id.equals(((Driver) o).getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", number=" + number + ", distance=" + distance + ", carRegistrationNumber="
				+ carRegistrationNumber + ", tripStartTimezone=" + tripStartTimezone + ", tripEndTimezone="
				+ tripEndTimezone + ", tripStartTimestamp=" + tripStartTimestamp + ", tripEndTimestamp="
				+ tripEndTimestamp + ", driver=" + driver + "]";
	}
}
