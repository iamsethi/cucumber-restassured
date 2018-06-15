package com.apis.stepdefinitions;

import java.io.File;

import com.amazon.cucumber.TestContext;
import com.api.managers.FileReaderManager;
import com.api.steps.UserSteps;

import cucumber.api.java.en.When;

public class SwaggerStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;

	public SwaggerStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@When("^user create a pet$")
	public void user_create_a_pet() {
		userSteps.postJsonBodyRequest(
				new File(FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "data.json"));
	}

	@When("^user upload a file$")
	public void user_upload_a_file() {
		userSteps.constructRequestPathParam("id", "20091990");
		userSteps.constructMultiPartFile("/home/ketan/git/cucumber-restassured/src/test/resources/upload/file 1.png");
		userSteps.postRequest("/uploadImage");

	}

}