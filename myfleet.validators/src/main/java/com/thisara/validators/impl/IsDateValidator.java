package com.thisara.validators.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;

import com.thisara.validators.IsDate;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class IsDateValidator implements ConstraintValidator<IsDate, String>{

	private final PropertyResolver propertyResolver;
	private String dateFormat;
	
	@Autowired
	public IsDateValidator(PropertyResolver propertyResolver) {
		this.propertyResolver = propertyResolver;
	}
	
	@Override
	public void initialize(IsDate isDate)
	{
	
		String dateFormat = isDate.dateFormatProperty();
		
		this.dateFormat = "".equals(dateFormat) ? "" : propertyResolver.getRequiredProperty(dateFormat, String.class);
		
		validateParameters();
	}
	
	private void validateParameters() {
		
		if (this.dateFormat == null) {
            throw new IllegalArgumentException("Date format cannot be empty.");
		}
	}
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		boolean isValidDate = true;
		
		try {
			
			LocalDate.parse(value, DateTimeFormatter.ofPattern(this.dateFormat));
			
		}catch(DateTimeParseException e) {
			isValidDate = false;
			context.disableDefaultConstraintViolation();
        	context.buildConstraintViolationWithTemplate("Date format should be " + this.dateFormat).addConstraintViolation();
		}
		return isValidDate;
	}
}
