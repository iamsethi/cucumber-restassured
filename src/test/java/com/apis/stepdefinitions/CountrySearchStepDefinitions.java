package com.apis.stepdefinitions;

import com.amazon.cucumber.TestContext;
import com.api.steps.UserSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CountrySearchStepDefinitions {

	TestContext testContext;
	UserSteps userSteps;

	public CountrySearchStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@Given("^a country exists with \"([^\"]*)\"$")
	public void a_country_exists_with(String iso) {
		userSteps.constructRequestPathParam("isoCode", iso);
	}

	@When("^a user retrieves the country by iso$")
	public void a_user_retrieves_the_country_by_iso() {
		userSteps.getRequest();

	}

	@Then("^the status code is (\\d+)$")
	public void the_status_code_is(int statusCode) {
		userSteps.searchIsExecutedSuccesfully(statusCode);
	}

	@Then("^response includes the \"([^\"]*)\"$")
	public void response_includes_the(String countryName) {
		userSteps.iShouldFindResponse(countryName);
	}

}
