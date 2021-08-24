# About Code #

- This is the car microservice which is a part of myfleet.

## Tech Stack 

- Java 8
- Springboot
- JPA (Spring Data)
- H2 DB
- Model Mapper

## Setup Guide 

##### Dependancy Setup

##### Validators :
1. Clone the Code : `` git clone git@bitbucket.org:thisara_udaya/myfleet.validators.git -b expnval ``

2. Install Dependencies : `` mvnw install ``

##### Utils :
1. Clone the Code : `` git clone git@bitbucket.org:thisara_udaya/myfleet.utils.git -b expnval ``

2. Install Dependencies : `` mvnw install ``

##### Microservice Setup
1. Clone the Code : `` git clone git@bitbucket.org:thisara_udaya/myfleet-cars.git -b expnval ``

2. Update the validate and utils target file path on pom.xml

2. Install Dependencies : `` mvnw install ``

3. Run the Code : `` mvnw spring-boot:run ``

## APIs

> GET http://localhost:8081/cars/search/:carId

> GET http://localhost:8081/cars/:carId

> POST http://localhost:8081/cars

> DELETE http://localhost:8081/cars

## ERROR Codes

	Data Access Layer

	DA-GEN001 - General Data Access Exception
	DA-DAT001 - No Results Found, Entity Not Found
	DA-DAT002 - Data Integrity Violation, Data Exception, Constraint Violation Exception, Date Time Parse Exception, NullPointer, Illegal Argument Exception 
	DA-DAO001 - Query Timeout Exception, Connection Refused

	Service Layer

	SE-GEN002 - General Service Exception 
	SE-TRN001 - Transaction Exception
	SE-DAT001 - Entity Not Found
	SE-DAT002 - Data Inconsistency

	Controller

	CO-BND001 - Data Bind Exception
	CO-DAT003 - Validation Exception
	CO-DAT002 - Null Pointer, Illegal Argument Exception, Data Integrity Violation Exception
	GEN000 - Unhanddled Exception
	
#### Demo video link :  https://www.youtube.com/watch?v=Lkq9-srSWbc