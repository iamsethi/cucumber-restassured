package com.apis.stepdefinitions;

import com.apis.steps.UserSearchSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

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
		userSearchSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret);
	}

	@When("^a user post the tweet$")
	public void a_user_post_the_tweet(DataTable tweetMessage) {
		userSearchSteps.constructRequestQueryParam("status", tweetMessage.raw().get(0).toString());

		userSearchSteps.postRequest("/update.json");
		userSearchSteps.response.then().body(matchesJsonSchemaInClasspath("json/twitter.json"));
		Serenity.setSessionVariable("tweetId").to(userSearchSteps.response.jsonPath().get("id_str"));
	}

	@When("^a user delete the tweet$")
	public void a_user_delete_the_tweet() {
		userSearchSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret);
		userSearchSteps.postRequest("/destroy/" + Serenity.sessionVariableCalled("tweetId") + ".json");

	}

}