package com.apis.stepdefinitions;

import com.amazon.cucumber.TestContext;
import com.api.steps.UserSteps;

import cucumber.api.java.en.When;

public class FileUploadStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;

	public FileUploadStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@When("^user upload a file$")
	public void user_upload_a_file() {

		userSteps
				.constructMultiPartFile("/home/ketan/git/cucumber-restassured/src/test/resources/upload/file 1.png");
		userSteps.postRequest("/uploadImage");

	}

}