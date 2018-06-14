package com.apis.stepdefinitions;

import cucumber.api.java.Before;
import io.restassured.RestAssured;

public class Hooks {

	@Before("@PostTweet")
	public static void init() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
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

}
