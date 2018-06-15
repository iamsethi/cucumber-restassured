package com.apis.stepdefinitions;

import com.amazon.cucumber.TestContext;
import com.api.steps.UserSteps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.restassured.RestAssured;

public class Hooks {

	TestContext testContext;
	UserSteps userSteps;

	public Hooks(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@Before("@Upload")
	public static void upload() {
		RestAssured.baseURI = "http://petstore.swagger.io";
		RestAssured.basePath = "/v2/pet/{id}";
	}

	@Before("@CreatePet")
	public static void createPet() {
		RestAssured.baseURI = "http://petstore.swagger.io";
		RestAssured.basePath = "/v2/pet";
	}

	@Before("@ISO")
	public static void iso() {
		RestAssured.baseURI = "http://services.groupkt.com";
		RestAssured.basePath = "/country/get/iso2code/{isoCode}";
	}

	@Before("@ISBN")
	public static void isbn() {
		RestAssured.baseURI = "https://www.googleapis.com";
		RestAssured.basePath = "/books/v1/volumes";
	}

	@Before("@PostTweet")
	public static void init() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}

	@Before("@OAuth1")
	public static void OAuth1() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses/home_timeline.json";
	}

	@Before("@OAuth2")
	public static void OAuth2() {
		RestAssured.baseURI = "https://api.imgur.com";
		RestAssured.basePath = "/3/account/me/";
	}

	@Before("@BasicAuth")
	public static void BasicAuth() {
		RestAssured.baseURI = "https://postman-echo.com";
		RestAssured.basePath = "/basic-auth";
	}

	@After
	public void after() {
		RestAssured.reset();
		userSteps.request = null;
	}

}
