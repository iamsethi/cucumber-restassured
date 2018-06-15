package com.api.steps;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class UserSteps {

	public RequestSpecification request;
	public Response response;
	public ValidatableResponse json;

	public void constructOAuth1Request(String consumerKey, String consumerSecret, String accessToken,
			String tokenSecret) {
		request = given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).log().all();

	}

	public void constructOAuth2Request(String token) {
		request = given().auth().oauth2(token).log().all();

	}

	public void constructBasicRequest(String username, String password) {
		request = given().auth().preemptive().basic(username, password).log().all();

	}

	public void constructRequestAddAuthHeader(String token) {
		request = given().header("Authorization", "Bearer " + token);
	}

	public void constructMultiPartFile(File file) {
		try {
			request.given().header("Content-Type", "multipart/form-data").and().multiPart(file);
		} catch (NullPointerException e) {
			request = given().header("Content-Type", "multipart/form-data").and().multiPart(file);
		}

	}

	public void constructRequestQueryParam(String queryParam, String code) {
		try {
			request.given().queryParam(queryParam, code).log().all();
		} catch (NullPointerException e) {
			request = given().queryParam(queryParam, code).log().all();
		}
	}

	public void constructRequestPathParam(String pathParam, String code) {
		try {
			request.given().pathParam(pathParam, code).log().all();
		} catch (NullPointerException e) {
			request = given().pathParam(pathParam, code).log().all();
		}

	}

	public void postRequest(String post) {
		response = request.when().post(post).then().contentType(ContentType.JSON).extract().response();
		response.then().log().all();

	}

	public void postJsonBodyRequest(File file) {
		response = given().contentType("application/json").body(file).log().all().when().post().then()
				.contentType(ContentType.JSON).extract().response();
		response.then().log().all();

	}

	public void getRequest() {
		try {
			response = request.when().get();
			response.then().log().all();
		} catch (NullPointerException e) {
			response = given().when().get();
			response.then().log().all();
		}

	}

	public void searchIsExecutedSuccesfully(int statusCode) {
		json = response.then().statusCode(statusCode);
	}

	public void iShouldFindResponse(String expected) {
		response.then().body("RestResponse.result.name", is(expected));
	}

	public void iShouldFindFollowingResponse(Map<String, String> responseFields) {
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if (StringUtils.isNumeric(field.getValue())) {
				json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			} else {
				json.body(field.getKey(), equalTo(field.getValue()));
			}
		}

	}

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