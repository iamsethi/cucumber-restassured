package com.apis.stepdefinitions;

import java.io.File;

import com.apis.steps.UserSearchSteps;

import static io.restassured.RestAssured.given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class FileUploadStepDefinitions {

	@Steps
	UserSearchSteps userSearchSteps;

	@When("^user upload a file$")
	public void user_upload_a_file() {

		userSearchSteps
				.constructMultiPartFile("/home/ketan/git/cucumber-restassured/src/test/resources/upload/file 1.png");
		userSearchSteps.postRequest("/uploadImage");

	}

}