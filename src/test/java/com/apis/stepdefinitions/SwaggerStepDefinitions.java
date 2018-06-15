package com.apis.stepdefinitions;

import java.io.File;

import com.amazon.cucumber.TestContext;
import com.api.managers.FileReaderManager;
import com.api.steps.UserSteps;

import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SwaggerStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;
	public static String petId;
	private RequestSpecification request;
	private Response response;

	public SwaggerStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@When("^user create a pet$")
	public void user_create_a_pet() {
		response = userSteps.postJsonBodyRequest(
				new File(FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "data.json"));
		petId = response.jsonPath().get("id").toString();

	}

	@When("^user get a pet$")
	public void user_get_a_pet() {
		request = userSteps.constructRequestPathParam("id", petId);
		response = userSteps.getRequestWithPathParam(request, "id");

	}

	@When("^user upload a file$")
	public void user_upload_a_file() {
		request = userSteps.constructRequestPathParam("id", petId);
		request = userSteps.constructMultiPartFile(request,
				new File(FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "dog.png"));
		userSteps.postRequestWithPathParam(request, "id", "/uploadImage");

	}

}