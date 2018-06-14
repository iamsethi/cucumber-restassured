package com.apis.stepdefinitions;

import java.util.List;

import com.apis.steps.UserSearchSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class AuthStepDefinitions {

	public String clientId;
	public String clientSecret;
	public String accessTokenUrl;
	public String callBackUrl;
	public String token;
	public String username;
	public String password;

	@Steps
	UserSearchSteps userSearchSteps;

	@Given("^a user exists with Basic Auth with below details$")
	public void a_user_exists_with_basic_auth_with_below_details(DataTable accessFields) {
		List<List<String>> data = accessFields.raw();
		this.username = data.get(0).get(1);
		this.password = data.get(1).get(1);

		userSearchSteps.constructBasicRequest(username, password);

	}

	@Given("^a user exists with OAuth2 with below details$")
	public void a_user_exists_with_OATH2_with_below_details(DataTable accessFields) {
		List<List<String>> data = accessFields.raw();
		this.clientId = data.get(0).get(1);
		this.clientSecret = data.get(1).get(1);
		this.accessTokenUrl = data.get(2).get(1);
		this.callBackUrl = data.get(3).get(1);
		this.token = data.get(4).get(1);
		userSearchSteps.constructOAuth2Request(token);
	}

	@When("^a user send the request$")
	public void a_user_send_the_request() {
		userSearchSteps.getRequest();
		;
	}

}