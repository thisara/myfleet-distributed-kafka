package com.thisara.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "fl_engine", uniqueConstraints = @UniqueConstraint(name = "number", columnNames = "fe_number"))
public class Engine extends Audit{

	@Id
	@GeneratedValue
	@Column(name = "fe_id")
	private Long id;

	@Size(maxProperty = "car.engine.number.length.max")
	@Column(name = "fe_number", unique = true)
	private String number;
	
	@Max(maxProperty = "car.engine.capacity.max")
	@Column(name = "fe_capacity")
	private Double capacity;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "fc_id")
	@JsonIgnore
	private Car car;

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

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Engine))
			return false;

		Engine engine = (Engine) o;
		return Objects.equals(number, engine.number);
	}

	@Override
	public String toString() {
		return "Engine [id=" + id + ", number=" + number + ", capacity=" + capacity + ", car=" + car + "]";
	}

}
