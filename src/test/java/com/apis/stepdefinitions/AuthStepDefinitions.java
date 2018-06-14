package com.apis.stepdefinitions;

import java.util.List;
import java.util.Map;

import com.apis.steps.UserSearchSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class AuthStepDefinitions {

	public String clientId;
	public String clientSecret;
	public String accessTokenUrl;
	public String token;
	public String callBackUrl;

	@Steps
	UserSearchSteps userSearchSteps;

	@Given("^a user exists with (.*) with below details$")
	public void a_user_exists_with_OATH_with_below_details(DataTable accessFields) {

		List<List<String>> data = accessFields.raw();

		for (Map<String, String> data2 : accessFields.asMaps(String.class, String.class)) {

		}

		this.clientId = accessFields.raw().get(0).get(1);
		this.clientSecret = accessFields.raw().get(1).get(1);
		this.accessTokenUrl = accessFields.raw().get(2).get(1);
		this.callBackUrl = accessFields.raw().get(3).get(1);
		this.token = accessFields.raw().get(4).get(1);

	}

	@When("^a user send the request$")
	public void a_user_send_the_request() {
		userSearchSteps.constructOAuth2Request(token);
		userSearchSteps.searchByCode("images");
	}

}