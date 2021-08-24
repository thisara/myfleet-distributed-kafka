package com.thisara.validators.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;

import com.thisara.validators.IsDateTime;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class IsDateTimeValidator implements ConstraintValidator<IsDateTime, String>{

	private final PropertyResolver propertyResolver;
	private String dateTimeFormat;
	
	@Autowired
	public IsDateTimeValidator(PropertyResolver propertyResolver) {
		this.propertyResolver = propertyResolver;
	}
	
	@Override
	public void initialize(IsDateTime isDateTime) {
	
		String dateTimeFormat = isDateTime.dateTimeFormatProperty();
		
		this.dateTimeFormat = "".equals(dateTimeFormat) ? "" : propertyResolver.getRequiredProperty(dateTimeFormat, String.class);
		
		validateParameters();
	}
	
	private void validateParameters() {
		
		if (this.dateTimeFormat == null) {
            throw new IllegalArgumentException("Date Time format cannot be empty.");
		}
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		boolean isValidDate = true;
		
		try {
			
			LocalDateTime.parse(value, DateTimeFormatter.ofPattern(this.dateTimeFormat));
			
		}catch(DateTimeParseException e) {
			isValidDate = false;
			context.disableDefaultConstraintViolation();
        	context.buildConstraintViolationWithTemplate("Timestamp format should be " + this.dateTimeFormat).addConstraintViolation();
		}
		
		return isValidDate;
	}
}
