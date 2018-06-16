package com.apis.stepdefinitions;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import org.junit.Assert;

import com.amazon.cucumber.TestContext;
import com.api.managers.FileReaderManager;
import com.api.steps.UserSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SwaggerStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;
	public static String petId;
	private RequestSpecification request;
	private Response responseBody;

	public SwaggerStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@When("^user create a pet$")
	public void user_create_a_pet() {
		// http://petstore.swagger.io/v2/pet
		request = userSteps.constructRequestWithPath(given(), "/pet");
		responseBody = userSteps.postWithJsonFile(request,
				new File(FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "dog.json"));
		responseBody.then().assertThat().statusCode(200).log().all();
		petId = responseBody.jsonPath().get("id").toString();

	}

	@When("^user get a pet$")
	public void user_get_a_pet() {
		// http://petstore.swagger.io/v2/pet/:id
		request = userSteps.constructRequestWithPath(given(), "/pet");
		request = userSteps.constructRequestWithParam(request, "id", petId);

		responseBody = userSteps.getRequestWithParam(request, "id");
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^user upload a file$")
	public void user_upload_a_file() {
		// http://petstore.swagger.io/v2/pet/:id
		request = userSteps.constructRequestWithPath(given(), "/pet");
		request = userSteps.constructRequestWithParam(request, "id", petId);

		request = userSteps.constructMultiPartFile(request,
				new File(FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "dog.png"));
		responseBody = userSteps.postRequestWithParamPath(request, "id", "/uploadImage");
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@Then("^the response status code should be \"([^\"]*)\"$")
	public void theResponseStatusCodeShouldBe(int expectedResponseCode) {
		Assert.assertEquals(expectedResponseCode, responseBody.then().extract().statusCode());
	}

	@And("swagger response includes the following$")
	public void response_equals(Map<String, String> responseFields) {
		userSteps.iShouldFindFollowingResponse(responseBody, responseFields);
	}

	@When("^user update an existing pet$")
	public void user_update_an_existing_pet() {
		// http://petstore.swagger.io/v2/pet
		request = userSteps.constructRequestWithPath(given(), "/pet");
		responseBody = userSteps.postWithJsonFile(request, new File(
				FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "elephant.json"));
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^user finds pets by status$")
	public void user_finds_pets_by_status() {
		// http://petstore.swagger.io/v2/pet/findByStatus?status=available
		request = userSteps.constructRequestWithPath(given(), "/pet/finByStatus");
		request = userSteps.constructRequestQueryParam(request, "status", "available");
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^user deletes a pet$")
	public void user_deletes_a_pet() {
		// http://petstore.swagger.io/v2/pet/:id
		request = userSteps.constructRequestWithPath(given(), "/pet");
		request = userSteps.constructRequestWithParam(request, "id", petId);
		responseBody = userSteps.deleteRequestWithParam(request, "id");
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^user checks the inventory$")
	public void user_checks_the_inventory() {
		// http://petstore.swagger.io/v2/store/inventory
		request = userSteps.constructRequestWithPath(given(), "/store/inventory");
		responseBody = userSteps.getRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();

	}

}