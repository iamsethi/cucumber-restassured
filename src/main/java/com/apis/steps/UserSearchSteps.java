package com.apis.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class UserSearchSteps {

	private RequestSpecification request;
	private Response response;
	public ValidatableResponse json;

	@Step
	public void constructOAuthRequest(String consumerKey, String consumerSecret, String accessToken,
			String tokenSecret) {
		request.given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret);
		

	}

	@Step
	public void constructRequestQueryParam(String queryParam, String code) {
		request = given().queryParam(queryParam, code).log().all();

	}

	@Step
	public void constructRequestPathParam(String pathParam, String code) {
		request = given().pathParam(pathParam, code).log().all();

	}

	@Step
	public void constructRequestAddAuthHeader(String token) {
		request = given().header("Authorization", "Bearer " + token);

	}

	@Step
	public void postRequest(String post) {
		response = request.when().post(post);
		response.then().log().all();

	}

	@Step
	public void searchByCode(String CODE_SEARCH) {
		response = request.when().get(CODE_SEARCH);
		response.then().log().all();

	}

	@Step
	public void searchIsExecutedSuccesfully(int statusCode) {
		json = response.then().statusCode(statusCode);
	}

	@Step
	public void iShouldFindResponse(String expected) {
		response.then().body("RestResponse.result.name", is(expected));
	}

	@Step
	public void iShouldFindFollowingResponse(Map<String, String> responseFields) {
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if (StringUtils.isNumeric(field.getValue())) {
				json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			} else {
				json.body(field.getKey(), equalTo(field.getValue()));
			}
		}

	}

	@Step
	public void iShouldFindFollowingResponseInAnyOrder(Map<String, String> responseFields) {
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if (StringUtils.isNumeric(field.getValue())) {
				json.body(field.getKey(), containsInAnyOrder(Integer.parseInt(field.getValue())));
			} else {
				json.body(field.getKey(), containsInAnyOrder(field.getValue()));
			}
		}
	}

}