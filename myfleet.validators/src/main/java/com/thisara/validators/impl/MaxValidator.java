package com.thisara.validators.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;

import com.thisara.validators.Max;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class MaxValidator implements ConstraintValidator<Max, Double>{

	private final PropertyResolver propertyResolver;
	private double max;
	
	@Autowired
	public MaxValidator(PropertyResolver propertyResolver) {
		this.propertyResolver = propertyResolver;
	}
	
	@Override
	public void initialize(Max max) {
	
		String maxProperty = max.maxProperty();
		
		this.max = "".equals(maxProperty) ? -1d : propertyResolver.getRequiredProperty(maxProperty, Double.class);
		
		validateParameters();
	}
	
	private void validateParameters() {
		
		if (this.max < 0) {
            throw new IllegalArgumentException("The max parameter cannot be negative.");
		}
	}

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		
		boolean isLessThanMax = false;
		
		try {
			
			isLessThanMax = (value < this.max) ? true : false;
			
		}catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
		if(!isLessThanMax) {
			context.disableDefaultConstraintViolation();
        	context.buildConstraintViolationWithTemplate("Value should be less than " + this.max).addConstraintViolation();
		}
		return isLessThanMax;
	}
	
}
