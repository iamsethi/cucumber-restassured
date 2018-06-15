package com.apis.stepdefinitions;

import java.io.File;

import com.amazon.cucumber.TestContext;
import com.api.managers.FileReaderManager;
import com.api.steps.UserSteps;
import static io.restassured.RestAssured.given;
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
				new File(FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "dog.json"));
		petId = response.jsonPath().get("id").toString();

	}

	@When("^user get a pet$")
	public void user_get_a_pet() {
		request = userSteps.constructRequestParam("id", petId);
		response = userSteps.getRequestWithParam(request, "id");
		response.then().assertThat().statusCode(200).log().all();

	}

	@When("^user upload a file$")
	public void user_upload_a_file() {
		request = userSteps.constructRequestParam("id", petId);
		request = userSteps.constructMultiPartFile(request,
				new File(FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "dog.png"));
		userSteps.postRequestWithParamPath(request, "id", "/uploadImage");

	}

	@When("^user update an existing pet$")
	public void user_update_an_existing_pet() {
		userSteps.postJsonBodyRequest(new File(
				FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "elephant.json"));

	}

	@When("^user finds pets by status$")
	public void user_finds_pets_by_status() {
		request = userSteps.constructRequestQueryParam(given(), "status", "available");
		response = userSteps.getRequestWithPath(request, "/findByStatus");
		response.then().assertThat().statusCode(200).log().all();

	}

	@When("^user deletes a pet$")
	public void user_deletes_a_pet() {
	}

	@When("^user checks the inventory$")
	public void user_checks_the_inventory() {

	}

}