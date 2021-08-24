package com.thisara.validators.impl;

import java.time.ZoneId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.thisara.validators.IsTimezone;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
public class IsTimezoneValidator implements ConstraintValidator<IsTimezone, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		boolean isValidTimezone = (value !=null && ZoneId.getAvailableZoneIds().contains(value));
		
		return isValidTimezone;
	}
}