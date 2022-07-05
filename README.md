# APIScenarios

## Table of contents
* [General info](#general-info)
* [Project Structure](#project-structure)
* [Technologies](#technologies)
* [Setup](#setup)
* [How to run a test](#How-to-run-a-test)
* [Assumptions](#assumptions)
* [Non-functional tests](#Non-functional tests)
* [Tool for Non-functional tests]()

## General info
This project is an automating the CocktailBD service using rest assured and testng.

## Project Structure
On an upper level it contains two directories: Reporting and src.
* Reporting store reports that are generated.
* src contains two directories: main and test.

### main directory
* contains two directories :
* resources directory has a JSON file that has the input values
* java directory has three directories :
* utilities directory which has two classes : BaseFramework and Reporting.
* models directory which has six classes for mapping a JSON object into a Java class, e.g reading the value of response body into a Drink class.
* automation directory has a TheCockTailDB Class where the actual automation of scenarios is happening.

#### TheCockTailDB Class
* checkstatus an abstract method for validating returned status.
* getResources method for doing the GET call.
*	getFoundResponseBody method for extracting body from response.

#### TheCockTailDB Class
*	Inherits BaseFramework class.
*	Implements the checkstatus method from BaseFramework class to validate a status after making a GET Call.
*	It has methods for each scenario: validate_IngredientsSearchedByName ,validate_CocktailsSearchByName, filterByAlcoholic and filterAlistBy_Categories_Glasses_ngredients_Alcoholic

##### The flow of these methods:
*	Validate status :	checkStatus(basicInformation);
*	Extract body from response	: responseBody = getFoundResponseBody();
*	Read response body into java class:	mapper.readValue(responseBody, Drinks.class);
*	Validate values : Assert.assertNotNull(drink.getStrDrink());

### test directory
*	Has a class CockTails that has all the test methods for executing the scenarios.
* Has data_providers package that has ListOfDataProviders class. this class has data providers that allows the iteration with different data inputs.

## Technologies
* Java
* Rest Assured
* Testng

## Setup
*	Install IDE e.g Intellij
*	Install JDK preferable java version 17
*	Install maven
*	Clone the project
*	Ensure that dependencies are loaded

## How to run a test
* Find this class \src\test\java\CockTails.java
*	Right click on the class name or meyhod name then select run
* View report on Reports about the execution

## Assumptions
* All the test Scenarios must pass therefore I adjusted validation so that they can pass.
## Non-functional tests
*	What number of users can work with the API at the same time
*	Response time for each transaction
## Tool for Non-functional tests
* Apache JMeter
