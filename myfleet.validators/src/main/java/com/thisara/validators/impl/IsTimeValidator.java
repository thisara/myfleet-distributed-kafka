package com.thisara.validators.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;

import com.thisara.validators.IsTime;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class IsTimeValidator implements ConstraintValidator<IsTime, String>{

	private final PropertyResolver propertyResolver;
	private String timeFormat;
	
	@Autowired
	public IsTimeValidator(PropertyResolver propertyResolver) {
		this.propertyResolver = propertyResolver;
	}
	
	@Override
	public void initialize(IsTime isTime)
	{
	
		String timeFormat = isTime.timeFormatProperty();
		
		this.timeFormat = "".equals(timeFormat) ? "" : propertyResolver.getRequiredProperty(timeFormat, String.class);
		
		validateParameters();
	}
	
	private void validateParameters() {
		
		if (this.timeFormat == null) {
            throw new IllegalArgumentException("Time format cannot be empty.");
		}
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		boolean isValidDate = true;
		
		try {
			
			LocalTime.parse(value, DateTimeFormatter.ofPattern(this.timeFormat));
			
		}catch(DateTimeParseException e) {
			isValidDate = false;
			context.disableDefaultConstraintViolation();
        	context.buildConstraintViolationWithTemplate("Time format should be " + this.timeFormat).addConstraintViolation();
		}
		return isValidDate;
	}
}
