package com.thisara.validators.impl;

import java.util.logging.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;

import com.thisara.validators.Size;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class SizeValidator implements ConstraintValidator<Size, CharSequence>{

	Logger logger = Logger.getLogger(SizeValidator.class.getName());
	
    private final PropertyResolver propertyResolver;
    private int min;
    private int max;

    @Autowired
    public SizeValidator(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }
    
    @Override
    public void initialize(Size size) {
        
        String minProperty = size.minProperty();
    	String maxProperty = size.maxProperty();
        
    	this.min = "".equals(minProperty) ? 0 : propertyResolver.getRequiredProperty(minProperty, Integer.class);
        this.max = "".equals(maxProperty) ? 0 : propertyResolver.getRequiredProperty(maxProperty, Integer.class);
        
        validateParameters();
    }

    private void validateParameters() {
    	
        if (this.min < 0) {
            throw new IllegalArgumentException("The min parameter cannot be negative.");
        } else if (this.max < 0) {
            throw new IllegalArgumentException("The max parameter cannot be negative.");
        } else if (this.max < this.min) {
            throw new IllegalArgumentException("The length cannot be negative.");
        }
    }
    
	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		
		if (value == null) {
            return true;
        } else {
            int length = value.length();
            boolean isInRange = length >= this.min && length <= this.max;
            if(!isInRange) {
            	context.disableDefaultConstraintViolation();
            	context.buildConstraintViolationWithTemplate("Value length should be less than " + this.max + " and more than " + min).addConstraintViolation();
            }
            return isInRange;
        }
    }
}
