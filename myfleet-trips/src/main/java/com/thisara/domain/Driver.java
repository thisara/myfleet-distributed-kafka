package com.thisara.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Entity
@Table(name = "fl_driver")
public class Driver extends Audit{

	@Id
	@GeneratedValue
	@Column(name = "fd_id")
	private Long id;

	@NotBlank
	@Size(max = 10)
	@Column(name = "fd_license_number")
	private String licenceNumber;

	@NotBlank
	@Size(max = 10)
	@Column(name = "fd_first_name")
	private String firstName;

	@NotBlank
	@Size(max = 10)
	@Column(name = "fd_last_name")
	private String lastName;

	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Trip> trips = new ArrayList<Trip>();

	public void addTrip(Trip trip) {
		trips.add(trip);
		trip.setDriver(this);
	}

	public void removeTrip(Trip trip) {
		trips.remove(trip);
		trip.setDriver(null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Driver))
			return false;

		return licenceNumber != null && licenceNumber.equals(((Driver) o).getLicenceNumber());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "Driver [id=" + id + ", licenceNumber=" + licenceNumber + ", firstName=" + firstName + ", lastName="
				+ lastName + ", trips=" + trips + "]";
	}
}
