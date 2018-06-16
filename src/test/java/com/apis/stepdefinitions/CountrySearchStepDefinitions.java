package com.apis.stepdefinitions;

import com.amazon.cucumber.TestContext;
import static io.restassured.RestAssured.given;
import com.api.steps.UserSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CountrySearchStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;
	private RequestSpecification request;
	private Response response;

	public CountrySearchStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@Given("^a country exists with \"([^\"]*)\"$")
	public void a_country_exists_with(String countryCode) {
		request = userSteps.constructRequestWithParam(given(), "code", countryCode);
	}

	@When("^a user retrieves the country by iso$")
	public void a_user_retrieves_the_country_by_iso() {
		// http://services.groupkt.com/country/get/iso2code/BR
		response = userSteps.getRequestWithParam(request, "code"); // BR is code and it's value is countryCode
		response.then().assertThat().statusCode(200).log().all();
	}

	@Then("^the status code is (\\d+)$")
	public void the_status_code_is(int statusCode) {
		userSteps.searchIsExecutedSuccesfully(response, statusCode);
	}

	@Then("^response includes the \"([^\"]*)\"$")
	public void response_includes_the(String countryName) {
		userSteps.iShouldFindResponse(response, countryName);
	}

}
