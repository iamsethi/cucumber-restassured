package com.apis.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.amazon.cucumber.TestContext;
import com.api.steps.UserSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TwitterStepDefinitions {
	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String tokenSecret;

	TestContext testContext;
	UserSteps userSteps;

	private RequestSpecification request;
	private Response response;
	private String tweetId;

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
		request = userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret);
	}

	@When("^a user post the tweet$")
	public void a_user_post_the_tweet(DataTable tweetMessage) {
		request = userSteps.constructRequestQueryParam(request,"status", tweetMessage.raw().get(0).toString());
		response = userSteps.postRequestWithPath(request, "update.json");
		response.then().body(matchesJsonSchemaInClasspath("schema-json/twitter.json"));
		this.tweetId = response.jsonPath().get("id_str");
	}

	@When("^a user delete the tweet$")
	public void a_user_delete_the_tweet() {
		userSteps.postRequestWithPath(request, "/destroy/" + this.tweetId + ".json");

	}

}