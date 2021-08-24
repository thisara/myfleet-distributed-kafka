package com.thisara.controller.dto.car;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
public class PostCarDTO {
	
	@NotNull @Size(maxProperty = "car.registration.number.length.max") private String registrationNumber;
	@NotNull @Size(maxProperty = "car.chasis.number.length.max") private String chasisNumber;
	@NotNull @Size(maxProperty = "car.color.length.max") private String chasisColor;
	@NotNull @Size(maxProperty = "car.engine.number.length.max") private String number;
	@NotNull @Max(maxProperty = "car.engine.capacity.max") private Double capacity;
	@NotNull @Valid private List<TyreDTO> tyreData;
	@NotNull @Size(maxProperty = "user.name.length.max") private String user;
	
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
	public List<TyreDTO> getTyreData() {
		return tyreData;
	}
	public void setTyreData(List<TyreDTO> tyreData) {
		this.tyreData = tyreData;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "PostCarDTO [registrationNumber=" + registrationNumber + ", chasisNumber=" + chasisNumber
				+ ", chasisColor=" + chasisColor + ", number=" + number + ", capacity=" + capacity + ", tyreData="
				+ tyreData + ", user=" + user + "]";
	}
		
}
