package com.apis.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.amazon.cucumber.TestContext;
import com.api.steps.UserSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class TwitterStepDefinitions {

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String tokenSecret;
	private String tweetId;

	TestContext testContext;
	UserSteps userSteps;

	public TwitterStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
	}

	@Given("^a user exists with OATH (\\d+)\\.(\\d+) with below details$")
	public void a_user_exists_with_OATH_with_below_details(int oauth, int version, DataTable accessFields) {

		this.consumerKey = accessFields.raw().get(0).get(1);
		this.consumerSecret = accessFields.raw().get(1).get(1);
		this.accessToken = accessFields.raw().get(2).get(1);
		this.tokenSecret = accessFields.raw().get(3).get(1);
		userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret);
	}

	@When("^a user post the tweet$")
	public void a_user_post_the_tweet(DataTable tweetMessage) {
		userSteps.constructRequestQueryParam("status", tweetMessage.raw().get(0).toString());
		userSteps.postRequest("update.json");
		userSteps.response.then().body(matchesJsonSchemaInClasspath("schema-json/twitter.json"));
		this.tweetId = userSteps.response.jsonPath().get("id_str");
	}

	@When("^a user delete the tweet$")
	public void a_user_delete_the_tweet() {
		userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret);
		userSteps.postRequest("/destroy/" + this.tweetId + ".json");

	}

}