package com.thisara.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "fl_tyre")
public class Tyre extends Audit{

	@Id
	@GeneratedValue
	@Column(name = "ft_id")
	private Long id;

	@NotBlank
	@Size(max = 15)
	@Column(name = "ft_number")
	private String number;

	@NotBlank
	@Column(name = "ft_year")
	private String year;

	@ManyToOne(fetch = FetchType.LAZY)
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

		if (!(o instanceof Tyre))
			return false;

		Tyre tyre = (Tyre) o;
		return Objects.equals(number, tyre.number);
	}

	@Override
	public String toString() {
		return "Tyre [id=" + id + ", number=" + number + ", year=" + year + ", status=" + getStatus() + ", car=" + car + "]";
	}

}
