package com.apis.stepdefinitions;

import com.apis.steps.UserSearchSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class TwitterStepDefinitions {

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String tokenSecret;

	@Steps
	UserSearchSteps userSearchSteps;

	@Given("^a user exists with OATH (\\d+)\\.(\\d+) with below details$")
	public void a_user_exists_with_OATH_with_below_details(int oauth, int version, DataTable accessFields) {

		this.consumerKey = accessFields.raw().get(0).get(1);
		this.consumerSecret = accessFields.raw().get(1).get(1);
		this.accessToken = accessFields.raw().get(2).get(1);
		this.tokenSecret = accessFields.raw().get(3).get(1);

	}

	@When("^a user post the tweet$")
	public void a_user_post_the_tweet(DataTable tweetMessage) {
		userSearchSteps.constructOAuthRequest(consumerKey, consumerSecret, accessToken, tokenSecret, "status",
				tweetMessage.raw().get(0).toString());

	}

}