package com.apis.stepdefinitions;

import com.apis.steps.UserSearchSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class CountrySearchStepDefinitions {
	private String ISO_REQ_URL = "http://services.groupkt.com/country/get/iso2code/{pathParam}";

	@Steps
	UserSearchSteps userSearchSteps;

	@Given("^a country exists with \"([^\"]*)\"$")
	public void a_country_exists_with(String iso) {
		userSearchSteps.constructRequestPathParam("iso", iso);
	}

	@When("^a user retrieves the country by iso$")
	public void a_user_retrieves_the_country_by_iso() {
		userSearchSteps.searchByCode(ISO_REQ_URL);
	}

	@Then("^the status code is (\\d+)$")
	public void the_status_code_is(int statusCode) {
		userSearchSteps.searchIsExecutedSuccesfully(statusCode);
	}

	@Then("^response includes the \"([^\"]*)\"$")
	public void response_includes_the(String countryName) {
		userSearchSteps.iShouldFindResponse(countryName);
	}

}
