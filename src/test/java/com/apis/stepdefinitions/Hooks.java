package com.apis.stepdefinitions;

import cucumber.api.java.Before;
import io.restassured.RestAssured;

public class Hooks {

	@Before("@PostTweet")
	public static void init() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}

	@Before("@Auth")
	public static void auth() {
		RestAssured.baseURI = "https://api.imgur.com";
		RestAssured.basePath = "/3/account/me/";
	}

}
