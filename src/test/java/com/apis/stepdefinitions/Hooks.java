package com.apis.stepdefinitions;

import com.amazon.cucumber.TestContext;
import com.api.managers.FileReaderManager;
import com.api.steps.UserSteps;

import cucumber.api.java.Before;
import io.restassured.RestAssured;

public class Hooks {

	TestContext testContext;
	UserSteps userSteps;

	public Hooks(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@Before("@OAuth1")
	public static void OAuth1() {
		RestAssured.baseURI = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("OAUTH1_BASEURI");
		RestAssured.basePath = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("OAUTH1_BASEPATH");
	}

	@Before("@OAuth2")
	public static void OAuth2() {
		RestAssured.baseURI = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("OAUTH2_BASEURI");
		RestAssured.basePath = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("OAUTH2_BASEPATH");

	}

	@Before("@BasicAuth")
	public static void BasicAuth() {
		RestAssured.baseURI = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("BASIC_AUTH_BASEURI");
		RestAssured.basePath = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("BASIC_AUTH_BASEPATH");
	}

}
