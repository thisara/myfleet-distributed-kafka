package com.thisara.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "fl_car")
public class Car extends Audit{

	@Id
	@GeneratedValue
	@Column(name = "fc_id")
	private Long id;

	@Size(maxProperty = "car.registration.number.length.max")
	@Column(name = "fc_reg_number", unique = true)
	private String registrationNumber;

	@Size(maxProperty = "car.chasis.number.length.max")
	@Column(name = "fc_chasis_number", nullable = false, unique = true, length = 10)
	private String chasisNumber;

	@Size(maxProperty = "car.color.length.max")
	@Column(name = "fc_chasis_color", nullable = false, length = 10)
	private String chasisColor;

	@OneToOne(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Engine engine;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tyre> tyres = new ArrayList<Tyre>();

	public void addTyre(Tyre seat) {
		tyres.add(seat);
		seat.setCar(this);
	}

	public void removeTyre(Tyre seat) {
		tyres.remove(seat);
		seat.setCar(null);
	}

	public void addEngine(Engine engine) {
		if (engine == null) {
			if (this.engine != null) {
				this.engine.setCar(null);
			}
		} else {
			engine.setCar(this);
		}
		this.engine = engine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getChasisNumber() {
		return chasisNumber;
	}

	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	public String getChasisColor() {
		return chasisColor;
	}

	public void setChasisColor(String chasisColor) {
		this.chasisColor = chasisColor;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public List<Tyre> getTyres() {
		return tyres;
	}

	public void setTyres(List<Tyre> tyres) {
		this.tyres = tyres;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Car))
			return false;

		Car car = (Car) o;
		return Objects.equals(chasisNumber, car.chasisNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(chasisNumber);
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", registrationNumber=" + registrationNumber + ", chasisNumber=" + chasisNumber
				+ ", chasisColor=" + chasisColor + ", engine=" + engine + ", tyres=" + tyres + "]";
	}

}
