package com.thisara.controller.dto.driver;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.thisara.validators.Size;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class PostDriverDTO {

	@NotEmpty @Size(maxProperty = "driver.licence.length.max") private String licenceNumber;
	@NotEmpty @Size(maxProperty = "driver.firatname.length.max") private String firstName;
	@NotEmpty @Size(maxProperty = "driver.lastname.length.max") private String lastName;
	@NotBlank private String user;
	
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
