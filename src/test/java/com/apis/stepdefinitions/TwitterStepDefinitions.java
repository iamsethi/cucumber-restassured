package com.apis.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.Assert;
import static io.restassured.RestAssured.given;
import com.amazon.cucumber.TestContext;
import com.api.steps.UserSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TwitterStepDefinitions {
	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String tokenSecret;

	TestContext testContext;
	UserSteps userSteps;
	public static ResponseSpecBuilder builder;
	public static ResponseSpecification responseSpec;
	private RequestSpecification requestBody;
	private Response responseBody;
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

	}

	@When("^a user post the tweet$")
	public void a_user_post_the_tweet(DataTable tweetMessage) {
		// https://api.twitter.com/1.1/statuses/update.json?status=Hi There!!
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/update.json");
		request = userSteps.constructRequestQueryParam(request, "status", tweetMessage.raw().get(0).toString());
		responseBody = userSteps.postRequest(request);
		responseBody.then().body(matchesJsonSchemaInClasspath("schema-json/twitter.json"));
		this.tweetId = responseBody.jsonPath().get("id_str");
	}

	@When("^a user delete the tweet$")
	public void a_user_delete_the_tweet() {
		// https://api.twitter.com/1.1/statuses/destroy/{id}.json
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret), "/destroy");
		request = userSteps.constructRequestWithParam(request, "id", tweetId);
		userSteps.postRequestWithParam(request, "id");

	}

	@Then("^twitter response status code should be \"([^\"]*)\"$")
	public void theResponseStatusCodeShouldBe(int expectedResponseCode) {
		Assert.assertEquals(expectedResponseCode, responseBody.then().extract().statusCode());
	}

}