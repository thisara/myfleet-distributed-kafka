package com.thisara.controller.dto.trip.transform;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thisara.controller.dto.trip.GetTripResponse;
import com.thisara.domain.Trip;

/*
 * Copyright the original author.
 * 
 * @author Thisara Alawala
 * @author https://mytechblogs.com
 * @author https://www.youtube.com/channel/UCRJtsC5VYYhmKnEqAGLKc2A
 * @since 2021-05-30
 */
@Component
public class TripDTOTransformerImpl implements TripDTOTransformer {

	@Autowired
	public ModelMapper modelMapper;

	Logger logger = LoggerFactory.getLogger(TripDTOTransformerImpl.class);

	@Value("${trip.datetime.pattern}")
	private String dateTimePattern;

	@Override
	public List<GetTripResponse> carPageTripList(List<Trip> tripList, String timezone) {

		modelMapper.getConfiguration()
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true)
				.setMatchingStrategy(MatchingStrategies.STANDARD);

		Converter<String, String> caseConverter = new Converter<String, String>() {
			public String convert(MappingContext<String, String> context) {
				return context.getSource() == null ? null : context.getSource().toUpperCase();
			}
		};

		Converter<OffsetDateTime, String> timezoneConverter = new Converter<OffsetDateTime, String>() {

			String actualTime = null;

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

			public String convert(MappingContext<OffsetDateTime, String> context) {

				actualTime = context.getSource().toInstant().atZone(ZoneId.of(timezone)).format(dateTimeFormatter);

				return actualTime;
			}
		};

		Converter<OffsetDateTime, String> recordAgeConverter = new Converter<OffsetDateTime, String>() {

			String recordAge = null;

			long years;
			long months;
			long days;
			long hours;
			long minutes;
			long seconds;

			public String convert(MappingContext<OffsetDateTime, String> context) {

				LocalDateTime recordDateTime = context.getSource().toInstant().atZone(ZoneId.of(timezone))
						.toLocalDateTime();

				LocalDateTime currentDateTime  = LocalDateTime.now(ZoneId.of(timezone));

				years = ChronoUnit.YEARS.between(recordDateTime, currentDateTime);
				months = ChronoUnit.MONTHS.between(recordDateTime, currentDateTime);
				days = ChronoUnit.DAYS.between(recordDateTime, currentDateTime);
				hours = ChronoUnit.HOURS.between(recordDateTime, currentDateTime);
				minutes = ChronoUnit.MINUTES.between(recordDateTime, currentDateTime);
				seconds = ChronoUnit.SECONDS.between(recordDateTime, currentDateTime);

				if (years > 0) {
					recordAge = years + " years";
				} else if (months > 0) {
					recordAge = months + " months";
				} else if (days > 0) {
					recordAge = days + " days";
				} else if (hours > 0) {
					recordAge = hours + " hours";
				} else if (minutes > 0) {
					recordAge = minutes + " minutes";
				} else if (seconds > 0) {
					recordAge = seconds + " seconds";
				}

				return recordAge;
			}
		};

		TypeMap<Trip, GetTripResponse> tripTypeMap = modelMapper.getTypeMap(Trip.class, GetTripResponse.class);

		if (tripTypeMap == null) {
			tripTypeMap = modelMapper.createTypeMap(Trip.class, GetTripResponse.class);
		}

		tripTypeMap.addMappings(mappings -> mappings.using(timezoneConverter).map(Trip::getTripStartTimestamp,
				GetTripResponse::setStartTimestamp));
		tripTypeMap.addMappings(mappings -> mappings.using(recordAgeConverter).map(Trip::getTripStartTimestamp,
				GetTripResponse::setRecordAge));

		modelMapper.addConverter(caseConverter);

		List<GetTripResponse> tripDTOs = tripList.stream().map(source -> modelMapper.map(source, GetTripResponse.class))
				.collect(Collectors.toList());

		return tripDTOs;
	}

}
