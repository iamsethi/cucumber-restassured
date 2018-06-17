package com.apis.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.Assert;

import com.amazon.cucumber.TestContext;
import com.api.managers.FileReaderManager;
import com.api.steps.UserSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
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
	private Response responseBody;
	private String tweetId;

	public TwitterStepDefinitions(TestContext context) {
		testContext = context;
		userSteps = testContext.getUserSteps();
		RestAssured.baseURI = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("TWITTER_BASEURI");
		RestAssured.basePath = FileReaderManager.getInstance().getServiceFileReader()
				.getServiceEndPoint("TWITTER_BASEPATH");

	}

	@Given("^a user exists with OATH (\\d+)\\.(\\d+) with below details$")
	public void a_user_exists_with_OATH_with_below_details(int oauth, int version, DataTable accessFields) {

		this.consumerKey = accessFields.raw().get(0).get(1);
		this.consumerSecret = accessFields.raw().get(1).get(1);
		this.accessToken = accessFields.raw().get(2).get(1);
		this.tokenSecret = accessFields.raw().get(3).get(1);

	}

	@When("^a user post the tweet$")
	public void a_user_post_the_tweet(String tweetMessage) {
		// https://api.twitter.com/1.1/statuses/update.json?status=Hi There!!
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/statuses/update.json");
		request = userSteps.constructRequestWithQueryParam(request, "status", tweetMessage);
		responseBody = userSteps.postRequest(request);
		responseBody.then().body(matchesJsonSchemaInClasspath("schema-json/twitter.json"));
		this.tweetId = responseBody.jsonPath().get("id_str");
	}

	@When("^user like the tweet$")
	public void user_like_the_tweet() {
		// https://api.twitter.com/1.1/favorites/create.json?id=243138128959913986
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/favorites/create.json");
		request = userSteps.constructRequestWithQueryParam(request, "id", tweetId);
		responseBody = userSteps.postRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^user unlike the tweet$")
	public void user_unlike_the_tweet() {
		// https://api.twitter.com/1.1/favorites/destroy.json?id=243138128959913986
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/favorites/destroy.json");
		request = userSteps.constructRequestWithQueryParam(request, "id", tweetId);
		responseBody = userSteps.postRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^a user delete the tweet$")
	public void a_user_delete_the_tweet() {
		// https://api.twitter.com/1.1/statuses/destroy/{id}.json
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/statuses/destroy/{id}.json");
		request = userSteps.constructRequestWithParam(request, "id", tweetId);
		responseBody = userSteps.postRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^a user get the tweet$")
	public void a_user_get_the_tweet() {
		// https://api.twitter.com/1.1/statuses/show.json?id=990896540611788800
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/statuses/show.json");
		request = userSteps.constructRequestWithQueryParam(request, "id", tweetId);
		responseBody = userSteps.getRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^a user get the Tweet timeline$")
	public void a_user_get_the_Tweet_timeline() {
		// https://api.twitter.com/1.1/statuses/home_timeline.json
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/statuses/home_timeline.json");
		responseBody = userSteps.getRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@When("^a user get the previous tweets in limit$")
	public void a_user_get_the_previous_tweets_in_limit() {
		// https://api.twitter.com/1.1/favorites/list.json?count=2&user_id=iam_sethi
		RequestSpecification request = userSteps.constructRequestWithPath(
				userSteps.constructOAuth1Request(consumerKey, consumerSecret, accessToken, tokenSecret),
				"/favorites/list.json");
		request = userSteps.constructRequestWithQueryParam(request, "count", "100");
		request = userSteps.constructRequestWithQueryParam(request, "user_id", "iam_sethi");
		responseBody = userSteps.getRequest(request);
		responseBody.then().assertThat().statusCode(200).log().all();

	}

	@Then("^twitter response status code should be \"([^\"]*)\"$")
	public void theResponseStatusCodeShouldBe(int expectedResponseCode) {
		Assert.assertEquals(expectedResponseCode, responseBody.then().extract().statusCode());
	}

}